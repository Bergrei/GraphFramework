package org.tonerds.graphframework.planargraph;

public interface NodeAction<Node extends PlanarNode, Edge extends PlanarEdge, Face extends PlanarFace> {
	void action(PlanarGraph<Node, Edge, Face> graph, Node node);
}
