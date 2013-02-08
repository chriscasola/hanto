/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.gamma;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.studentccasola.gamma.GammaHantoGame;
import hanto.studentccasola.util.HexCell;
import hanto.testutil.TestHantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the gamma version of Hanto
 *
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class GammaHantoGameTest
{

	private GammaHantoGame game;

	@Before
	public void setUp()
	{
		game = new GammaHantoGame();
	}
	
	@Test
	public void allowButterflyToMoveOneCell() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1));
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0,0), new TestHantoCoordinate(1,0));
		assertNull(game.getBoard().getCellAtCoordinate(new TestHantoCoordinate(0,0)));
		assertEquals(new HexCell(new TestHantoCoordinate(1,0), HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY), game.getBoard().getCellAtCoordinate(new TestHantoCoordinate(1,0)));
	}
	
	@Test(expected=HantoException.class)
	public void cannotMoveFromUnoccupiedCell() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1,-1), new TestHantoCoordinate(1,0));
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
	public void butterflyCannotMoveMoreThanOneCell() throws HantoException
	{
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
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(3,0));
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0,0), new TestHantoCoordinate(1,-1));
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
	public void cannotMoveSparrows() throws HantoException
	{
		try
		{
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		}
		catch (HantoException e)
		{
			fail("The above moves should succeed.");
		}
		game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(0,0), new TestHantoCoordinate(1,0));
	}
	
	@Test
	public void endGameWithDrawAfterTenRoundWhenButterflyNotSurrounded() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,0));
		
		for (int i = 0; i < 3; i++)
		{
			assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1,0), new TestHantoCoordinate(-1,1)));
			assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1,0), new TestHantoCoordinate(1,1)));
			assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1,1), new TestHantoCoordinate(-1,0)));
			assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1,1), new TestHantoCoordinate(1,0)));
		}
		
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1,0), new TestHantoCoordinate(-1,1)));
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1,0), new TestHantoCoordinate(1,1)));
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(-1,1), new TestHantoCoordinate(-1,0)));
		assertEquals(10, game.getRound());
		assertEquals(MoveResult.DRAW, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1,1), new TestHantoCoordinate(1,0)));
		assertEquals(11, game.getRound());
	}

	@Test
	public void keepTrackOfRound() throws HantoException
	{
		assertEquals(1, game.getRound());
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		assertEquals(1, game.getRound());
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		assertEquals(2, game.getRound());
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,2));
		assertEquals(2, game.getRound());
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,3));
		assertEquals(3, game.getRound());
	}
	
	@Test
	public void bothPlayersLegallyPlaceButterfliesInFourthRound() throws HantoException
	{
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

	@Test(expected=HantoException.class)
	public void butterflyMustBeOnBoardByFourthTurn() throws HantoException
	{
		try
		{
			for (int i = 0; i < 6; i++)
			{
				game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,i));
			}
		}
		catch (HantoException e)
		{
			fail("Prior moves should have succeeded.");
		}
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,6)); // Illegally place fourth sparrow before butterfly
	}

	@Test(expected=HantoException.class)
	public void eachPlayerHasOnlyFiveSparrows() throws HantoException
	{
		try {
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0)); // blue
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1)); // red

			for (int i = 2; i < 12; i++)
			{
				game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,i));
			}
		}
		catch (HantoException e)
		{
			fail("Prior moves should have succeeded.");
		}

		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,12)); // blue (using a non-existant 6th sparrow)
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
		assertEquals(HantoPlayerColor.BLUE, game.getTurn());
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,0)); // blue
		assertEquals(HantoPlayerColor.RED, game.getTurn());
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,0)); // blue
		assertEquals(HantoPlayerColor.BLUE, game.getTurn());
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
	public void ableToInitializeToRed() throws HantoException
	{
		game.initialize(HantoPlayerColor.RED);
		assertEquals(HantoPlayerColor.RED, game.getTurn());
	}

	@Test
	public void byDefaultInitializeToBlueFirst() throws HantoException
	{
		assertEquals(HantoPlayerColor.BLUE, game.getTurn());
		game.initialize(null);
		assertEquals(HantoPlayerColor.BLUE, game.getTurn());
	}

}
