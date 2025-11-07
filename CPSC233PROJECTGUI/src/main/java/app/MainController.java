package app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import data.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import player.FieldPlayer;
import player.GoaliePlayer;
import player.Player;
import util.Reader;
import util.Writer;

import java.io.File;
import java.util.*;

/**
 * MainController class manages UI control and lets user deal wth the application
 * It provides user all the options to enter player ,update their stats and retrieve their data or team data
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date April 14, 2025
 * @version 1.3
 */
public class MainController {

    @FXML private GridPane gridPane;
    @FXML private ComboBox<String> menuOptions;
    @FXML private Label errorForInput;
    @FXML private Label jerseyLabel;
    @FXML private TextField jerseyTF;
    @FXML private TextArea outputText;
    @FXML private Label playerLabel;
    @FXML private TextField playerTF;
    @FXML private Label roleLabel;
    @FXML private TextField roleTF;
    @FXML private Label teamLabel;
    @FXML private TextField teamTF;
    @FXML ComboBox<String> teamChoice = new ComboBox<>();
    @FXML ComboBox<String> playerChoice = new ComboBox<>();
    @FXML ComboBox<Integer> jerseyChoice = new ComboBox<>();
    @FXML AnchorPane rightPane;
    @FXML AnchorPane leftPane;
    @FXML StackPane outputTextPane;

    @FXML private static final String[] options = {
            "Add a new player",
            "Add a goal to player",
            "Add an assist to a player",
            "Add a save to a player",
            "Add Yellow card to a player",
            "Add a Red card to a player",
            "Print player's data",
            "Get a recommended duo (Goalkeeper and Player)",
            "Print all the players of a specific team",
            "Print top 3 players with the most assists",
            "Print top 3 players with the most fouls (Red cards + Yellow Cards)",
            "Print the player who scored the most goals in the tournament",
            "Print the team that has the most combined goals",
            "Print the player that has the highest goal/foul ratio",
            "Print players with a specific Jersey Number",
            "Print all players",
            "Get best Field player",
            "Get best Goal-keeper",
            "Print all teams"
    };

    final int INVALID = -1;

    private static final Data data = new Data();

    /**
     * This method loads the file selected by user and adds the data to program
     */
    @FXML
    public void initialize() {
        // Call getSaveFile method in main
        String saveFileName = Main.getSaveFileName();
        // If saveFile data found in main, then load to current data
        if ( saveFileName != null) {
            File saveFile = new File(saveFileName);
            boolean success = Reader.read(saveFile, data);
            if (success) {
                outputText.setText("File successfully loaded!");
            } else {
                errorForInput.setText("Error reading file!");
            }
        }
        menuOptions.setItems(FXCollections.observableArrayList(options));
        playerChoice.setPromptText("Select Player");
        teamChoice.setPromptText("Select Team");
        menuOptions.setValue(options[0]);
        outputText.setFont(Font.font("Courier New", 14));
    }

    /**
     * This method gets the value of the option selected by user and directs it to particular function
     */
    @FXML
    public void executeMethod(){
        outputText.setText("");
        String optionSelected = menuOptions.getValue();
        switch (optionSelected) {
            case "Add a new player" -> menuEnterPlayer();                                                                             // Add a new player
            case "Add a goal to player" -> menuAddGoalToPlayer();                                                                         // Add a goal to a player
            case "Add an assist to a player" -> menuAddAssistToPlayer();                                                              // Add an assist
            case "Add a save to a player" -> menuAddSavetoPlayer();                                                                       // Add a save
            case "Add Yellow card to a player" -> menuAddYellowCardToPlayer();                                                        // Add a yellow card
            case "Add a Red card to a player" -> menuAddRedCardToPlayer();                                                                // Add a red card
            case "Print player's data" -> menuPrintPlayerData();                                                                      // Print player data
            case "Get a recommended duo (Goalkeeper and Player)" -> getRecommendedDuo();                                                  // Get the best goalkeeper-player duo
            case "Print all the players of a specific team" -> printPlayersInTeam();                                                  // Print all players from a team
            case "Print top 3 players with the most assists" -> menuPrintThreePlayersWithMostAssists();                                   // Print top 3 players with most assists
            case "Print top 3 players with the most fouls (Red cards + Yellow Cards)" -> printThreePlayersWithMostFouls();            // Print top 3 players with most fouls
            case "Print the player who scored the most goals in the tournament" -> menuGetPlayerWithMaximumGoals();                       // Print player with max goals
            case "Print the team that has the most combined goals" -> menuPrintTeamWithMaxGoals();                                    // Print the team with max goals
            case "Print the player that has the highest goal/foul ratio" -> printPlayerWithMaxGoalToFoulRatio();                          // Print player with the best goal/foul ratio
            case "Print players with a specific Jersey Number" -> printPlayersWithJerseyNumber();                                     // Print players with a specific jersey number
            case "Print all players" -> menuPrintAllPlayer();                                                                             // Print all players
            case "Get best Field player" -> getBestFieldPlayer();                                                                     // Get best field player
            case "Get best Goal-keeper" -> getBestGoalKeeper();                                                                           // Get best goalkeeper
            case "Print all teams" -> allTeams();
        }
    }

    /**
     * This is helper function to avoid any exception while receiving role
     * @return role  of player as String as
     */
    private String getRole() {
        if (roleTF.getText().isEmpty()){

            roleTF.setBorder(Border.stroke(Color.RED));
        }
        String roleInput = roleTF.getText();
        if (roleInput.equalsIgnoreCase("G") || (roleInput.equalsIgnoreCase("F")) ){
            roleTF.setBorder(Border.stroke(Color.SKYBLUE));
            return roleInput;
        }
        roleTF.setBorder(Border.stroke(Color.RED));
        errorForInput.setText("Invalid Role! Enter 'G' for goal-keeper or 'F' for Field Player");
        return null;
    }

    /**
     * Prompts the user to enter a valid player name.
     * Ensures the name is not empty and contains only alphabetic characters.
     * Returns the name in uppercase format.
     */
    @FXML
    public String getName() {
        if(playerTF.getText().isEmpty()){
            playerTF.setBorder(Border.stroke(Color.RED));
        }
        String nameEntered = playerTF.getText();
        if (!nameEntered.isEmpty() && nameEntered.matches("[A-Za-z ]+")) {
            playerTF.setBorder(Border.stroke(Color.SKYBLUE));
            return nameEntered.toUpperCase();
        }
        errorForInput.setText("Invalid Player Name! Name cannot be empty or contain numbers/symbols. Try again.");
        errorForInput.setTextFill(Color.RED);
        return null;
    }

    /**
     * Prompts the user to enter a valid jersey number.
     * Ensures the number is greater than 0.
     * Returns the jersey number as an integer.
     */
    @FXML
    private int getJersey() {
        if (jerseyTF.getText().isEmpty()){
            jerseyTF.setBorder(Border.stroke(Color.RED));
        }

        String num = jerseyTF.getText();
        int jerseyNumber;
        if (isNumeric(num)){
            jerseyTF.setBorder(Border.stroke(Color.SKYBLUE));
            jerseyNumber = Integer.parseInt(num);
            // Validate the jersey number to be positive
            if (jerseyNumber > 0) {
                return jerseyNumber;
            }
            jerseyTF.setBorder(Border.stroke(Color.RED));
            // Show error message if the jersey number is not valid
            errorForInput.setText("Invalid Jersey Number! Jersey number must be greater than 0. Try again.");
            errorForInput.setTextFill(Color.RED);
        } else {
            jerseyTF.setBorder(Border.stroke(Color.RED));
            // Handle non-integer input
            errorForInput.setText("Invalid Jersey Number! Enter a valid number.");
            errorForInput.setTextFill(Color.RED);
        }
        return INVALID;
    }

    /**
     * Prompts the user to enter a valid team name.
     * Ensures the name is not empty and contains only alphabetic characters.
     * Returns the team name in uppercase format.
     */
    @FXML
    private String getTeam() {
        if (teamTF.getText().isEmpty()){
            teamTF.setBorder(Border.stroke(Color.RED));
        }
        String teamName = teamTF.getText();
        // Validate input to ensure only letters and spaces are allowed
        if (!teamName.isEmpty() && teamName.matches("[A-Za-z ]+")) {
            teamTF.setBorder(Border.stroke(Color.SKYBLUE));
            return teamName.toUpperCase();
        }
        teamTF.setBorder(Border.stroke(Color.RED));
        // Display error message for invalid input
        errorForInput.setText("Invalid Team Name! Team name cannot be empty or contain numbers/symbols. Try again.");
        errorForInput.setTextFill(Color.RED);
        return null;
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

    /**
     * Adds a player to the data.
     * Prompts the user to enter the team, player name, role and jersey number of the player.
     * Calls 'Data.addPlayer()' to update data.
     * Displays a success message if the player was added or an error message if the player was not added.
     */
    @FXML
    public void menuEnterPlayer() {
        errorForInput.setText("");
        String role = getRole();
        int jersey = getJersey(); // Get valid jersey number
        String name = getName();// Get player name first
        String team = getTeam(); // Get team name


        if (name == null || team == null || jersey == INVALID || role == null){
            return;
        }
        errorForInput.setText("");
        // Call the Data class to add the player
        boolean success = data.addPlayer(name, team, jersey, role);


        // Display result message
        if (success) {
            outputText.setText("Player Stored Successfully!"+"\n"+"Player Added: "+name+"\t jersey number: "+jersey);
        }
        else
            outputText.setText("Player Already Exists!");
    }

    /**
     * This method gets the Value of Player selected
     * @return the values of player in String array name[0] Team[1] Jersey[2]
     */
    public  String[] getPlayerInfo(){
        if (playerChoice.getValue() !=null) {
            return (playerChoice.getValue().split(","));
        }
        return null;
    }

    // ------------ 2 --------------

    /**
     * Adds a goal to a specific player.
     * Prompts the user to enter the team and jersey number of the player.
     * Calls 'Data.addGoalToPlayer()' to update the player's goal count by 1.
     * Displays a success message if the goal was added or an error message if the player was not found.
     */
    private void menuAddGoalToPlayer() {
        errorForInput.setText("");
        if (getPlayerInfo() !=null){
            int jersey = Integer.parseInt(getPlayerInfo()[2].trim());   // Get valid jersey number
            String team = getPlayerInfo()[1].trim(); // Get team name
            if (jersey == INVALID) {
                return;
            }
            // Prompt user to enter team name and jersey number of the player
            boolean success = data.addGoalToPlayer(team, jersey);
            errorForInput.setText("");


            // Display result message
            if (success) {
                outputText.setText("Goal Added!");
            } else {
                outputText.setText("No Field Player found with given jersey number and team name.");
            }
        }
    }

    // -------------------- 3 -----------------

    /**
     * Adds an assist to a specific player.
     * Prompts the user to enter the team and jersey number of the player.
     * Calls 'Data.addAssistToPlayer()' to update the player's assist count by 1.
     * Displays a success message if the assist was added or an error message if the player was not found.
     */
    public void menuAddAssistToPlayer() {
        errorForInput.setText("");
        if (getPlayerInfo() !=null){

        int jersey =Integer.parseInt(getPlayerInfo()[2].trim()) ;   // Get valid jersey number
        String team = getPlayerInfo()[1].trim(); // Get team name

        if (jersey == INVALID){
            return;
        }
        // Prompt user to enter team name and jersey number of the player
        boolean success = (data.addAssistToPlayer(team, jersey));
        errorForInput.setText("");


        // Display result message
        if (success) {
            outputText.setText("Assist Added!");
        } else {
            outputText.setText("No Field Player found with given jersey number and team name.");
        }
        }

    }

    // -------------  4 -------------

    /**
     * Adds a save to a specific player.
     * Prompts the user to enter the team and jersey number of the player.
     * Calls 'Data.addSaveToPlayer()' to update the player's save count by 1.
     * Displays a success message if the save was added or an error message if the player was not found.
     */
    public void menuAddSavetoPlayer() {
        errorForInput.setText("");
        if (getPlayerInfo() !=null){

        int jersey =Integer.parseInt(getPlayerInfo()[2].trim()) ;   // Get valid jersey number
        String team = getPlayerInfo()[1].trim(); // Get team name

        if (jersey == INVALID){
            return;
        }
        // Prompt user to enter team name and jersey number of the player
        boolean success = data.addSaveToPlayer(team, jersey);
        errorForInput.setText("");

        // Display result message
        if (success) {
            outputText.setText("Save Added!");
        } else {
            outputText.setText("No Goal-Keeper with given jersey number and team name found");
        }
        }

    }

    // ----------- 5 ------------------

    /**
     * Adds a yellow card to a specific player.
     * Prompts the user to enter the team and jersey number of the player.
     * Calls '.Data.addYellowCardToPlayer()' to update the player's yellow card count by 1.
     * Displays a success message if the yellow card was added or an error message if the player was not found.
     */
    private void menuAddYellowCardToPlayer() {
        errorForInput.setText("");
        if (getPlayerInfo() !=null){

        int jersey =Integer.parseInt(getPlayerInfo()[2].trim()) ;   // Get valid jersey number
        String team = getPlayerInfo()[1].trim(); // Get team name

        if (jersey == INVALID){
            return;
        }
        // Prompt user to enter team name and jersey number of the player
        boolean success = data.addYellowCardToPlayer(team, jersey);
        errorForInput.setText("");


        // Display result message
        if (success) {
            outputText.setText("Yellow Card Added!");
        } else {
            outputText.setText("Player not found");
        }
        }

    }

    // ----------- 6 -----------------

    /**
     * Adds a red card to a specific player
     * Prompts the user to enter the team and jersey number of the player
     * Calls 'Data.addRedCardToPlayer()' to update the player's red card count by 1.
     * Displays a success message if the red card was added or an error message if the player was not found.
     */
    private void menuAddRedCardToPlayer() {

        errorForInput.setText("");
        if (getPlayerInfo() !=null){

        int jersey =Integer.parseInt(getPlayerInfo()[2].trim()) ;   // Get valid jersey number
        String team = getPlayerInfo()[1].trim(); // Get team name

        if (jersey == INVALID){
            return;
        }
        errorForInput.setText("");

        // Prompt user to enter team name and jersey number of the player
        boolean success = data.addRedCardToPlayer(team, jersey);


        // Display result message
        if (success) {
            outputText.setText("Red Card Added!");
        } else {
            outputText.setText("Player not found");
        }
        }
    }

    // -------- 8 ------------

    /**
     * Displays data for a specific player.
     * Prompts the user to enter the team name and jersey number.
     * Calls 'Data.getPlayerData()' to retrieve player details.
     * Prints the player's data if found, otherwise displays an error message.
     */
    private void menuPrintPlayerData() {
        errorForInput.setText("");

        // Prompt user to enter jersey number
        int jerseyNumber = Integer.parseInt(getPlayerInfo()[2].trim());

        // Prompt user to enter team name
        String teamName = getPlayerInfo()[1];

        if (teamName == null || jerseyNumber == INVALID){
            return;
        }
        errorForInput.setText("");

        // Fetch player's data from the Data class
        String PlayerData = data.getPlayerData(teamName, jerseyNumber);

        // Check if the player exists and display data
        if (PlayerData == null) {
            errorForInput.setText("");
            outputText.setText("No player with given jersey number and team name found!");
        }
        else
            outputText.setText(PlayerData);
    }

    // ----------- 9 ---------------

    /**
     * Finds and displays a recommended player and goalkeeper duo.
     * Calls 'Data.getPlayerWithMaximumGoals()' to find the player with the most goals.
     * Calls 'Data.getBestGoalkeeper()' to find the goalkeeper with the most saves.
     * Displays the duo if found, otherwise shows an error message.
     */
    private void getRecommendedDuo() {
        errorForInput.setText("");

        StringBuilder outputString = new StringBuilder();

        // Retrieve the player with the most goals
        Player playerWithMaxGoal = data.getPlayerWithMaximumGoals();

        // Retrieve the goalkeeper with the most saves
        Player goalkeeper = data.getBestGoalkeeper();

        // If either player or goalkeeper is missing, display an error message
        if (playerWithMaxGoal == null || goalkeeper == null) {
            outputText.setText("Not enough players!");
        } else if (goalkeeper.equals(playerWithMaxGoal)) {
            outputText.setText("Duo cannot be formed as they have the same Jersey number!");
        }
        // If both a player and a goalkeeper are found, display them
        else {
            FieldPlayer fieldPlayer = (FieldPlayer) playerWithMaxGoal;
            GoaliePlayer goaliePlayer = (GoaliePlayer) goalkeeper;
            outputString.append("DUO FOUND\n").append("Field Player: ").append(fieldPlayer.getName()).append(" GOALS: ")
                    .append(fieldPlayer.getGoalScored()).append("\nGoal-Keeper:  ").append(goaliePlayer.getName()).
                    append(" SAVES: ").append(goaliePlayer.getSaveScore());
            outputText.setText(outputString.toString());
        }
    }

    // ------------- 10 --------------

    /**
     * Displays all players from a specific team.
     * Prompts the user to enter the team name.
     * Calls 'Data.getAllPlayersOnTeam()' to retrieve a list of players.
     * Prints the names and jersey numbers of the players  of that team if found, otherwise shows an error message.
     */
    private void printPlayersInTeam() {

        errorForInput.setText("");

        // Prompt user to enter a team name
        errorForInput.setText("");
        ArrayList<Player> players = data.getAllPlayersOnTeam(teamChoice.getValue());

        // Check if there are any players on the team
        if (players != null) {
            StringBuilder output = new StringBuilder();
            output.append("NAME                JERSEY\n");
            output.append("--------------------------\n");

            // Loop through players ArrayList and display their name and jersey number
            for (Player player : players) {
                output.append(String.format("%-20s%-6s\n", player.getName(), player.getJerseyNumber()));
            }
            outputText.setText(output.toString());
        } else {
            outputText.setText("Can not find any team with given name. Try again!");
        }
    }


    // ----------- 11 ---------------

    /**
     * Displays the top three players with the most assists.
     * Calls 'Data.getThreePlayersWithMostAssists()' to retrieve the top players.
     * Prints their names, teams, jersey numbers, and assist counts.
     * If no players are found, displays an error message.
     */
    private void menuPrintThreePlayersWithMostAssists() {
        errorForInput.setText("");
        // Retrieve the top three players with the most assists
        ArrayList<FieldPlayer> PlayersWithMostAssists = data.getThreePlayersWithMostAssists();

        // Check if there are no players with assists
        if (PlayersWithMostAssists.isEmpty()) {
            outputText.setText("No players to display with most assists.");
            return;
        }
        clear();
        GridPane grid = new GridPane();
        outputTextPane.getChildren().add(grid);
        grid.setMaxSize(1000,350);
        grid.setAlignment(Pos.TOP_CENTER);
        // Print table header
        addToOutputTable("Player Name", 0, 0, grid);
        addToOutputTable("Team", 0, 1, grid);
        addToOutputTable("Jersey", 0, 2, grid);
        addToOutputTable("Assists", 0, 3, grid);
        // Loop through players and print their details
        int counter = 1;
        for (FieldPlayer player : PlayersWithMostAssists) {
            addToOutputTable(player.getName(), counter, 0, grid);
            addToOutputTable(player.getTeam(), counter, 1, grid);
            addToOutputTable(Integer.toString(player.getJerseyNumber()), counter, 2, grid);
            addToOutputTable(Integer.toString(player.getAssist()), counter, 3, grid);
            counter++;
        }
    }


    // ------------ 12 ---------------

    /**
     * Displays the top three players with the highest combined red and yellow card count.
     * Calls 'Data.getThreePlayersWithMostFouls()' to retrieve the Players.
     * Prints their names and total foul count.
     * If no Players are found, displays an error message.
     */
    private void printThreePlayersWithMostFouls() {

        errorForInput.setText("");

        // Retrieve the top three players with the most fouls
        ArrayList<Player> topFouls = data.getThreePlayersWithMostFouls();
        String outputString = "";

        // Check if there are no players with fouls
        if (topFouls == null || topFouls.isEmpty()) {
            outputText.setText("No players to display with most Fouls.");
            return;
        }
        clear();
        GridPane grid = new GridPane();
        outputTextPane.getChildren().add(grid);
        grid.setMaxSize(1000,350);
        grid.setAlignment(Pos.TOP_CENTER);
        // Print table header
        addToOutputTable("Player Name", 0, 0, grid);
        addToOutputTable("Team", 0, 1, grid);
        addToOutputTable("Jersey", 0, 2, grid);
        addToOutputTable("Fouls", 0, 3, grid);
        // Loop through players and print their details
        int counter = 1;
        for (Player player : topFouls) {
            addToOutputTable(player.getName(), counter, 0, grid);
            addToOutputTable(player.getTeam(), counter, 1, grid);
            addToOutputTable(Integer.toString(player.getJerseyNumber()), counter, 2, grid);
            addToOutputTable(Integer.toString(player.getRedCardScore() + player.getYellowCardScore()), counter, 3, grid);
            counter++;
        }
        outputText.setText(outputString);
    }


    // ----------- 13 ------------

    /**
     * Displays the player with the most goals.
     * Calls 'Data.getPlayerWithMaximumGoals()' to retrieve the player.
     * Prints the player's name and goal count.
     * If no player is found, displays an error message.
     */
    private void menuGetPlayerWithMaximumGoals() {
        errorForInput.setText("");

        // Retrieve the player with the most goals
        Player playerWithMaxGoal = data.getPlayerWithMaximumGoals();
        StringBuilder outputString = new StringBuilder();

        // Check if a player was found
        if (playerWithMaxGoal != null) {
            FieldPlayer fieldPlayer = (FieldPlayer) playerWithMaxGoal;
            outputString.append("NAME: ").append(fieldPlayer.getName()).append("\n").append("GOALS: ").
                    append(fieldPlayer.getGoalScored());
            outputText.setText(outputString.toString());
        } else {
            outputText.setText("No players to display with most goals.");
        }
    }


    // ----------- 14 --------------

    /**
     * Displays the team with the most total goals.
     * Calls 'Data.getTeamWithMaxGoals()' to determine the leading team.
     * Prints the team name and total goals.
     */
    private void menuPrintTeamWithMaxGoals() {
        errorForInput.setText("");

        outputText.setText(data.getTeamWithMaxGoals());
    }


    // ------------ 15 ------------------

    /**
     * Displays the player with the highest goal-to-foul ratio.
     * Calls 'Data.getGreatestGoalFoulRatio()' to retrieve the best player.
     * Prints the player's name and ratio.
     * If no players are found, displays an error message.
     */
    private void printPlayerWithMaxGoalToFoulRatio() {
        errorForInput.setText("");

        // Retrieve the player with the best goal-to-foul ratio
        String output = data.getGreatestGoalFoulRatio();

        // Check if a valid player was not found
        if (output == null) {
            outputText.setText("No players in database!");
            return;
        }

        // Display the player's name and ratio
        outputText.setText(output);
    }

    // -------------- 16 ---------------

    /**
     * Displays all players with a specific jersey number.
     * Prompts the user to enter the jersey number.
     * Calls 'Data.getPlayersWithJerseyNumber()' to find matching players from different teams.
     * Prints the names of players if found, otherwise shows an error message.
     */
    private void printPlayersWithJerseyNumber() {

        try {
            // Prompt user to enter the jersey number
            int jerseyNumber = jerseyChoice.getValue();
            if (jerseyNumber == INVALID) {
                return;
            }
            errorForInput.setText("");

            // Retrieve all players with the specified jersey number
            ArrayList<Player> Players = data.getPlayersWithJerseyNumber(jerseyNumber);

            // Check if there are any players with the given jersey number
            if (Players == null) {
                outputText.setText("No player found with given jersey number number.");
            }

            // Display the header for player data
            else {
                StringBuilder output = new StringBuilder();
                output.append("Player(s) with given jersey number: ").append(jerseyNumber).append("\nNAME:\n");
                // Loop through ArrayList of players that was returned from Data function and print them to console
                for (Player TPlayer : Players) {
                    output.append(TPlayer.getName()).append("\n");
                }
                outputText.setText(output.toString());
            }
        }
        catch (NullPointerException _){
        }
    }

    // ---------------- 17 -----------

    /**
     * Displays data for all players.
     * Calls 'Data.getAllPlayersData()' to retrieve the full player list.
     * Prints player details.
     */
    public void menuPrintAllPlayer() {
        errorForInput.setText("");

        // Retrieve and display all players' data from the Data class
        outputText.setText(data.getAllPlayersData());
    }

    // ---------------- 18 -----------

    /**
     * This method fetches and prints the best field player.
     * It retrieves the best field player from the data and prints it.
     * If no best field player is found, an error message is displayed.
     */
    public void getBestFieldPlayer() {
        errorForInput.setText("");

        // Fetch the best field player from the data
        String fieldPlayer = data.getBestFieldPlayer();

        // If no field player is found, print an error message
        if (fieldPlayer == null)
            outputText.setText("No best Field player found");
        else
            // Otherwise, print the best field player
            outputText.setText(fieldPlayer);
    }

    // ---------------- 19 -----------

    /**
     * This method fetches and prints the best goalkeeper.
     * It retrieves the best goalkeeper from the data and prints it.
     * If no best goalkeeper is found, an error message is displayed.
     */
    public void getBestGoalKeeper() {
        errorForInput.setText("");

        // Fetch the best goalkeeper from the data
        String goaliePlayer = data.getBestGoalKeeper2();

        // If no goalkeeper is found, print an error message
        if (goaliePlayer == null)
            outputText.setText("No best Goalkeeper found");
        else
            // Otherwise, print the best goalkeeper
            outputText.setText(goaliePlayer);
    }

    // ---------------- 20 -----------

    /**
     * Prompts the user to enter a file name for saving data.
     * Ensures the file name is not empty.
     * Saves the data to the specified file if valid.
     */
    public void save() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Find file to save to");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.csv"));
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("FIFA.csv");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null){
            Writer.save(file, data);
        }
        else {
            outputText.setText("Failed to save data!");
        }
    }

    // ---------------- 21 -----------

    /**
     * Prompts the user to enter a file name for reading data.
     * Loads data from the specified file if valid.
     */
    public void readFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Find file to load from");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("testFile", "*.csv"));
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(new Stage());
        boolean success = Reader.read(file, data);
        if (success) {
            outputText.setText("Data Loaded successfully!");
        } else {
            outputText.setText("Error loading data!");
        }
    }
    /**
     * Alerts the user and confirm if user want to quit.
     * Quits the program if user presses OK button.
     */
    @FXML
    public void exit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        // Check if the user pressed "OK"
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0); // Exit the application
        }
    }

    /**
     * This method gets Team Names dropdown combobox and called Player Team Function according to team selected
     */
    public void teamNames(){
        teamChoice.setOnAction(_ -> {});
        // Store all teams present
        String[] teamN = data.getTeams().keySet().toArray(new String[0]);
        Arrays.sort(teamN);
        //add those teams to combobox
        teamChoice.setItems(FXCollections.observableArrayList(teamN));
        teamChoice.setOnAction(_ -> playerTeam());
    }
    /**
     * This function is used to get drop box to get players to that team
     */
    public void teamPlayers(){
        teamChoice.setOnAction(_ -> {});
        String[] teamN =data.getTeams().keySet().toArray(new String[0]);
        Arrays.sort(teamN);
        teamChoice.setItems(FXCollections.observableArrayList(teamN));
        teamChoice.setOnAction(_ -> printPlayersInTeam());
    }

    /**
     * get players of that team selected and call executable methods to perform particular action
     */
    public void playerTeam(){
        playerChoice.setOnAction(_ -> FXCollections.observableArrayList());
        if (teamChoice.getValue() ==null) {
                errorForInput.setText("Please select a team");
        } else {
            // store Player in teamPlayer Arraylist
            ArrayList<Player> teamPlayers = data.getPlayerOfTeam(teamChoice.getValue());
            if (teamPlayers != null) {
                String[] teamName = new String[teamPlayers.size()];
                for (int i = 0; i < teamPlayers.size(); i++) {
                    //Store in Array to use these value in getPlayerInfo method
                    teamName[i] = teamPlayers.get(i).getName() + " , " + teamPlayers.get(i).getTeam() + " , " + teamPlayers.get(i).getJerseyNumber();
                }
                playerChoice.setItems(FXCollections.observableArrayList(teamName));
                if (playerChoice.getValue() !=null) {
                    playerChoice.setOnAction(_ -> executeMethod());
                    playerChoice.setDisable(false);
                }
                else {
                    errorForInput.setText("Please select Player and press Enter button");
                    errorForInput.setTextFill(Color.RED);
                }
            }
        }
    }
    /**
     * This method helps to select jersey number of player and print all of those
     */
    public void jersey(){
        jerseyChoice.setOnAction( _ -> {});
        // get all players present in data
        ArrayList<Player> allPlayer=data.getAllPlayers();
        //store jersey numbers using set
        Set<Integer> jerseyNumbers=new HashSet<>();
        for (Player player : allPlayer) {
            jerseyNumbers.add(player.getJerseyNumber());
        }
        jerseyChoice.setItems(FXCollections.observableArrayList(jerseyNumbers));
        jerseyChoice.setOnAction(_ -> executeMethod());
    }


    /**
     * All these functions below are used to add textFields and labels to gridPane according to option selected by user.
     */
    public void addTeamField(){
        gridPane.add(teamLabel, 0, 0);
        teamTF.clear();
        gridPane.add(teamTF, 1, 0);
    }
    public void addTeamPlayerDrop(){
        clear();
        teamNames();
        gridPane.add(teamLabel, 0, 1);
        gridPane.add(teamChoice, 0, 2);
        teamChoice.setPromptText("Select Team");
        gridPane.add(playerLabel, 1, 1);
        gridPane.add(playerChoice, 1, 2);
        playerChoice.setPromptText("Select Player");
    }
    public void teamDrop(){
        clear();
        teamPlayers();
        gridPane.add(teamLabel, 0, 1);
        gridPane.add(teamChoice, 1, 1);
        teamChoice.setPromptText("Select Team");
    }
    public void jerseyDrop(){
        clear();
        jersey();
        gridPane.add(new Label("Jersey Number"), 0, 1);
        gridPane.add(jerseyChoice, 1, 1);

    }
    public void addPlayerField(){
        gridPane.add(playerLabel, 0, 1);
        playerTF.clear();
        gridPane.add(playerTF, 1, 1);
    }

    public void addJerseyField(){
        gridPane.add(jerseyLabel, 0, 2);
        jerseyTF.clear();
        gridPane.add(jerseyTF, 1, 2);
    }

    public void addRoleField(){
        gridPane.add(roleLabel, 0, 3);
        roleTF.clear();
        gridPane.add(roleTF, 1, 3);
    }

    public void addAllFields(){
        addTeamField();
        addPlayerField();
        addJerseyField();
        addRoleField();
    }

    public void addToOutputTable(String data, int row, int col, GridPane grid) {
        Label text = new Label(data);
        text.setStyle("-fx-border-color: #37dde6; -fx-border-width: 0; -fx-alignment: center; -fx-padding: 10;");
        grid.add(text, col, row);
    }

    /**
     * Clears output terminal
     */
    public void clear(){
        outputTextPane.getChildren().removeIf(node -> node instanceof GridPane);
        gridPane.getChildren().clear();
        outputText.setText("");
    }

    /**
     * These methods help to figure out how the structure of gridPane will look like according to particular option selected
     */
    public void setFields(){
        String option = menuOptions.getValue();
        switch (option){
            case "Add a new player" -> {
                clear();
                addAllFields();
            }
            case "Add a goal to player", "Add an assist to a player", "Add a save to a player",
                 "Add Yellow card to a player", "Add a Red card to a player", "Print player's data" -> {
                clear();
                addTeamPlayerDrop();
            }

            case "Print all the players of a specific team" -> {
                clear();
                teamDrop();
            }
            case "Print players with a specific Jersey Number" -> {
                clear();
                jerseyDrop();

            }
            case "Print top 3 players with the most assists",
                 "Print top 3 players with the most fouls (Red cards + Yellow Cards)",
                 "Print the player who scored the most goals in the tournament",
                 "Print the team that has the most combined goals",
                 "Print the player that has the highest goal/foul ratio", "Print all players",
                 "Get a recommended duo (Goalkeeper and Player)",
                 "Get best Field player", "Get best Goal-keeper" -> clear();
        }
    }

    /**
     * Shows an informational alert with details about the application.
     */
    @FXML
    public void viewAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setContentText("""
                Author: Armaan Gill
                           Justin Tan
                           Simrandeep Kaur
                Email:  armaan.gill1@ucalgary.ca
                           justin.tan1@ucalgary.ca
                           simrandeep.simrandee@ucalgary.ca
                Version: 1.3
                This is a Player Data Tracker for FIFA""");
        alert.show();
    }

    /**
     * This function displays all teams present
     */
    @FXML
    public void allTeams() {
        String stringBuilder = "**************************\nTeams:\n" +
                data.getAllTeams() +
                "**************************";
        outputText.setText("");
        outputText.setText(stringBuilder);
    }

}
