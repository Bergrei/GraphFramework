package org.tonerds.graphframework.planargraph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlanarDirectedDartTest {

	PlanarNode from = new DefaultPlanarNode();
	PlanarNode to = new DefaultPlanarNode();
	PlanarFace face = new DefaultPlanarFace();
	PlanarDirectedDart dart = new DefaultPlanarDirectedDart(from, to, face);
	
	@Test
	void containsFromTest() {
		assertTrue(dart.containsNode(from));
	}

	@Test
	void containsToTest() {
		assertTrue(dart.containsNode(to));
	}

	@Test
	void notContainsOtherNodeTest() {
		assertFalse(dart.containsNode(null));
	}

	@Test
	void nextNodeFromIsToTest() {
		assertEquals(to, dart.getNextNode(from));
	}

	@Test
	void nextNodeToIsNullTest() {
		assertNull(dart.getNextNode(to));
	}
	
	@Test
	void containsFaceTest() {
		assertTrue(dart.containsFace(face));
	}
	
	@Test
	void notContainsOtherFaceTest() {
		assertFalse(dart.containsFace(null));
	}

	@Test
	void getFaceIsFaceTest() {
		assertEquals(face, dart.getFace());
	}

	@Test
	void getFaceFromIsFaceTest() {
		assertEquals(face, dart.getFace(from));
	}

	@Test
	void getFaceOtherIsNullTest() {
		assertNull(dart.getFace(to));
	}

	@Test
	void hasSameNodesTrueTest() {
		assertTrue(dart.hasSameNodes(new DefaultPlanarDirectedDart(from, to, face)));
		assertTrue(dart.hasSameNodes(new DefaultPlanarDirectedDart(to, from, face)));
	}
	
	@Test
	void hasSameNodesFalseTest() {
		assertFalse(dart.hasSameNodes(new DefaultPlanarDirectedDart(from, null, face)));
		assertFalse(dart.hasSameNodes(new DefaultPlanarDirectedDart(null, from, face)));
	}
}
