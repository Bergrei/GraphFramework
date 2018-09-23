package org.tonerds.graphframework.planargraph;

public class DefaultPlanarDirectedDart<
			Node extends PlanarNode<Node, Edge, Face>,
			Edge extends PlanarEdge<Node, Edge, Face>,
			Face extends PlanarFace<Node, Edge, Face>
		> implements PlanarDirectedDart<Node, Edge, Face> {

	private Node top;
	private Node bottom;
	private Face right;
	
	DefaultPlanarDirectedDart(Node bottom, Node top, Face right) throws UnsupportedOperationException {
		if (top == bottom) {
			throw new UnsupportedOperationException("Loop edges are not supported");
		}
		this.top = top;
		this.bottom = bottom;
		this.right = right;
	}
	
	@Override
	public boolean containsNode(Node node) {
		return top == node || bottom == node;
	}

	@Override
	public Node getNextNode(Node node) {
		if (node == bottom)
			return top;
		return null;
	}

	@Override
	public boolean containsFace(Face face) {
		return right == face;
	}

	@Override
	public Face getFace() {
		return right;
	}

	@Override
	public Face getFace(Node from) {
		if (from == bottom)
			return right;
		return null;
	}

	@Override
	public boolean hasSameNodes(PlanarDirectedDart<Node, Edge, Face> dart) {
		return dart.containsNode(top) && dart.containsNode(bottom);
	}

	@Override
	public boolean equals(PlanarDirectedDart<Node, Edge, Face> dart) {
		return (top == dart.getTop() && bottom == dart.getBottom() && right == dart.getRight());
	}

	@Override
	public Node getTop() {
		return top;
	}

	@Override
	public Node getBottom() {
		return bottom;
	}

	@Override
	public Face getRight() {
		return right;
	}

	@Override
	public boolean replaceFace(Face oldface, Face newface) {
		if (right == oldface) {
			right = newface;
			return true;
		}
		return false;
	}
}
