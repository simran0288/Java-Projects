package player;

import java.util.Objects;

/**
 * The abstract Player class represents a player in the system.
 * This class holds the common properties and methods shared by all types of players,
 * including goals, assists, fouls, and role (Goalie or FieldPlayer).
 * Concrete classes (like FieldPlayer or Goalie) will extend this class and implement the abstract methods.
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */


public abstract class Player {
    // Instance variables representing player's details
    private final String name; // The player's name
    private final String team; // The team the player belongs to
    private int jerseyNumber; // The player's jersey number
    private int redCardScore; // The number of red cards the player has received
    private int yellowCardScore; // The number of yellow cards the player has received

    /**
     * Enum for defining player's role, either Goalie or Field Player.
     */
    public enum Role {
        GOALIE, FIELD_PLAYER
    }

    /**
     * Constructor to initialize the player with the provided name, team, and jersey number.
     * By default, the player starts with 0 red cards and 0 yellow cards.
     *
     * @param name         The name of the player.
     * @param team         The team the player is part of.
     * @param jerseyNumber The jersey number of the player.
     */
    public Player(String name, String team, int jerseyNumber) {
        this.name = name;
        this.team = team;
        this.jerseyNumber = jerseyNumber;
        this.redCardScore = 0; // Initialize red cards to 0
        this.yellowCardScore = 0; // Initialize yellow cards to 0
    }

    // Setter methods to update player's attributes
    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public void setRedCardScore(int redCardScore) {
        this.redCardScore = redCardScore;
    }

    public void setYellowCardScore(int yellowCardScore) {
        this.yellowCardScore = yellowCardScore;
    }

    // Getter methods to retrieve player's attributes
    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public int getYellowCardScore() {
        return yellowCardScore;
    }

    public int getRedCardScore() {
        return redCardScore;
    }

    /**
     * Adds one red card to the player's red card score.
     */
    public void addRedCard() {
        redCardScore++;
    }

    /**
     * Adds one yellow card to the player's yellow card score.
     */
    public void addYellowCard() {
        yellowCardScore++;
    }

    /**
     * Compares the fouls between this player and another player.
     * A foul is determined by the sum of yellow and red cards.
     *
     * @param player The player to compare with.
     * @return 1 if this player has more fouls, 0 if both players have equal fouls, -1 if the other player has more fouls.
     */
    public int compareWithFouls(Player player) {
        int fouls1 = this.yellowCardScore + this.redCardScore;
        int fouls2 = player.yellowCardScore + player.redCardScore;
        if (fouls1 > fouls2)
            return 1;

        else if (fouls1 == fouls2)
            return 0;

        return -1;
    }


    /**
     * Determines the role of the player (either Goalie or Field Player).
     * This is determined by checking if the instance is a FieldPlayer or another type of player.
     *
     * @return The role of the player (GOALIE or FIELD_PLAYER).
     */
    public Role getRole() {
        if (this instanceof FieldPlayer) {
            return Role.FIELD_PLAYER;
        }
        return Role.GOALIE;
    }

    /**
     * Abstract method to be implemented by subclasses to return the string representation of the player.
     *
     * @return A string representation of the player.
     */
    @Override
    public abstract String toString();

    /**
     * Abstract method for comparing two players. The comparison is determined by the concrete class implementing this method.
     *
     * @param player The player to compare with.
     * @return A negative integer, zero, or a positive integer if this player is less than, equal to, or greater than the specified player.
     */
    public abstract int compareTo(Player player);

    /**
     * Returns a hash code value for the player, based on name, team, and jersey number.
     *
     * @return A hash code value for this player.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, team, jerseyNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return yellowCardScore == player.yellowCardScore && Objects.equals(name, player.name);
    }
}
