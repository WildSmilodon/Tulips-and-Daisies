package com.codingame.game;

public class Action {
    public final int row;
    public final int col;
    public final String comment;
    public Player player;
    
    public Action(Player player, int row, int col, String comment) {
        this.player = player;
        this.row = row;
        this.col = col;
        this.comment = comment;
    }
    
    @Override
    public String toString() {
        return row + " " + col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Action) {
            Action other = (Action) obj;
            return col == other.col && row == other.row;
        } else {
            return false;
        }
    }
}