package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		Set<String> params = context.getParameterNames();
		
		StringBuilder sb = new StringBuilder(
				"<html>\r\n" + 
				"  <head>\r\n" + 
				"    <title>ispis parametara</title>\r\n" + 
				"  </head>\r\n" +
				"  <body>\r\n" + 
				"    <table border='1'>\r\n");
		
		for(String param: params) {
			sb.append("      <tr><td>")
			.append(param).append("</td><td>")
			.append(context.getParameter(param))
			.append("</td></tr>\r\n");
		}
		
		sb.append("    </table>\r\n" + "  </body>\r\n" + "</html>\r\n");
		context.setMimeType("text/html");
		context.write(sb.toString());
	}

}
