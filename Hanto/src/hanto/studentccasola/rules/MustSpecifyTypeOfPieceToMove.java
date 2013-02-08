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
import hanto.studentccasola.util.GameState;
import hanto.studentccasola.util.HantoRule;
import hanto.util.HantoCoordinate;

/**
 * The type of piece being moved must be specified and match the actual
 * type of the piece.
 *
 * @author Chris Casola
 * @version Jan 24, 2013
 */
public class MustSpecifyTypeOfPieceToMove implements HantoRule
{

	/* 
	 * @see hanto.studentccasola.util.HantoRule#validate(hanto.studentccasola.util.GameState)
	 */
	@Override
	public void validate(GameState state) throws HantoException
	{
		final HantoCoordinate from = state.getFrom();
		if (from != null && state.getBoard().getCellAtCoordinate(from) != null)
		{
			if (state.getBoard().getCellAtCoordinate(from).getPiece() != 
					state.getPieceType())
			{
				throw new HantoException(
						"The type of piece to move does not match the piece in the from cell.");
			}
		}
	}

}
