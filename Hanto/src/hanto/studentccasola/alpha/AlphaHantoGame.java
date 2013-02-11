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
import hanto.studentccasola.common.HexCell;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * This is the alpha version of Hanto as specified in the Hanto Developer's
 * Guide. There is one type of piece: butterfly. The piece cannot move. There
 * is only one round: blue places the butterfly at (0,0), then red places the
 * butterfly adjacent to the blue butterfly. The game ends in a draw.
 * 
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class AlphaHantoGame implements HantoGame
{

	private List<HexCell> board;
	private HantoPlayerColor turn;

	/**
	 * Constructs a new AlphaHantoGame with
	 * Blue as the first player.
	 */
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
	}

	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.util.HantoPieceType,
	 * hanto.util.HantoCoordinate, hanto.util.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		// only butterfly pieces are valid
		if (pieceType != HantoPieceType.BUTTERFLY)
		{
			throw new HantoException("Only butterflies may be used in this game.");
		}
		// make sure if this is blue's turn, they place their piece at (0,0)
		if (turn == HantoPlayerColor.BLUE && !(to.getX() == 0 && to.getY() == 0))
		{
			throw new HantoException("Blue must place a butterfly at (0,0)");
		}
		// make sure this piece will be adjacent to another piece on the board
		else if (!isAdjacent(to))
		{
			throw new HantoException("Piece must be adjacent to another piece on the board.");
		}
		
		// add the piece to the board
		board.add(new HexCell(to, turn, pieceType));
		
		// flip the turn status to the opposite player
		turn = (turn == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		return checkGameState(); // return the state of the game
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		return board.toString();
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
