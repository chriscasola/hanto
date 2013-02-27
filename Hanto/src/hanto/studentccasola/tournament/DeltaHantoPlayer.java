/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.tournament;

import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoPlayerColor;

/**
 * This class implements a player for the delta version of Hanto.
 *
 * @author Chris Casola
 * @version Feb 26, 2013
 */
public class DeltaHantoPlayer implements HantoGamePlayer
{
	
	private HantoPlayerColor myColor;
	private boolean isFirst;
	
	public DeltaHantoPlayer(HantoPlayerColor myColor, boolean isFirst)
	{
		this.myColor = myColor;
		this.isFirst = isFirst;
	}

	/* 
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
