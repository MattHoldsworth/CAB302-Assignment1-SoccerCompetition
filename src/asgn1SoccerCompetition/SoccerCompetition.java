package asgn1SoccerCompetition;
import java.util.ArrayList;
import asgn1Exceptions.CompetitionException;
import asgn1Exceptions.LeagueException;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * A class to model a soccer competition. The competition contains one or more number of leagues, 
 * each of which contain a number of teams. Over the course a season matches are played between
 * teams in each league. At the end of the season a premier (top ranked team) and wooden spooner 
 * (bottom ranked team) are declared for each league. If there are multiple leagues then relegation 
 * and promotions occur between the leagues.
 * 
 * @author Matthew Holdsworth
 * @version 1.0
 *
 */
public class SoccerCompetition implements SportsCompetition{
	private ArrayList<SoccerLeague> soccerComp;
	private Iterator<SoccerLeague> leagueIterator;
	private LinkedList<SoccerTeam> promotionTeams;
	private LinkedList<SoccerTeam> demotionTeams;
	private String name;
	private SoccerLeague valueStore;
	private SoccerTeam promoteTeam;
	private SoccerTeam demoteTeam;
	private int leagueNum;
	private int numLeagues;

	/**
	 * Creates the model for a new soccer competition with a specific name,
	 * number of leagues and number of teams in each league
	 * 
	 * @param name The name of the competition.
	 * @param numLeagues The number of leagues in the competition.
	 * @param numTeams The number of teams in each league.
	 */
	public SoccerCompetition(String name, int numLeagues, int numTeams){
		soccerComp = new ArrayList<SoccerLeague>();
		this.name = name;
		this.numLeagues = numLeagues;
		int position = 0;
		for (position = 0; position < numLeagues; position++) {
			soccerComp.add(new SoccerLeague(numTeams));
		}// End for loop
	}//end constructor
	
	/**
	 * Retrieves a league with a specific number (indexed from 0). Returns an exception if the 
	 * league number is invalid.
	 * 
	 * @param leagueNum The number of the league to return.
	 * @return A league specified by leagueNum.
	 * @throws CompetitionException if the specified league number is less than 0.
	 *  or equal to or greater than the number of leagues in the competition.
	 */
	public SoccerLeague getLeague(int leagueNum) throws CompetitionException{
		if (leagueNum < 0 || leagueNum >= soccerComp.size()){
			throw new CompetitionException("Invalid league number");
		} else {
			return soccerComp.get(leagueNum);
		}//end if-else
	}//end getLeague
	
	/**
	 * Starts a new soccer season for each league in the competition.
	 */
	public void startSeason() {
		// For each league in soccer competition, the program tries to start a new season and throws a LeagueException if it can't
		for (SoccerLeague league: soccerComp) {
			try {
				league.startNewSeason();
			} catch (LeagueException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}// End Try-Catch
		}// End for loop
	}// End startSeason

	/** 
	 * Ends the season of each of the leagues in the competition. 
	 * If there is more than one league then it handles promotion
	 * and relegation between the leagues.  
	 * 
	 */
	public void endSeason()  {
		leagueIterator = soccerComp.iterator();
		promotionTeams = new LinkedList<SoccerTeam>();
		demotionTeams = new LinkedList<SoccerTeam>();
		try {
			while (leagueIterator.hasNext()) {
				valueStore = leagueIterator.next();
				valueStore.endSeason();
				promotionTeams.add(valueStore.getTopTeam());
				demotionTeams.add(valueStore.getBottomTeam());
			}//end while
		} catch (LeagueException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}//end try-catch
		promotionTeams.removeFirst();
		demotionTeams.removeLast();
		for (leagueNum = 1; leagueNum < soccerComp.size(); leagueNum++) {
			promoteTeam = promotionTeams.removeFirst();
			demoteTeam = demotionTeams.removeFirst();
			try {
				soccerComp.get(leagueNum).removeTeam(promoteTeam);
				soccerComp.get(leagueNum - 1).removeTeam(demoteTeam);
				soccerComp.get(leagueNum - 1).registerTeam(promoteTeam);
				soccerComp.get(leagueNum).registerTeam(demoteTeam);
			} catch (LeagueException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}//end try-catch
		}//end for
	}//end endSeason

	/** 
	 * For each league displays the competition standings.
	 */
	public void displayCompetitionStandings(){
		for (SoccerLeague league: soccerComp) {
			league.sortTeams();
		}
		System.out.println("+++++" + this.name + "+++++");
		for (int position = 0; position < numLeagues; position++) {
			System.out.println("---- League" + (position +1) + " ----");
			System.out.println("Official Name" +  '\t' +  "Nick Name" + '\t' + "Form" + '\t' +  "Played" + '\t' + "Won" + '\t' + "Lost" + '\t' + "Drawn" + '\t' + "For" + '\t' + "Against" + '\t' + "GlDiff" + '\t' + "Points");
			soccerComp.get(position).displayLeagueTable();
		}//end for
	}//end displayCompetitionStandings
}//end soccerCompetition
