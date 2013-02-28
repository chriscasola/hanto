/**
 * 
 */
package hanto.studentccasola.tournament;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentccasola.HantoGameFactory;
import hanto.studentccasola.common.HantoBoard;
import hanto.studentccasola.common.HexCell;
import hanto.studentccasola.common.HexCoordinate;
import hanto.testutil.TestHantoCoordinate;
import hanto.tournament.HantoGameDirector;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for the delta hanto player
 * 
 * @author Chris
 */
public class DeltaHantoPlayerTest
{
	private DeltaHantoPlayer player;
	private DeltaHantoPlayer player2;
	private HantoGame game;

	@Before
	public void setup()
	{
		player = new DeltaHantoPlayer(HantoPlayerColor.BLUE, true);
		player2 = new DeltaHantoPlayer(HantoPlayerColor.RED, false);
		game = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
	}
	
	@Test
	public void playerWorksWithDirector()
	{
		System.out.println("\n\n------Starting Director test----------\n\n");
		player.randSeed(1362005752870l);
		player2.randSeed(1362005752870l);
		
		HantoGameDirector director = HantoGameDirector.getInstance();
		if (director == null)
		{
			fail("Director should not be null");
		}
		
		director.initialize(player, player2);
		director.setLogLevel(HantoGameDirector.HantoLogLevel.FULL);
		director.setMoveLimit(150);
		final MoveResult result = director.playGame();
		System.out.println("Result: " + result.toString());
		if (director.getErrorCause() != null)
		{
			System.out.println(">>" + director.getErrorCause().getMessage());
		}
		System.out.println("\n\n------Ending Director test----------\n\n");
	}
	
	@Test
	public void continueMakingMovesAfterPiecesArePlaced() throws HantoException
	{
		HantoMoveRecord lastMove;
		
		lastMove = player.makeMove(null);
		game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());
		lastMove = player2.makeMove(lastMove);
		game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());
		System.out.println("\n\n------Starting test----------\n\n");
		for (int i = 0; i < 150; i++)
		{
			lastMove = player.makeMove(lastMove);
			MoveResult result = game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());
			if (result != MoveResult.OK)
			{
				break;
			}
			lastMove = player2.makeMove(lastMove);
			result = game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());
			if (result != MoveResult.OK)
			{
				break;
			}
		}
		System.out.println("\n\n---------BOARD---------\n\n" + game.getPrintableBoard() + "\n\n---------END---------\n\n");
	}

	@Test
	public void makeValidMovesUntilPiecesAreAllPlaced() throws HantoException
	{
		HantoMoveRecord lastMove;

		// player makes first move
		lastMove = player.makeMove(null);
		game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());

		// play round
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		lastMove = player.makeMove(new HantoMoveRecord(
				HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1)));
		assertFalse(lastMove.getPiece() == null && lastMove.getFrom() == null && lastMove.getTo() == null);
		game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());

		for (int i = 2; i < 6; i++)
		{
			// play round
			game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,i));
			lastMove = player.makeMove(new HantoMoveRecord(
					HantoPieceType.SPARROW, null, new TestHantoCoordinate(0,i)));
			assertFalse(lastMove.getPiece() == null && lastMove.getFrom() == null && lastMove.getTo() == null);
			game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());
		}
		
		for (int i = 6; i < 9; i++)
		{
			// play round
			game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0,i));
			lastMove = player.makeMove(new HantoMoveRecord(
					HantoPieceType.CRAB, null, new TestHantoCoordinate(0,i)));
			assertFalse(lastMove.getPiece() == null && lastMove.getFrom() == null && lastMove.getTo() == null);
			game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());
		}
	}
	
	@Test
	public void playerCanGoSecond() throws HantoException
	{
		HantoMoveRecord lastMove;
		
		lastMove = player.makeMove(null);
		game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());
		lastMove = player2.makeMove(lastMove);
		assertNotNull(lastMove);
		game.makeMove(lastMove.getPiece(), lastMove.getFrom(), lastMove.getTo());
	}

	@Test
	public void makeValidSecondMove() throws HantoException
	{
		HantoMoveRecord move;
		// player makes first move
		move = player.makeMove(null);
		game.makeMove(move.getPiece(), move.getFrom(), move.getTo());

		// opponent makes a valid move
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1));
		move = player.makeMove(new HantoMoveRecord(
				HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0,1)));

		// make sure the player didn't resign
		assertFalse(move.getPiece() == null && move.getFrom() == null && move.getTo() == null);

		// check to make sure second move by the player is valid
		game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
	}
	
	@Test
	public void canFindValidPlacement() throws HantoException
	{
		player.makeMove(null); // blue butterfly at 0,0
		player.getGame().makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1,-1)); // red butterfly at 1,-1
		player.getGame().makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1,1)); // blue sparrow at -1,1
		player.getGame().makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1,-2)); // red sparrow at 1,-2
		
		
		final Collection<HexCoordinate> validPlacements = new ArrayList<HexCoordinate>();
		validPlacements.add(new HexCoordinate(-1,2));
		validPlacements.add(new HexCoordinate(-2,2));
		validPlacements.add(new HexCoordinate(-2,1));
		validPlacements.add(new HexCoordinate(-1,0));
		validPlacements.add(new HexCoordinate(0,1));
		
		assertEquals(5, player.findValidPlacement(HantoPieceType.SPARROW).size());
		assertTrue(player.findValidPlacement(HantoPieceType.SPARROW).containsAll(validPlacements));
	}

	@Test
	public void thisPlayerMovesFirstShouldPlacePieceAtOrigin() throws HantoException
	{
		final HantoMoveRecord result = player.makeMove(null);
		game.makeMove(result.getPiece(), result.getFrom(), result.getTo());
	}

}
