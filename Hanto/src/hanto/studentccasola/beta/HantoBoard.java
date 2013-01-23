/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.beta;

import hanto.common.HantoException;
import hanto.studentccasola.alpha.HexCell;
import hanto.studentccasola.alpha.HexCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A Hanto board. This board keeps track of the placement of Hanto
 * pieces and performs other functions, including checking the adjacency
 * of cells and determining if butterflies are surrounded.
 *
 * @author Chris Casola
 * @version Jan 23, 2013
 */
public class HantoBoard
{
	private final Map<HexCoordinate, HexCell> coordinateMap;
	
	
	/**
	 * Constructs a hanto board that initially contains no cells
	 */
	public HantoBoard()
	{
		coordinateMap = new HashMap<HexCoordinate, HexCell>();
	}

	/**
	 * Places the given cell in the board, assuming the cell is not
	 * already occupied on the board.
	 * 
	 * @param hexCell the cell to place
	 * @throws HantoException if the cell is already occupied on the board
	 */
	public void placePiece(HexCell hexCell) throws HantoException
	{
		if (coordinateMap.containsKey(hexCell.getCoordinate()))
		{
			throw new HantoException("This cell already contains a piece.");
		}
		else
		{
			coordinateMap.put(hexCell.getCoordinate(), hexCell);
		}
	}
	
	/**
	 * Returns OK if there is no winner, RED_WINS if the blue butterfly
	 * has been surrounded, BLUE_WINS if the red butterfly has been
	 * surrounded, or DRAW if both butterflies have been surrounded.
	 * 
	 * @return the current state of the board
	 */
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
	
	/**
	 * @return the cells currently in the board
	 */
	public Collection<HexCell> getCells()
	{
		return coordinateMap.values();
	}
	
	/**
	 * Determines if the given cell would be adjacent to other cells
	 * if it were in the board.
	 * 
	 * @param cell the cell to check for adjacency
	 * @return true if the cell would be adjacent to other cells, false otherwise
	 */
	protected boolean isAdjacent(HexCell cell)
	{
		boolean isAdjacent = false;
		if (getNeighbors(cell.getCoordinate()).size() > 0)
		{
			isAdjacent = true;
		}
		return isAdjacent;
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
}
