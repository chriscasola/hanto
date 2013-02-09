package hanto.studentccasola.common;

import hanto.common.HantoException;
import hanto.studentccasola.util.GameState;
import hanto.studentccasola.util.HexCell;
import hanto.studentccasola.util.HexCoordinate;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.MoveResult;

public abstract class HantoRuleset
{
	protected GameState gameState;
	
	public void checkAll(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException
	{
		HexCoordinate hexFrom = null;
		if (from != null)
		{
			hexFrom = new HexCoordinate(from);
		}
		final HexCoordinate hexTo = new HexCoordinate(to);
		
		checkIfGameIsOver();
		butterflyCanWalkOneHex(pieceType, hexFrom, hexTo);
		cannotMoveFromUnoccupiedHex(hexFrom, hexTo);
		cannotMoveOpponentsPiece(pieceType, hexFrom);
		firstMoveAtOrigin(hexTo);
		pieceToMoveMatchesPieceAtFrom(pieceType, hexFrom);
		onlyButterfliesMayMove(pieceType, hexFrom);
		piecesMustBeAdjacent(hexTo);
		playButterflyByRoundFour(pieceType);
	}
	
	/**
	 * Make sure the game is not over
	 * 
	 * @throws HantoException
	 */
	protected void checkIfGameIsOver() throws HantoException
	{
		if (gameState.getStatus() != MoveResult.OK) {
			throw new HantoException("The game is over, no more moves can be played.");
		}
	}
	
	/**
	 * Verify that the butterfly is not attempting to walk more than one hex
	 * 
	 * @param pieceType the piece being moved during this turn
	 * @param from the source location of the piece
	 * @param to the intended destination of the piece
	 * @throws HantoException if the butterfly attempts to walk more than one hex
	 */
	protected void butterflyCanWalkOneHex(HantoPieceType pieceType, HexCoordinate from, HexCoordinate to) throws HantoException
	{
		if (from != null && pieceType == HantoPieceType.BUTTERFLY &&
				!from.getAdjacentCoordinates().contains(to))
		{
			throw new HantoException("Butterfly can only walk one hex!");
		}
	}
	
	/**
	 * Do not allow a move from an unoccupied cell
	 * 
	 * @param from the source location of the piece
	 * @param to the destination of the move
	 * @throws HantoException if the from location on the board does not contain a piece
	 */
	protected void cannotMoveFromUnoccupiedHex(HexCoordinate from, HexCoordinate to) throws HantoException
	{
		if (from != null && gameState.getBoard().getCellAtCoordinate(from) == null)
		{
			throw new HantoException("Cannot move from unoccupied cell.");
		}
	}
	
	/**
	 * Do not allow a player to move another player's piece
	 * 
	 * @param pieceType the piece being moved during this turn
	 * @param from the source location of the piece
	 * @throws HantoException
	 */
	protected void cannotMoveOpponentsPiece(HantoPieceType pieceType, HexCoordinate from) throws HantoException
	{
		if (from != null)
		{
			final HexCell cellAtFromLocation = gameState.getBoard().getCellAtCoordinate(from);
			if (cellAtFromLocation != null && cellAtFromLocation.getPlayer() != gameState.getTurn())
			{
				throw new HantoException("You cannot move another player's piece.");
			}
		}
	}
	
	/**
	 * Make sure the first move is at 0,0
	 * 
	 * @param to the destination of this move
	 * @throws HantoException if the move is not at the origin
	 */
	protected void firstMoveAtOrigin(HexCoordinate to) throws HantoException
	{
		if (gameState.getBoard().getNumOccupiedCells() < 1 && 
				(to.getX() != 0 || to.getY() != 0))
		{
			throw new HantoException("First move must be at (0,0)");
		}
	}
	
	/**
	 * Make sure the piece type specified matches the piece at the from location
	 * 
	 * @param pieceType the piece being moved during this turn
	 * @param from the source location of the piece
	 * @throws HantoException if the piece type specified does not match the piece
	 * type at the from location
	 */
	protected void pieceToMoveMatchesPieceAtFrom(HantoPieceType pieceType, HexCoordinate from) throws HantoException
	{
		if (from != null)
		{
			final HexCell cellAtFromLocation = gameState.getBoard().getCellAtCoordinate(from);
			if (cellAtFromLocation != null && cellAtFromLocation.getPiece() != pieceType)
			{
				throw new HantoException(
						"The type of piece to move does not match the piece in the from cell.");
			}
		}
	}
	
	/**
	 * Ensure only butterflies are able to move
	 * 
	 * @param pieceType the piece being moved during this turn
	 * @param from the source location of the piece
	 * @throws HantoException
	 */
	protected void onlyButterfliesMayMove(HantoPieceType pieceType, HexCoordinate from) throws HantoException
	{
		if (from != null && pieceType != HantoPieceType.BUTTERFLY)
		{
			throw new HantoException("Only butterflies may move in this game.");
		}
	}
	
	/**
	 * Make sure all pieces will be adjacent if this move is made
	 * 
	 * @param to the destination of this move
	 * @throws HantoException
	 */
	protected void piecesMustBeAdjacent(HexCoordinate to) throws HantoException
	{
		if (!gameState.getBoard().isAdjacent(to) && gameState.getBoard().getNumOccupiedCells() > 0)
		{
			throw new HantoException("Pieces must be placed adjacent to other pieces.");
		}
	}
	
	/**
	 * Make sure the butterfly is played by round four
	 * 
	 * @param pieceType the piece being moved during this turn
	 * @throws HantoException
	 */
	protected void playButterflyByRoundFour(HantoPieceType pieceType) throws HantoException
	{
		if (gameState.getCurrentRound() == 4 && 
				pieceType != HantoPieceType.BUTTERFLY && 
				gameState.getPieces().get(gameState.getTurn()).contains(HantoPieceType.BUTTERFLY))
		{
			throw new HantoException("You must play your butterfly before or during round 4.");
		}
	}
}
