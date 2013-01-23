/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.alpha;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.studentccasola.alpha.AlphaHantoGame;
import hanto.studentccasola.alpha.HexCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for AlphaHantoGame
 *
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class AlphaHantoGameTest
{
	
	private AlphaHantoGame game;
	
	@Before
	public void setUp()
	{
		game = new AlphaHantoGame();
	}
	
	/**
	 * The second move must be placed adjacent to the first move
	 * 
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void redMustPlaceButterflyAdjacentToBlue() throws HantoException {
		try
		{
			game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
		}
		catch (HantoException e) {
			fail("First move should succeed.");
		}
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(2,0));
	}
	
	/**
	 * The first move must be by made by blue with a butterfly at (0,0)
	 * 
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void blueMustPlaceButterflyAtZeroZero() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,1));
	}
	
	/**
	 * Second call to make move should return DRAW
	 * 
	 * @throws HantoException
	 */
	@Test
	public void secondCallToMakeMoveReturnsDRAW() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
		assertEquals(MoveResult.DRAW, game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,1)));
	}
	
	/**
	 * First call to make move should return OK, assuming a valid
	 * piece and coordinate were passed.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void firstCallToMakeMoveReturnsOK() throws HantoException
	{
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0)));
	}
	
	/**
	 * Make sure only butterfly pieces may be moved
	 * 
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void onlyButterflyPiecesMayBeMoved() throws HantoException
	{
		game.makeMove(HantoPieceType.CRAB, null, new HexCoordinate(0,0));
	}
	
	/**
	 * Make sure the second move is by red and the piece is a butterfly
	 * 
	 * @throws HantoException
	 */
	@Test
	public void redMovesSecond() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,1));
		assertEquals(HantoPlayerColor.RED, game.getBoard().get(1).getPlayer());
		assertEquals(HantoPieceType.BUTTERFLY, game.getBoard().get(1).getPiece());
	}
	
	/**
	 * Make sure the first move is by blue and the piece is a Butterfly
	 * 
	 * @throws HantoException
	 */
	@Test
	public void blueMovesFirst() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
		assertEquals(HantoPlayerColor.BLUE, game.getBoard().get(0).getPlayer());
		assertEquals(HantoPieceType.BUTTERFLY, game.getBoard().get(0).getPiece());
	}
	
	/**
	 * Make sure a butterfly can be placed on the board at (0,0)
	 * 
	 * @throws HantoException
	 */
	@Test
	public void placeButterflyAtZeroZero() throws HantoException {
		final HexCoordinate origin = new HexCoordinate(0, 0);
		game.makeMove(HantoPieceType.BUTTERFLY, null, origin);
		assertEquals(origin, game.getBoard().get(0).getCoordinate());
		assertEquals(HantoPieceType.BUTTERFLY, game.getBoard().get(0).getPiece());
	}
	
	/**
	 * getPrintableBoard() should return a string representation of
	 * the game board.
	 * 
	 * @throws HantoException
	 */
	@Test
	public void printTheGameBoard() throws HantoException {
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,1));
		assertEquals("[Coordinate: (0,0) Player: BLUE Piece: Butterfly, Coordinate: (0,1) Player: RED Piece: Butterfly]",
				game.getPrintableBoard());
	}
	
	@Test
	public void dummyTestToExerciseEnums() {
		HantoPlayerColor.valueOf("BLUE");
		MoveResult.valueOf("DRAW");
		HantoPieceType.BUTTERFLY.toString();
		HantoPieceType.BUTTERFLY.getSymbol();
		HantoPieceType.valueOf("BUTTERFLY");
	}
	
	/**
	 * Make sure game cannot be initialized with red as the first player
	 * @throws HantoException 
	 */
	@Test(expected=HantoException.class)
	public void boardMustBeInitializedWithBlueFirst() throws HantoException {
		game.initialize(HantoPlayerColor.RED);
	}
	
	/**
	 * Make sure that when the board is initialized, blue will move first
	 * 
	 * @throws HantoException
	 */
	@Test
	public void boardInitializedWithBlueMovingFirst() throws HantoException {
		game.initialize(HantoPlayerColor.BLUE);
		assertEquals(HantoPlayerColor.BLUE, game.getTurn());
	}

}
