package org.tonerds.graphframework.planargraph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlanarDirectedDartTest {

	PlanarNode from = new DefaultPlanarNode();
	PlanarNode to = new DefaultPlanarNode();
	PlanarFace face = new DefaultPlanarFace();
	PlanarDirectedDart dart = new DefaultPlanarDirectedDart(from, to, face);
	
	@Test
	void containsFrom() {
		assertTrue(dart.containsNode(from));
	}

	@Test
	void containsTo() {
		assertTrue(dart.containsNode(to));
	}

	@Test
	void notContainsOtherNode() {
		assertFalse(dart.containsNode(null));
	}

	@Test
	void nextNodeFromIsTo() {
		assertEquals(to, dart.getNextNode(from));
	}

	@Test
	void nextNodeToIsNull() {
		assertNull(dart.getNextNode(to));
	}
	
	@Test
	void containsFace() {
		assertTrue(dart.containsFace(face));
	}
	
	@Test
	void notContainsOtherFace() {
		assertFalse(dart.containsFace(null));
	}

	@Test
	void getFaceIsFace() {
		assertEquals(face, dart.getFace());
	}

	@Test
	void getFaceFromIsFace() {
		assertEquals(face, dart.getFace(from));
	}

	@Test
	void getFaceOtherIsNull() {
		assertNull(dart.getFace(to));
	}

	@Test
	void hasSameNodesTrue() {
		assertTrue(dart.hasSameNodes(new DefaultPlanarDirectedDart(from, to, face)));
		assertTrue(dart.hasSameNodes(new DefaultPlanarDirectedDart(to, from, face)));
	}
	
	@Test
	void hasSameNodesFalse() {
		assertFalse(dart.hasSameNodes(new DefaultPlanarDirectedDart(from, null, face)));
		assertFalse(dart.hasSameNodes(new DefaultPlanarDirectedDart(null, from, face)));
	}
}
