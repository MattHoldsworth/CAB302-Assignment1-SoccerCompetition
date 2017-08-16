package asgn1SoccerCompetition;
import java.util.ArrayList;
import java.util.Collections;
import asgn1Exceptions.LeagueException;
import asgn1Exceptions.TeamException;

/**
 * A class to model a soccer league. Matches are played between teams and points awarded for a win,
 * loss or draw. After each match teams are ranked, first by points, then by goal difference and then
 * alphabetically. 
 * 
 * @author Matthew Holdsworth
 * @version 1.0
 *
 */
public class SoccerLeague implements SportsLeague{
	// Specifies the number of teams required/limit of teams for the league
	private int requiredTeams;
	// Specifies is the league is in the off season
	private boolean offSeason;
	// Holds the SoccerTeams in a league
	private ArrayList<SoccerTeam> leagueTeams;

	/**
	 * Generates a model of a soccer team with the specified number of teams. 
	 * A season can not start until that specific number of teams has been added. 
	 * Once that number of teams has been reached no more teams can be added unless
	 * a team is first removed. 
	 * 
	 * @param requiredTeams The number of teams required/limit for the league.
	 */
	public SoccerLeague (int requiredTeams){
		// Ensures season has not started
		offSeason = true;
		this.requiredTeams = requiredTeams;
		leagueTeams = new ArrayList<SoccerTeam>();
	}//end

	/**
	 * Registers a team to the league.
	 * 
	 * @param team Registers a team to play in the league.
	 * @throws LeagueException If the season has already started, if the maximum number of 
	 * teams allowed to register has already been reached or a team with the 
	 * same official name has already been registered.
	 */
	public void registerTeam(SoccerTeam team) throws LeagueException {
		if(!offSeason){
			throw new LeagueException("You cannot register teams whilst a season is already in progress!");
		} else if(requiredTeams == leagueTeams.size()){
			throw new LeagueException("You have reached the maximum number of teams in a single league!");
		} else {
			for(SoccerTeam thisTeam: leagueTeams){
				// Compares the official name of the team to be added with the teams in the league
				if (thisTeam.getOfficialName().equals(team.getOfficialName())){
					throw new LeagueException("This team cannot be registered as its name is already in use!");
				}//end if
			}//end for
			leagueTeams.add(team);
		}//end if-else
	}//end registerTeam
	
	/**
	 * Removes a team from the league.
	 * 
	 * @param team The team to remove
	 * @throws LeagueException if the season has not ended or if the team is not registered into the league.
	 */
	public void removeTeam(SoccerTeam team) throws LeagueException{
		// Determines if the league contains the team and if the season has already started
		if (!offSeason||!leagueTeams.contains(team)) {
			throw new LeagueException("Cannot remove team because it is off-season or it does not exist");
		} else {
			leagueTeams.remove(team);
		}//end if-else
	}//end removeTeam
	
	/** 
	 * Gets the number of teams currently registered to the league
	 * 
	 * @return the current number of teams registered
	 */
	public int getRegisteredNumTeams(){
		return leagueTeams.size();
	}//end
	
	/**
	 * Gets the number of teams required for the league to begin its 
	 * season which is also the maximum number of teams that can be registered
	 * to a league.

	 * @return The number of teams required by the league/maximum number of teams in the league
	 */
	public int getRequiredNumTeams(){
		return requiredTeams;
	}//end
	
	/** 
	 * Starts a new season by reverting all statistics for each team to initial values.
	 * 
	 * @throws LeagueException if the number of registered teams does not equal the required number of teams or if the season has already started
	 */
	public void startNewSeason() throws LeagueException{
		// Determines if the required teams have been met and if the season has already started
		if (leagueTeams.size() != requiredTeams||(!offSeason)){
			throw new LeagueException("There is not enough teams registered in the league to start a season, or the season has already started!");
		} else {
			for (SoccerTeam team: leagueTeams){
				team.resetStats();
				offSeason = false;// Season started
			}//end for loop
		}//end if-else
	}//end startNewSeason
	
	/**
	 * Ends the season.
	 * 
	 * @throws LeagueException if season has not started
	 */
	public void endSeason() throws LeagueException{
		if (offSeason) {
			throw new LeagueException("Season is already in progress");
		} else {
			offSeason = true;
		}//end if-else
	}//end endSeason
	
	/**
	 * Specifies if the league is in the off season (i.e. when matches are not played).
	 * @return True If the league is in its off season, false otherwise.
	 */
	public boolean isOffSeason(){
		return this.offSeason;
	}//end
	
	/**
	 * Returns a team with a specific name.
	 * 
	 * @param name The official name of the team to search for.
	 * @return The team object with the specified official name.
	 * @throws LeagueException if no team has that official name.
	 */
	public SoccerTeam getTeamByOfficalName(String name) throws LeagueException{
		for(SoccerTeam team: leagueTeams){
			if(name.equals(team.getOfficialName())){
				return team;
			}//end if
		}//end for
		throw new LeagueException("This team does not exist in this league!");
	}//end
		
	/**
	 * Plays a match in a specified league between two teams with the respective goals. After each match the teams are
	 * resorted.
     *
	 * @param homeTeamName The name of the home team.
	 * @param homeTeamGoals The number of goals scored by the home team.
	 * @param awayTeamName The name of the away team.
	 * @param awayTeamGoals The number of goals scored by the away team.
	 * @throws LeagueException If the season has not started or if both teams have the same official name. 
	 */
	public void playMatch(String homeTeamName, int homeTeamGoals, String awayTeamName, int awayTeamGoals) throws LeagueException{
		if(offSeason){
			throw new LeagueException("The season has not started yet!");
		} else if (homeTeamName == awayTeamName) {
			throw new LeagueException("The same team cannot play each other!");
		} else {
			SoccerTeam homeTeam = getTeamByOfficalName(homeTeamName);
			SoccerTeam awayTeam = getTeamByOfficalName(awayTeamName);
			try {
				homeTeam.playMatch(homeTeamGoals, awayTeamGoals);
				awayTeam.playMatch(awayTeamGoals, homeTeamGoals);
			} catch (TeamException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}//end try-catch
		}//end if-else
	}//end playMatch
	
	/**
	 * Displays a ranked list of the teams in the league  to the screen.
	 */
	public void displayLeagueTable(){
		// For each team in array teams, display team details
		for (SoccerTeam team: leagueTeams) {
			team.displayTeamDetails();
		}//end for
	}//end displayLeagueTable
	
	/**
	 * Returns the highest ranked team in the league.
     *
	 * @return The highest ranked team in the league. 
	 * @throws LeagueException if the number of teams is zero or less than the required number of teams.
	 */
	public SoccerTeam getTopTeam() throws LeagueException {
		SoccerTeam topTeam = leagueTeams.get(0);
		if (leagueTeams.size() < requiredTeams){
			throw new LeagueException("There is insufficient teams in the league!");
		} else {
			for (SoccerTeam thisTeam: leagueTeams){
				if(topTeam.compareTo(thisTeam) >= 1){
					topTeam = thisTeam;
				}//end if
			}//end for
		return topTeam;
		}//end if-else
	}//end getTopTeam

	/**
	 * Returns the lowest ranked team in the league.
     *
	 * @return The lowest ranked team in the league. 
	 * @throws LeagueException if the number of teams is zero or less than the required number of teams.
	 */
	public SoccerTeam getBottomTeam() throws LeagueException {
		SoccerTeam bottomTeam = leagueTeams.get(0);
		if (leagueTeams.size() < requiredTeams){
			throw new LeagueException("There is insufficient teams in the league!");
		} else {
			for (SoccerTeam thisTeam: leagueTeams){
				if(bottomTeam.compareTo(thisTeam) <= -1){
					bottomTeam = thisTeam;
				}//end if
			}//end for
		return bottomTeam;
		}//end if-else
	}//end getBottomTeam
	/** 
	 * Sorts the teams in the league.
	 */
    public void sortTeams(){		
    	Collections.sort(leagueTeams);
    }//end
    
    /**
     * Specifies if a team with the given official name is registered to the league.
     * 
     * @param name The name of a team.
     * @return True if the team is registered to the league, false otherwise. 
     */
    public boolean containsTeam(String name){
    	boolean containsTeam = false;
    	for(SoccerTeam thisTeam: leagueTeams){
    		// Determines if the official names of the compared teams match
    		if(thisTeam.getOfficialName().equals(name)){
    			containsTeam = true;
    		}//end if
    	}//end for
    	return containsTeam;
    }//end containsTeam
}//end SoccerLeague
