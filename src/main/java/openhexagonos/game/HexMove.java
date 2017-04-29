/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhexagonos.game;

import java.util.Objects;

import openhex.vec.fin.VectorAS;

/**
 *
 * @author b0rg3rt
 */
public class HexMove {
    
    private HexPlayer player;
    private VectorAS from;
    private VectorAS to;

    public HexMove(HexPlayer player, VectorAS from, VectorAS to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public HexPlayer getPlayer() {
        return player;
    }

    public VectorAS getFrom() {
        return from;
    }

    public VectorAS getTo() {
        return to;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.player);
        hash = 97 * hash + Objects.hashCode(this.to);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HexMove other = (HexMove) obj;
        if (!Objects.equals(this.player, other.player)) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HexMove{" + "player=" + player + ", from=" + from + ", to=" + to + '}';
    }
    
    
}
