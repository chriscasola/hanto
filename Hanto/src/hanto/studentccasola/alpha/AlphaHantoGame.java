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

	private final List<HexCell> board;
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
		// TODO Auto-generated method stub

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

			board.add(new HexCell(to, turn, pieceType));
			turn = (turn == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
			return MoveResult.OK;
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
}
