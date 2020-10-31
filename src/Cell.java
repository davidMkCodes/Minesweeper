public class Cell {
    private int row, col;
    private boolean covered, marked, mined;
    private int adjcount;

    public Cell(int r, int c) {
        row = r;
        col = c;
        covered = true;
        marked = false;
        mined = false;
        adjcount = 0;
    }
    public void show() {
        if (covered == true && marked == false){
            System.out.print("?");}
        else if (covered == true && marked == true){
            System.out.print("X");}
        else if (covered == false && mined == true){
            System.out.print("*");}
        else if (covered == false && mined == false && adjcount == 0){
            System.out.print("_");}
        else if (covered == false && mined == false && adjcount > 0){
            System.out.print(this.adjcount);}

    }
    public boolean getMined() {
        return this.mined;
    }
    public void setMined(boolean m) {
        this.mined = m;
    }
    public boolean getCovered() {
        return this.covered;
    }
    public void setUncovered() {
        this.covered = false;
    }
    public boolean getMarked() {
        return this.marked;
    }
    public void setMarked(boolean m) {
        this.marked = m;
    }
    public int getAdjCount() {
        return this.adjcount;
    }
    public void setAdjCount(int c) {
        this.adjcount = c;
    }
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }

}
