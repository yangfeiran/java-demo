package com.chinacloud.yangfeiran;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.google.common.collect.Lists;

@SuppressWarnings("deprecation")
public class HBaseUtils {

	private static String ZKIP = "172.16.50.83";
	private static final String PORT = "2181";
	private static Configuration config = null;
	private static HTablePool tp = null;
	private static String firstCF = "cf";
	private static HBaseAdmin hBaseAdmin;

	public static void init() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.property.clientPort", PORT); 
		config.set("hbase.zookeeper.quorum", ZKIP); 
		hBaseAdmin = new HBaseAdmin(config);
	}
	
	public static HTableInterface deleteTable(String tableName) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
    	Configuration conf = HBaseConfiguration.create();
        String ZKHost = "172.16.50.22:2181";
		String[] zks = ZKHost.split(",");
		String quorum = zks[0].substring(0, zks[0].indexOf(":"));
		for (int i = 1; i < zks.length; i++) {
			quorum = quorum + "," + zks[i].substring(0, zks[i].indexOf(":"));
		}
		String zkport = zks[0].substring(zks[0].indexOf(":") + 1);
		conf.set("hbase.zookeeper.quorum", quorum);
		conf.set("hbase.zookeeper.property.clientPort", zkport);
    	HBaseAdmin hBaseAdmin = new HBaseAdmin(conf); 
    	hBaseAdmin.disableTable(tableName);  
    	hBaseAdmin.deleteTable(tableName); 
    	hBaseAdmin.close();
    	return null;
    }

	/*
	 * get hbase table
	 */
	public static HTableInterface getTable(String tableName)
			throws MasterNotRunningException, ZooKeeperConnectionException, IOException {

		if (StringUtils.isEmpty(tableName)) {
			return null;
		}

		System.out.println("---------------------");
		System.out.println(hBaseAdmin.tableExists(tableName));
		return null;
	}

	/* change byte to array */
	public static byte[] getBytes(String str) {
		if (str == null)
			str = "";
		return Bytes.toBytes(str);
	}

	private static String getRandomRowKey(final String tableName) {

		ResultScanner scanner = null;
		try {

			HTableInterface table = getTable(tableName);
			// Scan scan = getScan(startRow, stopRow);
			Scan scan = new Scan();
			scan.setMaxResultSize(10);
			scan.setStartRow("".getBytes());
			scan.setCaching(1000);
			scan.setCacheBlocks(false);
			scanner = table.getScanner(scan);
			for (Result result : scanner) {

				Cell[] rawCells = result.rawCells();
				for (Cell cell : rawCells) {
					String clonerow = toStr(CellUtil.cloneRow(cell));
					// String qualifier = toStr(CellUtil.cloneQualifier(cell));
					return clonerow;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			closeScanner(scanner);
		}
		throw new IllegalStateException("no rowkey find");
	}

	public static List<String> getQualifiers(final String tableName) {

		ResultScanner scanner = null;
		try {

			HTableInterface table = getTable(tableName); // 获取筛�?�对�?
			// Scan scan = getScan(startRow, stopRow);
			Scan scan = new Scan();
			scan.setMaxResultSize(10);

			scan.setCaching(1000);
			scan.setCacheBlocks(false);
			scanner = table.getScanner(scan);
			for (Result result : scanner) {

				List<String> qualifiers = Lists.newArrayList();
				Cell[] rawCells = result.rawCells();
				for (Cell cell : rawCells) {
					String qualifier = toStr(CellUtil.cloneQualifier(cell));
					qualifiers.add(qualifier);
					firstCF = toStr(CellUtil.cloneFamily(cell));

				}

				return qualifiers;
			}

		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			closeScanner(scanner);
		}
		throw new IllegalStateException("no data in table !");
	}

	@SuppressWarnings("unused")
	private static Scan getScan(String startRow, String stopRow) {

		Scan scan = new Scan();
		scan.setStartRow(getBytes(startRow));
		scan.setStopRow(getBytes(stopRow));
		return scan;
	}

	private static void closeScanner(ResultScanner scanner) {
		if (scanner != null)
			scanner.close();
	}

	private static String toStr(byte[] bt) {
		return Bytes.toString(bt);
	}

	public static String builderImpalaSql(String database, String tableName) {

		StringBuffer sql = new StringBuffer();
		sql.append("CREATE EXTERNAL TABLE ");
		sql.append(database + ".");
		sql.append(tableName + "(");

		StringBuffer fields = new StringBuffer();
		fields.append("rowKey" + " ");
		fields.append("string ,");

		List<String> qualifiers = getQualifiers(tableName);

		if (qualifiers != null && qualifiers.size() > 0) {

			int size = qualifiers.size();

			for (int i = 0; i < size; i++) {

				if (i != size - 1) {
					fields.append(qualifiers.get(i) + " ");
					fields.append("string ,");
				} else {
					fields.append(qualifiers.get(i) + " ");
					fields.append("string ) ");
				}

			}
		} else {
			throw new IllegalStateException("get hbase qualifier failed");
		}

		sql.append(fields.toString());
		sql.append(" ROW FORMAT SERDE 'org.apache.hadoop.hive.hbase.HBaseSerDe' ");
		sql.append(" STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' ");
		sql.append("WITH SERDEPROPERTIES (\"hbase.columns.mapping\" = \":key,");

		StringBuffer hbasefields = new StringBuffer();
		if (qualifiers != null && qualifiers.size() > 0) {

			int size = qualifiers.size();

			for (int i = 0; i < size; i++) {
				if (i != size - 1) {
					hbasefields.append(firstCF + ":");
					hbasefields.append(qualifiers.get(i) + ",");
				} else {
					hbasefields.append(firstCF + ":");
					hbasefields.append(qualifiers.get(i) + "\")");
				}
			}
		}

		sql.append(hbasefields.toString());
		sql.append(" TBLPROPERTIES(\"hbase.table.name\" =\"" + tableName + "\")");

		return sql.toString();
	}

	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		init();
		String s = getRandomRowKey("clobtest_13");
		System.out.println(s);
	}


	public static String getZKIP() {
		return ZKIP;
	}

	public static void setZKIP(String zKIP) {
		ZKIP = zKIP;
	}

}
