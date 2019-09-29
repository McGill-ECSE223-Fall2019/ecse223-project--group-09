/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 3 "../../../../../Quoridor.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private long timeRemaining;

  //Player Associations
  private List<Wall> wallsLeft;
  private Username username;
  private List<Wall> wallsOnBoard;
  private Pawn pawn;
  private List<Game> games;
  private Game game;
  private List<Move> moves;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(long aTimeRemaining, Username aUsername, Pawn aPawn, Quoridor aQuoridor)
  {
    timeRemaining = aTimeRemaining;
    wallsLeft = new ArrayList<Wall>();
    if (aUsername == null || aUsername.getPlayer() != null)
    {
      throw new RuntimeException("Unable to create Player due to aUsername");
    }
    username = aUsername;
    wallsOnBoard = new ArrayList<Wall>();
    if (aPawn == null || aPawn.getPlayer() != null)
    {
      throw new RuntimeException("Unable to create Player due to aPawn");
    }
    pawn = aPawn;
    games = new ArrayList<Game>();
    moves = new ArrayList<Move>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create player due to quoridor");
    }
  }

  public Player(long aTimeRemaining, String aUsernameForUsername, Quoridor aQuoridorForUsername, Position aCurrentPositionForPawn, Board aBoardForPawn, Quoridor aQuoridorForPawn, String aColorForPawn, Quoridor aQuoridor)
  {
    timeRemaining = aTimeRemaining;
    wallsLeft = new ArrayList<Wall>();
    username = new Username(aUsernameForUsername, this, aQuoridorForUsername);
    wallsOnBoard = new ArrayList<Wall>();
    pawn = new Pawn(aCurrentPositionForPawn, aBoardForPawn, aQuoridorForPawn, aColorForPawn, this);
    games = new ArrayList<Game>();
    moves = new ArrayList<Move>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create player due to quoridor");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTimeRemaining(long aTimeRemaining)
  {
    boolean wasSet = false;
    timeRemaining = aTimeRemaining;
    wasSet = true;
    return wasSet;
  }

  public long getTimeRemaining()
  {
    return timeRemaining;
  }
  /* Code from template association_GetMany */
  public Wall getWallsLeft(int index)
  {
    Wall aWallsLeft = wallsLeft.get(index);
    return aWallsLeft;
  }

  public List<Wall> getWallsLeft()
  {
    List<Wall> newWallsLeft = Collections.unmodifiableList(wallsLeft);
    return newWallsLeft;
  }

  public int numberOfWallsLeft()
  {
    int number = wallsLeft.size();
    return number;
  }

  public boolean hasWallsLeft()
  {
    boolean has = wallsLeft.size() > 0;
    return has;
  }

  public int indexOfWallsLeft(Wall aWallsLeft)
  {
    int index = wallsLeft.indexOf(aWallsLeft);
    return index;
  }
  /* Code from template association_GetOne */
  public Username getUsername()
  {
    return username;
  }
  /* Code from template association_GetMany */
  public Wall getWallsOnBoard(int index)
  {
    Wall aWallsOnBoard = wallsOnBoard.get(index);
    return aWallsOnBoard;
  }

  public List<Wall> getWallsOnBoard()
  {
    List<Wall> newWallsOnBoard = Collections.unmodifiableList(wallsOnBoard);
    return newWallsOnBoard;
  }

  public int numberOfWallsOnBoard()
  {
    int number = wallsOnBoard.size();
    return number;
  }

  public boolean hasWallsOnBoard()
  {
    boolean has = wallsOnBoard.size() > 0;
    return has;
  }

  public int indexOfWallsOnBoard(Wall aWallsOnBoard)
  {
    int index = wallsOnBoard.indexOf(aWallsOnBoard);
    return index;
  }
  /* Code from template association_GetOne */
  public Pawn getPawn()
  {
    return pawn;
  }
  /* Code from template association_GetMany */
  public Game getGame(int index)
  {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames()
  {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames()
  {
    int number = games.size();
    return number;
  }

  public boolean hasGames()
  {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame)
  {
    int index = games.indexOf(aGame);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }

  public boolean hasGame()
  {
    boolean has = game != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Move getMove(int index)
  {
    Move aMove = moves.get(index);
    return aMove;
  }

  public List<Move> getMoves()
  {
    List<Move> newMoves = Collections.unmodifiableList(moves);
    return newMoves;
  }

  public int numberOfMoves()
  {
    int number = moves.size();
    return number;
  }

  public boolean hasMoves()
  {
    boolean has = moves.size() > 0;
    return has;
  }

  public int indexOfMove(Move aMove)
  {
    int index = moves.indexOf(aMove);
    return index;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWallsLeft()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWallsLeft()
  {
    return 10;
  }
  /* Code from template association_AddOptionalNToOne */
  public Wall addWallsLeft(Position aCurrentPosition, Board aBoard, Quoridor aQuoridor)
  {
    if (numberOfWallsLeft() >= maximumNumberOfWallsLeft())
    {
      return null;
    }
    else
    {
      return new Wall(aCurrentPosition, aBoard, aQuoridor, this);
    }
  }

  public boolean addWallsLeft(Wall aWallsLeft)
  {
    boolean wasAdded = false;
    if (wallsLeft.contains(aWallsLeft)) { return false; }
    if (numberOfWallsLeft() >= maximumNumberOfWallsLeft())
    {
      return wasAdded;
    }

    Player existingAssociatedPlayer = aWallsLeft.getAssociatedPlayer();
    boolean isNewAssociatedPlayer = existingAssociatedPlayer != null && !this.equals(existingAssociatedPlayer);
    if (isNewAssociatedPlayer)
    {
      aWallsLeft.setAssociatedPlayer(this);
    }
    else
    {
      wallsLeft.add(aWallsLeft);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWallsLeft(Wall aWallsLeft)
  {
    boolean wasRemoved = false;
    //Unable to remove aWallsLeft, as it must always have a associatedPlayer
    if (!this.equals(aWallsLeft.getAssociatedPlayer()))
    {
      wallsLeft.remove(aWallsLeft);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWallsLeftAt(Wall aWallsLeft, int index)
  {  
    boolean wasAdded = false;
    if(addWallsLeft(aWallsLeft))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWallsLeft()) { index = numberOfWallsLeft() - 1; }
      wallsLeft.remove(aWallsLeft);
      wallsLeft.add(index, aWallsLeft);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWallsLeftAt(Wall aWallsLeft, int index)
  {
    boolean wasAdded = false;
    if(wallsLeft.contains(aWallsLeft))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWallsLeft()) { index = numberOfWallsLeft() - 1; }
      wallsLeft.remove(aWallsLeft);
      wallsLeft.add(index, aWallsLeft);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWallsLeftAt(aWallsLeft, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWallsOnBoard()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWallsOnBoard()
  {
    return 10;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addWallsOnBoard(Wall aWallsOnBoard)
  {
    boolean wasAdded = false;
    if (wallsOnBoard.contains(aWallsOnBoard)) { return false; }
    if (numberOfWallsOnBoard() >= maximumNumberOfWallsOnBoard())
    {
      return wasAdded;
    }

    wallsOnBoard.add(aWallsOnBoard);
    if (aWallsOnBoard.indexOfOwner(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aWallsOnBoard.addOwner(this);
      if (!wasAdded)
      {
        wallsOnBoard.remove(aWallsOnBoard);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeWallsOnBoard(Wall aWallsOnBoard)
  {
    boolean wasRemoved = false;
    if (!wallsOnBoard.contains(aWallsOnBoard))
    {
      return wasRemoved;
    }

    int oldIndex = wallsOnBoard.indexOf(aWallsOnBoard);
    wallsOnBoard.remove(oldIndex);
    if (aWallsOnBoard.indexOfOwner(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aWallsOnBoard.removeOwner(this);
      if (!wasRemoved)
      {
        wallsOnBoard.add(oldIndex,aWallsOnBoard);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetOptionalNToMany */
  public boolean setWallsOnBoard(Wall... newWallsOnBoard)
  {
    boolean wasSet = false;
    ArrayList<Wall> verifiedWallsOnBoard = new ArrayList<Wall>();
    for (Wall aWallsOnBoard : newWallsOnBoard)
    {
      if (verifiedWallsOnBoard.contains(aWallsOnBoard))
      {
        continue;
      }
      verifiedWallsOnBoard.add(aWallsOnBoard);
    }

    if (verifiedWallsOnBoard.size() != newWallsOnBoard.length || verifiedWallsOnBoard.size() > maximumNumberOfWallsOnBoard())
    {
      return wasSet;
    }

    ArrayList<Wall> oldWallsOnBoard = new ArrayList<Wall>(wallsOnBoard);
    wallsOnBoard.clear();
    for (Wall aNewWallsOnBoard : verifiedWallsOnBoard)
    {
      wallsOnBoard.add(aNewWallsOnBoard);
      if (oldWallsOnBoard.contains(aNewWallsOnBoard))
      {
        oldWallsOnBoard.remove(aNewWallsOnBoard);
      }
      else
      {
        aNewWallsOnBoard.addOwner(this);
      }
    }

    for (Wall anOldWallsOnBoard : oldWallsOnBoard)
    {
      anOldWallsOnBoard.removeOwner(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWallsOnBoardAt(Wall aWallsOnBoard, int index)
  {  
    boolean wasAdded = false;
    if(addWallsOnBoard(aWallsOnBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWallsOnBoard()) { index = numberOfWallsOnBoard() - 1; }
      wallsOnBoard.remove(aWallsOnBoard);
      wallsOnBoard.add(index, aWallsOnBoard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWallsOnBoardAt(Wall aWallsOnBoard, int index)
  {
    boolean wasAdded = false;
    if(wallsOnBoard.contains(aWallsOnBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWallsOnBoard()) { index = numberOfWallsOnBoard() - 1; }
      wallsOnBoard.remove(aWallsOnBoard);
      wallsOnBoard.add(index, aWallsOnBoard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addWallsOnBoardAt(aWallsOnBoard, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    games.add(aGame);
    if (aGame.indexOfCurrentPlayer(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aGame.addCurrentPlayer(this);
      if (!wasAdded)
      {
        games.remove(aGame);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    if (!games.contains(aGame))
    {
      return wasRemoved;
    }

    int oldIndex = games.indexOf(aGame);
    games.remove(oldIndex);
    if (aGame.indexOfCurrentPlayer(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aGame.removeCurrentPlayer(this);
      if (!wasRemoved)
      {
        games.add(oldIndex,aGame);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index)
  {  
    boolean wasAdded = false;
    if(addGame(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(games.contains(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameAt(aGame, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setGame(Game aNewGame)
  {
    boolean wasSet = false;
    if (game != null && !game.equals(aNewGame) && equals(game.getWinner()))
    {
      //Unable to setGame, as existing game would become an orphan
      return wasSet;
    }

    game = aNewGame;
    Player anOldWinner = aNewGame != null ? aNewGame.getWinner() : null;

    if (!this.equals(anOldWinner))
    {
      if (anOldWinner != null)
      {
        anOldWinner.game = null;
      }
      if (game != null)
      {
        game.setWinner(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addMove(Move aMove)
  {
    boolean wasAdded = false;
    if (moves.contains(aMove)) { return false; }
    Player existingPlayer = aMove.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
    if (isNewPlayer)
    {
      aMove.setPlayer(this);
    }
    else
    {
      moves.add(aMove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMove(Move aMove)
  {
    boolean wasRemoved = false;
    //Unable to remove aMove, as it must always have a player
    if (!this.equals(aMove.getPlayer()))
    {
      moves.remove(aMove);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMoveAt(Move aMove, int index)
  {  
    boolean wasAdded = false;
    if(addMove(aMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoves()) { index = numberOfMoves() - 1; }
      moves.remove(aMove);
      moves.add(index, aMove);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMoveAt(Move aMove, int index)
  {
    boolean wasAdded = false;
    if(moves.contains(aMove))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoves()) { index = numberOfMoves() - 1; }
      moves.remove(aMove);
      moves.add(index, aMove);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMoveAt(aMove, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setQuoridor(Quoridor aQuoridor)
  {
    boolean wasSet = false;
    if (aQuoridor == null)
    {
      return wasSet;
    }

    Quoridor existingQuoridor = quoridor;
    quoridor = aQuoridor;
    if (existingQuoridor != null && !existingQuoridor.equals(aQuoridor))
    {
      existingQuoridor.removePlayer(this);
    }
    quoridor.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=wallsLeft.size(); i > 0; i--)
    {
      Wall aWallsLeft = wallsLeft.get(i - 1);
      aWallsLeft.delete();
    }
    Username existingUsername = username;
    username = null;
    if (existingUsername != null)
    {
      existingUsername.delete();
    }
    ArrayList<Wall> copyOfWallsOnBoard = new ArrayList<Wall>(wallsOnBoard);
    wallsOnBoard.clear();
    for(Wall aWallsOnBoard : copyOfWallsOnBoard)
    {
      aWallsOnBoard.removeOwner(this);
    }
    Pawn existingPawn = pawn;
    pawn = null;
    if (existingPawn != null)
    {
      existingPawn.delete();
    }
    ArrayList<Game> copyOfGames = new ArrayList<Game>(games);
    games.clear();
    for(Game aGame : copyOfGames)
    {
      if (aGame.numberOfCurrentPlayers() <= Game.minimumNumberOfCurrentPlayers())
      {
        aGame.delete();
      }
      else
      {
        aGame.removeCurrentPlayer(this);
      }
    }
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
    for(int i=moves.size(); i > 0; i--)
    {
      Move aMove = moves.get(i - 1);
      aMove.delete();
    }
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "timeRemaining" + ":" + getTimeRemaining()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "username = "+(getUsername()!=null?Integer.toHexString(System.identityHashCode(getUsername())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "pawn = "+(getPawn()!=null?Integer.toHexString(System.identityHashCode(getPawn())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}