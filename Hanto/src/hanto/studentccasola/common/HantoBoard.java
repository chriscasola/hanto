/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.common;

import java.util.Collection;

import hanto.common.HantoException;
import hanto.studentccasola.util.HexCell;
import hanto.studentccasola.util.HexCoordinate;
import hanto.util.HantoCoordinate;
import hanto.util.MoveResult;

/**
 * This interface defines a Hanto board. It keeps track of cells on
 * a hexagonal board and the contents of those cells. It also provides
 * functionality to ensure adjacency of all cells and to determine the
 * state of the board based on the rules of Hanto (e.g. RED_WINS, BLUE_WINS,
 * DRAW, or OK).
 *
 * @author Chris Casola
 * @version Jan 23, 2013
 */
public interface HantoBoard
{
	/**
	 * Places the given cell in the board, assuming the cell is not
	 * already occupied on the board.
	 * 
	 * @param hexCell the cell to place
	 * @throws HantoException if the cell is already occupied on the board or
	 * the cell would not be adjacent to other occupied cells.
	 */
	void placePiece(HexCell hexCell) throws HantoException;
	
	/**
	 * Moves the cell at the given from location to the given to location.
	 * @param from the original location of the cell
	 * @param to the new location of the cell
	 * @throws HantoException if there is no cell at the given from location, if
	 * there is already a cell at the given to location, or if moving the piece
	 * would result in all cells no longer being contiguous.
	 */
	void movePiece(HantoCoordinate from, HantoCoordinate to) throws HantoException;
	
	/**
	 * Returns OK if there is no winner, RED_WINS if the blue butterfly
	 * has been surrounded, BLUE_WINS if the red butterfly has been
	 * surrounded, or DRAW if both butterflies have been surrounded.
	 * 
	 * @return the current state of the board
	 */
	MoveResult getBoardState();
	
	/**
	 * Determines if the given cell would be adjacent to other cells
	 * if it were placed in the board. This method should not have any
	 * effect on the actual contents of the board.
	 * 
	 * @param cell the cell to check for adjacency
	 * @return true if the cell would be adjacent to other cells, false otherwise
	 */
	boolean isAdjacent(HexCoordinate cell);
	
	
	/**
	 * Returns the cell at the given coordinate on the board, or null
	 * if the given coordinate is unoccupied.
	 * 
	 * @param coordinate the location on the board
	 * @return the cell at the given location, or null if the location is unoccupied
	 */
	HexCell getCellAtCoordinate(HantoCoordinate coordinate);
	
	/**
	 * @return a collection of the cells that are currently occupied
	 * on the board
	 */
	Collection<HexCell> getCells();
	
	/**
	 * @return the number of cells currently occupied on the board
	 */
	int getNumOccupiedCells();
	
}
