import java.util.Scanner;
import java.util.Random;

public class MineSweeper {
    private int numrows, numcols, numcells, nummines;
    private Cell[][] cells;

    public MineSweeper(int col, int row) {
        this.numrows = row;
        this. numcols = col;
        this.numcells = numrows*numcols;
        this.nummines = 0;
        this.cells = new Cell[numcols][numrows];
        Random random = new Random();

        for(int i = 0; i<numcols; i++){
            for(int j = 0; j<numrows; j++){
                cells[i][j] = new Cell(i,j);            //initializes each cell
                int temp = random.nextInt(10);

                if(temp <= 2){                          //randomly sets mines
                    cells[i][j].setMined(true);
                    nummines++;
                }
            }
        }

        for(int i = 0; i<numcols; i++){                 //sets the adjcounts
            for(int j = 0; j<numrows; j++){
                getAdjMineCount(i,j);
            }
        }
    }
    public int getAdjMineCount(int i, int j) {
        int direction = 0;
        int mineCount = 0;

        while(direction < 8){
            if(i <= numcols && j <= numrows && i >= 0 && j >= 0);{
                Cell tempCell = getAdjCell(i, j, direction);

                if(tempCell != null && tempCell.getMined() == true){
                    mineCount++;
                }
            }

            direction++;
        }

        cells[i][j].setAdjCount(mineCount);     //setting to null

        return mineCount;
    }
    public int getMarkCount() {
        int markCount = 0;

        for(int i = 0; i < numcols; i++){
            for(int j = 0; j < numrows; j++){
                if(cells[i][j].getMarked() == true){
                    markCount++;
                }
            }
        }

        return markCount;
    }
    public void show() {
        System.out.println();
        System.out.println(" Status : " + getMarkCount() + "/" + nummines);
        System.out.print("     ");
        if(numcols>10){
            System.out.print("");
        }

        for(int i = 0; i < numrows; i++){                   //First row of #
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.print("     ");

        for(int i = 0; i < numrows*2 + 1; i++){             //Top borders
            System.out.print("_");
        }

        System.out.println();

        for(int i = 0; i < numcols; i++){
            System.out.print(" " + i + " | ");              //vertical borders

            for(int j = 0; j < numrows; j++){
                cells[i][j].show();                         //cells
                System.out.print(" ");
            }

            System.out.println(" | " + i);                  //vertical border
        }

        System.out.print("     ");

        for(int i = 0; i < numrows*2 + 1; i++){             //bottom borders
            System.out.print("_");
        }

        System.out.println();
        System.out.print("     ");

        for(int i = 0; i < numrows; i++){
            System.out.print(i + " ");
        }

        System.out.println();

    }
    public void uncoverAll() {
        for(int i = 0; i < numcols; i++){
            for(int j = 0; j < numrows; j++){
                cells[i][j].setUncovered();
            }
        }
    }
    public Cell getAdjCell(int c, int r, int direction) {
        Cell tempCell = null;

        if(direction == 0){
            if(c<=numcols  && c>0 && r>0 && r<=numrows) {
                tempCell = cells[c - 1][r - 1];                      //top left
            }
        }
        else if(direction == 1){
            if(c<=numcols  && c>0 && r>=0 && r<=numrows) {
                tempCell = cells[c - 1][r];                         //left
            }
        }
        else if(direction == 2){
            if(c<=numcols  && c>0 && r>=0 && r<numrows-1) {
                tempCell = cells[c - 1][r + 1];                  //bottom left
            }
        }
        else if(direction == 3){
            if(c<=numcols && c>=0 && r<=numrows && r > 0) {
                tempCell = cells[c][r - 1];                         //up
            }
        }
        else if(direction == 4){
            if(c<=this.numcols  && c>=0 && r>=0 && r<this.numrows-1){ //down
                tempCell = this.cells[c][r + 1];
            }
        }
        else if(direction == 5){
            if(c<numcols-1  && c>=0 && r>0 && r<=numrows){
                tempCell = cells[c+1][r-1];                     //top right
            }
        }
        else if(direction == 6){
            if(c<numcols-1  && c>=0 && r>=0 && r<=numrows) {
                tempCell = cells[c + 1][r];                     //right
            }
        }
        else if(direction == 7){
            if(c<numcols-1  && c>=0 && r>=0 && r<numrows-1){
                tempCell = cells[c+1][r+1];                     //bottom right
            }
        }

        return tempCell;
    }

    public boolean allNonMinesUncovered() {
        boolean win = true;

        for(int i = 0; i < numcols; i++){
            for(int j = 0; j < numrows; j++){
                if(cells[i][j].getMined() == true
                        && cells[i][j].getCovered() == true){
                    win = false;
                }
            }
        }

        return win;
    }
    public boolean allMinesMarked() {
        boolean win = true;

        for(int i = 0; i < numcols; i++){
            for(int j = 0; j < numrows; j++){
                if(cells[i][j].getMined() == true
                        && cells[i][j].getMarked() == false){
                    win = false;
                }
            }
        }

        return win;
    }
    public void game() {
        Scanner scan = new Scanner(System.in);

        boolean exit = false;
        do{
            System.out.println("\nshow - Show the game board");
            System.out.println("u x y - Uncover cell at X Y coordinates");
            System.out.println("m x y - Mark cell at X Y coordinates");
            System.out.println("q - Quit the game");
            String input = scan.nextLine();
            String[] inputSplit = input.split(" ");
            String firstInput = inputSplit[0];

            if (firstInput.equals("u")) {
                int x = -1;             //if invalid number (i.e "-","p") is
                int y = 1;              //entered, try/catch will handle it
                                        // I set these to -1 to prevent
                                        // the following if-statement
                                        // from triggering

                try {
                    x = Integer.parseInt(inputSplit[1]);
                    y = Integer.parseInt(inputSplit[2]);
                }
                catch(NumberFormatException e){
                    System.out.println("Please input a valid number.");
                }
                if(x <= numrows-1 && y <= numcols-1 && x >= 0 && y >= 0){
                    if(cells[y][x].getMarked() == true){
                        //do nothing
                    }
                    else if(cells[y][x].getMined() == true){
                        uncoverAll();
                        show();
                        System.out.println("You lose!");
                        break;
                    }
                    else{
                        cells[y][x].setUncovered();
                        if(allNonMinesUncovered() == true &&
                            allMinesMarked() == true){
                            uncoverAll();
                            show();
                            System.out.println("You win!");
                        }
                    }
                }
                else{
                    System.out.println("Please input valid coordinates.");
                }
            }
            else if (firstInput.equals("m")) {
                int x = Integer.parseInt(inputSplit[1]);
                int y = Integer.parseInt(inputSplit[2]);

                if(x <= this.numrows-1 && y <= this.numcols-1 && x>=0 && y>= 0){
                    if(cells[y][x].getCovered() == false){
                        //do nothing
                    }
                    else if(cells[y][x].getMarked() == true){
                        cells[y][x].setMarked(false);
                    }
                    else{
                        cells[y][x].setMarked(true);

                        if(allNonMinesUncovered() == true
                            && allMinesMarked() == true){
                            uncoverAll();
                            show();
                            System.out.println("You win!");
                        }
                    }
                }
                else{
                    System.out.println("Please input valid coordinates.");
                }
            }
            else if (firstInput.equals("q")) {
                System.out.println("Thanks for playing!");
                exit = true;
            }
            else if (firstInput.equals("show")) {
                show();
            }
            else {
                System.out.println("Invalid command.");
            }
        }while (exit == false);
    }
    public static void play() {             //if time allows, take in user input
        Scanner inp = new Scanner(System.in);
        System.out.println("Please enter the column size of the game board");
        int c = Integer.parseInt(inp.nextLine());
        System.out.println("Please enter the row size of the game board");
        int r= Integer.parseInt(inp.nextLine());
        MineSweeper mine = new MineSweeper(c, r);
        mine.game();
    }
}