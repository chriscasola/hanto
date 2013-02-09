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
import hanto.studentccasola.common.HantoRuleset;
import hanto.studentccasola.util.BasicHantoBoard;
import hanto.studentccasola.util.GameState;
import hanto.studentccasola.util.HantoRule;
import hanto.studentccasola.util.HexCell;
import hanto.studentccasola.util.HexCoordinate;
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

	/** The color of the players whose turn it is */
	//private HantoPlayerColor turn;
	
	/** The color of the player whose turn was first */
	//private HantoPlayerColor wentFirst;

	/** The board, which contains all pieces that have been placed */
	//private HantoBoard board;

	/** A map containing a list of pieces for each player that have not been placed yet. */
	//private Map<HantoPlayerColor,List<HantoPieceType>> pieces;

	/** The current round (there is a max of 10 in this version of Hanto) */
	//private Integer round;

	/** The current state of the game: either OK, RED_WINS, BLUE_WINS, or DRAW */
	private GameState gameState;
	
	/** The rules enforced by this game */
	//private Set<HantoRule> rules;
	
	private HantoRuleset ruleset;

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

	/* 
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		return gameState.getBoard().toString();
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
	private void usePiece(HantoPieceType piece)
	{
		gameState.getPieces().get(gameState.getTurn()).remove(piece);
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

		gameState.getPieces().put(HantoPlayerColor.BLUE, bluePieces);
		gameState.getPieces().put(HantoPlayerColor.RED, redPieces);

	}

	/**
	 * Initializes all of the field values
	 */
	private void initializeFields()
	{
		//turn = wentFirst = HantoPlayerColor.BLUE;
		//board = new BasicHantoBoard();
		//pieces = new HashMap<HantoPlayerColor,List<HantoPieceType>>();
		//distributePieces();
		//round = 1;
		//gameState = MoveResult.OK;
		//rules = new HashSet<HantoRule>();
		gameState = new GameState(new BasicHantoBoard(), HantoPlayerColor.BLUE, 1, new HashMap<HantoPlayerColor,List<HantoPieceType>>(), MoveResult.OK, HantoPlayerColor.BLUE);
		ruleset = new GammaHantoRuleset(gameState);
		distributePieces();
		//buildRuleList();
	}
	
	/**
	 * @return the state of this game
	 */
	public GameState getState()
	{
		return gameState;
	}
}
