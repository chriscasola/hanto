/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola;

import hanto.common.HantoGame;
import hanto.studentccasola.alpha.AlphaHantoGame;
import hanto.studentccasola.gamma.GammaHantoGame;
import hanto.util.HantoGameID;

/**
 * A factory that makes Hanto games.
 * 
 * @author Chris Casola
 * @version Feb 9, 2013
 */
public class HantoGameFactory
{
	private static HantoGameFactory instance = null;
	
	private HantoGameFactory()
	{
		// make the constructor private
	}
	
	/**
	 * @return the singleton instance of the HantoGameFactory
	 */
	public static HantoGameFactory getInstance()
	{
		if (instance == null)
		{
			instance = new HantoGameFactory();
		}
		return instance;
	}
	
	/**
	 * Makes a HantoGame of the requested type
	 * 
	 * @param gameID the type of HantoGame to make
	 * @return a new HantoGame of the requested type
	 */
	public HantoGame makeHantoGame(HantoGameID gameID)
	{
		HantoGame game = null;
		
		switch(gameID) {
		case ALPHA_HANTO:
			game = new AlphaHantoGame();
			break;
		case GAMMA_HANTO:
			game = new GammaHantoGame();
			break;
		default:
			break;
		}
		
		return game;
	}
}
