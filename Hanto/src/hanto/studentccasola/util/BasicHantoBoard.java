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

import hanto.common.HantoException;
import hanto.studentccasola.common.HantoBoard;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A basic Hanto board. This board keeps track of the placement of Hanto
 * pieces and performs other functions, including checking the adjacency
 * of cells and determining if butterflies are surrounded. This implementation
 * uses a relatively primitive data structure and algorithms for determining
 * adjacency.
 *
 * @author Chris Casola
 * @version Jan 23, 2013
 */
public class BasicHantoBoard implements HantoBoard
{

	/** A map with coordinate as the key and cells as the value. */
	private final Map<HexCoordinate, HexCell> coordinateMap;


	/**
	 * Constructs a hanto board that initially contains no cells
	 */
	public BasicHantoBoard()
	{
		coordinateMap = new HashMap<HexCoordinate, HexCell>();
	}

	/* 
	 * @see hanto.studentccasola.common.HantoBoard#placePiece(hanto.studentccasola.util.HexCell)
	 */
	@Override
	public void placePiece(HexCell hexCell) throws HantoException
	{
		if (coordinateMap.containsKey(hexCell.getCoordinate()))
		{
			throw new HantoException("This cell already contains a piece.");
		}
		else if (isAdjacent(hexCell) || getNumOccupiedCells() < 1)
		{
			coordinateMap.put(hexCell.getCoordinate(), hexCell);
		}
		else {
			throw new HantoException("This piece would not be adjacent to other pieces.");
		}
	}

	/* 
	 * @see hanto.studentccasola.common.HantoBoard#movePiece(hanto.util.HantoCoordinate, hanto.util.HantoCoordinate)
	 */
	public void movePiece(HantoCoordinate from, HantoCoordinate to) throws HantoException
	{
		// Get the cell currently at the from coordinate
		final HexCell oldCell = coordinateMap.get(from);
		if (oldCell == null)
		{
			throw new HantoException("There is no cell at the given from coordinate");
		}

		// Create the new cell, replacing the coordinate of the old cell
		final HexCell newCell = new HexCell(to, oldCell.getPlayer(), oldCell.getPiece());

		// Remove the old cell
		coordinateMap.remove(from);

		// Place the new cell in the board
		try
		{
			placePiece(newCell);
			checkContiguity();
			
		}
		catch (HantoException e) // error occured placing the new cell, put the old one back
		{
			coordinateMap.put(oldCell.getCoordinate(), oldCell);
			throw new HantoException("Cannot place new cell.", e);
		}
	}

	/* 
	 * @see hanto.studentccasola.common.HantoBoard#getBoardState()
	 */
	@Override
	public MoveResult getBoardState() {
		MoveResult state = MoveResult.OK;
		for (HexCell cell : coordinateMap.values())
		{
			if (cell.getPiece() == HantoPieceType.BUTTERFLY &&
					getNeighbors(cell.getCoordinate()).size() == 6)
			{
				if (state == MoveResult.OK)
				{
					if (cell.getPlayer() == HantoPlayerColor.BLUE)
					{
						state = MoveResult.RED_WINS;
					}
					else
					{
						state = MoveResult.BLUE_WINS;
					}
				}
				else
				{
					state = MoveResult.DRAW;
				}
			}
		}
		return state;
	}

	/* 
	 * @see hanto.studentccasola.common.HantoBoard#getCellAtCoordinate(hanto.util.HantoCoordinate)
	 */
	public HexCell getCellAtCoordinate(HantoCoordinate coordinate)
	{
		return coordinateMap.get(new HexCoordinate(coordinate.getX(), coordinate.getY()));
	}

	/* 
	 * @see hanto.studentccasola.common.HantoBoard#getCells()
	 */
	@Override
	public Collection<HexCell> getCells()
	{
		return coordinateMap.values();
	}

	/* 
	 * @see hanto.studentccasola.common.HantoBoard#getNumOccupiedCells()
	 */
	@Override
	public int getNumOccupiedCells()
	{
		return coordinateMap.values().size();
	}

	/* 
	 * @see hanto.studentccasola.common.HantoBoard#isAdjacent(hanto.studentccasola.util.HexCell)
	 */
	@Override
	public boolean isAdjacent(HexCell cell)
	{
		boolean isAdjacent = false;
		if (getNeighbors(cell.getCoordinate()).size() > 0)
		{
			isAdjacent = true;
		}
		return isAdjacent;
	}

	@Override
	public String toString()
	{
		String retVal = "";
		for (HexCell cell : coordinateMap.values())
		{
			retVal += cell.toString() + "\n";
		}
		return retVal;
	}

	/**
	 * Returns the set of cells currently in the board that are neighbors
	 * of the given coordinate.
	 * 
	 * @param givenCoordinate the coordinate to check for neighbors
	 * @return the set of cells that are neighbors of the given coordinate
	 */
	protected Set<HexCell> getNeighbors(HexCoordinate givenCoordinate)
	{
		final Set<HexCell> neighbors = new HashSet<HexCell>();
		final Set<HexCoordinate> neighborCoordinates = givenCoordinate.getAdjacentCoordinates();
		for (HexCoordinate currentCoordinate : neighborCoordinates)
		{
			if (coordinateMap.get(currentCoordinate) != null)
			{
				neighbors.add(coordinateMap.get(currentCoordinate));
			}
		}
		return neighbors;
	}

	/**
	 * Ensure that the board is contiguous. That is all cells make up one
	 * contiguous group.
	 * @throws HantoException if the board is not contiguous
	 */
	private void checkContiguity() throws HantoException
	{
		final Set<HexCoordinate> visitedCoords = new HashSet<HexCoordinate>();
		final Iterator<HexCoordinate> coordIterator = coordinateMap.keySet().iterator();
		if (coordIterator.hasNext())
		{
			visitCoordinate(visitedCoords, coordIterator.next());
			if (!visitedCoords.containsAll(coordinateMap.keySet()))
			{
				throw new HantoException("The board is not contiguous.");
			}
		}
	}

	private void visitCoordinate(Set<HexCoordinate> visitedCoords, HexCoordinate currentCoord)
	{
		if (!visitedCoords.contains(currentCoord))
		{
			visitedCoords.add(currentCoord);
			for (HexCell cell : getNeighbors(currentCoord))
			{
				visitCoordinate(visitedCoords, cell.getCoordinate());
			}
		}
	}
}
