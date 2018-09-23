package org.tonerds.graphframework.planargraph;

public interface PlanarDirectedDart<
			Node extends PlanarNode<Node, Edge, Face>,
			Edge extends PlanarEdge<Node, Edge, Face>,
			Face extends PlanarFace<Node, Edge, Face>
		> {
	
	public boolean containsNode(Node node);
	
	public Node getNextNode(Node node);
	
	public boolean containsFace(Face face);
	
	public Face getFace();
	
	public Face getFace(Node from);
	
	public boolean hasSameNodes(PlanarDirectedDart<Node, Edge, Face> dart);
	
	public boolean equals(PlanarDirectedDart<Node, Edge, Face> dart);
	
	Node getTop();
	Node getBottom();
	Face getRight();
	
	boolean replaceFace(Face oldface, Face newface);
}
