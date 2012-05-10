/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Year2012.com.BCHS;

/**
 *
 * @author laxman
 */
public class SafetyHelper
{
	private static class ThreadTask implements Runnable
	{
		private SafetyHelper threadExe;
		
		public ThreadTask(SafetyHelper threadExe)
		{
			this.threadExe =  threadExe;
			
		}
		
		public void run()
		{
			threadExe.task();
		}
	}
        private static SafetyHelper instance = new SafetyHelper();
        
        public static SafetyHelper getInstance()
        {
            return instance;
        }
	
	Thread threadRun;
	boolean thread_keepalive;
	
	public SafetyHelper()
	{
		threadRun = new Thread(new ThreadTask(this), "Task");
		threadRun.setPriority(Thread.NORM_PRIORITY);
		threadRun.start();
		
		thread_keepalive = false;
		
	}
	private void task()
	{
		int i = 0;
		while (thread_keepalive)
		{
			i++;
			if (i <= 5)
				System.out.println("Hello");
			else
				release();
		}
	}
	
	public void release()
	{
		thread_keepalive = false;
	}
       
}
