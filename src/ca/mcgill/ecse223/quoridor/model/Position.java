/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 61 "../../../../../Quoridor.ump"
public class Position
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Position Attributes
  private int row;
  private String column;

  //Position Associations
  private Board board;
  private Move move;
  private GamePiece piece;
  private List<PawnMove> moves;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Position(int aRow, String aColumn, Board aBoard, Quoridor aQuoridor)
  {
    row = aRow;
    column = aColumn;
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create position due to board");
    }
    moves = new ArrayList<PawnMove>();
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create position due to quoridor");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRow(int aRow)
  {
    boolean wasSet = false;
    row = aRow;
    wasSet = true;
    return wasSet;
  }

  public boolean setColumn(String aColumn)
  {
    boolean wasSet = false;
    column = aColumn;
    wasSet = true;
    return wasSet;
  }

  public int getRow()
  {
    return row;
  }

  public String getColumn()
  {
    return column;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetOne */
  public Move getMove()
  {
    return move;
  }

  public boolean hasMove()
  {
    boolean has = move != null;
    return has;
  }
  /* Code from template association_GetOne */
  public GamePiece getPiece()
  {
    return piece;
  }

  public boolean hasPiece()
  {
    boolean has = piece != null;
    return has;
  }
  /* Code from template association_GetMany */
  public PawnMove getMove(int index)
  {
    PawnMove aMove = moves.get(index);
    return aMove;
  }

  public List<PawnMove> getMoves()
  {
    List<PawnMove> newMoves = Collections.unmodifiableList(moves);
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

  public int indexOfMove(PawnMove aMove)
  {
    int index = moves.indexOf(aMove);
    return index;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    if (aBoard == null)
    {
      return wasSet;
    }

    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      existingBoard.removePosition(this);
    }
    board.addPosition(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setMove(Move aNewMove)
  {
    boolean wasSet = false;
    if (move != null && !move.equals(aNewMove) && equals(move.getDestination()))
    {
      //Unable to setMove, as existing move would become an orphan
      return wasSet;
    }

    move = aNewMove;
    Position anOldDestination = aNewMove != null ? aNewMove.getDestination() : null;

    if (!this.equals(anOldDestination))
    {
      if (anOldDestination != null)
      {
        anOldDestination.move = null;
      }
      if (move != null)
      {
        move.setDestination(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setPiece(GamePiece aNewPiece)
  {
    boolean wasSet = false;
    if (piece != null && !piece.equals(aNewPiece) && equals(piece.getCurrentPosition()))
    {
      //Unable to setPiece, as existing piece would become an orphan
      return wasSet;
    }

    piece = aNewPiece;
    Position anOldCurrentPosition = aNewPiece != null ? aNewPiece.getCurrentPosition() : null;

    if (!this.equals(anOldCurrentPosition))
    {
      if (anOldCurrentPosition != null)
      {
        anOldCurrentPosition.piece = null;
      }
      if (piece != null)
      {
        piece.setCurrentPosition(this);
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
  /* Code from template association_AddManyToManyMethod */
  public boolean addMove(PawnMove aMove)
  {
    boolean wasAdded = false;
    if (moves.contains(aMove)) { return false; }
    moves.add(aMove);
    if (aMove.indexOfPosition(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aMove.addPosition(this);
      if (!wasAdded)
      {
        moves.remove(aMove);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeMove(PawnMove aMove)
  {
    boolean wasRemoved = false;
    if (!moves.contains(aMove))
    {
      return wasRemoved;
    }

    int oldIndex = moves.indexOf(aMove);
    moves.remove(oldIndex);
    if (aMove.indexOfPosition(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aMove.removePosition(this);
      if (!wasRemoved)
      {
        moves.add(oldIndex,aMove);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMoveAt(PawnMove aMove, int index)
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

  public boolean addOrMoveMoveAt(PawnMove aMove, int index)
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
      existingQuoridor.removePosition(this);
    }
    quoridor.addPosition(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removePosition(this);
    }
    Move existingMove = move;
    move = null;
    if (existingMove != null)
    {
      existingMove.delete();
    }
    GamePiece existingPiece = piece;
    piece = null;
    if (existingPiece != null)
    {
      existingPiece.delete();
    }
    ArrayList<PawnMove> copyOfMoves = new ArrayList<PawnMove>(moves);
    moves.clear();
    for(PawnMove aMove : copyOfMoves)
    {
      aMove.removePosition(this);
    }
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removePosition(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "row" + ":" + getRow()+ "," +
            "column" + ":" + getColumn()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "move = "+(getMove()!=null?Integer.toHexString(System.identityHashCode(getMove())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "piece = "+(getPiece()!=null?Integer.toHexString(System.identityHashCode(getPiece())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}