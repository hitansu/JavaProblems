package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ThreadPool {
	  /* Default MAX Thread count in the Pool */
    private static final int DEFAULT_THREAD_COUNT= 5;

    /* Default MAX Task count which can be executed at a time */
    private static final int DEFAULT_TASK_COUNT= 5;

    private  boolean isShutDown= false;
    private boolean isStarted= false;

    /* List of Thread present in Pool*/
    List<PoolThread> threadList;

    /* BlockingQueue implementation */
    PoolBlockingQueue blockingQueue;

    public ThreadPool() {
        threadList= new ArrayList<>(DEFAULT_THREAD_COUNT);
        init(DEFAULT_THREAD_COUNT, DEFAULT_TASK_COUNT);
    }


    public ThreadPool(int max_thread_count, int max_task_count) {
        threadList= new ArrayList<>(max_thread_count);
        init(max_thread_count, max_task_count);
    }

    private void init(int max_thread_count, int max_task_count) {
        blockingQueue= new PoolBlockingQueue();
        addThreadToPool(max_thread_count);

    }

    private void addThreadToPool(int threadCount) {
        for(int i= 0;i< threadCount;i++) {
            PoolThread poolThread= new PoolThread(blockingQueue);
            threadList.add(poolThread);
        }
    }


    /**
     * Enqueu task to the ThreadPool.
     * Throws Exception if the Task is not instance of either Runnable or Callable
     * @param task
     * @throws InterruptedException
     */
    public synchronized void enqueueTask(Object task) throws InterruptedException{

        if(!isStarted) {
            System.out.println("The has not started yet.First start it ");
            throw new RuntimeException("The Pool has not started.First start it ");
        }
        if(task instanceof Runnable || task instanceof Callable) {
            blockingQueue.enqueueTask(task);
        }else {
            throw new RuntimeException("Not a Valid Task");
        }
    }

    /**
     * Starts the ThreadPool
     * Start all the Threads
     */
    public synchronized void execute() {
        if(isShutDown) {
            throw new RuntimeException(" Trying to start a ThreadPool which is already shutdown");
        }
        isStarted= true;
        for(PoolThread poolThread: threadList) {
            poolThread.start();
        }
    }

    /**
     * ShutDown the ThreadPool
     * Stop all the THreads
     */
    public synchronized void shutDown() {
        isShutDown= true;
        for(PoolThread poolThread: threadList) {
            poolThread.stopThread();
        }
    }

}
