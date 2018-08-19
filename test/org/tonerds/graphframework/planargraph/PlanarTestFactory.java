package org.tonerds.graphframework.planargraph;

final class PlanarTestFactory {
	static PlanarNode makeNode() {
		return new DefaultPlanarNode();
	}
	
	static PlanarFace makeFace() {
		return new DefaultPlanarFace();
	}
	
	static PlanarEdge makeEdge(PlanarNode top, PlanarFace right, PlanarNode bottom, PlanarFace left) {
		return new PlanarDartEdge(top, right, bottom, left);
	}
	
	static PlanarDirectedDart makeDart(PlanarNode from, PlanarNode to, PlanarFace face) {
		return new DefaultPlanarDirectedDart(from, to, face);
	}
}
