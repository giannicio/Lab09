package it.polito.tdp.borders.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;

public class EdgeTraversedGraphListener implements TraversalListener<Country, DefaultEdge> {
	Graph<Country, DefaultEdge> grafo;
	Map<Country, Country> backVisit;
	
	public EdgeTraversedGraphListener(Graph<Country, DefaultEdge> grafo, Map<Country, Country> backVisit) {
		super();
		this.grafo = grafo;
		this.backVisit = backVisit;
	}

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> ev) {
		Country sourceVertex = grafo.getEdgeSource(ev.getEdge());
		Country targetVertex = grafo.getEdgeTarget(ev.getEdge());
		
		if(!backVisit.containsKey(targetVertex) && backVisit.containsKey(sourceVertex)) {
			backVisit.put(targetVertex, sourceVertex);
		} else if(!backVisit.containsKey(sourceVertex) && backVisit.containsKey(targetVertex)) {
			backVisit.put(sourceVertex, targetVertex);
		}
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Country> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<Country> arg0) {
		// TODO Auto-generated method stub
		
	}

}
