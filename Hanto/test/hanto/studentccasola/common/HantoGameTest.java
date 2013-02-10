package hanto.studentccasola.common;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.studentccasola.HantoGameFactory;
import hanto.studentccasola.gamma.GammaHantoGame;
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
}
