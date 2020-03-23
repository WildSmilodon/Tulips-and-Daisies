import java.util.Random;
import java.util.Scanner;

public class Agent2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        System.err.println(input);
        
        String[] inputs = input.split(" ");
        
        int fieldWidth = (int) Integer.parseInt(inputs[0]);
        int fieldHeight = (int) Integer.parseInt(inputs[1]);

        input = scanner.nextLine();
        System.err.println(input);

        input = scanner.nextLine();
        System.err.println(input);
        
        while (true) {
            input = scanner.nextLine();
            System.err.println(input);

            input = scanner.nextLine();
            System.err.println(input); 
                        
            for (int r = 0; r < fieldHeight; r++) {
            
            	input = scanner.nextLine();
                //System.err.println(input);
            
            }
            
            int row = randomWithRange(0,fieldHeight-1);
            int col = randomWithRange(0,fieldWidth-1);
            
            System.out.println(row + " " + col + " (" + row +","+ col + ")");
        }
    }
    
    private static int randomWithRange(int min, int max)
    {
       int range = (max - min) + 1;     
       return (int)(Math.random() * range) + min;
    }
}
