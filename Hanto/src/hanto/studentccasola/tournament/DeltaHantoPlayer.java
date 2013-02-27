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

import java.util.Collection;

import hanto.common.HantoException;
import hanto.studentccasola.common.GameState;
import hanto.studentccasola.common.HexCell;
import hanto.studentccasola.common.HexCoordinate;
import hanto.studentccasola.delta.DeltaHantoGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * This class implements a player for the delta version of Hanto.
 *
 * @author Chris Casola
 * @version Feb 26, 2013
 */
public class DeltaHantoPlayer implements HantoGamePlayer
{
	private HantoPlayerColor myColor;
	private DeltaHantoGame game;
	
	public DeltaHantoPlayer(HantoPlayerColor myColor, boolean isFirst)
	{
		this.myColor = myColor;
		game = new DeltaHantoGame();
	}

	/* 
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		HantoMoveRecord result = null;
		try {
			if (opponentsMove != null) {
				// Add the opponents move to the local game
				game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
				
				// Make a move
				result = makeMove();
			}
			else {
				// Set the color of this player
				game.initialize(myColor);
				
				// Make the first move, at the origin
				result = new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
				game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
			}
		}
		catch (HantoException e) {
			result = new HantoMoveRecord(null, null, null);
		}
		
		return result;
	}

	protected HantoMoveRecord makeMove() throws HantoException
	{
		HantoMoveRecord result = null;
		
		// Place a piece if possible
		if (game.getState().getPieces().get(myColor).size() > 0)
		{
			HantoPieceType piece = game.getState().getPieces().get(myColor).get(0);
			HexCoordinate dest = findValidPlacement(piece);
			if (dest != null)
			{
				result = new HantoMoveRecord(piece, null, dest);
			}
		}
		
		// Move a piece if placement was not possible
		if (result == null)
		{
			// TODO move piece
		}
		
		// Make the move
		game.makeMove(result.getPiece(), result.getFrom(), result.getTo());
		
		return result;
	}
	
	protected HexCoordinate findValidPlacement(HantoPieceType pieceType)
	{
		Collection<HexCell> cells = game.getState().getBoard().getCells();
		for (HexCell cell : cells)
		{
			for (HexCoordinate coord : cell.getCoordinate().getAdjacentCoordinates())
			{
				try {
					game.getRuleset().checkAll(pieceType, null, coord);
					if (game.getState().getBoard().getCellAtCoordinate(coord) == null)
					{
						return coord;
					}
				} 
				catch (HantoException e) {
					// keep looking for a valid placement
					continue;
				}
			}
		}
		
		return null;
	}
	
	protected GameState getGameState()
	{
		return game.getState();
	}
}
