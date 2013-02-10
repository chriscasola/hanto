/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.gamma;

import hanto.common.HantoException;
import hanto.studentccasola.common.HantoRuleset;
import hanto.studentccasola.util.GameState;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.MoveResult;

/**
 * The ruleset for the Gamma version of Hanto
 * 
 * @author Chris Casola
 * @version Feb 9, 2013
 */
public class GammaHantoRuleset extends HantoRuleset
{
	public GammaHantoRuleset(GameState gameState)
	{
		super(gameState);
		getWalkingPieces().add(HantoPieceType.BUTTERFLY);
	}
	
	/*
	 * @see hanto.studentccasola.common.HantoRuleset#postMoveChecks(
	 * hanto.util.HantoPieceType, hanto.util.HantoCoordinate, 
	 * hanto.util.HantoCoordinate)
	 */
	@Override
	public void postMoveChecks(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		super.postMoveChecks(pieceType, from, to);
		onlyAllowTenRounds();
	}

	/**
	 * The game should end with a draw if there is no winner after
	 * ten rounds of play
	 */
	protected void onlyAllowTenRounds()
	{
		if (gameState.getCurrentRound() > 10 && gameState.getStatus() == MoveResult.OK)
		{
			gameState.setStatus(MoveResult.DRAW);
		}
	}
}
