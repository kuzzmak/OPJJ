package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Sučelje koje omogućava obilazak elementa pojedinog {@code node} objekta.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface INodeVisitor {

	/**
	 * Obrada tekstualnog {@code node}-a.
	 * 
	 * @param node {@code node} koji se obrađuje
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Obrada {@code node}-a koji predstavlja for petlju.
	 * 
	 * @param node {@code node} koji se obrađuje
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Obrada {@code echo node}-a.
	 * 
	 * @param node {@code node} koji se obrađuje
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Obrada {@code node}-a koji predstavlja parsirani dokument 
	 * u manje cjeline, odnosno {@code node}-ove.
	 * 
	 * @param node {@code node} koji se obrađuje
	 */
	public void visitDocumentNode(DocumentNode node);
}
