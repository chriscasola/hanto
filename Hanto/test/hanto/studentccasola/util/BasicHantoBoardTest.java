/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import hanto.common.HantoException;
import hanto.studentccasola.util.BasicHantoBoard;
import hanto.studentccasola.util.HexCell;
import hanto.studentccasola.util.HexCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the BasicHantoBoard class.
 *
 * @author Chris Casola
 * @version Jan 23, 2013
 */
public class BasicHantoBoardTest
{
	private BasicHantoBoard board;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		board = new BasicHantoBoard();
	}

	@Test
	public void gameBoardStateDrawIfBothButterfliesSurrounded() throws HantoException
	{
		board.placePiece(new HexCell(new HexCoordinate(0,0), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(-1,1), HantoPlayerColor.RED, HantoPieceType.BUTTERFLY));
		board.placePiece(new HexCell(new HexCoordinate(-1,0), HantoPlayerColor.RED, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(-2,1), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(-2,2), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(-1,2), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));

		board.placePiece(new HexCell(new HexCoordinate(0,1), HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY));
		board.placePiece(new HexCell(new HexCoordinate(0,2), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(1,1), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(1,0), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));

		assertEquals(MoveResult.DRAW, board.getBoardState());
	}

	@Test
	public void gameBoardStateBlueWinsIfRedButterflySurrounded() throws HantoException
	{
		board.placePiece(new HexCell(new HexCoordinate(-1,1), HantoPlayerColor.RED, HantoPieceType.BUTTERFLY));
		board.placePiece(new HexCell(new HexCoordinate(-1,0), HantoPlayerColor.RED, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(-2,1), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(-2,2), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(-1,2), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(0,1), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		board.placePiece(new HexCell(new HexCoordinate(0,0), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		assertEquals(MoveResult.BLUE_WINS, board.getBoardState());
	}

	@Test
	public void gameBoardStateOKIfNoButterflyIsSurrounded() throws HantoException
	{
		board.placePiece(new HexCell(new HexCoordinate(0,0), HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY));
		board.placePiece(new HexCell(new HexCoordinate(1,0), HantoPlayerColor.RED, HantoPieceType.BUTTERFLY));
		board.placePiece(new HexCell(new HexCoordinate(0,1), HantoPlayerColor.BLUE, HantoPieceType.SPARROW));
		assertEquals(MoveResult.OK, board.getBoardState());
	}

	/**
	 * Determine if a cell is adjacent to at least one other cell
	 * 
	 * @throws HantoException
	 */
	@Test
	public void determineIfACellWouldBeAdjacentToAtLeastOneOtherCell() throws HantoException
	{
		HexCell hexCell1 = new HexCell(new HexCoordinate(-1,1), HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
		HexCell hexCell2 = new HexCell(new HexCoordinate(-1,2), HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
		board.placePiece(hexCell1);
		board.placePiece(hexCell2);

		assertTrue(board.isAdjacent(new HexCell(new HexCoordinate(0,1), HantoPlayerColor.BLUE, HantoPieceType.SPARROW)));
		assertFalse(board.isAdjacent(new HexCell(new HexCoordinate(1,0), HantoPlayerColor.BLUE, HantoPieceType.SPARROW)));
	}

	/**
	 * Get adjacent cells when some cells are occupied
	 * 
	 * @throws HantoException
	 */
	@Test
	public void getAdjacentOccupiedCells() throws HantoException
	{
		HexCell hexCell1 = new HexCell(new HexCoordinate(-1,1), HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
		HexCell hexCell2 = new HexCell(new HexCoordinate(-1,2), HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
		board.placePiece(hexCell1);
		board.placePiece(hexCell2);
		Set<HexCell> neighbors = board.getNeighbors(new HexCoordinate(0,1));
		assertEquals(2, neighbors.size());
		assertTrue(neighbors.contains(hexCell1));
		assertTrue(neighbors.contains(hexCell2));
	}

	/**
	 * Get adjacent cells when no cells are occupied
	 */
	@Test
	public void getAdjacentOccupiedHexCellsWhenNoCellsAreOccupied()
	{
		assertEquals(0, board.getNeighbors(new HexCoordinate(0,0)).size());
	}

	/**
	 * Should be able to get the coordinates of cells adjacent
	 * to a cell
	 */
	@Test
	public void getAdjacentCellsOfCellDownRightFromOrigin()
	{
		HexCoordinate coordinate = new HexCoordinate(1,-1);

		ArrayList<HexCoordinate> expectedNeighbors = new ArrayList<HexCoordinate>();
		expectedNeighbors.add(new HexCoordinate(1, -2));
		expectedNeighbors.add(new HexCoordinate(0, -1));
		expectedNeighbors.add(new HexCoordinate(0, 0));
		expectedNeighbors.add(new HexCoordinate(1, 0));
		expectedNeighbors.add(new HexCoordinate(2, -1));
		expectedNeighbors.add(new HexCoordinate(2, -2));

		assertTrue(coordinate.getAdjacentCoordinates().containsAll(expectedNeighbors));
	}

	/**
	 * Should be able to get the coordinates of cells adjacent to the origin
	 */
	@Test
	public void getCoordinatesAdjacentToOrigin()
	{
		HexCoordinate coordinate = new HexCoordinate(0,0);

		ArrayList<HexCoordinate> expectedNeighbors = new ArrayList<HexCoordinate>();
		expectedNeighbors.add(new HexCoordinate(0, -1));
		expectedNeighbors.add(new HexCoordinate(-1, 0));
		expectedNeighbors.add(new HexCoordinate(-1, 1));
		expectedNeighbors.add(new HexCoordinate(0, 1));
		expectedNeighbors.add(new HexCoordinate(1, 0));
		expectedNeighbors.add(new HexCoordinate(1, -1));

		assertTrue(coordinate.getAdjacentCoordinates().containsAll(expectedNeighbors));
	}

	@Test
	public void boardCanBePrinted() throws HantoException
	{
		HexCell hexCell1 = new HexCell(new HexCoordinate(-1,1), HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
		HexCell hexCell2 = new HexCell(new HexCoordinate(-1,2), HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
		board.placePiece(hexCell1);
		board.placePiece(hexCell2);

		String expectedOutput = "Coordinate: (-1,1) Player: BLUE Piece: Sparrow\n" +
				"Coordinate: (-1,2) Player: BLUE Piece: Sparrow\n";

		assertEquals(expectedOutput, board.toString());
	}

	/**
	 * Make sure a piece cannot be placed in an occupied cell
	 * @throws HantoException 
	 */
	@Test(expected=HantoException.class)
	public void onlyOnePieceMayOccupyEachCell() throws HantoException
	{
		HexCell hexCell = new HexCell(new HexCoordinate(0,0), HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
		HexCell hexCell2 = new HexCell(new HexCoordinate(0,0), HantoPlayerColor.RED, HantoPieceType.SPARROW);
		try
		{
			board.placePiece(hexCell);
		}
		catch (HantoException e) {
			fail("The cell was not occupied, so the first placePiece() should have succeeded.");
		}
		board.placePiece(hexCell2);
	}

	/**
	 * A piece can be placed on the board, and hence occupy a cell
	 * @throws HantoException
	 */
	@Test
	public void placePieceInBoard() throws HantoException
	{
		HexCoordinate origin = new HexCoordinate(0,0);
		HexCell hexCell = new HexCell(origin, HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
		assertNull(board.getCellAtCoordinate(origin));
		board.placePiece(hexCell);
		assertTrue(board.getCells().contains(hexCell));
		assertEquals(hexCell, board.getCellAtCoordinate(origin));
	}

	/**
	 * Board is initialized with no occupied cells
	 */
	@Test
	public void boardInitializedWithNoOccupiedCells()
	{
		assertEquals(0, board.getCells().size());
	}

}
