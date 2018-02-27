# Sudoku
An Android application to solve sudoku puzzles, the algorithm used here follows a constraint method to solve the puzzle.. To input a number, just place your finger on a cell and drag it down. Link to APK: https://goo.gl/7QMzo8

Alright, so this application solves a puzzle in the following ways.

Firstly, after you enter the puzzle and press the smiley,it checks the row, column and the 3x3 square that each empty cell belongs to and sees if there is just one unique number in 0-9 that could fit in the cell, and finalizes the number if found. I feel this is the most basic way one would write a solving algorithm for Sudoku puzzles and so I included it.

Then, it would create data in an array(9x9x9) about which numbers could possibly fill an empty cell, after eliminating all the 
numbers that could not exist ( it finds them out after applying various constraints). If the algorithm finds a single possible number in an empty cell, it places it in there.

This algorithm has a while loop in which the array data is populated and examined. If any one lead could be found in an 
iteration, the loop continues, and if not, the loop terminates and the answers that are found are shown on the screen.

An algorithm using recursive technique is more popular for solving sudoku puzzles but this approach was chosen as it follows the way most people follow to solve a sudoku puzzle. The algoritihm used here to solve the puzzle is not perfect yet and some more constraints can be programmatically added to this to make this solve even the hardest puzzles.
