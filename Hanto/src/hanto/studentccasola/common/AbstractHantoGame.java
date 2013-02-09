package hanto.studentccasola.common;

import java.util.List;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentccasola.util.GameState;
import hanto.studentccasola.util.HexCell;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

public abstract class AbstractHantoGame implements HantoGame
{
	/** The current state of the game: either OK, RED_WINS, BLUE_WINS, or DRAW */
	protected GameState gameState;
	
	/** The ruleset for this game */
	protected HantoRuleset ruleset;
	
	/**
	 * Construct a new game with Blue moving first
	 */
	public AbstractHantoGame()
	{
		initializeFields();
	}
	
	/**
	 * Initializes all of the field values
	 */
	protected abstract void initializeFields();

	/* 
	 * @see hanto.common.HantoGame#initialize(hanto.util.HantoPlayerColor)
	 */
	@Override
	public void initialize(HantoPlayerColor firstPlayer) throws HantoException
	{
		initializeFields();

		// Set the current turn based on the value of firstPlayer
		if (firstPlayer != null)
		{
			if (firstPlayer != HantoPlayerColor.BLUE && firstPlayer != HantoPlayerColor.RED)
			{
				throw new HantoException("Player color must be blue or red.");
			}
			gameState.setTurn(firstPlayer);
			gameState.setFirstPlayer(firstPlayer);
		}
		else
		{
			gameState.setTurn(HantoPlayerColor.BLUE);
			gameState.setFirstPlayer(HantoPlayerColor.BLUE);
		}
	}

	/* 
	 * @see hanto.common.HantoGame#makeMove(hanto.util.HantoPieceType,
	 * hanto.util.HantoCoordinate, hanto.util.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		// Verify this move does not violate the rules
		ruleset.checkAll(pieceType, from, to);

		if (from != null) // move the piece
		{
			gameState.getBoard().movePiece(from, to);
		}
		else // place the piece
		{
			// Make sure the player has this piece to play
			accountForPiece(pieceType);

			// Place the piece on the board
			gameState.getBoard().placePiece(new HexCell(to, gameState.getTurn(), pieceType));

			// Remove the piece from the player's available list
			usePiece(pieceType);
		}

		// Move the game state to the next turn
		return gameState.nextTurn();
	}
	
	/**
	 * Make sure the user has one of the given pieces so he/she can place it. Remove
	 * the piece from that user's list of available pieces to place.
	 * 
	 * @param piece the piece type to place
	 * @throws HantoException if the player does not have a piece of this type remaining to play
	 */
	protected void accountForPiece(HantoPieceType piece) throws HantoException
	{
		final List<HantoPieceType> currentPlayersPieces = gameState.getPieces().get(gameState.getTurn());
		if (!currentPlayersPieces.contains(piece))
		{
			throw new HantoException("You do not have any pieces remaining of this type.");
		}
	}
	
	/**
	 * Removes the give piece from the current player's list of available
	 * pieces.
	 * 
	 * @param piece the piece to remove
	 */
	protected void usePiece(HantoPieceType piece)
	{
		gameState.getPieces().get(gameState.getTurn()).remove(piece);
	}
	
	/**
	 * @return the state of this game
	 */
	public GameState getState()
	{
		return gameState;
	}
	
	/* 
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		return gameState.getBoard().toString();
	}
}
