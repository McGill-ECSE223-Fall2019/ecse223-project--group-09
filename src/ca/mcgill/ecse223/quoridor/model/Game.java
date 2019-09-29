/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.model;
import java.util.*;
import java.sql.Time;

// line 26 "../../../../../Quoridor.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private int id;

  //Game Associations
  private List<Player> currentPlayers;
  private List<Move> moveHistory;
  private Player winner;
  private Board board;
  private Quoridor quoridor;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(int aId, Player aWinner, Board aBoard, Quoridor aQuoridor, Player... allCurrentPlayers)
  {
    id = aId;
    currentPlayers = new ArrayList<Player>();
    boolean didAddCurrentPlayers = setCurrentPlayers(allCurrentPlayers);
    if (!didAddCurrentPlayers)
    {
      throw new RuntimeException("Unable to create Game, must have 2 to 4 currentPlayers");
    }
    moveHistory = new ArrayList<Move>();
    boolean didAddWinner = setWinner(aWinner);
    if (!didAddWinner)
    {
      throw new RuntimeException("Unable to create game due to winner");
    }
    if (aBoard == null || aBoard.getCurrentGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aBoard");
    }
    board = aBoard;
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create game due to quoridor");
    }
  }

  public Game(int aId, Player aWinner, Quoridor aQuoridorForBoard, Quoridor aQuoridor, Player... allCurrentPlayers)
  {
    id = aId;
    currentPlayers = new ArrayList<Player>();
    boolean didAddCurrentPlayers = setCurrentPlayers(allCurrentPlayers);
    if (!didAddCurrentPlayers)
    {
      throw new RuntimeException("Unable to create Game, must have 2 to 4 currentPlayers");
    }
    moveHistory = new ArrayList<Move>();
    boolean didAddWinner = setWinner(aWinner);
    if (!didAddWinner)
    {
      throw new RuntimeException("Unable to create game due to winner");
    }
    board = new Board(aQuoridorForBoard, this);
    boolean didAddQuoridor = setQuoridor(aQuoridor);
    if (!didAddQuoridor)
    {
      throw new RuntimeException("Unable to create game due to quoridor");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetMany */
  public Player getCurrentPlayer(int index)
  {
    Player aCurrentPlayer = currentPlayers.get(index);
    return aCurrentPlayer;
  }

  public List<Player> getCurrentPlayers()
  {
    List<Player> newCurrentPlayers = Collections.unmodifiableList(currentPlayers);
    return newCurrentPlayers;
  }

  public int numberOfCurrentPlayers()
  {
    int number = currentPlayers.size();
    return number;
  }

  public boolean hasCurrentPlayers()
  {
    boolean has = currentPlayers.size() > 0;
    return has;
  }

  public int indexOfCurrentPlayer(Player aCurrentPlayer)
  {
    int index = currentPlayers.indexOf(aCurrentPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public Move getMoveHistory(int index)
  {
    Move aMoveHistory = moveHistory.get(index);
    return aMoveHistory;
  }

  public List<Move> getMoveHistory()
  {
    List<Move> newMoveHistory = Collections.unmodifiableList(moveHistory);
    return newMoveHistory;
  }

  public int numberOfMoveHistory()
  {
    int number = moveHistory.size();
    return number;
  }

  public boolean hasMoveHistory()
  {
    boolean has = moveHistory.size() > 0;
    return has;
  }

  public int indexOfMoveHistory(Move aMoveHistory)
  {
    int index = moveHistory.indexOf(aMoveHistory);
    return index;
  }
  /* Code from template association_GetOne */
  public Player getWinner()
  {
    return winner;
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
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfCurrentPlayersValid()
  {
    boolean isValid = numberOfCurrentPlayers() >= minimumNumberOfCurrentPlayers() && numberOfCurrentPlayers() <= maximumNumberOfCurrentPlayers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCurrentPlayers()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfCurrentPlayers()
  {
    return 4;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addCurrentPlayer(Player aCurrentPlayer)
  {
    boolean wasAdded = false;
    if (currentPlayers.contains(aCurrentPlayer)) { return false; }
    if (numberOfCurrentPlayers() >= maximumNumberOfCurrentPlayers())
    {
      return wasAdded;
    }

    currentPlayers.add(aCurrentPlayer);
    if (aCurrentPlayer.indexOfGame(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aCurrentPlayer.addGame(this);
      if (!wasAdded)
      {
        currentPlayers.remove(aCurrentPlayer);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMNToMany */
  public boolean removeCurrentPlayer(Player aCurrentPlayer)
  {
    boolean wasRemoved = false;
    if (!currentPlayers.contains(aCurrentPlayer))
    {
      return wasRemoved;
    }

    if (numberOfCurrentPlayers() <= minimumNumberOfCurrentPlayers())
    {
      return wasRemoved;
    }

    int oldIndex = currentPlayers.indexOf(aCurrentPlayer);
    currentPlayers.remove(oldIndex);
    if (aCurrentPlayer.indexOfGame(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aCurrentPlayer.removeGame(this);
      if (!wasRemoved)
      {
        currentPlayers.add(oldIndex,aCurrentPlayer);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToMany */
  public boolean setCurrentPlayers(Player... newCurrentPlayers)
  {
    boolean wasSet = false;
    ArrayList<Player> verifiedCurrentPlayers = new ArrayList<Player>();
    for (Player aCurrentPlayer : newCurrentPlayers)
    {
      if (verifiedCurrentPlayers.contains(aCurrentPlayer))
      {
        continue;
      }
      verifiedCurrentPlayers.add(aCurrentPlayer);
    }

    if (verifiedCurrentPlayers.size() != newCurrentPlayers.length || verifiedCurrentPlayers.size() < minimumNumberOfCurrentPlayers() || verifiedCurrentPlayers.size() > maximumNumberOfCurrentPlayers())
    {
      return wasSet;
    }

    ArrayList<Player> oldCurrentPlayers = new ArrayList<Player>(currentPlayers);
    currentPlayers.clear();
    for (Player aNewCurrentPlayer : verifiedCurrentPlayers)
    {
      currentPlayers.add(aNewCurrentPlayer);
      if (oldCurrentPlayers.contains(aNewCurrentPlayer))
      {
        oldCurrentPlayers.remove(aNewCurrentPlayer);
      }
      else
      {
        aNewCurrentPlayer.addGame(this);
      }
    }

    for (Player anOldCurrentPlayer : oldCurrentPlayers)
    {
      anOldCurrentPlayer.removeGame(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCurrentPlayerAt(Player aCurrentPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addCurrentPlayer(aCurrentPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentPlayers()) { index = numberOfCurrentPlayers() - 1; }
      currentPlayers.remove(aCurrentPlayer);
      currentPlayers.add(index, aCurrentPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCurrentPlayerAt(Player aCurrentPlayer, int index)
  {
    boolean wasAdded = false;
    if(currentPlayers.contains(aCurrentPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCurrentPlayers()) { index = numberOfCurrentPlayers() - 1; }
      currentPlayers.remove(aCurrentPlayer);
      currentPlayers.add(index, aCurrentPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCurrentPlayerAt(aCurrentPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMoveHistory()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addMoveHistory(Move aMoveHistory)
  {
    boolean wasAdded = false;
    if (moveHistory.contains(aMoveHistory)) { return false; }
    Game existingGame = aMoveHistory.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aMoveHistory.setGame(this);
    }
    else
    {
      moveHistory.add(aMoveHistory);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMoveHistory(Move aMoveHistory)
  {
    boolean wasRemoved = false;
    //Unable to remove aMoveHistory, as it must always have a game
    if (!this.equals(aMoveHistory.getGame()))
    {
      moveHistory.remove(aMoveHistory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMoveHistoryAt(Move aMoveHistory, int index)
  {  
    boolean wasAdded = false;
    if(addMoveHistory(aMoveHistory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoveHistory()) { index = numberOfMoveHistory() - 1; }
      moveHistory.remove(aMoveHistory);
      moveHistory.add(index, aMoveHistory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMoveHistoryAt(Move aMoveHistory, int index)
  {
    boolean wasAdded = false;
    if(moveHistory.contains(aMoveHistory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMoveHistory()) { index = numberOfMoveHistory() - 1; }
      moveHistory.remove(aMoveHistory);
      moveHistory.add(index, aMoveHistory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMoveHistoryAt(aMoveHistory, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setWinner(Player aNewWinner)
  {
    boolean wasSet = false;
    if (aNewWinner == null)
    {
      //Unable to setWinner to null, as game must always be associated to a winner
      return wasSet;
    }
    
    Game existingGame = aNewWinner.getGame();
    if (existingGame != null && !equals(existingGame))
    {
      //Unable to setWinner, the current winner already has a game, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Player anOldWinner = winner;
    winner = aNewWinner;
    winner.setGame(this);

    if (anOldWinner != null)
    {
      anOldWinner.setGame(null);
    }
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
      existingQuoridor.removeGame(this);
    }
    quoridor.addGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Player> copyOfCurrentPlayers = new ArrayList<Player>(currentPlayers);
    currentPlayers.clear();
    for(Player aCurrentPlayer : copyOfCurrentPlayers)
    {
      aCurrentPlayer.removeGame(this);
    }
    for(int i=moveHistory.size(); i > 0; i--)
    {
      Move aMoveHistory = moveHistory.get(i - 1);
      aMoveHistory.delete();
    }
    Player existingWinner = winner;
    winner = null;
    if (existingWinner != null)
    {
      existingWinner.setGame(null);
    }
    Board existingBoard = board;
    board = null;
    if (existingBoard != null)
    {
      existingBoard.delete();
    }
    Quoridor placeholderQuoridor = quoridor;
    this.quoridor = null;
    if(placeholderQuoridor != null)
    {
      placeholderQuoridor.removeGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "winner = "+(getWinner()!=null?Integer.toHexString(System.identityHashCode(getWinner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "quoridor = "+(getQuoridor()!=null?Integer.toHexString(System.identityHashCode(getQuoridor())):"null");
  }
}