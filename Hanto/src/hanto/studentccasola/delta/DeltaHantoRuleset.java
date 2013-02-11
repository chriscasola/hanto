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

import hanto.common.HantoException;
import hanto.studentccasola.common.GameState;
import hanto.studentccasola.common.HantoRuleset;
import hanto.studentccasola.common.HexCell;
import hanto.studentccasola.common.HexCoordinate;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * Ruleset for the Delta version of Hanto
 *  
 * @author Chris Casola
 * @version Feb 10, 2013 
 */
public class DeltaHantoRuleset extends HantoRuleset
{
	/**
	 * Construct the ruleset
	 * 
	 * @param gameState the state of the game
	 */
	public DeltaHantoRuleset(GameState gameState)
	{
		super(gameState);
		getWalkingPieces().add(HantoPieceType.BUTTERFLY);
		getWalkingPieces().add(HantoPieceType.CRAB);
		getFlyingPieces().add(HantoPieceType.SPARROW);
	}
	
	/*
	 * @see hanto.studentccasola.common.HantoRuleset#checkAll(
	 * hanto.util.HantoPieceType, hanto.util.HantoCoordinate, 
	 * hanto.util.HantoCoordinate)
	 */
	@Override
	public void checkAll(HantoPieceType pieceType, HantoCoordinate from, 
			HantoCoordinate to) throws HantoException
	{
		// Check all rules in HantoRuleset
		super.checkAll(pieceType, from, to);
		
		// Convert coordinates to HexCoordinate
		HexCoordinate hexFrom = null;
		HexCoordinate hexTo = null;
		if (from != null)
		{
			hexFrom = new HexCoordinate(from);
		}
		if (to != null)
		{
			hexTo = new HexCoordinate(to);
		}
		
		// Check all rules in DeltaHantoRuleset
		pieceMustBePlacedNextToLikeColorOnly(hexFrom, hexTo);
	}
	
	/**
	 * Make sure pieces are placed next to a like color and not
	 * next to an opposite color
	 * 
	 * @param from the source location of the piece
	 * @param to the destination location of the piece
	 * @throws HantoException if the rule is violated
	 */
	protected void pieceMustBePlacedNextToLikeColorOnly(HexCoordinate from, HexCoordinate to) 
			throws HantoException
	{
		// rule does not apply for first round and only when placing (not moving)
		if (gameState.getCurrentRound() > 1 && from == null)
		{
			final HantoPlayerColor color = gameState.getTurn();
			int likeNeighbors = 0;
			int unlikeNeighbors = 0;
			
			for (HexCoordinate adjCell : to.getAdjacentCoordinates())
			{
				HexCell neighbor = gameState.getBoard().getCellAtCoordinate(adjCell);
				if (neighbor != null && neighbor.getPlayer() == color)
				{
					likeNeighbors++;
				}
				else if (neighbor != null && neighbor.getPlayer() != color)
				{
					unlikeNeighbors++;
				}
			}
			
			if (likeNeighbors == 0 || unlikeNeighbors > 0)
			{
				throw new HantoException("Pieces must be placed adjacent to a piece of like color");
			}
		}
	}
}
