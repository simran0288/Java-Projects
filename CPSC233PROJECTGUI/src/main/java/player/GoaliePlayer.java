package player;

/**
 * The GoaliePlayer class represents a goalkeeper in the system.
 * It extends the Player class and implements the Comparable interface to compare goalkeepers.
 * A goalkeeper has additional attributes like saves, and the player can be compared based on their performance in terms of saves and fouls (yellow and red cards).
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */
public class GoaliePlayer extends Player implements Comparable<Player> {

    // Instance variable for saves made by the goalkeeper
    private int saves;

    /**
     * Constructor to initialize the GoaliePlayer object with the provided name, team, and jersey number.
     * The saves are initialized to 0.
     *
     * @param name         The name of the goalkeeper.
     * @param team         The team the goalkeeper is part of.
     * @param jerseyNumber The jersey number of the goalkeeper.
     */
    public GoaliePlayer(String name, String team, int jerseyNumber) {
        super(name, team, jerseyNumber); // Calling the constructor of the parent class (Player)
        this.saves = 0; // Initialize saves to 0
    }

    // Setter for save score
    public void setSaveScore(int score) {
        this.saves = score;
    }

    // Getter for save score
    public int getSaveScore() {
        return this.saves;
    }

    // Method to add a save
    public void addSave() {
        saves++;
    }

    /**
     * Provides the string representation of the GoaliePlayer object, displaying details such as name, team,
     * jersey number, yellow and red cards, and saves.
     *
     * @return A formatted string representing the goalkeeper details.
     */
    @Override
    public String toString() {
        return String.format("Name: %-15s Team: %-10s Jersey Number: %-4s Position: Goal Keeper    " +
                        "Yellow Cards: %-4s Red Cards: %-4s Saves: %-4s", getName(),
                getTeam(), getJerseyNumber(), getYellowCardScore(), getRedCardScore(), getSaveScore());
    }

    /**
     * Compares this GoaliePlayer with another player based on their performance.
     * The comparison is based on the player's saves and fouls (yellow and red cards).
     * The goalkeeper with more saves minus fouls is considered better.
     *
     * @param player The player to compare with.
     * @return 1 if this goalkeeper has better performance, 0 if both players are equal, -1 if the other player is better.
     */
    @Override
    public int compareTo(Player player) {

        GoaliePlayer player2 = (GoaliePlayer) player; // Cast the player to a GoaliePlayer
        double fouls1 = this.getRedCardScore() + this.getYellowCardScore(); // Calculate fouls for this goalkeeper
        double fouls2 = player2.getRedCardScore() + player2.getYellowCardScore(); // Calculate fouls for the other goalkeeper

        // Compare performance: saves - fouls
        if ((this.saves - fouls1) > (player2.saves - fouls2))
            return 1;

        else if ((this.saves - fouls1) == (player2.saves - fouls2))
            return 0;
        return -1;
    }
}

