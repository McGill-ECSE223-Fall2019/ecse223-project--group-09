package ca.mcgill.ecse223.quoridor.features;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWallCandidate;
import ca.mcgill.ecse223.quoridor.controller.Orientation;
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
	}
	
	@And("^I have a wall in my hand over the board$")
	public void iHaveAWallInMyHandOverTheBoard() throws Throwable {
		// GUI-related feature -- TODO for later
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
	
	@Given("A new game is initializing") //only once - correct if not
	public void newGameInitializing() {
		quoridor = QuoridorApplication.getQuoridor();
	}

	// ProvideOrSelectUserName.feature (Ada)
	// Scenario: Select existing user name

	@Given("Next player to set user name is <color>")
	public void nextPlayerToSetUserNameIsColor(String color) {
	}

	@And("There is existing user <username>")
	public void existingUser(boolean user) {
		Assert.assertTrue(user); 
	}

	@When("The player selects existing <username>") 
	public void playerSelectsExistingUsername(String user) {
		QuoridorController.selectUsername(user);
	}


	@Then("The name of player <color> in the new game shall be <username>")
	public void nameOfPlayerInNewGameShallBeUsername() {
	}

	//Scenario: Create new user name

	@And("There is no existing user")
	public void noExistingUser(boolean user) {
		Assert.assertFalse(user); 
	}
	
	@When("The player provides new user name: <username>")
	public void playerProvidesNewUserName(String user) {
		QuoridorController.createUsername(user);		
	}

	//Scenario: User name already exists

	@Then("The player shall be warned that <username> already exists") 
	public void playerShallBeWarnedThatUsernameAlreadyExists() {
		throw new PendingException(); 
	}
	
	// SetTotalThinkingTime.feature (Ada)

	@When("<min>:<sec> is set as the thinking time")
	public void setAsThinkingTime(Time remainingTime) {
		QuoridorController.setTime(remainingTime); 
	}

	@Then("Both players shall have <min>:<sec> remaining time left")
	public void bothPlayersShallHaveRemainingTimeLeft(Time remainingTime) {
		Assert.assertEquals(expected,actual);
	}

	// ***** SavePosition.feature *****

	private String fileName;
	private boolean fileOverwriteFlag;

	@Given("No file {string} exists in the filesystem")
	public void noFileExistsInTheFilesystem(String filename) {
		final File file = new File(filename);
		Assert.assertFalse(file.exists());
	}

	@When("The user initiates to save the game with name {string}")
	public void userInitiatesToSaveTheGameWithName(String filename) {
		this.fileName = filename;
	}

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

	@Given("File {string} exists in the filesystem")
	public void fileExistsInTheFilesystem(String filename) {
		final File file = new File(filename);
		Assert.assertTrue(file.exists());
	}

	@And("The user confirms to overwrite existing file")
	public void userConfirmsToOverwriteExistingFile() {
		try {
			// Pass true since we are overwriting a file
			this.fileOverwriteFlag = QuoridorController.savePosition(this.fileName, true);
		} catch (IOException ex) {
			Assert.fail("No IOException should happen: " + ex.getMessage());
		}
	}
	
	@Then("File with {string} is updated in the filesystem")
	public void fileIsUpdatedInTheFilesystem(String filename) {
		// Just a sanity check
		Assert.assertEquals(filename, this.fileName);

		// Actual file-updated check
		Assert.assertTrue(this.fileOverwriteFlag);
	}
	
	@And("The user cancels to overwrite existing file")
	public void userCancelsToOverwriteExistingFile() {
		try {
			// Pass false since we are not overwriting a file
			this.fileOverwriteFlag = QuoridorController.savePosition(this.fileName, false);
		} catch (IOException ex) {
			Assert.fail("No IOException should happen: " + ex.getMessage());
		}
	}
	
	@Then("File {string} shall not be changed in the filesystem")
	public void fileIsNotChangedInTheFilesystem(String filename) {
		// Just a sanity check
		Assert.assertEquals(filename, this.fileName);

		// Actual file-updated check
		Assert.assertFalse(this.fileOverwriteFlag);
	}
	
	// ***** LoadPosition.feature *****

	private boolean positionValidFlag;

	@When("I initiate to load a saved game {string}")
	public void iInitiateToLoadASavedGame(String filename) {
		try {
			this.positionValidFlag = QuoridorController.loadPosition(filename);
		} catch (IOException ex) {
			Assert.fail("No IOException should happen:" + ex.getMessage());
		}
	}

	@And("The position to load is valid")
	public void positionToLoadIsValid() {
		Assert.assertTrue(this.positionValidFlag);
	}

	@Then("It shall be {string}'s turn")
	public void itShallBePlayersTurn(String playerName) {
		final TOPlayer player = QuoridorController.getPlayerOfCurrentTurn();
		Assert.assertNotNull(player);
		Assert.assertEquals(playerName, player.getName());
	}

	@And("{string} shall be at {int}:{int}")
	public void playerIsAtRowCol(String playerName, int row, int col) {
		final TOPlayer player = QuoridorController.getPlayerByName(playerName);
		Assert.assertNotNull(player);
		Assert.assertEquals(row, player.getRow());
		Assert.assertEquals(col, player.getColumn());
	}

	@And("{string} shall have a {string} wall at {int}:{int}")
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

	@And("Both players shall have {int} in their stacks")
	public void bothPlayersHaveWallCountInTheirStacks(int remainingWalls) {
		Assert.assertEquals(remainingWalls, QuoridorController.getWallsInStockOfColoredPawn("black"));
		Assert.assertEquals(remainingWalls, QuoridorController.getWallsInStockOfColoredPawn("white"));
	}

	@And("The position to load is invalid")
	public void positionToLoadIsInvalid() {
		Assert.assertFalse(this.positionValidFlag);
	}
	
	@Then("The load shall return an error")
	public void loadReturns() {
		Assert.assertFalse(this.positionValidFlag);
	}
	
	// ***** GrabWall.feature *****
	
		//Start wall placement
	
	@Given("I have more walls on stock")
	public void moreWallsOnStock() {
		Assert.assertNotNull(QuoridorController.getWallsOwnedByPlayer(QuoridorController.getPlayerOfCurrentTurn().getName()));
	
	}
	
	@When("I try to grab a wall from my stock")
	public void playerTryToGrabWall() {
		throw new PendingException();
	}
	
	@Then("I have a wall in my hand over the board")
	public void wallOverBoard() {
		Assert.assertTrue(QuoridorController.getCurrentGrabbedWall().grabbed);	
	}
	
	@And("The wall in my hand should disappear from my stock")
	public void removeWallFromStock() {
		Assert.assertTrue(QuoridorController.getCurrentGrabbedWall().grabbed);
		QuoridorController.getWallsOwnedByPlayer(QuoridorController.getPlayerOfCurrentTurn().getName()).remove(QuoridorController.getCurrentGrabbedWall());
		
	}
	
	@And("A wall move candidate shall be created at initial position")
	public void createWallMoveCandidate() {
		Assert.assertTrue(QuoridorController.getCurrentGrabbedWall().grabbed);
		QuoridorController.getCurrentGrabbedWall().createWallCandidate();
		throw new PendingException();
		
	}
		
		//No more walls in stock
	@Given("I have no more walls on stock")
	public boolean noMoreWallsOnStock() {
		Assert.assertNull(QuoridorController.getWallsOwnedByPlayer(QuoridorController.getPlayerOfCurrentTurn().getName()));
		return true;
	}
	
	@Then("I should be notified that I have no more walls")
	public void notifNoMoreWalls() {
		Assert.assertTrue(noMoreWallsOnStock());
		throw new PendingException();
		
	}
	
	@But("I do not have a wall in my hand ")
	public void noWallInHand() {
		Assert.assertFalse(QuoridorController.getPlayerOfCurrentTurn().hasWallInHand());
	}
	
	// ***** MoveWall.feature *****
	
	@Given("A wall move candidate exists with {word} at position ({int}, {int})")
	public void wallCandidateExists() {
		Assert.assertTrue(QuoridorController.getCurrentGrabbedWall().getWallCandidate() != null);
	}
	
	
	// ***** RotateWall feature ***** @Author Mohamed Mohamed
	
	@Given("The game is running")
	public void gameisRunning() {
		//check if the fame is running
	}
	
	// ***** ValidatePosition.feature *****

	private int row;
	private int column;
	private Orientation orientation;

	private boolean positionValidityFlag;

	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void gamePositionIsSuppliedWithPawn(int row, int column) {
		this.row = row;
		this.column = column;
		// hack for this#validationOfThePositionIsInitiated to work!
		this.orientation = null;
	}

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

	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void gamePositionIsSuppliedWithWall(int row, int column, String orientation) {
		this.row = row;
		this.column = column;
		this.orientation = Orientation.valueOf(orientation.toUpperCase());
	}

	@Then("The position shall be valid")
	public void positionShallBeValid() {
		Assert.assertTrue(this.positionValidityFlag);
	}

	@Then("The position shall be invalid")
	public void positionShallBeInvalid() {
		Assert.assertFalse(this.positionValidityFlag);
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