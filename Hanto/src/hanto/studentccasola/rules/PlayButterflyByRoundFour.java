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
import hanto.util.HantoPieceType;

/**
 *
 *
 * @author Chris Casola
 * @version Jan 24, 2013
 */
public class PlayButterflyByRoundFour implements HantoRule
{

	/* 
	 * @see hanto.studentccasola.util.HantoRule#validate(hanto.studentccasola.util.GameState)
	 */
	@Override
	public void validate(GameState state) throws HantoException
	{
		if (state.getCurrentRound() == 4 && 
				state.getPieceType() != HantoPieceType.BUTTERFLY && 
				state.getPieces().get(state.getTurn()).contains(HantoPieceType.BUTTERFLY))
		{
			throw new HantoException("You must play your butterfly before or during round 4.");
		}
	}

}
