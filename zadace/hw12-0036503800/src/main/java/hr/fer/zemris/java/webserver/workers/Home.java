package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Radnik za prikaz stranice {@code home}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		
		String colorValue = context.getPersistentParameter("bgcolor");
		
		if(colorValue != null) {
			context.setTemporaryParameter("background", colorValue);
		}else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}
