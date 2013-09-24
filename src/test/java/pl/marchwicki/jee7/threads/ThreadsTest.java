package pl.marchwicki.jee7.threads;

import static org.fest.assertions.Assertions.*;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ThreadsTest {

	@Inject
	ProcessingService service;
	
	@Inject
	ThreadManager threads;
	
	@Deployment
	public static WebArchive deploy() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addClasses(ThreadManager.class, ProcessingService.class, WorkerThread.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void run() {
		ExecutorService executor = threads.getThreadManager();
		
		service.doMuchStuff(executor);
		
		while (!executor.isTerminated()) {
		}
		
		assertThat(executor.isTerminated()).isEqualTo(true);
		System.out.println("Finished all threads");
	}
	
}
