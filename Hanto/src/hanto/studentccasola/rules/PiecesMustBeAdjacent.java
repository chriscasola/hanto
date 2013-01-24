/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.rules;

import hanto.common.HantoException;
import hanto.studentccasola.common.HantoBoard;
import hanto.studentccasola.util.GameState;
import hanto.studentccasola.util.HantoRule;
import hanto.studentccasola.util.HexCell;

/**
 * All pieces must be adjacent
 *
 * @author Chris Casola
 * @version Jan 24, 2013
 */
public class PiecesMustBeAdjacent implements HantoRule
{

	/* 
	 * @see hanto.studentccasola.util.HantoRule#validate(hanto.studentccasola.util.GameState)
	 */
	@Override
	public void validate(GameState state) throws HantoException
	{
		final HexCell currentMove = 
				new HexCell(state.getTo(), state.getTurn(), state.getPieceType());
		
		final HantoBoard board = state.getBoard();
		
		if (!board.isAdjacent(currentMove) && board.getNumOccupiedCells() > 0)
		{
			throw new HantoException("Pieces must be placed adjacent to other pieces.");
		}
	}

}
