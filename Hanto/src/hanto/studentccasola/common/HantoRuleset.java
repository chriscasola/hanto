/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentccasola.common;

import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoException;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.MoveResult;

/**
 * An abstract ruleset implementation that enforces rules
 * common to all versions of Hanto
 * 
 * @author Chris Casola
 * @version Feb 9, 2013
 */
public abstract class HantoRuleset
{
	/** The state of the game */
	protected GameState gameState;
	
	/** A list of pieces that can walk */
	protected Set<HantoPieceType> walkingPieces;
	
	/** A list of pieces that can fly */
	protected Set<HantoPieceType> flyingPieces;
	
	/**
	 * Construct the ruleset
	 * 
	 * @param gameState the game state
	 */
	protected HantoRuleset(GameState gameState)
	{
		this.gameState = gameState;
		walkingPieces = new HashSet<HantoPieceType>();
		flyingPieces = new HashSet<HantoPieceType>();
	}
	
	/**
	 * Checks that all rules specified in this class are met
	 * 
	 * @param pieceType the piece being placed/moved
	 * @param from the source location of the piece
	 * @param to the destination location of the piece
	 * @throws HantoException if a rule is violated
	 */
	public void checkAll(HantoPieceType pieceType, HantoCoordinate from, 
			HantoCoordinate to) throws HantoException
	{
		HexCoordinate hexFrom = null;
		HexCoordinate hexTo = null;
		if (from != null)
		{
			hexFrom = new HexCoordinate(from);
		}
		if (to != null)
		{
			hexTo = new HexCoordinate(to);
		}
		
		checkIfGameIsOver();
		onlyMoveAfterButterflyIsPlaced(hexFrom);
		onlyCertainPiecesCanMove(pieceType, hexFrom);
		pieceCanWalkOneHex(pieceType, hexFrom, hexTo);
		cannotMoveFromUnoccupiedHex(hexFrom, hexTo);
		cannotMoveOpponentsPiece(pieceType, hexFrom);
		firstMoveAtOrigin(hexTo);
		pieceToMoveMatchesPieceAtFrom(pieceType, hexFrom);
		piecesMustBeAdjacent(hexTo);
		playButterflyByRoundFour(pieceType);
	}
	
	/**
	 * Performs post-move checks
	 * 
	 * @param pieceType the piece being placed/moved
	 * @param from the source location of the piece
	 * @param to the destination location of the piece
	 * @throws HantoException if a rule is violated
	 */
	public void postMoveChecks(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		gameState.getBoard().checkContiguity();
	}
	
	/**
	 * @return the set of piece types that are able to walk
	 */
	public Set<HantoPieceType> getWalkingPieces()
	{
		return walkingPieces;
	}
	
	/**
	 * @return the set of piece types that are able to fly
	 */
	public Set<HantoPieceType> getFlyingPieces()
	{
		return flyingPieces;
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
	 * Some pieces are not able to move
	 * 
	 * @param pieceType the piece being moved during this turn
	 * @param from the source location of the piece
	 * @throws HantoException
	 */
	protected void onlyCertainPiecesCanMove(HantoPieceType pieceType, 
			HexCoordinate from) throws HantoException
	{
		if (from != null && !walkingPieces.contains(pieceType) &&
				!flyingPieces.contains(pieceType))
		{
			throw new HantoException("This piece type cannot move!");
		}
	}
	
	/**
	 * Pieces cannot move until the butterfly is placed
	 * 
	 * @param from the source location of the piece
	 * @throws HantoException
	 */
	protected void onlyMoveAfterButterflyIsPlaced(HexCoordinate from) throws HantoException
	{
		if (from != null && gameState.getPieces().get(
				gameState.getTurn()).contains(HantoPieceType.BUTTERFLY))
		{
			throw new HantoException("Cannot move until butterfly is placed!");
		}
	}
	
	/**
	 * Verify that the the piece is allowed to walk and that it only
	 * walks on hex
	 * 
	 * @param pieceType the piece being moved during this turn
	 * @param from the source location of the piece
	 * @param to the intended destination of the piece
	 * @throws HantoException if the butterfly attempts to walk more than one hex
	 */
	protected void pieceCanWalkOneHex(HantoPieceType pieceType, 
			HexCoordinate from, HexCoordinate to) throws HantoException
	{
		if (from != null)
		{
			if (walkingPieces.contains(pieceType) && !canSlide(from, to))
			{
				throw new HantoException("This is not a valid walk!");
			}
		}
	}
	
	/**
	 * Do not allow a move from an unoccupied cell
	 * 
	 * @param from the source location of the piece
	 * @param to the destination of the move
	 * @throws HantoException if the from location on the board does not contain a piece
	 */
	protected void cannotMoveFromUnoccupiedHex(
			HexCoordinate from, HexCoordinate to) throws HantoException
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
	protected void cannotMoveOpponentsPiece(
			HantoPieceType pieceType, HexCoordinate from) throws HantoException
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
	protected void pieceToMoveMatchesPieceAtFrom(
			HantoPieceType pieceType, HexCoordinate from) throws HantoException
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
	 * Make sure all pieces will be adjacent if this move is made
	 * 
	 * @param to the destination of this move
	 * @throws HantoException
	 */
	protected void piecesMustBeAdjacent(HexCoordinate to) throws HantoException
	{
		if (!gameState.getBoard().isAdjacent(to) && 
				gameState.getBoard().getNumOccupiedCells() > 0)
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
	
	/**
	 * Determine if a piece is able to slide from the given source location
	 * to the given destination location.
	 * @param from the source location
	 * @param to the destination location
	 * @return true if the piece is able to slide, false otherwise
	 */
	private boolean canSlide(HexCoordinate from, HexCoordinate to)
	{
		boolean ableToSlide = false;
		
		if (!from.getAdjacentCoordinates().contains(to))
		{
			ableToSlide = false;
		}
		else {
			final Set<HexCoordinate> commonCells = to.getAdjacentCoordinates();
			commonCells.retainAll(from.getAdjacentCoordinates());
			for (HexCoordinate coord : commonCells)
			{
				if (gameState.getBoard().getCellAtCoordinate(coord) == null)
				{
					ableToSlide = true;
				}
			}
		}
		
		return ableToSlide;
	}
}
