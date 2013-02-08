/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.grading.gamma;

import static hanto.util.HantoPieceType.*;
import static hanto.util.MoveResult.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentccasola.alpha.AlphaHantoGame;
import hanto.studentccasola.gamma.GammaHantoGame;
import hanto.testutil.TestHantoCoordinate;
import hanto.util.HantoPlayerColor;
import org.junit.*;

/**
 * Test cases for the first project deliverable (homework assignment 2)
 * @author gpollice
 * @version Jan 27, 2013
 */
public class GammaHantoTest
{
	private final TestHantoCoordinate
		tc00 = new TestHantoCoordinate(0, 0),
		tc01 = new TestHantoCoordinate(0, 1),
		tc10 = new TestHantoCoordinate(1, 0),
		tc1_1 = new TestHantoCoordinate(1, -1),
		tc_1_1 = new TestHantoCoordinate(-1, -1),
		tc0_1 = new TestHantoCoordinate(0, -1),
		tc_10 = new TestHantoCoordinate(-1, 0),
		tc_11 = new TestHantoCoordinate(-1, 1),
		tc_12 = new TestHantoCoordinate(-1, 2),
		tc02 = new TestHantoCoordinate(0, 2),
		tc0_2 = new TestHantoCoordinate(0, -2),
		tc1_2 = new TestHantoCoordinate(1, -2),
		tc11 = new TestHantoCoordinate(1, 1);
	
	private HantoGame alphaGame;
	private HantoGame gammaGame;
	
	@Before
	public void setup() throws HantoException
	{
		alphaGame = new AlphaHantoGame();
		gammaGame = new GammaHantoGame();
	}
	
	/*
	 * Alpha Hanto tests
	 */
	@Test
	public void createANewAlphaHantoGame()
	{
		assertNotNull(new AlphaHantoGame());
	}

	@Test
	public void placeBlueButterflyAsFirstMove() throws HantoException
	{
		assertThat(alphaGame.makeMove(BUTTERFLY, null, tc00),
				is(OK));
	}
	
	@Test(expected=HantoException.class)
	public void placeButterflyOnWrongHexAsFirstMove() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc01);
	}
	
	@Test(expected=HantoException.class)
	public void nonButterflyPieceCausesExecption() throws HantoException
	{
		alphaGame.makeMove(CRAB, null, tc00);
	}
	
	@Test
	public void placeRedButterflyAdjacentToBlueButterflyForDraw() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		assertThat(alphaGame.makeMove(BUTTERFLY, null, tc10),
				is(DRAW));
	}
	
	@Test(expected=HantoException.class)
	public void placeRedButterflyOnBlueButterfly() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		alphaGame.makeMove(BUTTERFLY, null, tc00);
	}
	
	@Test(expected=HantoException.class)
	public void placeRedButterflyNotAdjacentToBlueButterfly() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		alphaGame.makeMove(BUTTERFLY, null, new TestHantoCoordinate(2, 3));
	}
	
	@Test(expected=HantoException.class)
	public void testAdjacencyCalulationDefect() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		alphaGame.makeMove(BUTTERFLY, null, tc11);
	}
	
	@Test
	public void initializeHasNoEffect() throws HantoException
	{
		alphaGame.initialize(HantoPlayerColor.RED);
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		assertThat(alphaGame.makeMove(BUTTERFLY, null, tc10),
				is(DRAW));
	}
	
	@Test
	public void printableBoardIsNotNullAtGameStart()
	{
		assertNotNull(alphaGame.getPrintableBoard());
	}
	
	@Test
	public void printableBoardWithPiecesIsMeangingful() throws HantoException
	{
		alphaGame.makeMove(BUTTERFLY, null, tc00);
		assertFalse(alphaGame.getPrintableBoard().length() == 0);
		System.out.println(alphaGame.getPrintableBoard());
	}
	
	/*
	 * Gamma Hanto tests
	 */
	@Test
	public void blueButterflyMovesFirst() throws HantoException
	{
		assertThat(gammaGame.makeMove(BUTTERFLY, null, tc00), is(OK));
	}

	@Test(expected = HantoException.class)
	public void placeFirstPiecesInWrongCoordinate() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc11);
	}

	@Test(expected = HantoException.class)
	public void placePieceThatIsNotInTheGame() throws HantoException
	{
		gammaGame.makeMove(DOVE, null, tc00);
	}

	@Test(expected = HantoException.class)
	public void placeSecondPieceNotAdjacentToFirstPiece() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(3, -2));
	}

	@Test
	public void placeValidThirdPiece() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(SPARROW, null, tc01);
		gammaGame.makeMove(SPARROW, null, tc02);
	}

	@Test(expected = HantoException.class)
	public void placeThirdPieceNotAdjacentToAnyOtherPiece() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(SPARROW, null, tc01);
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(3, -2));
	}

	@Test
	public void blueWinsWithBlueMove() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(BLUE_WINS)); // Blue
	}

	@Test
	public void blueWinsWithRedMove() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc0_1); // Blue
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(BLUE_WINS));
	}

	@Test
	public void redWins() throws HantoException
	{
		gammaGame.initialize(HantoPlayerColor.RED);
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Red
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Blue
		gammaGame.makeMove(SPARROW, null, tc10); // Red
		gammaGame.makeMove(SPARROW, null, tc11); // Blue
		gammaGame.makeMove(SPARROW, null, tc02); // Red
		gammaGame.makeMove(SPARROW, null, tc_12); // Blue
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(RED_WINS)); // Red
	}

	@Test
	public void redWinsOnLastMove() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(-1, 3)); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc1_1); // Red
		gammaGame.makeMove(SPARROW, null, tc0_1); // Blue
		gammaGame.makeMove(SPARROW, null, tc0_2); // Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 3)); // Blue
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(RED_WINS)); // RED
	}

	@Test(expected = HantoException.class)
	public void attemptToPlaceTwoBlueButterflies() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(BUTTERFLY, null, tc01);
		gammaGame.makeMove(BUTTERFLY, null, tc10);
	}

	@Test(expected = HantoException.class)
	public void attemptToPlaceTwoRedButterflies() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00);
		gammaGame.makeMove(BUTTERFLY, null, tc01);
		gammaGame.makeMove(SPARROW, null, tc11);
		gammaGame.makeMove(BUTTERFLY, null, tc10);
	}

	@Test
	public void drawBySurroundingBothButterflies() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc1_1); // Red
		gammaGame.makeMove(SPARROW, null, tc0_1); // Blue
		assertThat(gammaGame.makeMove(SPARROW, null, tc_11), is(DRAW)); // Red
	}

	@Test
	public void drawByExhaustingMoves() throws HantoException
	{
		final TestHantoCoordinate tc1 = tc01, tc2 = tc10, tc3 = tc0_1, tc4 = tc_10;

		gammaGame.makeMove(SPARROW, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, null, tc3); // Blue
		gammaGame.makeMove(BUTTERFLY, tc1, tc2); // Red
		gammaGame.makeMove(BUTTERFLY, tc3, tc4); // Blue
		gammaGame.makeMove(BUTTERFLY, tc2, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, tc4, tc3); // Blue
		gammaGame.makeMove(BUTTERFLY, tc1, tc2); // Red
		gammaGame.makeMove(BUTTERFLY, tc3, tc4); // Blue
		gammaGame.makeMove(BUTTERFLY, tc2, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, tc4, tc3); // Blue
		gammaGame.makeMove(BUTTERFLY, tc1, tc2); // Red
		gammaGame.makeMove(BUTTERFLY, tc3, tc4); // Blue
		gammaGame.makeMove(BUTTERFLY, tc2, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, tc4, tc3); // Blue
		gammaGame.makeMove(BUTTERFLY, tc1, tc2); // Red
		gammaGame.makeMove(BUTTERFLY, tc3, tc4); // Blue
		gammaGame.makeMove(BUTTERFLY, tc2, tc1); // Red
		gammaGame.makeMove(BUTTERFLY, tc4, tc3); // Blue
		assertThat(gammaGame.makeMove(BUTTERFLY, tc1, tc2), is(DRAW)); // Red
	}

	@Test(expected = HantoException.class)
	public void blueDoesNotPlaceButterflyWithinFourMoves() throws HantoException
	{
		gammaGame.makeMove(SPARROW, null, tc00); // Blue
		gammaGame.makeMove(SPARROW, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
	}

	@Test(expected = HantoException.class)
	public void redDoesNotPlaceButterflyWithinFourMoves() throws HantoException
	{
		gammaGame.makeMove(SPARROW, null, tc00); // Blue
		gammaGame.makeMove(SPARROW, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(BUTTERFLY, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0)); // Red
	}

	@Test(expected = HantoException.class)
	public void placePieceOnTopOfAnother() throws HantoException
	{
		gammaGame.makeMove(SPARROW, null, tc00); // Blue
		gammaGame.makeMove(SPARROW, null, tc00); // Red
	}

	@Test(expected = HantoException.class)
	public void attemptMoveAfterGameIsOver() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc_11); // Blue wins
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(2, 0));
	}

	@Test
	public void printableBoardTest() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, null, tc02); // Blue
		gammaGame.makeMove(SPARROW, null, tc11); // Red
		gammaGame.makeMove(SPARROW, null, tc10); // Blue
		gammaGame.makeMove(SPARROW, null, tc1_1); // Red
		gammaGame.makeMove(SPARROW, null, tc0_1); // Blue
		gammaGame.makeMove(SPARROW, null, tc_11); // Red
		String pb = gammaGame.getPrintableBoard();
		assertNotNull(pb);
		assertTrue(pb.length() > 0);
		System.out.println(pb);
	}

	@Test(expected = HantoException.class)
	public void moveFromUnoccupiedHex() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(BUTTERFLY, tc10, tc11); // Blue
	}

	@Test(expected = HantoException.class)
	public void moveFromSparrowHex() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(BUTTERFLY, tc_10, tc11); // Blue
	}

	@Test(expected = HantoException.class)
	public void moveFromWrongButterflyHex() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(BUTTERFLY, tc01, tc11); // Blue
	}

	@Test
	public void winByMovingButterfly() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(SPARROW, null, tc10); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc11); 	// Red
		gammaGame.makeMove(BUTTERFLY, tc00, tc1_1);	// Blue
		gammaGame.makeMove(SPARROW, null, tc02); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_12); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc_11);	// Red
		assertThat(gammaGame.makeMove(BUTTERFLY, tc1_1, tc00), is(BLUE_WINS));	// Blue
	}
	
	@Test(expected = HantoException.class)
	public void attemptToMoveSparrow() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); // Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); // Red
		gammaGame.makeMove(SPARROW, null, tc_10); // Blue
		gammaGame.makeMove(SPARROW, null, tc_12); // Red
		gammaGame.makeMove(SPARROW, tc_10, tc0_1); // Blue
	}
	
	@Test(expected = HantoException.class)
	public void blueAttemptsTooPlaceToManySparrows() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_10); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc_11); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_1_1); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc0_2); 	// Red
		gammaGame.makeMove(SPARROW, null, tc02); 	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 3)); 	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 4));	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 5));	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 6));	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 7));	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 8));	// Blue
	}
	
	@Test(expected = HantoException.class)
	public void redAttemptsTooPlaceToManySparrows() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_10); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc_11); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_1_1); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc0_2); 	// Red
		gammaGame.makeMove(SPARROW, null, tc02); 	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 3)); 	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 4));	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 5));	// Red
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 6));	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 7));	// Red
		gammaGame.makeMove(BUTTERFLY, tc00, tc1_2);	// Blue
		gammaGame.makeMove(SPARROW, null, new TestHantoCoordinate(0, 8));	// Red
	}
	
	@Test(expected = HantoException.class)
	public void attemptToMoveButterflyTwoHexes() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(BUTTERFLY, tc00, tc11);	// Blue
	}
	
	@Test(expected = HantoException.class)
	public void moveDividesTheConfiguration() throws HantoException
	{
		gammaGame.makeMove(BUTTERFLY, null, tc00); 	// Blue
		gammaGame.makeMove(BUTTERFLY, null, tc01); 	// Red
		gammaGame.makeMove(SPARROW, null, tc_10); 	// Blue
		gammaGame.makeMove(SPARROW, null, tc02); 	// Red
		gammaGame.makeMove(BUTTERFLY, tc00, tc0_1);	// Blue
	}
}