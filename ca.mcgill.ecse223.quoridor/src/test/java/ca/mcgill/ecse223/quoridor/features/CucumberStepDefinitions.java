package ca.mcgill.ecse223.quoridor.features;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.validator.ValidateWith;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.TOWall;

import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;

import ca.mcgill.ecse223.quoridor.controller.*;
import ca.mcgill.ecse223.quoridor.model.*;

import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;

import ca.mcgill.ecse223.quoridor.model.Game.*;


import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import cucumber.api.PendingException;

public class CucumberStepDefinitions {

	// ***********************************************
	// Background step definitions
	// ***********************************************

	@Given("^The game is not running$")
	public void theGameIsNotRunning() {
		initQuoridorAndBoard();
		createUsersAndPlayers("user1", "user2");
	}
	

	@Given("^The game is running$")
	public void theGameIsRunning() {
		initQuoridorAndBoard();
		ArrayList<Player> createUsersAndPlayers = createUsersAndPlayers("user1", "user2");
		createAndStartGame(createUsersAndPlayers);
	}

	@And("^It is my turn to move$")
	public void itIsMyTurnToMove() throws Throwable {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("The following walls exist:")
	public void theFollowingWallsExist(io.cucumber.datatable.DataTable dataTable) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		// keys: wrow, wcol, wdir
		Player[] players = { quoridor.getCurrentGame().getWhitePlayer(), quoridor.getCurrentGame().getBlackPlayer() };
		int playerIdx = 0;
		int wallIdxForPlayer = 0;
		for (Map<String, String> map : valueMaps) {
			Integer wrow = Integer.decode(map.get("wrow"));
			Integer wcol = Integer.decode(map.get("wcol"));
			// Wall to place
			// Walls are placed on an alternating basis wrt. the owners
			//Wall wall = Wall.getWithId(playerIdx * 10 + wallIdxForPlayer);
			Wall wall = players[playerIdx].getWall(wallIdxForPlayer); // above implementation sets wall to null

			String dir = map.get("wdir");

			Direction direction;
			switch (dir) {
			case "horizontal":
				direction = Direction.Horizontal;
				break;
			case "vertical":
				direction = Direction.Vertical;
				break;
			default:
				throw new IllegalArgumentException("Unsupported wall direction was provided");
			}
			new WallMove(0, 1, players[playerIdx], quoridor.getBoard().getTile((wrow - 1) * 9 + wcol - 1), quoridor.getCurrentGame(), direction, wall);
			if (playerIdx == 0) {
				quoridor.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(wall);
			} else {
				quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
				quoridor.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(wall);
			}
			wallIdxForPlayer = wallIdxForPlayer + playerIdx;
			playerIdx++;
			playerIdx = playerIdx % 2;
		}
		System.out.println();

	}

	@And("I do not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand() {
		// GUI-related feature -- TODO for later
		throw new PendingException();

		// Assert.assertFalse(QuoridorController.getPlayerOfCurrentTurn().hasWallInHand());
	}
	
	@And("^I have a wall in my hand over the board$")
	public void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		// GUI-related feature -- TODO for later
		throw new PendingException();

		// Assert.assertTrue(QuoridorController.getCurrentGrabbedWall().grabbed);
	}
	
	@Given("^A new game is initializing$")
	public void aNewGameIsInitializing() throws Throwable {
		initQuoridorAndBoard();
		ArrayList<Player> players = createUsersAndPlayers("user1", "user2");
		new Game(GameStatus.Initializing, MoveMode.PlayerMove, players.get(0), players.get(1), QuoridorApplication.getQuoridor());
	}

	// ***********************************************
	// Scenario and scenario outline step definitions
	// ***********************************************

	/*
	 * TODO Insert your missing step definitions here
	 * 
	 * Call the methods of the controller that will manipulate the model once they
	 * are implemented
	 * 
	 */

	// ***** InitializeBoard.feature *****
		
	/**
	* @author Barry Chen
	*/
	@When("The initialization of the board is initiated")
	public void initializationOfTheBoardInitiated() {
		throw new PendingException();
	}
	
	/**
	* @author Barry Chen
	*/
	@Then("It shall be white player to move")
	public void whitePlayerToMove() {
		throw new PendingException();
	}
	
	/**
	* @author Barry Chen
	*/
	@And("White's pawn shall be in its initial position")
	public void whitePawnBeAtInitialPosition() {
		throw new PendingException();
	}
	
	/**
	* @author Barry Chen
	*/
	@And("Black's pawn shall be in its initial position")
	public void blackPawnBeAtInitialPosition() {
		throw new PendingException();
	}
	
	/**
	* @author Barry Chen
	*/
	@And("All of White's walls shall be in stock")
	public void allWhiteWallsBeInStock() {
		throw new PendingException();
	}
	
	/**
	* @author Barry Chen
	*/
	@And("All of Black's walls shall be in stock")
	public void allBlackWallsBeInStock() {
		throw new PendingException();
	}
	
	/**
	* @author Barry Chen
	*/
	@And("White's clock shall be counting down")
	public void whiteClockShallBeCoutingDown() {
		throw new PendingException();
	}
	
	/**
	* @author Barry Chen
	*/
	@And("It shall be shown that this is White's turn")
	public void ShownThatIsWhiteTurn() {
		throw new PendingException();
	}
	
	
	// ***** StartNewGame.feature *****
	
	/**
	 * @author Barry Chen
	 */
	@When("A new game is being initialized")
	public void aNewGameIsBeingInitialized() {
		throw new PendingException();
	}
	
	/**
	 * @author Barry Chen
	 */
	@And("White player chooses a username")
	public void whitePlayerChoosesUsername( ) {
		throw new PendingException();
	}
	
	/**
	 * @author Barry Chen
	 */
	@And("Black player chooses a username")
	public void blackPlayerChoosesUsername( ) {
		throw new PendingException();
	}
	
	/**
	 * @author Barry Chen
	 */
	@And("Total thinking time is set")
	public void totalThinkingTimeSet( ) {
		throw new PendingException();
	}
	
	/**
	 * @author Barry Chen
	 */
	@Then("The game shall become ready to start")
	public void gameShallBecomeReadyToStart( ) {
		throw new PendingException();
	}
	
	/*
	Given The game is ready to start
  	When I start the clock
  	Then The game shall be running
  	And The board shall be initialized
	 */
	
	/**
	 * @author Barry Chen
	 */
	@Given("The game is ready to start")
	public void gameIsReadyToStart() {
		throw new PendingException();
	}
	
	/**
	 * @author Barry Chen
	 */
	@When("I start the clock")
	public void startTheClock() {
		throw new PendingException();
	}
	
	/**
	 * @author Barry Chen
	 */
	@Then("The game shall be running")
	public void gameShallBeRunning() {
		throw new PendingException();
	}
	
	/**
	 * @author Barry Chen
	 */
	@And("The board shall be initialized")
	public void boardShallBeInitialized() {
		throw new PendingException();
	}
	
	
	
	// ***** ProvideOrSelectUserName.feature *****

	
	private Color color;
	
	// selecting an existing username
		
	/**
	*@param String color;
	*@author Ada Andrei
	*/

	@Given("Next player to set user name is {string}")
	public void nextPlayerToSetUserNameIsColor(String color) {
		this.color = Color.valueOf(color.toUpperCase());
		throw new PendingException();
	}

	/**
	*@param boolean user; 
	*@author Ada Andrei
	*/

	@And("There is existing user {string}")
	public void existingUser(boolean user) {
		Assert.assertTrue(user); 
	}

	/**
	*@param String user; 
	*@author Ada Andrei
	*/
	@When("The player selects existing {string}") 
	public void playerSelectsExistingUsername(String user) {
		QuoridorController.selectUsername(user);
	}

	/**
	*@param String color;
	*@param String user;
	*@author Ada Andrei
	*/
	@Then("The name of player {string} in the new game shall be {string}")
	public void nameOfPlayerInNewGameShallBeUsername(String color, String user) {
		throw new PendingException();
	}

	// create new user name

	/**
	*@param boolean user;
	*@author Ada Andrei
	*/
	@And("There is no existing user {string}")
	public void noExistingUser(boolean user) {
		Assert.assertFalse(user); 
	}
	
	/**
	*@param String user; 
	*@author Ada Andrei
	*/
	@When("The player provides new user name: {string}")
	public void playerProvidesNewUserName(String user) {
		QuoridorController.createUsername(user);		
	}

	// user name already exists

	/**
	*@author Ada Andrei
	*/
	@Then("The player shall be warned that {string} already exists") 
	public void playerShallBeWarnedThatUsernameAlreadyExists() {
		throw new PendingException(); 
	}
	
	/**
	*@author Ada Andrei
	*/
	@And("Next player to set user name shall be {string}")
	public void nextPlayerToSetUserNameShallBe(String color) {
		throw new PendingException();
	}
	
	// ***** SetTotalThinkingTime.feature *****

	/**
	*@param int mins;
	*@param int secs;
	*@author Ada Andrei
	*/
	@When("{int}:{int} is set as the thinking time")
	public void setAsThinkingTime(int mins, int secs) {
		QuoridorController.setTime(mins, secs); 
	}

	/**
	*@param int mins;
	*@param int secs; 
	*@author Ada Andrei
	*/
	@Then("Both players shall have {int}:{int} remaining time left")
	public void bothPlayersShallHaveRemainingTimeLeft(int mins, int secs) {
		Time time1 = QuoridorController.getBlackPlayer().getTimeRemaining();
		Time time2 = QuoridorController.getWhitePlayer().getTimeRemaining();
		Assert.assertEquals(0, time1.getHours());
		Assert.assertEquals(mins, time1.getMinutes());
		Assert.assertEquals(secs, time1.getSeconds());
		Assert.assertEquals(0, time2.getHours());
		Assert.assertEquals(mins, time2.getMinutes());
		Assert.assertEquals(secs, time2.getSeconds());

	}

	
	// ***** SavePosition.feature *****

	private String fileName;
	private boolean fileOverwriteFlag;

	/**
	 * @param filename Name of file
	 * @author Paul Teng (260862906)
	 */
	@Given("No file {string} exists in the filesystem")
	public void noFileExistsInTheFilesystem(String filename) {
		final File file = new File(filename);
		Assert.assertFalse(file.exists());
	}

	/**
	 * @param filename Name of file
	 * @author Paul Teng (260862906)
	 */
	@When("The user initiates to save the game with name {string}")
	public void userInitiatesToSaveTheGameWithName(String filename) {
		this.fileName = filename;
	}

	/**
	 * @param filename Name of file
	 * @author Paul Teng (260862906)
	 */
	@Then("A file with {string} shall be created in the filesystem")
	public void fileWithFilenameIsCreatedInTheFilesystem(String filename) {
		try {
			// Passing false as argument since file does not exist:
			// we are not overwriting any file (but this argument
			// is ignored in this case)
			Assert.assertTrue(QuoridorController.savePosition(this.fileName, false));
		} catch (IOException ex) {
			Assert.fail("No IOException should happen: " + ex.getMessage());
		}

		final File file = new File(filename);
		Assert.assertTrue(file.exists());
	}

	/**
	 * @param filename Name of file
	 * @author Paul Teng (260862906)
	 */
	@Given("File {string} exists in the filesystem")
	public void fileExistsInTheFilesystem(String filename) {
		final File file = new File(filename);
		Assert.assertTrue(file.exists());
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@And("The user confirms to overwrite existing file")
	public void userConfirmsToOverwriteExistingFile() {
		try {
			// Pass true since we are overwriting a file
			this.fileOverwriteFlag = QuoridorController.savePosition(this.fileName, true);
		} catch (IOException ex) {
			Assert.fail("No IOException should happen: " + ex.getMessage());
		}
	}
	

	/**
	 * @param filename Name of file
	 * @author Paul Teng (260862906)
	 */
	@Then("File with {string} shall be updated in the filesystem")
	public void fileIsUpdatedInTheFilesystem(String filename) {
		// Just a sanity check
		Assert.assertEquals(filename, this.fileName);

		// Actual file-updated check
		Assert.assertTrue(this.fileOverwriteFlag);
	}
	
	/**
	 * @author Paul Teng (260862906)
	 */
	@And("The user cancels to overwrite existing file")
	public void userCancelsToOverwriteExistingFile() {
		try {
			// Pass false since we are not overwriting a file
			this.fileOverwriteFlag = QuoridorController.savePosition(this.fileName, false);
		} catch (IOException ex) {
			Assert.fail("No IOException should happen: " + ex.getMessage());
		}
	}
	
	/**
	 * @param filename Name of file
	 * @author Paul Teng (260862906)
	 */
	@Then("File {string} shall not be changed in the filesystem")
	public void fileIsNotChangedInTheFilesystem(String filename) {
		// Just a sanity check
		Assert.assertEquals(filename, this.fileName);

		// Actual file-updated check
		Assert.assertFalse(this.fileOverwriteFlag);
	}
	
	// ***** LoadPosition.feature *****

	private boolean positionValidFlag;

	/**
	 * @param filename Name of file
	 * @author Paul Teng (260862906)
	 */
	@When("I initiate to load a saved game {string}")
	public void iInitiateToLoadASavedGame(String filename) {
		try {
			this.positionValidFlag = QuoridorController.loadPosition(filename);
		} catch (IOException ex) {
			Assert.fail("No IOException should happen:" + ex.getMessage());
		}
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@And("The position to load is valid")
	public void positionToLoadIsValid() {
		Assert.assertTrue(this.positionValidFlag);
	}

	/**
	 * @param playerName Name of player
	 * @author Paul Teng (260862906)
	 */
	@Then("It shall be {string}'s turn")
	public void itShallBePlayersTurn(String playerName) {
		final TOPlayer player = QuoridorController.getPlayerOfCurrentTurn();
		Assert.assertNotNull(player);
		Assert.assertEquals(playerName, player.getName());
	}

	/**
	 * @param playerName Name of player
	 * @param row Row of player's pawn piece (pawn coordinates)
	 * @param col Column of player's pawn piece (pawn coordinates)
	 * @author Paul Teng (260862906)
	 */
	@And("{string} shall be at {int}:{int}")
	public void playerIsAtRowCol(String playerName, int row, int col) {
		final TOPlayer player = QuoridorController.getPlayerByName(playerName);
		Assert.assertNotNull(player);
		Assert.assertEquals(row, player.getRow());
		Assert.assertEquals(col, player.getColumn());
	}

	/**
	 * @param playerName Name of player
	 * @param orientation Orientation of player's wall piece
	 * @param row Row of player's wall piece (wall coordinates)
	 * @param col Column of player's wall piece (wall coordinates)
	 * @author Paul Teng (260862906)
	 */
	@And("{string} shall have a {word} wall at {int}:{int}")
	public void playerHasOrientedWallAtRowCol(String playerName, String orientation, int row, int col) {
		final List<TOWall> walls = QuoridorController.getWallsOwnedByPlayer(playerName);
		Assert.assertNotNull(walls);

		// Count the walls that satisfy the orientation and location
		// We expect only 1 that matches:
		int matches = 0;
		for (final TOWall wall : walls) {
			if (orientation.equalsIgnoreCase(wall.getOrientation().name())
					&& wall.getRow() == row && wall.getColumn() == col) {
				++matches;
			}
		}
		Assert.assertEquals(1, matches);
	}

	/**
	 * @param remainingWalls the number of walls remaining
	 * @author Paul Teng (260862906)
	 */
	@And("Both players shall have {int} in their stacks")
	public void bothPlayersHaveWallCountInTheirStacks(int remainingWalls) {
		Assert.assertEquals(remainingWalls, QuoridorController.getWallsInStockOfColoredPawn(Color.BLACK));
		Assert.assertEquals(remainingWalls, QuoridorController.getWallsInStockOfColoredPawn(Color.WHITE));
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@And("The position to load is invalid")
	public void positionToLoadIsInvalid() {
		Assert.assertFalse(this.positionValidFlag);
	}
	
	/**
	 * @author Paul Teng (260862906)
	 */
	@Then("The load shall return an error")
	public void loadReturns() {
		Assert.assertFalse(this.positionValidFlag);
	}
	
	
	private List<TOWall> wallStock;
	private TOWall currentWall;
	private TOWallCandidate wallCandidate;
	
	// ***** GrabWall.feature *****
	
		//Start wall placement
	
	/**
	 * @author Alixe Delabrousse (260868412)
	 */
	@Given("I have more walls on stock")
	public void moreWallsOnStock() {
		this.wallStock = QuoridorController.getWallsOwnedByPlayer(QuoridorController.getPlayerOfCurrentTurn().getName());
		Assert.assertNotNull(wallStock);
	}
	
	/**
	 * @author Alixe Delabrousse (260868412)
	 */
	
	@When("I try to grab a wall from my stock")
	public void playerTryToGrabWall() {
		QuoridorController.grabWall(this.wallStock);
		
	}
	
	/**
	 * @author Alixe Delabrousse
	 * @param currentGrabbedWall
	 */
	
	@Then("A wall move candidate shall be created at initial position")
	public void createNewWallMoveCandidate() {
		this.wallCandidate = QuoridorController.createWallCandidateAtInitialPosition();
;		this.currentWall = this.wallCandidate.getAssociatedWall();
	}
	
	/**
	 * @author Alixe Delabrousse (260868412) & Mohamed Mohamed (260855731)
	 */
	
	@And("I shall have a wall in my hand over the board")
	public void wallOverBoard() {
		throw new PendingException();
		//This is a UI related method
	}

	/**
	 * 
	 * @author Alixe Delabrousse (260868412)
	 * 
	 */
	@And("The wall in my hand shall disappear from my stock")
	public void removeWallFromStock() {
		Assert.assertFalse(this.wallStock.contains(this.currentWall));
	}
	
	/**
	 * @author Alixe Delabrousse (260868412)
	 */
	@Given("I have no more walls on stock")
	public void noMoreWallsOnStock() {
		Assert.assertTrue(wallStock.isEmpty());
	}
	
	/**
	 * @author Alixe Delabrousse (260868412)
	 */
	@Then("I shall be notified that I have no more walls")
	public void notifNoMoreWalls() {
		throw new PendingException();
		//UI related method
	}
	
	/**
	 * @author Alixe Delabrousse (260868412)
	 * 
	 */
	@But("I shall have no walls in my hand")
	public void noWallInHand() {
		Assert.assertFalse(QuoridorController.getPlayerOfCurrentTurn().hasWallInHand());
		
	}
	

	
	// ***** MoveWall.feature *****
	
	/**
	 * @author Alixe Delabrousse (260868412) & Mohamed Mohamed (260855731)
	 */
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void wallCandidateExists(String direction, int row, int column) {
		
		Orientation orientation = Orientation.valueOf(direction.toUpperCase());
		
		Assert.assertTrue(this.wallCandidate != null);
		Assert.assertTrue(this.wallCandidate.getOrientation() == orientation);
		Assert.assertTrue(this.wallCandidate.getColumn() == column);
		Assert.assertTrue(this.wallCandidate.getRow() == row);

	}
	
	
	/**
	 * 
	 * @author Alixe Delabrousse (260868412)
	 * 
	 * @param wallCandidate
	 */
	@And("The wall candidate is not at the {string} edge of the board")
	public void wallCandidateNotOnEdge(String side) {
		if (side.equals("up")) {
			Assert.assertTrue(this.wallCandidate.getRow() != 1);
		} else if (side.equals("down")) {
			Assert.assertTrue(this.wallCandidate.getRow() != 9);
		} else if (side.equals("left")) {
			Assert.assertTrue(this.wallCandidate.getColumn() != 1);
		} else if (side.equals("right")) {
			Assert.assertTrue(this.wallCandidate.getColumn() != 9);
		}
	
	}
	

	
	/**
	 * @author Alixe Delabrousse (260868412)
	 */
	@When("I try to move the wall {string}")
	public void attemptToMoveWall(String side) {
		QuoridorController.moveWall(side);
		
	}
	
	/**
	 * 
	 * @author Alixe Delabrouse (260868412)
	 * 
	 * @param row
	 * @param column
	 */
	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void wallMoving(int row, int column) {
		QuoridorController.updateWallPosition(this.currentWall, row, column);
		
	}
	
	/**
	 * 
	 * @author Alixe Delabrousse (260868412) & Mohamed Mohamed (260855731)
	 * 
	 * @param direction
	 * @param nrow
	 * @param ncolumn
	 */
	
	@And("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void newWallCandidate(String direction, int row, int col) {
		
		Orientation orientation = Orientation.valueOf(direction.toUpperCase());
		this.wallCandidate = QuoridorController.createWallCandidateAtPosition(orientation,  row, col);
	}
	
	/**
	 * @author Alixe Delabrousse (260868412)
	 * 
	 * 
	 */
	
	@And("The wall candidate is at the {string} edge of the board")
	public void wallCandidateAtEdge(String side) {
		if (side.equals("up")) {
			Assert.assertTrue(this.wallCandidate.getRow() == 1);
		} else if (side.equals("down")) {
			Assert.assertTrue(this.wallCandidate.getRow() == 9);
		} else if (side.equals("left")) {
			Assert.assertTrue(this.wallCandidate.getColumn() == 1);
		} else if (side.contentEquals("right")) {
			Assert.assertTrue(this.wallCandidate.getColumn() == 9);
		}
		
	}
	
	/**
	 * @author alixe delabrousse (260868412) & mohamed mohamed (260855731)
	 */
	
	@Then("I shall be notified that my move is illegal")
	public void notifIllegalMove() {
		throw new PendingException();
		//UI related method
	}


	// ***** RotateWall feature ***** @Author Mohamed Mohamed
	
		//background feature is already written 
		//given clause already implemented
		//then clause implemented
	
		
	/**
	 * @author Mohamed Mohamed (260855731)
	 */
	@When("I try to flip the wall")
	public void tryFlipWall() {
		QuoridorController.rotateWall(this.wallCandidate);
		//this method will do the rotate wall method on a wall candidate that will do the following -->
	}
	
	/**
	 * @author Mohamed Mohamed (260855731)
	 */
	@Then("The wall shall be rotated over the board to {string}")
	public void rotateWall(String newdir) {
		//checking if the wall actually got rotated to newdir
		Orientation newDir=Orientation.valueOf(newdir.toUpperCase());//constructor takes an Orientation enum so the conversion is necessary
		Assert.assertTrue(this.wallCandidate.getOrientation()==newDir);
		
		
	}
		
	
	
	// ***** DropWall feature ***** @Author Mohamed Mohamed
	
			//background feature is already written 
	
	
	private TOPlayer player=null; //will be used to switch current player
	
	/**
	 * @author Mohamed Mohamed (260855731)
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void wallMoveCandidateIsValid(String direction, int row, int col) {
			
		this.wallCandidate.setOrientation(Orientation.valueOf(direction.toUpperCase()));
		this.wallCandidate.setRow(row);
		this.wallCandidate.setColumn(col);
		//now check if the position is valid
		boolean isValid= QuoridorController.validWallPlacement(row, col, Orientation.valueOf(direction.toUpperCase()));
		Assert.assertTrue(isValid);//if valid it will be true
	}
	
	/**
	 * @author Mohamed Mohamed (260855731)
	 */	
	@When("I release the wall in my hand")
	public void realeaseWall() {
		this.currentWall.setOrientation(this.wallCandidate.getOrientation());
		this.currentWall.setRow(this.wallCandidate.getRow());
		this.currentWall.setColumn(this.wallCandidate.getColumn());
		//calling the method drop wall that should drop the wall by removing the wall form hand and registering the position
		QuoridorController.dropWall(this.currentWall);//method drop wall will take as constructor a transfer object of wall
	}
		
	/**
	 * @author Mohamed Mohamed (260855731)
	 * @param dir Dir is a string that indicates the direction of the wall
	 * @param row Row is the row of the wall
	 * @param col Col is the column of the wall
	 */
	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void aWallIsRegisteredAt(String dir, int row , int col){
	//	check if a wall exists at the given information //implemented by the drop wall method
		Assert.assertTrue(QuoridorController.createWallMove(row, col, Orientation.valueOf(dir.toUpperCase())));
		
	}
	
	/**
	 * @author Mohamed Mohamed (260855731)
	 */
	@Then("I shall not have a wall in my hand")
	public void removeWallFromHand() {
		Assert.assertFalse(QuoridorController.getPlayerOfCurrentTurn().hasWallInHand());//check if the player has a wall or not
	}
	
	/**
	 * @author Mohamed Mohamed (260855731)
	 */
	@And("My move shall be completed")
	public void CompleteMove() {
		this.player=QuoridorController.getPlayerOfCurrentTurn();
		//move completed hence switch player
		QuoridorController.switchCurrentPlayer();//
	}
		
	/**
	 * @author Mohamed Mohamed (260855731)
	 */
	@And("It shall not be my turn to move")
	public void finishMove() {
		//if it's no longer my move than player is no longer referencing the current player
		Assert.assertTrue(this.player!=QuoridorController.getPlayerOfCurrentTurn());//condition should be true
	}

	/**
	 * @author Mohamed Mohamed (260855731)
	 * @param direction Direction is a string that indicates the direction of the wall
	 * @param row Row is the row of the wall
	 * @param col Col is the column of the wall
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void wallMoveCandidateIsInvalid(String direction, int row, int col) {
		
		this.wallCandidate.setOrientation(Orientation.valueOf(direction.toUpperCase()));
		this.wallCandidate.setRow(row);
		this.wallCandidate.setColumn(col);
		//now check if the position is valid
		boolean isValid=QuoridorController.validWallPlacement(row, col, Orientation.valueOf(direction.toUpperCase()));
		Assert.assertFalse(isValid);//should be false since there is no move available
		
	}
    
	/**
	 * @author Mohamed Mohamed (260855731)
	 */
    @Then("I shall be notified that my wall move is invalid")
    public void invalidWallMove(){
    	//UI implemented method that will tell the user that he can't place the wall there
        throw new PendingException();
    }

    /**
	 * @author Mohamed Mohamed (260855731)
	 */
    @And("It shall be my turn to move")
    public void continueMove() {
    	Assert.assertTrue(this.player==QuoridorController.getPlayerOfCurrentTurn());//condition should be true
    }

    /**
	 * @author Mohamed Mohamed (260855731)
	 */
    @But("No wall move shall be registered with {string} at position \\({int}, {int})")
    public void unregisteredWallMove(String dir, int row, int col){
    	Assert.assertFalse(QuoridorController.createWallMove(row, col, Orientation.valueOf(dir.toUpperCase())));  
    }
	
	
	
	
	
	// ***** ValidatePosition.feature *****

	private int row;
	private int column;
	private Orientation orientation;

	private boolean positionValidityFlag;

	/**
	 * @param row Row in pawn coordinates
	 * @param column Column in pawn coordinates
	 * @author Group 9
	 */
	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void gamePositionIsSuppliedWithPawn(int row, int column) {
		this.row = row;
		this.column = column;
		// hack for this#validationOfThePositionIsInitiated to work!
		this.orientation = null;
	}

	/**
	 * @author Group 9
	 */
	@When("Validation of the position is initiated")
	public void validationOfThePositionIsInitiated() {
		if (this.orientation == null) {
			// This is a pawn position
			this.positionValidityFlag = QuoridorController.validatePawnPlacement(this.row, this.column);
		} else {
			// This is a wall position
			this.positionValidityFlag = QuoridorController.validateWallPlacement(this.row, this.column, this.orientation);
		}
	}

	/**
	 * @param result Either "ok" or "error"
	 * @author Group 9
	 */
	@Then("The position shall be {string}")
	public void positionShallBe(String result) {
		switch (result) {
			case "ok":
				Assert.assertTrue(this.positionValidityFlag);
				break;
			case "error":
				Assert.assertFalse(this.positionValidityFlag);
				break;
			default:
				Assert.fail("Unhandled result: " + result);
		}
	}

	/**
	 * @param row Row in wall coordinates
	 * @param column Column in wall coordinates
	 * @param orientation either "horizontal" or "vertical"
	 * @author Group 9
	 */
	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void gamePositionIsSuppliedWithWall(int row, int column, String orientation) {
		this.row = row;
		this.column = column;
		this.orientation = Orientation.valueOf(orientation.toUpperCase());
	}

	/**
	 * @author Group 9
	 */
	@Then("The position shall be valid")
	public void positionShallBeValid() {
		Assert.assertTrue(this.positionValidityFlag);
	}

	/**
	 * @author Group 9
	 */
	@Then("The position shall be invalid")
	public void positionShallBeInvalid() {
		Assert.assertFalse(this.positionValidityFlag);
	}

	// ***** SwitchCurrentPlayer.feature *****

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@Given("The player to move is {string}")
	public void playerToMoveIs(String playerColor) {
		final TOPlayer player = QuoridorController.getPlayerOfCurrentTurn();
		Assert.assertNotNull(player);
		Assert.assertEquals(Color.valueOf(playerColor.toUpperCase()), player.getColor());
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@And("The clock of {string} is running")
	public void clockOfPlayerIsRunning(String playerColor) {
		throw new PendingException();
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@And("The clock of {string} is stopped")
	public void clockOfPlayerIsStopped(String playerColor) {
		throw new PendingException();
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@When("Player {string} completes his move")
	public void playerCompletesHisMove(String playerColor) {
		QuoridorController.switchCurrentPlayer();
	}

	/**
	 * @param opponentColor color of opponent
	 * @author Group-9
	 */
	@Then("The user interface shall be showing it is {string} turn")
	public void userInterfaceShallBeShowingItIsOpponentsTurn(String opponentColor) {
		throw new PendingException();
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@And("The clock of {string} shall be running")
	public void clockOfPlayerShallBeRunning(String playerColor) {
		throw new PendingException();
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@And("The clock of {string} shall be stopped")
	public void clockOfPlayerShallBeStopped(String playerColor) {
		throw new PendingException();
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@And("The next player to move shall be {string}")
	public void nextPlayerToMoveShallBe(String playerColor) {
		final TOPlayer player = QuoridorController.getPlayerOfCurrentTurn();
		Assert.assertNotNull(player);
		Assert.assertEquals(Color.valueOf(playerColor.toUpperCase()), player.getColor());
	}
	
	// ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public void tearDown() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// Avoid null pointer for step definitions that are not yet implemented.
		if (quoridor != null) {
			quoridor.delete();
			quoridor = null;
		}
		for (int i = 0; i < 20; i++) {
			Wall wall = Wall.getWithId(i);
			if(wall != null) {
				wall.delete();
			}
		}
	}

	// ***********************************************
	// Extracted helper methods
	// ***********************************************

	// Place your extracted methods below

	private void initQuoridorAndBoard() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Board board = new Board(quoridor);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
	}

	private ArrayList<Player> createUsersAndPlayers(String userName1, String userName2) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		User user1 = quoridor.addUser(userName1);
		User user2 = quoridor.addUser(userName2);

		int thinkingTime = 180;

		// Players are assumed to start on opposite sides and need to make progress
		// horizontally to get to the other side
		//@formatter:off
		/*
		 *  __________
		 * |          |
		 * |          |
		 * |x->    <-x|
		 * |          |
		 * |__________|
		 * 
		 */
		//@formatter:on
		Player player1 = new Player(new Time(thinkingTime), user1, 9, Direction.Horizontal);
		Player player2 = new Player(new Time(thinkingTime), user2, 1, Direction.Horizontal);

		Player[] players = { player1, player2 };

		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}
		
		ArrayList<Player> playersList = new ArrayList<Player>();
		playersList.add(player1);
		playersList.add(player2);
		
		return playersList;
	}

	private void createAndStartGame(ArrayList<Player> players) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);
		
		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, players.get(0), players.get(1), quoridor);

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, players.get(0), game);

		// Add the walls as in stock for the players
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j);
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (int j = 0; j < 10; j++) {
			Wall wall = Wall.getWithId(j + 10);
			gamePosition.addBlackWallsInStock(wall);
		}

		game.setCurrentPosition(gamePosition);
	}
}
