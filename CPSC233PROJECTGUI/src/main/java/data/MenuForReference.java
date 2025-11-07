package data;

import player.FieldPlayer;
import player.GoaliePlayer;
import player.Player;
import util.Reader;
import util.Writer;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * NOTE: THIS IS THE OLD MENU AND IS NOT BEING USED, THIS FILE IS FOR REFERENCE ONLY
 *
 * The menu runs in a loop until the user selects "Exit" (option 0).
 * It validates user input and calls corresponding methods in the 'Data' class.
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */
public class MenuForReference {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> options = new ArrayList<>();
    private static Data data = new Data();

    // Initialize the menu options for the user interface
    static {
        options.add("Exit");
        options.add("Add a new player");
        options.add("Add a goal to player");
        options.add("Add an assist to a player.");
        options.add("Add a save to a player");
        options.add("Add Yellow card to a player");
        options.add("Add a Red card to a player");
        options.add("Change player's Jersey number");
        options.add("Print player's data");
        options.add("Get a recommended duo (Goalkeeper and Player)");
        options.add("Print all the players of a specific team");
        options.add("Print top 3 players with the most assists");
        options.add("Print top 3 players with the most fouls (Red cards + Yellow Cards)");
        options.add("Print the player who scored the most goals in the tournament");
        options.add("Print the team that has the most combined goals");
        options.add("Print the player that has the highest goal/foul ratio");
        options.add("Print players with a specific Jersey Number");
        options.add("Print all players");
        options.add("Get best Field player");
        options.add("Get best Goal-keeper");
        options.add("Save data to file");
        options.add("Load data from file");

    }

    private static String optionsMessage = "Store and access FIFA player's data\nMenu Options\n";

    // Format the options message for display to the user
    static {
        StringBuilder newString = new StringBuilder();
        newString.append(optionsMessage);
        for (int i = 0; i < options.size(); i++) {
            newString.append(String.format("%d. %s\n", i, options.get(i)));
        }
        optionsMessage = newString.toString();
    }

    /**
     * Displays the main menu and processes user input to navigate through options.
     * The menu runs in a loop until the user selects "Exit" (option 0).
     * It validates user input, executes the corresponding menu function,
     * and prompts for further actions.
     */
    public static void menuLoop(File loadFile) {
        if (loadFile != null) {
            readFile(loadFile);
        }

        // Display the menu options
        System.out.println(optionsMessage);
        System.out.println("Enter your choice: ");

        // Read user input
        String input = scanner.nextLine();

        // Validate input to ensure it's numeric
        while (!isNumeric(input)) {
            System.out.println("Invalid entry!\nPlease choose one of the numbered options provided above: ");
            input = scanner.nextLine();
        }

        // Convert input to an integer
        int optionSelected = Integer.parseInt(input);

        // Continue running the menu until the user chooses to exit (option 0)
        while (optionSelected != 0) {
            // Check if the input is a valid menu option
            if (optionSelected >= 1 && optionSelected < options.size()) {
                System.out.println("Option " + optionSelected + " is selected" + "\n" + options.get(optionSelected));
            }

            // Execute the corresponding function based on user input
            switch (optionSelected) {
                case 1 -> menuEnterPlayer();                          // Add a new player
                case 2 -> menuAddGoalToPlayer();                      // Add a goal to a player
                case 3 -> menuAddAssistToPlayer();                    // Add an assist
                case 4 -> menuAddSavetoPlayer();                      // Add a save
                case 5 -> menuAddYellowCardToPlayer();                // Add a yellow card
                case 6 -> menuAddRedCardToPlayer();                   // Add a red card
                case 7 -> menuChangeJerseyNumber();                   // Change jersey number
                case 8 -> menuPrintPlayerData();                      // Print player data
                case 9 -> getRecommendedDuo();                        // Get the best goalkeeper-player duo
                case 10 -> printPlayersInTeam();                      // Print all players from a team
                case 11 -> menuPrintThreePlayersWithMostAssists();    // Print top 3 players with most assists
                case 12 -> printThreePlayersWithMostFouls();          // Print top 3 players with most fouls
                case 13 -> menuGetPlayerWithMaximumGoals();           // Print player with max goals
                case 14 -> menuPrintTeamWithMaxGoals();               // Print the team with max goals
                case 15 -> printPlayerWithMaxGoalToFoulRatio();       // Print player with the best goal/foul ratio
                case 16 -> printPlayersWithJerseyNumber();            // Print players with a specific jersey number
                case 17 -> menuPrintAllPlayer();                      // Print all players
                case 18 -> getBestFieldPlayer();                      // Get best field player
                case 19 -> getBestGoalKeeper();                       // Get best goalkeeper
                case 20 -> save();                                    // Save data to file
                case 21 -> readFile();                                // Load data from file
                default -> System.out.println("Please select one of the options below.");
            }

            // Prompt user to press Enter before returning to the menu
            System.out.println("Press Enter to return to the program");
            scanner.nextLine();

            // Display the menu options again
            System.out.println(optionsMessage);
            input = scanner.nextLine();

            // Validate input to ensure it's numeric
            while (!isNumeric(input)) {
                System.out.println("Invalid entry!\nPlease choose one of the options provided above: ");
                input = scanner.nextLine();
            }

            // Convert input to an integer for the next iteration
            optionSelected = Integer.parseInt(input);
        }

        // User selected exit (0), print farewell message
        System.out.println("Thank you for using this program.");
    }

    // ------------------- 1 ------------------------

    /**
     * Prompts the user to enter a player's name, team, and jersey number.
     * Calls the 'Data.addPlayer()' function to store the new player.
     * Displays a success or failure message based on whether the player was added successfully.
     */
    public static void menuEnterPlayer() {
        String name = getName(); // Get player name first
        String team = getTeam(); // Get team name
        int jersey = getJersey(); // Get valid jersey number
        String role = getRole();
        // Call the Data class to add the player
        boolean success = data.addPlayer(name, team, jersey, role);

        // Display result message
        if (success)
            System.out.println("Player Stored Successfully!");
        else
            System.out.println("Player Already Exists!");
    }

    /**
     * Prompts the user for the player's role (Goalkeeper or Field Player).
     * Validates the input and returns the selected role.
     *
     * @return The player's role ('G' for Goalkeeper, 'F' for Field Player).
     */
    private static String getRole() {
        System.out.println("Enter 'G' if player is a Goalkeeper.");
        System.out.println("Enter 'F' if player is a Field Player.");

        String input = scanner.nextLine().toUpperCase();

        // Ensure the input is either 'G' or 'F'
        while (!(input.equalsIgnoreCase("G") || input.equalsIgnoreCase("F"))) {
            System.out.println("Invalid input! Please enter 'G' for Goalkeeper or 'F' for Field Player.");
            input = scanner.nextLine().toUpperCase();
        }
        System.out.println("You have selected the position: " +
                (input.equalsIgnoreCase("G") ? "Goalkeeper" : "Field Player"));
        return input;
    }


    // ------------ 2 --------------

    /**
     * Adds a goal to a specific player.
     * Prompts the user to enter the team and jersey number of the player.
     * Calls 'Data.addGoalToPlayer()' to update the player's goal count by 1.
     * Displays a success message if the goal was added or an error message if the player was not found.
     */
    private static void menuAddGoalToPlayer() {
        // Prompt user to enter team name and jersey number of the player
        boolean success = data.addGoalToPlayer(getTeam(), getJersey());

        // Display result message
        if (success) {
            System.out.println("Goal Added!");
        } else {
            System.out.println("No Field Player found with given jersey number and team name.");
        }
    }

    // -------------------- 3 -----------------

    /**
     * Adds an assist to a specific player.
     * Prompts the user to enter the team and jersey number of the player.
     * Calls 'Data.addAssistToPlayer()' to update the player's assist count by 1.
     * Displays a success message if the assist was added or an error message if the player was not found.
     */
    public static void menuAddAssistToPlayer() {
        // Prompt user to enter team name and jersey number of the player
        boolean success = (data.addAssistToPlayer(getTeam(), getJersey()));

        // Display result message
        if (success) {
            System.out.println("Assist Added!");
        } else {
            System.out.println("No Field Player found with given jersey number and team name.");
        }
    }

    // -------------  4 -------------

    /**
     * Adds a save to a specific player.
     * Prompts the user to enter the team and jersey number of the player.
     * Calls 'Data.addSaveToPlayer()' to update the player's save count by 1.
     * Displays a success message if the save was added or an error message if the player was not found.
     */
    public static void menuAddSavetoPlayer() {
        // Prompt user to enter team name and jersey number of the player
        boolean success = data.addSaveToPlayer(getTeam(), getJersey());

        // Display result message
        if (success) {
            System.out.println("Save Added!");
        } else {
            System.out.println("No Goal-Keeper with given jersey number and team name found");
        }
    }

    // ----------- 5 ------------------

    /**
     * Adds a yellow card to a specific player.
     * Prompts the user to enter the team and jersey number of the player.
     * Calls '.Data.addYellowCardToPlayer()' to update the player's yellow card count by 1.
     * Displays a success message if the yellow card was added or an error message if the player was not found.
     */
    private static void menuAddYellowCardToPlayer() {
        // Prompt user to enter team name and jersey number of the player
        boolean success = data.addYellowCardToPlayer(getTeam(), getJersey());

        // Display result message
        if (success) {
            System.out.println("Yellow Card Added!");
        } else {
            System.out.println("Player not found");
        }
    }

    // ----------- 6 -----------------

    /**
     * Adds a red card to a specific player
     * Prompts the user to enter the team and jersey number of the player
     * Calls 'Data.addRedCardToPlayer()' to update the player's red card count by 1.
     * Displays a success message if the red card was added or an error message if the player was not found.
     */
    private static void menuAddRedCardToPlayer() {
        // Prompt user to enter team name and jersey number of the player
        boolean success = data.addRedCardToPlayer(getTeam(), getJersey());

        // Display result message
        if (success) {
            System.out.println("Red Card Added!");
        } else {
            System.out.println("Player not found");
        }
    }
    // --------- 7 -------------

    /**
     * Changes the jersey number of a player
     * Prompts the user to enter the old jersey number, new jersey number, and team name.
     * Calls 'Data.changeJerseyNumber()' to update the player's jersey number.
     * Displays a success message if the jersey number was changed or an error message if the player was not found.
     */
    private static void menuChangeJerseyNumber() {

        // Prompt user for the team name
        String teamName = getTeam();


        // Prompt user for the old jersey number
        System.out.println("OLD JERSEY NUMBER--------");
        int oldJerseyNum = getJersey();

        // Prompt user for the new jersey number
        System.out.println("NEW JERSEY NUMBER--------");
        int newJerseyNum = getJersey();


        // Call function to update the jersey number
        boolean success = data.changeJerseyNumber(teamName, oldJerseyNum, newJerseyNum);

        // Display result message
        if (success) {
            System.out.println("Jersey Changed!");
        } else {
            System.out.println("Player not found");
        }
    }

    // -------- 8 ------------

    /**
     * Displays data for a specific player.
     * Prompts the user to enter the team name and jersey number.
     * Calls 'Data.getPlayerData()' to retrieve player details.
     * Prints the player's data if found, otherwise displays an error message.
     */
    private static void menuPrintPlayerData() {
        // Prompt user to enter team name
        String teamName = getTeam();

        // Prompt user to enter jersey number
        int jerseyNumber = getJersey();

        // Fetch player's data from the Data class
        String PlayerData = data.getPlayerData(teamName, jerseyNumber);

        // Check if the player exists and display data
        if (PlayerData == null)
            System.out.println("No player with given jersey number and team name found!");
        else
            System.out.println(PlayerData);
    }

    // ----------- 9 ---------------

    /**
     * Finds and displays a recommended player and goalkeeper duo.
     * Calls 'Data.getPlayerWithMaximumGoals()' to find the player with the most goals.
     * Calls 'Data.getBestGoalkeeper()' to find the goalkeeper with the most saves.
     * Displays the duo if found, otherwise shows an error message.
     */
    private static void getRecommendedDuo() {
        StringBuilder outputString = new StringBuilder();

        // Retrieve the player with the most goals
        Player playerWithMaxGoal = data.getPlayerWithMaximumGoals();

        // Retrieve the goalkeeper with the most saves
        Player goalkeeper = data.getBestGoalkeeper();

        // If either player or goalkeeper is missing, display an error message
        if (playerWithMaxGoal == null || goalkeeper == null) {
            System.out.println("Not enough players!");
        } else if (goalkeeper.equals(playerWithMaxGoal)) {
            System.out.println("Duo cannot be formed as they have the same Jersey number!");
        }
        // If both a player and a goalkeeper are found, display them
        else {
            FieldPlayer fieldPlayer = (FieldPlayer) playerWithMaxGoal;
            GoaliePlayer goaliePlayer = (GoaliePlayer) goalkeeper;
            outputString.append("DUO FOUND\n").append("Player: ").append(fieldPlayer.getName()).append(" GOALS: ")
                    .append(fieldPlayer.getGoalScored()).append("\nGOALKEEPER: ").append(goaliePlayer.getName()).
                    append(" SAVES: ").append(goaliePlayer.getSaveScore());
            System.out.println(outputString);
        }
    }
    // ------------- 10 --------------

    /**
     * Displays all players from a specific team.
     * Prompts the user to enter the team name.
     * Calls 'Data.getAllPlayersOnTeam()' to retrieve a list of players.
     * Prints the names and jersey numbers of the players  of that team if found, otherwise shows an error message.
     */
    private static void printPlayersInTeam() {
        boolean success = false;

        // Prompt user to enter a team name
        do {
            ArrayList<Player> players = data.getAllPlayersOnTeam(getTeam());

            // Check if there are any players on the team
            if (players != null) {
                success = true;
                System.out.println("NAME                JERSEY");
                System.out.println("--------------------------");

                // Loop through players ArrayList and display their name and jersey number
                for (Player player : players) {
                    System.out.printf("%-20s%-6s\n", player.getName(), player.getJerseyNumber());
                }
            }
            if (!success)
                System.out.println("Can not find any team with given name. Please try again: ");
        } while (!success);
    }


    // ----------- 11 ---------------

    /**
     * Displays the top three players with the most assists.
     * Calls 'Data.getThreePlayersWithMostAssists()' to retrieve the top players.
     * Prints their names, teams, jersey numbers, and assist counts.
     * If no players are found, displays an error message.
     */
    private static void menuPrintThreePlayersWithMostAssists() {
        // Retrieve the top three players with the most assists
        ArrayList<FieldPlayer> PlayersWithMostAssists = data.getThreePlayersWithMostAssists();

        // Check if there are no players with assists
        if (PlayersWithMostAssists.isEmpty()) {
            System.out.println("No players to display with most assists.");
            return;
        }

        // Print table header
        System.out.println("Top player(s) with most assists");
        System.out.printf("%-20s %-15s %-10s %-10s\n", "Player Name", "Team", "Jersey", "Assists");
        System.out.println("-------------------------------------------------------");

        // Loop through players and print their details
        for (FieldPlayer player : PlayersWithMostAssists) {
            System.out.printf("%-20s %-15s %-10s %-10s\n",
                    player.getName(), player.getTeam(), player.getJerseyNumber(), player.getAssist());
        }
    }


    // ------------ 12 ---------------

    /**
     * Displays the top three players with the highest combined red and yellow card count.
     * Calls 'Data.getThreePlayersWithMostFouls()' to retrieve the Players.
     * Prints their names and total foul count.
     * If no Players are found, displays an error message.
     */
    private static void printThreePlayersWithMostFouls() {
        // Retrieve the top three players with the most fouls
        ArrayList<Player> topFouls = data.getThreePlayersWithMostFouls();
        StringBuilder outputString = new StringBuilder();

        // Check if there are no players with fouls
        if (topFouls == null || topFouls.isEmpty()) {
            System.out.println("No players to display with most Fouls.");
            return;
        }
        // Loop through ArrayList that was returned from Data function and output to console
        for (Player player : topFouls) {
            outputString.append("NAME: ").append(player.getName()).append(" FOULS: ").append(player.getRedCardScore()
                    + player.getYellowCardScore()).append("\n");
        }
        System.out.println(outputString);
    }


    // ----------- 13 ------------

    /**
     * Displays the player with the most goals.
     * Calls 'Data.getPlayerWithMaximumGoals()' to retrieve the player.
     * Prints the player's name and goal count.
     * If no player is found, displays an error message.
     */
    private static void menuGetPlayerWithMaximumGoals() {
        // Retrieve the player with the most goals
        Player playerWithMaxGoal = data.getPlayerWithMaximumGoals();
        StringBuilder outputString = new StringBuilder();

        // Check if a player was found
        if (playerWithMaxGoal != null) {
            FieldPlayer fieldPlayer = (FieldPlayer) playerWithMaxGoal;
            outputString.append("NAME: ").append(fieldPlayer.getName()).append("\n").append("GOALS: ").
                    append(fieldPlayer.getGoalScored());
            System.out.println(outputString);
        } else {
            System.out.println("No players to display with most goals.");
        }
    }


    // ----------- 14 --------------

    /**
     * Displays the team with the most total goals.
     * Calls 'Data.getTeamWithMaxGoals()' to determine the leading team.
     * Prints the team name and total goals.
     */
    private static void menuPrintTeamWithMaxGoals() {
        System.out.println(data.getTeamWithMaxGoals());
    }


    // ------------ 15 ------------------

    /**
     * Displays the player with the highest goal-to-foul ratio.
     * Calls 'Data.getGreatestGoalFoulRatio()' to retrieve the best player.
     * Prints the player's name and ratio.
     * If no players are found, displays an error message.
     */
    private static void printPlayerWithMaxGoalToFoulRatio() {
        // Retrieve the player with the best goal-to-foul ratio
        String output = data.getGreatestGoalFoulRatio();

        // Check if a valid player was not found
        if (output == null) {
            System.out.println("No players in database!");
            return;
        }

        // Display the player's name and ratio
        System.out.println(output);
    }

    // -------------- 16 ---------------

    /**
     * Displays all players with a specific jersey number.
     * Prompts the user to enter the jersey number.
     * Calls 'Data.getPlayersWithJerseyNumber()' to find matching players from different teams.
     * Prints the names of players if found, otherwise shows an error message.
     */
    private static void printPlayersWithJerseyNumber() {
        // Prompt user to enter the jersey number
        int jerseyNumber = getJersey();

        // Retrieve all players with the specified jersey number
        ArrayList<Player> Players = data.getPlayersWithJerseyNumber(jerseyNumber);

        // Check if there are any players with the given jersey number
        if (Players == null) {
            System.out.println("No players found with that number.");
        }

        // Display the header for player data
        else {
            System.out.printf("players found with jersey number %d:\nNAME\n", jerseyNumber);
            // Loop through ArrayList of players that was returned from Data function and print them to console
            for (Player TPlayer : Players) {
                System.out.println(TPlayer.getName());
            }
        }
    }

    // ---------------- 17 -----------

    /**
     * Displays data for all players.
     * Calls 'Data.getAllPlayersData()' to retrieve the full player list.
     * Prints player details.
     */
    public static void menuPrintAllPlayer() {
        // Retrieve and display all players' data from the Data class
        System.out.println(data.getAllPlayersData());
    }

    // ---------------- 18 -----------

    /**
     * This method fetches and prints the best field player.
     * It retrieves the best field player from the data and prints it.
     * If no best field player is found, an error message is displayed.
     */
    public static void getBestFieldPlayer() {

        // Fetch the best field player from the data
        String fieldPlayer = data.getBestFieldPlayer();

        // If no field player is found, print an error message
        if (fieldPlayer == null)
            System.out.println("No best Field player found");
        else
            // Otherwise, print the best field player
            System.out.println(fieldPlayer);
    }

    // ---------------- 19 -----------

    /**
     * This method fetches and prints the best goalkeeper.
     * It retrieves the best goalkeeper from the data and prints it.
     * If no best goalkeeper is found, an error message is displayed.
     */
    public static void getBestGoalKeeper() {
        // Fetch the best goalkeeper from the data
        String goaliePlayer = data.getBestGoalKeeper2();

        // If no goalkeeper is found, print an error message
        if (goaliePlayer == null)
            System.out.println("No best Goalkeeper found");
        else
            // Otherwise, print the best goalkeeper
            System.out.println(goaliePlayer);
    }

    // ---------------- 20 -----------

    /**
     * Prompts the user to enter a file name for saving data.
     * Ensures the file name is not empty.
     * Saves the data to the specified file if valid.
     */
    public static void save() {
        String fileName = "";

        // Keep asking the user for a file name until it's non-empty
        do {
            System.out.println("Enter file name where you want to save data:");

            fileName = scanner.nextLine();
        } while (fileName.isEmpty());

        // Create a File object using the provided file name, appending ".csv"
        File saveFile = new File(String.format("%s.csv", fileName));

        // Attempt to save the data to the file
        boolean success = Writer.save(saveFile, data);

        // Provide feedback based on whether the save operation was successful
        if (success) {
            System.out.println("Data saved successfully!");
        } else {
            System.out.println("Error saving data!");
        }
    }

    // ---------------- 21 -----------

    /**
     * Prompts the user to enter a file name for reading data.
     * Loads data from the specified file if valid.
     */
    public static void readFile() {
        String fileName = "";

        // Keep asking the user for a file name until it's non-empty
        do {
            System.out.println("Enter file name to load data from:");
            fileName = scanner.nextLine();

        } while (fileName.isEmpty());

        // Create a File object using the provided file name
        File saveFile = new File(fileName);

        // Attempt to load data from the file
        boolean success = Reader.read(saveFile, data);

        // Provide feedback based on whether the load operation was successful
        if (success) {
            System.out.println("Data Loaded successfully!");
        } else {
            System.out.println("Error loading data!");
        }
    }

    /**
     * Loads file from given file
     *
     * @param saveFile file to be read
     */
    public static void readFile(File saveFile) {
        // Attempt to load data from the file
        boolean success = Reader.read(saveFile, data);

        // Provide feedback based on whether the load operation was successful
        if (success) {
            System.out.println("Data Loaded successfully!");
        } else {
            System.out.println("Error loading data!");
        }
    }

    //   -------------- Getter Methods -----------------------

    /**
     * Prompts the user to enter a valid player name.
     * Ensures the name is not empty and contains only alphabetic characters.
     * Returns the name in uppercase format.
     */
    public static String getName() {

        String name;
        while (true) {
            System.out.println("Enter player name:");

            // Read input and convert to uppercase
            name = scanner.nextLine().trim().toUpperCase();

            // Validate input to ensure only letters and spaces are allowed
            if (!name.isEmpty() && name.matches("[A-Z ]+")) {
                return name;
            }

            // Display error message for invalid input
            System.out.println("Invalid input! Name cannot be empty or contain numbers/symbols. Try again.");
        }
    }

    /**
     * Prompts the user to enter a valid jersey number.
     * Ensures the number is greater than 0.
     * Returns the jersey number as an integer.
     */
    private static int getJersey() {
        int jerseyNumber;
        while (true) {
            System.out.println("Enter a Player's jersey number (must be greater than 0):");

            // Check if the input is an integer
            if (scanner.hasNextInt()) {
                jerseyNumber = scanner.nextInt();

                // Validate the jersey number to be positive
                if (jerseyNumber > 0) {
                    scanner.nextLine(); // Consume the newline character
                    return jerseyNumber;
                }

                // Show error message if the jersey number is not valid
                System.out.println("Invalid input! Jersey number must be greater than 0. Try again.");
            } else {
                // Handle non-integer input
                System.out.println("Invalid input! Enter a valid number.");
                scanner.nextLine(); // Clear invalid input from scanner
            }
        }
    }

    /**
     * Prompts the user to enter a valid team name.
     * Ensures the name is not empty and contains only alphabetic characters.
     * Returns the team name in uppercase format.
     */
    private static String getTeam() {
        String teamName;
        while (true) {
            System.out.println("Enter team name:");

            // Read input and convert to uppercase
            teamName = scanner.nextLine().trim().toUpperCase();

            // Validate input to ensure only letters and spaces are allowed
            if (!teamName.isEmpty() && teamName.matches("[A-Z ]+")) {
                return teamName;
            }

            // Display error message for invalid input
            System.out.println("Invalid input! Team name cannot be empty or contain numbers/symbols. Try again.");
        }
    }

    /**
     * Checks if a given string is a valid integer.
     *
     * @param input The string to check.
     * @return true if the string is a valid integer, otherwise false.
     * @throws NumberFormatException If input is not a number.
     */
    public static boolean isNumeric(String input) {
        try {
            // Attempt to parse the input as an integer
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            // Return false if input is not a valid integer
            return false;
        }
    }
}
