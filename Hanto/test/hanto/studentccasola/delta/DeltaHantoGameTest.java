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
import org.junit.Ignore;
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
	public void tryingToDisconnectPieceDoesNotModifyBoard() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,-1));
		
		// place sparrows
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,-1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,-2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-2,2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-2,-1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,-3));
		
		// place crabs
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-2,3));
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-2,-2));
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-1,1));
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(2,-3));
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-3,2));
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-2,-3));
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-3,4));
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-2,-4));
		
		// move pieces
		game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(-1,2), new TestHantoCoordinate(1,-1));
		String board = game.getPrintableBoard();
		try {
			game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(-2,-1), new TestHantoCoordinate(0,-2));
		}
		catch(HantoException e)
		{
			assertEquals(board, game.getPrintableBoard());
		}
	}
	
	@Test
	public void peiceMayBePlaced() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,0));
	}
	
	@Test(expected=HantoException.class)
	public void cannotMoveCrabFromUnoccupiedHex() throws HantoException
	{
		try {
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0)); //blue
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1)); //red
			
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,0)); //blue
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,2)); //red
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,-1)); //blue
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,3)); //red
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,-2)); //blue
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,4)); //red
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,-3)); //blue
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,5)); //red
			
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0,-1)); //blue
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-1,4)); //red
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(1,-2)); //blue
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(1,4)); //red
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(1,-3)); //blue
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-1,2)); //red
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(2,-3)); //blue
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(2,3)); //red
			
			game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(1,-3), new TestHantoCoordinate(2,-4)); //blue
			game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(-1,2), new TestHantoCoordinate(-1,3)); //red
			
			game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(-1,0), new TestHantoCoordinate(1,0)); //blue
		}
		catch (HantoException e)
		{
			fail("The code above should not throw an exception");
		}
		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(-2,5), new TestHantoCoordinate(-1,5)); //red
	}
	
	@Test(expected=HantoException.class)
	public void cannotMovePieceAndDisconnectBoard() throws HantoException
	{
		try {
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0)); // blue butterfly
			game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,-1)); // red butterfly
			
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,1)); // blue sparrow
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,-1)); // red sparrow
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,2)); // blue sparrow
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(3,-1)); // red sparrow
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,1)); // blue sparrow
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,-2)); // red sparrow
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-2,2)); // blue sparrow
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2,-3)); // red sparrow
			
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-2,1)); // blue crab
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(1,-3)); // red crab
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-3,3)); // blue crab
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(4,-1)); // red crab
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-1,0)); // blue crab
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(2,0)); // red crab
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(-1,-1)); // blue crab
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(5,-2)); // red crab
			
			game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(-1,2), new TestHantoCoordinate(1,0)); // blue move sparrow
			
			game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(2,-1), new TestHantoCoordinate(1,-2)); // red move sparrow
			
			game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(-1,-1), new TestHantoCoordinate(-2,0)); // blue move crab
		}
		catch (HantoException e) {
			fail("No exception should have occurred in the block above");
		}
		game.makeMove(HantoPieceType.CRAB, new TestHantoCoordinate(4,-1), new TestHantoCoordinate(5,-1)); // blue move crab to disconnected cell
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
	
	@Test(expected=HantoException.class)
	public void cannotPlacePieceOnTopOfAnotherPiece() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,-1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,-1));
	}
	
	@Test(expected=HantoException.class)
	public void cannotMovePieceOntoAnotherPiece() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,-1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,1));
		
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,-1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,2));
		
		game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(1,-1), new TestHantoCoordinate(0,-1));
	}
}
