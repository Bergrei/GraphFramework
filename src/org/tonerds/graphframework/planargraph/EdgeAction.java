package org.tonerds.graphframework.planargraph;

public interface EdgeAction<
			Node extends PlanarNode<Node, Edge, Face>, 
			Edge extends PlanarEdge<Node, Edge, Face>, 
			Face extends PlanarFace<Node, Edge, Face>
		> {
	void action(PlanarGraph<Node, Edge, Face> graph, Edge edge);
}
