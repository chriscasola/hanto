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

import hanto.common.*;
import hanto.util.HantoPlayerColor;

/**
 * This interface defines the behavior of a HantoGame that is used solely for testing.
 * There is an initial initialize method that not only takes the color of the first player
 * to move, but also a board configuration (array of HexPiece objects).
 * 
 * @author gpollice
 * @version Jan 12, 2013
 */
public interface TestHantoGame extends HantoGame
{
	/**
	 * Initialize a test Hanto game.
	 * 
	 * @param firstPlayer
	 *            the (color of) the player who moves first. If this is null, then the
	 *            default player, as specified by the rule set, moves first.
	 * @param configuration
	 *            the pieces and hexes where they will be for initializing this game for
	 *            testing
	 */
	void initialize(HantoPlayerColor firstPlayer, HexPiece[] configuration);
}
