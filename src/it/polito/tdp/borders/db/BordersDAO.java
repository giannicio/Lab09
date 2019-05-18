package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries(HashMap<Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country country = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				result.add(country);
				if(!idMap.containsValue(country)) {
					idMap.put(rs.getInt("ccode"), country);
				}
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	 public List<String> findVertex(int anno) {
		List<String> connectedCountry = new ArrayList<String>();
		String sql = " SELECT DISTINCT(state1no) " + 
				"FROM contiguity " + 
				"WHERE YEAR <= ? ";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String codiceStato = rs.getString("state1no");
				connectedCountry.add(codiceStato);
			}
			
			conn.close();
			return connectedCountry;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	} 
	 
	 public List<Border> findEdges(int anno) {
		List<Border> edges = new ArrayList<Border>();
		String sql = "SELECT state1no, state2no " + 
				"FROM contiguity " + 
				"WHERE YEAR <= ? AND conttype = 1 " + 
				"AND state1no > state2no";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Border border = new Border(rs.getInt("state1no"), rs.getInt("state2no"));
				edges.add(border);
			}
			
			conn.close();
			return edges;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	} 
}

