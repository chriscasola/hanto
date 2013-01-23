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

/**
 * Represents a coordinate on a hexagonal board
 *
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class HexCoordinate implements HantoCoordinate
{
	
	/** The x position */
	private final int xPos;
	
	
	/** The y position */
	private final int yPos;
	
	/**
	 * Constructs a new HexCoordinate with the given coordinates
	 * @param xPos the x coordinate
	 * @param yPos the y coordinate
	 */
	public HexCoordinate(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/* 
	 * @see hanto.util.HantoCoordinate#getX()
	 */
	@Override
	public int getX()
	{
		return xPos;
	}

	/* 
	 * @see hanto.util.HantoCoordinate#getY()
	 */
	@Override
	public int getY()
	{
		return yPos;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(" + xPos + "," + yPos + ")";
	}
}
