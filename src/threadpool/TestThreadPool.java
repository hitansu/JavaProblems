package threadpool;

import java.util.concurrent.Callable;

public class TestThreadPool {

    public static void main(String[] args) {

        try{
            ThreadPool threadPool= new ThreadPool();
            threadPool.execute();

            threadPool.enqueueTask(new Task(1));
            threadPool.enqueueTask(new Task(2));
            threadPool.enqueueTask(new Task(3));
            threadPool.enqueueTask(new Task(4));
            threadPool.enqueueTask(new Task(5));
            threadPool.enqueueTask(new Task(6));
            threadPool.enqueueTask(new Task(7));
            threadPool.enqueueTask(new Task(8));
            threadPool.enqueueTask(new Task(9));
            threadPool.enqueueTask(new Task(10));
            threadPool.enqueueTask(new Task1(11));




        }
        catch(InterruptedException e) {
            e.printStackTrace();

        }

    }

    static class Task implements Runnable{

       int task_no;

       public Task(int task_no) {
           this.task_no= task_no;
       }

        @Override
        public void run() {
            System.out.println("Taskask_no: "+task_no+" is running ");
            try {
            	if(task_no%2== 0) {
            		Thread.sleep(10000);
            	} else {
            		Thread.sleep(1000);
            	}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
    
    static class Task1 implements Callable<String>{

        int task_no;

        public Task1(int task_no) {
            this.task_no= task_no;
        }

		@Override
		public String call() throws Exception {
            System.out.println("callable task");
            return "hello";
        }


     }
}
