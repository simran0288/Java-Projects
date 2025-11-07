package util;

import data.Data;
import player.FieldPlayer;
import player.GoaliePlayer;

import java.io.File;
import java.util.Scanner;

/**
 * The ReadFile class provides functionality for reading player data from a file.
 * It reads data line by line, processes the contents based on player type (Field or Goalkeeper),
 * and then adds the player information into the provided Data object.
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */
public class Reader {

    /**
     * Reads player data from the specified file and populates the given Data object with players.
     * The file is assumed to have data formatted with the player type (F or G) followed by various player attributes.
     *
     * @param file The file containing player data.
     * @param data The Data object to which the players will be added.
     * @return true if the file is read successfully and players are added, false otherwise.
     */
    public static boolean read(File file, Data data) {
        try (Scanner scanner = new Scanner(file)) {

            // Process each line of the file
            while (scanner.hasNextLine()) {

                // Split the line into parts (comma-separated)
                String[] teamContents = scanner.nextLine().split(",");

                // Check if the line corresponds to a FieldPlayer (denoted by "F" in the file)
                if (teamContents[0].equals("F")) {

                    // Create a FieldPlayer and populate its attributes from the file data
                    FieldPlayer fieldPlayer = new FieldPlayer(teamContents[1], teamContents[2], Integer.parseInt(teamContents[3]));
                    fieldPlayer.setRedCardScore(Integer.parseInt(teamContents[4])); // Set the red card score
                    fieldPlayer.setYellowCardScore(Integer.parseInt(teamContents[5])); // Set the yellow card score
                    fieldPlayer.setGoalScored(Integer.parseInt(teamContents[6])); // Set the number of goals scored
                    fieldPlayer.setAssist(Integer.parseInt(teamContents[7])); // Set the number of assists
                    // Add the field player to the data object
                    data.addPlayer(fieldPlayer);

                    // Check if the line corresponds to a GoaliePlayer (denoted by "G" in the file)
                } else if (teamContents[0].equals("G")) {
                    GoaliePlayer goaliePlayer = new GoaliePlayer(teamContents[1], teamContents[2], Integer.parseInt(teamContents[3]));
                    goaliePlayer.setRedCardScore(Integer.parseInt(teamContents[4])); // Set the red card score
                    goaliePlayer.setYellowCardScore(Integer.parseInt(teamContents[5])); // Set the yellow card score
                    goaliePlayer.setSaveScore(Integer.parseInt(teamContents[6])); // Set the number of saves
                    // Add the goalie player to the data object
                    data.addPlayer(goaliePlayer);
                }
            }
            return true; // Return true if the file was read successfully and players were added.
        } catch (Exception e) {
            // Handle any IOException (such as file not found or issues while reading)
            return false; // Return false if there's an error reading the file
        }
    }
}

