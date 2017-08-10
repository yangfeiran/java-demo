package com.chinacloud.yangfeiran;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.sqoop.client.SqoopClient;
import org.apache.sqoop.model.MConfig;
import org.apache.sqoop.model.MConnector;
import org.apache.sqoop.model.MDriverConfig;
import org.apache.sqoop.model.MFromConfig;
import org.apache.sqoop.model.MInput;
import org.apache.sqoop.model.MJob;
import org.apache.sqoop.model.MLink;
import org.apache.sqoop.model.MLinkConfig;
import org.apache.sqoop.model.MSubmission;
import org.apache.sqoop.model.MToConfig;
import org.apache.sqoop.submission.counter.Counter;
import org.apache.sqoop.submission.counter.CounterGroup;
import org.apache.sqoop.submission.counter.Counters;
import org.apache.sqoop.validation.Status;

public class Sqoop2Client {

	public static final String HDFS = "hdfs-connector";
	public static final String KITE = "kite-connector";
	public static final String JDBC = "generic-jdbc-connector";
	public static final long ERROR = -1;

	static void describe(SqoopClient client, String connectorName) {
		MConnector mConnector = client.getConnector(connectorName);
		List<MConfig> configs = client.getConnector(connectorName).getLinkConfig().getConfigs();
		ResourceBundle resource = client.getConnectorConfigBundle(mConnector.getPersistenceId());
		for (MConfig config : configs) {
			System.out.println(resource.getString(config.getLabelKey()) + ":");
			List<MInput<?>> inputs = config.getInputs();
			for (MInput input : inputs) {
				System.out.println(resource.getString(input.getLabelKey()) + " : " + input.getValue());
			}
			System.out.println();
		}
	}

	static void describeAllLinks(SqoopClient client) {
		List<MLink> mLinkList = client.getLinks();
		for (MLink link : mLinkList) {
			System.out.println(link.getName());
			MLinkConfig linkConfig = link.getConnectorLinkConfig();
			List<MConfig> configs = linkConfig.getConfigs();
			ResourceBundle resource = client.getConnectorConfigBundle(link.getConnectorId());
			for (MConfig config : configs) {
				System.out.println(resource.getString(config.getLabelKey()) + ":");
				List<MInput<?>> inputs = config.getInputs();
				for (MInput input : inputs) {
					System.out.println(resource.getString(input.getLabelKey()) + " : " + input.getValue());
				}
				System.out.println();
			}
		}
	}

	static long hdfs_Connector(SqoopClient client, String linkName) {
		MLink mLink = client.createLink(HDFS);
		mLink.setName(linkName);
		mLink.setCreationUser("hdfs");
		MLinkConfig mLinkConfig = mLink.getConnectorLinkConfig();
		mLinkConfig.getStringInput("linkConfig.uri").setValue("hdfs://master01.huacloud.com:8020");
		Status status = client.saveLink(mLink);
		if (status.canProceed()) {
			return mLink.getPersistenceId();
		} else {
			System.out.println("Something went wrong when creating the link");
			return ERROR;
		}
	}

	static long kite_Connector(SqoopClient client, String linkName) {
		MLink mLink = client.createLink(KITE);
		mLink.setName(linkName);
		mLink.setCreationUser("root");
		MLinkConfig mLinkConfig = mLink.getConnectorLinkConfig();
		mLinkConfig.getStringInput("linkConfig.hdfsHostAndPort").setValue("172.16.80.70:8020");
		Status status = client.saveLink(mLink);
		if (status.canProceed()) {
			return mLink.getPersistenceId();
		} else {
			System.out.println("Something went wrong when creating the link");
			return ERROR;
		}
	}

	static long generic_Jdbc_Connector_Mysql(SqoopClient client, String linkName) {
		MLink mLink = client.createLink(JDBC);
		mLink.setName(linkName);
		mLink.setCreationUser("root");
		MLinkConfig mLinkConfig = mLink.getConnectorLinkConfig();
		mLinkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:mysql://172.16.50.21/datasync");
		mLinkConfig.getStringInput("linkConfig.jdbcDriver").setValue("com.mysql.jdbc.Driver");
		mLinkConfig.getStringInput("linkConfig.username").setValue("root");
		mLinkConfig.getStringInput("linkConfig.password").setValue("654321");
		Status status = client.saveLink(mLink);
		if (status.canProceed()) {
			return mLink.getPersistenceId();
		} else {
			System.out.println("Something went wrong when creating the link");
			return ERROR;
		}
	}
	
	static long generic_Jdbc_Connector_Oracle(SqoopClient client, String linkName) {
		MLink mLink = client.createLink(JDBC);
		mLink.setName(linkName);
		mLink.setCreationUser("hdfs");
		MLinkConfig mLinkConfig = mLink.getConnectorLinkConfig();
		mLinkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:oracle:thin:@172.16.50.24:1521:dev");
		mLinkConfig.getStringInput("linkConfig.jdbcDriver").setValue("oracle.jdbc.driver.OracleDriver");
		mLinkConfig.getStringInput("linkConfig.username").setValue("stargate");
		mLinkConfig.getStringInput("linkConfig.password").setValue("123456");
		Status status = client.saveLink(mLink);
		if (status.canProceed()) {
			return mLink.getPersistenceId();
		} else {
			System.out.println("Something went wrong when creating the link: " );
			return ERROR;
		}
	}
	
	static long generic_Jdbc_Connector_Phoenix(SqoopClient client, String linkName) {
		MLink mLink = client.createLink(JDBC);
		mLink.setName(linkName);
		mLink.setCreationUser("root");
		MLinkConfig mLinkConfig = mLink.getConnectorLinkConfig();
		mLinkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:phoenix:172.16.80.71:2181");
		mLinkConfig.getStringInput("linkConfig.jdbcDriver").setValue("org.apache.phoenix.jdbc.PhoenixDriver");
		Status status = client.saveLink(mLink);
		if (status.canProceed()) {
			return mLink.getPersistenceId();
		} else {
			System.out.println("Something went wrong when creating the link");
			return ERROR;
		}
	}

	public static void deleteAllLinks(SqoopClient client) {
		List<MLink> links = client.getLinks();
		for (MLink link : links) {
			client.deleteLink(link.getPersistenceId());
		}
	}
	
	public static void deleteAllJobs(SqoopClient client){
		List<MJob> jobLists = client.getJobs();
        if(jobLists !=null && jobLists.size()>0) {
            for (MJob mjob : jobLists) {
                client.deleteJob(mjob.getPersistenceId());
            }
        }
	}

	public static long mysqlToHive(SqoopClient client) {
		MJob job = client.createJob(generic_Jdbc_Connector_Mysql(client, "jdbc_link"), kite_Connector(client, "kite_link"));
		System.out.println("1111111111111111111");
		describeAllLinks(client);
		System.out.println("1111111111111111111");
		job.setName("mysqlToHive");
		job.setCreationUser("root");
		MFromConfig mFromConfig = job.getFromJobConfig();
		mFromConfig.getStringInput("fromJobConfig.schemaName").setValue("datasync");
		mFromConfig.getStringInput("fromJobConfig.tableName").setValue("job");
		mFromConfig.getStringInput("fromJobConfig.partitionColumn").setValue("jobId");
		MToConfig mToConfig = job.getToJobConfig();
//		mToConfig.getStringInput("toJobConfig.uri").setValue("dataset:hive:chinacloud/yangfeiran_1111");
		mToConfig.getStringInput("toJobConfig.uri").setValue("dataset:hdfs:/tmp/example1");
		mToConfig.getEnumInput("toJobConfig.fileFormat").setValue("PARQUET");
//		MDriverConfig mDriverConfig = job.getDriverConfig();
//		mDriverConfig.getIntegerInput("throttlingConfig.numExtractors").setValue(1);
		Status status = client.saveJob(job);
		if (status.canProceed()) {
			System.out.println("Created Job with Job ID: " + job.getPersistenceId());
			return job.getPersistenceId();
		} else {
			System.out.println("Something went wrong creating the job");
			return ERROR;
		}
	}
	
	public static long mysqlToHDFS(SqoopClient client) {
		MJob job = client.createJob(generic_Jdbc_Connector_Mysql(client, "jdbc_link"), hdfs_Connector(client, "hdfs_link"));
		System.out.println("1111111111111111111");
		describeAllLinks(client);
		System.out.println("1111111111111111111");
		job.setName("mysqlToHive");
		job.setCreationUser("root");
		MFromConfig mFromConfig = job.getFromJobConfig();
		mFromConfig.getStringInput("fromJobConfig.schemaName").setValue("datasync");
		mFromConfig.getStringInput("fromJobConfig.tableName").setValue("job");
		mFromConfig.getStringInput("fromJobConfig.partitionColumn").setValue("jobId");
		MToConfig mToConfig = job.getToJobConfig();
//		mToConfig.getStringInput("toJobConfig.uri").setValue("dataset:hive:chinacloud/yangfeiran_1111");
		mToConfig.getStringInput("toJobConfig.outputDirectory").setValue("/tmp/example7");
		mToConfig.getEnumInput("toJobConfig.outputFormat").setValue("TEXT_FILE");
		mToConfig.getBooleanInput("toJobConfig.overrideNullValue").setValue(false);
		mToConfig.getStringInput("toJobConfig.nullValue").setValue("");
//		mToConfig.getEnumInput("toJobConfig.compression").setValue("GZIP");
//		mToConfig.getStringInput("toJobConfig.customCompression").setValue("GZIP");
		
//		MDriverConfig mDriverConfig = job.getDriverConfig();
//		mDriverConfig.getIntegerInput("throttlingConfig.numExtractors").setValue(1);
		Status status = client.saveJob(job);
		if (status.canProceed()) {
			System.out.println("Created Job with Job ID: " + job.getPersistenceId());
			return job.getPersistenceId();
		} else {
			System.out.println("Something went wrong creating the job");
			return ERROR;
		}
	}
	
	public static long oracleToHDFS(SqoopClient client) {
		long i = generic_Jdbc_Connector_Oracle(client, "jdbc_link");
		System.out.println(i);
		long u = hdfs_Connector(client, "hdfs_link");
		MJob job = client.createJob(i, u);
		System.out.println("1111111111111111111");
		describeAllLinks(client);
		System.out.println("1111111111111111111");
		job.setName("oracleToHive");
		job.setCreationUser("root");
		MFromConfig mFromConfig = job.getFromJobConfig();
		mFromConfig.getStringInput("fromJobConfig.schemaName").setValue("STARGATE");
		mFromConfig.getStringInput("fromJobConfig.tableName").setValue("TEST1");
		mFromConfig.getStringInput("fromJobConfig.partitionColumn").setValue("ID");
		MToConfig mToConfig = job.getToJobConfig();
//		mToConfig.getStringInput("toJobConfig.uri").setValue("dataset:hive:chinacloud/yangfeiran_1111");
		mToConfig.getStringInput("toJobConfig.outputDirectory").setValue("/tmp/example1");
		mToConfig.getEnumInput("toJobConfig.outputFormat").setValue("TEXT_FILE");
		mToConfig.getBooleanInput("toJobConfig.overrideNullValue").setValue(false);
		mToConfig.getStringInput("toJobConfig.nullValue").setValue("");
//		mToConfig.getEnumInput("toJobConfig.compression").setValue("GZIP");
//		mToConfig.getStringInput("toJobConfig.customCompression").setValue("GZIP");
		
//		MDriverConfig mDriverConfig = job.getDriverConfig();
//		mDriverConfig.getIntegerInput("throttlingConfig.numExtractors").setValue(1);
		Status status = client.saveJob(job);
		if (status.canProceed()) {
			System.out.println("Created Job with Job ID: " + job.getPersistenceId());
			return job.getPersistenceId();
		} else {
			System.out.println("Something went wrong creating the job");
			return ERROR;
		}
	}
	
	public static long phoenixToHDFS(SqoopClient client) {
		MJob job = client.createJob(generic_Jdbc_Connector_Phoenix(client, "phoenix_from_link"), hdfs_Connector(client, "hdfs_link"));
		System.out.println("1111111111111111111");
		describeAllLinks(client);
		System.out.println("1111111111111111111");
		job.setName("phoenixToHDFS");
		job.setCreationUser("root");
		MFromConfig mFromConfig = job.getFromJobConfig();
		mFromConfig.getStringInput("fromJobConfig.schemaName").setValue("\"\"");
		mFromConfig.getStringInput("fromJobConfig.tableName").setValue("USER");
		mFromConfig.getStringInput("fromJobConfig.partitionColumn").setValue("ID");
		MToConfig mToConfig = job.getToJobConfig();
//		mToConfig.getStringInput("toJobConfig.uri").setValue("dataset:hive:chinacloud/yangfeiran_1111");
		mToConfig.getStringInput("toJobConfig.outputDirectory").setValue("/tmp/example8");
		mToConfig.getEnumInput("toJobConfig.outputFormat").setValue("TEXT_FILE");
		mToConfig.getBooleanInput("toJobConfig.overrideNullValue").setValue(false);
		mToConfig.getStringInput("toJobConfig.nullValue").setValue("");
//		mToConfig.getEnumInput("toJobConfig.compression").setValue("GZIP");
//		mToConfig.getStringInput("toJobConfig.customCompression").setValue("GZIP");
		
//		MDriverConfig mDriverConfig = job.getDriverConfig();
//		mDriverConfig.getIntegerInput("throttlingConfig.numExtractors").setValue(1);
		Status status = client.saveJob(job);
		if (status.canProceed()) {
			System.out.println("Created Job with Job ID: " + job.getPersistenceId());
			return job.getPersistenceId();
		} else {
			System.out.println("Something went wrong creating the job");
			return ERROR;
		}
	}
	
	public static void jobSubmit(SqoopClient client, long jobID){
		MSubmission mSubmission = client.startJob(jobID);
		System.out.println("Job Submission Status : " + mSubmission.getStatus());
		System.out.println("Hadoop job id :" + mSubmission.getExternalJobId());
		System.out.println("Job link : " + mSubmission.getExternalLink());
		
		System.out.println("Process: " + String.format("%.2f %%", mSubmission.getProgress() * -100));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		while (mSubmission.getStatus().isRunning() && mSubmission.getProgress() != ERROR) {
            System.out.println("Process: " + String.format("%.2f %%", mSubmission.getProgress() * -100));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		MSubmission mSubmission_end = client.stopJob(jobID);
		Counters counters = mSubmission_end.getCounters();
		if (counters != null) {
			System.out.println("Counters:");
			for (CounterGroup counterGroup : counters) {
				System.out.print("\t");
				System.out.println(counterGroup.getName());
				for (Counter counter : counterGroup) {
					System.out.print("\t\t");
					System.out.print(counter.getName());
					System.out.print(": ");
					System.out.println(counter.getValue());
				}
			}
		}
		
		if (mSubmission_end.getError() != null) {
			System.out.println("Exception info : " + mSubmission.getError());
		}
		
		
	}

	public static void main(String[] args) {
		String url = "http://172.16.50.81:12000/sqoop/";
		SqoopClient client = new SqoopClient(url);
		// describe(client, KITE);
		deleteAllJobs(client);
		deleteAllLinks(client);
		// mLinkConfig.getStringInput("linkConfig.Configurationdirectory").setValue("/tmp");

		long jobID = oracleToHDFS(client);
		jobSubmit(client, jobID);
		 

	}
}
