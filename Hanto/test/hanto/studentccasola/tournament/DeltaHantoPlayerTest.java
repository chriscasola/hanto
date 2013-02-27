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
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoGameID;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the delta hanto player
 * 
 * @author Chris
 */
public class DeltaHantoPlayerTest
{
	private DeltaHantoPlayer player;
	private HantoGame game;

	@Before
	public void setup()
	{
		player = new DeltaHantoPlayer(HantoPlayerColor.BLUE, true);
		game = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
	}
	
	@Test
	public void continueMakingMovesAfterPiecesArePlaced()
	{
		
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
		final HantoBoard board = player.getGameState().getBoard();
		board.placePiece(new HexCell(new TestHantoCoordinate(0,0), HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY));
		board.placePiece(new HexCell(new TestHantoCoordinate(0,1), HantoPlayerColor.RED, HantoPieceType.BUTTERFLY));

		final Collection<HexCoordinate> validPlacements = new ArrayList<HexCoordinate>();
		validPlacements.add(new HexCoordinate(-1,0));
		validPlacements.add(new HexCoordinate(1,-1));
		validPlacements.add(new HexCoordinate(0,-1));

		assertTrue(validPlacements.contains(player.findValidPlacement(HantoPieceType.SPARROW)));
	}

	@Test
	public void thisPlayerMovesFirstShouldPlacePieceAtOrigin() throws HantoException
	{
		final HantoMoveRecord result = player.makeMove(null);
		game.makeMove(result.getPiece(), result.getFrom(), result.getTo());
	}

}
