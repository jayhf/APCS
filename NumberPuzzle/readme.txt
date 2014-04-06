/**********************************************************************
 *  8-Puzzle readme.txt template
 **********************************************************************/

Name: Jay Fleischer

/**********************************************************************
 *  Explain briefly how you implemented Board.java.
    What methods (if any) did you add to the Board API? Why?
 **********************************************************************/
This is in AbstractBoard, a superclass of Board:
I added a getSize method, to make it easier to get the size of the board
I added a hashCode method, because you should always write a hashCode when writing an Equals method
I wrote an isSolvable method to check if the board is able to be solved
I wrote an isSolved method to make it easier to see if the board was already solved
I added a jayHeuristic to calculate my own heuristic value
I wrote a move method that creates a new board, with the empty spot moved by a certain amount
I added a toArray method to allow reference boards to be converted to regular boards
/**********************************************************************
 *  Explain briefly how you represented states in the game
*   (board + number of moves + previous state).
 **********************************************************************/
There are two types of AbstractBoard, Board and ReferenceBoard.
Boards store an array of numbers and ReferenceBoards store the modifications made since the previous Board.
This improves the efficiency of my algorithm.
Each board also stores the number of moves.
The previous state is not stored in the boards (except ReferenceBoards), but is instead kept track of
with a HashMap in the solver.


/**********************************************************************
 *  Explain briefly how you detected unsolvable puzzles <if you did>
 **********************************************************************/
I tried to think of how to detect unsolvable puzzles, for several days, but in the end gave up and went to
a wikipedia page on the problem and found the solution. I attempted to write the method and thought it worked,
but it has trouble with some boards, so it is not used in the solver.

/**********************************************************************
 *  For each of the following instances, give the minimal number of 
 *  moves to reach to goal state. Also give the amount of time
 *  it takes the A* heuristic with the Hamming and Manhattan
 *  priority functions to find the solution. If it can't find the
 *  solution in a reasonable amount of time (say 5 minutes) indicate
 *  that instead.
 **********************************************************************/

                  number of          seconds
     instance       moves      Hamming     Manhattan
   ------------  ----------   ----------   ----------
   puzzle28.txt		28			.27				.015
   puzzle30.txt		30			.30				.020
   puzzle32.txt		32			-				.194
   puzzle34.txt		34			-				.072
   puzzle36.txt		36			-				.420
   puzzle38.txt		38			-				.448
   puzzle40.txt		40			-				.165
   puzzle42.txt		44			-				5.9



/**********************************************************************
 *  If you wanted to solve random 4-by-4 or 5-by-5 puzzles, which
 *  would you prefer:  more time (say 10x as much), more memory (say
 *  (10x as much), or a better priority function. Why?
 **********************************************************************/
I'd prefer more memory, because on huge random boards that I've created,
I run out of ram quickly. (if I remember correctly)



/**********************************************************************
 *  Known bugs / limitations.
 **********************************************************************/
isSolvable doesn't work all of the time
puzzle42 should be solveable in 42 moves, but my shortest solution is 44.
I think my algorithm is slower than it used to be, especially with hamming,
even after I fixed it not finding the fastest solution.

/**********************************************************************
 *  Describe any serious problems you encountered.                    
 **********************************************************************/
I found that I had made a mistake that had made my algorithm very fast,
but not find the fastest solution.



/**********************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **********************************************************************/
I learned that -Xprof can be very useful for optimizing code and what A* is.
The lab was enjoyable, although it started getting a bit boring after a while.