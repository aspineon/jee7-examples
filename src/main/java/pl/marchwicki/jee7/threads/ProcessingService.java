package pl.marchwicki.jee7.threads;

import java.util.concurrent.ExecutorService;

import javax.inject.Named;

@Named
public class ProcessingService {

	public void doMuchStuff(ExecutorService executor) {
		// All threads created will be managed

		for (int i = 0; i < 50; i++) {
			Runnable worker = new WorkerThread("" + i);
			executor.execute(worker);
		}
		executor.shutdown();
	}
}
