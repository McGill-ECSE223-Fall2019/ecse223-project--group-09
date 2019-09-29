/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 78 "../../../../../Quoridor.ump"
public class PawnMove extends Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnMove Associations
  private Pawn pawn;
  private List<Position> position;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnMove(Time aStartTime, Time aEndTime, Position aDestination, Player aPlayer, Game aGame, Quoridor aQuoridor, Pawn aPawn)
  {
    super(aStartTime, aEndTime, aDestination, aPlayer, aGame, aQuoridor);
    boolean didAddPawn = setPawn(aPawn);
    if (!didAddPawn)
    {
      throw new RuntimeException("Unable to create move due to pawn");
    }
    position = new ArrayList<Position>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Pawn getPawn()
  {
    return pawn;
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
  public boolean setPawn(Pawn aPawn)
  {
    boolean wasSet = false;
    if (aPawn == null)
    {
      return wasSet;
    }

    Pawn existingPawn = pawn;
    pawn = aPawn;
    if (existingPawn != null && !existingPawn.equals(aPawn))
    {
      existingPawn.removeMove(this);
    }
    pawn.addMove(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPosition()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addPosition(Position aPosition)
  {
    boolean wasAdded = false;
    if (position.contains(aPosition)) { return false; }
    position.add(aPosition);
    if (aPosition.indexOfMove(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aPosition.addMove(this);
      if (!wasAdded)
      {
        position.remove(aPosition);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removePosition(Position aPosition)
  {
    boolean wasRemoved = false;
    if (!position.contains(aPosition))
    {
      return wasRemoved;
    }

    int oldIndex = position.indexOf(aPosition);
    position.remove(oldIndex);
    if (aPosition.indexOfMove(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aPosition.removeMove(this);
      if (!wasRemoved)
      {
        position.add(oldIndex,aPosition);
      }
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
    Pawn placeholderPawn = pawn;
    this.pawn = null;
    if(placeholderPawn != null)
    {
      placeholderPawn.removeMove(this);
    }
    ArrayList<Position> copyOfPosition = new ArrayList<Position>(position);
    position.clear();
    for(Position aPosition : copyOfPosition)
    {
      aPosition.removeMove(this);
    }
    super.delete();
  }

}