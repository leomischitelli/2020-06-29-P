package it.polito.tdp.PremierLeague.model;

import java.time.LocalDateTime;

public class Match {
	private Integer matchID;
	private Team teamHome;
	private Team teamAway;
	private Integer teamHomeFormation;
	private Integer teamAwayFormation;
	private Integer resultOfTeamHome;
	

	LocalDateTime date;
	
	public Match(Integer matchID, Team teamHome, Team teamAway, Integer teamHomeFormation,
			Integer teamAwayFormation, Integer resultOfTeamHome, LocalDateTime date) {
		super();
		this.matchID = matchID;
		this.teamHome = teamHome;
		this.teamAway = teamAway;
		this.teamHomeFormation = teamHomeFormation;
		this.teamAwayFormation = teamAwayFormation;
		this.resultOfTeamHome = resultOfTeamHome;
		this.date = date;
	}
	
	public Integer getMatchID() {
		return matchID;
	}
	public void setMatchID(Integer matchID) {
		this.matchID = matchID;
	}
	
	public Team getTeamHome() {
		return teamHome;
	}

	public void setTeamHome(Team teamHome) {
		this.teamHome = teamHome;
	}

	public Team getTeamAway() {
		return teamAway;
	}

	public void setTeamAway(Team teamAway) {
		this.teamAway = teamAway;
	}

	public Integer getTeamHomeFormation() {
		return teamHomeFormation;
	}
	public void setTeamHomeFormation(Integer teamHomeFormation) {
		this.teamHomeFormation = teamHomeFormation;
	}
	public Integer getTeamAwayFormation() {
		return teamAwayFormation;
	}
	public void setTeamAwayFormation(Integer teamAwayFormation) {
		this.teamAwayFormation = teamAwayFormation;
	}
	public Integer getReaultOfTeamHome() {
		return resultOfTeamHome;
	}
	public void setReaultOfTeamHome(Integer resultOfTeamHome) {
		this.resultOfTeamHome = resultOfTeamHome;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getResultOfTeamHome() {
		return resultOfTeamHome;
	}

	public void setResultOfTeamHome(Integer resultOfTeamHome) {
		this.resultOfTeamHome = resultOfTeamHome;
	}

	
	@Override
	public String toString() {
		return "[" + matchID + "] " + teamHome.toString() + " vs. " + teamAway.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matchID == null) ? 0 : matchID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (matchID == null) {
			if (other.matchID != null)
				return false;
		} else if (!matchID.equals(other.matchID))
			return false;
		return true;
	}
	
}
