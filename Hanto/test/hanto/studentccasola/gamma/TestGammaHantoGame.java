/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.gamma;

import hanto.common.HantoException;
import hanto.studentccasola.util.TestBasicHantoBoard;
import hanto.testutil.HexPiece;
import hanto.testutil.TestHantoGame;
import hanto.util.HantoPlayerColor;

/**
 * This is a version of the gamma hanto game for testing purposes. It allows
 * the specification of the initial board configuration.
 * 
 * !! Please note, the game will be in an undefined state, because it is impossible
 * to know what order the order that the pieces were placed.
 *
 * @author Chris Casola
 * @version Jan 24, 2013
 */
public class TestGammaHantoGame extends GammaHantoGame implements TestHantoGame
{

	/* 
	 * @see hanto.testutil.TestHantoGame#initialize(hanto.util.HantoPlayerColor, hanto.testutil.HexPiece[])
	 */
	@Override
	public void initialize(HantoPlayerColor firstPlayer,
			HexPiece[] configuration)
	{
		try {
			// Call the normal initializer in GammaHantoGame
			initialize(firstPlayer);
			
			// Replace the board with a test board, containing the provided configuration
			this.setBoard(new TestBasicHantoBoard(configuration));
		}
		catch(HantoException e)
		{
			throw new RuntimeException("Unable to initialize the Hanto game.", e);
		}
	}

}
