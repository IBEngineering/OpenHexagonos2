package openhexagonos.ai.random;

import java.util.ArrayList;

import openhex.game.board.Board;
import openhexagonos.game.HexMove;
import openhexagonos.game.HexMoveValidator;
import openhexagonos.game.HexPlayer;

public class HexRandomAI extends HexPlayer {

	public HexRandomAI(String name, Faction type) {
		super(name, type);
		this.human = false;
	}

	@Override
	public HexMove getNextHexMove(Board b, HexPlayer opponent) {
		 ArrayList<HexMove> moves = HexMoveValidator.getPossibleMoves(this, b);
	     int random = (int) ( Math.random() * moves.size() );
	     return moves.get(random);
	}
	
	
	
}
