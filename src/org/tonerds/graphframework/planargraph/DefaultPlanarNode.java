package org.tonerds.graphframework.planargraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.tonerds.utilities.ModuloArithmetcs;

public class DefaultPlanarNode implements PlanarNode {

	private List<PlanarEdge> edges = new ArrayList<>();
	
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
	public PlanarEdge nextEdge(PlanarEdge edge) {
		int index = edges.indexOf(edge);
		if (index != -1) {
			return edges.get(ModuloArithmetcs.add(index, 1, edges.size()));
		}
		return null;
	}

	@Override
	public PlanarNode nextNode(PlanarFace face) {
		for (PlanarEdge edge : edges) {
			if (edge.getFace(this) == face) {
				return edge.getNextNode(this);
			}
		}
		return null;
	}

	@Override
	public Collection<PlanarEdge> getEdges() {
		return new ArrayList<>(edges);
	}

}
