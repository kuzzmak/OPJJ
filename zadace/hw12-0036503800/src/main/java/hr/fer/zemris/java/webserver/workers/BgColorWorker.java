package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Implementacija radnika za promjenu pozadinske boje stranice.
 * 
 * @author Antonio Kuzminski
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		String color = context.getParameter("bgcolor");
		
		context.setPersistentParameter("bgcolor", color);
		
		context.getDispatcher().dispatchRequest("/home");
	}

}
