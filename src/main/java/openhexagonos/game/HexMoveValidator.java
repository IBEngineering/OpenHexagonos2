/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhexagonos.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import openhex.game.board.Board;
import openhex.game.board.HexTile;
import openhex.game.board.owner.Ownable;
import openhex.vec.Vectors;
import openhex.vec.fin.VectorAS;

/**
 *
 * @author b0rg3rt
 */
public class HexMoveValidator {
    
	private static final Logger LOG = LoggerFactory.getLogger(HexMoveValidator.class);

	
    public static boolean isValidMove(HexMove move, Board board)
    {
        // Check if the source is a valid position
        HexTile fromTile = board.getTile(move.getFrom());
        if (fromTile == null)
        {
        	LOG.debug("Source invalid");
            return false;
        }
        // Check if we are moving our piece
        if (fromTile.getOwner() != move.getPlayer())
        {
            if (fromTile.getOwner() == null)
            {
                return false;
            }
            LOG.debug("Owner invalid Tile:" + fromTile.getOwner().toString() + " move owner: " + move.getPlayer().toString());
            return false;
        }
        
        // Check if the destination is a valid position
        HexTile toTile = board.getTile(move.getTo());
        if (toTile == null)
        {
        	LOG.debug("Destination invalid");
            return false;
        }
        if (toTile.getOwner() != null)
        {
        	LOG.debug("Destination occupied");
            return false;
        }
        
        // Check if the distance between a and b is < 3
        if (Vectors.toVectorCD(move.getFrom()).distanceToTile(Vectors.toVectorCD(move.getTo())) > 2)
        {
            LOG.debug("Distance to big");
            return false;
        }
        
        LOG.debug("This is a valid move !");
        return true;
    }
    
    /*
    * Returns all possible moves for player P
    */
    
    public static ArrayList<HexMove> getPossibleMoves(HexPlayer p, Board board)
    {
        ArrayList<HexMove> moves = new ArrayList<HexMove>();        

        // Get all tiles owned by P
        ArrayList<HexTile> tiles = new ArrayList<HexTile>();
        Iterator<Ownable> it = p.getOwnables().iterator();
        while (it.hasNext()) {
        	
        	Ownable o = it.next();
        	if (o instanceof HexTile) {
        		tiles.add((HexTile)o);
        	}
        }
              
        // Get All possible moves
        for (HexTile tile: tiles)
        {
            ArrayList<VectorAS> ringlist = Vectors.getRing(tile.getPosition(),1);
            ringlist.addAll(Vectors.getRing(tile.getPosition(),2));
            
            for (VectorAS coord: ringlist)
            {
                HexTile t = board.getTile(coord);
                if (t != null)
                {
                    if (t.getOwner() == null)
                    {
                        moves.add(new HexMove(p, tile.getPosition(), coord));
                    }
                }
            }
        }
        
        return moves;
    }
    
    /*
     * Return possible moves from position for player p
     */
    public static ArrayList<HexMove> getPossibleMoves(HexPlayer p, VectorAS position , Board board) {
    	ArrayList<HexMove> moves = new ArrayList<HexMove>();
    	
    	ArrayList<VectorAS> ringlist = Vectors.getRing(position,1);
        ringlist.addAll(Vectors.getRing(position,2));
        
        for (VectorAS coord: ringlist)
        {
            HexTile t = board.getTile(coord);
            if (t != null)
            {
                if (t.getOwner() == null)
                {
                    moves.add(new HexMove(p, position, coord));
                }
            }
        }
        return moves;
    }

    public static HexMove getBestMove(ArrayList<HexMove> moves, Board board)
    {
        int bestMoveVal = -1;
        HexMove bestMove = null;
        for(HexMove m : moves)
        {
            Integer moveVal = new Integer(getMoveValue(m, board));
            if (moveVal > bestMoveVal)
            {
                bestMoveVal = moveVal;
                bestMove = m;
            }
        }
        return bestMove;
    }
    
    public static int getMoveValue(HexMove move, Board board)
    {
        if (move == null) { return Integer.MIN_VALUE; }
            
        int points = 0;
        
        //If we do a clone we get 1 point
        if (Vectors.getTileDistance(move.getFrom(), move.getTo()) == 1)
        {
            points = points + 1;
        }

        ArrayList<VectorAS> neighs = Vectors.getRing(move.getTo(),1);
        
        for(VectorAS v : neighs)
        {
            HexTile t = board.getTile(v);
            if (t != null)
            {
                if (t.getOwner() != null)
                {
                    if (t.getOwner() != move.getPlayer())
                    {
                        points = points + 1;
                    }
                }
            }
        }
        return points;
    }
    
    
    
    public static void executeMove(HexMove move, Board board, HexPlayer currPlayer)
    {
        
        if(move == null) { return; }
        if(move.getFrom() == null) { return; }
        if(move.getTo() == null) { return; }
        
        //Determine if we do a move or clone
        if (move.getFrom().distance(move.getTo()) < 2)
        {
            //Clone
            HexTile src = board.getTile(move.getFrom());
            HexTile dst = board.getTile(move.getTo());
            dst.setOwner(move.getPlayer());
            
            //increment points by 1
            //move.getPlayer().incrementPoints();
            
            board.addTile(dst);
            
        }
        else
        {
            //Jump
            HexTile src = board.getTile(move.getFrom());
            HexTile dst = board.getTile(move.getTo());
            dst.setOwner(move.getPlayer());
            
            src.setOwner(null);
            
            board.addTile(src);
            board.addTile(dst);


        }
        
        //Defeat Neigbors
        for (int i = 0; i < 6; i++)
        {
            HexTile neighTile = board.getTile((VectorAS) move.getTo().add(Vectors.directionsAS[i])); 
            
            // If an existing tile
            // and If not an empty tile
            // and if not our tile
            if (neighTile != null &&
                    neighTile.getOwner() != null && 
                    neighTile.getOwner() != move.getPlayer() )
            {
                //neighTile.getOwner().decrementPoints();
                //move.getPlayer().incrementPoints();
                
                neighTile.setOwner(currPlayer);
                board.addTile(neighTile);
                
            }
        }
    }
    
}
