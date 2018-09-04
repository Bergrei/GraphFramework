package org.tonerds.graphframework.planargraph;

public interface FaceAction<Node extends PlanarNode, Edge extends PlanarEdge, Face extends PlanarFace> {
	void action(PlanarGraph<Node, Edge, Face> graph, Face face);
}
