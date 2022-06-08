package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.genes.model.Arco;
import it.polito.tdp.genes.model.CoppiaGeni;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Integer> getVertici(){
		
		String sql = "select DISTINCT g.`Chromosome` as cromosoma "
				+ "from genes g "
				+ "where g.`Chromosome` <> 0 ";
			
		List<Integer> vertici = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				vertici.add(res.getInt("cromosoma"));
				
			}
			res.close();
			st.close();
			conn.close();
			return vertici;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
		
		
	}
	
	public Map<Arco, Set<CoppiaGeni>> getArchi(){
		
		String sql = "select g1.`Chromosome` as c1, g2.`Chromosome` as c2, i.`GeneID1` as g1, i.`GeneID2` as g2, i.`Expression_Corr` as peso "
				+ "from interactions i, genes g1, genes g2 "
				+ "where i.`GeneID1`= g1.`GeneID` and i.`GeneID2`= g2.`GeneID` and g1.`Chromosome`<> g2.`Chromosome` and g1.`Chromosome`<> 0 and g2.`Chromosome`<>0 and i.`GeneID1`<> i.`GeneID2` ";
			
		Map<Arco, Set<CoppiaGeni> > result = new HashMap<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				Arco a= new Arco(res.getInt("c1"), res.getInt("c2"));
				CoppiaGeni co= new CoppiaGeni(res.getString("g1"), res.getString("g2"), res.getDouble("peso"));
				
				if(!result.containsKey(a)) {
					Set<CoppiaGeni> elenco= new HashSet<>();
					result.put(a, elenco);
				}
				
				result.get(a).add(co);	
				
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
		
	}
	


	
}
