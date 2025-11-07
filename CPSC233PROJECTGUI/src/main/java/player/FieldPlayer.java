package player;

import java.util.Objects;

/**
 * The FieldPlayer class represents a field player in the system.
 * It extends the Player class and implements the Comparable interface to compare field players.
 * A field player has additional attributes like goals and assists, along with methods to update them.
 * The player can also be compared based on their performance in terms of goals, assists, and fouls.
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */


public class FieldPlayer extends Player implements Comparable<Player> {
    // Instance variables for goals and assists
    private int goals; // The number of goals scored by the field player
    private int assists; // The number of assists made by the field player

    /**
     * Constructor to initialize the FieldPlayer object with the provided name, team, and jersey number.
     * The goals and assists are initialized to 0, and the role is set to FIELD_PLAYER.
     *
     * @param name         The name of the player.
     * @param team         The team the player is part of.
     * @param jerseyNumber The jersey number of the player.
     */
    public FieldPlayer(String name, String team, int jerseyNumber) {
        super(name, team, jerseyNumber); // Calling the constructor of the parent class (Player)
        this.goals = 0; // Initialize goals to 0
        this.assists = 0; // Initialize assists to 0
    }

    // Setter for goal scored
    public void setGoalScored(int goalScored) {
        this.goals = goalScored;
    }

    // Getter for goal scored
    public int getGoalScored() {
        return goals;
    }

    // Getter for assists
    public int getAssist() {
        return this.assists;
    }

    // Setter for assists
    public void setAssist(int assists) {
        this.assists = assists;
    }

    // Method to add an assist
    public void addAssist() {
        assists++;
    }

    // Method to add a goal
    public void addGoal() {
        goals++;
    }

    /**
     * Compares this FieldPlayer with another player based on their assists.
     *
     * @param player The player to compare with.
     * @return 1 if this player has more assists, 0 if both players have equal assists, -1 if the other player has more assists.
     */
    public int compareWithAssists(Player player) {
        FieldPlayer player2 = (FieldPlayer) player; // Cast the player to a FieldPlayer
        if (this.assists > player2.assists)
            return 1;
        else if (this.assists == player2.assists)
            return 0;
        return -1;
    }

    /**
     * Provides the string representation of the FieldPlayer object, displaying details such as name, team,
     * jersey number, yellow and red cards, goals, and assists.
     *
     * @return A formatted string representing the player details.
     */
    @Override
    public String toString() {
        return String.format("Name: %-15s Team: %-10s Jersey Number: %-4s Position: Field Player   " +
                        "Yellow Cards: %-4s Red Cards: %-4s Goals: %-4s Assists: %-4s", getName(),
                getTeam(), getJerseyNumber(), getYellowCardScore(), getRedCardScore(), getGoalScored(), getAssist());
    }

    /**
     * Compares this FieldPlayer with another player based on their performance.
     * The comparison is based on the player's goals, assists, and fouls (yellow and red cards).
     * The player with more goals and assists minus fouls is considered better.
     *
     * @param player The player to compare with.
     * @return 1 if this player has better performance, 0 if both players are equal, -1 if the other player is better.
     */
    @Override
    public int compareTo(Player player) {
        FieldPlayer player2 = (FieldPlayer) player; // Cast the player to a FieldPlayer
        double fouls1 = this.getRedCardScore() + this.getYellowCardScore(); // Calculate fouls for this player
        double fouls2 = player2.getRedCardScore() + player2.getYellowCardScore(); // Calculate fouls for the other player

        // Compare performance: goals + assists - fouls
        if ((this.goals + this.assists - fouls1) >
                (player2.goals + player2.assists - fouls2))
            return 1;

        else if ((this.goals + this.assists - fouls1) ==
                (player2.goals + player2.assists) - fouls2)
            return 0;
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FieldPlayer that)) return false;
        if (!super.equals(o)) return false;
        return goals == that.goals && assists == that.assists;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), goals, assists);
    }
}
