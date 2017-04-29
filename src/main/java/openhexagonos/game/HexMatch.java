package openhexagonos.game;

import java.util.Iterator;

import openhex.game.Game;
import openhex.game.board.Board;
import openhex.game.board.HexTile;

public class HexMatch {
	
	private HexPlayer[] players = new HexPlayer[2];
	private Game g;
	
	public HexMatch(Game g, HexPlayer a, HexPlayer b) throws Exception{
		this.g= g;
		players[0] = a;
		players[1] = b;
		
		if (a.getFaction() == b.getFaction()) {
			throw new Exception("Impossible factions");
		}
		
	}
	
	public void startMatch()
	{
		cleanBoard();
		addStartPositions();
		
		
	}
	
	
	private void addStartPositions() {
		Board b = g.getBoard();
		
		//Player 0
		
		
		//Player 1

	}

	public void cleanBoard () {
		Board b = g.getBoard();
		
		Iterator<HexTile> it = b.getTiles().iterator();
		
		while (it.hasNext()) {
			it.next().setOwner(null);
		}
		
		
	}
	

}
