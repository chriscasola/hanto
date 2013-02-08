package hanto.studentccasola.rules;

import hanto.common.HantoException;
import hanto.studentccasola.util.GameState;
import hanto.studentccasola.util.HantoRule;

public class CannotMoveFromUnoccupiedHex implements HantoRule
{

	@Override
	public void validate(GameState state) throws HantoException
	{
		if (state.getFrom() != null && state.getBoard().getCellAtCoordinate(state.getFrom()) == null) {
			throw new HantoException("Cannot move from unoccupied cell.");
		}
	}

}
