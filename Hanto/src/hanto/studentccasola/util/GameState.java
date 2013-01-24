/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.util;

import hanto.studentccasola.common.HantoBoard;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

import java.util.List;
import java.util.Map;

/**
 * This class holds information about the current state of a
 * Hanto game, for the purposes of verifying that rules are met.
 *
 * @author Chris Casola
 * @version Jan 24, 2013
 */
public class GameState
{
	private final HantoBoard board;
	private final HantoCoordinate from;
	private final HantoCoordinate to;
	private final HantoPieceType pieceType;
	private final HantoPlayerColor turn;
	private final int currentRound;
	private final Map<HantoPlayerColor,List<HantoPieceType>> pieces;
	
	/**
	 * @param board the board
	 * @param from the coordinate where the piece is being moved from
	 * @param to the coordinate where the piece is being moved to
	 * @param pieceType the type of piece
	 * @param turn the color of the player whose turn it is
	 * @param currentRound the number of the current round
	 * @param pieces a map containing the pieces yet to be placed
	 */
	public GameState(HantoBoard board, HantoCoordinate from,
			HantoCoordinate to, HantoPieceType pieceType,
			HantoPlayerColor turn, int currentRound,
			Map<HantoPlayerColor, List<HantoPieceType>> pieces)
	{
		this.board = board;
		this.from = from;
		this.to = to;
		this.pieceType = pieceType;
		this.turn = turn;
		this.currentRound = currentRound;
		this.pieces = pieces;
	}

	/**
	 * @return the board
	 */
	public HantoBoard getBoard()
	{
		return board;
	}

	/**
	 * @return the from
	 */
	public HantoCoordinate getFrom()
	{
		return from;
	}

	/**
	 * @return the to
	 */
	public HantoCoordinate getTo()
	{
		return to;
	}

	/**
	 * @return the pieceType
	 */
	public HantoPieceType getPieceType()
	{
		return pieceType;
	}

	/**
	 * @return the turn
	 */
	public HantoPlayerColor getTurn()
	{
		return turn;
	}

	/**
	 * @return the currentRound
	 */
	public int getCurrentRound()
	{
		return currentRound;
	}

	/**
	 * @return the pieces
	 */
	public Map<HantoPlayerColor, List<HantoPieceType>> getPieces()
	{
		return pieces;
	}
	
	
}
