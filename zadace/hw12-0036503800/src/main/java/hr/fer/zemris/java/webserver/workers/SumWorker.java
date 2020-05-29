package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		int a = 1;
		int b = 2;
		
		try {
			a = Integer.parseInt(context.getParameter("a"));
			b = Integer.parseInt(context.getParameter("b"));
			
		}catch(NumberFormatException e) {
		}
		
		int sum = a + b;
		context.setTemporaryParameter("zbroj", String.valueOf(sum));
		context.setTemporaryParameter("varA", String.valueOf(a));
		context.setTemporaryParameter("varB", String.valueOf(b));
		
		String im1 = "/images/image1.jpgq";
		String im2 = "/images/image2.jpg";
		
		String imageName = sum % 2 == 0 ? im1 : im2;
		context.setTemporaryParameter("imgName", imageName);
		
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
