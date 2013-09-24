package pl.marchwicki.jee7.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.inject.Named;

@Named
public class ThreadManager {

	@Resource
	ManagedThreadFactory mtf;

	public ExecutorService getThreadManager() {
		return new ThreadPoolExecutor(5,10, 10,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), mtf);
	}
	
}
