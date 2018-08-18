package org.tonerds.graphframework.planargraph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PlanarEdgeTest {

	static PlanarNode firstNode = makePlanarNode();
	static PlanarNode secondNode = makePlanarNode();
	static PlanarFace firstFace = makePlanarFace();
	static PlanarFace secondFace = makePlanarFace();
	
	public static Stream<Arguments> edgeProvider(){
		return Stream.of(
				Arguments.of(
					makeDartEdge(firstNode, firstFace, secondNode, secondFace),
					makeDartEdge(firstNode, firstFace, secondNode, firstFace)
					)
				);
	}

	@Test
	void throwOnOneNodeEdgeTest() {
		assertThrows(UnsupportedOperationException.class, () -> makeDartEdge(firstNode, firstFace, firstNode, secondFace));
	}

	@ParameterizedTest
	@MethodSource("edgeProvider")
	void edgeContainsNodeTest(PlanarEdge normalEdge, PlanarEdge oneFaceEdge) {
		assertTrue(normalEdge.containsNode(secondNode));
		assertTrue(normalEdge.containsNode(firstNode));
		assertTrue(oneFaceEdge.containsNode(firstNode));
		assertTrue(oneFaceEdge.containsNode(secondNode));
	}

	@ParameterizedTest
	@MethodSource("edgeProvider")
	void edgeNextNodeTest(PlanarEdge normalEdge, PlanarEdge oneFaceEdge) {
		assertEquals(firstNode, normalEdge.getNextNode(secondNode));
		assertEquals(secondNode, normalEdge.getNextNode(firstNode));
		assertEquals(firstNode, oneFaceEdge.getNextNode(secondNode));
		assertEquals(secondNode, oneFaceEdge.getNextNode(firstNode));
	}

	@ParameterizedTest
	@MethodSource("edgeProvider")
	void edgeContainsFacesTest(PlanarEdge normalEdge, PlanarEdge oneFaceEdge) {
		assertTrue(normalEdge.containsFace(firstFace));
		assertTrue(normalEdge.containsFace(secondFace));
		assertTrue(oneFaceEdge.containsFace(firstFace));
		assertFalse(oneFaceEdge.containsFace(secondFace));
	}

	@ParameterizedTest
	@MethodSource("edgeProvider")
	void edgeNextFaceFromFaceTest(PlanarEdge normalEdge, PlanarEdge oneFaceEdge) {
		assertEquals(firstFace, normalEdge.getNextFace(secondFace));
		assertEquals(secondFace, normalEdge.getNextFace(firstFace));
		assertEquals(firstFace, oneFaceEdge.getNextFace(firstFace));
		assertNull(oneFaceEdge.getNextFace(secondFace));
	}
	
	@ParameterizedTest
	@MethodSource("edgeProvider")
	void edgeFaceFromNodesTest(PlanarEdge normalEdge, PlanarEdge oneFaceEdge) {
		assertEquals(secondFace, normalEdge.getFace(firstNode));
		assertEquals(firstFace, normalEdge.getFace(secondNode));
		assertEquals(firstFace, oneFaceEdge.getFace(firstNode));
		assertEquals(firstFace, oneFaceEdge.getFace(secondNode));
	}

	private static PlanarDartEdge makeDartEdge(PlanarNode top, PlanarFace right, PlanarNode bottom, PlanarFace left) {
		return new PlanarDartEdge(top, right, bottom, left);
	}
	
	private static PlanarFace makePlanarFace() {
		return new DefaultPlanarFace();
	}
	
	private static PlanarNode makePlanarNode() {
		return new DefaultPlanarNode();
	}
}
