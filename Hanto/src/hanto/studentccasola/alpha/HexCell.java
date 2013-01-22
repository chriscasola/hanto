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

import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * 
 * 
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class HexCell
{
	private final HantoCoordinate coordinate;
	private final HantoPlayerColor player;
	private final HantoPieceType piece;

	public HexCell(HantoCoordinate coordinate, HantoPlayerColor player, HantoPieceType piece)
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
	 * @return the piece
	 */
	public HantoPieceType getPiece()
	{
		return piece;
	}
	
	/**
	 * @return the player color
	 */
	public HantoPlayerColor getPlayer()
	{
		return player;
	}
}
