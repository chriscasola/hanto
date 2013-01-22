/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.alpha;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * 
 * 
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class AlphaHantoGame implements HantoGame
{

	private List<HexCell> board;
	private HantoPlayerColor turn;

	public AlphaHantoGame()
	{
		board = new ArrayList<HexCell>();
		turn = HantoPlayerColor.BLUE;
	}

	/*
	 * @see hanto.common.HantoGame#initialize(hanto.util.HantoPlayerColor)
	 */
	@Override
	public void initialize(HantoPlayerColor firstPlayer) throws HantoException
	{
		if (firstPlayer == HantoPlayerColor.BLUE)
		{
			board = new ArrayList<HexCell>();
			turn = firstPlayer;
		}
		else {
			throw new HantoException("Game cannot be initialized with RED as the first player.");
		}

	}

	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.util.HantoPieceType,
	 * hanto.util.HantoCoordinate, hanto.util.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		if (pieceType == HantoPieceType.BUTTERFLY)
		{
			if (turn == HantoPlayerColor.BLUE && !(to.getX() == 0 && to.getY() == 0))
			{
				throw new HantoException("Blue must place a butterfly at (0,0)");
			}
			else if (!isAdjacent(to))
			{
				throw new HantoException("Piece must be adjacent to another piece on the board.");
			}
			board.add(new HexCell(to, turn, pieceType));
			turn = (turn == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
			return checkGameState();
		}
		else
		{
			throw new HantoException("Only butterflies may be used in this game.");
		}
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return a HantoPlayerColor indicating which player can move next
	 */
	public HantoPlayerColor getTurn() {
		return turn;
	}

	/**
	 * @return the game board
	 */
	public List<HexCell> getBoard() {
		return board;
	}
	
	/**
	 * Returns the state of the game. If the game is over, this method
	 * returns DRAW, RED_WINS, or BLUE_WINS. Otherwise OK.
	 * 
	 * @return DRAW, RED_WINS, or BLUE_WINS if the game is over, otherwise OK.
	 */
	protected MoveResult checkGameState() {
		MoveResult gameState;
		if (board.size() < 2) {
			gameState = MoveResult.OK;
		}
		else {
			gameState = MoveResult.DRAW;
		}
		return gameState;
	}

	/**
	 * Check if the given coordinate is adjacent to (0,0)
	 * 
	 * @param to the coordinate to check
	 * @return true if the given coordinate is adjacent to (0,0), otherwise false
	 */
	protected boolean isAdjacent(HantoCoordinate to)
	{
		// always return true if no pieces have been placed yet
		if (board.size() < 1) {
			return true;
		}

		// brute force check for adjacency to (0,0)
		switch (to.getX())
		{
		case -1:
			if (to.getY() == 1 || to.getY() == 0)
			{
				return true;
			}
			break;
		case 0:
			if (to.getY() == 1 || to.getY() == -1)
			{
				return true;
			}
			break;
		case 1:
			if (to.getY() == -1 || to.getY() == 0)
			{
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
}
