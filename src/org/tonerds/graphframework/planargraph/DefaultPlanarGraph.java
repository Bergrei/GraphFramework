package org.tonerds.graphframework.planargraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tonerds.graphframework.graph.Graph;
import org.tonerds.utilities.Pair;

public class DefaultPlanarGraph<
			Node extends PlanarNode<Node, Edge, Face>, 
			Edge extends PlanarEdge<Node, Edge, Face>, 
			Face extends PlanarFace<Node, Edge, Face>
		> implements PlanarGraph<Node, Edge, Face> {
	
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
	public Node addNodeToFace(Face face) {
		Node node = factory.makeNode();
		face.addNode(node);
		nodes.add(node);
		return node;
	}

	@Override
	public Edge addEdgeToFaceBetweenNodes(Face face, Node fromnode, Node tonode) {
		Edge edge = factory.makeEdge(fromnode, tonode);
		Face newface = (Face) face.addEdge(edge);
		if (newface != null)
			faces.add(newface);
		/*
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
		}*/
		return edge;
	}
	@Override
	public void removeNode(Node node) {
		for (Edge edge : node.getEdges()) {
			removeEdge(edge);
		}
		for (Face face: faces) {
			face.removeNode(node);
		}
		nodes.remove(node);
	}

	@Override
	public void removeEdge(Edge edge) {
		Pair<PlanarDirectedDart<Node, Edge, Face>, PlanarDirectedDart<Node, Edge, Face>> darts = edge.getDarts();
		Face first = darts.first.getFace();
		Face second = darts.second.getFace();
		if (first == second) {
			first.removeEdge(edge);
		}
		else {
			if (second == outerface) {
				second.mergeByEdgeWithFace(edge, first);
				faces.remove(first);
			}
			else {
				first.mergeByEdgeWithFace(edge, second);
				faces.remove(second);
			}
		}
		edges.remove(edge);
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
		Collection<Face> retfaces = getFaces();
		retfaces.remove(outerface);
		return retfaces;
	}

	@Override
	public Graph extractGraph() {
		throw new UnsupportedOperationException("Not implemented yet!");
	}


	@Override
	public void forEachNode(NodeAction<Node, Edge, Face> action) {
		for (Node node : nodes) {
			action.action(this, node);
		}
	}

	@Override
	public void forEachEdge(EdgeAction<Node, Edge, Face> action) {
		for (Edge edge : edges) {
			action.action(this, edge);
		}
	}

	@Override
	public void forEachFace(FaceAction<Node, Edge, Face> action) {
		for (Face face : faces) {
			action.action(this, face);
		}
	}
	
	@Override
	public void forOneNode(NodeAction<Node, Edge, Face> action) {
		if (!nodes.isEmpty()) { 
			Node node = nodes.iterator().next();
			action.action(this, node);
		}
	}
	
	@Override
	public void forOneEdge(EdgeAction<Node, Edge, Face> action) {
		if (!edges.isEmpty()) {
			Edge edge = edges.iterator().next();
			action.action(this, edge);
		}
	}
	
	@Override
	public void forOneFace(FaceAction<Node, Edge, Face> action) {
		if (!faces.isEmpty()) {
			Face face = faces.iterator().next(); 
			action.action(this, face);
		}
	}
	
}
