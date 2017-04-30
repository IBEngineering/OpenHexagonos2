/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhexagonos.ai.minmax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import openhex.game.board.Board;
import openhexagonos.game.HexMove;
import openhexagonos.game.HexMoveValidator;
import openhexagonos.game.HexPlayer;

/**
 *
 * @author b0rg3rt
 */
public class HexOneStepAiPlayer extends HexPlayer {
    
    public HexOneStepAiPlayer(String name, Faction faction) {
        super(name, faction);
        this.human = false;
    }
    
    @Override
    public HexMove getNextHexMove(Board b, HexPlayer opponent)
    {
        ArrayList<HexMove> allMoves = HexMoveValidator.getPossibleMoves(this, b);
     
        HashMap <Integer,ArrayList<HexMove>> moveMap = new HashMap<Integer,ArrayList<HexMove>>();
        
        for (HexMove m : allMoves) {
            Integer moveVal = new Integer(HexMoveValidator.getMoveValue(m, b));

            ArrayList<HexMove> moves = moveMap.get(moveVal);
            if (moves == null)
            {
                moves = new ArrayList<HexMove>();
            }
            moves.add(m);
            moveMap.put(moveVal, moves);
        }
        
        Integer[] moveValues = moveMap.keySet().toArray(new Integer[moveMap.size()]);
        Arrays.sort(moveValues);
        
        Integer highest = moveValues[moveValues.length -1];
        
        ArrayList<HexMove> bestMoves = moveMap.get(highest);
        
        int random = (int) ( Math.random() * bestMoves.size() );
        
        return bestMoves.get(random);
    }
}