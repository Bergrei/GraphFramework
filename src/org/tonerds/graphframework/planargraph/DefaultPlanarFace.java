package org.tonerds.graphframework.planargraph;

import java.util.HashSet;
import java.util.Set;

public class DefaultPlanarFace implements PlanarFace {

	private Set<PlanarNode> nodes = new HashSet<>();	
	private Set<PlanarEdge> edges = new HashSet<>();	
	
	@Override
	public void addNode(PlanarNode node) {
		nodes.add(node);
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
		edges.add(edge);
	}

	@Override
	public boolean containsEdge(PlanarEdge edge) {
		return edges.contains(edge);
	}

	@Override
	public boolean removeEdge(PlanarEdge edge) {
		return edges.remove(edge);
	}

	@Override
	public PlanarEdge getEdge(PlanarNode from) {
		for (PlanarEdge edge : edges) {
			if (edge.getFace(from) == this) {
				return edge;
			}
		}
		return null;
	}

}
