/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import ca.mcgill.ecse223.quoridor.model.*;

// line 5 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior State Machines
  public enum PawnSM { Idle, Playing, Finished }
  public enum PawnSMPlayingHorizontal { Null, Horizontal }
  public enum PawnSMPlayingHorizontalHorizontal { Null, AtCenter, AtTopBorder, AtBottomBorder }
  public enum PawnSMPlayingVertical { Null, Vertical }
  public enum PawnSMPlayingVerticalVertical { Null, AtCenter, AtLeftBorder, AtRightBorder }
  private PawnSM pawnSM;
  private PawnSMPlayingHorizontal pawnSMPlayingHorizontal;
  private PawnSMPlayingHorizontalHorizontal pawnSMPlayingHorizontalHorizontal;
  private PawnSMPlayingVertical pawnSMPlayingVertical;
  private PawnSMPlayingVerticalVertical pawnSMPlayingVerticalVertical;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior()
  {
    setPawnSMPlayingHorizontal(PawnSMPlayingHorizontal.Null);
    setPawnSMPlayingHorizontalHorizontal(PawnSMPlayingHorizontalHorizontal.Null);
    setPawnSMPlayingVertical(PawnSMPlayingVertical.Null);
    setPawnSMPlayingVerticalVertical(PawnSMPlayingVerticalVertical.Null);
    setPawnSM(PawnSM.Idle);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getPawnSMFullName()
  {
    String answer = pawnSM.toString();
    if (pawnSMPlayingHorizontal != PawnSMPlayingHorizontal.Null) { answer += "." + pawnSMPlayingHorizontal.toString(); }
    if (pawnSMPlayingHorizontalHorizontal != PawnSMPlayingHorizontalHorizontal.Null) { answer += "." + pawnSMPlayingHorizontalHorizontal.toString(); }
    if (pawnSMPlayingVertical != PawnSMPlayingVertical.Null) { answer += "." + pawnSMPlayingVertical.toString(); }
    if (pawnSMPlayingVerticalVertical != PawnSMPlayingVerticalVertical.Null) { answer += "." + pawnSMPlayingVerticalVertical.toString(); }
    return answer;
  }

  public PawnSM getPawnSM()
  {
    return pawnSM;
  }

  public PawnSMPlayingHorizontal getPawnSMPlayingHorizontal()
  {
    return pawnSMPlayingHorizontal;
  }

  public PawnSMPlayingHorizontalHorizontal getPawnSMPlayingHorizontalHorizontal()
  {
    return pawnSMPlayingHorizontalHorizontal;
  }

  public PawnSMPlayingVertical getPawnSMPlayingVertical()
  {
    return pawnSMPlayingVertical;
  }

  public PawnSMPlayingVerticalVertical getPawnSMPlayingVerticalVertical()
  {
    return pawnSMPlayingVerticalVertical;
  }

  public boolean initialize()
  {
    boolean wasEventProcessed = false;
    
    PawnSM aPawnSM = pawnSM;
    switch (aPawnSM)
    {
      case Idle:
        setPawnSM(PawnSM.Playing);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition97__()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontal aPawnSMPlayingHorizontal = pawnSMPlayingHorizontal;
    switch (aPawnSMPlayingHorizontal)
    {
      case Horizontal:
        if (getCurrentPawnRow()==1)
        {
          exitPawnSMPlayingHorizontalHorizontal();
          setPawnSMPlayingHorizontalHorizontal(PawnSMPlayingHorizontalHorizontal.AtBottomBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition98__()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontal aPawnSMPlayingHorizontal = pawnSMPlayingHorizontal;
    switch (aPawnSMPlayingHorizontal)
    {
      case Horizontal:
        if (getCurrentPawnRow()==9)
        {
          exitPawnSMPlayingHorizontalHorizontal();
          setPawnSMPlayingHorizontalHorizontal(PawnSMPlayingHorizontalHorizontal.AtTopBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition99__()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontal aPawnSMPlayingHorizontal = pawnSMPlayingHorizontal;
    switch (aPawnSMPlayingHorizontal)
    {
      case Horizontal:
        if (inRangeExclusive(getCurrentPawnRow(),1,9))
        {
          exitPawnSMPlayingHorizontalHorizontal();
          setPawnSMPlayingHorizontalHorizontal(PawnSMPlayingHorizontalHorizontal.AtCenter);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpUpLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontal aPawnSMPlayingHorizontal = pawnSMPlayingHorizontal;
    switch (aPawnSMPlayingHorizontal)
    {
      case Horizontal:
        if (isLegalLateralJump(MoveDirection.North,MoveDirection.West))
        {
          exitPawnSM();
        // line 21 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(1, -1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpUpRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontal aPawnSMPlayingHorizontal = pawnSMPlayingHorizontal;
    switch (aPawnSMPlayingHorizontal)
    {
      case Horizontal:
        if (isLegalLateralJump(MoveDirection.North,MoveDirection.East))
        {
          exitPawnSM();
        // line 22 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(1, 1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpDownLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontal aPawnSMPlayingHorizontal = pawnSMPlayingHorizontal;
    switch (aPawnSMPlayingHorizontal)
    {
      case Horizontal:
        if (isLegalLateralJump(MoveDirection.South,MoveDirection.West))
        {
          exitPawnSM();
        // line 24 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(-1, -1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpDownRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontal aPawnSMPlayingHorizontal = pawnSMPlayingHorizontal;
    switch (aPawnSMPlayingHorizontal)
    {
      case Horizontal:
        if (isLegalLateralJump(MoveDirection.South,MoveDirection.East))
        {
          exitPawnSM();
        // line 25 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(-1, 1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveUp()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontalHorizontal aPawnSMPlayingHorizontalHorizontal = pawnSMPlayingHorizontalHorizontal;
    switch (aPawnSMPlayingHorizontalHorizontal)
    {
      case AtCenter:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSM();
        // line 28 "../../../../../PawnStateMachine.ump"
          this.playStepMove(1, 0);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtBottomBorder:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSM();
        // line 42 "../../../../../PawnStateMachine.ump"
          this.playStepMove(1, 0);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveDown()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontalHorizontal aPawnSMPlayingHorizontalHorizontal = pawnSMPlayingHorizontalHorizontal;
    switch (aPawnSMPlayingHorizontalHorizontal)
    {
      case AtCenter:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSM();
        // line 29 "../../../../../PawnStateMachine.ump"
          this.playStepMove(-1, 0);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtTopBorder:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSM();
        // line 36 "../../../../../PawnStateMachine.ump"
          this.playStepMove(-1, 0);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpUp()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontalHorizontal aPawnSMPlayingHorizontalHorizontal = pawnSMPlayingHorizontalHorizontal;
    switch (aPawnSMPlayingHorizontalHorizontal)
    {
      case AtCenter:
        if (isLegalFarJump(MoveDirection.North))
        {
          exitPawnSM();
        // line 31 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(2, 0);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtBottomBorder:
        if (isLegalFarJump(MoveDirection.North))
        {
          exitPawnSM();
        // line 44 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(2, 0);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpDown()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingHorizontalHorizontal aPawnSMPlayingHorizontalHorizontal = pawnSMPlayingHorizontalHorizontal;
    switch (aPawnSMPlayingHorizontalHorizontal)
    {
      case AtCenter:
        if (isLegalFarJump(MoveDirection.South))
        {
          exitPawnSM();
        // line 32 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(-2, 0);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtTopBorder:
        if (isLegalFarJump(MoveDirection.South))
        {
          exitPawnSM();
        // line 38 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(-2, 0);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition100__()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVertical aPawnSMPlayingVertical = pawnSMPlayingVertical;
    switch (aPawnSMPlayingVertical)
    {
      case Vertical:
        if (getCurrentPawnColumn()==1)
        {
          exitPawnSMPlayingVerticalVertical();
          setPawnSMPlayingVerticalVertical(PawnSMPlayingVerticalVertical.AtLeftBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition101__()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVertical aPawnSMPlayingVertical = pawnSMPlayingVertical;
    switch (aPawnSMPlayingVertical)
    {
      case Vertical:
        if (getCurrentPawnColumn()==9)
        {
          exitPawnSMPlayingVerticalVertical();
          setPawnSMPlayingVerticalVertical(PawnSMPlayingVerticalVertical.AtRightBorder);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private boolean __autotransition102__()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVertical aPawnSMPlayingVertical = pawnSMPlayingVertical;
    switch (aPawnSMPlayingVertical)
    {
      case Vertical:
        if (inRangeExclusive(getCurrentPawnColumn(),1,9))
        {
          exitPawnSMPlayingVerticalVertical();
          setPawnSMPlayingVerticalVertical(PawnSMPlayingVerticalVertical.AtCenter);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpLeftUp()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVertical aPawnSMPlayingVertical = pawnSMPlayingVertical;
    switch (aPawnSMPlayingVertical)
    {
      case Vertical:
        if (isLegalLateralJump(MoveDirection.West,MoveDirection.North))
        {
          exitPawnSM();
        // line 55 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(1, -1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpLeftDown()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVertical aPawnSMPlayingVertical = pawnSMPlayingVertical;
    switch (aPawnSMPlayingVertical)
    {
      case Vertical:
        if (isLegalLateralJump(MoveDirection.West,MoveDirection.South))
        {
          exitPawnSM();
        // line 56 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(-1, -1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpRightUp()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVertical aPawnSMPlayingVertical = pawnSMPlayingVertical;
    switch (aPawnSMPlayingVertical)
    {
      case Vertical:
        if (isLegalLateralJump(MoveDirection.East,MoveDirection.North))
        {
          exitPawnSM();
        // line 58 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(1, 1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpRightDown()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVertical aPawnSMPlayingVertical = pawnSMPlayingVertical;
    switch (aPawnSMPlayingVertical)
    {
      case Vertical:
        if (isLegalLateralJump(MoveDirection.East,MoveDirection.South))
        {
          exitPawnSM();
        // line 59 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(-1, 1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVerticalVertical aPawnSMPlayingVerticalVertical = pawnSMPlayingVerticalVertical;
    switch (aPawnSMPlayingVerticalVertical)
    {
      case AtCenter:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSM();
        // line 62 "../../../../../PawnStateMachine.ump"
          this.playStepMove(0, -1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtRightBorder:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSM();
        // line 76 "../../../../../PawnStateMachine.ump"
          this.playStepMove(0, -1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVerticalVertical aPawnSMPlayingVerticalVertical = pawnSMPlayingVerticalVertical;
    switch (aPawnSMPlayingVerticalVertical)
    {
      case AtCenter:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSM();
        // line 63 "../../../../../PawnStateMachine.ump"
          this.playStepMove(0, 1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtLeftBorder:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSM();
        // line 70 "../../../../../PawnStateMachine.ump"
          this.playStepMove(0, 1);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVerticalVertical aPawnSMPlayingVerticalVertical = pawnSMPlayingVerticalVertical;
    switch (aPawnSMPlayingVerticalVertical)
    {
      case AtCenter:
        if (isLegalFarJump(MoveDirection.West))
        {
          exitPawnSM();
        // line 65 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(0, -2);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtRightBorder:
        if (isLegalFarJump(MoveDirection.West))
        {
          exitPawnSM();
        // line 78 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(0, -2);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMPlayingVerticalVertical aPawnSMPlayingVerticalVertical = pawnSMPlayingVerticalVertical;
    switch (aPawnSMPlayingVerticalVertical)
    {
      case AtCenter:
        if (isLegalFarJump(MoveDirection.East))
        {
          exitPawnSM();
        // line 66 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(0, 2);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      case AtLeftBorder:
        if (isLegalFarJump(MoveDirection.East))
        {
          exitPawnSM();
        // line 72 "../../../../../PawnStateMachine.ump"
          this.playJumpMove(0, 2);
          setPawnSM(PawnSM.Finished);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitPawnSM()
  {
    switch(pawnSM)
    {
      case Playing:
        exitPawnSMPlayingHorizontal();
        exitPawnSMPlayingVertical();
        break;
    }
  }

  private void setPawnSM(PawnSM aPawnSM)
  {
    pawnSM = aPawnSM;

    // entry actions and do activities
    switch(pawnSM)
    {
      case Playing:
        if (pawnSMPlayingHorizontal == PawnSMPlayingHorizontal.Null) { setPawnSMPlayingHorizontal(PawnSMPlayingHorizontal.Horizontal); }
        if (pawnSMPlayingVertical == PawnSMPlayingVertical.Null) { setPawnSMPlayingVertical(PawnSMPlayingVertical.Vertical); }
        break;
      case Finished:
        // line 84 "../../../../../PawnStateMachine.ump"
        QuoridorController.switchCurrentPlayer();
        break;
    }
  }

  private void exitPawnSMPlayingHorizontal()
  {
    switch(pawnSMPlayingHorizontal)
    {
      case Horizontal:
        exitPawnSMPlayingHorizontalHorizontal();
        setPawnSMPlayingHorizontal(PawnSMPlayingHorizontal.Null);
        break;
    }
  }

  private void setPawnSMPlayingHorizontal(PawnSMPlayingHorizontal aPawnSMPlayingHorizontal)
  {
    pawnSMPlayingHorizontal = aPawnSMPlayingHorizontal;
    if (pawnSM != PawnSM.Playing && aPawnSMPlayingHorizontal != PawnSMPlayingHorizontal.Null) { setPawnSM(PawnSM.Playing); }

    // entry actions and do activities
    switch(pawnSMPlayingHorizontal)
    {
      case Horizontal:
        if (pawnSMPlayingHorizontalHorizontal == PawnSMPlayingHorizontalHorizontal.Null) { setPawnSMPlayingHorizontalHorizontal(PawnSMPlayingHorizontalHorizontal.AtCenter); }
        __autotransition97__();
        __autotransition98__();
        __autotransition99__();
        break;
    }
  }

  private void exitPawnSMPlayingHorizontalHorizontal()
  {
    switch(pawnSMPlayingHorizontalHorizontal)
    {
      case AtCenter:
        setPawnSMPlayingHorizontalHorizontal(PawnSMPlayingHorizontalHorizontal.Null);
        break;
      case AtTopBorder:
        setPawnSMPlayingHorizontalHorizontal(PawnSMPlayingHorizontalHorizontal.Null);
        break;
      case AtBottomBorder:
        setPawnSMPlayingHorizontalHorizontal(PawnSMPlayingHorizontalHorizontal.Null);
        break;
    }
  }

  private void setPawnSMPlayingHorizontalHorizontal(PawnSMPlayingHorizontalHorizontal aPawnSMPlayingHorizontalHorizontal)
  {
    pawnSMPlayingHorizontalHorizontal = aPawnSMPlayingHorizontalHorizontal;
    if (pawnSMPlayingHorizontal != PawnSMPlayingHorizontal.Horizontal && aPawnSMPlayingHorizontalHorizontal != PawnSMPlayingHorizontalHorizontal.Null) { setPawnSMPlayingHorizontal(PawnSMPlayingHorizontal.Horizontal); }
  }

  private void exitPawnSMPlayingVertical()
  {
    switch(pawnSMPlayingVertical)
    {
      case Vertical:
        exitPawnSMPlayingVerticalVertical();
        setPawnSMPlayingVertical(PawnSMPlayingVertical.Null);
        break;
    }
  }

  private void setPawnSMPlayingVertical(PawnSMPlayingVertical aPawnSMPlayingVertical)
  {
    pawnSMPlayingVertical = aPawnSMPlayingVertical;
    if (pawnSM != PawnSM.Playing && aPawnSMPlayingVertical != PawnSMPlayingVertical.Null) { setPawnSM(PawnSM.Playing); }

    // entry actions and do activities
    switch(pawnSMPlayingVertical)
    {
      case Vertical:
        if (pawnSMPlayingVerticalVertical == PawnSMPlayingVerticalVertical.Null) { setPawnSMPlayingVerticalVertical(PawnSMPlayingVerticalVertical.AtCenter); }
        __autotransition100__();
        __autotransition101__();
        __autotransition102__();
        break;
    }
  }

  private void exitPawnSMPlayingVerticalVertical()
  {
    switch(pawnSMPlayingVerticalVertical)
    {
      case AtCenter:
        setPawnSMPlayingVerticalVertical(PawnSMPlayingVerticalVertical.Null);
        break;
      case AtLeftBorder:
        setPawnSMPlayingVerticalVertical(PawnSMPlayingVerticalVertical.Null);
        break;
      case AtRightBorder:
        setPawnSMPlayingVerticalVertical(PawnSMPlayingVerticalVertical.Null);
        break;
    }
  }

  private void setPawnSMPlayingVerticalVertical(PawnSMPlayingVerticalVertical aPawnSMPlayingVerticalVertical)
  {
    pawnSMPlayingVerticalVertical = aPawnSMPlayingVerticalVertical;
    if (pawnSMPlayingVertical != PawnSMPlayingVertical.Vertical && aPawnSMPlayingVerticalVertical != PawnSMPlayingVerticalVertical.Null) { setPawnSMPlayingVertical(PawnSMPlayingVertical.Vertical); }
  }
  /* Code from template association_GetOne */
  public Game getCurrentGame()
  {
    return currentGame;
  }

  public boolean hasCurrentGame()
  {
    boolean has = currentGame != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentGame(Game aNewCurrentGame)
  {
    boolean wasSet = false;
    currentGame = aNewCurrentGame;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    player = aNewPlayer;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    currentGame = null;
    player = null;
  }


  /**
   * Returns the current row number of the pawn
   */
  // line 90 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    final GamePosition gpos = this.getCurrentGame().getCurrentPosition();
        if (this.getPlayer().hasGameAsWhite()) {
            return gpos.getWhitePosition().getTile().getRow();
        } else {
            return gpos.getBlackPosition().getTile().getRow();
        }
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 100 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    final GamePosition gpos = this.getCurrentGame().getCurrentPosition();
        if (this.getPlayer().hasGameAsWhite()) {
            return gpos.getWhitePosition().getTile().getColumn();
        } else {
            return gpos.getBlackPosition().getTile().getColumn();
        }
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 110 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    final GamePosition gpos = this.getCurrentGame().getCurrentPosition();

        // If we have a pawn or pawn in the direction we are moving,
        // the step cannot be legal
        switch (dir) {
            case East:
                return QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() + 1)
                    && !QuoridorController.anyWallRightOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
            case South:
                return QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() - 1, this.getCurrentPawnColumn())
                    && !QuoridorController.anyWallBelowTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
            case West:
                return QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() - 1)
                    && !QuoridorController.anyWallLeftOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
            case North:
                return QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() + 1, this.getCurrentPawnColumn())
                    && !QuoridorController.anyWallAboveTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
            default:
                throw new IllegalArgumentException("Unknown move direction: " + dir);
        }
  }


  /**
   * Returns if it is legal to far jump in the given direction
   */
  // line 134 "../../../../../PawnStateMachine.ump"
  public boolean isLegalFarJump(MoveDirection dir){
    final GamePosition gpos = this.getCurrentGame().getCurrentPosition();

        // Two types:
        // - Far jump:
        //   [X O @] where 'X' jumps over 'O' to '@'
        //   [     ]
        // - Lateral jump:
        //   [X O| ] where 'X' moves over 'O' to '@'
        //   [  @  ]

        switch (dir) {
            case East:
                if (QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() + 1)) {
                    // We expected pawn 'O' and it's not there
                    // Pawn 'X' should not be able to jump
                    return false;
                }
                if (QuoridorController.anyWallRightOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn())) {
                    // Nothing can jump over walls
                    return false;
                }

                // This determines if we are far jumping or lateral jumping
                return QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() + 2)
                    && !QuoridorController.anyWallRightOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() + 1);
            case South:
                if (QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() - 1, this.getCurrentPawnColumn())) {
                    // We expected pawn 'O' and it's not there
                    // Pawn 'X' should not be able to jump
                    return false;
                }
                if (QuoridorController.anyWallBelowTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn())) {
                    // Nothing can jump over walls
                    return false;
                }

                return QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() - 2, this.getCurrentPawnColumn())
                    && !QuoridorController.anyWallBelowTile(gpos, this.getCurrentPawnRow() - 1, this.getCurrentPawnColumn());
            case West:
                if (QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() - 1)) {
                    // We expected pawn 'O' and it's not there
                    // Pawn 'X' should not be able to jump
                    return false;
                }
                if (QuoridorController.anyWallLeftOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn())) {
                    // Nothing can jump over walls
                    return false;
                }

                return QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() - 2)
                    && !QuoridorController.anyWallLeftOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() - 1);
            case North:
                if (QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() + 1, this.getCurrentPawnColumn())) {
                    // We expected pawn 'O' and it's not there
                    // Pawn 'X' should not be able to jump
                    return false;
                }
                if (QuoridorController.anyWallAboveTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn())) {
                    // Nothing can jump over walls
                    return false;
                }

                return QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() + 2, this.getCurrentPawnColumn())
                    && !QuoridorController.anyWallAboveTile(gpos, this.getCurrentPawnRow() + 1, this.getCurrentPawnColumn());
        default:
            throw new IllegalArgumentException("Unknown jump direction: " + dir);
        }
  }


  /**
   * Returns if it is legal to lateral jump in the given direction
   */
  // line 205 "../../../../../PawnStateMachine.ump"
  public boolean isLegalLateralJump(MoveDirection dir1, MoveDirection dir2){
    final GamePosition gpos = this.getCurrentGame().getCurrentPosition();

        // Two types:
        // - Far jump:
        //   [X O @] where 'X' jumps over 'O' to '@'
        //   [     ]
        // - Lateral jump:
        //   [X O| ] where 'X' moves over 'O' to '@'
        //   [  @  ]

        switch (dir1) {
            case East:
                if (QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() + 1)) {
                    // We expected pawn 'O' and it's not there
                    // Pawn 'X' should not be able to jump
                    return false;
                }
                if (QuoridorController.anyWallRightOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn())) {
                    // Nothing can jump over walls
                    return false;
                }

                switch (dir2) {
                    case South:
                        return (QuoridorController.anyWallRightOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() + 1)
                            || !QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() + 2))
                            && !QuoridorController.anyWallBelowTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
                    case North:
                        return (QuoridorController.anyWallRightOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() + 1)
                            || !QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() + 2))
                            && !QuoridorController.anyWallAboveTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
                    default:
                        return false;
                }
            case South:
                if (QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() - 1, this.getCurrentPawnColumn())) {
                    // We expected pawn 'O' and it's not there
                    // Pawn 'X' should not be able to jump
                    return false;
                }
                if (QuoridorController.anyWallBelowTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn())) {
                    // Nothing can jump over walls
                    return false;
                }

                switch (dir2) {
                    case East:
                        return (QuoridorController.anyWallBelowTile(gpos, this.getCurrentPawnRow() - 1, this.getCurrentPawnColumn())
                            || !QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() - 2, this.getCurrentPawnColumn()))
                            && !QuoridorController.anyWallRightOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
                    case West:
                        return (QuoridorController.anyWallBelowTile(gpos, this.getCurrentPawnRow() - 1, this.getCurrentPawnColumn())
                            || !QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() - 2, this.getCurrentPawnColumn()))
                            && !QuoridorController.anyWallLeftOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
                    default:
                        return false;
                }
            case West:
                if (QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() - 1)) {
                    // We expected pawn 'O' and it's not there
                    // Pawn 'X' should not be able to jump
                    return false;
                }
                if (QuoridorController.anyWallLeftOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn())) {
                    // Nothing can jump over walls
                    return false;
                }

                switch (dir2) {
                    case South:
                        return (QuoridorController.anyWallLeftOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() - 1)
                            || !QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() - 2))
                            && !QuoridorController.anyWallBelowTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
                    case North:
                        return (QuoridorController.anyWallLeftOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() - 1)
                            || !QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn() - 2))
                            && !QuoridorController.anyWallAboveTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
                    default:
                        return false;
                }
            case North:
                if (QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() + 1, this.getCurrentPawnColumn())) {
                    // We expected pawn 'O' and it's not there
                    // Pawn 'X' should not be able to jump
                    return false;
                }
                if (QuoridorController.anyWallAboveTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn())) {
                    // Nothing can jump over walls
                    return false;
                }

                switch (dir2) {
                    case East:
                        return (QuoridorController.anyWallAboveTile(gpos, this.getCurrentPawnRow() + 1, this.getCurrentPawnColumn())
                            || !QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() + 2, this.getCurrentPawnColumn()))
                            && !QuoridorController.anyWallRightOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
                    case West:
                        return (QuoridorController.anyWallAboveTile(gpos, this.getCurrentPawnRow() + 1, this.getCurrentPawnColumn())
                            || !QuoridorController.validatePawnPlacement(gpos, this.getCurrentPawnRow() + 2, this.getCurrentPawnColumn()))
                            && !QuoridorController.anyWallLeftOfTile(gpos, this.getCurrentPawnRow(), this.getCurrentPawnColumn());
                    default:
                        return false;
                }
        default:
            throw new IllegalArgumentException("Unknown jump direction: " + dir1);
        }
  }

  // line 314 "../../../../../PawnStateMachine.ump"
   private void playStepMove(int drow, int dcol){
    final int moves = this.getCurrentGame().numberOfMoves();
        final GamePosition gpos = this.getCurrentGame().getCurrentPosition();
        final Tile target = QuoridorController.getTileFromRowAndColumn(
                this.getCurrentPawnRow() + drow, this.getCurrentPawnColumn() + dcol);
        QuoridorController.forcePlayStepMove(moves, moves / 2, this.getPlayer(), target, gpos);
  }

  // line 322 "../../../../../PawnStateMachine.ump"
   private void playJumpMove(int drow, int dcol){
    final int moves = this.getCurrentGame().numberOfMoves();
        final GamePosition gpos = this.getCurrentGame().getCurrentPosition();
        final Tile target = QuoridorController.getTileFromRowAndColumn(
                this.getCurrentPawnRow() + drow, this.getCurrentPawnColumn() + dcol);
        QuoridorController.forcePlayJumpMove(moves, moves / 2, this.getPlayer(), target, gpos);
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 331 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    throw new IllegalPawnMoveException("Attempted pawn move is illegal!");
  }

  // line 335 "../../../../../PawnStateMachine.ump"
   private static  boolean inRangeExclusive(int val, int lo, int hi){
    return lo < val && val < hi;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 340 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}