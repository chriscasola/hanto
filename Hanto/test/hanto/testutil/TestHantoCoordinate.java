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

import hanto.util.HantoCoordinate;

/**
 * A simple coordinate class used for testing.
 * @author gpollice
 * @version Jan 19, 2013
 */
public class TestHantoCoordinate implements HantoCoordinate
{
	private final int xCoordinate, yCoordinate;
	
	public TestHantoCoordinate(int x, int y)
	{
		xCoordinate = x;
		yCoordinate = y;
	}
	@Override
	public int getX()
	{
		return xCoordinate;
	}

	@Override
	public int getY()
	{
		return yCoordinate;
	}
}
