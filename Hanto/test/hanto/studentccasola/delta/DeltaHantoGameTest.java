/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.delta;

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

public class DeltaHantoGameTest extends HantoGameTest
{
	@BeforeClass
	public static void setupBeforeClass()
	{
		game = (AbstractHantoGame) HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
	}
	
	@Before
	public void setUp()
	{
		game = (AbstractHantoGame) HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
	}
	
	@Test
	public void peiceMayBePlaced() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,0));
	}
	
	@Test(expected=HantoException.class)
	public void pieceCannotBePlacedNextToPieceOfOppositeColor() throws HantoException
	{
		try {
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,0));
		}
		catch (HantoException e)
		{
			fail("Unexpected exception: " + e.toString());
		}
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,-1));
	}
	
	@Test(expected=HantoException.class)
	public void pieceMustBePlacedNextToPieceOfLikeColor() throws HantoException
	{
		try {
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,0));
		}
		catch (HantoException e)
		{
			fail("Unexpected exception: " + e.toString());
		}
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,0));
	}
	
	@Test
	public void sparrowCanFly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,0));
	
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,-1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,-1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,0));
		
		game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(-1,0), new TestHantoCoordinate(1,-2));
		assertNull(game.getState().getBoard().getCellAtCoordinate(new TestHantoCoordinate(-1,0)));
		assertEquals(new HexCell(new TestHantoCoordinate(1,-2), HantoPlayerColor.BLUE, HantoPieceType.SPARROW), 
				game.getState().getBoard().getCellAtCoordinate(new TestHantoCoordinate(1,-2)));
	}
	
	@Test(expected=HantoException.class)
	public void crabCannotFly() throws HantoException
	{
		try {
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,0));
		
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0,-1));
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(2,-1));
			
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-1,0));
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(2,0));
		}
		catch (HantoException e)
		{
			fail("Unexpected exception: " + e.toString());
		}
		
		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(-1,0), new TestHantoCoordinate(1,-2));
	}
	
	@Test(expected=HantoException.class)
	public void cannotWalkBeforePlacingButterfly() throws HantoException
	{
		try {
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0,0));
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0,1));
		}
		catch (HantoException e) {
			fail("Did not expect: " + e.toString());
		}
		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(0,0), new TestHantoCoordinate(1,0));
	}
	
	@Test
	public void crabMayWalkOneHex() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(1,-1));
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(1,1));
		
		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(1,-1), new TestHantoCoordinate(1,0));
		
		assertNull(game.getState().getBoard().getCellAtCoordinate(new TestHantoCoordinate(1,-1)));
		assertEquals(new HexCell(new TestHantoCoordinate(1,0), HantoPlayerColor.BLUE, HantoPieceType.CRAB), game.getState().getBoard().getCellAtCoordinate(new TestHantoCoordinate(1,0)));
	}
	
	@Test
	public void playerCanResignByPassingNulls() throws HantoException
	{
		assertEquals(MoveResult.RED_WINS, game.makeMove(null, null, null));
		assertEquals(MoveResult.RED_WINS, game.makeMove(null, null, null));
		assertEquals(MoveResult.RED_WINS, game.getState().getStatus());
	}
	
	@Test
	public void gameProceedsIndefinitely() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		
		for (int i = 1; i < 5; i++)
		{
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,-i));
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,i+1));
		}
		
		for (int i = 5; i < 8; i++)
		{
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0,-i));
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0,i+1));
		}
	}
}
