import java.util.Scanner;

public class Agent1 {
	
	private static int[][] grid; 
	private static int soil = 5;
	private static int grass = 1;
	private static int rocks = 2;
	private static int myFlower = 3;
	private static int hisFlower = 4;
	private static String soilLetter = "S";
	private static String grassLetter = "G";
	private static String rocksLetter = "R";
	private static String myFlowerLetter = "";
	private static String hisFlowerLetter = "";
	private static String[] flowerName = { "daisies", "tulips"}; 
	
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read grid size
        String input = scanner.nextLine();
        String[] inputs = input.split(" ");
        int fieldWidth = (int) Integer.parseInt(inputs[0]);
        int fieldHeight = (int) Integer.parseInt(inputs[1]);
        grid = new int[fieldHeight][fieldWidth];

        // Read costs
        input = scanner.nextLine();
        inputs = input.split(" ");
        int costSoil = (int) Integer.parseInt(inputs[0]);
        int costGrass  = (int) Integer.parseInt(inputs[1]);
        int costRocks = (int) Integer.parseInt(inputs[2]);
        int costFlower = (int) Integer.parseInt(inputs[3]);
        
        // Read flower names
        input = scanner.nextLine();
        inputs = input.split(" ");
        String myFlowerName = inputs[0];
        String hisFlowerName = inputs[1];
        if (myFlowerName.contentEquals(flowerName[0])) { myFlowerLetter = "D"; hisFlowerLetter = "T"; } 
        if (myFlowerName.contentEquals(flowerName[1])) { myFlowerLetter = "T"; hisFlowerLetter = "D"; } 
        
        while (true) {
        	
        	// Read turns left
            input = scanner.nextLine();
            int turnsLeft = (int) Integer.parseInt(input);
            
            // Read gold amounts
            input = scanner.nextLine(); 
            inputs = input.split(" ");
            int myGold = (int) Integer.parseInt(inputs[0]);
            int hisGOld  = (int) Integer.parseInt(inputs[1]);
            
            
            // Read in grid
            for (int r = 0; r < fieldHeight; r++) {
            
            	input = scanner.nextLine();
                for (int c = 0; c < input.length(); c++) {
                	String s = input.substring(c,c+1);                 
                	if (s.contentEquals(soilLetter)) { grid[r][c] = soil; }
                	if (s.contentEquals(grassLetter)) { grid[r][c] = grass; } 
                	if (s.contentEquals(rocksLetter)) { grid[r][c] = rocks; } 
                	if (s.contentEquals(myFlowerLetter)) { grid[r][c] = myFlower; }
                	if (s.contentEquals(hisFlowerLetter)) { grid[r][c] = hisFlower; }
                }
            }

            int row = randomWithRange(0,fieldHeight-1);
            int col = randomWithRange(0,fieldWidth-1);            
            
            for (int r = 1; r < fieldHeight-1; r++) {               
                for (int c = 1; c < fieldWidth-1; c++) {  
                	if (grid[r][c] == myFlower) {                		
                		boolean done = false;

                		if (!done & grid[r][c+1] == soil) { done = true; row=r; col=c+1;  }
                		if (!done & grid[r+1][c] == soil) { done = true; row=r+1; col=c;  }
                		if (!done & grid[r-1][c] == soil) { done = true; row=r-1; col=c;  }
                		if (!done & grid[r][c-1] == soil) { done = true; row=r; col=c-1;  }
                		
                		if (!done & grid[r+1][c] != myFlower & grid[r+1][c] != hisFlower) { done = true; row=r+1; col=c;  }
                		if (!done & grid[r-1][c] != myFlower & grid[r+1][c] != hisFlower) { done = true; row=r-1; col=c;  }
                		if (!done & grid[r][c+1] != myFlower & grid[r+1][c] != hisFlower) { done = true; row=r; col=c+1;  }
                		if (!done & grid[r][c-1] != myFlower & grid[r+1][c] != hisFlower) { done = true; row=r; col=c-1;  }
                	}
                }
            }
            
            System.out.println(row + " " + col + " (" + row + "," + col + ")");
        }
    }
    
    private static int randomWithRange(int min, int max)
    {
       int range = (max - min) + 1;     
       return (int)(Math.random() * range) + min;
    }
}
