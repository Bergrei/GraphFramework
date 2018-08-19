package org.tonerds.graphframework.planargraph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlanarFaceTest {

	PlanarNode firstNode = PlanarTestFactory.makeNode();
	PlanarNode secondNode = PlanarTestFactory.makeNode();
	PlanarNode thirdNode = PlanarTestFactory.makeNode();
	
	PlanarFace insideFace = PlanarTestFactory.makeFace();
	PlanarFace outsideFace = PlanarTestFactory.makeFace();
	
	PlanarEdge firstsecondEdge = PlanarTestFactory.makeEdge(firstNode, outsideFace, secondNode, insideFace);
	PlanarEdge secondthirdEdge = PlanarTestFactory.makeEdge(secondNode, outsideFace, thirdNode, insideFace);
	PlanarEdge thirdfirstEdge = PlanarTestFactory.makeEdge(thirdNode, outsideFace, firstNode, insideFace);
	@BeforeEach
	void setupLoop() {
		insideFace.addNode(firstNode);
		insideFace.addNode(secondNode);
		insideFace.addNode(thirdNode);
		
		insideFace.addEdge(firstsecondEdge);
		insideFace.addEdge(secondthirdEdge);
		insideFace.addEdge(thirdfirstEdge);

		outsideFace.addNode(firstNode);
		outsideFace.addNode(secondNode);
		outsideFace.addNode(thirdNode);
		
		outsideFace.addEdge(firstsecondEdge);
		outsideFace.addEdge(secondthirdEdge);
		outsideFace.addEdge(thirdfirstEdge);
	}
	
	@Test
	void faceContainsNodesTest() {
		assertTrue(insideFace.containsNode(firstNode));
		assertTrue(insideFace.containsNode(secondNode));
		assertTrue(insideFace.containsNode(thirdNode));
		assertFalse(insideFace.containsNode(PlanarTestFactory.makeNode()));
	}
	
	@Test
	void faceContainsEdgesTest() {
		assertTrue(insideFace.containsEdge(firstsecondEdge));
		assertTrue(insideFace.containsEdge(secondthirdEdge));
		assertTrue(insideFace.containsEdge(thirdfirstEdge));
	}
	
	@Test
	void nodesGetRemoved() {
		insideFace.removeNode(firstNode);
		insideFace.removeNode(secondNode);
		insideFace.removeNode(thirdNode);

		assertFalse(insideFace.containsNode(firstNode));
		assertFalse(insideFace.containsNode(secondNode));
		assertFalse(insideFace.containsNode(thirdNode));	
	}
	
	@Test
	void edgesGetRemoved() {
		insideFace.removeEdge(firstsecondEdge);
		insideFace.removeEdge(secondthirdEdge);
		insideFace.removeEdge(thirdfirstEdge);

		assertFalse(insideFace.containsEdge(firstsecondEdge));
		assertFalse(insideFace.containsEdge(secondthirdEdge));
		assertFalse(insideFace.containsEdge(thirdfirstEdge));	
	}
	
	@Test
	void nodesAreOnlyAddedOnceTest() {
		insideFace.addNode(firstNode);
		insideFace.addNode(firstNode);
		insideFace.removeNode(firstNode);
		assertFalse(insideFace.containsNode(firstNode));
	}
	
	@Test
	void edgesAreOnlyAddedOnceTest() {
		insideFace.addEdge(firstsecondEdge);
		insideFace.addEdge(firstsecondEdge);
		insideFace.removeEdge(firstsecondEdge);
		assertFalse(insideFace.containsEdge(firstsecondEdge));
	}
	
	@Test
	void getEdgeFromNodeTest() {
		assertEquals(firstsecondEdge, insideFace.getEdge(firstNode));
		assertEquals(secondthirdEdge, insideFace.getEdge(secondNode));
		assertEquals(thirdfirstEdge, insideFace.getEdge(thirdNode));
		assertNull(insideFace.getEdge(PlanarTestFactory.makeNode()));
	}

}
