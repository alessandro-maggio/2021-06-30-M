package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private Graph<Integer, DefaultWeightedEdge> grafo;
	private List<Integer> vertici;
	private List<Arco> ordinata;
	private GenesDao dao= new GenesDao();
	
	private Double lOttimo;
	
	private List<Integer> ottimo;
	
	
	public String creaGrafo() {
		
		this.grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.vertici= dao.getVertici();
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		
		Map<Arco, Set<CoppiaGeni>> mappa= dao.getArchi();
		
		for(Arco a: mappa.keySet()) {
			for(CoppiaGeni c: mappa.get(a)) {
				a.setPeso(c.getPeso());
			}
		}
		
		for(Arco a: mappa.keySet()) {
			
			Graphs.addEdge(this.grafo, a.getCromo1(), a.getCromo2(), a.getPeso());
		}
		
		ordinata= new ArrayList<>(mappa.keySet());
		
		Collections.sort(ordinata);
		
		String s= "Grafo creato! "+this.grafo.vertexSet().size()+" vertici, "+this.grafo.edgeSet().size()+" archi\n"+"Peso minimo= "+ordinata.get(0).getPeso()+", Peso massimo= "+ordinata.get(ordinata.size()-1).getPeso()+"\n";
	
		return s;
	}
	
	
	public String contaArchi(double soglia) {
		
		int contaMaggiore=0;
		int contaMinore=0;
		
		for(Arco a: ordinata) {
			if(a.getPeso()>soglia)
				contaMaggiore++;
			else if(a.getPeso()<soglia)
				contaMinore++;
		}
		
		String s="Soglia: "+soglia+" --> Maggiori: "+contaMaggiore+", Minori: "+contaMinore;
		
		return s;
		
	}
	
	public List<Integer> cercaCammino(double soglia){
		
		List<Integer> parziale= new ArrayList<>();
		ottimo = new ArrayList<>();
		lOttimo= 0.0;
		
		for(Integer v: this.grafo.vertexSet()) {
			parziale.clear();
			parziale.add(v);
			cerca(soglia, parziale, 0.0);
		}
		
		
		
		return ottimo;
		
	}


	private void cerca(double soglia, List<Integer> parziale, double L) {
		
		if(L > lOttimo) {
			
			ottimo= new ArrayList<Integer>(parziale);
			lOttimo= L;
		}
		
		
			List<Integer> vicini= Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
			for(Integer v2: vicini) {
				DefaultWeightedEdge e= this.grafo.getEdge(parziale.get(parziale.size()-1), v2);
				if(e!=null && this.grafo.getEdgeWeight(e)> soglia && !parziale.contains(v2)) {
					parziale.add(v2);
					L+=this.grafo.getEdgeWeight(e);
					cerca(soglia, parziale, L);
					parziale.remove(v2);
					L-=this.grafo.getEdgeWeight(e);
				}
			}
		}
	
	public double getMinimo() {
		 return this.ordinata.get(0).getPeso();
		
	}
	public double getMassimo() {
		return this.ordinata.get(ordinata.size()-1).getPeso();
	}
		
	}





