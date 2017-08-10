package com.chinacloud.yangfeiran;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/7/31.
 */
public class PriorityBlockingQueueDemo {
public static void main(String[] args){
    Random rand = new Random(47);
    ExecutorService exec = Executors.newCachedThreadPool();
    PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<Runnable>();
    exec.execute(new PrioritizedTaskConsumer(queue));
    exec.execute(new PrioritizedTaskProducer(queue,exec));
//    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor();
}
}

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private Random rand = new Random(47);
    private static int counter = 0;
    private final int id = counter++;
    private final int priority;
    protected static List<PrioritizedTask> sequence = new ArrayList<PrioritizedTask>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    public int compareTo(PrioritizedTask arg) {
        return priority < arg.priority ? 1 : (priority > arg.priority ? -1 : 0);
    }

    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
        } catch (InterruptedException e) {

        }
        System.out.println(this);
    }

    public String toString() {
        String s = "";
        for(PrioritizedTask pt : sequence){
            s+=" " +pt.priority;
        }
        return String.format("[%1$-3d]", priority) + " Task " + id +s;
    }

    public String summary() {
        return "(" + id + ":" + priority + ")";
    }

    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService exec;

        public EndSentinel(ExecutorService e) {
            super(-1);
            exec = e;

        }

        public void run() {
            int count = 0;
            for (PrioritizedTask pt : sequence) {
                System.out.println(pt.summary());
                if (++count % 5 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

class PrioritizedTaskProducer implements Runnable {
    private Random rand = new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService exec;

    public PrioritizedTaskProducer(Queue<Runnable> q, ExecutorService e) {
        queue = q;
        exec = e;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            queue.add(new PrioritizedTask(rand.nextInt(10)));
            Thread.yield();
        }
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }
            for (int i = 0; i < 10; i++) {
                queue.add(new PrioritizedTask(i));
            }
            TimeUnit.MILLISECONDS.sleep(1000);
            queue.add(new PrioritizedTask.EndSentinel(exec));

        } catch (InterruptedException e) {

        }
        System.out.println("Finished PrioritizedTaskProducer");
    }

}
class  PrioritizedTaskConsumer implements Runnable{
    private PriorityBlockingQueue<Runnable> q;
    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q){
        this.q=q;
    }
    public void run(){
        try{
            while (!Thread.interrupted()){
                q.take().run();
            }
        }catch (InterruptedException e){

        }
        System.out.println("Finished PrioritizedTaskConsumer");
    }
}