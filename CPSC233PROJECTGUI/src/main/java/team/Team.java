package team;

import player.*;

import java.util.ArrayList;

/**
 * The Team class represents a team consisting of players.
 * It allows you to add players to the team, retrieve the list of players,
 * and get the team's name.
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */
public class Team {

    // Team name
    private final String teamName;
    // List to store players belonging to the team
    private ArrayList<Player> teamPlayers = new ArrayList<>();

    /**
     * Constructor to create a team with a given name and an initial player.
     *
     * @param teamName The name of the team.
     * @param player   The initial player to add to the team.
     */
    public Team(String teamName, Player player) {
        this.teamName = teamName;
        // Add the initial player to the team
        teamPlayers.add(player);
    }

    /**
     * Adds a new player to the team.
     *
     * @param p The player to be added to the team.
     * @return boolean indicating whether the player was added successfully.
     */
    public boolean addPlayer(Player p) {
        // Add the player to the team's player list
        teamPlayers.add(p);
        // Return true to indicate the player was successfully added
        return true;
    }

    /**
     * Gets the list of players in the team.
     *
     * @return The list of players in the team.
     */
    public ArrayList<Player> getTeamPlayers() {
        return teamPlayers;
    }

    /**
     * Gets the name of the team.
     *
     * @return The name of the team.
     */
    public String getTeamName() {
        return teamName;
    }
}


