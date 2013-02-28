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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hanto.common.HantoException;
import hanto.common.HantoGame;
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
	private HexCoordinate theirButterfly;
	private Random randGen;

	public DeltaHantoPlayer(HantoPlayerColor myColor, boolean isFirst)
	{
		this.myColor = myColor;
		game = new DeltaHantoGame();
		long seed = System.currentTimeMillis();
		randGen = new Random(seed);
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
				// Keep track of the opponent's butterfly
				if (opponentsMove.getPiece() == HantoPieceType.BUTTERFLY)
				{
					theirButterfly = new HexCoordinate(opponentsMove.getTo());
				}
				
				// Add the opponents move to the local game
				game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());

				// Make a move
				result = findMove();
			}
			else {
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
		if (myButterfly == null)
		{
			HantoPieceType piece = HantoPieceType.BUTTERFLY;
			HexCoordinate dest = findValidPlacementHelper(piece);
			if (dest != null)
			{
				result = new HantoMoveRecord(piece, null, dest);
				myButterfly = dest;
			}
		}

		// Place a piece if possible
		if (result == null && game.getState().getPieces().get(myColor).size() > 0)
		{
			HantoPieceType piece = game.getState().getPieces().get(myColor).get(0);
			HexCoordinate dest = findValidPlacementHelper(piece);
			if (dest != null)
			{
				result = new HantoMoveRecord(piece, null, dest);
			}
		}

		// Make the move
		if (result != null)
		{
			game.makeMove(result.getPiece(), result.getFrom(), result.getTo());
		}

		// Move a piece if placement was not possible
		if (result == null)
		{
			result = movePiece();
			if (result != null)
			{
				game.makeMove(result.getPiece(), result.getFrom(), result.getTo());
			}
		}

		return result;
	}

	protected HexCoordinate findValidPlacementHelper(HantoPieceType pieceType)
	{
		List<HexCoordinate> validCoord = findValidPlacement(pieceType);
		if (validCoord.size() > 0)
		{
			return validCoord.get(randGen.nextInt(validCoord.size()));
		}
		return null;
	}

	protected HantoMoveRecord movePiece()
	{
		List<HantoMoveRecord> possibleMoves = new ArrayList<HantoMoveRecord>();
		HexCell[] cells = game.getState().getBoard().getCells().toArray(new HexCell[0]);
		List<HexCell> cellList = new ArrayList<HexCell>();
		for (int i = 0; i < cells.length; i++)
		{
			if (i == 0)
				cellList.add(cells[i]);
			else
				cellList.add(randGen.nextInt(cellList.size()), cells[i]);
		}

		for (HexCell cell : cellList)
		{
			if (cell.getPlayer() == myColor && cell.getPiece() == HantoPieceType.SPARROW)
			{
				HexCoordinate butterfly = (theirButterfly != null) ? theirButterfly : myButterfly;
				for (HexCoordinate coord : butterfly.getAdjacentCoordinates())
				{
					if (game.getState().getBoard().getCellAtCoordinate(coord) == null)
					{
						if (game.getRuleset().isValidMove(cell.getPiece(), cell.getCoordinate(), coord))
						{
							possibleMoves.add(new HantoMoveRecord(cell.getPiece(), cell.getCoordinate(), coord));
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
						if (game.getRuleset().isValidMove(cell.getPiece(), cell.getCoordinate(), coord))
						{
							possibleMoves.add(new HantoMoveRecord(cell.getPiece(), cell.getCoordinate(), coord));
						}
					}
				}
			}
		}

		if (possibleMoves.size() > 0)
		{
			HantoMoveRecord record = null;
			int movePick = randGen.nextInt(possibleMoves.size());
			record = possibleMoves.get(movePick);
			return record;
		}
		else
		{
			return null;
		}
	}

	protected List<HexCoordinate> findValidPlacement(HantoPieceType pieceType)
	{
		Collection<HexCell> cells = game.getState().getBoard().getCells();
		Set<HexCoordinate> validCells = new HashSet<HexCoordinate>();
		for (HexCell cell : cells)
		{
			if (cell.getPlayer() == game.getState().getTurn() || game.getState().getCurrentRound() < 2)
			{
				for (HexCoordinate coord : cell.getCoordinate().getAdjacentCoordinates())
				{
					if (game.getRuleset().isValidMove(pieceType, null, coord))
					{
						validCells.add(coord);
					}
				}
			}
		}
		List<HexCoordinate> retVal = new ArrayList<HexCoordinate>();
		retVal.addAll(validCells);
		return retVal;
	}

	protected HantoGame getGame()
	{
		return game;
	}

	protected GameState getGameState()
	{
		return game.getState();
	}
	
	protected void randSeed(long seed)
	{
		randGen.setSeed(seed);
		System.out.println("Seed: " + String.valueOf(seed));
	}
	
}
