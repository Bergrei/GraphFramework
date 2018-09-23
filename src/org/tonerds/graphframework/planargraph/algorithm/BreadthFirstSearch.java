package org.tonerds.graphframework.planargraph.algorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tonerds.graphframework.planargraph.PlanarEdge;
import org.tonerds.graphframework.planargraph.PlanarFace;
import org.tonerds.graphframework.planargraph.PlanarNode;

public final class BreadthFirstSearch {
	
	public static <
				Node extends PlanarNode<Node, Edge, Face>, 
				Edge extends PlanarEdge<Node, Edge, Face>, 
				Face extends PlanarFace<Node, Edge, Face>
			> Collection<Node> getReachableNodes(Node node){
		Set<Node> seenNodes = new HashSet<>();
		Set<Node> visitedNodes = new HashSet<>();
		seenNodes.add(node);
		while (!seenNodes.isEmpty()) {
			Node activenode = seenNodes.iterator().next();
			for (Edge edge : activenode.getEdges()) {
				Node nextnode = edge.getNextNode(activenode);
				if (!visitedNodes.contains(nextnode)) {
					seenNodes.add(nextnode);
				}
			}
			seenNodes.remove(activenode);
		}
		return visitedNodes;
	}
	
	public static<
				Node extends PlanarNode<Node, Edge, Face>, 
				Edge extends PlanarEdge<Node, Edge, Face>, 
				Face extends PlanarFace<Node, Edge, Face>
			> boolean existsPath(Node from, Node to) {
		return getReachableNodes(from).contains(to);
	}
}
