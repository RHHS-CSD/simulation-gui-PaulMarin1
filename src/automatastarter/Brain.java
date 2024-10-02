/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatastarter;

/**
 *
 * @author paul
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


public class Brain {
    // Names for the possible cell states
    public static final int OFF = 0;
    public static final int ON = 1;
    public static final int DYING = 2;
    public static final int NEW = 3;
    
    // Define the possible states for a cell; N is used for cells that will become ON at the end of the iteration
    public static final char[] STATES = {' ', 'o', '*', 'N'};
    
    // Define coordinate changes for all eight Moore neighbors
    private static final int[][] DIRECTIONS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    
    // Initialise variables
    private int height;
    private int width;
    private int birthNumber;
    private boolean wrapping;
    public char[][] cells;
    
    public Brain(int width, int height, int birthNumber, boolean wrapping){
        this.width = width;
        this.height = height;
        this.birthNumber = birthNumber;
        this.wrapping = wrapping;
        cells = new char[height][width];
        clear();
    }
    
    // Randomly populates the simulation grid
    public void randomize() {
        // Iterates through the grid
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                // Assigns the random state not including N, which is only used in the simulation step
                cells[i][j] = STATES[(int)Math.floor(Math.random() * 2)];
            }
        }
    }
    
    // Prints out the grid to the console
    private void display() {
        // Iterate through the grid
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                //print each cell in the row
                System.out.print(" " + cells[i][j]);
            }
            // Create a new line to display the next row of the grid
            System.out.println();
        }
    }
    
    // Moves to the next step of the simulation
    public void update() {
        /* It is not possible to update all cells in one pass through the grid so
            we have to do it in multiple steps
            First step, mark all the cells that should be turned ON with NEW  - 
            this way they do not yet count as ON neighbors
        */
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == ' '){
                    if (countNeighbors(i, j) == birthNumber){
                        // If the cell is OFF and the number of ON neighbors exceeds the birth number we mark it to be turned ON
                        cells[i][j] = STATES[NEW];
                    }
                }
            }
        }
        
        // Update the state of all Cells
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == STATES[ON]){
                    cells[i][j] = STATES[DYING];
                } else if (cells[i][j] == STATES[DYING]) {
                    cells[i][j] = STATES[OFF];
                } else if (cells[i][j] == STATES[NEW]) {
                    // Cells are only turned ON now to prevent miscounting in countNeighbors
                    cells[i][j] = STATES[ON];
                }
            }
        }
    }
    
    // Counts the number of Moore neighbors that are ON
    private int countNeighbors(int i, int j) {
        int count  = 0;
        int ni, nj;
        for (int k = 0; k < DIRECTIONS.length ; k++) {
            // Calculate the coordinates of each and every Moore neighbor
            ni = i + DIRECTIONS[k][0];
            nj = j + DIRECTIONS[k][1];
            if (wrapping){
                if (ni < 0){
                    ni = height - 1;
                }
                
                if (nj < 0){
                    nj = width - 1;
                }
                
                if (ni >= height){
                    ni = 0;
                }
                
                if (nj >= width){
                    nj = 0;
                }
            }
            // Increment the counter
            if (ni >= 0 && ni < height && nj >= 0 && nj < width) {
                if (cells[ni][nj] == STATES[ON]){
                    count ++;
                }
            }
        }
        return count;
    }
    
    // Checks if all cells are turned OFF
    private boolean isDead(){
        boolean dead = true;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] != STATES[OFF]){
                    // If there is any cell that is not OFF we return
                    return false;
                }
            }
        }
        System.out.println("No cells left ON, simulation ended!");
        return dead;
    }
    
    public void clear(){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = STATES[OFF];
            }
        }
    }
    
    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
    
    
    /*  
    // Simulation engine
    public static void main(String[] args) {
        getParameters();
        char[][] brain = new char[height][width];
        generate(brain);
        
        while (true) {
            display(brain);
            
            if (isDead(brain)){
                // if all cells are OFF we no longer prompt the user to continue
                break;
            }

            if (getInput()) {
                break;
            }
            update(brain);
        }
    }
    */
}

