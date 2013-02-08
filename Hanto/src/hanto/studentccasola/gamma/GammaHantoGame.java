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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentccasola.common.HantoBoard;
import hanto.studentccasola.rules.*;
import hanto.studentccasola.util.BasicHantoBoard;
import hanto.studentccasola.util.GameState;
import hanto.studentccasola.util.HantoRule;
import hanto.studentccasola.util.HexCell;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * This is the gamma version of Hanto as specified in the Hanto Developer's
 * Guide. There are two types of pieces: butterflies and sparrows. Each player
 * has one butterfly and five sparrows. Blue moves first by default. Each
 * player's butterfly must be placed on the board by the fourth turn. Butterflies
 * may walk one cell. The game ends after 10 rounds.
 *
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class GammaHantoGame implements HantoGame
{

	/** The number of sparrows alloted to each player */
	public static final int NUM_SPARROWS = 5;
	
	public static final int MAX_NUM_ROUNDS = 10;

	/** The color of the players whose turn it is */
	private HantoPlayerColor turn;
	
	/** The color of the player whose turn was first */
	private HantoPlayerColor wentFirst;

	/** The board, which contains all pieces that have been placed */
	private HantoBoard board;

	/** A map containing a list of pieces for each player that have not been placed yet. */
	private Map<HantoPlayerColor,List<HantoPieceType>> pieces;

	/** The current round (there is a max of 10 in this version of Hanto) */
	private int round;

	/** The current state of the game: either OK, RED_WINS, BLUE_WINS, or DRAW */
	private MoveResult gameState;
	
	/** The rules enforced by this game */
	private Set<HantoRule> rules;

	/**
	 * Constructs a new GammaHantoGame with Blue moving first.
	 */
	public GammaHantoGame()
	{
		initializeFields();
	}

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
			turn = wentFirst = firstPlayer;
		}
		else
		{
			turn = wentFirst = HantoPlayerColor.BLUE;
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
		if (gameState == MoveResult.OK) // only allow moves if the game has not ended
		{
			// Verify this move does not violate the rules
			checkAdherenceToRules(from, to, pieceType);

			if (from != null) // move the piece
			{
				board.movePiece(from, to);
			}
			else // place the piece
			{
				// Make sure the player has this piece to play
				accountForPiece(pieceType);

				// Place the piece on the board
				board.placePiece(new HexCell(to, turn, pieceType));

				// Remove the piece from the player's available list
				usePiece(pieceType);
			}

			// Switch the turn to the other player
			turn = (turn == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;

			// Increment the round if both players have had their turn
			round += (turn == wentFirst) ? 1 : 0;
			
			// Get the state of the game board
			gameState = board.getBoardState();

			// End the game if round MAX_NUM_ROUNDS is complete
			if (round > MAX_NUM_ROUNDS)
			{
				gameState = (gameState != MoveResult.OK) ? gameState : MoveResult.DRAW; 
			}
		}
		else {
			throw new HantoException("The game is over, no more moves can be played.");
		}

		return gameState;
	}

	/* 
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		return board.toString();
	}

	/**
	 * @return the color of the player whose turn it is
	 */
	public HantoPlayerColor getTurn()
	{
		return turn;
	}

	/**
	 * @return the number of the current round (between 1 and 6)
	 */
	public int getRound()
	{
		return round;
	}

	/**
	 * @return the game board
	 */
	protected HantoBoard getBoard()
	{
		return board;
	}
	
	/**
	 * @param board the new game board
	 */
	protected void setBoard(HantoBoard board)
	{
		this.board = board;
	}

	/**
	 * Make sure the user has one of the given pieces so he/she can place it. Remove
	 * the piece from that user's list of available pieces to place.
	 * 
	 * @param piece the piece type to place
	 * @throws HantoException if the player does not have a piece of this type remaining to play
	 */
	private void accountForPiece(HantoPieceType piece) throws HantoException
	{
		final List<HantoPieceType> currentPlayersPieces = pieces.get(turn);
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
	private void usePiece(HantoPieceType piece)
	{
		pieces.get(turn).remove(piece);
	}

	/**
	 * Called when the game is initialized to divy up the pieces to each
	 * player's list of available pieces.
	 */
	private void distributePieces()
	{
		final List<HantoPieceType> bluePieces = new ArrayList<HantoPieceType>();
		final List<HantoPieceType> redPieces = new ArrayList<HantoPieceType>();

		// Give each player two butterflies
		bluePieces.add(HantoPieceType.BUTTERFLY);
		redPieces.add(HantoPieceType.BUTTERFLY);

		// Give each player NUM_SPARROWS sparrows
		for (int i = 0; i < NUM_SPARROWS; i++)
		{
			bluePieces.add(HantoPieceType.SPARROW);
			redPieces.add(HantoPieceType.SPARROW);
		}

		pieces.put(HantoPlayerColor.BLUE, bluePieces);
		pieces.put(HantoPlayerColor.RED, redPieces);

	}

	/**
	 * Verify that the current potential move would not violate
	 * any of the rules of this game.
	 * 
	 * @param from the location of the piece to move
	 * @param to the location to move the piece to
	 * @param pieceType the type of piece
	 * @throws HantoException if any rule is violated
	 */
	private void checkAdherenceToRules(HantoCoordinate from, HantoCoordinate to, 
			HantoPieceType pieceType) throws HantoException
	{
		final GameState currentState = new GameState(board, from, to, pieceType, turn, round, pieces);
		for (HantoRule rule : rules)
		{
			rule.validate(currentState);
		}
	}

	/**
	 * Initializes all of the field values
	 */
	private void initializeFields()
	{
		turn = wentFirst = HantoPlayerColor.BLUE;
		board = new BasicHantoBoard();
		pieces = new HashMap<HantoPlayerColor,List<HantoPieceType>>();
		distributePieces();
		round = 1;
		gameState = MoveResult.OK;
		rules = new HashSet<HantoRule>();
		buildRuleList();
	}
	
	private void buildRuleList()
	{
		rules.add(new ButterflyCanWalkOneHex());
		rules.add(new CannotMoveFromUnoccupiedHex());
		rules.add(new CannotMoveOpponentsPiece());
		rules.add(new FirstMoveAtOrigin());
		rules.add(new MustSpecifyTypeOfPieceToMove());
		rules.add(new OnlyButterfliesMayMove());
		rules.add(new PiecesMustBeAdjacent());
		rules.add(new PlayButterflyByRoundFour());
	}
}
