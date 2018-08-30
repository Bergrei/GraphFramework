package org.tonerds.graphframework.planargraph.algorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tonerds.graphframework.planargraph.PlanarEdge;
import org.tonerds.graphframework.planargraph.PlanarNode;

public final class BreadthFirstSearch {
	
	public static Collection<PlanarNode> getReachableNodes(PlanarNode node){
		Set<PlanarNode> seenNodes = new HashSet<>();
		Set<PlanarNode> visitedNodes = new HashSet<>();
		seenNodes.add(node);
		while (!seenNodes.isEmpty()) {
			PlanarNode activenode = seenNodes.iterator().next();
			for (PlanarEdge edge : activenode.getEdges()) {
				PlanarNode nextnode = edge.getNextNode(activenode);
				if (!visitedNodes.contains(nextnode)) {
					seenNodes.add(nextnode);
				}
			}
			seenNodes.remove(activenode);
		}
		return visitedNodes;
	}
	
	public static boolean existsPath(PlanarNode from, PlanarNode to) {
		return getReachableNodes(from).contains(to);
	}
}
