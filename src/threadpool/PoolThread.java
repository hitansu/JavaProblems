package threadpool;

import java.util.concurrent.Callable;

/**
 * Thread implementation which is added to ThreadPool
 *
 * @author hitansu
 */
public class PoolThread extends Thread {

    /* boolean indicating whether Thread is running or not */
    private boolean isStopped = false;

    /* BlockingQueue instance */
    private PoolBlockingQueue blockingQueue = null;

    public PoolThread(PoolBlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isStopped) {
            try {
                Object task = blockingQueue.dequeue();
                if (task instanceof Callable) {
                    Callable<?> callableTask = (Callable<?>) task;
                    Object call = callableTask.call();
                    System.out.println("call:: "+call);
                } else if (task instanceof Runnable) {
                    Runnable runnableTask = (Runnable) task;
                    runnableTask.run();
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                System.out.println("Errorxecuting task");
           }



      }

    }

    public void stopThread() {
        isStopped = true;
    }
}
