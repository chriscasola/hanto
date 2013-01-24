/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.beta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentccasola.common.HantoBoard;
import hanto.studentccasola.util.BasicHantoBoard;
import hanto.studentccasola.util.HexCell;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * This is the beta version of Hanto as specified in the Hanto Developer's
 * Guide. There are two types of pieces: butterflies and sparrows. Each player
 * has one butterfly and five sparrows. All pieces have the same abilities: they
 * may be placed on the board but not moved. Blue moves first by default. Each
 * player's butterfly must be placed on the board by the fourth turn.
 *
 * @author Chris Casola
 * @version Jan 22, 2013
 */
public class BetaHantoGame implements HantoGame
{
	
	/** The number of sparrows alloted to each player */
	public static final int NUM_SPARROWS = 5;
	
	/** The color of the players whose turn it is */
	private HantoPlayerColor turn;
	
	/** The board, which contains all pieces that have been placed */
	private HantoBoard board;
	
	/** A map containing a list of pieces for each player that have not been placed yet. */
	private Map<HantoPlayerColor,List<HantoPieceType>> pieces;
	
	/** The current round (there are 6 in this version of Hanto) */
	private int round;
	
	/** The current state of the game: either OK, RED_WINS, BLUE_WINS, or DRAW */
	private MoveResult gameState;
	
	/**
	 * Constructs a new BetaHantoGame with Blue moving first.
	 */
	public BetaHantoGame()
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
			turn = firstPlayer;
		}
		else
		{
			turn = HantoPlayerColor.BLUE;
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
			final int numOccupiedCells = board.getNumOccupiedCells();
			
			// Verify this move does not violate the rules
			checkAdherenceToRules(to, pieceType);
			
			// Make sure the player has this piece to play
			accountForPiece(pieceType);
			
			// Place the piece on the board
			board.placePiece(new HexCell(to, turn, pieceType));
			
			// Switch the turn to the other player
			turn = (turn == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
			
			// Increment the round if both players have had their turn
			round += numOccupiedCells % 2;
			
			// Get the state of the game board
			gameState = board.getBoardState();
			
			// End the game if round 6 is complete
			if (round > 6)
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
		else {
			currentPlayersPieces.remove(piece);
		}
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
	 * Verify that placing the given piece type at the given location for
	 * the current player would not violate any of the rules of Beta Hanto.
	 * 
	 * @param to the destination coordinate
	 * @param pieceType the type of piece
	 * @throws HantoException if one of the rules is violated
	 */
	private void checkAdherenceToRules(HantoCoordinate to, HantoPieceType pieceType) 
			throws HantoException
	{
		final HexCell currentMove = new HexCell(to, turn, pieceType);
		final int numOccupiedCells = board.getNumOccupiedCells();
		
		if (numOccupiedCells < 1 && (to.getX() != 0 || to.getY() != 0))
		{
			throw new HantoException("First move must be at (0,0)");
		}
		if (!board.isAdjacent(currentMove) && numOccupiedCells > 0)
		{
			throw new HantoException("Pieces must be placed adjacent to other pieces.");
		}
		if (round == 4 && pieceType != HantoPieceType.BUTTERFLY && 
				pieces.get(turn).contains(HantoPieceType.BUTTERFLY))
		{
			throw new HantoException("You must play your butterfly before or during round 4.");
		}
	}
	
	/**
	 * Initializes all of the field values
	 */
	private void initializeFields()
	{
		turn = HantoPlayerColor.BLUE;
		board = new BasicHantoBoard();
		pieces = new HashMap<HantoPlayerColor,List<HantoPieceType>>();
		distributePieces();
		round = 1;
		gameState = MoveResult.OK;
	}
}
