package org.tonerds.graphframework.planargraph;

public interface PlanarGraphFactory<Node extends PlanarNode, Edge extends PlanarEdge, Face extends PlanarFace> {
	public Node makeNode();
	public Edge makeEdge(Node from, Node to);
	public Edge makeEdge(Node top, Face right, Node bottom, Face left);
	public default Edge makeEdge(Node to, Node from, Face right, Face left) {
		return makeEdge(to, right, from, left);
	}
	public Face makeFace();
}
