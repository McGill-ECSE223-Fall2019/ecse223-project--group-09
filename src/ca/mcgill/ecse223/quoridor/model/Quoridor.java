/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 91 "../../../../../Quoridor.ump"
public class Quoridor
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Quoridor Associations
  private List<Game> games;
  private List<Player> players;
  private List<Username> usernames;
  private List<Move> moves;
  private List<GamePiece> gamePieces;
  private List<Position> positions;
  private List<Board> boards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Quoridor()
  {
    games = new ArrayList<Game>();
    players = new ArrayList<Player>();
    usernames = new ArrayList<Username>();
    moves = new ArrayList<Move>();
    gamePieces = new ArrayList<GamePiece>();
    positions = new ArrayList<Position>();
    boards = new ArrayList<Board>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public Username getUsername(int index)
  {
    Username aUsername = usernames.get(index);
    return aUsername;
  }

  public List<Username> getUsernames()
  {
    List<Username> newUsernames = Collections.unmodifiableList(usernames);
    return newUsernames;
  }

  public int numberOfUsernames()
  {
    int number = usernames.size();
    return number;
  }

  public boolean hasUsernames()
  {
    boolean has = usernames.size() > 0;
    return has;
  }

  public int indexOfUsername(Username aUsername)
  {
    int index = usernames.indexOf(aUsername);
    return index;
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
  /* Code from template association_GetMany */
  public GamePiece getGamePiece(int index)
  {
    GamePiece aGamePiece = gamePieces.get(index);
    return aGamePiece;
  }

  public List<GamePiece> getGamePieces()
  {
    List<GamePiece> newGamePieces = Collections.unmodifiableList(gamePieces);
    return newGamePieces;
  }

  public int numberOfGamePieces()
  {
    int number = gamePieces.size();
    return number;
  }

  public boolean hasGamePieces()
  {
    boolean has = gamePieces.size() > 0;
    return has;
  }

  public int indexOfGamePiece(GamePiece aGamePiece)
  {
    int index = gamePieces.indexOf(aGamePiece);
    return index;
  }
  /* Code from template association_GetMany */
  public Position getPosition(int index)
  {
    Position aPosition = positions.get(index);
    return aPosition;
  }

  public List<Position> getPositions()
  {
    List<Position> newPositions = Collections.unmodifiableList(positions);
    return newPositions;
  }

  public int numberOfPositions()
  {
    int number = positions.size();
    return number;
  }

  public boolean hasPositions()
  {
    boolean has = positions.size() > 0;
    return has;
  }

  public int indexOfPosition(Position aPosition)
  {
    int index = positions.indexOf(aPosition);
    return index;
  }
  /* Code from template association_GetMany */
  public Board getBoard(int index)
  {
    Board aBoard = boards.get(index);
    return aBoard;
  }

  public List<Board> getBoards()
  {
    List<Board> newBoards = Collections.unmodifiableList(boards);
    return newBoards;
  }

  public int numberOfBoards()
  {
    int number = boards.size();
    return number;
  }

  public boolean hasBoards()
  {
    boolean has = boards.size() > 0;
    return has;
  }

  public int indexOfBoard(Board aBoard)
  {
    int index = boards.indexOf(aBoard);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Game addGame(int aId, Player aWinner, Board aBoard, Player... allCurrentPlayers)
  {
    return new Game(aId, aWinner, aBoard, this, allCurrentPlayers);
  }

  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    Quoridor existingQuoridor = aGame.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aGame.setQuoridor(this);
    }
    else
    {
      games.add(aGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    //Unable to remove aGame, as it must always have a quoridor
    if (!this.equals(aGame.getQuoridor()))
    {
      games.remove(aGame);
      wasRemoved = true;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Player addPlayer(long aTimeRemaining, Username aUsername, Pawn aPawn)
  {
    return new Player(aTimeRemaining, aUsername, aPawn, this);
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    Quoridor existingQuoridor = aPlayer.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aPlayer.setQuoridor(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a quoridor
    if (!this.equals(aPlayer.getQuoridor()))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsernames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Username addUsername(String aUsername, Player aPlayer)
  {
    return new Username(aUsername, aPlayer, this);
  }

  public boolean addUsername(Username aUsername)
  {
    boolean wasAdded = false;
    if (usernames.contains(aUsername)) { return false; }
    Quoridor existingQuoridor = aUsername.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aUsername.setQuoridor(this);
    }
    else
    {
      usernames.add(aUsername);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUsername(Username aUsername)
  {
    boolean wasRemoved = false;
    //Unable to remove aUsername, as it must always have a quoridor
    if (!this.equals(aUsername.getQuoridor()))
    {
      usernames.remove(aUsername);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUsernameAt(Username aUsername, int index)
  {  
    boolean wasAdded = false;
    if(addUsername(aUsername))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsernames()) { index = numberOfUsernames() - 1; }
      usernames.remove(aUsername);
      usernames.add(index, aUsername);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUsernameAt(Username aUsername, int index)
  {
    boolean wasAdded = false;
    if(usernames.contains(aUsername))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsernames()) { index = numberOfUsernames() - 1; }
      usernames.remove(aUsername);
      usernames.add(index, aUsername);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUsernameAt(aUsername, index);
    }
    return wasAdded;
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
    Quoridor existingQuoridor = aMove.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aMove.setQuoridor(this);
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
    //Unable to remove aMove, as it must always have a quoridor
    if (!this.equals(aMove.getQuoridor()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGamePieces()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addGamePiece(GamePiece aGamePiece)
  {
    boolean wasAdded = false;
    if (gamePieces.contains(aGamePiece)) { return false; }
    Quoridor existingQuoridor = aGamePiece.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aGamePiece.setQuoridor(this);
    }
    else
    {
      gamePieces.add(aGamePiece);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGamePiece(GamePiece aGamePiece)
  {
    boolean wasRemoved = false;
    //Unable to remove aGamePiece, as it must always have a quoridor
    if (!this.equals(aGamePiece.getQuoridor()))
    {
      gamePieces.remove(aGamePiece);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGamePieceAt(GamePiece aGamePiece, int index)
  {  
    boolean wasAdded = false;
    if(addGamePiece(aGamePiece))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGamePieces()) { index = numberOfGamePieces() - 1; }
      gamePieces.remove(aGamePiece);
      gamePieces.add(index, aGamePiece);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGamePieceAt(GamePiece aGamePiece, int index)
  {
    boolean wasAdded = false;
    if(gamePieces.contains(aGamePiece))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGamePieces()) { index = numberOfGamePieces() - 1; }
      gamePieces.remove(aGamePiece);
      gamePieces.add(index, aGamePiece);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGamePieceAt(aGamePiece, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPositions()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Position addPosition(int aRow, String aColumn, Board aBoard)
  {
    return new Position(aRow, aColumn, aBoard, this);
  }

  public boolean addPosition(Position aPosition)
  {
    boolean wasAdded = false;
    if (positions.contains(aPosition)) { return false; }
    Quoridor existingQuoridor = aPosition.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aPosition.setQuoridor(this);
    }
    else
    {
      positions.add(aPosition);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePosition(Position aPosition)
  {
    boolean wasRemoved = false;
    //Unable to remove aPosition, as it must always have a quoridor
    if (!this.equals(aPosition.getQuoridor()))
    {
      positions.remove(aPosition);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPositionAt(Position aPosition, int index)
  {  
    boolean wasAdded = false;
    if(addPosition(aPosition))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPositions()) { index = numberOfPositions() - 1; }
      positions.remove(aPosition);
      positions.add(index, aPosition);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePositionAt(Position aPosition, int index)
  {
    boolean wasAdded = false;
    if(positions.contains(aPosition))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPositions()) { index = numberOfPositions() - 1; }
      positions.remove(aPosition);
      positions.add(index, aPosition);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPositionAt(aPosition, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBoards()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Board addBoard(Game aCurrentGame)
  {
    return new Board(this, aCurrentGame);
  }

  public boolean addBoard(Board aBoard)
  {
    boolean wasAdded = false;
    if (boards.contains(aBoard)) { return false; }
    Quoridor existingQuoridor = aBoard.getQuoridor();
    boolean isNewQuoridor = existingQuoridor != null && !this.equals(existingQuoridor);
    if (isNewQuoridor)
    {
      aBoard.setQuoridor(this);
    }
    else
    {
      boards.add(aBoard);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBoard(Board aBoard)
  {
    boolean wasRemoved = false;
    //Unable to remove aBoard, as it must always have a quoridor
    if (!this.equals(aBoard.getQuoridor()))
    {
      boards.remove(aBoard);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBoardAt(Board aBoard, int index)
  {  
    boolean wasAdded = false;
    if(addBoard(aBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoards()) { index = numberOfBoards() - 1; }
      boards.remove(aBoard);
      boards.add(index, aBoard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBoardAt(Board aBoard, int index)
  {
    boolean wasAdded = false;
    if(boards.contains(aBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoards()) { index = numberOfBoards() - 1; }
      boards.remove(aBoard);
      boards.add(index, aBoard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBoardAt(aBoard, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (games.size() > 0)
    {
      Game aGame = games.get(games.size() - 1);
      aGame.delete();
      games.remove(aGame);
    }
    
    while (players.size() > 0)
    {
      Player aPlayer = players.get(players.size() - 1);
      aPlayer.delete();
      players.remove(aPlayer);
    }
    
    while (usernames.size() > 0)
    {
      Username aUsername = usernames.get(usernames.size() - 1);
      aUsername.delete();
      usernames.remove(aUsername);
    }
    
    while (moves.size() > 0)
    {
      Move aMove = moves.get(moves.size() - 1);
      aMove.delete();
      moves.remove(aMove);
    }
    
    while (gamePieces.size() > 0)
    {
      GamePiece aGamePiece = gamePieces.get(gamePieces.size() - 1);
      aGamePiece.delete();
      gamePieces.remove(aGamePiece);
    }
    
    while (positions.size() > 0)
    {
      Position aPosition = positions.get(positions.size() - 1);
      aPosition.delete();
      positions.remove(aPosition);
    }
    
    while (boards.size() > 0)
    {
      Board aBoard = boards.get(boards.size() - 1);
      aBoard.delete();
      boards.remove(aBoard);
    }
    
  }

}