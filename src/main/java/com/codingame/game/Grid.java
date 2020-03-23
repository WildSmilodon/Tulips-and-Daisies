package com.codingame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.codingame.gameengine.module.entities.Curve;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Group;
import com.codingame.gameengine.module.entities.Line;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class Grid {
    @Inject private GraphicEntityModule graphicEntityModule;
    @Inject private Provider<Grid> GridProvider;

    
    private int grass = 0;
    private int soil = 1;
    private int rocks = 2;
	private int[] costs = new int[5];
    
    private String[] images = { "grass.png", "soil.png", "rocks.png", "daisy.png", "tulip.png", "ograja_zgoraj_desno.png", "ograja_vodoravno.png", "ograja_zgoraj_levo.png", "ograja_navpicno.png", "ograja_spodaj_levo.png", "ograja_spodaj_desno.png", "coin.png", "empty.png"};
    private String[] letters = { "G", "S", "R", "D", "T" };
    private int fieldWidth;
    private int fieldHeight;
    
    private int cellSize; 
    private int origX; 
    private int origY; 
    private int[][] grid; 
    private Sprite[][] sprites;
    
    private Group entity;
    
    public void init(int fW, int fH, int costSoil, int costGrass, int costRocks, int costFlower, int rockPercent, Random random)  {
    	   	
    	this.entity = graphicEntityModule.createGroup();
    	this.fieldWidth = fW;
    	this.fieldHeight = fH;
    	this.costs[0]= costGrass;
    	this.costs[1]= costSoil;
    	this.costs[2]= costRocks;
    	this.costs[3]= costFlower;
    	this.costs[4]= costFlower;
    	
        cellSize = 960 / larger(fieldWidth+2,fieldHeight+2);
        origX = (int) Math.round( 1920 / 2 - (fieldWidth / 2 - 0.5) * cellSize);
        origY = (int) Math.round( 1080 / 2 - (fieldHeight / 2 - 0.5) * cellSize);
        grid = new int[fieldHeight][fieldWidth];
        sprites = new Sprite[fieldHeight][fieldWidth];

        // Draw initial field
    	 for (int r = 0; r < fieldHeight; r++) {
    		 for (int c = 0; c < fieldWidth; c++) {
    			grid[r][c] = getInitialTile(random, rockPercent);
    		 }
    	 }

         // Draw initial field
     	 for (int r = 0; r < fieldHeight; r++) {
     		 for (int c = 0; c < fieldWidth; c++) {
     			sprites[r][c] = createSprite(r,c,grid[r][c], 1.0);
     			drawSprite(sprites[r][c]);		
     		 }
     	 }    	 
    	 
    	 // Draw fence around the field
    	 drawFence();   	 
    }
    
    private int getInitialTile(Random random, int rockPercent) {
    	
    	int r = random.nextInt(100) + 1;
    	if (rockPercent <= r) {
    		return soil;
    	} else {
    		return rocks;
    	}
    }

    
    public void gridCout(Player player) {

    	for (int r = 0; r < fieldHeight; r++) {
    		String s = "";
    		for (int c = 0; c < fieldWidth; c++) {
    			s = s + letters[grid[r][c]];    			   			
    		}
    		player.sendInputLine(s);
    	}
    }
    
    public int getFieldWidth() { return fieldWidth; }
    public int getFieldHeight() { return fieldHeight; }
    
    public int getCost(Action action) {
    	
    	return costs[grid[action.row][action.col]];
    }
    
    public Boolean isValid(Action action, int gold) {
    	Boolean ok = action.row >= 0 & action.row < fieldHeight & action.col >=0 & action.col <  fieldWidth;
    	if (ok) {
    		int cost = getCost(action);
    		if (gold - cost < 0) { ok = false; }
    	}
    	return ok;
    }
    
    public void plantFlower(Action action) {   	
    	int flower = action.player.getIndex()+3;
    	grid[action.row][action.col]=flower;
    }

    
    
    public void drawPlay(Action action) {    	
    	sprites[action.row][action.col].setImage(images[grid[action.row][action.col]]);
    	drawSprite(sprites[action.row][action.col]);
    }

    
    public int harvest(Action action) {    	
    	int goldEarned = 0;
    	int flower = grid[action.row][action.col];

    	int totalFlowers = 0;
    	int lineFlowers;
    	boolean harvestPossible = false;
    	
    	ArrayList<Integer> allRows=new ArrayList<Integer>();
    	ArrayList<Integer> allCols=new ArrayList<Integer>();

    	ArrayList<Integer> row=new ArrayList<Integer>();
    	ArrayList<Integer> col=new ArrayList<Integer>();

    	// CONSIDER NORTH - SOUTH
    	// south
    	lineFlowers = 1;    	
    	for (int r = action.row + 1; r < fieldHeight; r++) {
    		if ( grid[r][action.col]==flower) {
    			row.add(r);
    			col.add(action.col);
    			lineFlowers++;
    		} else {
    			break;
    		}
    	}
    	// north
    	for (int r = action.row - 1; r > -1; r--) {
    		if ( grid[r][action.col]==flower) {
    			row.add(r);
    			col.add(action.col);    			
    			lineFlowers++;
    		} else {
    			break;
    		}
    	}
    	
    	// Can I harvest in NORTH - SOUTH DIRECTION ?
    	if (lineFlowers >= 4) {
    		harvestPossible = true;
    	    for(Integer r:row) { allRows.add(r); }
    	    for(Integer c:col) { allCols.add(c); }
    	}
    	row.clear();
    	col.clear();
    	
    	
    	// CONSIDER EAST - WEST
    	lineFlowers = 1;
    	// east
    	for (int c = action.col + 1; c < fieldWidth; c++) {
    		if ( grid[action.row][c]==flower) {
    			row.add(action.row);
    			col.add(c);
    			lineFlowers++;
    		} else {
    			break;
    		}
    	}
    	// west
    	for (int c = action.col - 1; c > -1; c--) {
    		if ( grid[action.row][c]==flower) {
    			row.add(action.row);
    			col.add(c);    			
    			lineFlowers++;
    		} else {
    			break;
    		}
    	}
    	   	
    	// Can I harvest in EAST - WEST DIRECTION ?
    	if (lineFlowers >= 4) {
    		harvestPossible = true;
    	    for(Integer r:row) { allRows.add(r); }
    	    for(Integer c:col) { allCols.add(c); }
    	}    	
    	row.clear();
    	col.clear();

    	
    	int n;
    	// CONSIDER SW - NE DIAGONAL
    	lineFlowers = 1;  
    	// south west    	
    	n = smaller(fieldHeight - 1 - action.row, action.col);
    	for (int i = 0; i<n; i++) {
    		int r = action.row + (i+1);
    		int c = action.col - (i+1);
    		if ( grid[r][c]==flower) {
    			row.add(r);
    			col.add(c);    			
    			lineFlowers++;
    		} else {
    			break;
    		}
    	}
    	// north east
    	n = smaller(fieldWidth - 1 - action.col, action.row);
    	for (int i = 0; i<n; i++) {
    		int r = action.row - (i+1);
    		int c = action.col + (i+1);
    		if ( grid[r][c]==flower) {
    			row.add(r);
    			col.add(c);    			
    			lineFlowers++;
    		} else {
    			break;
    		}
    	}
    	
    	// Can I harvest in SW - NE DIAGONAL ?
    	if (lineFlowers >= 4) {
    		harvestPossible = true;
    	    for(Integer r:row) { allRows.add(r); }
    	    for(Integer c:col) { allCols.add(c); }
    	}    	
    	row.clear();
    	col.clear();

    	// CONSIDER NW - SE DIAGONAL
    	lineFlowers = 1;  
    	// north west    	
    	n = smaller(action.row, action.col);
    	for (int i = 0; i<n; i++) {
    		int r = action.row - (i+1);
    		int c = action.col - (i+1);
    		if ( grid[r][c]==flower) {
    			row.add(r);
    			col.add(c);    			
    			lineFlowers++;
    		} else {
    			break;
    		}
    	}
    	// south east
    	n = smaller(fieldWidth - 1 - action.col, fieldHeight - 1 - action.row);
    	for (int i = 0; i<n; i++) {
    		int r = action.row + (i+1);
    		int c = action.col + (i+1);
    		if ( grid[r][c]==flower) {
    			row.add(r);
    			col.add(c);    			
    			lineFlowers++;
    		} else {
    			break;
    		}
    	}
    	
    	// Can I harvest in SW - NE DIAGONAL ?
    	if (lineFlowers >= 4) {
    		harvestPossible = true;
    	    for(Integer r:row) { allRows.add(r); }
    	    for(Integer c:col) { allCols.add(c); }
    	}    	
    	row.clear();
    	col.clear();    	
    	
    	
    	if (harvestPossible) {

	    	// Draw flower
			sprites[action.row][action.col].setImage(images[grid[action.row][action.col]]);
			graphicEntityModule.commitEntityState(0.0, sprites[action.row][action.col]);
			
    		
    		allRows.add(action.row);
    		allCols.add(action.col);
    		totalFlowers = allRows.size();
    		goldEarned = FibonacciN(totalFlowers);
    		for (int i = 0; i < totalFlowers; i++) {   
    			int r = allRows.get(i);
    			int c = allCols.get(i);
   	    	
    			graphicEntityModule.commitEntityState(0.2, sprites[r][c]);    			
    	    	// Draw coins    			
    	    	sprites[r][c].setImage(images[11]);    	    	
    	    	sprites[r][c].setScale(0);
    	    	graphicEntityModule.commitEntityState(0.3, sprites[r][c]);
    	    	sprites[r][c].setScale(1, Curve.ELASTIC);
    	        graphicEntityModule.commitEntityState(0.4, sprites[r][c]);
    	    	
    	        // Animate coins
    	        int x = sprites[r][c].getX();
    	        int y = sprites[r][c].getY();
    	        sprites[r][c].setX(300 + (1920 - 600) * action.player.getIndex() );
    	        sprites[r][c].setY(500);
    	        graphicEntityModule.commitEntityState(0.7, sprites[r][c]);
    	        sprites[r][c].setImage(images[12]); // empty
    	        graphicEntityModule.commitEntityState(0.71, sprites[r][c]);
    	        sprites[r][c].setX(x);
    	        sprites[r][c].setY(y);
    	    	    	        
    		}

    		for (int i = 0; i < totalFlowers; i++) {   
    			int r = allRows.get(i);
    			int c = allCols.get(i);

    			grid[r][c]=grass;
    	    	// Draw grass    	    	
    	    	sprites[r][c].setImage(images[grid[r][c]]);

    	    	sprites[r][c].setScale(0);
    	    	graphicEntityModule.commitEntityState(0.8, sprites[r][c]);
    	    	sprites[r][c].setScale(1, Curve.ELASTIC);
    	        graphicEntityModule.commitEntityState(1, sprites[r][c]);

    		}

    	}
   	
    	
    	return goldEarned;
    }
      
    
    public Sprite createSprite(int row, int col, int type, double scale) {
    	
        Sprite avatar = graphicEntityModule.createSprite()
                .setX(convert(origX, cellSize, col))
                .setY(convert(origY, cellSize, row))
                .setImage(images[type])
                .setBaseWidth((int) (scale * cellSize))
                .setBaseHeight((int) (scale * cellSize))            
                .setAnchor(0.5); 
        
        return avatar;
    	
    }        

    public void drawFence()  {
    	
    	Sprite sprite;
    	// top left
    	sprite =  createSprite(-1,-1, 5, 1.0 );
    	drawSprite(sprite);
    	// horizontal
    	for (int i = 0; i < fieldWidth; i++) {
    		sprite =  createSprite(-1, i, 6, 1.0 );
    		drawSprite(sprite);
    		sprite =  createSprite(fieldHeight, i, 6, 1.0 );
    		drawSprite(sprite);    		
    	}
    	// top right
    	sprite =  createSprite(-1,fieldWidth, 7, 1.0 );
    	drawSprite(sprite);
    	// vertical
    	for (int i = 0; i < fieldHeight; i++) {
    		sprite =  createSprite(i, -1, 8, 1.0 );
    		drawSprite(sprite);
    		sprite =  createSprite(i, fieldWidth, 8, 1.0 );
    		drawSprite(sprite);
    	}
    	// bottom right
    	sprite =  createSprite(fieldHeight,fieldWidth, 9, 1.0 );
    	drawSprite(sprite);
    	// bottom left
    	sprite =  createSprite(fieldHeight,-1, 10, 1.0 );
    	drawSprite(sprite);               	
    }
    
    public void drawSprite(Sprite sprite) {
   
    	sprite.setScale(0);
        graphicEntityModule.commitEntityState(0.2, sprite);
        sprite.setScale(1, Curve.ELASTIC);
        graphicEntityModule.commitEntityState(1.0, sprite);
       
    	
    }

    
    public void drawText(int ind, String s) {
    
        graphicEntityModule.createText(s)
                .setX(333+ind*1333)
                .setY(333)
                .setZIndex(20)
                .setFontSize(40)
                .setFillColor(0xffffff)
                .setAnchor(0.5);
    	
    }
    
    private int convert(int orig, int cellSize, double unit) {
        return (int) (orig + unit * cellSize);
    }   
    
    private int larger(int a, int b) {
    	if (a<b)  {
    		return b;
    	} else  {
    		return a;
    	}
    }

    private int smaller(int a, int b) {
    	if (a>b)  {
    		return b;
    	} else  {
    		return a;
    	}
    }
    
    public int FibonacciN(int n) {
    	int a = 0;
    	int b = 1;
    	int f = 0;
    	int s = 1;
    	for (int i = 0; i < n - 1; i++) {
    		f = a + b;
    		a = b;
    		b = f;
    		s = s + f;
    	}
    	return s;
    }


    
	//Text text = graphicEntityModule.createText(String.valueOf(Math.random()))
    //System.err.println(outputs[0]); // izpise v error od referreja
	//System.err.println(lineFlowers); // izpise v error od referreja   	
}