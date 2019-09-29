/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;

// line 54 "../../../../../Quoridor.ump"
public abstract class GamePiece
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GamePiece Associations
  private Position currentPosition;
  private Board board;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GamePiece(Position aCurrentPosition, Board aBoard, Quoridor aQuoridor)
  {
    boolean didAddCurrentPosition = setCurrentPosition(aCurrentPosition);
    if (!didAddCurrentPosition)
    {
      throw new RuntimeException("Unable to create piece due to currentPosition");
    }
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create onBoardPiece due to board");
    }
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create gamePiece due to quoridor");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Position getCurrentPosition()
  {
    return currentPosition;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetOne */
  public Quoridor getQuoridor()
  {
    return quoridor;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCurrentPosition(Position aNewCurrentPosition)
  {
    boolean wasSet = false;
    if (aNewCurrentPosition == null)
    {
      //Unable to setCurrentPosition to null, as piece must always be associated to a currentPosition
      return wasSet;
    }
    
    GamePiece existingPiece = aNewCurrentPosition.getPiece();
    if (existingPiece != null && !equals(existingPiece))
    {
      //Unable to setCurrentPosition, the current currentPosition already has a piece, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Position anOldCurrentPosition = currentPosition;
    currentPosition = aNewCurrentPosition;
    currentPosition.setPiece(this);

    if (anOldCurrentPosition != null)
    {
      anOldCurrentPosition.setPiece(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to onBoardPiece
    if (aBoard == null)
    {
      return wasSet;
    }

    //board already at maximum (24)
    if (aBoard.numberOfOnBoardPieces() >= Board.maximumNumberOfOnBoardPieces())
    {
      return wasSet;
    }
    
    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removeOnBoardPiece(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addOnBoardPiece(this);
    wasSet = true;
    return wasSet;
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
      existingQuoridor.removeGamePiece(this);
    }
    quoridor.addGamePiece(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Position existingCurrentPosition = currentPosition;
    currentPosition = null;
    if (existingCurrentPosition != null)
    {
      existingCurrentPosition.setPiece(null);
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeOnBoardPiece(this);
    }
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removeGamePiece(this);
    }
  }

}