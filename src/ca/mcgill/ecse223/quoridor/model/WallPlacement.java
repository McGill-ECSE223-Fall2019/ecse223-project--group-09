/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.sql.Time;

// line 67 "../../../../../Quoridor.ump"
public class WallPlacement extends Move
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Orientation { HORIZONTAL, VERTICAL }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WallPlacement Attributes
  private Orientation orientation;

  //WallPlacement Associations
  private Wall wall;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WallPlacement(Time aStartTime, Time aEndTime, Position aDestination, Player aPlayer, Game aGame, Quoridor aQuoridor, Orientation aOrientation, Wall aWall)
  {
    super(aStartTime, aEndTime, aDestination, aPlayer, aGame, aQuoridor);
    orientation = aOrientation;
    boolean didAddWall = setWall(aWall);
    if (!didAddWall)
    {
      throw new RuntimeException("Unable to create placement due to wall");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOrientation(Orientation aOrientation)
  {
    boolean wasSet = false;
    orientation = aOrientation;
    wasSet = true;
    return wasSet;
  }

  public Orientation getOrientation()
  {
    return orientation;
  }
  /* Code from template association_GetOne */
  public Wall getWall()
  {
    return wall;
  }
  /* Code from template association_SetOneToMany */
  public boolean setWall(Wall aWall)
  {
    boolean wasSet = false;
    if (aWall == null)
    {
      return wasSet;
    }

    Wall existingWall = wall;
    wall = aWall;
    if (existingWall != null && !existingWall.equals(aWall))
    {
      existingWall.removePlacement(this);
    }
    wall.addPlacement(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Wall placeholderWall = wall;
    this.wall = null;
    if(placeholderWall != null)
    {
      placeholderWall.removePlacement(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orientation" + "=" + (getOrientation() != null ? !getOrientation().equals(this)  ? getOrientation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "wall = "+(getWall()!=null?Integer.toHexString(System.identityHashCode(getWall())):"null");
  }
}