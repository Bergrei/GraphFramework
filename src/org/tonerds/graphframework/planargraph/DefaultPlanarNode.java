package org.tonerds.graphframework.planargraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.tonerds.utilities.ModuloArithmetcs;

public class DefaultPlanarNode<
			Edge extends PlanarEdge<DefaultPlanarNode<Edge, Face>, Edge, Face>, 
			Face extends PlanarFace<DefaultPlanarNode<Edge, Face>, Edge, Face>
		> implements PlanarNode<DefaultPlanarNode<Edge, Face>, Edge, Face> {

	private List<Edge> edges = new ArrayList<>();
	protected PlanarGraphFactory<DefaultPlanarNode<Edge, Face>, Edge, Face> factory;
	
	DefaultPlanarNode(PlanarGraphFactory<DefaultPlanarNode<Edge, Face>, Edge, Face> factory) {
		this.factory = factory;
	}
	
	@Override
	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	@Override
	public boolean containsEdge(Edge edge) {
		return edges.contains(edge);
	}

	@Override
	public boolean removeEdge(Edge edge) {
		return edges.remove(edge);
	}

	@Override
	public Edge nextEdge(Edge edge) {
		int index = edges.indexOf(edge);
		if (index != -1) {
			return edges.get(ModuloArithmetcs.add(index, 1, edges.size()));
		}
		return null;
	}

	@Override
	public DefaultPlanarNode<Edge, Face> nextNode(Face face) {
		for (Edge edge : edges) {
			if (edge.getFace(this) == face) {
				return edge.getNextNode(this);
			}
		}
		return null;
	}

	@Override
	public Collection<Edge> getEdges() {
		return new ArrayList<>(edges);
	}

}
