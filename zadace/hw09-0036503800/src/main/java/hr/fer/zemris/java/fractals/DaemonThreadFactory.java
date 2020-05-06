package hr.fer.zemris.java.fractals;

import java.util.concurrent.ThreadFactory;

/**
 * Thread factory koji opisuje daemon dretve.
 * 
 * @author Antonio Kuzminski
 *
 */
public class DaemonThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}

}
