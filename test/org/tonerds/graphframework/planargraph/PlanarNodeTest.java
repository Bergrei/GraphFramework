package org.tonerds.graphframework.planargraph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tonerds.utilities.ModuloArithmetcs;

class PlanarNodeTest {

	PlanarNode node = PlanarTestFactory.makeNode();
	
	List<PlanarNode> otherNodes = new ArrayList<>();
	List<PlanarEdge> edges = new ArrayList<>();
	List<PlanarFace> faces = new ArrayList<>();

	private final static int edgecount = 5;
	
	@BeforeEach
	void setupPartialGraph() {
		for (int i = 0; i < edgecount; i++) {
			otherNodes.add(PlanarTestFactory.makeNode());
			faces.add(PlanarTestFactory.makeFace());
		}
		for (int i = 0; i < edgecount; i++) {
			edges.add(PlanarTestFactory.makeEdge(otherNodes.get(i), faces.get(i), node, faces.get(ModuloArithmetcs.add(i, -1, edgecount))));
			node.addEdge(edges.get(i));
		}
	}
	
	@Test
	void nodeContainsEdgeTest() {
		for (int i = 0; i < edgecount; i++) {
			assertTrue(node.containsEdge(edges.get(i)));
		}
		assertFalse(node.containsEdge(null));
	}
	
	@Test
	void edgesGetRemovedTest() {
		for (int i = 0; i < edgecount; i++) {
			assertTrue(node.containsEdge(edges.get(i)));
			node.removeEdge(edges.get(i));
			assertFalse(node.containsEdge(edges.get(i)));
		}
	}

	@Test
	void nextEdgeReturnsInOrderTest() {
		for (int i = 0; i < edgecount; i++) {
			assertEquals(edges.get(ModuloArithmetcs.add(i, 1, edgecount)), 
					node.nextEdge(edges.get(i)));
		}
	}
	
	@Test
	void nextEdgeUnknownReturnsNullTest() {
		assertNull(node.nextEdge(null));
	}

	@Test
	void nextNodeReturnsInOrderTest() {
		for (int i = 0; i < edgecount; i++) {
			assertEquals(otherNodes.get(i), 
				node.nextNode(faces.get(i)));
		}
	}
	
	@Test
	void nextNodeUnknownReturnsNullTest() {
		assertNull(node.nextNode(PlanarTestFactory.makeFace()));
	}

}
