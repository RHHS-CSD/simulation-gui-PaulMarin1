/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatastarter;

/**
 * The data model for Brian's Brain simulation
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
    
    // Define Patterns
    private static final char[][] OSCILLATOR = {{' ', '*', ' ', ' '},
                                                {' ', 'o', 'o', '*'},
                                                {'*', 'o', 'o', ' '}, 
                                                {' ', ' ', '*', ' '}};
    
    private static final char[][] LINEAR = {{' ', ' ', 'o', '*', ' ', ' ', ' ', ' ', ' ', ' '},
                                            {'o', '*', ' ', 'o', '*', ' ', 'o', '*', ' ', ' '},
                                            {'o', '*', ' ', ' ', 'o', '*', ' ', ' ', 'o', '*'}};
    
    private static final char[][] DIAGONAL = {{'*', ' ', ' '},
                                              {'o', ' ', 'o'},
                                              {' ', '*', '*'},
                                              {' ', ' ', 'o'}};
    
    private static final char[][] EXPANDING = {{'o', 'o', ' ', ' ', ' ', ' '},
                                               {' ', '*', 'o', ' ', ' ', ' '},
                                               {' ', ' ', '*', 'o', ' ', ' '},
                                               {' ', ' ', ' ', '*', 'o', ' '},
                                               {' ', ' ', ' ', ' ', '*', 'o'},
                                               {' ', ' ', ' ', ' ', ' ', 'o'}};
    
    // Initialise variables
    private int height;
    private int width;
    private int birthNumber;
    private boolean wrapping;
    public char[][] cells;
    private int onCount;
    private int offCount;
    private int dyingCount;
    private int stepCount;
    
    /**
     * Constructor, creates a brain simulation given the parameter values
     * @param width the width of the grid
     * @param height the height of the grid
     * @param birthNumber the number of neighboring cells that must be on in 
     *                    order an off cell on
     * @param wrapping if true the grid wraps both horizontally and vertically
     */
    public Brain(int width, int height, int birthNumber, boolean wrapping){
        this.width = width;
        this.height = height;
        this.birthNumber = birthNumber;
        this.wrapping = wrapping;
        cells = new char[height][width];
        onCount = 0;
        offCount = 0;
        dyingCount = 0;
        stepCount = 0;
        clear();
    }
    
    /**
     * Initialize the simulation with a random pattern
     */
    public void randomize() {
        // Reset all counters to zero
        onCount = 0;
        offCount = 0;
        dyingCount = 0;
        stepCount = 0;
        // Iterates through the grid
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                // Assigns the random state not including N or DYING
                cells[i][j] = STATES[(int)Math.floor(Math.random() * 2)];
                // Count the number of ON and OFF cells
                if (cells[i][j] == STATES[ON]){
                    onCount++;
                } else {
                    offCount++;
                }
            }
        }
    }
    
    /**
     * Advance the simulation by one step
     */
    public void update() {
        /* It is not possible to update all cells in one pass through the grid so
            we have to do it in multiple steps
            First step, mark all the cells that should be turned ON with NEW  - 
            this way they do not yet count as ON neighbors
        */
        // Reset cell counters to zero
        onCount = 0;
        offCount = 0;
        dyingCount = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == ' '){
                    if (countNeighbors(i, j) == birthNumber){
                        /* If the cell is OFF and the number of ON neighbors is equal to 
                        the birth number we mark it to be turned ON
                        */
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
                    dyingCount++;
                } else if (cells[i][j] == STATES[DYING]) {
                    cells[i][j] = STATES[OFF];
                    offCount++;
                } else if (cells[i][j] == STATES[NEW]) {
                    // Cells are only turned ON now to prevent miscounting in countNeighbors
                    cells[i][j] = STATES[ON];
                    onCount++;  
                } else {
                    offCount++;
                }
            }
        }
        stepCount++;
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
    
    /**
     * Reset all cells to off state
     */
    public void clear(){
        // Reset all counters
        onCount = 0;
        offCount = 0;
        dyingCount = 0;
        stepCount = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = STATES[OFF];
                // Increment the number of off cells accordingly
                offCount++;
            }
        }
    }
    
    /**
     * Cycles through the states of a given cell
     * @param i the cell row
     * @param j the cell column
     */
    public void switchState(int i, int j){
        if (cells[i][j] == Brain.STATES[Brain.OFF]) {
            cells[i][j] = Brain.STATES[Brain.ON];
            onCount++;
            offCount--;
        } else if (cells[i][j] == Brain.STATES[Brain.ON]) {
            cells[i][j] = Brain.STATES[Brain.DYING];
            dyingCount++;
            onCount--;
        } else {
            cells[i][j] = Brain.STATES[Brain.OFF];
            offCount++;
            dyingCount--;
        }  
    }
    
    // Copies a pattern to the center of the grid
    private void copyPattern(char[][] pattern){
        // Calculate top right corner of pattern to be centered
        int row = (height - pattern.length) / 2;
        int column = (width - pattern[0].length) / 2;

        // Iterates through the grid
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[i].length; j++) {
                // Update cells corresponding to the pattern
                cells[row + i][column + j] = pattern[i][j];
            }
        }
    }
    
    /**
     * Initializes the grid with an oscillator pattern
     */
    public void createOscillator() {
        // Make sure grid is clear
        clear();
        
        // Set all counters to correct value
        onCount = 4;
        dyingCount = 4;
        offCount = width * height - onCount - dyingCount;
        stepCount = 0;
        
        copyPattern(OSCILLATOR);
    }
    
     /**
     * Initializes the grid with a static, horizontally moving pattern
     */
    public void createLinear() {
        // Make sure grid is clear
        clear();

        // Set all counters to correct value
        onCount = 7;
        dyingCount = 7;
        offCount = width * height - onCount - dyingCount;
        stepCount = 0;

        copyPattern(LINEAR);
    }
    
    /**
     * Initializes the grid with a cyclical, diagonally moving pattern
     */
    public void createDiagonal() {
        // Make sure grid is clear
        clear();

        // Set all counters to correct value
        onCount = 3;
        dyingCount = 3;
        offCount = width * height - onCount - dyingCount;
        stepCount = 0;

        copyPattern(DIAGONAL);
    }
    
    /**
     * Initializes the grid with an interesting evolving pattern
     */
    public void createExpanding() {
        // Make sure grid is clear
        clear();

        // Set all counters to correct value
        onCount = 7;
        dyingCount = 4;
        offCount = width * height - onCount - dyingCount;
        stepCount = 0;

        copyPattern(EXPANDING);
    }

  
    
    /**
     * Returns the width of the grid
     * @return the width of the grid
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * Returns the height of the grid
     * @return the height of the grid
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Returns the number of cells with off status
     * @return the number of off cells
     */
    public int getOffCount(){
        return offCount;
    }
    
    /**
     * Returns the number of cells with on status
     * @return the number of on cells
     */
    public int getOnCount(){
        return onCount;
    }
    
    /**
     * Returns the number of cells with dying status
     * @return the number of dying cells
     */
    public int getDyingCount(){
        return dyingCount;
    }
    
    /**
     * Returns the current simulation step
     * @return the simulation step
     */
    public int getStepCount(){
        return stepCount;
    }
}

