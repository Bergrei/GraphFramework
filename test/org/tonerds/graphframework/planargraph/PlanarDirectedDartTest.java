package org.tonerds.graphframework.planargraph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlanarDirectedDartTest {

	PlanarNode from = PlanarTestFactory.makeNode();
	PlanarNode to = PlanarTestFactory.makeNode();
	PlanarFace face = PlanarTestFactory.makeFace();
	PlanarDirectedDart dart = PlanarTestFactory.makeDart(from, to, face);
	
	@Test
	void sameNodeThrowsExceptionTest() {
		assertThrows(UnsupportedOperationException.class, 
				() -> PlanarTestFactory.makeDart(from, from, face));
	}
	
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
		assertTrue(dart.hasSameNodes(
				PlanarTestFactory.makeDart(from, to, face)));
		assertTrue(dart.hasSameNodes(
				PlanarTestFactory.makeDart(to, from, face)));
	}
	
	@Test
	void hasSameNodesFalseTest() {
		assertFalse(dart.hasSameNodes(new DefaultPlanarDirectedDart(from, null, face)));
		assertFalse(dart.hasSameNodes(new DefaultPlanarDirectedDart(null, from, face)));
		assertFalse(dart.hasSameNodes(
				PlanarTestFactory.makeDart(new DefaultPlanarNode(), new DefaultPlanarNode(), face)));
	}
}
