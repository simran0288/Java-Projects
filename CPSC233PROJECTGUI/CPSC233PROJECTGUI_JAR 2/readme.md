# Fifa Stats Tracker

A stats tracker that allows users to enter in data for soccer players! 
Authors: Justin Tan, Simrandeep Kaur, Armaan Singh Gill

## what this Application does
- We let user store data of teams and players.
- User need to enter the information of player:(Name,Team Name,Jersey Number, Role: Goalie or Field Player)
- User can add Goal,save,assist or fouls according to their needs
- User gets various options to view data according to their need .
- user can see the best player, the best goalie or other information about player
# Program Information
You can run the program with 1 command line argument specifying a data file to load from.
Various options are displayed in the dropdown menu that allow users to 
interact with the data.


# Setup
Run the project through an IDE such as Intellij or follow the steps to 
run the program through the command line (make sure to replace the path
with your JavaFX installation path):
```declarative
java --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules
javafx.controls,javafx.fxml app.Main
```

Or if you are running through the .JAR file:
```declarative
java --module-path "C:\Program Files\Java\javafx-sdk-23.0.2\lib" --add-modules
javafx.controls,javafx.fxml -jar CPSC233PROJECTGUI.jar
```
If you want to run the program with a specified save file,
place the name of the save file directly after the program name.


# Code Information
### Main.java
 - Where the program is launched 
### MainController.java
- Links JavaFX gui with event driven actions
- Calls associated backend functions
### Data.java
- All actions that manipulate and return data are placed here
- Each dataset is represented by a custom Data object
### Player.java/Team.java
- Each player/team is represented by an associated object
### Reader.java/Writer.java
- Utility functions to read and write save files

