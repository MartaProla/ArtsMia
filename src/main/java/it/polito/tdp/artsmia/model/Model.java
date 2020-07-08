package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private Graph<ArtObject, DefaultWeightedEdge>grafo;
	private Map<Integer, ArtObject>idMap;
	
	
	public Model() {
		//this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap=new HashMap<Integer, ArtObject>();
	}
	
	public void creaGrafo() {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		ArtsmiaDAO dao=new ArtsmiaDAO();
		dao.listObjects(idMap);
		// aggiungere i Vertici --> sono tutti gli oggetti
		Graphs.addAllVertices(this.grafo, idMap.values());
		//aggiungere gli archi
		//APPROCCIO 1 --> doppio ciclo for sui vertici e controllo se sono collegati
		/*for(ArtObject a1: this.grafo.vertexSet()) {
			for(ArtObject a2:this.grafo.vertexSet()) {
				//devo collegare a1 e a 2
				//controllo se non esiste già l'arco
				int peso=dao.getPeso(a1,a2);
				if(peso>0) {
					if(!this.grafo.containsEdge(a1,a2)) {
						Graphs.addEdge(grafo, a1, a2, peso);
					}
				}
			}*/
	
		//APPROCCIO 3 --> Mi faccio dare le adiacenze DAL DB
		for(Adiacenza a: dao.getAdiacenze()) {
			if(a.getPeso()>0) {
				Graphs.addEdge(this.grafo, this.idMap.get(a.getObj1()), this.idMap.get(a.getObj2()),a.getPeso());
			}
		}
		System.out.println(String.format("Grafo creato! Ha %d vertici e %d archi", this.grafo.vertexSet().size(),this.grafo.edgeSet().size()));
		}
		
		public int nVertici() {
			return this.grafo.vertexSet().size();
		}
		
		
		public int nArchi() {
			return this.grafo.edgeSet().size();
		}

}
