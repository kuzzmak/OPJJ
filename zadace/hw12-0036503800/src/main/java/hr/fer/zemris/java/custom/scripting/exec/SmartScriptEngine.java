package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();

	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {

			Element startExpression = node.getStartExpression();
			Element endExpression = node.getEndExpression();
			Element stepExpression = node.getStepExpression();
			ElementVariable variable = node.getVariable();

			int start = Integer.parseInt(startExpression.asText());
			int end = Integer.parseInt(endExpression.asText());
			int step = Integer.parseInt(stepExpression.asText());
			String var = variable.asText();

			ValueWrapper vw = new ValueWrapper(start);
			multistack.push(node.getVariable().asText(), vw);

			while (multistack.peek(var).numCompare(end) < 0) {
				node.children.forEach(n -> n.accept(this));
				multistack.peek(var).add(step);
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {

			ObjectStack<Object> tempStack = new ObjectStack<>();

			Element[] elements = node.getElements();

			for (int i = 0; i < elements.length; i++) {

				Element element = elements[i];

				if (element instanceof ElementFunction) {

					String functionName = element.asText();

					if (functionName.equals("@setMimeType")) {
						executeSetMimeType(tempStack);

					} else if (functionName.equals("@decfmt")) {
						executeSetDecimalFormat(tempStack);
					
					} else if(functionName.equals("@sin")) {
						
						Object arg = tempStack.pop();
						double result;
						
						if(arg instanceof Integer) {
							result = Math.sin((int) arg);
						}else {
							result = Math.sin((double) arg);
						}
						tempStack.push(result);
					}

				} else if (element instanceof ElementString) {
					tempStack.push(element.asText());

				} else if (element instanceof ElementOperator) {

					String operator = element.asText();
					executeOperator(tempStack, operator);

				} else if (element instanceof ElementVariable) {

					String key = elements[i].asText();
					tempStack.push(multistack.peek(key).getValue());
				}
			}

			while(!tempStack.isEmpty()) {
				try {
					Object o = tempStack.pop();
					requestContext.write(String.valueOf(o));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			node.children.forEach(n -> n.accept(this));
		}
	};

	private void executeSetMimeType(ObjectStack<Object> tempStack) {

		String mimeType = String.valueOf(tempStack.pop());
		if (mimeType != null) {

			String newMimeType = mimeType.replaceAll("\"", "").replaceAll("\\s+", "");
			requestContext.setMimeType(newMimeType);
		}
	}

	private void executeSetDecimalFormat(ObjectStack<Object> tempStack) {

		Object format = tempStack.pop();
		String formatString = (String) format;
		Object number = tempStack.pop();
		
		DecimalFormat decimalFormat = new DecimalFormat(formatString.replaceAll("\"", ""));
		tempStack.push(decimalFormat.format(number));
	}

	private void executeOperator(ObjectStack<Object> tempStack, String operator) {

		Object o1 = tempStack.pop();
		Object o2 = tempStack.pop();

		ValueWrapper vw = new ValueWrapper(null);
		vw.add(o1);
		
		if (operator.equals("+")) {
			
			vw.add(o2);
			
		} else if (operator.equals("*")) {
			
			vw.multiply(o2);

		} else if (operator.equals("-")) {
			
			vw.subtract(o2);

		} else if (operator.equals("/")) {
			
			vw.divide(o2);
			
		} else
			throw new RuntimeException("Nepodržan operator: " + operator);

		tempStack.push(vw.getValue());
	}

	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	public void execute() {
		documentNode.accept(visitor);
	}

}
