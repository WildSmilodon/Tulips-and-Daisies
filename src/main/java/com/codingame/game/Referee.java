package com.codingame.game;
import java.util.List;
import java.util.Random;

import com.codingame.gameengine.core.AbstractPlayer.TimeoutException;
import com.codingame.gameengine.core.AbstractReferee;
import com.codingame.gameengine.core.GameManager;
import com.codingame.gameengine.core.MultiplayerGameManager;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class Referee extends AbstractReferee {
    @Inject private MultiplayerGameManager<Player> gameManager;
    @Inject private GraphicEntityModule graphicEntityModule;
    @Inject private Provider<Grid> GridProvider;
  
    private Random random;
    private Grid grid;
    private int turnsLeft;
    private Text[] textGold;
    private Text[] textComment;  
    private String[] flowerName = { "daisies", "tulips"}; 
    
    @Override
    public void init() {
        // Initialize random generator
     	random = new Random(gameManager.getSeed());   	    	
     	
     	// Set game parameters
    	int fieldWidth = rndInt(8,16); 
    	int fieldHeight = rndInt(8,16); 
    	int startGold = 100;
    	int maxTurns = 16*16; 
    	
    	int costSoil = 0;
    	int costGrass = 1;
    	int costRocks = 5;
    	int costFlower = 10;
    	
    	int rockPercent = rndInt(5,10); 
    	
    	turnsLeft = maxTurns;
    	
    	gameManager.setFirstTurnMaxTime(1000);
    	gameManager.setTurnMaxTime(50);

    	// Init game       	
    	drawBackground();
    	gameManager.setMaxTurns(maxTurns);
    	grid = GridProvider.get();
    	grid.init(fieldWidth, fieldHeight, costSoil, costGrass, costRocks, costFlower, rockPercent, random);
    	
    	// Init players
     	textGold = new Text[2];
     	textComment = new Text[2];
    	for (Player player : gameManager.getActivePlayers()) {
            player.init(startGold);             
            drawPlayerHUD(player);
        }
    	
    	// Send initial parameters to players
    	for (Player player : gameManager.getActivePlayers()) {
            player.sendInputLine(fieldWidth + " " + fieldHeight);
            player.sendInputLine(costSoil + " " + costGrass + " " + costRocks + " " + costFlower);             
            player.sendInputLine(flowerName[player.getIndex()] + " " + flowerName[getOpponent(player.getIndex())]);
        }
    	
    }

    @Override
    public void gameTurn(int turn) {
    	
    	// Take note of the remaining number of turns
    	turnsLeft = (int) ( gameManager.getMaxTurns() - turn ) / 2 + 1; 
    	

    	// Get player from game manager
    	Player player = gameManager.getPlayer( (turn-1) % gameManager.getPlayerCount());
       	Player opponent = gameManager.getPlayer(getOpponent(player.getIndex()));

       	// Send input to player
       	player.sendInputLine(new Integer(turnsLeft).toString());
        player.sendInputLine(new Integer(player.gold).toString() + " " + new Integer(opponent.gold).toString());
        grid.gridCout(player);

        // Let the player play
        player.execute();

        try {
        
        	// Get players move
        	Action action = player.getAction();
               
        	// Validate move
            if (grid.isValid(action,player.gold)) {
            	
            	// Update players gold
            	player.gold = player.gold - grid.getCost(action);
                drawPlayerGold(textGold[player.getIndex()],player);
                
                // Draw players comment on the screen
                drawPlayerComment(textComment[player.getIndex()],action.comment);
              		
            	// Plant the flower on the grid
                grid.plantFlower(action);
            	           		
            	// Harvest flowers
            	int goldEarned = grid.harvest(action);
            	if (goldEarned > 0) {
                	// Update players gold            		
            		player.gold = player.gold + goldEarned;
                    drawPlayerGold(textGold[player.getIndex()],player);
            	} else {
                	// Draw the move
                	grid.drawPlay(action);
            	}
            	
            	// Set player score
            	player.setScore(player.gold);    
            	
            	// Are we done?
            	if (turnsLeft == 1 & player.getIndex() == 1 ) { endGame(); } 
            	            	
               		              
            } else {
               	throw new InvalidAction( String.format("$%d invalid action.", player.getIndex()) );
            }
             
                          
            } catch (TimeoutException e) {
                gameManager.addToGameSummary(GameManager.formatErrorMessage(player.getNicknameToken() + " timeout!"));
                player.deactivate(player.getNicknameToken() + " timeout!");
                player.setScore(-1);
                endGame();

            } catch (InvalidAction e) {
            	gameManager.addToGameSummary(GameManager.formatErrorMessage(player.getNicknameToken() + " invalid action!"));
                player.deactivate(e.getMessage());
                player.setScore(-1);
                endGame();
            }       
    }
    
    
    
    private void drawBackground() {
    	
        graphicEntityModule.createSprite()
                .setImage("Background.jpg")
                .setAnchor(0);
        graphicEntityModule.createSprite()
                .setImage("bag_of_gold.png")
                .setX(360)
                .setY(530)
                .setAnchor(0.5);
        graphicEntityModule.createSprite()
                .setImage("bag_of_gold_flip.png")
                .setX(1920 - 360)
                .setY(530)
                .setAnchor(0.5);
        graphicEntityModule.createSprite()
        	.setImage("sign_right.png")
        	.setX(250)
        	.setY(300)
        	.setAnchor(0.5);
        graphicEntityModule.createSprite()
        	.setImage("sign_left.png")
        	.setX(1920 - 250)
        	.setY(300)
        	.setAnchor(0.5);

        graphicEntityModule.createSprite()
    		.setImage("tulip.png")
    		.setX(1920 - 250)
    		.setY(750)
    		.setBaseWidth(188)
    		.setBaseHeight(188)
    		.setAnchor(0.5);
        
        graphicEntityModule.createSprite()
    		.setImage("daisy.png")
    		.setX(250)
    		.setY(750)
    		.setBaseWidth(188)
    		.setBaseHeight(188)
    		.setAnchor(0.5);       

    }
    
    private void drawPlayerHUD(Player player) {

        graphicEntityModule.createText(player.getNicknameToken())
        	    .setX(250+(1920-500)*player.getIndex())
        	    .setY(175)
        	    .setZIndex(20)
        	    .setFontSize(50)
        	    .setFillColor(0xffffff)
        	    .setAnchor(0.5);
        
        textGold[player.getIndex()] = graphicEntityModule.createText(new Integer(player.gold).toString())
        	    .setX(360+(1920-720)*player.getIndex())
        	    .setY(425)
        	    .setZIndex(20)
        	    .setFontSize(60)
        	    .setFillColor(0xffffff)
        	    .setAnchor(0.5);
        
        textComment[player.getIndex()] = graphicEntityModule.createText("I love " + flowerName[player.getIndex()] + ".")
        	    .setX(250+(1920-500)*player.getIndex())
        	    .setY(888)
        	    .setZIndex(20)
        	    .setFontSize(40)
        	    .setFillColor(0xffffff)
        	    .setAnchor(0.5);
        
    }

    private void drawPlayerGold(Text text, Player player) {
        text.setText(new Integer(player.gold).toString());
    }    
    
    private void drawPlayerComment(Text text, String s) {
        text.setText(s);
    }    
    
    private int rndInt(int min, int max) {
    	return random.nextInt(max - min + 1) + min;
    }
       
    private void endGame() {
        gameManager.endGame();
        
        Player p0 = gameManager.getPlayers().get(0);
        Player p1 = gameManager.getPlayers().get(1);
        String names[] = new String[2];
        int scores[] = new int[2];
        if (p0.getScore() > p1.getScore()) {
            names[0] = p0.getNicknameToken();
            names[1] = p1.getNicknameToken();
            scores[0] = p0.getScore();
            scores[1] = p1.getScore();
        } else {
            names[0] = p1.getNicknameToken();
            names[1] = p0.getNicknameToken();
            scores[0] = p1.getScore();
            scores[1] = p0.getScore();
        } 
        
        new EndGameView(graphicEntityModule, names, scores);
   }
    
    private int getOpponent(int i) {
    	if (i==0) {
    		return 1;
    	} else {
    		return 0;
    	}
    }

    
}
