/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhexagonos.ai.minmax;

import java.util.ArrayList;
import openhex.game.board.Board;
import openhexagonos.game.HexMove;
import openhexagonos.game.HexPlayer;

/**
 *
 * @author b0rg3rt
 */
public class HexMultiStepAiPLayer extends HexPlayer {
    
    private HexStateTreeNode root;
    private int depth = 3;
    
    public HexMultiStepAiPLayer(String name, Faction faction, int depth) {
        super(name, faction);
        this.human = false;
        this.depth = depth;
    }
    
    private void recurse(HexStateTreeNode node, int levels)
    {
        if (levels > 0)
        {
            ArrayList<HexStateTreeNode> children = node.getChildren();
            for (HexStateTreeNode child : children)
            {
                child.generateChildren();
                recurse(child, levels -1);
            }
            
        }
        
    }
    
    @Override
    public HexMove getNextHexMove(Board b, HexPlayer opponent)
    {
        root = new HexStateTreeNode(b, this , opponent, null, false);
        root.generateChildren();
        

        recurse(root,depth);
        ArrayList<HexStateTreeNode> nodes = root.getChildren();

        //System.out.println(root.toString());
        
//        System.out.println("digraph g {");
//        System.out.println(root.dumpTree());
//        System.out.println("}");
        
        int bestMoveVal = Integer.MIN_VALUE;
        HexMove bestMove = null;
        nodes = root.getChildren();
        for(HexStateTreeNode node : nodes)
        {
            int val = node.accumulatePoints(node);
            //System.out.println("int val " + val);
            if (val > bestMoveVal)
            {
                bestMoveVal = val;
                bestMove = node.getMove();
            }
        }
        /*
        System.out.println("digraph g {");
        System.out.println(root.dumpTree());
        System.out.println("}");*/
       // System.out.println("bestMove: " + bestMove);
        return bestMove;
    }
}
