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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hanto.studentccasola.common.AbstractHantoGame;
import hanto.studentccasola.util.BasicHantoBoard;
import hanto.studentccasola.util.GameState;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * This is the gamma version of Hanto as specified in the Hanto Developer's
 * Guide. There are two types of pieces: butterflies and sparrows. Each player
 * has one butterfly and five sparrows. Blue moves first by default. Each
 * player's butterfly must be placed on the board by the fourth turn. Butterflies
 * may walk one cell. The game ends after 10 rounds.
 *
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class GammaHantoGame extends AbstractHantoGame
{
	/** The number of sparrows alloted to each player */
	public static final int NUM_SPARROWS = 5;

	/*
	 * @see hanto.studentccasola.common.AbstractHantoGame#initializeFields()
	 */
	protected void initializeFields()
	{
		gameState = new GameState(new BasicHantoBoard(), HantoPlayerColor.BLUE, 1, new HashMap<HantoPlayerColor,List<HantoPieceType>>(), MoveResult.OK, HantoPlayerColor.BLUE);
		ruleset = new GammaHantoRuleset(gameState);
		distributePieces();
	}
	

	/*
	 * @see hanto.studentccasola.common.AbstractHantoGame#distributePieces()
	 */
	private void distributePieces()
	{
		final List<HantoPieceType> bluePieces = new ArrayList<HantoPieceType>();
		final List<HantoPieceType> redPieces = new ArrayList<HantoPieceType>();

		// Give each player a butterfly
		bluePieces.add(HantoPieceType.BUTTERFLY);
		redPieces.add(HantoPieceType.BUTTERFLY);

		// Give each player NUM_SPARROWS sparrows
		for (int i = 0; i < NUM_SPARROWS; i++)
		{
			bluePieces.add(HantoPieceType.SPARROW);
			redPieces.add(HantoPieceType.SPARROW);
		}

		// Store the pieces in the game state
		gameState.getPieces().put(HantoPlayerColor.BLUE, bluePieces);
		gameState.getPieces().put(HantoPlayerColor.RED, redPieces);
	}
}
