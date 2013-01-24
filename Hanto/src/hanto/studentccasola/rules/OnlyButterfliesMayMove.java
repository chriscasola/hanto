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
import hanto.util.HantoPieceType;

/**
 * Only butterfly pieces may move.
 *
 * @author Chris Casola
 * @version Jan 24, 2013
 */
public class OnlyButterfliesMayMove implements HantoRule
{

	/* 
	 * @see hanto.studentccasola.util.HantoRule#validate(hanto.studentccasola.util.GameState)
	 */
	@Override
	public void validate(GameState state) throws HantoException
	{
		final HantoCoordinate from = state.getFrom();
		if (from != null)
		{
			if (state.getPieceType() != HantoPieceType.BUTTERFLY)
			{
				throw new HantoException("Only butterflies may move in this game.");
			}
		}

	}

}
