/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;

// line 35 "../../../../../Quoridor.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private Quoridor quoridor;
  private Game currentGame;
  private List<GamePiece> onBoardPieces;
  private List<Position> position;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Quoridor aQuoridor, Game aCurrentGame)
  {
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create board due to quoridor");
    }
    if (aCurrentGame == null || aCurrentGame.getBoard() != null)
    {
      throw new RuntimeException("Unable to create Board due to aCurrentGame");
    }
    currentGame = aCurrentGame;
    onBoardPieces = new ArrayList<GamePiece>();
    position = new ArrayList<Position>();
  }

  public Board(Quoridor aQuoridor, int aIdForCurrentGame, Player aWinnerForCurrentGame, Quoridor aQuoridorForCurrentGame, Player... allCurrentPlayersForCurrentGame)
  {
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create board due to quoridor");
    }
    currentGame = new Game(aIdForCurrentGame, aWinnerForCurrentGame, this, aQuoridorForCurrentGame, allCurrentPlayersForCurrentGame);
    onBoardPieces = new ArrayList<GamePiece>();
    position = new ArrayList<Position>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_GetOne */
  public Game getCurrentGame()
  {
    return currentGame;
  }
  /* Code from template association_GetMany */
  public GamePiece getOnBoardPiece(int index)
  {
    GamePiece aOnBoardPiece = onBoardPieces.get(index);
    return aOnBoardPiece;
  }

  public List<GamePiece> getOnBoardPieces()
  {
    List<GamePiece> newOnBoardPieces = Collections.unmodifiableList(onBoardPieces);
    return newOnBoardPieces;
  }

  public int numberOfOnBoardPieces()
  {
    int number = onBoardPieces.size();
    return number;
  }

  public boolean hasOnBoardPieces()
  {
    boolean has = onBoardPieces.size() > 0;
    return has;
  }

  public int indexOfOnBoardPiece(GamePiece aOnBoardPiece)
  {
    int index = onBoardPieces.indexOf(aOnBoardPiece);
    return index;
  }
  /* Code from template association_GetMany */
  public Position getPosition(int index)
  {
    Position aPosition = position.get(index);
    return aPosition;
  }

  public List<Position> getPosition()
  {
    List<Position> newPosition = Collections.unmodifiableList(position);
    return newPosition;
  }

  public int numberOfPosition()
  {
    int number = position.size();
    return number;
  }

  public boolean hasPosition()
  {
    boolean has = position.size() > 0;
    return has;
  }

  public int indexOfPosition(Position aPosition)
  {
    int index = position.indexOf(aPosition);
    return index;
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
      existingQuoridor.removeBoard(this);
    }
    quoridor.addBoard(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOnBoardPieces()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfOnBoardPieces()
  {
    return 24;
  }
  /* Code from template association_AddOptionalNToOne */


  public boolean addOnBoardPiece(GamePiece aOnBoardPiece)
  {
    boolean wasAdded = false;
    if (onBoardPieces.contains(aOnBoardPiece)) { return false; }
    if (numberOfOnBoardPieces() >= maximumNumberOfOnBoardPieces())
    {
      return wasAdded;
    }

    Board existingBoard = aOnBoardPiece.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
    if (isNewBoard)
    {
      aOnBoardPiece.setBoard(this);
    }
    else
    {
      onBoardPieces.add(aOnBoardPiece);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOnBoardPiece(GamePiece aOnBoardPiece)
  {
    boolean wasRemoved = false;
    //Unable to remove aOnBoardPiece, as it must always have a board
    if (!this.equals(aOnBoardPiece.getBoard()))
    {
      onBoardPieces.remove(aOnBoardPiece);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOnBoardPieceAt(GamePiece aOnBoardPiece, int index)
  {  
    boolean wasAdded = false;
    if(addOnBoardPiece(aOnBoardPiece))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOnBoardPieces()) { index = numberOfOnBoardPieces() - 1; }
      onBoardPieces.remove(aOnBoardPiece);
      onBoardPieces.add(index, aOnBoardPiece);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOnBoardPieceAt(GamePiece aOnBoardPiece, int index)
  {
    boolean wasAdded = false;
    if(onBoardPieces.contains(aOnBoardPiece))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOnBoardPieces()) { index = numberOfOnBoardPieces() - 1; }
      onBoardPieces.remove(aOnBoardPiece);
      onBoardPieces.add(index, aOnBoardPiece);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOnBoardPieceAt(aOnBoardPiece, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPosition()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Position addPosition(int aRow, String aColumn, Quoridor aQuoridor)
  {
    return new Position(aRow, aColumn, this, aQuoridor);
  }

  public boolean addPosition(Position aPosition)
  {
    boolean wasAdded = false;
    if (position.contains(aPosition)) { return false; }
    Board existingBoard = aPosition.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
    if (isNewBoard)
    {
      aPosition.setBoard(this);
    }
    else
    {
      position.add(aPosition);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePosition(Position aPosition)
  {
    boolean wasRemoved = false;
    //Unable to remove aPosition, as it must always have a board
    if (!this.equals(aPosition.getBoard()))
    {
      position.remove(aPosition);
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
      if(index > numberOfPosition()) { index = numberOfPosition() - 1; }
      position.remove(aPosition);
      position.add(index, aPosition);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePositionAt(Position aPosition, int index)
  {
    boolean wasAdded = false;
    if(position.contains(aPosition))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPosition()) { index = numberOfPosition() - 1; }
      position.remove(aPosition);
      position.add(index, aPosition);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPositionAt(aPosition, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removeBoard(this);
    }
    Game existingCurrentGame = currentGame;
    currentGame = null;
    if (existingCurrentGame != null)
    {
      existingCurrentGame.delete();
    }
    for(int i=onBoardPieces.size(); i > 0; i--)
    {
      GamePiece aOnBoardPiece = onBoardPieces.get(i - 1);
      aOnBoardPiece.delete();
    }
    for(int i=position.size(); i > 0; i--)
    {
      Position aPosition = position.get(i - 1);
      aPosition.delete();
    }
  }

}