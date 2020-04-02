package com.codingame.game;
import com.codingame.gameengine.core.AbstractMultiplayerPlayer;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.google.inject.Inject;

public class Player extends AbstractMultiplayerPlayer {
	 @Inject private MultiplayerGameManager<Player> gameManager;
	public int gold;
	
	@Override 
    public int getExpectedOutputLines() {
        // Returns the number of expected lines of outputs for a player
        return 1;
    }
    
    public Action getAction() throws TimeoutException, NumberFormatException {
        
    	        
        	String[] output = getOutputs().get(0).split(" ");
            String comment = "";
    	        
            if (output.length > 2) {
            	for (int i = 2; i < output.length; i++) {   
            		comment = comment + output[i] + " ";
            	}
            }

            try
            {
               int row = Integer.parseInt(output[0]);
               int col = Integer.parseInt(output[1]);
       
               return new Action(this, row, col, comment);       	        
            }
            catch (NumberFormatException ex)
            {	        	
            }    	        	
            return new Action(this, -999,-999,"");   
    }    
    
    public void init(int g) {
    	gold = g;
    }
}
