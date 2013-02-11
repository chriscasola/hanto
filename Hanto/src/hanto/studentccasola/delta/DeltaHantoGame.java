/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.delta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hanto.common.HantoException;
import hanto.studentccasola.common.AbstractHantoGame;
import hanto.studentccasola.common.BasicHantoBoard;
import hanto.studentccasola.common.GameState;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * The Delta version of Hanto. This version provides additional
 * pieces to each player, namely four sparrows (who can fly), four
 * crabs (who may walk one hex), and one butterfly (who may walk one hex), 
 * 
 * @author Chris Casola
 * @version Feb 9, 2013
 */
public class DeltaHantoGame extends AbstractHantoGame
{
	private static final int NUM_SPARROWS = 4;
	private static final int NUM_CRABS = 4;

	/*
	 * @see hanto.studentccasola.common.AbstractHantoGame#initializeFields()
	 */
	@Override
	protected void initializeFields()
	{
		gameState = new GameState(new BasicHantoBoard(), HantoPlayerColor.BLUE, 
				1 /* round 1 */, new HashMap<HantoPlayerColor,List<HantoPieceType>>(), 
				MoveResult.OK, HantoPlayerColor.BLUE);
		ruleset = new DeltaHantoRuleset(gameState);
		distributePieces();
	}

	/*
	 * @see hanto.studentccasola.common.AbstractHantoGame#makeMove(
	 * hanto.util.HantoPieceType, hanto.util.HantoCoordinate,
	 * hanto.util.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		MoveResult moveResult;

		if (pieceType == null && from == null && to == null)
		{
			moveResult = gameState.resign();
		}
		else {
			moveResult = super.makeMove(pieceType, from, to);
		}

		return moveResult;
	}

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

		// Give each player NUM_CRABS crabs
		for (int i = 0; i < NUM_CRABS; i++)
		{
			bluePieces.add(HantoPieceType.CRAB);
			redPieces.add(HantoPieceType.CRAB);
		}

		// Store the pieces in the game state
		gameState.getPieces().put(HantoPlayerColor.BLUE, bluePieces);
		gameState.getPieces().put(HantoPlayerColor.RED, redPieces);
	}
}
