package ca.mcgill.ecse223.quoridor.features;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.TOWall;
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
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import cucumber.api.PendingException;

public class CucumberStepDefinitions {

	private Quoridor quoridor;
	private Board board;
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private Game game;

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
		theGameIsNotRunning();
		createAndStartGame();
	}

	@And("^It is my turn to move$")
	public void itIsMyTurnToMove() throws Throwable {
		currentPlayer = player1;
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(currentPlayer);
	}

	@Given("The following walls exist:")
	public void theFollowingWallsExist(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		// keys: wrow, wcol, wdir
		Player[] players = { player1, player2 };
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
			new WallMove(0, 1, players[playerIdx], board.getTile((wrow - 1) * 9 + wcol - 1), game, direction, wall);
			if (playerIdx == 0) {
				game.getCurrentPosition().removeWhiteWallsInStock(wall);
				game.getCurrentPosition().addWhiteWallsOnBoard(wall);
			} else {
				game.getCurrentPosition().removeBlackWallsInStock(wall);
				game.getCurrentPosition().addBlackWallsOnBoard(wall);
			}
			wallIdxForPlayer = wallIdxForPlayer + playerIdx;
			playerIdx++;
			playerIdx = playerIdx % 2;
		}
		System.out.println();

	}

	@And("I do not have a wall in my hand")
	public void iDoNotHaveAWallInMyHand() {
		// Walls are in stock for all players
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
	
	// ***** SavePosition.feature *****

	private String fileName;
	private boolean fileOverwriteFlag;

	@Given("No file {word} exists in the filesystem")
	public void noFileExistsInTheFilesystem(String filename) {
		final File file = new File(filename);
		Assert.assertFalse(file.exists());
	}

	@When("The user initiates to save the game with name {word}")
	public void userInitiatesToSaveTheGameWithName(String filename) {
		this.fileName = filename;
	}

	@Then("A file with {word} is created in the filesystem")
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

	@Given("File {word} exists in the filesystem")
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
	
	@Then("File with {word} is updated in the filesystem")
	public void fileIsUpdatedInTheFilesystem(String filename) {
		// Just a sanity check
		Assert.assertEquals(filename, this.fileName);

		// Actual file-updated check
		Assert.assertTrue(this.fileOverwriteFlag);
	}
	
	@And("The user cancels to overwrite existing file")
	public void userCancelsToOverwriteExistingFile() {
		try {
			// Pass false since we are not overwriting a filex
			this.fileOverwriteFlag = QuoridorController.savePosition(this.fileName, false);
		} catch (IOException ex) {
			Assert.fail("No IOException should happen: " + ex.getMessage());
		}
	}
	
	@Then("File {word} is not changed in the filesystem")
	public void fileIsNotChangedInTheFilesystem(String filename) {
		// Just a sanity check
		Assert.assertEquals(filename, this.fileName);

		// Actual file-updated check
		Assert.assertFalse(this.fileOverwriteFlag);
	}
	
	// ***** LoadPosition.feature *****

	private boolean positionValidFlag;

	@When("I initiate to load a saved game {word}")
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

	@Then("It is {word}'s turn")
	public void itIsPlayersTurn(String playerName) {
		final TOPlayer player = QuoridorController.getPlayerOfCurrentTurn();
		Assert.assertNotNull(player);
		Assert.assertEquals(playerName, player.getName());
	}

	@And("{word} is at {int}:{int}")
	public void playerIsAtRowCol(String playerName, int row, int col) {
		final TOPlayer player = QuoridorController.getPlayerByName(playerName);
		Assert.assertNotNull(player);
		Assert.assertEquals(row, player.getRow());
		Assert.assertEquals(col, player.getColumn());
	}

	@And("{word} has a {word} wall at {int}:{int}")
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

	@And("Both players have {int} in their stacks")
	public void bothPlayersHaveWallCountInTheirStacks(int remainingWalls) {
		Assert.assertEquals(remainingWalls, QuoridorController.getWhiteWallsInStock());
		Assert.assertEquals(remainingWalls, QuoridorController.getBlackWallsInStock());
	}

	@And("The position to load is invalid")
	public void positionToLoadIsInvalid() {
		Assert.assertFalse(this.positionValidFlag);
	}
	
	@Then("The load returns {word}")
	public void loadReturns(String result) {
		if ("success".equals(result)) {
			Assert.assertTrue(this.positionValidFlag);
		} else if ("error".equals(result)) {
			Assert.assertFalse(this.positionValidFlag);
		} else {
			Assert.fail("Unknown result: " + result);
		}
	}
	
	// ***** GrabWall.feature *****
	
	@Given("I have more walls on stock")
	public void moreWallsOnStock() {
		
		Assert.assertTrue(QuoridorController.getWallsOwnedByPlayer(QuoridorController.getPlayerOfCurrentTurn().getName()) != null);
	
	}
	
	@When("I try to grab a wall from my stock")
	public void playerTryToGrabWall() {
		throw new PendingException();
	}
	
	@Then("I have a wall in my hand over the board")
	public void wallOverBoard() {
		throw new PendingException();
	}

	// ***********************************************
	// Clean up
	// ***********************************************

	// After each scenario, the test model is discarded
	@After
	public void tearDown() {
		// Avoid null pointer for step definitions that are not yet implemented.
		if (quoridor != null) {
			quoridor.delete();
			quoridor = null;
		}
	}

	// ***********************************************
	// Extracted helper methods
	// ***********************************************

	// Place your extracted methods below

	private void initQuoridorAndBoard() {
		quoridor = QuoridorApplication.getQuoridor();
		board = new Board(quoridor);
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				board.addTile(i, j);
			}
		}
	}

	private void createUsersAndPlayers(String userName1, String userName2) {
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
		player1 = new Player(new Time(thinkingTime), user1, 9, Direction.Horizontal);
		player2 = new Player(new Time(thinkingTime), user2, 1, Direction.Horizontal);

		Player[] players = { player1, player2 };

		// Create all walls. Walls with lower ID belong to player1,
		// while the second half belongs to player 2
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				new Wall(i * 10 + j, players[i]);
			}
		}
	}

	private void createAndStartGame() {
		// There are total 36 tiles in the first four rows and
		// indexing starts from 0 -> tiles with indices 36 and 36+8=44 are the starting
		// positions
		Tile player1StartPos = board.getTile(36);
		Tile player2StartPos = board.getTile(44);

		PlayerPosition player1Position = new PlayerPosition(player1, player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(player2, player2StartPos);

		game = new Game(GameStatus.Running, MoveMode.PlayerMove, player1, player2, quoridor);
		GamePosition gamePosition = new GamePosition(0, player1Position, player2Position, player1, game);

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
