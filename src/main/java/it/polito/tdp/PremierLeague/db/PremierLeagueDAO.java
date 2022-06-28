package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Connessione;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
//				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
//							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
//				
//				
//				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> listAllTeams(){
		String sql = "SELECT * FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				result.add(new Team(res.getInt("TeamID"), res.getString("Name")));
				
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Match> getMatchesByMonth(int mese, Map<Integer, Team> mappaTeam) {
		String sql = "SELECT m.* "
				+ "FROM matches m "
				+ "WHERE MONTH(m.Date) = ? ";
		
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Team home = mappaTeam.get(res.getInt("m.TeamHomeID"));
				Team away = mappaTeam.get(res.getInt("m.TeamAwayID"));
				if(home!=null && away!=null)
					result.add(new Match(res.getInt("m.MatchID"), home , away, res.getInt("m.teamHomeFormation"), 
						res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime()));

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Connessione> getConnessioniByMonthMinutes(int soglia, int mese, Map<Integer, Match> mappaMatch) {
		String sql = "SELECT m1.MatchID AS m1, m2.MatchID AS m2, COUNT(DISTINCT a1.PlayerID) AS peso "
				+ "FROM matches m1, matches m2, actions a1, actions a2 "
				+ "WHERE a1.MatchID = m1.MatchID "
				+ "AND a2.MatchID = m2.MatchID "
				+ "AND a1.MatchID > a2.MatchID "
				+ "AND a1.PlayerID = a2.PlayerID "
				+ "AND a1.TimePlayed >= ? "
				+ "AND a2.TimePlayed >= ? "
				+ "AND MONTH(m1.Date) = ? "
				+ "AND MONTH(m2.Date) = MONTH(m1.Date) "
				+ "GROUP BY m1.MatchID, m2.MatchID "
				+ "ORDER BY peso DESC";
		List<Connessione> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, soglia);
			st.setInt(2, soglia);
			st.setInt(3, mese);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Match m1 = mappaMatch.get(res.getInt("m1"));
				Match m2 = mappaMatch.get(res.getInt("m2"));
				if(m1!=null && m2!=null)
					result.add(new Connessione(m1, m2, res.getInt("peso")));

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
