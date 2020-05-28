package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
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

	/**
	 * Privatni razred implementacije node posjetitelja baziran na 
	 * oblikovnom obrazcu {@code Visitor} i {@code Composite}.
	 * 
	 */
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

			while (multistack.peek(var).numCompare(end) <= 0) {
				node.children.forEach(n -> n.accept(this));
				multistack.peek(var).add(step);
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {

			Stack<Object> tempStack = new Stack<>();

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
						
					}else if(functionName.equals("@dup")) {
						executeDup(tempStack);
					
					}else if(functionName.equals("@swap")) {
						executeSwap(tempStack);
					
					}else if(functionName.equals("@paramGet")) {
						exectuteParamGet(tempStack);
					
					}else if(functionName.equals("@pparamGet")) {
						executePParamGet(tempStack);
						
					}else if(functionName.equals("@pparamSet")) {
						executePParamSet(tempStack);
						
					}else if(functionName.equals("@pparamDel")) {
						executePParamDel(tempStack);
						
					}else if(functionName.equals("@tparamGet")) {
						executeTParamGet(tempStack);
						
					}else if(functionName.equals("@tparamSet")) {
						executeTParamSet(tempStack);
						
					}else if(functionName.equals("@tparamDel")) {
						executeTParamDel(tempStack);
						
					}

				} else if (element instanceof ElementString || 
						element instanceof ElementConstantDouble || 
						element instanceof ElementConstantInteger) {
					tempStack.push(element.asText().contains("\"") ? element.asText().replaceAll("\"", "") : element.asText());

				} else if (element instanceof ElementOperator) {

					String operator = element.asText();
					executeOperator(tempStack, operator);

				} else if (element instanceof ElementVariable) {

					String key = elements[i].asText();
					tempStack.push(multistack.peek(key).getValue());
				}
			}

				
			List<String> stackContent = tempStack.stream().map(o -> String.valueOf(o)).collect(Collectors.toList());
			
			stackContent.forEach(t -> {
				try {
					requestContext.write(t);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			node.children.forEach(n -> n.accept(this));
		}
	};

	private void executeSetMimeType(Stack<Object> tempStack) {

		String mimeType = String.valueOf(tempStack.pop());
		if (mimeType != null) {

			String newMimeType = mimeType.replaceAll("\"", "").replaceAll("\\s+", "");
			requestContext.setMimeType(newMimeType);
		}
	}

	private void executeSetDecimalFormat(Stack<Object> tempStack) {

		Object format = tempStack.pop();
		String formatString = (String) format;
		Object number = tempStack.pop();

		DecimalFormat decimalFormat = new DecimalFormat(formatString.replaceAll("\"", ""));
		tempStack.push(decimalFormat.format(number));
	}

	private void executeDup(Stack<Object> tempStack) {
		Object o = tempStack.pop();
		tempStack.push(o);
		tempStack.push(o);
	}

	private void executeSwap(Stack<Object> tempStack) {
		Object o1 = tempStack.pop();
		Object o2 = tempStack.pop();
		tempStack.push(o1);
		tempStack.push(o2);
	}

	private void exectuteParamGet(Stack<Object> tempStack) {
		Object defaultValue = tempStack.pop();
		Object name = tempStack.pop();

		String value = requestContext.getParameter(String.valueOf(name));
		tempStack.push(value == null ? defaultValue : value);
	}

	/**
	 * Metoda za izvođenje osnovnih aritmetičkih operacija.
	 * 
	 * @param tempStack trenutni stog nekog taga
	 * @param operator  operator koji se treba izvršiti
	 */
	private void executeOperator(Stack<Object> tempStack, String operator) {

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

	private void executePParamGet(Stack<Object> tempStack) {

		Object defaultValue = tempStack.pop();
		Object name = tempStack.pop();

		String value = requestContext.getPersistentParameter(String.valueOf(name));
		tempStack.push(value == null ? defaultValue : value);
	}

	private void executePParamSet(Stack<Object> tempStack) {

		Object name = tempStack.pop();
		Object value = tempStack.pop();
		requestContext.setPersistentParameter(String.valueOf(name), String.valueOf(value));
	}

	private void executePParamDel(Stack<Object> tempStack) {
		Object name = tempStack.pop();
		requestContext.setPersistentParameter(String.valueOf(name), null);
	}

	private void executeTParamGet(Stack<Object> tempStack) {
		Object defaultValue = tempStack.pop();
		Object name = tempStack.pop();

		String value = requestContext.getTemporaryParameter(String.valueOf(name));
		tempStack.push(value == null ? defaultValue : value);
	}

	private void executeTParamSet(Stack<Object> tempStack) {

		Object name = tempStack.pop();
		Object value = tempStack.pop();
		requestContext.setTemporaryParameter(String.valueOf(name), String.valueOf(value));
	}
	
	private void executeTParamDel(Stack<Object> tempStack) {
		Object name = tempStack.pop();
		requestContext.setTemporaryParameter(String.valueOf(name), null);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param documentNode   oblik dokumenta koji je parsiran u odgovarajuće
	 *                       node-ove
	 * @param requestContext referenca konteksta webservera
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	public void execute() {
		documentNode.accept(visitor);
	}

}
