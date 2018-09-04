package org.tonerds.graphframework.planargraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tonerds.graphframework.graph.Graph;
import org.tonerds.graphframework.planargraph.algorithm.BreadthFirstSearch;
import org.tonerds.utilities.ModuloArithmetcs;
import org.tonerds.utilities.Pair;

public class DefaultPlanarGraph<Node extends PlanarNode, Edge extends PlanarEdge, Face extends PlanarFace> implements PlanarGraph<Node, Edge, Face> {
	
	private PlanarGraphFactory<Node, Edge, Face> factory;
	
	private Set<Node> nodes = new HashSet<>();
	private Set<Edge> edges = new HashSet<>();
	private Set<Face> faces = new HashSet<>();

	private List<List<PlanarDirectedDart>> circles = new ArrayList<>();
	
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

	@Override
	public void addEdge(PlanarEdge edge) {
		Pair<PlanarNode, PlanarNode> edgenodes = edge.getNodes();
		int firstcircleid = findCircle(edgenodes.first);
		if (firstcircleid == -1) {
			firstcircleid = circles.size();
			circles.add(new ArrayList<>());
		}
		int secondcircleid = findCircle(edgenodes.second);
		if (secondcircleid == -1) {
			secondcircleid = circles.size();
			circles.add(new ArrayList<>());
		}
		
		if (firstcircleid != secondcircleid) {
			mergeCirclesByEdge(firstcircleid, secondcircleid, edge);
		}
		else {
			splitCircleByEdge(firstcircleid, edge);
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
		//return new HashSet<Face>(faces);
	}

	@Override
	public Collection<Face> getFacesWithoutOuterFace() {
		Collection<Face> retfaces = getFaces();
		retfaces.remove(outerface);
		return retfaces;
	}

	@Override
	public void forEachNode(NodeAction<Node, Edge, Face> action) {
		refreshFaces();
		for (Node node : nodes) {
			action.action(this, node);
		}
	}

	@Override
	public void forEachEdge(EdgeAction<Node, Edge, Face> action) {
		refreshfaces();
		for (Edge edge : edges) {
			action.action(this, edge);
		}
	}

	@Override
	public void forEachFace(FaceAction<Node, Edge, Face> action) {
		refreshFaces();
		for (Face face : faces) {
			action.action(this, face);
		}
	}

	
	@Override
	public void addNode(PlanarNode node) {
		nodes.add(node);
		//circles.add(new ArrayList<>());
	}

	@Override
	public boolean containsNode(PlanarNode node) {
		return nodes.contains(node);
	}

	@Override
	public boolean removeNode(PlanarNode node) {
		return nodes.remove(node);
	}



	private void mergeCirclesByEdge(int firstcircleid, int secondcircleid, PlanarEdge edge) {
		List<PlanarDirectedDart> firstcircle = circles.get(firstcircleid);
		List<PlanarDirectedDart> secondcircle = circles.get(secondcircleid);
		Pair<PlanarDirectedDart, PlanarDirectedDart> edgedarts = edge.getDarts();
		Pair<PlanarNode, PlanarNode> edgenodes = edge.getNodes();
		if (edgenodes.first != edgedarts.first.getBottom() && edgenodes.second != edgedarts.second.getBottom()) {
			edgedarts = new Pair<PlanarDirectedDart, PlanarDirectedDart>(edgedarts.second, edgedarts.first);
		}
		
		int firstnodeid = -1;
		int secondnodeid = -1;
		for (int i = 0; i < firstcircle.size(); i++) {
			if (firstcircle.get(i).getBottom() == edgedarts.first.getBottom()) {
				firstnodeid = i;
			}
			if (secondcircle.get(i).getBottom() == edgedarts.second.getBottom()) {
				secondnodeid = i;
			}
		}
		
		List<PlanarDirectedDart> newcircle = new ArrayList<>();
		int i = firstnodeid;
		do {
			newcircle.add(firstcircle.get(i));
			i = ModuloArithmetcs.add(i, 1, firstcircle.size());
		}
		while(i != ModuloArithmetcs.add(firstnodeid, -1, firstcircle.size()));
		newcircle.add(edgedarts.first);
		i = secondnodeid;
		do {
			newcircle.add(secondcircle.get(i));
			i = ModuloArithmetcs.add(i, 1, secondcircle.size());
		}
		while(i != ModuloArithmetcs.add(secondnodeid, -1, secondcircle.size()));
		newcircle.add(edgedarts.second);
		
		circles.set(firstcircleid, newcircle);
		circles.remove(secondcircleid);
	}

	private void splitCircleByEdge(int circleid, PlanarEdge edge) {
		List<PlanarDirectedDart> circle = circles.get(circleid);
		Pair<PlanarDirectedDart ,PlanarDirectedDart> edgedarts = edge.getDarts();
		
		int firstnodeid = -1;
		int secondnodeid = -1;
		for (int i = 0; i < circle.size(); i++) {
			if (circle.get(i).getBottom() == edgedarts.first.getBottom()) {
				firstnodeid = i;
			}
			if (circle.get(i).getBottom() == edgedarts.second.getBottom()) {
				secondnodeid = i;
			}
		}
		int newcircleid = circles.size();
		circles.add(new ArrayList<>());
		List<PlanarDirectedDart> newcircle = circles.get(newcircleid);
		for (int i = firstnodeid; i != secondnodeid; i = ModuloArithmetcs.add(i, 1, circle.size())) {
			newcircle.add(circle.get(i));
		}
		newcircle.add(edgedarts.second);
		circle.add(firstnodeid, edgedarts.first);
		for (PlanarDirectedDart dart : newcircle) {
			circle.remove(dart);
		}
	}

	private int findCircle(PlanarNode node) {
		for (int i = 0; i < circles.size(); i++) {
			List<PlanarDirectedDart> circle = circles.get(i);
			for (PlanarDirectedDart dart : circle) {
				if (dart.getBottom() == node) {
					return i;
				}
			}
		}
		return 0;
	}
	
}
