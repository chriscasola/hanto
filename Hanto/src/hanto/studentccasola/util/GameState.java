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
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

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
	public static final int MAX_NUM_ROUNDS = 10;
	private final HantoBoard board;
	private HantoPlayerColor turn;
	private int currentRound;
	private final Map<HantoPlayerColor,List<HantoPieceType>> pieces;
	private MoveResult gameStatus;
	private HantoPlayerColor firstPlayer;
	
	/**
	 * Construct the game state
	 * 
	 * @param board the board
	 * @param turn the color of the player whose turn it is
	 * @param currentRound the number of the current round
	 * @param pieces a map containing the pieces yet to be placed
	 * @param gameStatus
	 * @param firstPlayer
	 */
	public GameState(HantoBoard board,
			HantoPlayerColor turn, int currentRound,
			Map<HantoPlayerColor, List<HantoPieceType>> pieces,
			MoveResult gameStatus, HantoPlayerColor firstPlayer)
	{
		this.board = board;
		this.turn = turn;
		this.currentRound = currentRound;
		this.pieces = pieces;
		this.gameStatus = gameStatus;
		this.firstPlayer = firstPlayer;
	}
	
	/**
	 * Update the game state to indicate that the turn has changed
	 * 
	 * @return the status of the game (e.g. RED_WIN, BLUE_WIN, DRAW)
	 */
	public MoveResult nextTurn()
	{
		turn = (turn == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		currentRound += (turn == firstPlayer) ? 1 : 0;
		gameStatus = board.getBoardState();
		
		// End the game if round MAX_NUM_ROUNDS is complete
		if (currentRound > MAX_NUM_ROUNDS)
		{
			gameStatus = (gameStatus != MoveResult.OK) ? gameStatus : MoveResult.DRAW; 
		}
		
		return gameStatus;
	}
	
	public void setTurn(HantoPlayerColor turn)
	{
		this.turn = turn;
	}
	
	public void setFirstPlayer(HantoPlayerColor firstPlayer)
	{
		this.firstPlayer = firstPlayer;
	}

	/**
	 * @return the board
	 */
	public HantoBoard getBoard()
	{
		return board;
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
	
	public MoveResult getStatus() {
		return gameStatus;
	}
}
