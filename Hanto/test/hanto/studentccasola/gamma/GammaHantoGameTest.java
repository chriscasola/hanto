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
import hanto.studentccasola.HantoGameFactory;
import hanto.studentccasola.common.AbstractHantoGame;
import hanto.studentccasola.common.HantoGameTest;
import hanto.studentccasola.common.HexCell;
import hanto.testutil.TestHantoCoordinate;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the gamma version of Hanto
 *
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class GammaHantoGameTest extends HantoGameTest
{
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
		assertEquals(10, game.getState().getCurrentRound());
		assertEquals(MoveResult.DRAW, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(1,1), new TestHantoCoordinate(1,0)));
		assertEquals(11, game.getState().getCurrentRound());
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
}
