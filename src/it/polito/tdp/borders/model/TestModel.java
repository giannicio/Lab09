package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;

import it.polito.tdp.borders.db.BordersDAO;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.createGraph(2000);
		HashMap<Integer, Country> idMap = new HashMap<>();
		BordersDAO dao = new BordersDAO();
		dao.loadAllCountries(idMap);
		List<Country> countryRaggiungibili = model.countryRaggiundibiliDa(idMap.get(325));
		System.out.print(countryRaggiungibili);
		
//		System.out.println("Creo il grafo relativo al 2000");
//		model.createGraph(2000);
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
