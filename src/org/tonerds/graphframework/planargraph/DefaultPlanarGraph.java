package org.tonerds.graphframework.planargraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tonerds.graphframework.graph.Graph;
import org.tonerds.graphframework.planargraph.algorithm.BreadthFirstSearch;

public class DefaultPlanarGraph<Node extends PlanarNode, Edge extends PlanarEdge, Face extends PlanarFace> implements PlanarGraph<Node, Edge, Face> {
	
	private PlanarGraphFactory<Node, Edge, Face> factory;
	
	private Set<Node> nodes = new HashSet<>();
	private Set<Edge> edges = new HashSet<>();
	private Set<Face> faces = new HashSet<>();
	
	private Face outerface;
	
	public DefaultPlanarGraph(PlanarGraphFactory<Node, Edge, Face> factory) {
		this.factory = factory;
		this.outerface = factory.makeFace();
		faces.add(this.outerface);
		
	}
	
	@Override
	public Node addNodeToFace(PlanarFace face) {
		Node node = factory.makeNode();
		face.addNode(node);
		nodes.add(node);
		return node;
	}

	@Override
	public Edge addEdgeToFaceBetweenNodes(Face face, Node fromnode, Node tonode) {
		if (face.containsNode(fromnode) && face.containsNode(tonode)) {
			Edge edge;
			if (isSplitting(fromnode, tonode)) {
				edge = splitFace(face, fromnode, tonode);
			}
			else {
				edge = factory.makeEdge(fromnode, face, tonode, face);
				face.addEdge(edge);
			}
			edges.add(edge);
			return edge;
		}
		else {
			throw new RuntimeException("At least one of the nodes is not on the face");
		}
	}

	private boolean isSplitting(Node fromnode, Node tonode) {
		return BreadthFirstSearch.existsPath(fromnode, tonode);
	}

	private Edge splitFace(Face face, Node fromnode, Node tonode) {
		PlanarNode node = fromnode;
		Face newface = factory.makeFace(); 
		while (node != tonode) {
			PlanarEdge edge = face.getEdge(node);
			PlanarNode nextnode = edge.getNextNode(node);
			edge.replaceFace(nextnode, node, face, newface);
			node = nextnode;
		}
		Edge newedge = factory.makeEdge(fromnode, tonode, newface, face);
		return newedge;
	}

	@Override
	public void removeNode(Node node) {
		for (PlanarFace face : faces) {
			face.removeNode(node);
		}
	}

	@Override
	public void removeEdge(Edge edge) {
		for (Face face : faces) {
			face.removeEdge(edge);
		}
	}

	@Override
	public Node getNextNode(Face face, Node node) {
		return (Node) node.nextNode(face);
	}

	@Override
	public Graph extractGraph() {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public Face getOuterFace() {
		return outerface;
		}

	@Override
	public Collection<Face> getFaces() {
		return new HashSet<Face>(faces);
	}

	@Override
	public Collection<Face> getFacesWithoutOuterFace() {
		Collection<Face> retfaces = new HashSet<Face>(faces);
		retfaces.remove(outerface);
		return retfaces;
	}

	
}
