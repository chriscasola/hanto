/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.util;

import hanto.common.HantoException;

/**
 * This interface provides a framework for rules in the Hanto game.
 *
 * @author Chris Casola
 * @version Jan 24, 2013
 */
public interface HantoRule
{

	/**
	 * @param state the current state of the game
	 * @throws HantoException if this rule has been violated
	 */
	void validate(GameState state) throws HantoException;
}
