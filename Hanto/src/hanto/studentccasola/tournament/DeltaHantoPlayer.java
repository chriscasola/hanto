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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
	private final HantoPlayerColor myColor;
	private final DeltaHantoGame game;
	private HexCoordinate myButterfly;
	
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
				result = findMove();
			}
			else {
				// Set the color of this player
				game.initialize(myColor);
				
				// Make the first move, at the origin
				myButterfly = new HexCoordinate(0,0);
				result = new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
				game.makeMove(HantoPieceType.BUTTERFLY, null, new HexCoordinate(0,0));
			}
		}
		catch (HantoException e) {
			result = new HantoMoveRecord(null, null, null);
		}
		
		return result;
	}

	protected HantoMoveRecord findMove() throws HantoException
	{
		HantoMoveRecord result = null;
		
		// Place butterfly if not already placed
		if (game.getState().getPieces().get(myColor).contains(HantoPieceType.BUTTERFLY))
		{
			HantoPieceType piece = HantoPieceType.BUTTERFLY;
			HexCoordinate dest = findValidPlacement(piece);
			if (dest != null)
			{
				result = new HantoMoveRecord(piece, null, dest);
			}
		}
		
		// Place a piece if possible
		if (result == null && game.getState().getPieces().get(myColor).size() > 0)
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
			result = movePiece();
		}
		
		// Make the move
		if (result != null)
		{
			game.makeMove(result.getPiece(), result.getFrom(), result.getTo());
		}
		
		return result;
	}
	
	protected HantoMoveRecord movePiece()
	{
		Random randGen = new Random(System.currentTimeMillis());
		List<HantoMoveRecord> possibleMoves = new ArrayList<HantoMoveRecord>();
		HexCell[] cells = game.getState().getBoard().getCells().toArray(new HexCell[0]);
		
		while (possibleMoves.size() < 1)
		{
			HexCell cell = cells[randGen.nextInt(cells.length)];
			if (cell.getPlayer() == myColor && cell.getPiece() == HantoPieceType.SPARROW)
			{
				for (HexCoordinate coord : myButterfly.getAdjacentCoordinates())
				{
					if (game.getState().getBoard().getCellAtCoordinate(coord) == null)
					{
						try {
							game.getRuleset().checkAll(cell.getPiece(), cell.getCoordinate(), coord);
							possibleMoves.add(new HantoMoveRecord(cell.getPiece(), cell.getCoordinate(), coord));
						}
						catch (HantoException e) {
							// keep looking
							continue;
						}
					}
				}
			}
			else if (cell.getPlayer() == myColor && cell.getPiece() == HantoPieceType.CRAB)
			{
				for (HexCoordinate coord : cell.getCoordinate().getAdjacentCoordinates())
				{
					if (game.getState().getBoard().getCellAtCoordinate(coord) == null)
					{
						try {
							game.getRuleset().checkAll(cell.getPiece(), cell.getCoordinate(), coord);
							possibleMoves.add(new HantoMoveRecord(cell.getPiece(), cell.getCoordinate(), coord));
						}
						catch (HantoException e) {
							// keep looking
							continue;
						}
					}
				}
			}
			
			// TODO attempt to move crabs toward butterfly if no sparrows were moved
			
			// TODO attempt to move piece randomly if no crabs were moved, otherwise resign
			
		}
		
		if (possibleMoves.size() > 0)
		{
			int movePick = randGen.nextInt(possibleMoves.size());
			return possibleMoves.get(movePick);
		}
		else
		{
			return null;
		}
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
