package openhexagonos.game;

import java.util.Iterator;

import openhex.game.Game;
import openhex.game.board.Board;
import openhex.game.board.HexTile;
import openhex.vec.Vectors;
import openhex.vec.fin.VectorAS;

public class HexMatch {
	
	private HexPlayer[] players = new HexPlayer[2];
	private Game g;
	private int currentPlayer = 1;
	
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
		
		VectorAS[] startpositions = new VectorAS[6];
		
		for (int q = 0; q < 6; q++) {
			startpositions[q] = new VectorAS(0,0,0);
			for (int i = 0; i < HexagonosGame.BOARDSIZE; i++)
			{
				startpositions[q] = (VectorAS)startpositions[q].add(Vectors.directionsAS[q]);	
			}
		}
		//Player 0

		HexTile t = b.getTile(startpositions[0]);
		t.setOwner(players[0]);
		b.addTile(t);
		
		t = b.getTile(startpositions[2]);
		t.setOwner(players[0]);
		b.addTile(t);
		
		t = b.getTile(startpositions[4]);
		t.setOwner(players[0]);
		b.addTile(t);
		
		//Player 1
		
		t = b.getTile(startpositions[1]);
		t.setOwner(players[1]);
		b.addTile(t);
		
		t = b.getTile(startpositions[3]);
		t.setOwner(players[1]);
		b.addTile(t);
		
		t = b.getTile(startpositions[5]);
		t.setOwner(players[1]);
		b.addTile(t);

	}

	public void cleanBoard () {
		Board b = g.getBoard();
		
		Iterator<HexTile> it = b.getTiles().iterator();
		
		while (it.hasNext()) {
			it.next().setOwner(null);
		}		
	}
	
	public void nextPlayer() throws EndGameException {
		
		HexPlayer opponent = players[currentPlayer];
		currentPlayer++;
		if (currentPlayer > 1) { currentPlayer = 0; }
		
		if (HexMoveValidator.getPossibleMoves( players[currentPlayer], g.getBoard()).isEmpty()) {
			if ( players[currentPlayer].getPoints() > opponent.getPoints()) {
				throw new EndGameException(players[currentPlayer], opponent);
			} else if  ( players[currentPlayer].getPoints() < opponent.getPoints()){
				throw new EndGameException(opponent, players[currentPlayer]);
			} else {
				//draw
				throw new EndGameException(null, null);
			}
		}
		
		HexMove move = players[currentPlayer].getNextHexMove(g.getBoard(), opponent);
		HexMoveValidator.executeMove(move, g.getBoard(), players[currentPlayer]);
	}
	

}
