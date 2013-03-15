/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.tournament;

import hanto.common.HantoException;
import hanto.studentccasola.common.HantoMoveStrategy;
import hanto.studentccasola.delta.DeltaHantoGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoPlayerColor;

/**
 * This class implements a player for the delta version of Hanto. It
 * uses the DeltaMoveStrategy to pick moves.
 * 
 *
 * @author Chris Casola
 * @version Feb 26, 2013
 */
public class DeltaHantoPlayer implements HantoGamePlayer
{
	private final DeltaHantoGame game;
	private final HantoMoveStrategy moveStrategy;

	/**
	 * Constructs the player
	 * 
	 * @param playerColor the color of this player
	 * @param isFirst indicates if this player moves first
	 */
	public DeltaHantoPlayer(HantoPlayerColor playerColor, boolean isFirst)
	{
		game = new DeltaHantoGame();
		moveStrategy = new DeltaMoveStrategy(game, playerColor);
	}

	/* 
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		HantoMoveRecord result = null;
		try
		{
			// Record the opponent's move in the game if they moved
			if (opponentsMove != null)
			{
				game.makeMove(opponentsMove.getPiece(), 
						opponentsMove.getFrom(), opponentsMove.getTo());
			}
			
			// Get a move to make using the strategy
			result = moveStrategy.getNextMove(opponentsMove);
			
			// Record this player's move in the game
			game.makeMove(result.getPiece(), result.getFrom(), result.getTo());
		}
		catch (HantoException e)
		{
			// Return null if unable to choose a move
			result = null;
		}

		// Return the move that was chosen
		return result;
	}
	
	/**
	 * Seeds the random number generator
	 * 
	 * @param seed the seed to use
	 */
	protected void randSeed(long seed)
	{
		moveStrategy.setSeed(seed);
	}
	
}
