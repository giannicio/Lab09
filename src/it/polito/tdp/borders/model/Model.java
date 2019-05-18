package it.polito.tdp.borders.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Graph<Country, DefaultEdge> grafo;
	private HashMap<Integer, Country> idMap;
	Map<Country, Country> backVisit;
	BordersDAO dao;
	List<Country> connectedCountryList;
	
	public Model() {
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		idMap = new HashMap<Integer, Country>(); 
		dao = new BordersDAO();
		popolaIdMap(idMap);
		
	}
	public List<Country> getAllCountries(){
		return dao.loadAllCountries(idMap);
	}
	public void popolaIdMap(HashMap<Integer, Country> idMap) {
		dao.loadAllCountries(idMap);
	}
	
	public List<Country> ottieniCountryList(int anno) {
		List<Country> connectedCountryList = new ArrayList<>();
		List<String> idList = dao.findVertex(anno);
		for(String s: idList) {
			connectedCountryList.add(idMap.get(Integer.parseInt(s)));
		}
		return connectedCountryList;
	}
	
	public List<String> getListaGradi(List<Country> countryListYear, int anno) {
		createGraph(anno);
		List<String> countryDegreeList = new ArrayList<>();
		String result = "";
		int grado;
		for(Country c: countryListYear) {
			grado = this.grafo.degreeOf(c);
			result = c + " - " +grado;
			countryDegreeList.add(result);
			result = "";
		}
		return countryDegreeList;
	}
	
	
	public void createGraph(int anno) {
		List<Country> countryList = new ArrayList<>();
		List<String> connectedCountryList = new ArrayList<>();
		List<Border> edges = new ArrayList<>();
		List<Country> countryListYear = new ArrayList<>();
		List<String> degreeList = new ArrayList<>();
		countryList = dao.loadAllCountries(idMap);
		connectedCountryList = dao.findVertex(anno);
		edges = dao.findEdges(anno);
		
		for(String id: connectedCountryList) {
			countryListYear.add(idMap.get(Integer.parseInt(id)));
			this.grafo.addVertex(idMap.get(Integer.parseInt(id)));
		}
		
		for(Border b: edges) {
			Country origine = idMap.get(b.getState1no());
			Country destinazione = idMap.get(b.getState2no());
			grafo.addEdge(origine, destinazione);
		}
	}
	
	
	
	public String ottieniNumComponenti(int anno) {
		createGraph(anno);
		return "\nNumero di vertici: "+this.grafo.vertexSet().size() +"\nNumero di archi: "+this.grafo.edgeSet().size();
	}
	
	public List<Country> trovaViciniDi(Country vertice, int anno) {
		createGraph(anno);
		List<Country> result = new ArrayList<>();
		result = Graphs.neighborListOf(grafo, vertice);
		return result;
	}
	
	public List<Country> countryRaggiundibiliDa(Country countrySorgente) {
		HashMap<Country, Country> back = new HashMap<>();
		List<Country> result = new ArrayList<Country>();
		
		GraphIterator<Country, DefaultEdge> it = new DepthFirstIterator<>(this.grafo, countrySorgente);
		it.addTraversalListener(new EdgeTraversedGraphListener(grafo, back));
		
		back.put(countrySorgente, null);
		
		while(it.hasNext()) {
			result.add(it.next());
		}
		//System.out.println(Arrays.asList(back));
		return result;
	}
	
	public List<String> trovaConfini(int anno) {
		String confine = "";
		List<Border> confini = dao.findEdges(anno);
		List<String> result = new ArrayList<>();
		for(Border b: confini) {
			confine = idMap.get(b.getState1no()) + " - " + idMap.get(b.getState2no()) ;
			result.add(confine);			
		}
		return result;
	}
}
