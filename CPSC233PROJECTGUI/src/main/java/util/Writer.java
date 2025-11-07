package util;

import data.Data;
import player.FieldPlayer;
import player.GoaliePlayer;
import player.Player;
import team.Team;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The SaveFile class provides functionality to save player data to a specified file.
 * It iterates over all the players in all teams, writes their details in a CSV format,
 * and stores the data to the provided file. The data is stored with different formats for
 * field players and goalie players.
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */
public class Writer {

    /**
     * Saves the player data to the specified file.
     *
     * @param file The file to which the data will be written.
     * @param data The data containing all teams and players.
     * @return boolean indicating whether the save operation was successful or not.
     */
    public static boolean save(File file, Data data) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        try (FileWriter fw = new FileWriter(file)) {

            // Write header for the players section
            fw.write("Players\n");

            // Get all the teams from the data
            HashMap<String, Team> teams = data.getTeams();

            // Prepare a list to collect all players from all teams
            ArrayList<Player> players = new ArrayList<>();

            for (Team team : teams.values()) {

                // Add all players of the current team to the players list
                players.addAll(team.getTeamPlayers());
            }

            // Iterate over the players and write their data based on their type (FieldPlayer or GoaliePlayer)
            for (Player player : players) {

                // If the player is a FieldPlayer, write their data in the appropriate format
                if (player.getClass().equals(FieldPlayer.class)) {
                    fw.write("F,");
                    fw.write(String.format("%s,%s,%s,%s,%s,%s,%s\n", player.getName(), player.getTeam(),
                            player.getJerseyNumber(), player.getRedCardScore(), player.getYellowCardScore(),
                            ((FieldPlayer) player).getGoalScored(), ((FieldPlayer) player).getAssist()));
                }
                // If the player is a GoaliePlayer, write their data in the appropriate format
                else if (player.getClass().equals(GoaliePlayer.class)) {
                    fw.write("G,");
                    fw.write(String.format("%s,%s,%s,%s,%s,%s\n", player.getName(), player.getTeam(),
                            player.getJerseyNumber(), player.getRedCardScore(), player.getYellowCardScore(),
                            ((GoaliePlayer) player).getSaveScore()));
                }
            }

            // Ensure all data is written to the file
            fw.flush();

            // Return true to indicate success
            return true;
        }
        // Catch IOExceptions (e.g., file write issues) and return false
        catch (IOException e) {
            return false;
        }
    }
}
