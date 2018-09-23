package org.tonerds.graphframework.planargraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tonerds.utilities.ModuloArithmetcs;
import org.tonerds.utilities.Pair;

public class DefaultPlanarFace<
			Node extends PlanarNode<Node, Edge, DefaultPlanarFace<Node, Edge>>, 
			Edge extends PlanarEdge<Node, Edge, DefaultPlanarFace<Node, Edge>>
		> implements PlanarFace<Node, Edge, DefaultPlanarFace<Node, Edge>> {

	private Set<Node> nodes = new HashSet<>();
	//hard to maintain
	protected List<List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>>> circles = new ArrayList<>();
	

	private PlanarGraphFactory<Node, Edge, DefaultPlanarFace<Node, Edge>> factory;
	
	DefaultPlanarFace(PlanarGraphFactory<Node, Edge, DefaultPlanarFace<Node, Edge>> factory) {
		this.factory = factory;
	}
	
	@Override
	public void addNode(Node node) {
		nodes.add(node);
		//circles.add(new ArrayList<>());
	}

	@Override
	public boolean containsNode(Node node) {
		return nodes.contains(node);
	}

	@Override
	public boolean removeNode(Node node) {
		return nodes.remove(node);
	}


	@Override
	public DefaultPlanarFace<Node, Edge> addEdge(Edge edge) {
		Pair<Node, Node> edgenodes = edge.getNodes();
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
			return null;
		}
		else {
			return splitCircleByEdge(firstcircleid, edge);
		}
	}
	

	private void mergeCirclesByEdge(int firstcircleid, int secondcircleid, Edge edge) {
		List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> firstcircle = circles.get(firstcircleid);
		List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> secondcircle = circles.get(secondcircleid);
		Pair<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>, PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> edgedarts = edge.getDarts();
		Pair<Node, Node> edgenodes = edge.getNodes();
		if (edgenodes.first != edgedarts.first.getBottom() && edgenodes.second != edgedarts.second.getBottom()) {
			edgedarts = new Pair<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>, PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>>(edgedarts.second, edgedarts.first);
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
		
		List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> newcircle = new ArrayList<>();
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

	private DefaultPlanarFace<Node, Edge> splitCircleByEdge(int circleid, Edge edge) {
		List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> circle = circles.get(circleid);
		Pair<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>> ,PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> edgedarts = edge.getDarts();
		//add face
		DefaultPlanarFace<Node, Edge> newFace = factory.makeFace();
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
		List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> newcircle = new ArrayList<>();
		for (int i = firstnodeid; i != secondnodeid; i = ModuloArithmetcs.add(i, 1, circle.size())) {
			newcircle.add(circle.get(i));
		}
		newcircle.add(edgedarts.second);
		circle.add(firstnodeid, edgedarts.first);
		for (PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>> dart : newcircle) {
			circle.remove(dart);
		}
		newFace.addCircle(newcircle);
		return newFace;
	}

	@Override
	public boolean containsDart(PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>> dart) {
		for (List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> circle : circles) {
			if (circle.contains(dart)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean mergeByEdgeWithFace(Edge edge, DefaultPlanarFace<Node, Edge> face) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean removeEdge(Edge edge) {
		if (containsEdge(edge)) {
			int circleid = -1;
			for (int i = 0; i < circles.size(); i++) {
				if (circles.get(i).contains(edge.getDarts().first)) {
					circleid = i;
					break;
				}
			}
			splitCircleByEdge(circleid, edge);
			return true;
		}
		return false;
	}
	
	@Override
	public PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>> getNextDart(PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>> after) {
		for (List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> circle : circles) {
			if (circle.contains(after)) {
				return circle.get(ModuloArithmetcs.add(circle.indexOf(after), 1, circle.size()));
			}
		}
		return null;
	}
	
	@Override
	public void addCircle(List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> circle) {
		circles.add(circle);
	}
	
	private void removeCircle(List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> circle) {
		circles.remove(circle);
	}
	
	private int findCircle(Node node) {
		for (int i = 0; i < circles.size(); i++) {
			List<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> circle = circles.get(i);
			for (PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>> dart : circle) {
				if (dart.getBottom() == node) {
					return i;
				}
			}
		}
		return 0;
	}

	
	public boolean containsEdge(Edge edge) {
		Pair<PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>> ,PlanarDirectedDart<Node, Edge, DefaultPlanarFace<Node, Edge>>> darts = edge.getDarts();
		return containsDart(darts.first) && containsDart(darts.second);
	}
}
