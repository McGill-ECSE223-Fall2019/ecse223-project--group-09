/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 12 "../../../../../Quoridor.ump"
public class Pawn extends GamePiece
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Pawn Attributes
  private String color;

  //Pawn Associations
  private Player player;
  private List<PawnMove> moves;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Pawn(Position aCurrentPosition, Board aBoard, Quoridor aQuoridor, String aColor, Player aPlayer)
  {
    super(aCurrentPosition, aBoard, aQuoridor);
    color = aColor;
    if (aPlayer == null || aPlayer.getPawn() != null)
    {
      throw new RuntimeException("Unable to create Pawn due to aPlayer");
    }
    player = aPlayer;
    moves = new ArrayList<PawnMove>();
  }

  public Pawn(Position aCurrentPosition, Board aBoard, Quoridor aQuoridor, String aColor, long aTimeRemainingForPlayer, Username aUsernameForPlayer, Quoridor aQuoridorForPlayer)
  {
    super(aCurrentPosition, aBoard, aQuoridor);
    color = aColor;
    player = new Player(aTimeRemainingForPlayer, aUsernameForPlayer, this, aQuoridorForPlayer);
    moves = new ArrayList<PawnMove>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setColor(String aColor)
  {
    boolean wasSet = false;
    color = aColor;
    wasSet = true;
    return wasSet;
  }

  public String getColor()
  {
    return color;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMoves()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PawnMove addMove(Time aStartTime, Time aEndTime, Position aDestination, Player aPlayer, Game aGame, Quoridor aQuoridor)
  {
    return new PawnMove(aStartTime, aEndTime, aDestination, aPlayer, aGame, aQuoridor, this);
  }

  public boolean addMove(PawnMove aMove)
  {
    boolean wasAdded = false;
    if (moves.contains(aMove)) { return false; }
    Pawn existingPawn = aMove.getPawn();
    boolean isNewPawn = existingPawn != null && !this.equals(existingPawn);
    if (isNewPawn)
    {
      aMove.setPawn(this);
    }
    else
    {
      moves.add(aMove);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMove(PawnMove aMove)
  {
    boolean wasRemoved = false;
    //Unable to remove aMove, as it must always have a pawn
    if (!this.equals(aMove.getPawn()))
    {
      moves.remove(aMove);
      wasRemoved = true;
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

  public void delete()
  {
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
    for(int i=moves.size(); i > 0; i--)
    {
      PawnMove aMove = moves.get(i - 1);
      aMove.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "color" + ":" + getColor()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}