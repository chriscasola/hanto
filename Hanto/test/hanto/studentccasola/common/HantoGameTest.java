package hanto.studentccasola.common;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.studentccasola.HantoGameFactory;
import hanto.studentccasola.gamma.GammaHantoRuleset;
import hanto.studentccasola.util.HexCell;
import hanto.testutil.TestHantoCoordinate;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HantoGameTest
{
	protected static AbstractHantoGame game;
	
	@BeforeClass
	public static void setupBeforeClass()
	{
		game = (AbstractHantoGame) HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
	}
	
	@Before
	public void setUp()
	{
		game = (AbstractHantoGame) HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
	}
	
	@Test(expected=HantoException.class)
	public void walkingRequiresTwoConsecutiveOpenAdjacentCells() throws HantoException
	{
		game.ruleset = new GammaHantoRuleset(game.getState());
		game.ruleset.walkingPieces.add(HantoPieceType.SPARROW);
		try
		{
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,0));
			
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,-1));
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,1));
		}
		catch (HantoException e)
		{
			fail("Previous moves should have succeeded");
		}
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0,0), new TestHantoCoordinate(0,-1));
	}
	
	@Test
	public void bothPlayersLegallyPlaceButterfliesInFourthRound() throws HantoException
	{
		game.ruleset = new GammaHantoRuleset(game.getState());
		for (int i = 0; i < 6; i++)
		{
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,i));
		}
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,6)); // Legally place butterfly in fourth round
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,7)); // Legally place butterfly in fourth round
	}

	@Test(expected=HantoException.class)
	public void onePlayerLegallyPlacesButterflyOnFourthButSecondPlayerDoesNot() throws HantoException
	{
		game.ruleset = new GammaHantoRuleset(game.getState());
		try
		{
			for (int i = 0; i < 6; i++)
			{
				game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,i));
			}
	
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,6)); // Legally place butterfly in fourth round
		}
		catch (HantoException e)
		{
			fail("Prior moves should have been successful.");
		}
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,7)); // Illegally place fourth sparrow before butterfly
	}
	
	@Test
	public void gameEndsInDrawWhenBothButterfliesSurroundedInOneMove() throws HantoException
	{
		game.ruleset = new GammaHantoRuleset(game.getState());
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,0));
	
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,-1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,-1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,0));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1));
		assertEquals(MoveResult.DRAW, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,-1)));
	}
	
	@Test
	public void ableToInitializeToRed() throws HantoException
	{
		game.initialize(HantoPlayerColor.RED);
		assertEquals(HantoPlayerColor.RED, game.getState().getTurn());
	}

	@Test
	public void byDefaultInitializeToBlueFirst() throws HantoException
	{
		assertEquals(HantoPlayerColor.BLUE, game.getState().getTurn());
		game.initialize(null);
		assertEquals(HantoPlayerColor.BLUE, game.getState().getTurn());
	}
	
	@Test
	public void endGameWithDrawIfBothButterfliesSurrounded() throws HantoException
	{
		game.ruleset = new GammaHantoRuleset(game.getState());
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0))); // blue
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,0))); // red
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,-1))); // blue
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,0))); // red
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,1))); // blue
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1))); // red
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,1))); // blue
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,0))); // red
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,-1))); // blue
		assertEquals(MoveResult.DRAW, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,-1))); // red
	}

	@Test
	public void endGameWithBlueWinsIfRedButterflySurrounded() throws HantoException
	{
		game.ruleset = new GammaHantoRuleset(game.getState());
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,0))); // blue
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1,1))); // red
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1,0))); // blue
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-2,1))); // red
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-2,2))); // blue
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,2))); // red
		assertEquals(MoveResult.BLUE_WINS, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1))); // blue
	}

	@Test
	public void turnsAlternateBetweenPlayers() throws HantoException
	{
		assertEquals(HantoPlayerColor.BLUE, game.getState().getTurn());
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,0)); // blue
		assertEquals(HantoPlayerColor.RED, game.getState().getTurn());
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,0)); // blue
		assertEquals(HantoPlayerColor.BLUE, game.getState().getTurn());
	}

	@Test(expected=HantoException.class)
	public void piecesMustBeAdjacentToOtherPieces() throws HantoException
	{
		try {
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		}
		catch (HantoException e)
		{
			fail("First two moves were adjacent, they should succeed.");
		}
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(5,5));
	}

	@Test
	public void secondMoveMustNotBeAtOrigin() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,0)));
	}

	@Test(expected=HantoException.class)
	public void firstMoveMustBeAtOrigin() throws HantoException
	{
		try
		{
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		}
		catch (HantoException e)
		{
			fail("First move should be able to be made at origin");
		}
		setUp();
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
	}
	
	@Test
	public void keepTrackOfRound() throws HantoException
	{
		game.ruleset = new GammaHantoRuleset(game.getState());
		assertEquals(1, game.getState().getCurrentRound());
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		assertEquals(1, game.getState().getCurrentRound());
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		assertEquals(2, game.getState().getCurrentRound());
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,2));
		assertEquals(2, game.getState().getCurrentRound());
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,3));
		assertEquals(3, game.getState().getCurrentRound());
	}
	
	@Test(expected=HantoException.class)
	public void cannotMoveOtherPlayersButterfly() throws HantoException
	{
		try
		{
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		}
		catch (HantoException e)
		{
			fail("The above moves should succeed.");
		}
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0,1), new TestHantoCoordinate(1,0));
	}
	
	@Test(expected=HantoException.class)
	public void cannotMovePieceIfFromCellDoesNotContainSamePieceTypeAsSpecified() throws HantoException
	{
		try
		{
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1));
		}
		catch (HantoException e)
		{
			fail("The above moves should succeed.");
		}
		game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(0,0), new TestHantoCoordinate(0,2));
	}
	
	@Test(expected=HantoException.class)
	public void cannotMoveFromUnoccupiedCell() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1,-1), new TestHantoCoordinate(1,0));
	}
	
	@Test
	public void allowButterflyToMoveOneCell() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1));
		
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0,0), new TestHantoCoordinate(1,0));
		
		assertNull(game.getState().getBoard().getCellAtCoordinate(new TestHantoCoordinate(0,0)));
		assertEquals(new HexCell(new TestHantoCoordinate(1,0), HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY), game.getState().getBoard().getCellAtCoordinate(new TestHantoCoordinate(1,0)));
	}
	
	@Test(expected=HantoException.class)
	public void butterflyCannotMoveMoreThanOneCell() throws HantoException
	{
		game.ruleset = new GammaHantoRuleset(game.getState());
		game.ruleset.walkingPieces.add(HantoPieceType.BUTTERFLY);
		try
		{
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1));
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,2));
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,3));
		}
		catch (HantoException e)
		{
			fail("The above moves should succeed.");
		}
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0,0), new TestHantoCoordinate(1,1));
	}
	
	@Test
	public void butterflyCanWalkOneCellTowardBottomRight() throws HantoException {
		game.ruleset = new GammaHantoRuleset(game.getState());
		game.ruleset.walkingPieces.add(HantoPieceType.BUTTERFLY);
		
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(3,0));
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0,0), new TestHantoCoordinate(1,-1));
	}
	
	@Test(expected=HantoException.class)
	public void eachPlayerHasOnlyOneButterfly() throws HantoException
	{
		try {
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		}
		catch (HantoException e)
		{
			fail("Each person gets one butterfly, neither player has used more than one.");
		}
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,2));
	}
}
