package threadpool;

import java.util.LinkedList;
import java.util.Queue;

/**
 * BlockingQueue implementation.
 *
 * @author hitansu
 *
 */
public class PoolBlockingQueue {

    /* Default MAX Queue size */
    private static final int DEFAULT_BLOCKING_QUEUE_LIMIT= 5;

    /* MAX Queue size */
    private final int MAX_LIMIT;

    /* LinkedList represents a Queue */
    private Queue<Object> blockingQueue= null;

    public PoolBlockingQueue(int MAX_LIMIT) {
        this.MAX_LIMIT= MAX_LIMIT;
        init();
    }

    private void init() {
        blockingQueue= new LinkedList<Object>();
    }

    public PoolBlockingQueue() {
        this.MAX_LIMIT= DEFAULT_BLOCKING_QUEUE_LIMIT;
        init();
    }

    /**
     * This method causes a Thread to wait while adding task when the Queue is full
     * @param task
     * @throws InterruptedException
     */
    public synchronized void enqueueTask(Object task) throws InterruptedException{
        while(blockingQueue.size()>= MAX_LIMIT) {
            System.out.println(Thread.currentThread() +" is waiting for enqueue. Current Task size- "+blockingQueue.size());
            wait();
        }
        if(blockingQueue.size()< MAX_LIMIT) { // cant be < 0,put it to be safer
            notifyAll();
        }
        blockingQueue.add(task);
    }

    /**
     * This method causes a Thread to wait while removing task when Queue is empty
     * @return
     * @throws InterruptedException
     */
    public synchronized Object dequeue() throws InterruptedException{
    	while(blockingQueue.size()<= 0) {
        	wait();
        }     
        if(blockingQueue.size()> 0) {
            notifyAll();
        }
        Object task = blockingQueue.remove();
        return task;
    }
}
