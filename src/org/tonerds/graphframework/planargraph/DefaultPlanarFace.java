package org.tonerds.graphframework.planargraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.tonerds.utilities.ModuloArithmetcs;
import org.tonerds.utilities.Pair;

public class DefaultPlanarFace implements PlanarFace {

	private Set<PlanarNode> nodes = new HashSet<>();
	//hard to maintain
	private List<List<PlanarDirectedDart>> circles = new ArrayList<>();
	
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

	@Override
	public boolean containsDart(PlanarDirectedDart dart) {
		for (List<PlanarDirectedDart> circle : circles) {
			if (circle.contains(dart)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsEdge(PlanarEdge edge) {
		return containsDart(edge.getDarts().first) && containsDart(edge.getDarts().second);
	}

	@Override
	public PlanarDirectedDart getNextDart(PlanarDirectedDart after) {
		for (List<PlanarDirectedDart> circle : circles) {
			if (circle.contains(after)) {
				return circle.get(ModuloArithmetcs.add(circle.indexOf(after), 1, circle.size()));
			}
		}
		return null;
	}


}
