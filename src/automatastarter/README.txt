ABOUT:
This program is a simulator for Brian's Brain,
a cellular automaton devised by Brian Silverman 
in the mid 1990’s which is similar to Conway's Game of Life. 

RULES:
Each cell has three possible states: on, off, and dying represented by 
red, black and orange respectively. 
Each cell is considered to have eight neighbors (Moore neighborhood). 
The simulation is controlled by a numeric parameter Birth Number, 
default value 2.  

At each simulation step:
- An off cell turns on if it has exactly Birth Number neighbors that are on
- An on cell always turns to dying
- A dying cell always turns off

INSTRUCTIONS:
To start the simulation click the start simulation button. 
The simulation screen displays the grid at the top and 
simulation control parameters underneath.
At the bottom of the screen there are counters for each of the cell states 
as well as a simulation step counter.

CONTROLS:
Start/Stop button: starts or stops a simulation run. 
When a simulation is running the steps are automatically updated 
every number of milliseconds indicated by the speed slider

Step button: when a simulation is not running the step button allows 
the user to advance the simulation step by step

Reset button: resets a simulation by switching all cells to off

Toggle Edit mode button: allows manual editing of the cells. 
When the button is toggled a mouse click in a cell 
will cycle through its possible states

Set Pattern combo box: selecting a preset pattern will reset the simulation 
to the specified pattern.
Patterns are described further below.

Speed Slider: controls the speed in milliseconds of a running simulation. 
Changes are immediately reflected in the simulation

Birth Numbers slider: sets the birthNumber parameter value (2 - 7) inclusive

Wrapping checkbox: if checked the grid wraps around 
both horizontally and vertically

Width and Height fields: control the size of the simulation grid. 
Exceedingly large values are not recommended (above couple hundreds)

Apply Button: only changes in the speed slider are immediately 
reflected in the simulation. All other changes require clicking 
this button to take effect.

PATTERNS:
Randomize: randomly initializes the cells with on or off states

Oscillator: creates a three step oscillator as seen in the main screen

Linear: creates a stable pattern (worm) that moves horizontally

Diagonal: creates a cyclical pattern that moves diagonally

Expanding: creates an expanding pattern that results in interesting simulations
