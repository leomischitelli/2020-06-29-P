package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Match, DefaultWeightedEdge> grafo;
	private Map<Integer, Team> mappaTeam;
	private List<Match> listaMatch;
	private Map<Integer, Match> mappaMatch;
	private PremierLeagueDAO dao;
	private List<Connessione> listaConnessioni;
	private List<Match> sequenzaMigliore;
	private int pesoMax;
	
	public Model () {
		this.dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo(int soglia, int mese) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.mappaTeam = new HashMap<>();
		this.mappaMatch = new HashMap<>();
		
		for(Team t : this.dao.listAllTeams())
			mappaTeam.put(t.getTeamID(), t);
		
		this.listaMatch = new ArrayList<>(this.dao.getMatchesByMonth(mese, mappaTeam));
		for(Match m : this.listaMatch)
			mappaMatch.put(m.getMatchID(), m);
		
		Graphs.addAllVertices(this.grafo, this.listaMatch);
		
		this.listaConnessioni = new ArrayList<>(this.dao.getConnessioniByMonthMinutes(soglia, mese, mappaMatch));
		for(Connessione c : this.listaConnessioni)
			Graphs.addEdge(this.grafo, c.getM1(), c.getM2(), c.getGiocatori());
		
	}
	
	public List<Match> cercaCollegamento(Match m1, Match m2){
		this.sequenzaMigliore = new ArrayList<>();
		this.pesoMax = 0;
		
		List<Match> sequenzaAttuale = new ArrayList<>();
		sequenzaAttuale.add(m1); //parto necessariamente da m1
		cerca(sequenzaAttuale, m1, m2, 0);
		
		return this.sequenzaMigliore;
	}
	
	
	private void cerca(List<Match> sequenzaAttuale, Match ultimoInserimento, Match m2, int pesoAttuale) {
		Set<Match> vicini = new HashSet<>(Graphs.neighborSetOf(this.grafo, ultimoInserimento));
		vicini.removeAll(sequenzaAttuale); //evito contains e cicli
		
		for(Match m : vicini) {
			if(controllaTeam(m, ultimoInserimento)) { //true vuol dire che passo il controllo
				//passo ricorsivo
				DefaultWeightedEdge e = this.grafo.getEdge(m, ultimoInserimento);
				int peso = (int)this.grafo.getEdgeWeight(e);
				sequenzaAttuale.add(m);
				if(m.equals(m2)) //se ho aggiunto m2 controllo la soluzione
					controllaSoluzione(sequenzaAttuale, pesoAttuale+peso);
				cerca(sequenzaAttuale, m, m2, pesoAttuale+peso);
				sequenzaAttuale.remove(sequenzaAttuale.size() - 1);
			}
		}
		
	}

	

	private void controllaSoluzione(List<Match> sequenzaAttuale, int pesoAttuale) {
		
		if(pesoAttuale > this.pesoMax) {
			this.pesoMax = pesoAttuale;
			this.sequenzaMigliore = new ArrayList<>(sequenzaAttuale);
		}
		
	}

	private boolean controllaTeam(Match m, Match ultimo) {
		if(m.getTeamHome().equals(ultimo.getTeamHome()) && m.getTeamAway().equals(ultimo.getTeamAway()))
			return false;
		if(m.getTeamHome().equals(ultimo.getTeamAway()) && m.getTeamAway().equals(ultimo.getTeamHome()))
			return false;
		
		return true;
			
	}
	
	

	public int getPesoMax() {
		return pesoMax;
	}

	public int getNumeroVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Connessione> getConnessioniDescrescenti(){
//		Collections.sort(this.listaConnessioni);
		return this.listaConnessioni;
	}

	public List<Match> getListaMatch() {
		// TODO Auto-generated method stub
		return this.listaMatch;
	}
	
}
