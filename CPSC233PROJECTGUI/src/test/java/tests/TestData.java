package tests;

import player.FieldPlayer;
import player.Player;
import util.Reader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import data.Data;

/**
 * TestData class contains JUnit tests for the Data class.
 * It tests various functionalities such as adding players, modifying statistics,
 * retrieving player data, and ranking players based on performance metrics.
 *
 * @author Simrandeep Kaur, Armaan Singh Gill, Justin Tan
 * Tutorial - T06
 * Date March 20, 2025
 * @version 1.1
 */

class TestData {
    /**
     * Create test datasets for testing purposes
     */

    private Data addTestPlayers() {
        Data data = new Data();
        data.addPlayer("John Doe", "Eagles", 10, "G");
        data.addPlayer("John Cena", "USA", 10, "P");
        data.addPlayer("Xi", "China", 19, "G");
        data.addPlayer("Donald", "USA", 2, "P");
        data.addGoalToPlayer("Usa", 10);
        data.addGoalToPlayer("China", 19);
        data.addRedCardToPlayer("USA", 10);
        data.addYellowCardToPlayer("USA", 10);
        data.addYellowCardToPlayer("USA", 10);
        data.addRedCardToPlayer("CHINA", 19);
        data.addRedCardToPlayer("CHINA", 19);
        data.addRedCardToPlayer("EAGLES", 10);
        data.addGoalToPlayer("CHINA", 19);
        data.addGoalToPlayer("CHINA", 19);
        data.addGoalToPlayer("USA", 10);
        data.addSaveToPlayer("CHINA", 19);
        data.addSaveToPlayer("CHINA", 19);
        data.addSaveToPlayer("CHINA", 19);
        return data;
    }

    private Data addTestPlayersSet2() {
        Data data = new Data();
        data.addPlayer("Eagles Player", "Eagles", 10, "G");
        data.addPlayer("USA Player", "USA", 10, "G");
        data.addPlayer("China Player", "China", 19, "P");
        data.addPlayer("USA Other Player", "USA", 2, "P");

        data.addRedCardToPlayer("USA", 2);
        data.addYellowCardToPlayer("USA", 2);
        data.addYellowCardToPlayer("USA", 2);
        data.addRedCardToPlayer("USA", 2);
        data.addRedCardToPlayer("USA", 10);
        data.addRedCardToPlayer("USA", 10);
        data.addRedCardToPlayer("Eagles", 10);
        data.addGoalToPlayer("USA", 2);
        data.addGoalToPlayer("USA", 2);
        data.addGoalToPlayer("USA", 2);
        data.addGoalToPlayer("USA", 2);
        data.addGoalToPlayer("USA", 2);
        data.addGoalToPlayer("USA", 2);
        data.addGoalToPlayer("CHINA", 19);
        data.addSaveToPlayer("USA", 2);
        data.addSaveToPlayer("USA", 2);
        data.addSaveToPlayer("CHINA", 19);
        data.addSaveToPlayer("EAGLES", 10);
        data.addSaveToPlayer("EAGLES", 10);
        data.addSaveToPlayer("EAGLES", 10);
        return data;
    }

    /**
     * Test  for AddPlayer method
     */
    // Test1  we add one Player and expect true that it is added in Arraylist and check with size that should be 1 after adding one Player
    @Test
    public void testAddNewPlayer_Test1() {
        Data data = new Data();
        boolean expected = true;
        assertEquals(expected, data.addPlayer("John Doe", "Eagles", 10, "G"));
        assertEquals(1, data.getAllPlayersOnTeam("Eagles").size());
    }

    // Test2 We try to Add 2 players with same name in same team
    @Test
    public void testAddNewPlayer_Duplicate() {
        Data data = new Data();
        boolean Expected = false;
        data.addPlayer("John Doe", "Eagles", 10, "G");
        assertEquals(Expected, data.addPlayer("John Doe", "Eagles", 10, "F"));
        assertEquals(1, data.getAllPlayersOnTeam("Eagles").size());//test
    }

    // Test3 We add 2 players with same name and jersey number in different teams
    @Test
    public void testAddNewPlayer_Test3() {
        Data data = new Data();
        boolean expected = true;
        assertEquals(expected, data.addPlayer("John Doe", "Eagles", 10, "F"));
        assertFalse(data.addPlayer("John Doe", "Eagles", 10, "G"));

        assertEquals(1, data.getAllPlayersOnTeam("Eagles").size());
        boolean Expected = true;
        assertEquals(Expected, data.getTeams().containsKey("EAGLES"));
        assertFalse(data.getTeams().containsKey("FALCONS"));
        boolean Expected1 = false;
        assertEquals(Expected1, data.getTeams().containsKey("Flames"));
    }

    // Test4 Trying to add 2 Players with same jersey number in same team.
    @Test
    public void testAddNewPlayer_Test4() {
        Data data = new Data();
        assertTrue(data.addPlayer("John Doe", "Eagles", 10, "G"));
        assertFalse(data.addPlayer("john Tam", "eagles", 10, "G"));
        assertEquals(1, data.getAllPlayersOnTeam("Eagles").size());
    }

    // Test 5 Add Player with same name with different jersey number in same team.
    @Test
    public void testAddNewPlayer_Test5() {
        Data data = new Data();
        assertTrue(data.addPlayer("John Doe", "Eagles", 10, "F"));
        assertTrue(data.addPlayer("John Doe", "Eagles", 11, "F"));
        assertEquals(2, data.getAllPlayersOnTeam("Eagles").size());
    }

    //     -----------------------------------------------------------------------------------------------------------
    //Test Cases for ADD GOAL Player
    @Test
    void addGoalToPlayer_Test1() {
        Data data = new Data();
        //adding 4 Players to different team
        data.addPlayer("John Doe", "Eng", 10, "F");
        data.addPlayer("John Cena", "aus", 10, "F");
        data.addPlayer("Xi", "China", 19, "F");
        data.addPlayer("Donald", "USA", 2, "F");
        data.addPlayer("Not a Field Player", "USA", 6, "G");

        assertTrue(data.addGoalToPlayer("eng", 10));
        assertEquals(1, data.getGoals("eng", 10));// True as we found that team and jersey
        assertFalse(data.addGoalToPlayer("eng", 11));
        assertFalse(data.addGoalToPlayer("USA", 6));
        assertTrue(data.addGoalToPlayer("aus", 10));
        assertTrue(data.addGoalToPlayer("aus", 10));
        assertEquals(2, data.getGoals("aus", 10));// True as we found that team and jersey
        assertFalse(data.addGoalToPlayer("mex", 11));// False as we do not have Mex team.
    }

    @Test
    void addAssistToPlayer_Test() {
        Data data = new Data();
        //Add players to different teams
        data.addPlayer("John Doe", "Eng", 10, "F");
        data.addPlayer("John Cena", "aus", 10, "F");
        data.addPlayer("Xi", "China", 19, "F");
        data.addPlayer("Donald", "USA", 2, "F");
        //Assert - Testing valid assist addition
        assertTrue(data.addAssistToPlayer("eng", 10)); //Add assist to John Doe
        assertEquals(1, data.getAssists("eng", 10)); // Verify assist count is updated
        // Edge Case - Player with given jersey number does not exist
        assertFalse(data.addAssistToPlayer("eng", 11)); // No player with jersey 11 in Eng
        //Assert - Testing multiple assists for a player
        assertTrue(data.addAssistToPlayer("aus", 10)); // Add assist to John Cena
        assertTrue(data.addAssistToPlayer("aus", 10)); // Add another assist
        assertTrue(data.addAssistToPlayer("aus", 10)); // Add a third assist
        assertEquals(3, data.getAssists("aus", 10)); //Verify total assist count
        // Edge Case - team does not exist
        assertFalse(data.addAssistToPlayer("mex", 11)); // mes team does not exist
    }

    @Test
    void addSaveToPlayer_Test() {
        Data data = new Data();
        data.addPlayer("John Doe", "Eng", 10, "G");
        data.addPlayer("John Cena", "aus", 10, "G");
        data.addPlayer("Xi", "China", 19, "G");
        data.addPlayer("Donald", "USA", 2, "G");
        data.addPlayer("Not a goalie", "USD", 5, "F");

        assertTrue(data.addSaveToPlayer("eng", 10));
        assertTrue(data.addSaveToPlayer("eng", 10));
        assertTrue(data.addSaveToPlayer("eng", 10));
        assertTrue(data.addSaveToPlayer("eng", 10));
        assertEquals(4, data.getSaves("eng", 10));
        assertFalse(data.addSaveToPlayer("eng", 11));
        assertFalse(data.addSaveToPlayer("USD", 5));
        assertTrue(data.addSaveToPlayer("aus", 10));
        assertEquals(1, data.getSaves("aus", 10));
        assertFalse(data.addSaveToPlayer("mex", 11));


    }

    @Test
    void addYellowCardToPlayer_test() {
        Data data = new Data();
        //Add players to different teams
        data.addPlayer("John Doe", "Eng", 10, "F");
        data.addPlayer("John Cena", "aus", 10, "G");
        data.addPlayer("Xi", "China", 19, "F");
        data.addPlayer("Donald", "USA", 2, "G");
        //Assert - Testing yellow card addition for a valid Player
        assertTrue(data.addYellowCardToPlayer("eng", 10)); // Add yellow card to John Doe
        assertEquals(1, data.getYellowCards("eng", 10)); // Verify yellow card count
        assertTrue(data.addYellowCardToPlayer("aus", 10)); // Add yellow card to John Cena
        assertEquals(1, data.getYellowCards("aus", 10)); // Verify yellow card count
        assertTrue(data.addYellowCardToPlayer("aus", 10)); // Add another yellow card
        assertEquals(2, data.getYellowCards("aus", 10)); // Verify yellow card count
        assertTrue(data.addYellowCardToPlayer("aus", 10)); // Add another yellow card
        assertEquals(3, data.getYellowCards("aus", 10)); // Verify yellow card count
        // Edge Case - Attempting to add a yellow card to a non-existent player
        assertFalse(data.addYellowCardToPlayer("aus", 11)); // No Player with jersey 11 in Australia
        // Edge Case - Attempting to add a yellow card to a non-existent team
        assertFalse(data.addYellowCardToPlayer("mex", 11)); // mex team does not exist
    }

    @Test
    void addRedCardToPlayer_Test() {
        Data data = new Data();
        data.addPlayer("John Doe", "Eng", 10, "F");
        data.addPlayer("John Cena", "aus", 10, "G");
        data.addPlayer("Xi", "China", 19, "F");
        data.addPlayer("Donald", "USA", 2, "G");

        assertTrue(data.addRedCardToPlayer("eng", 10));
        assertEquals(1, data.getRedCards("eng", 10));
        assertFalse(data.addRedCardToPlayer("eng", 11));
        assertTrue(data.addRedCardToPlayer("china", 19));
        assertEquals(1, data.getRedCards("china", 19));
        assertTrue(data.addRedCardToPlayer("china", 19));
        assertEquals(2, data.getRedCards("china", 19));
        assertTrue(data.addRedCardToPlayer("china", 19));
        assertEquals(3, data.getRedCards("china", 19));
        assertTrue(data.addRedCardToPlayer("china", 19));
        assertEquals(4, data.getRedCards("china", 19));
        assertFalse(data.addRedCardToPlayer("mex", 11));


    }

    @Test
    void changeJerseyNumber_test() {
        Data data = addTestPlayers();
        assertTrue(data.changeJerseyNumber("usa", 2, 5));
    }

    @Test
    void getPlayerData_Test() {
        Data data = new Data();
        data.addPlayer("Lionel Messi", "Argentina", 10, "G");
        data.addPlayer("Cristiano Ronaldo", "Portugal", 7, "F");
        data.addPlayer("Jr", "Brazil", 10, "F");

        // Get Player data
        String expectedOutput = "Name: LIONEL MESSI    Team: ARGENTINA  Jersey Number: 10   " +
                "Position: Goal Keeper    Yellow Cards: 0    Red Cards: 0    Saves: 0   ";

        String actualOutput = data.getPlayerData("Argentina", 10);

        // Assert - Check if the method returns the expected output
        assertNotNull(actualOutput, "Expected non-null output.");
        assertEquals(expectedOutput, actualOutput);

        // Negative test - Player does not exist
        assertNull(data.getPlayerData("Spain", 6), "Should return null for a non-existent Player.");
    }


    @Test
    void getTop3Assists() {
        Data data = new Data();
        // Arrange - Add test Players with different assist values
        data.addPlayer("Messi", "Argentina", 10, "F");
        data.addPlayer("Ronaldo", "Portugal", 7, "F");
        data.addPlayer("Jr", "Brazil", 10, "F");
        data.addPlayer("Kevin", "Belgium", 17, "F");
        data.addPlayer("Bruno", "Portugal", 8, "F");

        // Add assists to the Players
        data.addAssistToPlayer("Argentina", 10); // Messi - 1 Assist
        data.addAssistToPlayer("Argentina", 10); // Messi - 2 Assists
        data.addAssistToPlayer("Portugal", 7); // Ronaldo - 1 Assist
        data.addAssistToPlayer("Belgium", 17); // De Bruyne - 1 Assist
        data.addAssistToPlayer("Belgium", 17); // De Bruyne - 2 Assists
        data.addAssistToPlayer("Belgium", 17); // De Bruyne - 3 Assists
        data.addAssistToPlayer("Portugal", 8); // Fernandes - 1 Assist
        data.addAssistToPlayer("Portugal", 8); // Fernandes - 2 Assists
        data.addAssistToPlayer("Portugal", 8); // Fernandes - 3 Assists
        data.addAssistToPlayer("Portugal", 8); // Fernandes - 4 Assists

        // Act - Get top 3 Players with most assists
        ArrayList<FieldPlayer> top3Players = data.getThreePlayersWithMostAssists();

        //Debugging Prints
        System.out.println("Top 3 Players with Most Assists:");
        for (FieldPlayer player : top3Players) {
            System.out.println(player.getName() + " - Assists: " + player.getAssist());
        }

        // Assert - Check if the correct Players are in the top 3
        assertNotNull(top3Players, "Expected non-null result.");
        assertEquals(3, top3Players.size(), "Should return exactly 3 Players.");

        //Expected top 3 Players (sorted)
        assertEquals("BRUNO", top3Players.get(0).getName());
        assertEquals("KEVIN", top3Players.get(1).getName());
        assertEquals("MESSI", top3Players.get(2).getName());

        //Check assist values
        assertEquals(4, top3Players.get(0).getAssist()); // Bruno - 4 assists
        assertEquals(3, top3Players.get(1).getAssist()); // De Bruyne - 3 assists
        assertEquals(2, top3Players.get(2).getAssist());
    }

    @Test
    void getThreePlayersMostFouls() {
        Data data = addTestPlayers();
        ArrayList<Player> players = data.getThreePlayersWithMostFouls();
        assert players != null;
        String actual1 = players.get(0).getName();
        String actual2 = players.get(1).getName();
        String actual3 = players.get(2).getName();
        String expected1 = "JOHN CENA";
        String expected2 = "XI";
        String expected3 = "JOHN DOE";
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
    }

    @Test
    void getThreePlayersMostFoulsSet2() {
        Data data = addTestPlayersSet2();
        ArrayList<Player> players = data.getThreePlayersWithMostFouls();
        assert players != null;
        String actual1 = players.get(0).getName();
        String actual2 = players.get(1).getName();
        String actual3 = players.get(2).getName();
        String expected1 = "USA OTHER PLAYER";
        String expected2 = "USA PLAYER";
        String expected3 = "EAGLES PLAYER";
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
    }

    @Test
    void retrievePlayerMostGoals() {
        Data data = addTestPlayers();
        Player player = data.getPlayerWithMaximumGoals();
        assert player != null;
        String actual = player.getName();
        String expected = "JOHN CENA";
        assertEquals(expected, actual);
    }

    @Test
    void getTeamMaxGoals() {
        Data data = new Data();
        // Arrange - Add Players with goals to different teams
        data.addPlayer("Messi", "Argentina", 10, "F");
        data.addPlayer("Ronaldo", "Portugal", 7, "F");
        data.addPlayer("Jr", "Brazil", 10, "F");

        data.addGoalToPlayer("Argentina", 10); // 1 goal for Argentina
        data.addGoalToPlayer("Argentina", 10); // 2 goals for Argentina
        data.addGoalToPlayer("Portugal", 7);   // 1 goal for Portugal
        data.addGoalToPlayer("Brazil", 10);    // 1 goal for Brazil

        // Act - Get the team with maximum goals
        String expectedOutput = "ARGENTINA";
        String actualOutput = data.getTeamWithMaxGoals();

        // Assert - Check if the method returns the expected output
        assertNotNull(actualOutput, "Expected non-null output.");
        assertEquals(expectedOutput, actualOutput);

    }

    @Test
    void checkGreatestGoalFoulRatio() {
        Data data = addTestPlayers();
        String actual = data.getGreatestGoalFoulRatio();
        String expected = "NAME: JOHN CENA GOAL/FOUL ratio: 2/3";
        assertEquals(expected, actual);
    }

    @Test
    void checkGreatestGoalFoulRatioSet2() {
        Data data = addTestPlayersSet2();
        String actual = data.getGreatestGoalFoulRatio();
        String expected = "NAME: CHINA PLAYER GOAL/FOUL ratio: 1/0";
        assertEquals(expected, actual);
    }

    @Test
    void getPlayersWithJerseyNumber2() {
        Data data = addTestPlayers();
        ArrayList<Player> players = data.getPlayersWithJerseyNumber(10);
        assert players != null;
        String actual1 = players.get(0).getName();
        String actual2 = players.get(1).getName();
        String expected1 = "JOHN CENA";
        String expected2 = "JOHN DOE";
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void getPlayersWithJerseyNumber0() {
        Data data = addTestPlayers();
        ArrayList<Player> players = data.getPlayersWithJerseyNumber(99);
        assertNull(players);
    }

    @Test
    void getMostSaves() {
        Data data = addTestPlayers();
        Player player = data.getBestGoalkeeper();
        assert player != null;
        String expected = player.getName();
        String actual = "XI";
        assertEquals(actual, expected);
    }

    @Test
    void getMostSavesSet2() {
        Data data = addTestPlayersSet2();
        Player player = data.getBestGoalkeeper();
        assert player != null;
        String expected = player.getName();
        String actual = "EAGLES PLAYER";
        assertEquals(actual, expected);
    }

    @Test
    void retrieveAllPlayersOnTeam() {
        Data data = addTestPlayers();
        ArrayList<Player> players = data.getAllPlayersOnTeam("CHINA");
        assert players != null;
        String actual = players.getFirst().getName();
        String expected = "XI";
        assertEquals(expected, actual);
    }

    @Test
    void retrieveAllPlayersOnTeamSet2() {
        Data data = addTestPlayersSet2();
        ArrayList<Player> Players = data.getAllPlayersOnTeam("USA");
        assert Players != null;
        String actual1 = Players.get(0).getName();
        String actual2 = Players.get(1).getName();
        String expected1 = "USA PLAYER";
        String expected2 = "USA OTHER PLAYER";
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void checkPlayerExist() {
        Data data = addTestPlayers();
        boolean actual = data.checkPlayerExist(2, "USA");
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void checkPlayerNotExist() {
        Data data = addTestPlayers();
        boolean actual = data.checkPlayerExist(5, "USA");
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    void getBestFieldPlayer_Test() {
        Data data = new Data();
        // Arrange - Add field players to the data
        data.addPlayer("Messi", "Argentina", 10, "F");
        data.addPlayer("Ronaldo", "Portugal", 7, "F");
        data.addPlayer("Neymar", "Brazil", 11, "F");
        data.addPlayer("Mbappe", "France", 8, "F");

        // Add some statistics to field players
        data.addGoalToPlayer("Argentina", 10);  // Messi - 1 Goal
        data.addAssistToPlayer("Argentina", 10); // Messi - 1 Assist
        data.addGoalToPlayer("Portugal", 7);   // Ronaldo - 1 Goal
        data.addAssistToPlayer("Portugal", 7);  // Ronaldo - 1 Assist
        data.addGoalToPlayer("Brazil", 11);     // Neymar - 1 Goal
        data.addAssistToPlayer("Brazil", 11);   // Neymar - 1 Assist
        data.addAssistToPlayer("Brazil", 11);   // Neymar - 1 Assist
        data.addGoalToPlayer("France", 8);      // Mbappe - 1 Goal

        // Act - Get the best field player based on performance
        String bestFieldPlayer = data.getBestFieldPlayer();

        // Assert - Check if the method returns the expected player (based on a specific ranking or metric)
        assertNotNull(bestFieldPlayer, "Best field player should not be null.");
        assertEquals("Name: NEYMAR          Team: BRAZIL     Jersey Number: 11   " +
                        "Position: Field Player   Yellow Cards: 0    Red Cards: 0    Goals: 1    Assists: 2   ",
                bestFieldPlayer, "Expected Messi to be the best field player.");
    }

    @Test
    void getBestGoalkeeper_Test() {
        Data data = new Data();
        // Arrange - Add goalkeepers to the data
        data.addPlayer("De Gea", "Manchester United", 1, "G"); // Goalkeeper
        data.addPlayer("Alisson", "Liverpool", 13, "G");      // Goalkeeper

        // Add some statistics to goalkeepers
        data.addSaveToPlayer("Manchester United", 1);  // De Gea - 1 Save
        data.addSaveToPlayer("Manchester United", 1);  // De Gea - 2 Saves
        data.addSaveToPlayer("Liverpool", 13);         // Alisson - 5 Saves
        data.addSaveToPlayer("Liverpool", 13);         // Alisson - 6 Saves
        data.addYellowCardToPlayer("Manchester United", 1); //De Gea - 1 Foul
        // Act - Get the best goalkeeper based on saves or other criteria
        String bestGoalkeeper = data.getBestGoalKeeper2();

        // Assert - Check if the method returns the expected goalkeeper
        assertNotNull(bestGoalkeeper, "Best goalkeeper should not be null.");
        assertEquals("Name: ALISSON         Team: LIVERPOOL  Jersey Number: 13   Position: Goal Keeper    " +
                "Yellow Cards: 0    Red Cards: 0    Saves: 2   ", bestGoalkeeper, "Expected Alisson to be " +
                "the best goalkeeper.");
    }

    @Test
    void getBestFieldPlayerTest() {
        Data data = new Data();
        String bestFieldPlayer = data.getBestFieldPlayer();
        assertNull(bestFieldPlayer, "Best field player should be null.");
    }

    @Test
    void getBestGoalkeeperTest() {
        Data data = new Data();
        String bestGoalkeeper = data.getBestGoalKeeper2();
        assertNull(bestGoalkeeper, "Best goalkeeper should be null.");
    }

    @Test
    void reader() {

        Data data = new Data();
        File targetFile = new File("testfile.csv");
        boolean success = Reader.read(targetFile, data);
        assertTrue(success);
        assertEquals(2, data.getAllPlayersOnTeam("team1").size());
        assertEquals("Fieldplayer2", data.getPlayerWithMaximumGoals().getName());
        assertEquals(4, data.getSaves("team2", 14));
        assertEquals(20, data.getGoals("HIteam", 1));
        assertEquals(0, data.getRedCards("team1", 19));
        assertEquals(1, data.getPlayersWithJerseyNumber(1).size());
    }
}
