package com.codingame.game;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Text;
import com.codingame.gameengine.module.entities.Sprite;

public class EndGameView {
    public EndGameView(GraphicEntityModule entityModule, String[] names, int[] scores){
    	
        int xCenter = entityModule.getWorld().getWidth()/2;
        int yCenter = entityModule.getWorld().getHeight()/2;
        Group group = entityModule.createGroup().setX(xCenter).setY(yCenter).setZIndex(1000000000);
        entityModule.createRectangle().setWidth(xCenter*2).setHeight(yCenter*2).setZIndex(1000000).setAlpha(0.8).setFillColor(0x000000).setLineWidth(0);

        Text text;
        
        text = entityModule.createText(names[0] + " has " + scores[0] + " gold.")
        	    .setX(0)
        	    .setY(-50)
        	    .setZIndex(20)
        	    .setFontSize(50)
        	    .setFillColor(0xffffff)
        	    .setAnchor(0.5);
        group.add(text);

        text = entityModule.createText(names[1] + " has " + scores[1] + " gold.")
        	    .setX(0)
        	    .setY(0)
        	    .setZIndex(20)
        	    .setFontSize(50)
        	    .setFillColor(0xffffff)
        	    .setAnchor(0.5);
        group.add(text);

        if (scores[0] == scores[1])  {
            text = entityModule.createText("DRAW!")
            		.setX(0)
            		.setY(120)
            		.setZIndex(20)
            		.setFontSize(80)
            		.setFillColor(0xffffff)
            		.setAnchor(0.5);
        	
        } else {           
        	text = entityModule.createText(names[0] + " WINS!")
        			.setX(0)
        			.setY(120)
        			.setZIndex(20)
        			.setFontSize(80)
        			.setFillColor(0xffffff)
        			.setAnchor(0.5);
        }      

        group.add(text);       
        
        Sprite sprite = entityModule.createSprite()
    	.setImage("logo.png")
    	.setX(0)
    	.setY(-200)
    	.setAnchor(0.5);
        
        group.add(sprite);       
    }
}