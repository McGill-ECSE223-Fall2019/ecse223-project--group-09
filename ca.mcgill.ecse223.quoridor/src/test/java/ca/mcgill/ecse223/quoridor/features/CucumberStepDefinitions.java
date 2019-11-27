package ca.mcgill.ecse223.quoridor.features;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.EnumSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import ca.mcgill.ecse223.quoridor.application.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Color;
import ca.mcgill.ecse223.quoridor.controller.InvalidInputException;
import ca.mcgill.ecse223.quoridor.controller.InvalidLoadException;
import ca.mcgill.ecse223.quoridor.controller.InvalidPositionException;
import ca.mcgill.ecse223.quoridor.controller.NoGrabbedWallException;
import ca.mcgill.ecse223.quoridor.controller.Orientation;
import ca.mcgill.ecse223.quoridor.controller.PawnBehavior;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;
import ca.mcgill.ecse223.quoridor.controller.WallStockEmptyException;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Destination;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.model.GamePosition;
import ca.mcgill.ecse223.quoridor.model.JumpMove;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.StepMove;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.view.BoardWindow;
import cucumber.api.PendingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions {

	// ***********************************************
	// Background step definitions
	// ***********************************************

	private List<TOWall> wallStock;
	private TOWall currentWall;
	private TOWallCandidate wallCandidate;

	private WallMove currentMove;

	private boolean noMoreWallsFlag = false;
	private boolean invalidPositionFlag = false;

	private boolean wallGrabbedFlag  = false;



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
			// Wall wall = Wall.getWithId(playerIdx * 10 + wallIdxForPlayer);
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
			new WallMove(0, 1, players[playerIdx], quoridor.getBoard().getTile((wrow - 1) * 9 + wcol - 1),
					quoridor.getCurrentGame(), direction, wall);
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
	

	/**
	 * @author Alixe Delabrousse (260868412) and Mohamed Mohamed
	 */

	@And("I do not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand() {
		try {
			this.currentWall = QuoridorController.getCurrentGrabbedWall();
			this.wallGrabbedFlag = true;
		} catch (NoGrabbedWallException e) {
			this.wallGrabbedFlag = false;
		}

	}

	/**
	 * @author Alixe Delabrousse (260868412) and Mohamed Mohamed
	 *
	 * @throws Throwable
	 */

	@And("^I have a wall in my hand over the board$")
	public void iHaveAWallInMyHandOverTheBoard() throws Throwable {


			this.wallCandidate = QuoridorController.getWallCandidate();
			if (this.wallCandidate == null) {
				QuoridorController.grabWall();
				this.wallCandidate = QuoridorController.getWallCandidate();
			}


		Assert.assertFalse(this.wallGrabbedFlag);
		Assert.assertNotNull(QuoridorController.getCurrentGrabbedWall());

	}

	@Given("^A new game is initializing$")
	public void aNewGameIsInitializing() throws Throwable {
		initQuoridorAndBoard();
		ArrayList<Player> players = createUsersAndPlayers("user1", "user2");
		new Game(GameStatus.Initializing, MoveMode.PlayerMove, QuoridorApplication.getQuoridor());
	}
	
	/**
	 * @author alixe delabrousse
	 */
	
	@Given("The game is in replay mode")
	public void theGameIsInReplayMode() {
		initQuoridorAndBoard();
		ArrayList<Player> players = createUsersAndPlayers("user1", "user2");
		createAndStartGame(players);
		
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		game.setGameStatus(GameStatus.Replay);
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
	 * @author Barry Chen & Ada Andrei
	 */
	@And("White player chooses a username")
	public void whitePlayerChoosesUsername() {
		Game aNewGame = QuoridorApplication.getQuoridor().getCurrentGame();
		QuoridorController.createOrSelectUsername("Player Un", Color.WHITE);
		Assert.assertNotNull(aNewGame.getWhitePlayer().getUser().getName());
	}

	/**
	 * @author Barry Chen & Ada Andrei
	 */
	@And("Black player chooses a username")
	public void blackPlayerChoosesUsername() {
		Game aNewGame = QuoridorApplication.getQuoridor().getCurrentGame();
		QuoridorController.createOrSelectUsername("Player Deux", Color.BLACK);
		Assert.assertNotNull(aNewGame.getBlackPlayer().getUser().getName());
	}

	/**
	 * @author Barry Chen & Ada Andrei
	 */
	@And("Total thinking time is set")
	public void totalThinkingTimeSet() {
		Game aNewGame = QuoridorApplication.getQuoridor().getCurrentGame();
		Assert.assertNotNull(aNewGame.getWhitePlayer().getRemainingTime());
		Assert.assertNotNull(aNewGame.getBlackPlayer().getRemainingTime());
	}

	/**
	 * @author Barry Chen
	 */
	@Then("The game shall become ready to start")
	public void gameShallBecomeReadyToStart() {
		throw new PendingException();
	}

	/*
	 * Given The game is ready to start When I start the clock Then The game shall
	 * be running And The board shall be initialized
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
	 * @param String color;
	 * @author Ada Andrei
	 */

	@Given("Next player to set user name is {string}")
	public void nextPlayerToSetUserNameIsColor(String color) {
		this.color = Color.valueOf(color.toUpperCase());
	}

	/**
	 * @param String user;
	 * @author Ada Andrei
	 */

	@And("There is existing user {string}")
	public void existingUser(String user) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		quoridor.addUser(user); //check user is there
		Assert.assertTrue(QuoridorController.usernameExists(user));
	}

	/**
	 * @param String user;
	 * @author Ada Andrei
	 */
	@When("The player selects existing {string}")
	public void playerSelectsExistingUsername(String user) {
		QuoridorController.createOrSelectUsername(user, this.color);
	}

	/**
	 * @param String color;
	 * @param String user;
	 * @author Ada Andrei
	 */
	@Then("The name of player {string} in the new game shall be {string}")
	public void nameOfPlayerInNewGameShallBeUsername(String color, String user) {
		TOPlayer aPlayer = QuoridorController.getPlayerByColor(Color.valueOf(color.toUpperCase()));
		Assert.assertEquals(aPlayer.getUsername(), user);

	}

	// create new user name

	/**
	 * @param String user;
	 * @author Ada Andrei
	 */
	@And("There is no existing user {string}")
	public void noExistingUser(String user) {
		Assert.assertFalse(QuoridorController.usernameExists(user));
	}

	boolean ExceptionThrown = false;

	/**
	 * @param String user;
	 * @author Ada Andrei
	 * @throws InvalidInputException
	 */
	@When("The player provides new user name: {string}")
	public void playerProvidesNewUserName(String user) {
		try {
			this.ExceptionThrown = false;
			QuoridorController.createNewUsername(user, this.color);
		}
		catch (Exception e) {
			this.ExceptionThrown = true;
		}
	}

	// user name already exists

	/**
	 * @param String user;
	 * @author Ada Andrei
	 * @throws InvalidInputException
	 */

	@Then("The player shall be warned that {string} already exists")
	public void playerShallBeWarnedThatUsernameAlreadyExists(String user) {//throws InvalidInputException {
		Assert.assertTrue(this.ExceptionThrown);
	}

	/**
	*@author Ada Andrei
	*/
	@And("Next player to set user name shall be {string}")
	public void nextPlayerToSetUserNameShallBe(String color) {
		Game aNewGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if (color.equalsIgnoreCase("WHITE")) {
			Assert.assertFalse(aNewGame.hasWhitePlayer());
		}
		else if (color.equalsIgnoreCase("BLACK")) {
			Assert.assertFalse(aNewGame.hasBlackPlayer());
		}
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
		if (file.exists()) {
			file.delete();
	}
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
			if (this.fileName.endsWith(".dat")) {
				// Passing false as argument since file does not exist:
				// we are not overwriting any file (but this argument
				// is ignored in this case)
				Assert.assertTrue(QuoridorController.savePosition(this.fileName, false));
			} else if (this.fileName.endsWith(".mov")) {
				throw new UnsupportedOperationException("Someone needs to implement the saveGame method");
			} else {
				Assert.fail("Unhandled file type (.dat|.mov) for file name: " + this.fileName);
			}
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
		if (!file.exists()) {
			try {
				// Create a blank file with the same name
				file.createNewFile();
			} catch (IOException ex) {
				Assert.fail("No IOException should happen: " + ex.getMessage());
	}
		}
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@And("The user confirms to overwrite existing file")
	public void userConfirmsToOverwriteExistingFile() {
		try {
			if (this.fileName.endsWith(".dat")) {
				// Pass true since we are overwriting a file
				this.fileOverwriteFlag = QuoridorController.savePosition(this.fileName, true);
			} else if (this.fileName.endsWith(".mov")) {
				throw new UnsupportedOperationException("Someone needs to implement the saveGame method");
			} else {
				Assert.fail("Unhandled file type (.dat|.mov) for file name: " + this.fileName);
			}
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
			if (this.fileName.endsWith(".dat")) {
				// Pass false since we are not overwriting a file
				this.fileOverwriteFlag = QuoridorController.savePosition(this.fileName, false);
			} else if (this.fileName.endsWith(".mov")) {
				throw new UnsupportedOperationException("Someone needs to implement the saveGame method");
			} else {
				Assert.fail("Unhandled file type (.dat|.mov) for file name: " + this.fileName);
			}
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
			if (filename.endsWith(".dat")) {
				try {
					QuoridorController.loadPosition(filename);
					this.positionValidFlag = true;
				} catch (InvalidLoadException ex) {
					this.positionValidFlag = false;
				}
			} else if (filename.endsWith(".mov")) {
				throw new UnsupportedOperationException("Someone needs to implement the loadGame method");
			} else {
				Assert.fail("Unhandled file type (.dat|.move) for file name: " + filename);
			}
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
	 * @param playerColor Color of player
	 * @author Paul Teng (260862906)
	 */
	@Then("It shall be {string}'s turn")
	public void itShallBePlayersTurn(String playerColor) {
		final TOPlayer player = QuoridorController.getPlayerOfCurrentTurn();
		Assert.assertNotNull(player);
		Assert.assertEquals(Color.valueOf(playerColor.toUpperCase()), player.getColor());
	}

	/**
	 * @param playerColor Color of player
	 * @param row Row of player's pawn piece (pawn coordinates)
	 * @param col Column of player's pawn piece (pawn coordinates)
	 * @author Paul Teng (260862906)
	 */
	@And("{string} shall be at {int}:{int}")
	public void playerIsAtRowCol(String playerColor, int row, int col) {
		final TOPlayer player = QuoridorController.getPlayerByColor(Color.valueOf(playerColor.toUpperCase()));
		Assert.assertNotNull(player);
		Assert.assertEquals(row, player.getRow());
		Assert.assertEquals(col, player.getColumn());
	}

	/**
	 * @param playerColor Color of player
	 * @param orientation Orientation of player's wall piece
	 * @param row Row of player's wall piece (wall coordinates)
	 * @param col Column of player's wall piece (wall coordinates)
	 * @author Paul Teng (260862906)
	 */
	@And("{string} shall have a {word} wall at {int}:{int}")
	public void playerHasOrientedWallAtRowCol(String playerColor, String orientation, int row, int col) {
		final List<TOWall> walls = QuoridorController.getWallsOwnedByPlayer(Color.valueOf(playerColor.toUpperCase()));
		Assert.assertNotNull(walls);

		// Count the walls that satisfy the orientation and location
		// We expect only 1 that matches:
		int matches = 0;
		for (final TOWall wall : walls) {
			final Orientation wallOrientation = wall.getOrientation();
			if (wallOrientation == null) {
				// Wall has no orientation because it is not on board
				// definitely cannot match
				continue;
			}

			if (orientation.equalsIgnoreCase(wallOrientation.name())
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




	// ***** GrabWall.feature *****

		//Start wall placement

	/**
	 * @author Alixe Delabrousse (260868412)
	 */
	@Given("I have more walls on stock")
	public void moreWallsOnStock() {

			this.player = QuoridorController.getPlayerOfCurrentTurn();
			Assert.assertTrue(this.player.getWallsRemaining() != 0);
			this.wallStock = QuoridorController.getRemainingWallsOfCurrentPlayer();
			this.currentWall = this.wallStock.get(this.player.getWallsRemaining()-1);
			this.wallCandidate = new TOWallCandidate(QuoridorController.INITIAL_TO_ORIENTATION, QuoridorController.INITIAL_ROW,QuoridorController.INITIAL_COLUMN);

	}

	/**
	 * @author Alixe Delabrousse (260868412)
	 */

	@When("I try to grab a wall from my stock")
	public void playerTryToGrabWall() {
		try{
			this.currentWall = QuoridorController.grabWall();
		} catch (WallStockEmptyException e) {
			this.noMoreWallsFlag = true;
			this.currentWall = null;
			this.wallCandidate= null;
		}

	}

	/**
	 * @author Alixe Delabrousse
	 * @param currentGrabbedWall
	 */

	@Then("A wall move candidate shall be created at initial position")
	public void createNewWallMoveCandidate() {

		this.wallCandidate = QuoridorController.getWallCandidate();
		Assert.assertNotNull(this.wallCandidate);

	}

	/**
	 * @author Alixe Delabrousse (260868412) & Mohamed Mohamed (260855731)
	 */

	@And("I shall have a wall in my hand over the board")
	public void wallOverBoard() {

		Assert.assertFalse(this.noMoreWallsFlag);
		Assert.assertNotNull(this.currentWall);

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

		this.player = QuoridorController.getPlayerOfCurrentTurn();
		this.player.setWallsRemaining(0);

		Assert.assertTrue(this.player.getWallsRemaining() == 0);
		this.noMoreWallsFlag = true;
	}

	/**
	 * @author Alixe Delabrousse (260868412)
	 */
	@Then("I shall be notified that I have no more walls")
	public void notifNoMoreWalls() {
		Assert.assertTrue(this.noMoreWallsFlag);
	}

	/**
	 * @author Alixe Delabrousse (260868412)
	 *
	 */
	@But("I shall have no walls in my hand")
	public void noWallInHand() {

		Assert.assertFalse(this.wallGrabbedFlag);
	}



	// ***** MoveWall.feature *****

	/**
	 * @author Alixe Delabrousse (260868412) & Mohamed Mohamed (260855731)
	 */
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void wallCandidateExists(String direction, int row, int column) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();

		Orientation orientation = Orientation.valueOf(direction.toUpperCase());

		this.currentMove = quoridor.getCurrentGame().getWallMoveCandidate();

		int aRow = (9 - row);// we invert the row because in the controller, the rows
							// were numbered from bottom to top, unlike the Gherkin scenarios

		Tile targetTile = QuoridorController.getTileFromRowAndColumn(aRow, column);

		this.currentMove.setTargetTile(targetTile);

		this.wallCandidate = QuoridorController.getWallCandidate();
		this.wallCandidate.setOrientation(orientation);			//
		this.wallCandidate.setColumn(column);					// this is of the rotate wall test to pass
		this.wallCandidate.setRow(aRow);						//

		//we have to create the precondition

		this.currentWall = QuoridorController.getCurrentGrabbedWall();
		this.currentWall.setOrientation(orientation);
		this.currentWall.setColumn(column);
		this.currentWall.setRow(aRow);

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
			Assert.assertTrue(this.wallCandidate.getRow() != 9);
		} else if (side.equals("down")) {
			Assert.assertTrue(this.wallCandidate.getRow() != 1);
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

		try {
			this.wallCandidate = QuoridorController.moveWall(side);
		} catch (InvalidPositionException e) {
			this.invalidPositionFlag = true;
		}

	}

	/**
	 *
	 * @author Alixe Delabrousse (260868412)
	 *
	 * @param row
	 * @param column
	 */
	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void wallMoving(int row, int column) {

		int aRow= (9 - row);


		this.currentWall.setRow(aRow);
		this.currentWall.setColumn(column);


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

		int aRow = (9 - row);


		this.wallCandidate.setOrientation(orientation);
		this.wallCandidate.setRow(aRow);
		this.wallCandidate.setColumn(col);
	}

	/**
	 * @author Alixe Delabrousse (260868412)
	 *
	 *
	 */

	@And("The wall candidate is at the {string} edge of the board")
	public void wallCandidateAtEdge(String side) {

			if (side.equals("up")) {
				this.wallCandidate.setRow(8);
			} else if (side.equals("down")) {
				this.wallCandidate.setRow(1);
			} else if (side.equals("left")) {
				this.wallCandidate.setColumn(1);
			} else if (side.contentEquals("right")) {
				this.wallCandidate.setColumn(8);
			}
	}

	/**
	 * @author Alixe Delabrousse (260868412) & Mohamed Mohamed (260855731)
	 */

	@Then("I shall be notified that my move is illegal")
	public void notifIllegalMove() {

		Assert.assertTrue(this.invalidPositionFlag);

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
	boolean tester=false;

	/**
	 * @author Mohamed Mohamed (260855731)
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void wallMoveCandidateIsValid(String direction, int row, int col) {

		this.wallCandidate=new TOWallCandidate(Orientation.valueOf(direction.toUpperCase()), row, col);
		this.wallCandidate.setValidity(true);
		this.currentWall=wallCandidate.getAssociatedWall(); //for testing that i still have it

		//now check if the position is valid
		boolean isValid= QuoridorController.validateWallPlacement(row, col, Orientation.valueOf(direction.toUpperCase()));
		Assert.assertTrue(isValid);//if valid it will be true
		player=new TOPlayer();
		this.player.setColor(QuoridorController.getPlayerOfCurrentTurn().getColor());

	}

	/**
	 * @author Mohamed Mohamed (260855731)
	 */
	@When("I release the wall in my hand")
	public void realeaseWall() {
		//calling the method drop wall that should drop the wall by removing the wall form hand and registering the position
		//the right parameters are being passed
		tester=QuoridorController.dropWall(this.wallCandidate.getAssociatedWall());//method drop wall will take as constructor a transfer object of wall
		this.currentWall=wallCandidate.getAssociatedWall();

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
		Assert.assertTrue(QuoridorController.checkLastWallMove(row, col, Orientation.valueOf(dir.toUpperCase())));

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
	Assert.assertTrue(tester);

	}

	/**
	 * @author Mohamed Mohamed (260855731)
	 */
	@And("It shall not be my turn to move")
	public void finishMove() {
		//if it's no longer my move than player is no longer referencing the current player
		Color color=QuoridorController.getPlayerOfCurrentTurn().getColor();
		Assert.assertFalse(this.player.getColor().equals(color));//condition should be true
	}

	/**
	 * @author Mohamed Mohamed (260855731)
	 * @param direction Direction is a string that indicates the direction of the wall
	 * @param row Row is the row of the wall
	 * @param col Col is the column of the wall
	 */
	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void wallMoveCandidateIsInvalid(String direction, int row, int col) {

		this.wallCandidate=new TOWallCandidate(Orientation.valueOf(direction.toUpperCase()), row, col);
		this.wallCandidate.setValidity(false);
		this.currentWall=wallCandidate.getAssociatedWall();
		//now check if the position is valid
		boolean isValid=QuoridorController.validateWallPlacement(row, col, Orientation.valueOf(direction.toUpperCase()));
		Assert.assertFalse(isValid);//should be false since there is no move available
		player=QuoridorController.getPlayerOfCurrentTurn();

	}

	/**
	 * @author Mohamed Mohamed (260855731)
	 */
    @Then("I shall be notified that my wall move is invalid")
    public void invalidWallMove(){
    	//UI implemented method that will tell the user that he can't place the wall there
        //if it's an invalid move than the boolean should return a false to indicate to the
    	//ui that i have to give a message to the user.

    	Assert.assertFalse(tester); //tester must be false bc drop wont allow it
    	//Assert.assertFalse(QuoridorController.dropWall(this.wallCandidate.getAssociatedWall()));;

    }

    /**
	 * @author Mohamed Mohamed (260855731)
	 */
    @And("It shall be my turn to move")
    public void continueMove() {

    	Assert.assertTrue(this.player.getColor().equals(QuoridorController.getPlayerOfCurrentTurn().getColor()) );//condition should be true
    }

    /**
	 * @author Mohamed Mohamed (260855731)
	 */
    @But("No wall move shall be registered with {string} at position \\({int}, {int})")
    public void unregisteredWallMove(String dir, int row, int col){
    	Assert.assertFalse(QuoridorController.checkLastWallMove(row, col, Orientation.valueOf(dir.toUpperCase())));
    }





	// ***** ValidatePosition.feature *****

	private boolean positionValidityFlag;
	private boolean positionFailedEarly;

	/**
	 * @param row Row in pawn coordinates
	 * @param column Column in pawn coordinates
	 * @author Group 9
	 */
	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void gamePositionIsSuppliedWithPawn(int row, int column) {
		this.positionFailedEarly = !QuoridorController.validatePawnPlacement(row, column);
	}

	/**
	 * @author Group 9
	 */
	@When("Validation of the position is initiated")
	public void validationOfThePositionIsInitiated() {
		this.positionValidityFlag = QuoridorController.validateCurrentGamePosition();
	}

	/**
	 * @param result Either "ok" or "error"
	 * @author Group 9
	 */
	@Then("The position shall be {string}")
	public void positionShallBe(String result) {
		switch (result) {
			case "error":
				// Either position failed early or position is invalid
				Assert.assertTrue(this.positionFailedEarly || !this.positionValidityFlag);
				break;
			case "ok":
				// Opposite of the error case
				Assert.assertFalse(this.positionFailedEarly || !this.positionValidityFlag);
				break;
			default:
				Assert.fail("Unhandled result: " + result);
		}
		this.positionFailedEarly = false;
	}

	/**
	 * @param row Row in wall coordinates
	 * @param column Column in wall coordinates
	 * @param orientation either "horizontal" or "vertical"
	 * @author Group 9
	 */
	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void gamePositionIsSuppliedWithWall(int row, int column, String orientation) {
		this.positionFailedEarly = !QuoridorController.validateWallPlacement(row, column, Orientation.valueOf(orientation.toUpperCase()));
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

	private BoardWindow boardWindow = new BoardWindow();

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@Given("The player to move is {string}")
	public void playerToMoveIs(String playerColor) {
		QuoridorController.updatePlayerOfCurrentRound(Color.valueOf(playerColor.toUpperCase()));
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@And("The clock of {string} is running")
	public void clockOfPlayerIsRunning(String playerColor) {
		Assert.assertTrue(QuoridorController.clockIsRunningForPlayer(Color.valueOf(playerColor.toUpperCase())));
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@And("The clock of {string} is stopped")
	public void clockOfPlayerIsStopped(String playerColor) {
		Assert.assertFalse(QuoridorController.clockIsRunningForPlayer(Color.valueOf(playerColor.toUpperCase())));
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@When("Player {string} completes his move")
	public void playerCompletesHisMove(String playerColor) {
		// Issue a switch-current-player call
		QuoridorController.switchCurrentPlayer();

		// Right now, whatever board window was displaying is now incorrect.
		// Ask board window to fetch new player information and display it.
		this.boardWindow.fetchCurrentPlayerInfoFromController();
	}

	/**
	 * @param opponentColor color of opponent
	 * @author Group-9
	 */
	@Then("The user interface shall be showing it is {string} turn")
	public void userInterfaceShallBeShowingItIsOpponentsTurn(String opponentColor) {
		Assert.assertTrue(this.boardWindow.getPlayerInfoPanel().getPlayerColorText().equalsIgnoreCase(opponentColor));
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@And("The clock of {string} shall be running")
	public void clockOfPlayerShallBeRunning(String playerColor) {
		Assert.assertTrue(QuoridorController.clockIsRunningForPlayer(Color.valueOf(playerColor.toUpperCase())));
	}

	/**
	 * @param playerColor color of player
	 * @author Group-9
	 */
	@And("The clock of {string} shall be stopped")
	public void clockOfPlayerShallBeStopped(String playerColor) {
		Assert.assertFalse(QuoridorController.clockIsRunningForPlayer(Color.valueOf(playerColor.toUpperCase())));
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

	// ***** JumpPawn.feature *****
	
	private boolean moveResult;
	private Color lastPlayerColor;

	
	/**
	 * 
	 * @author Alixe Delabrousse (260868412)
	 * 
	 * @param prow
	 * @param pcol
	 */
	
	@And("The player is located at {int}:{int}")
	public void thePlayerIsLocatedAt(int prow, int pcol) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		PlayerPosition ppos;
		if (QuoridorController.getCurrentPlayer().hasGameAsBlack()) {
			ppos = game.getCurrentPosition().getBlackPosition();
		} else {
			ppos = game.getCurrentPosition().getWhitePosition();
		}
		
		ppos.setTile(QuoridorController.getTileFromRowAndColumn(prow, pcol));
	
	}
	
	/**
	 * 
	 * @author Alixe Delabrousse (260868412)
	 * 
	 * @param orow
	 * @param ocol
	 */
	
	@And("The opponent is located at {int}:{int}")
	public void theOpponentIsLocatedAt(int orow, int ocol) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		PlayerPosition opos;
		if (QuoridorController.getCurrentPlayer().hasGameAsBlack()) {
			opos = game.getCurrentPosition().getWhitePosition();
		} else {
			opos = game.getCurrentPosition().getBlackPosition();
		}
		
		opos.setTile(QuoridorController.getTileFromRowAndColumn(orow, ocol));
	
	}
	
	/**
	 * @param direction Direction of the wall
	 * @param side Side relative to the player
	 *
	 * @author Paul Teng (260862906)
	 */
	@And("There are no {string} walls {string} from the player nearby")
	public void noWallsFromPlayerNearby(String direction, String side) {
		noWallsFromPlayerHelper(direction, side);
		
	}

	/**
	 * A helper tester(?) because there are two clauses that do the exact same thing
	 *
	 * @param direction Direction of the wall
	 * @param side Side relative to the player
	 *
	 * @author Paul Teng (260862906)
	 */
	private void noWallsFromPlayerHelper(String direction, String side) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		final GamePosition gpos = quoridor.getCurrentGame().getCurrentPosition();

		final Tile pos;
		if (gpos.getPlayerToMove().hasGameAsWhite()) {
			pos = gpos.getWhitePosition().getTile();
		} else {
			pos = gpos.getBlackPosition().getTile();
		}

		// Note: In the controller and the view, up is defined as increasing in rows
		// which is the opposite from the tester. Same thing with down.
		//
		// which is why the code calls the method with opposite Y-direction!

		switch (side) {
			case "left":
				Assert.assertFalse(QuoridorController.anyWallLeftOfTile(pos.getRow(), pos.getColumn()));
				break;
			case "right":
				Assert.assertFalse(QuoridorController.anyWallRightOfTile(pos.getRow(), pos.getColumn()));
				break;
			case "up":
				Assert.assertFalse(QuoridorController.anyWallBelowTile(pos.getRow(), pos.getColumn()));
				break;
			case "down":
				Assert.assertFalse(QuoridorController.anyWallAboveTile(pos.getRow(), pos.getColumn()));
				break;
			case "upright":
				Assert.assertFalse(QuoridorController.anyWallBelowTile(pos.getRow(), pos.getColumn())
						&& QuoridorController.anyWallRightOfTile(pos.getRow(), pos.getColumn()));
				break;
			case "downright":
				Assert.assertFalse(QuoridorController.anyWallAboveTile(pos.getRow(), pos.getColumn())
						&& QuoridorController.anyWallRightOfTile(pos.getRow(), pos.getColumn()));
				break;
			case "upleft":
				Assert.assertFalse(QuoridorController.anyWallBelowTile(pos.getRow(), pos.getColumn())
						&& QuoridorController.anyWallLeftOfTile(pos.getRow(), pos.getColumn()));
				break;
			case "downleft":
				Assert.assertFalse(QuoridorController.anyWallAboveTile(pos.getRow(), pos.getColumn())
						&& QuoridorController.anyWallLeftOfTile(pos.getRow(), pos.getColumn()));
				break;
			default:
				throw new AssertionError("Unknown side: " + side);
		}
	}
	
	boolean successfulMoveFlag = false;
	
	/**
	 * 
	 * @author Group-9
	 * 
	 * @param player
	 * @param side
	 */
	@When("Player {string} initiates to move {string}")
	public void playerInititatesToMove(String color, String side) {
		this.lastPlayerColor = Color.valueOf(color.toUpperCase());

		// Note: In the controller and the view, up is defined as increasing in rows
		// which is the opposite from the tester. Same thing with down.
		//
		// which is why the code calls the method with opposite Y-direction!

		// Since jumps and moves both use the same thing
		// we will try moves first, and then try jumps if
		// that was unsuccessful
		switch (side) {
			case "left":
				this.moveResult = QuoridorController.moveCurrentPawnLeft()
						|| QuoridorController.jumpCurrentPawnLeft();
				break;
			case "right":
				this.moveResult = QuoridorController.moveCurrentPawnRight()
						|| QuoridorController.jumpCurrentPawnRight();
				break;
			case "down":
				this.moveResult = QuoridorController.moveCurrentPawnUp()
						|| QuoridorController.jumpCurrentPawnUp();
				break;
			case "up":
				this.moveResult = QuoridorController.moveCurrentPawnDown()
						|| QuoridorController.jumpCurrentPawnDown();
				break;
			case "downright":
				this.moveResult = QuoridorController.jumpCurrentPawnUpRight();
				break;
			case "upright":
				this.moveResult = QuoridorController.jumpCurrentPawnDownRight();
				break;
			case "downleft":
				this.moveResult = QuoridorController.jumpCurrentPawnUpLeft();
				break;
			case "upleft":
				this.moveResult = QuoridorController.jumpCurrentPawnDownLeft();
				break;
			default:
				throw new AssertionError("Unsupported move side: " + side);
		}
	}
		
	
	
	
	/**
	 * 
	 * @author Ada Andrei (260866279)
	 * 
	 * @param side
	 * @param status
	 */
	
	@Then("The move {string} shall be {string}")
	public void theMoveShallBe(String side, String status) {
		switch (status) {
			case "success":
				Assert.assertTrue(this.moveResult);
				break;
			case "illegal":
				Assert.assertFalse(this.moveResult);
				break;
			default:
				throw new IllegalArgumentException("Unrecognized status: " + status);
		}	}
	
	/**
	 * 
	 * @author Ada Andrei
	 * 
	 * @param nrow
	 * @param ncol
	 */
	
	@And("Player's new position shall be {int}:{int}")
	public void playerNewPosition(int nrow, int ncol) {
		final TOPlayer player = QuoridorController.getPlayerByColor(this.lastPlayerColor);
		Assert.assertNotNull(player);
		Assert.assertEquals(nrow, player.getRow());
		Assert.assertEquals(ncol, player.getColumn());
	}
	
	/**
	 * 
	 * @author Mohamed Mohamed(260855731)
	 * 
	 * @param nPlayer
	 * 
	 */
	@And("The next player to move shall become {string}")
	public void theNextPlayerToMoveIs(String nPlayer) {
		TOPlayer player = QuoridorController.getPlayerOfCurrentTurn();
		Assert.assertNotNull(player);//making sure he exists
		Assert.assertEquals(Color.valueOf(nPlayer.toUpperCase()), player.getColor());// making sure they are both the same player
	}
	
	
	/**
	 * 
	 * @author Barry Chen (260632542)
	 * 
	 * @param direction
	 * @param wrow
	 * @param wcol
	 */
	
	@And("There is a {string} wall at {int}:{int}")
	public void thereIsAWallAt(String direction, int wrow, int wcol) {
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		final Game game = quoridor.getCurrentGame();
		final GamePosition gpos = game.getCurrentPosition();

		final Direction dir = direction.equals("vertical") ? Direction.Vertical : Direction.Horizontal;
		final Tile tile = QuoridorController.getTileFromRowAndColumn(wrow, wcol);

		if (gpos.hasWhiteWallsOnBoard()) {
			final WallMove m = gpos.getWhiteWallsOnBoard(0).getMove();
			m.setTargetTile(tile);
			m.setWallDirection(dir);
		} else if (gpos.hasBlackWallsOnBoard()) {
			final WallMove m = gpos.getBlackWallsOnBoard(0).getMove();
			m.setTargetTile(tile);
			m.setWallDirection(dir);
		} else {
			final Wall w = gpos.getWhiteWallsInStock(0);
			gpos.removeWhiteWallsInStock(w);
			new WallMove(0, 0, game.getWhitePlayer(), tile, game, dir, w);
			gpos.addWhiteWallsOnBoard(w);
		}
	}
	

	/**
	 * @param direction Direction of wall
	 * @param side Side relative to player
	 *
	 * @author Paul Teng (260862906)
	 */
	@And("There are no {string} walls {string} from the player")
	public void thereAreNoWallsFromThePlayer(String direction, String side) {
		noWallsFromPlayerHelper(direction, side);
	}
	

	/**
	 * 
	 * @author Mohamed Mohamed(260855731)
	 * 
	 * @param side Side of the the enemy player, could be left, right, up or down.
	 * 
	 */
	@And("The opponent is not {string} from the player")
	public void theOpponentIsNotFromThePlayer(String side) {
		Quoridor quoridor = QuoridorApplication.getQuoridor(); // getting the current game
		GamePosition gamePos = quoridor.getCurrentGame().getCurrentPosition(); // getting the current gamePos
		Tile playerTile; // will be the tile of the current player.
		Tile enemyTile;
		
		if (gamePos.getPlayerToMove().hasGameAsWhite()) {//is white
			playerTile = gamePos.getWhitePosition().getTile();
			enemyTile = gamePos.getBlackPosition().getTile();
		} else {//is black
			playerTile = gamePos.getBlackPosition().getTile();
			enemyTile = gamePos.getWhitePosition().getTile();
		}

		boolean isThere=false;

		int rowDiff = enemyTile.getRow() - playerTile.getRow(); // difference in row
		int colDiff = enemyTile.getColumn() - playerTile.getColumn(); // difference in col

			// Same tile is for sure not on the side, but thats kind of dumb...
		if (rowDiff == 0 && colDiff == 0) {
			
		}

		switch (side) {
			case "left":
				if (rowDiff == 0 && colDiff == -1) { //if he is on left than this should be true 
					isThere=true;
				}  
				break; //dont check for the rest bc it stopped here
			case "right":
				if (rowDiff == 0 && colDiff == 1) {
					isThere=true;
				}
				break;
			case "up":
				if (rowDiff == 1 && colDiff == 0) {
					isThere=true;
				}
				break;
			case "down":
				if (rowDiff == -1 && colDiff == 0) {
					isThere=true;
				}
				break;
				//there is no deffault bc he should input something right	
		}
		
		Assert.assertFalse(isThere); // making sure he is not on my side
		Assert.assertNotNull(enemyTile); // should exist

	}

	// ***** CheckIfPathExists.feature *****

	private EnumSet<Color> pathFindResult;

	/**
	 * @param dir Direction
	 * @param row Row in wall coordinates
	 * @param col Column in wall coordinates
	 *
	 * @author Paul Teng (260862906)
	 */
	@Given("A {string} wall move candidate exists at position {int}:{int}")
	public void wallMoveCandidateExistsAt(String dir, int row, int col) {
		QuoridorController.grabWall();

		// TEST IS NOT PASSING, I THINK ISSUE IS WITH Y-AXIS INVERSION
		final WallMove move = QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate();
		move.setTargetTile(QuoridorController.getTileFromRowAndColumn(row, col));
		move.setWallDirection("vertical".equalsIgnoreCase(dir) ? Direction.Vertical : Direction.Horizontal);
	}

	/**
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 *
	 * @author Paul Teng (260862906)
	 */
	@And("The black player is located at {int}:{int}")
	public void blackPlayerIsLocatedAt(int row, int col) {
		QuoridorApplication.getQuoridor()
				.getCurrentGame()
				.getCurrentPosition()
				.getBlackPosition()
				.setTile(QuoridorController.getTileFromRowAndColumn(row, col));

		final Destination d = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getDestination();
		d.setTargetNumber(9);
	}

	/**
	 * @param row Row in pawn coordinates
	 * @param col Column in pawn coordinates
	 *
	 * @author Paul Teng (260862906)
	 */
	@And("The white player is located at {int}:{int}")
	public void whitePlayerIsLocatedAt(int row, int col) {
		QuoridorApplication.getQuoridor()
				.getCurrentGame()
				.getCurrentPosition()
				.getWhitePosition()
				.setTile(QuoridorController.getTileFromRowAndColumn(row, col));

		final Destination d = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getDestination();
		d.setTargetNumber(1);
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@When("Check path existence is initiated")
	public void initiateCheckPathExistence() {
		this.pathFindResult = QuoridorController.initiatePathExistenceTest();
	}

	/**
	 * @param result "white" | "black" | "both" | "none"
	 *
	 * @author Paul Teng (260862906)
	 */
	@Then("Path is available for {string} player\\(s)")
	public void pathIsAvailableForPlayers(String result) {
		switch (result) {
			case "both":
				Assert.assertEquals(EnumSet.allOf(Color.class), this.pathFindResult);
				break;
			case "none":
				Assert.assertEquals(EnumSet.noneOf(Color.class), this.pathFindResult);
				break;
			default:
				Assert.assertEquals(EnumSet.of(Color.valueOf(result.toUpperCase())), this.pathFindResult);
				break;
		}
	}
	
	//***** JumpToFinal.feature*****
	
	Move nextMove = null;
	
	@Given("The following moves have been played in game:")
	public void theFollowingMovesHaveBeenPlayedInGame(DataTable dataTable) {
		
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		
		Player player1 = game.getWhitePlayer();
		Player player2 = game.getBlackPlayer();
		
		int whiteWallUsed=1;
		int blackWallUsed=1;
		
		
		Move aMove;
		
		try {
			int a = player1.numberOfWalls();
		} catch (Exception e) {
			System.err.println("err");
		}
		
	
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		//keys: mv, rnd, move
		for (Map<String, String> map : valueMaps) {
			Integer mov = Integer.decode(map.get("mv"));
			Integer rnd = Integer.decode(map.get("rnd"));
			String move = map.get("move");
		//	System.err.println("\n"+mov+" move "+rnd+" round "+move+" move ");
			GamePosition gpos = game.getCurrentPosition();

			int col = QuoridorController.letterToNumberColumn(move.charAt(0));
			int row = Character.getNumericValue(move.charAt(1));
			System.err.println("\n");
			Tile target = QuoridorController.getTileFromRowAndColumn(row, col);
			
			if(move.length() == 3) {
				Wall nwall;
				
				Direction dir;
				if (move.charAt(2) == 'h') {
					dir = Direction.Horizontal;
				} else {
					dir = Direction.Vertical;
				}
				//
				if (rnd == 1) {
					nwall = gpos.getWhiteWallsInStock(player1.numberOfWalls()-whiteWallUsed);
					whiteWallUsed++;
					gpos.removeWhiteWallsInStock(nwall);
					aMove = new WallMove(mov, rnd, player1, QuoridorController.getTileFromRowAndColumn(row, col), game, dir, nwall);
					QuoridorController.switchCurrentPlayer();
					game.setCurrentMove(aMove);
					
				} else {
					nwall = gpos.getBlackWallsInStock(player2.numberOfWalls()-blackWallUsed);
					blackWallUsed++;
					gpos.removeBlackWallsInStock(nwall);
					aMove = new WallMove(mov, rnd, player2, QuoridorController.getTileFromRowAndColumn(row, col), game, dir, nwall);
					QuoridorController.switchCurrentPlayer();
					game.setCurrentMove(aMove);
				}
			} else {
				if (rnd == 1) {
					gpos.setWhitePosition(new PlayerPosition(player1, target));
					aMove = new StepMove(mov, rnd, player1, QuoridorController.getTileFromRowAndColumn(row, col), game);
					QuoridorController.switchCurrentPlayer();
					
					game.setCurrentMove(aMove);
					
					
				} else {
					gpos.setBlackPosition(new PlayerPosition(player2, target));
					aMove = new StepMove(mov, rnd, player2, QuoridorController.getTileFromRowAndColumn(row, col), game);
					QuoridorController.switchCurrentPlayer();
				
					game.setCurrentMove(aMove);
				}
				
			}
			
			game.addMoveAt(aMove, QuoridorController.getIndexFromMoveAndRoundNumber(mov, rnd));
			
//			System.err.println("Move: "+ aMove.getMoveNumber()+ " Round: "+ aMove.getRoundNumber());
		
		
		}
		
	}
	
	@When("Jump to start position is initiated")
	public void jumpToStartPositionIsInitiated() {
		QuoridorController.jumpToFinalPosition();
		
	}
	
	@And("White player's position shall be \\({int},{int})")
	public void whitePlayerPositionShallBe(int wrow, int wcol) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		
		System.err.println("wrow = "+ wrow);
		System.err.println("wcol = "+ wcol);
		
		System.err.println("current move number: "+ game.getCurrentMove().getMoveNumber());
		System.err.println("current round number: "+ game.getCurrentMove().getRoundNumber());
		
		int posWRow = game.getCurrentPosition().getWhitePosition().getTile().getRow();	
		int posWCol = game.getCurrentPosition().getWhitePosition().getTile().getColumn();
		
		System.err.println("posWRow = "+ posWRow);
		System.err.println("posWCol = "+ posWCol);
		
		
//		System.err.print(wrow+" "+wcol+" should be the position and i have"+posWRow+" "+posWCol);
		Assert.assertTrue(wrow == posWRow);
		Assert.assertTrue(wcol == posWCol);
		
	}
	
	@And("Black player's position shall be \\({int},{int})")
	public void blackPlayerPostionShallBe(int brow, int bcol) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		
		int posBRow = game.getCurrentPosition().getBlackPosition().getTile().getRow();	
		int posBCol = game.getCurrentPosition().getBlackPosition().getTile().getColumn();
		
		System.err.println("posBRow = "+ posBRow);
		System.err.println("posBCol = "+ posBCol);
		
		System.err.println("brow = "+ brow);
		System.err.println("bcol = "+ bcol);
		
		Assert.assertTrue(brow == posBRow);
		Assert.assertTrue(bcol == posBCol);
	}
	
	
	
	
	@And("White has {int} on stock")
	public void whiteHasOnStock(int wwallno) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		
		int posWWallNo = game.getCurrentPosition().getWhiteWallsInStock().size();
		Assert.assertTrue(wwallno == posWWallNo);
		
	}
	
	@And("Black has {int} on stock")
	public void blackHasOnStock(int bwallno) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		
		int posWWallNo = game.getCurrentPosition().getBlackWallsInStock().size();
		Assert.assertTrue(bwallno == posWWallNo);
	}
	
	//****** JumpToStart.feature******
	
//	@When("Jump to start position is initiated")
//	public void jumpToStartPosInitiated() {
//		this.nextMove = QuoridorController.jumpToStartPosition();
//	}
	

	//********STEP BACKWARD FEATURE***********
	

	    @And("The next move is {int}.{int}")
	    public void the_next_move_is_(int movno, int rndno) throws Throwable {
	    	Game game = QuoridorApplication.getQuoridor().getCurrentGame();
	    	
//	    	Move move= game.getCurrentMove();
//	    	int index = game.getMoves().indexOf(move);
//	    	
//	    	if(game.getMoves().size()==index) {
//	    		Move nextMove= game.getMove(index+1);
//	    	}else {
//	    		Move nextMove= game.getMove(index);
//	    	}	
	     
	        //System.err.println(movno+" moveno "+rndno+" roundnum");
	        Move aMove = game.getMove(QuoridorController.getIndexFromMoveAndRoundNumber(movno, rndno));
	        game.setCurrentMove(aMove);
	        System.err.print(game.getMoves().indexOf(aMove)+" index before");
	       // System.err.println(aMove+"\n\n my move BEFORE \n");
	      //  System.err.print(b);
	     //   System.err.print(nextMove.toString()+"\n \n is the move from me \n"+aMove.toString()+" is from alixe "+movno+" is movnum"+rndno+" is the roundNum");
	      //  Assert.assertEquals(nextMove, aMove);
	
	    }
	    
	    @When("Step backward is initiated")
	    public void step_backward_is_initiated() throws Throwable {
	        //call the stepBackward method;
	    	QuoridorController.stepBackward();
	    	
	    }
	    
	    @Then("The next move shall be {int}.{int}")
	    public void the_next_move_shall_be_(int nmov, int nrnd) throws Throwable {
	     	List<Move> moves=QuoridorApplication.getQuoridor().getCurrentGame().getMoves();
	    	int numOfMoves=moves.size();
	    	System.err.println("num of moves: "+ numOfMoves);
	    	
	    	//Move move= QuoridorApplication.getQuoridor().getCurrentGame().getMove(numOfMoves-1);
	    	Move move= QuoridorApplication.getQuoridor().getCurrentGame().getCurrentMove();
	    	int nextMoveIndex = moves.indexOf(move) ;//+ 1;
	    	System.err.println("nmov: "+ nmov);
	    	System.err.println("nrnd: "+ nrnd);
	    	int aIndex = QuoridorController.getIndexFromMoveAndRoundNumber(nmov, nrnd);
	    	System.err.println(nextMoveIndex+" your index "+aIndex+" what it is");
//	    	System.err.println("index: "+index);
//	    	Move nmove = moves.get(index);
    	
//	    	System.err.println(move+"\n my CURRENT move "+nmove+"\n move a Alixe");
//	    	System.err.println(nmov+" moveno "+nrnd+" roundnum");
	    	
	    	Assert.assertTrue(nextMoveIndex == aIndex);
	    }
 
	    
	    
	
	  //********STEP FORWARD FEATURE***********    
	    

	    @When("Step forward is initiated")
	    public void step_forward_is_initiated() throws Throwable {
	    	QuoridorController.stepForward();
	    }


	// ***** ReportFinalResult.feature *****

	/**
	 * @author Paul Teng (260862906)
	 */
	@When("The game is no longer running")
	public void gameNoLongerRunning() {
		// We start the game first
		// (otherwise game would just be null
		// which makes this test pretty useless
		// since in the actual game, game will
		// most certainly *not* be null)
		this.theGameIsRunning();

		// Let's say that white player won
		// (but really it doesn't matter)
		final Quoridor quoridor = QuoridorApplication.getQuoridor();
		quoridor.getCurrentGame().setGameStatus(GameStatus.WhiteWon);
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@Then("The final result shall be displayed")
	public void finalResultShallBeDisplayed() {
		// GUI code will call the following method
		// to check if there is a winner

		// Also scenario doesn't actually ask us
		// to check which player has won
		Assert.assertTrue(!QuoridorController.getWinner().isEmpty());
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@And("White's clock shall not be counting down")
	public void whiteClockShallNotBeCountingDown() {
		Assert.assertFalse(QuoridorController.clockIsRunningForPlayer(Color.WHITE));
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@And("Black's clock shall not be counting down")
	public void blackClockShallNotBeCountingDown() {
		Assert.assertFalse(QuoridorController.clockIsRunningForPlayer(Color.WHITE));
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@And("White shall be unable to move")
	public void whiteShallBeUnableToMove() {
		// Test by checking if white can move at all
		// If the hasn't ended yet, white would at least
		// be able to move in one of up, down, left, right directions

		final PawnBehavior sm = QuoridorController.setupPawnStateMachineForPlayer(Color.WHITE);
		Assert.assertFalse(sm.moveLeft());
		Assert.assertFalse(sm.moveRight());
		Assert.assertFalse(sm.moveUp());
		Assert.assertFalse(sm.moveDown());
	}

	/**
	 * @author Paul Teng (260862906)
	 */
	@And("Black shall be unable to move")
	public void blackShallBeUnableToMove() {
		// Test by checking if black can move at all
		// If the hasn't ended yet, black would at least
		// be able to move in one of up, down, left, right directions

		final PawnBehavior sm = QuoridorController.setupPawnStateMachineForPlayer(Color.BLACK);
		Assert.assertFalse(sm.moveLeft());
		Assert.assertFalse(sm.moveRight());
		Assert.assertFalse(sm.moveUp());
		Assert.assertFalse(sm.moveDown());
	}

	// ***** IdentifyGameWon.feature *****

	/**
	 * @param color Color of player
	 *
	 * @author Group-9
	 */
	@Given("Player {string} has just completed his move")
	public void playerHasJustCompletedHisMove(String color) {
		// Do nothing, all done by next clause
	}

	/**
	 * @param color Color of player
	 * @param row Row of player
	 * @param col Column of player
	 *
	 * @author Group-9
	 */
	@And("The new position of {string} is {int}:{int}")
	public void playerHasJustCompletedHisMove(String color, int row, int col) {
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gpos = game.getCurrentPosition();
		Tile tile = QuoridorController.getTileFromRowAndColumn(row, col);
		switch (color) {
		case "white":
			gpos.getWhitePosition().setTile(tile);
			break;
		case "black":
			gpos.getBlackPosition().setTile(tile);
			break;
		}
	}

	/**
	 * @param color Color of player
	 *
	 * @author Group-9
	 */
	@And("The clock of {string} is more than zero")
	public void clockIsMoreThanZero(String color) {
		Time t = null;
		switch (color) {
		case "white":
			t = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime();
			break;
		case "black":
			t = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().getRemainingTime();
			break;
		}
		
		// One of the HH:MM:SS fields must be greater than zero
		Assert.assertTrue(t.getHours() > 0 || t.getMinutes() > 0 || t.getSeconds() > 0);
	}

	/**
	 * @param color Color of player
	 *
	 * @author Group-9
	 */
	@And("The clock of {string} counts down to zero")
	public void clockCountsDownToZero(String color) {
		// Set remaining time to zero
		switch (color) {
		case "white":
			QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().setRemainingTime(new Time(0, 0, 0));
			QuoridorController.runClockForPlayer(Color.WHITE);
			break;
		case "black":
			QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer().setRemainingTime(new Time(0, 0, 0));
			QuoridorController.runClockForPlayer(Color.BLACK);
			break;
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			// Welp...
		}
	}

	/**
	 *
	 * @author Group-9
	 */
	@When("Checking of game result is initated")
	public void checkingOfGameResultIsInitiated() {
		QuoridorController.initiateCheckGameResult();
	}

	/**
	 * @param status regex: [Pp]ending|whiteWon|blackWon|Drawn
	 *
	 * @author Group-9
	 */
	@Then("Game result shall be {string}")
	public void gameResultShallBe(String status) {
		final Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		switch (status.toLowerCase()) {
		case "pending":
			Assert.assertEquals(GameStatus.Running, game.getGameStatus());
			break;
		case "whitewon":
			Assert.assertEquals(GameStatus.WhiteWon, game.getGameStatus());
			break;
		case "blackwon":
			Assert.assertEquals(GameStatus.BlackWon, game.getGameStatus());
			break;
		case "drawn":
			Assert.assertEquals(GameStatus.Draw, game.getGameStatus());
			break;
		default:
			throw new AssertionError("Unhandled result case: " + status);
		}
	}

	/**
	 *
	 * @author Group-9
	 */
	@And("The game shall no longer be running")
	public void gameShallNoLongerBeRunning() {
		final Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Assert.assertNotEquals(GameStatus.Running, game.getGameStatus());
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
		for (int i = 1; i <= 20; i++) {
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
			for (int j = 1; j <= 10; j++) {
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
		// Tile indices start from 0 -> tiles with indices 4 and 8*9+4=76 are the starting
		// positions
		Tile player1StartPos = quoridor.getBoard().getTile(4);
		Tile player2StartPos = quoridor.getBoard().getTile(76);

		Game game = new Game(GameStatus.Running, MoveMode.PlayerMove, quoridor);
		game.setWhitePlayer(players.get(0));
		game.setBlackPlayer(players.get(1));

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);

		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, players.get(0), game);

		// Add the walls as in stock for the players
		for (int j = 1; j <= 10; j++) {
			Wall wall = Wall.getWithId(j);
			gamePosition.addWhiteWallsInStock(wall);
		}
		for (int j = 1; j <= 10; j++) {
			Wall wall = Wall.getWithId(j + 10);
			gamePosition.addBlackWallsInStock(wall);
		}

		game.setCurrentPosition(gamePosition);
	}
	
	
	
	
	
	
	
	
}
