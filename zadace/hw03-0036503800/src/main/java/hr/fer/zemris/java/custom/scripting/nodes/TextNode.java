package hr.fer.zemris.java.custom.scripting.nodes;

public class TextNode extends Node {

	String text;
	
	public TextNode(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
