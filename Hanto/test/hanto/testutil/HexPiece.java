/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.testutil;

import hanto.util.*;

/**
 * This class is used to specifiy a piece and a hex on the board where the piece will be
 * placed. This class is only used for testing purposes.
 * 
 * @author gpollice
 * @version Jan 12, 2013
 */
public class HexPiece
{
	private final HantoCoordinate coordinate;
	private final HantoPlayerColor player;
	private final HantoPieceType piece;

	/**
	 * Constructor.
	 * 
	 * @param coordinate
	 *            the hex where the piece will be placed
	 * @param player
	 *            the color of the player / piece
	 * @param piece
	 *            the piece type
	 */
	public HexPiece(HantoCoordinate coordinate, HantoPlayerColor player,
			HantoPieceType piece)
	{
		this.coordinate = coordinate;
		this.player = player;
		this.piece = piece;
	}

	/**
	 * @return the coordinate
	 */
	public HantoCoordinate getCoordinate()
	{
		return coordinate;
	}

	/**
	 * @return the player
	 */
	public HantoPlayerColor getPlayer()
	{
		return player;
	}

	/**
	 * @return the piece
	 */
	public HantoPieceType getPiece()
	{
		return piece;
	}
}
