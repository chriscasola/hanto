/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.common;

import hanto.studentccasola.common.BasicHantoBoard;
import hanto.studentccasola.common.HexCell;
import hanto.studentccasola.common.HexCoordinate;
import hanto.testutil.HexPiece;

/**
 * This class is for testing purposes. It allows the specification of the
 * initial board configuration.
 *
 * @author Chris Casola
 * @version Jan 24, 2013
 */
public class TestBasicHantoBoard extends BasicHantoBoard
{
	public TestBasicHantoBoard(HexPiece[] configuration)
	{
		// Add each of the pieces in configuration to the board
		for (int i = 0; i < configuration.length; i++)
		{
			HexPiece curr = configuration[i];
			getCoordinateMap().put(new HexCoordinate(curr.getCoordinate()), new HexCell(curr.getCoordinate(), curr.getPlayer(), curr.getPiece()));
		}
	}
}
