package org.tonerds.graphframework.planargraph;

public interface FaceAction<
			Node extends PlanarNode<Node, Edge, Face>, 
			Edge extends PlanarEdge<Node, Edge, Face>, 
			Face extends PlanarFace<Node, Edge, Face>
		> {
	void action(PlanarGraph<Node, Edge, Face> graph, Face face);
}
