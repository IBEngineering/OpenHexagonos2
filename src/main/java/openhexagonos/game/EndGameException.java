package openhexagonos.game;

public class EndGameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public EndGameException () {

    }

    
    private HexPlayer winner;
    private HexPlayer loser;
    
    public EndGameException (HexPlayer winner, HexPlayer loser) {
        super ();
        this.winner = winner;
        this.loser = loser;
    }

	public HexPlayer getWinner() {
		return winner;
	}

	public HexPlayer getLoser() {
		return loser;
	}
    
    
}
