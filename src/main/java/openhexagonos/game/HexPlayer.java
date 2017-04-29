/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openhexagonos.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import openhex.game.board.Board;
import openhex.game.board.owner.Ownable;
import openhex.game.board.owner.Owner;

/**
 *
 * @author b0rg3rt
 */
public class HexPlayer implements Owner {
    
	public enum Faction {
		CELL, BACTERIA;
	}
 
	private Faction faction;
	private String name;
    protected boolean human = true;
    private int points = 0;
    private HashSet<Ownable> property = new HashSet<Ownable>();

    public HexPlayer(String name, Faction type) {
        this.name = name;
        this.faction = type;
    }
    
    public Faction getFaction() {
		return faction;
	}

	public String getName() {
        return name;
    }

    public boolean isHuman() {
        return human;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    public void incrementPoints() {
        points = points + 1;
    }
    
    public void decrementPoints() {
        points = points - 1;
    }
    
    
    public HexMove getNextHexMove(Board b, HexPlayer opponent) {
    	return null;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + (this.human ? 1 : 0);
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
        final HexPlayer other = (HexPlayer) obj;
        if (this.human != other.human) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.faction != other.getFaction()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HexPlayer{" + "name=" + name + ", human=" + human + ", faction=" + faction + ", points=" + points + '}';
    }

	@Override
	public void addOwnable(Ownable ownable) {
		property.add(ownable);
	}

	@Override
	public void removeOwnable(Ownable ownable) {
		property.remove(ownable);
	}

	@Override
	public Set<Ownable> getOwnables() {
		return property;
	}

    
}
