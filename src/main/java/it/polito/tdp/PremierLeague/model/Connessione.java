package it.polito.tdp.PremierLeague.model;

public class Connessione implements Comparable<Connessione> {

	private Match m1;
	private Match m2;
	private int giocatori;
	
	
	public Connessione(Match m1, Match m2, int giocatori) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.giocatori = giocatori;
	}


	public Match getM1() {
		return m1;
	}


	public Match getM2() {
		return m2;
	}


	public int getGiocatori() {
		return giocatori;
	}

	

	@Override
	public String toString() {
		return m1.toString() + " - " + m2.toString() + " (" + giocatori + ")";
	}


	@Override
	public int compareTo(Connessione o) {
		// TODO Auto-generated method stub
		return o.getGiocatori() - this.giocatori;
	}

}
