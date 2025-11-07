package data;

import player.Player;
import player.GoaliePlayer;
import player.FieldPlayer;
import team.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The Data class manages the FIFA player database, including player statistics, team management, and rankings.
 * It provides methods for adding players, updating their stats, retrieving individual and team data,
 * and ranking players based on various performance criteria.
 * <p>
 * The data is stored in:
 * - A HashMap ('team') for team-wise organization .
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */
public class Data {
    private HashMap<String, Team> teams;

    /**
     * Created Data constructor and initializes teams Hashmap.
     */
    public Data() {
        teams = new HashMap<>();
    }

    /**
     * These are Constants that we will use in Player Array to store main.Players.Player's data at particular index in Player Array.
     */
    public HashMap<String, Team> getTeams() {
        return teams;
    }


// ------------------- 1 --------------------------

    /**
     * Adds a new player to the team after checking for duplicate jersey numbers.
     *
     * @param PlayerName   Player's name.
     * @param teamName     The name of the team.
     * @param jerseyNumber The jersey number of the player.
     * @param role         The role of the player, either "G" (Goalkeeper) or other for field players.
     * @return True if the player was added successfully, false if a player with the same jersey already exists on the team.
     */
    public boolean addPlayer(String PlayerName, String teamName, int jerseyNumber, String role) {
        String formattedTeamName = teamName.toUpperCase().trim(); // Standardize team name to uppercase
        PlayerName = PlayerName.toUpperCase().trim(); // Standardize player name to uppercase
        if (!checkPlayerExist(jerseyNumber, teamName)) { // Ensure jersey number is unique for the team
            role = role.trim().toUpperCase(); // Format role as uppercase

            // Create a GoaliePlayer if the role is "G"
            Player player;
            if (role.equals("G")) {
                player = new GoaliePlayer(PlayerName, formattedTeamName, jerseyNumber);
                if (teams.containsKey(formattedTeamName)) {
                    teams.get(formattedTeamName).addPlayer(player);
                } else {
                    // If the team doesn't exist, create a new team and add the player
                    Team team1 = new Team(formattedTeamName, player);
                    teams.put(formattedTeamName, team1);
                }
            } else {
                // Create a FieldPlayer if the role is not "G"
                player = new FieldPlayer(PlayerName, formattedTeamName, jerseyNumber);
                if (teams.containsKey(formattedTeamName)) {
                    teams.get(formattedTeamName).addPlayer(player); // Add player to the existing team
                } else {
                    // If the team doesn't exist, create a new team and add the player
                    Team team1 = new Team(formattedTeamName, player);
                    teams.put(formattedTeamName, team1);
                }
            }
            return true;
        }
        return false; // Return false if the player already exists
    }

    /**
     * Adds a player object directly to the team.
     *
     * @param player The player object to add.
     * @return True if the player was added successfully, false if the team does not exist.
     */
    public boolean addPlayer(Player player) {
        String teamName = player.getTeam().toUpperCase(); // Get the player's team
        if (teams.containsKey(teamName)) {
            teams.get(teamName).addPlayer(player); // Add player to the existing team
        } else {
            // If the team doesn't exist, create a new team and add the player
            Team team = new Team(teamName, player);
            teams.put(teamName, team);
        }
        return true;
    }
    // ------------------------ 2 -----------------------------

    /**
     * Increases the goal count for a field player by 1.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return True if the player's goal count was updated successfully, false if no matching player was found.
     */
    public boolean addGoalToPlayer(String teamName, int jerseyNumber) {

        String formattedTeamName = teamName.toUpperCase().trim(); // Standardize team name
        if (teams.containsKey(formattedTeamName)) {
            ArrayList<Player> players = teams.get(formattedTeamName).getTeamPlayers(); // Get players of the team
            for (Player player : players) {
                if (player instanceof FieldPlayer) {
                    // Check if the player is a FieldPlayer and matches the jersey number
                    if (player.getJerseyNumber() == jerseyNumber) {
                        FieldPlayer fieldPlayer = (FieldPlayer) player;
                        fieldPlayer.addGoal(); // Increase goal count
                        return true;
                    }
                }
            }
        }
        return false; // Return false if no player was found
    }
    // ----------------------- 3 -------------------------

    /**
     * Increases the assist count for a field player by 1.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return True if the player's assist count was updated successfully, false if no matching player was found.
     */
    public boolean addAssistToPlayer(String teamName, int jerseyNumber) {
        String formattedTeamName = teamName.toUpperCase().trim(); // Standardize team name
        if (teams.containsKey(formattedTeamName)) {
            ArrayList<Player> players = teams.get(formattedTeamName).getTeamPlayers(); // Get players of the team
            for (Player player : players) {
                if (player instanceof FieldPlayer) {
                    // Check if the player is a FieldPlayer and matches the jersey number
                    if (player.getJerseyNumber() == jerseyNumber) {
                        FieldPlayer fieldPlayer = (FieldPlayer) player;
                        fieldPlayer.addAssist(); // Increase assist count
                        return true;
                    }
                }
            }
        }
        return false; // Return false if player is not found
    }

    // ------------------------- 4 --------------------------

    /**
     * Increases the save count for a goalkeeper by 1.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the goalkeeper.
     * @return True if the goalkeeper's save count was updated successfully, false if no matching goalkeeper was found.
     */
    public boolean addSaveToPlayer(String teamName, int jerseyNumber) {
        String formattedTeamName = teamName.toUpperCase().trim(); // Standardize team name
        if (teams.containsKey(formattedTeamName)) {
            ArrayList<Player> players = teams.get(formattedTeamName).getTeamPlayers(); // Get players of the team
            for (Player player : players) {
                if (player instanceof GoaliePlayer) {
                    // Check if the player is a GoaliePlayer and matches the jersey number
                    if (player.getJerseyNumber() == jerseyNumber) {
                        GoaliePlayer goaliePlayer = (GoaliePlayer) player;
                        goaliePlayer.addSave(); // Increase save count
                        return true;
                    }
                }
            }
        }
        return false; // Return false if player is not found
    }

    // ------------------------- 5 ------------------------

    /**
     * Increases the yellow card count for a player by 1.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return True if the player's yellow card count was updated successfully, false if no matching player was found.
     */
    public boolean addYellowCardToPlayer(String teamName, int jerseyNumber) {
        String formattedTeamName = teamName.toUpperCase().trim(); // Standardize team name
        if (teams.containsKey(formattedTeamName)) {
            ArrayList<Player> players = teams.get(formattedTeamName).getTeamPlayers(); // Get players of the team
            for (Player player : players) {
                // Check if the player matches the jersey number
                if (player.getJerseyNumber() == jerseyNumber) {
                    player.addYellowCard(); // Increase yellow card count
                    return true;
                }
            }
        }
        return false; // Return false if player is not found
    }


    // ----------------------- 6 -------------------------

    /**
     * Increases the red card count for a player by 1.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return True if the player's red card count was updated successfully, false if no matching player was found.
     */
    public boolean addRedCardToPlayer(String teamName, int jerseyNumber) {
        String formattedTeamName = teamName.toUpperCase().trim(); // Standardize team name
        if (teams.containsKey(formattedTeamName)) {
            ArrayList<Player> players = teams.get(formattedTeamName).getTeamPlayers(); // Get players of the team
            for (Player player : players) {
                // Check if the player matches the jersey number
                if (player.getJerseyNumber() == jerseyNumber) {
                    player.addRedCard(); // Increase red card count
                    return true;
                }
            }
        }
        return false; // Return false if player is not found
    }

    // ----------------------- 7 ----------------------------

    /**
     * Changes the jersey number of a player if the new jersey number is valid.
     *
     * @param teamName     The name of the team.
     * @param oldJerseyNum The old jersey number of the player.
     * @param newJerseyNum The new jersey number to assign to the player.
     * @return True if the jersey number was successfully changed, false otherwise.
     */
    public boolean changeJerseyNumber(String teamName, int oldJerseyNum, int newJerseyNum) {
        String formattedTeamName = teamName.toUpperCase().trim(); // Standardize team name
        if (teams.containsKey(formattedTeamName)) {
            ArrayList<Player> players = teams.get(formattedTeamName).getTeamPlayers(); // Get players of the team
            for (Player player : players) {
                // Check if the player matches the old jersey number
                if (player.getJerseyNumber() == oldJerseyNum) {
                    if (player.getJerseyNumber() != newJerseyNum && !checkPlayerExist(newJerseyNum, formattedTeamName)) {
                        player.setJerseyNumber(newJerseyNum); // Change jersey number
                        return true;
                    }
                }
            }
        }
        return false; // Return false if player is not found or new jersey number is invalid
    }

    // ----------------------- 8 --------------------------------

    /**
     * Retrieves detailed statistics for a player based on their jersey number.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return A formatted string containing the player's data, or null if the player is not found.
     */
    public String getPlayerData(String teamName, int jerseyNumber) {
        String formattedTeamName = teamName.toUpperCase().trim(); // Standardize team name
        if (teams.containsKey(formattedTeamName)) {
            ArrayList<Player> players = teams.get(formattedTeamName).getTeamPlayers(); // Get players of the team
            for (Player player : players) {
                // Check if the player matches the jersey number
                if (player.getJerseyNumber() == jerseyNumber) {
                    return player.toString(); // Return formatted player data
                }
            }
        }
        return null; // Return null if player is not found
    }

    // ----------------------- 9 --------------------------------
    /**
     * Loops through all players and returns the player with the greatest number of saves.
     *
     * @return The goalkeeper with the highest save score.
     */
    public Player getBestGoalkeeper() {
        int max = -1;
        Player maxSavePlayer = null;
        for (Team team : teams.values()) {
            for (Player player : team.getTeamPlayers()) {
                if (player instanceof GoaliePlayer goalPlayer) {
                    if (goalPlayer.getSaveScore() > max) {
                        max = goalPlayer.getSaveScore();
                        maxSavePlayer = player;
                    }
                }
            }
        }
        return maxSavePlayer;
    }

    // ----------------- 10 -------------------

    /**
     * Retrieves all players on a specific team.
     *
     * @param teamName The name of the team.
     * @return An ArrayList of players on the team, or null if no players are found.
     */
    public ArrayList<Player> getAllPlayersOnTeam(String teamName) {
        for (Team team : teams.values()) {
            if (team.getTeamName().equalsIgnoreCase(teamName)) {
                return team.getTeamPlayers(); // Return the list of players for the team
            }
        }
        return null; // Return null if the team does not exist
    }

    //  ------------------ 11 ---------------------

    /**
     * Retrieves the top three players with the highest assist count.
     *
     * @return An ArrayList of up to three players sorted by their assist count in descending order.
     */
    public ArrayList<FieldPlayer> getThreePlayersWithMostAssists() {
        ArrayList<FieldPlayer> players = new ArrayList<>();
        for (Team team : teams.values()) {
            for (Player player : team.getTeamPlayers()) {
                if (player instanceof FieldPlayer fieldPlayer) {
                    players.add(fieldPlayer); // Add FieldPlayer to the list
                }
            }
        }
        players.sort(FieldPlayer::compareWithAssists); // Sort players by assists in descending order
        ArrayList<FieldPlayer> topPlayers = new ArrayList<>();
        int size = players.size();
        if (size < 3) return new ArrayList<>();
        topPlayers.add(players.get(size - 1));
        topPlayers.add(players.get(size - 2));
        topPlayers.add(players.get(size - 3)); // Get the top 3 players
        return topPlayers;
    }

    // --------------------- 12 ------------------

    /**
     * Retrieves the top three players with the most combined red cards and yellow cards.
     * Loops through the player list multiple times to find the players with the highest foul counts.
     * The method adds the players with the greatest combined red and yellow cards to an ArrayList
     * and flags them to prevent duplicate entries.
     *
     * @return ArrayList of the top 3 players with the most fouls, sorted by the combined red and yellow cards.
     */
    public ArrayList<Player> getThreePlayersWithMostFouls() {
        ArrayList<Player> players = new ArrayList<>();
        // Add all players from all teams to the list
        for (Team team : teams.values()) {
            players.addAll(team.getTeamPlayers());
        }
        // Sort players by foul count
        players.sort(Player::compareWithFouls);
        ArrayList<Player> topPlayers = new ArrayList<>();
        int size = players.size();

        if (size < 3) return new ArrayList<>();

        // Add the top 3 players
        topPlayers.add(players.get(size - 1));
        topPlayers.add(players.get(size - 2));
        topPlayers.add(players.get(size - 3));

        return topPlayers;
    }

// ----------------------- 13 -------------------

    /**
     * Finds the player with the maximum number of goals scored.
     * The method iterates over all players and checks for the one with the highest goal count.
     *
     * @return The player with the maximum goals, or null if no players are found.
     */
    public Player getPlayerWithMaximumGoals() {
        int max = -1;
        Player maxGoalPlayer = null;

        // Loop through all teams and players to find the one with the most goals
        for (Team team : teams.values()) {
            for (Player player : team.getTeamPlayers()) {
                if (player instanceof FieldPlayer fieldPlayer) {
                    // Check if the current player's goals exceed the max found so far
                    if (fieldPlayer.getGoalScored() > max) {
                        max = fieldPlayer.getGoalScored();
                        maxGoalPlayer = player;
                    }
                }
            }
        }
        return maxGoalPlayer;
    }

// --------------- 14 -------------

    /**
     * Finds the team (or teams) with the highest total number of goals scored.
     *
     * @return A string containing the team(s) with the most goals, or appropriate message if no goals are scored.
     */
    public String getTeamWithMaxGoals() {
        int max = 0;
        String teamWithMaxGoals = null;

        // Loop through teams and their players to calculate total goals for each team
        for (Team team : teams.values()) {
            int teamGoals = 0;

            for (Player player : team.getTeamPlayers()) {
                if (player instanceof FieldPlayer fieldPlayer) {
                    teamGoals += fieldPlayer.getGoalScored();
                }
            }

            // Update team with max goals if current team has more goals
            if (teamGoals > max) {
                teamWithMaxGoals = team.getTeamName();
                max = teamGoals;
            }
        }

        if (teamWithMaxGoals == null) {
            return "No teams available";
        }
        return teamWithMaxGoals;
    }

// ----------------- 15 --------------------

    /**
     * Calculates the Goal/Foul ratio for each player and returns the player with the highest ratio.
     * The ratio is calculated as Goals/(Red Cards + Yellow Cards).
     *
     * @return A formatted string with the player’s statistics (name, goal/foul ratio).
     */
    public String getGreatestGoalFoulRatio() {
        Player bestPlayer = null;
        double bestRatio = 0;
        int bestPlayerFouls = 0;
        int bestPlayerGoals = 0;
        StringBuilder outputString = new StringBuilder();

        // Loop through all players to calculate the goal/foul ratio
        for (Team team : teams.values()) {
            for (Player player : team.getTeamPlayers()) {
                if (player instanceof FieldPlayer fieldPlayer) {
                    int currentPlayerFouls = fieldPlayer.getRedCardScore() + fieldPlayer.getYellowCardScore();
                    int currentPlayerGoals = fieldPlayer.getGoalScored();
                    double ratio = (double) currentPlayerGoals / (double) currentPlayerFouls;

                    // Update the best player if the current player has a better ratio
                    if (ratio >= bestRatio) {
                        bestPlayerFouls = currentPlayerFouls;
                        bestPlayerGoals = currentPlayerGoals;
                        bestPlayer = player;
                        bestRatio = ratio;
                    }
                }
            }
        }

        assert bestPlayer != null; // Ensure a player is found
        outputString.append("NAME: ").append(bestPlayer.getName()).append(" GOAL/FOUL Ratio: ").append(bestPlayerGoals).append("/").append(bestPlayerFouls);
        return outputString.toString();
    }

// ---------------- 16 ---------------

    /**
     * Retrieves all players with a specific jersey number from all teams.
     *
     * @param jerseyNumber The jersey number to search for.
     * @return An ArrayList of players that have the specified jersey number, or null if no such players exist.
     */
    public ArrayList<Player> getPlayersWithJerseyNumber(int jerseyNumber) {
        ArrayList<Player> playersWithNumber = new ArrayList<>();

        // Loop through all teams and their players to find the ones with the specified jersey number
        for (Team team : teams.values()) {
            for (Player player : team.getTeamPlayers()) {
                if (player.getJerseyNumber() == jerseyNumber) {
                    playersWithNumber.add(player);
                }
            }
        }

        // Return null if no players with the specified jersey number are found
        if (playersWithNumber.isEmpty())
            return null;

        return playersWithNumber;
    }

// ---------------------- 17 --------------------

    /**
     * Retrieves all players' data from all teams and returns it as a formatted string.
     *
     * @return A string containing the data of all players in the system.
     */
    public String getAllPlayersData() {
        if (teams.isEmpty()){
            return "No Players in data";
        }
        StringBuilder output = new StringBuilder(); // StringBuilder to efficiently build the output string
        output.append("Player DATA:\n"); // Header for player data

        // Loop through all teams and players, appending their data to the output
        for (Team team : teams.values()) {
            for (Player player : team.getTeamPlayers()) {
                output.append(player.toString()).append("\n");
            }
        }
        return output.toString(); // Return the complete player data as a formatted string
    }

// ---------------------- 18 --------------------

    /**
     * Retrieves the best field player from all teams.
     *
     * @return A string representation of the best field player, or null if no field players are found.
     */
    public String getBestFieldPlayer() {
        ArrayList<FieldPlayer> players = new ArrayList<>();

        // Loop through all teams and add field players to the list
        for (Team team : teams.values()) {
            for (Player player : team.getTeamPlayers()) {
                if (player instanceof FieldPlayer fieldPlayer) {
                    players.add(fieldPlayer);
                }
            }
        }

        if (players.isEmpty())
            return null;

        Collections.sort(players); // Sort field players
        FieldPlayer bestPlayer = players.getLast(); // Get the last (best) player
        return bestPlayer.toString();
    }

// ---------------------- 19 --------------------

    /**
     * Retrieves the best goalkeeper from all teams.
     *
     * @return A string representation of the best goalkeeper, or null if no goalkeepers are found.
     */
    public String getBestGoalKeeper2() {
        ArrayList<GoaliePlayer> players = new ArrayList<>();

        // Loop through all teams and add goalkeepers to the list
        for (Team team : teams.values()) {
            for (Player player : team.getTeamPlayers()) {
                if (player instanceof GoaliePlayer goaliePlayer) {
                    players.add(goaliePlayer);
                }
            }
        }

        if (players.isEmpty())
            return null;

        Collections.sort(players); // Sort goalkeepers
        GoaliePlayer bestPlayer = players.getLast(); // Get the last (best) player
        return bestPlayer.toString();
    }

    // -------------------------------------------------------
    /**
     * Checks whether a player with the given jersey number exists on a specific team.
     * This ensures no duplicate jersey numbers are assigned within a single team.
     *
     * @param jerseyNumber The jersey number to check.
     * @param teamName     The name of the team (case-insensitive).
     * @return True if a player with the same jersey number exists in the team, false otherwise.
     */
    public boolean checkPlayerExist(int jerseyNumber, String teamName) {
        teamName = teamName.toUpperCase();

        if (teams.containsKey(teamName)) {
            for (Player player : teams.get(teamName).getTeamPlayers()) {
                if (player.getJerseyNumber() == jerseyNumber)
                    return true; // Jersey number exists
            }
        }
        return false; // Jersey number does not exist
    }

//-----------------------------------------------------------------------------------------------------------

    /**
     * Retrieves the number of goals scored by a specific player.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return The number of goals scored by the player, or -1 if the player is not found.
     */
    public int getGoals(String teamName, int jerseyNumber) {
        teamName = teamName.toUpperCase();
        if (teams.containsKey(teamName)) {
            for (Player player : teams.get(teamName).getTeamPlayers()) {
                if (player.getJerseyNumber() == jerseyNumber && player instanceof FieldPlayer fieldPlayer)
                    return fieldPlayer.getGoalScored();
            }
        }
        return -1; // Player not found
    }

    /**
     * Retrieves the number of assists made by a specific player.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return The number of assists made by the player, or -1 if the player is not found.
     */
    public int getAssists(String teamName, int jerseyNumber) {
        teamName = teamName.toUpperCase();
        if (teams.containsKey(teamName)) {
            for (Player player : teams.get(teamName).getTeamPlayers()) {
                if (player.getJerseyNumber() == jerseyNumber && player instanceof FieldPlayer fieldPlayer)
                    return fieldPlayer.getAssist();
            }
        }
        return -1; // Player not found
    }

    // -------------------------------------------------------
    /**
     * Retrieves the number of saves made by a specific player.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return The number of saves made by the player, or -1 if the player is not found.
     */
    public int getSaves(String teamName, int jerseyNumber) {
        teamName = teamName.toUpperCase();
        if (teams.containsKey(teamName)) {
            for (Player player : teams.get(teamName).getTeamPlayers()) {
                if (player.getJerseyNumber() == jerseyNumber && player instanceof GoaliePlayer goaliePlayer)
                    return goaliePlayer.getSaveScore();
            }
        }
        return -1; // Player not found
    }

    // -------------------------------------------------------
      /**
     * Retrieves the number of yellow cards received by a specific player.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return The number of yellow cards received by the player, or -1 if the player is not found.
     */
    public int getYellowCards(String teamName, int jerseyNumber) {
        teamName = teamName.toUpperCase();
        if (teams.containsKey(teamName)) {
            for (Player player : teams.get(teamName).getTeamPlayers()) {
                if (player.getJerseyNumber() == jerseyNumber)
                    return player.getYellowCardScore();
            }
        }
        return -1; // Player not found
    }

    // -------------------------------------------------------
    /**
     * Retrieves the number of red cards received by a specific player.
     *
     * @param teamName     The name of the team (case-insensitive).
     * @param jerseyNumber The jersey number of the player.
     * @return The number of red cards received by the player, or -1 if the player is not found.
     */
    public int getRedCards(String teamName, int jerseyNumber) {
        teamName = teamName.toUpperCase();
        if (teams.containsKey(teamName)) {
            for (Player player : teams.get(teamName).getTeamPlayers()) {
                if (player.getJerseyNumber() == jerseyNumber)
                    return player.getRedCardScore();
            }
        }
        return -1; // Player not found
    }

    // -------------------------------------------------------
    /**
     * Retrieves all player names
     *
     * @return A string containing the data of all player names in the system.
     */
    public String getAllPlayerNames() {
        if (teams.isEmpty()){
            return "No Players in data\n";
        }
        StringBuilder output = new StringBuilder(); // StringBuilder to efficiently build the output string
        int i=0;
        // Loop through all teams and players, appending their data to the output
        for (Team team : teams.values()) {
            for (Player player : team.getTeamPlayers()) {
                i++;
                output.append((i)).append(". Player Name: ").append(player.getName()).append("\t").append(" ").append("Jersey Number: ").
                        append(player.getJerseyNumber()).append("\t" + "Team: ").append(player.getTeam()).append("\n");
            }
        }
        return output.toString(); // Return the complete player data as a formatted string
    }

    // -------------------------------------------------------

    /**
     * This method gets players of that team selected
     * @return Arraylist of player of that team
     */
    public ArrayList<Player> getPlayerOfTeam(String teamName){

        teamName = teamName.toUpperCase();
        if (teams.containsKey(teamName)) {
            return teams.get(teamName).getTeamPlayers();
        }
        else {
            return null;
        }

    }

    // -------------------------------------------------------

    /**
     * This method gets all players present in data
     * @return Arraylist of all players present in data
     */
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (Team team : teams.values()) {
            players.addAll(team.getTeamPlayers());
        }
        return players;
    }

    // -------------------------------------------------------

    /**
     * This method gets name of all teams present in data
     * @return String of name of all Teams present
     */
    public String getAllTeams(){
        if (teams.isEmpty()){
            return "No Teams in data\n";
        }
        StringBuilder output = new StringBuilder();
        int i=0;
        for (Team team : teams.values()) {
            i++;
            output.append((i)).append(" ").append(team.getTeamName()).append("\n");
        }
        return output.toString();
    }
}
