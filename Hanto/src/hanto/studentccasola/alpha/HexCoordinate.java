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

import java.util.HashSet;
import java.util.Set;

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
	
	public Set<HexCoordinate> getAdjacentCoordinates()
	{
		HashSet<HexCoordinate> neighbors = new HashSet<HexCoordinate>();
		neighbors.add(new HexCoordinate(xPos, yPos - 1));
		neighbors.add(new HexCoordinate(xPos, yPos + 1));
		neighbors.add(new HexCoordinate(xPos - 1, yPos));
		neighbors.add(new HexCoordinate(xPos - 1, yPos + 1));
		neighbors.add(new HexCoordinate(xPos + 1, yPos));
		neighbors.add(new HexCoordinate(xPos + 1, yPos - 1));
		
		return neighbors;
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
	
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other)
	{
		boolean isEqual = false;
		if (this == other) {
			isEqual = true;
		}
		else if (other instanceof HexCoordinate) {
			final HexCoordinate otherCoord = (HexCoordinate) other;
			if (xPos == otherCoord.getX() && yPos == otherCoord.getY()) {
				isEqual = true;
			}
		}
		return isEqual;
	}
	
	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += Integer.valueOf(xPos).hashCode();
		hash += Integer.valueOf(yPos).hashCode();
		return hash;
	}
}
