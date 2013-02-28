/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * This interface defines the behavior for a class that can pick valid
 * Hanto moves to play the game. All implementing classes must provide
 * some algorithm that produces valid Hanto moves.
 *
 * @author Chris Casola
 * @version Feb 27, 2013
 */
public abstract class HantoMoveStrategy
{
	protected final Random randGen;
	protected final AbstractHantoGame game;
	protected final HantoPlayerColor playerColor;
	
	/**
	 * Construct a move strategy
	 * @param game the HantoGame used to record the game
	 * @param playerColor the color of this player
	 */
	protected HantoMoveStrategy(AbstractHantoGame game, HantoPlayerColor playerColor)
	{
		randGen = new Random(System.currentTimeMillis());
		this.game = game;
		this.playerColor = playerColor;
	}
	
	/**
	 * Returns the next move to make
	 * @param opponentsMove the last move made by the opponent
	 * @return the next move to make
	 */
	public abstract HantoMoveRecord getNextMove(HantoMoveRecord opponentsMove);
	
	/**
	 * Sets the seed of the random number generator
	 * 
	 * @param seed the number to use as the seed
	 */
	public void setSeed(long seed)
	{
		randGen.setSeed(seed);
	}
	
	/**
	 * @return a new list containing all of the occupied cells on the game
	 * board in a random order
	 */
	protected List<HexCell> getShuffledBoardCells()
	{
		final HexCell[] cells = game.getState().getBoard().getCells().toArray(new HexCell[0]);
		final List<HexCell> cellList = new ArrayList<HexCell>();
		
		for (int i = 0; i < cells.length; i++)
		{
			if (i == 0)
			{
				cellList.add(cells[i]);
			}
			else
			{
				cellList.add(randGen.nextInt(cellList.size()), cells[i]);
			}
		}
		return cellList;
	}
	
	/**
	 * @param pieceType the piece that needs to be placed on the board
	 * @return the coordinate where the piece can be placed, or null if
	 * there is no possible location
	 */
	protected HexCoordinate getValidPlaceLocation(HantoPieceType pieceType)
	{
		HexCoordinate location = null;
		final List<HexCoordinate> validCoord = findValidPlacement(pieceType);
		
		if (validCoord.size() > 0)
		{
			location = validCoord.get(randGen.nextInt(validCoord.size()));
		}
		return location;
	}
	
	private List<HexCoordinate> findValidPlacement(HantoPieceType pieceType)
	{
		final Collection<HexCell> cells = game.getState().getBoard().getCells();
		final Set<HexCoordinate> validCells = new HashSet<HexCoordinate>();
		
		for (HexCell cell : cells)
		{
			if (cell.getPlayer() == game.getState().getTurn() || 
					game.getState().getCurrentRound() < 2)
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
		final List<HexCoordinate> retVal = new ArrayList<HexCoordinate>();
		retVal.addAll(validCells);
		return retVal;
	}
}
