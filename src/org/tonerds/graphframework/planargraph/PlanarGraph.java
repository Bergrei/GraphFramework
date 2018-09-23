package org.tonerds.graphframework.planargraph;

import java.util.Collection;

import org.tonerds.graphframework.graph.Graph;


public interface PlanarGraph<
			Node extends PlanarNode<Node, Edge, Face>, 
			Edge extends PlanarEdge<Node, Edge, Face>, 
			Face extends PlanarFace<Node, Edge, Face>
		> {
	
	public Node addNodeToFace(Face toface);
	public Edge addEdgeToFaceBetweenNodes(Face toface, Node fromnode, Node tonode);
	public void removeNode(Node node);
	public void removeEdge(Edge edge);
	
	public Face getOuterFace();
	public Collection<Face> getFaces();
	public Collection<Face> getFacesWithoutOuterFace();
	
	public Graph extractGraph();
	
	public void forEachNode(NodeAction<Node, Edge, Face> action);
	public void forEachEdge(EdgeAction<Node, Edge, Face> action);
	public void forEachFace(FaceAction<Node, Edge, Face> action);
	
	public void forOneNode(NodeAction<Node, Edge, Face> action);
	public void forOneEdge(EdgeAction<Node, Edge, Face> action);
	public void forOneFace(FaceAction<Node, Edge, Face> action);
}
