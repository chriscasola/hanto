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

import hanto.studentccasola.common.HantoRuleset;
import hanto.studentccasola.util.GameState;

/**
 * The ruleset for the Gamma version of Hanto
 * 
 * @author Chris Casola
 * @version Feb 9, 2013
 */
public class GammaHantoRuleset extends HantoRuleset
{
	/**
	 * Construct the ruleset
	 * @param gameState the game state
	 */
	public GammaHantoRuleset(GameState gameState)
	{
		this.gameState = gameState;
	}
}
