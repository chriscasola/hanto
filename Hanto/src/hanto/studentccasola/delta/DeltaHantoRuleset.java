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
import hanto.studentccasola.common.HantoRuleset;
import hanto.studentccasola.util.GameState;
import hanto.studentccasola.util.HexCell;
import hanto.studentccasola.util.HexCoordinate;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

public class DeltaHantoRuleset extends HantoRuleset
{
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
	
	public void pieceMustBePlacedNextToLikeColorOnly(HexCoordinate from, HexCoordinate to) throws HantoException
	{
		// rule does not apply for first round and only when placing (not moving)
		if (gameState.getCurrentRound() > 1 && from == null)
		{
			HantoPlayerColor color = gameState.getTurn();
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
