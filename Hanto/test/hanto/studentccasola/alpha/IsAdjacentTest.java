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
import hanto.util.HantoPieceType;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests to make sure the adjacency checker in AlphaHantoGame
 * performs as expected.
 *
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class IsAdjacentTest
{

	private AlphaHantoGame game;

	@Before
	public void setUp() throws HantoException
	{
		game = new AlphaHantoGame();
		game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
	}
	
	/**
	 * Check a couple cases far from the origin
	 */
	@Test
	public void checkPointsFarFromOrigin()
	{
		assertFalse(game.isAdjacent(new HexCoordinate(10, 20)));
		assertFalse(game.isAdjacent(new HexCoordinate(15, -20)));
	}
	
	/**
	 * Check edge cases right of the origin
	 */
	@Test
	public void checkEdgeCasesRightOfOrigin()
	{
		assertTrue(game.isAdjacent(new HexCoordinate(1,0)));
		assertTrue(game.isAdjacent(new HexCoordinate(1,-1)));
		assertFalse(game.isAdjacent(new HexCoordinate(1, 1)));
		assertFalse(game.isAdjacent(new HexCoordinate(1, -2)));
	}
	
	/**
	 * Check edge cases above and below the origin
	 */
	@Test
	public void checkEdgeCasesAboveBelowOrigin()
	{
		assertTrue(game.isAdjacent(new HexCoordinate(0,1)));
		assertTrue(game.isAdjacent(new HexCoordinate(0,-1)));
		assertFalse(game.isAdjacent(new HexCoordinate(0,2)));
		assertFalse(game.isAdjacent(new HexCoordinate(0,-2)));
	}
	
	/**
	 * Check edge cases left of the origin
	 */
	@Test
	public void checkEdgeCasesLeftOfOrigin()
	{
		assertTrue(game.isAdjacent(new HexCoordinate(-1,0)));
		assertTrue(game.isAdjacent(new HexCoordinate(-1,1)));
		assertFalse(game.isAdjacent(new HexCoordinate(-1, -1)));
		assertFalse(game.isAdjacent(new HexCoordinate(-1, 2)));
	}

}
