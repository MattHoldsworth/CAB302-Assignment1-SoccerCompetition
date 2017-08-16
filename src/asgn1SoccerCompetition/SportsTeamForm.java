package asgn1SoccerCompetition;
import java.util.LinkedList;
import asgn1SportsUtils.WLD;

/**
 * A data structure to store the 'form' of a sports team, that is the 
 * the result (win, loss, draw) of the last 5 matches played. Every time
 * that a new results is added, the previous results are shifted down a
 * position in the data structure. After 5 games are played, any new element 
 * will 'push' the least recent result from the back of the data structure and 
 * will then be added to the front. 
 * 
 *  
 * @author Matthew Holdsworth
 *
 */
public class SportsTeamForm {
	// The number of recent games to show in the recent form of the team
	private static final int maxLength = 5;
	// Creates the data structure to hold the W,L,D match results
	private LinkedList<WLD> matchResult = new LinkedList<WLD>();
	// Declares no match symbol '-' to be used when no match is played
	private String noMatch = "-";
	// Variable to store the number of games
	private int numGames;
	
	/**
	 * Constructs the data structure that holds the match results (win, loss, draw) for recent matches.
	 * For simplicity the results for the last 5 matches will be stored.
	 * 
	 */
	public SportsTeamForm() {
	}//end constructor
	
	/**
	 * Adds a new result to the data structure. If the number of games played is less than 5 then the 
	 * result will be added to the the front of the data structure - with all teams shifting down one 
	 * position in the data structure. If the number of games played is more than 5 then the 6th most
	 * recent game is removed, the 2nd - 5th most recent games are shifted down a position and the 
	 * most recent game is added to the front of the data structure. 
	 * 
	 * @param result The result of the latest match
	 *
	 */
	public void addResultToForm(WLD result){
		// Increments the number of games played even if it is more than the maximum value
		numGames += 1;
		// If there is less than 5 games played
		if (matchResult.size() < maxLength) {
			// Adds new result to matchResult at the front of the list
			matchResult.addFirst(result);
		} else {
			// Destroys the last element in the list
			matchResult.removeLast();
			// Adds the new result to the front of the list
			matchResult.addFirst(result);
		}// end if-else
	}// end addResultToForm
	
	/**
	 * Returns a string that represents the results of the last few matches that a team has played. 
	 * The length of the string returned will be equal to the maximum number of matches. 
	 * A win ('W'), loss ('L') or draw ('D') will be indicated as specified. The order of results 
	 * is "12345" (i.e left to right) where 1 is the last match played and 5 is the 5th last match played. 
	 * If the number of matches played is less than the maximum number of matches then a no match ('-') 
	 * character will be used instead. Again, the order will be left to right so after one match is
	 *  played the returned string will be "1----". 
	 * 
	 * @return A string representing the results of recent matches.
	 */
	public String toString(){
		// Declares an empty string to store the results
		String results = "";
		// Declares the size of the empty string variable
		int stringSize;
		// For each result in linked list matchResult, the result is added to the string
		for (WLD result: matchResult) {
			results += result.toString().charAt(0);
		}// End for loop
		
		// If the linked list size is less than the maximum length
		if(matchResult.size() < maxLength) {
			// While the stringSize is less than the linked list size, a no match symbol ("-") is added to result
			for(stringSize = matchResult.size(); stringSize < maxLength; stringSize++) {
				results += noMatch;
			}// End for loop
		}// End if
		return results;
	}// end toString
	
	/**
	 * Returns the number of games played  
     *
	 * @return The number of games played. 
	 */
	public int getNumGames(){
		return numGames;
	}//end
	
	/**
	 * Resets the data structure to its initial values.
	 */
	public void resetForm() {
		// Clears the linked list of previous data
		matchResult.clear();
		// Resets the number of games played
		numGames = 0;
	}//end
	
}//end SportsTeamForm
