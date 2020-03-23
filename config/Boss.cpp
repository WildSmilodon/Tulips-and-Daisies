#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

int daisy = 0;
int tulip = 1;
int soil = 2;
int rocks = 3;
int grass = 4;
int costs[5];

int fibonacci(int n) {

    if (n<=0) { 
        return 0; 
    } else {
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
	
}

int smaller(int a, int b) {
    	if (a>b)  {
    		return b;
    	} else  {
    		return a;
    	}
}


class Action {
public:
    int row;
    int col;
    int flower;

    Action(int r, int c, int f) { row = r; col = c; flower=f; }  // constructor
	Action() { row = -1; col = -1; flower = -1; } // default constructor

    void printCout() {
        cout << row << " " << col << endl; 
    }

    int manhattan(int r, int c) {
        return abs(r-row)+abs(c-col);
    }
};

/**
 * Have more gold than your opponent!
 **/

class Map {

public:
    int tile[16][16];
    int fieldWidth; // The width of the playing field
    int fieldHeight; // The height of the playing field

    int earnedGold(int row, int col) {

        int flower = tile[row][col];

        int flowersToHarvest = 0;
        // north - south
        int flowersInLine = 1;
        for (int r = row - 1 ; r > -1; r--) { if (tile[r][col] == flower) {flowersInLine++;} else { break; } }
        for (int r = row + 1; r < fieldHeight; r++) { if (tile[r][col] == flower) {flowersInLine++;} else { break; } }
        if (flowersInLine >= 4) {flowersToHarvest = flowersToHarvest + flowersInLine; }

        // east - west
        flowersInLine = 1;
        for (int c = col - 1; c > -1; c--) { if (tile[row][c] == flower) {flowersInLine++;} else { break; } }
        for (int c = col + 1; c < fieldWidth; c++) { if (tile[row][c] == flower) {flowersInLine++;} else { break; } }
        if (flowersInLine >= 4) {flowersToHarvest = flowersToHarvest + flowersInLine; }

        int n;
    	// CONSIDER SW - NE DIAGONAL
        flowersInLine = 1;
       	n = smaller(fieldHeight - 1 - row, col);
    	for (int i = 0; i<n; i++) { if ( tile[row + (i+1)][col - (i+1)]==flower) { flowersInLine++; } else { break; } } // south west    	   	
    	n = smaller(fieldWidth - 1 - col, row);
    	for (int i = 0; i<n; i++) { if ( tile[row - (i+1)][col + (i+1)]==flower) { flowersInLine++; } else { break; } } // north east
        if (flowersInLine >= 4) {flowersToHarvest = flowersToHarvest + flowersInLine; }


    	// CONSIDER NW - SE DIAGONAL
        flowersInLine = 1;
       	n = smaller(row, col);
    	for (int i = 0; i<n; i++) { if ( tile[row - (i+1)][col - (i+1)]==flower) { flowersInLine++; } else { break; } } // south west    	   	
    	n = smaller(fieldWidth - 1 - col, fieldHeight - 1 - row);
    	for (int i = 0; i<n; i++) { if ( tile[row + (i+1)][col + (i+1)]==flower) { flowersInLine++; } else { break; } } // north east
        if (flowersInLine >= 4) {flowersToHarvest = flowersToHarvest + flowersInLine; }

        return fibonacci(flowersToHarvest); 

    }

    int plantFlower(Action a) {
        int score = 0;

        int centerRow = (int) fieldHeight / 2;
        int centerCol = (int) fieldWidth / 2;
        
        score = score - a.manhattan(centerRow,centerCol);
        score = score - 10 * costs[tile[a.row][a.col]];

        tile[a.row][a.col]=a.flower;

        score = score + 100 * earnedGold(a.row,a.col);

        return score;
    }
};

Map createCopy(Map map) {
    Map m;
    m.fieldWidth = map.fieldWidth;
    m.fieldHeight = map.fieldHeight;

    for (int r = 0; r < m.fieldHeight; r++) {            
        for (int c = 0; c < m.fieldWidth; c++) {
            m.tile[r][c]=map.tile[r][c];
        }
    }
    return m;
}

class Game {
public:
    Map map;
      
    int yourGold; // Your current <gold>
    int opponentGold; // Opponents current <gold>
    int turnsLeft;

    string yourFlowers; // type of flowers you plant (tulips or daisies)
    string opponentsFlowers; // type of flowers you plant (tulips or daisies)

    int myFlower;
    int opponentsFlower;

    void readInit() {
        cin >> map.fieldWidth >> map.fieldHeight; cin.ignore();
        cin >> costs[soil] >>  costs[grass] >> costs[rocks] >> costs[tulip]; cin.ignore();
        costs[daisy] = costs[tulip];
        cin >> yourFlowers >> opponentsFlowers; cin.ignore();   
        if (yourFlowers=="daisies")  { myFlower=daisy; opponentsFlower=tulip;  }
        if (yourFlowers=="tulips")  { myFlower=tulip; opponentsFlower=daisy; }
    }

    void readTurn() {
        cin >> turnsLeft; cin.ignore();
        cin >> yourGold >> opponentGold; cin.ignore();
        for (int r = 0; r < map.fieldHeight; r++) {            
            string gridLine;
            cin >> gridLine; cin.ignore();
            for (int c = 0; c < map.fieldWidth; c++) {
                string letter = gridLine.substr(c,1);
                if (letter=="D") { map.tile[r][c] = daisy; }
                if (letter=="T") { map.tile[r][c] = tulip; }
                if (letter=="R") { map.tile[r][c] = rocks; }
                if (letter=="S") { map.tile[r][c] = soil; }
                if (letter=="G") { map.tile[r][c] = grass; }
            }
        }
    }



    Action getAction() {
        Action best;
        int goldEarned = 0;
        int bestScore = -100000;

        for (int r = 0; r < map.fieldHeight; r++) {            
            for (int c = 0; c < map.fieldWidth; c++) {
                Action move(r,c,myFlower);
                Map copyMap = createCopy(map);
                int score = copyMap.plantFlower(move);
                if (score > bestScore) { best = move; bestScore = score; }
            }
        }

        return best;
    }

};


int main()
{
    Game game;
    game.readInit();

    // game loop
    while (1) {
        game.readTurn();
        Action action = game.getAction();
        action.printCout();
    }
}