/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 21 "../../../../../Quoridor.ump"
public class Wall extends GamePiece
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wall Associations
  private Player associatedPlayer;
  private List<Player> owner;
  private List<WallPlacement> placements;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(Position aCurrentPosition, Board aBoard, Quoridor aQuoridor, Player aAssociatedPlayer)
  {
    super(aCurrentPosition, aBoard, aQuoridor);
    boolean didAddAssociatedPlayer = setAssociatedPlayer(aAssociatedPlayer);
    if (!didAddAssociatedPlayer)
    {
      throw new RuntimeException("Unable to create wallsLeft due to associatedPlayer");
    }
    owner = new ArrayList<Player>();
    placements = new ArrayList<WallPlacement>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Player getAssociatedPlayer()
  {
    return associatedPlayer;
  }
  /* Code from template association_GetMany */
  public Player getOwner(int index)
  {
    Player aOwner = owner.get(index);
    return aOwner;
  }

  public List<Player> getOwner()
  {
    List<Player> newOwner = Collections.unmodifiableList(owner);
    return newOwner;
  }

  public int numberOfOwner()
  {
    int number = owner.size();
    return number;
  }

  public boolean hasOwner()
  {
    boolean has = owner.size() > 0;
    return has;
  }

  public int indexOfOwner(Player aOwner)
  {
    int index = owner.indexOf(aOwner);
    return index;
  }
  /* Code from template association_GetMany */
  public WallPlacement getPlacement(int index)
  {
    WallPlacement aPlacement = placements.get(index);
    return aPlacement;
  }

  public List<WallPlacement> getPlacements()
  {
    List<WallPlacement> newPlacements = Collections.unmodifiableList(placements);
    return newPlacements;
  }

  public int numberOfPlacements()
  {
    int number = placements.size();
    return number;
  }

  public boolean hasPlacements()
  {
    boolean has = placements.size() > 0;
    return has;
  }

  public int indexOfPlacement(WallPlacement aPlacement)
  {
    int index = placements.indexOf(aPlacement);
    return index;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setAssociatedPlayer(Player aAssociatedPlayer)
  {
    boolean wasSet = false;
    //Must provide associatedPlayer to wallsLeft
    if (aAssociatedPlayer == null)
    {
      return wasSet;
    }

    //associatedPlayer already at maximum (10)
    if (aAssociatedPlayer.numberOfWallsLeft() >= Player.maximumNumberOfWallsLeft())
    {
      return wasSet;
    }
    
    Player existingAssociatedPlayer = associatedPlayer;
    associatedPlayer = aAssociatedPlayer;
    if (existingAssociatedPlayer != null && !existingAssociatedPlayer.equals(aAssociatedPlayer))
    {
      boolean didRemove = existingAssociatedPlayer.removeWallsLeft(this);
      if (!didRemove)
      {
        associatedPlayer = existingAssociatedPlayer;
        return wasSet;
      }
    }
    associatedPlayer.addWallsLeft(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOwner()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addOwner(Player aOwner)
  {
    boolean wasAdded = false;
    if (owner.contains(aOwner)) { return false; }
    owner.add(aOwner);
    if (aOwner.indexOfWallsOnBoard(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aOwner.addWallsOnBoard(this);
      if (!wasAdded)
      {
        owner.remove(aOwner);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeOwner(Player aOwner)
  {
    boolean wasRemoved = false;
    if (!owner.contains(aOwner))
    {
      return wasRemoved;
    }

    int oldIndex = owner.indexOf(aOwner);
    owner.remove(oldIndex);
    if (aOwner.indexOfWallsOnBoard(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aOwner.removeWallsOnBoard(this);
      if (!wasRemoved)
      {
        owner.add(oldIndex,aOwner);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOwnerAt(Player aOwner, int index)
  {  
    boolean wasAdded = false;
    if(addOwner(aOwner))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOwner()) { index = numberOfOwner() - 1; }
      owner.remove(aOwner);
      owner.add(index, aOwner);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOwnerAt(Player aOwner, int index)
  {
    boolean wasAdded = false;
    if(owner.contains(aOwner))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOwner()) { index = numberOfOwner() - 1; }
      owner.remove(aOwner);
      owner.add(index, aOwner);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOwnerAt(aOwner, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlacements()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WallPlacement addPlacement(Time aStartTime, Time aEndTime, Position aDestination, Player aPlayer, Game aGame, Quoridor aQuoridor, WallPlacement.Orientation aOrientation)
  {
    return new WallPlacement(aStartTime, aEndTime, aDestination, aPlayer, aGame, aQuoridor, aOrientation, this);
  }

  public boolean addPlacement(WallPlacement aPlacement)
  {
    boolean wasAdded = false;
    if (placements.contains(aPlacement)) { return false; }
    Wall existingWall = aPlacement.getWall();
    boolean isNewWall = existingWall != null && !this.equals(existingWall);
    if (isNewWall)
    {
      aPlacement.setWall(this);
    }
    else
    {
      placements.add(aPlacement);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlacement(WallPlacement aPlacement)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlacement, as it must always have a wall
    if (!this.equals(aPlacement.getWall()))
    {
      placements.remove(aPlacement);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlacementAt(WallPlacement aPlacement, int index)
  {  
    boolean wasAdded = false;
    if(addPlacement(aPlacement))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlacements()) { index = numberOfPlacements() - 1; }
      placements.remove(aPlacement);
      placements.add(index, aPlacement);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlacementAt(WallPlacement aPlacement, int index)
  {
    boolean wasAdded = false;
    if(placements.contains(aPlacement))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlacements()) { index = numberOfPlacements() - 1; }
      placements.remove(aPlacement);
      placements.add(index, aPlacement);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlacementAt(aPlacement, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Player placeholderAssociatedPlayer = associatedPlayer;
    this.associatedPlayer = null;
    if(placeholderAssociatedPlayer != null)
    {
      placeholderAssociatedPlayer.removeWallsLeft(this);
    }
    ArrayList<Player> copyOfOwner = new ArrayList<Player>(owner);
    owner.clear();
    for(Player aOwner : copyOfOwner)
    {
      aOwner.removeWallsOnBoard(this);
    }
    for(int i=placements.size(); i > 0; i--)
    {
      WallPlacement aPlacement = placements.get(i - 1);
      aPlacement.delete();
    }
    super.delete();
  }

}