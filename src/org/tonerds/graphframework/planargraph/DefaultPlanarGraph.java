package org.tonerds.graphframework.planargraph;

import java.util.HashSet;
import java.util.Set;

import org.tonerds.graphframework.graph.Graph;

public class DefaultPlanarGraph implements PlanarGraph {
	
	private PlanarGraphFactory factory;
	
	private Set<PlanarNode> nodes = new HashSet<>();
	private Set<PlanarEdge> edges = new HashSet<>();
	private Set<PlanarFace> faces = new HashSet<>();
	
	
	public DefaultPlanarGraph(PlanarGraphFactory factory) {
		this.factory = factory;
		faces.add(factory.makePlanarFace());
	}
	
	@Override
	public void addNodeToFace(PlanarFace face, PlanarNode node) {
		face.addNode(node);
		nodes.add(node);
	}

	@Override
	public void addEdgeToFaceBetweenNodes(PlanarFace face, PlanarNode fromnode, PlanarNode tonode, PlanarEdge edge) {
		face.addEdge(fromnode, tonode, edge);
		edges.add(edge);
		throw new RuntimeException("Set the edge parameters before adding to set");
	}

	@Override
	public void removeNode(PlanarNode node) {
		for (PlanarFace face : faces) {
			face.removeNode(node);
		}
	}

	@Override
	public void removeEdge(PlanarEdge edge) {
		for (PlanarFace face : faces) {
			face.removeEdge(edge);
		}
	}

	@Override
	public PlanarNode getNextNode(PlanarFace face, PlanarNode node) {
		return node.nextNode(face);
	}

	@Override
	public Graph extractGraph() {
		// TODO Auto-generated method stub
		return null;
	}

}
