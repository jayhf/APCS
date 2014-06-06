/**********************************************************************
 *  readme.txt template                                                   
 *  Rogue
**********************************************************************/

Name: Jay Fleischer

/**********************************************************************
 *  Describe your monster's strategy for intercepting the rogue.
 **********************************************************************/
My monster finds every path from itself to the rogue. It then takes the
direction it would have to go for each of the paths and adds up the
change in x and y coordinates required for each one. It then tries to go
in the direction of the sum of the relative directions from the current
position. If that doesn't work, it will try to move in just the relative
y direction and then just the relative x direction.

/**********************************************************************
 *  Are there any dungeons (supplied by us, or created by you)
 *  where the monster does not intercept the rogue even though
 *  with an optimal strategy it would? If so, try to describe
 *  why it doesn't work?
 **********************************************************************/
There are no such dungeons in the provided test cases or that I have
created, but it is not completely optimal in that it will fail a properly
created dungeonR (a dungeon where the shortest path is not the best).
However, I think I can prove that creating such a dungeon is impossible,
although I'm not positive.

/**********************************************************************
 *  Describe your rogue's strategy for evading the monster.
 **********************************************************************/
My rogue begins by finding cycles, which it only does on the first move
and is quick on most mazes (takes a few seconds on D).
On every move, if the rogue is next to the monster and is on a cycle, it
will move on the cycle in the direction going away from the monster.
Otherwise it searches for the best site to go to, which is the site that
is the farthest from the monster that the rogue can reach before the monster
or it's current location if this is not possible.

/**********************************************************************
 *  Are there any dungeons (supplied by us, or created by you)
 *  where the rogue does not evade the monster for as long as
 *  possible? If so, try to describe why it doesn't.
 **********************************************************************/
No.

/**********************************************************************
 *  Give the worst case asymptotic running time *per move* of your two
 *  algorithms as a function of the dungeon dimension N, e.g., N^2.
 **********************************************************************/

Monster: The bottleneck is finding every path to the rogue, which depends
a lot on the path arrangements, but I would estimate to be in the range of
O(N^2) in the case with few small rooms to O(N^3) when there are many
large rooms.
Rogue: Finding cycles is a one time operation, so I decided to determine
it's big O cost separately. I would estimate it to be O(N^3) or less, even
though it has four nested loops, because most tiles are removed before this
loop.
For the rest of the code, if the rogue is on a cycle and next to a monster,
each turn is O(1) and otherwise, the limiting factor is path finding, which
as already stated under monster ranges from O(N^2) to O(N^3)


/**********************************************************************
 *  Known bugs / limitations.
 **********************************************************************/
In hindsight, it seems like cycle detection wasn't that helpful and with
a few modifications, I could probably remove it entirely.
/**********************************************************************
 *  List whatever help (if any) that you received.
 **********************************************************************/

/**********************************************************************
 *  Describe any serious problems you encountered.                    
 **********************************************************************/
Both of the student created dungeons, dungeons Q and R are incorrectly
described. In Q, it is impossible for the Rogue to survive with an optimal
Monster. The student assumed that moving diagonally only works when room
cells are part of the same cell. The Monster will never beat an optimal
Rogue in dungeon R, because of the cycle ((2,3),(2,4),(3,4),(3,3)). This
Student assumed the only cycle was the one with four corridors in a square.

/**********************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **********************************************************************/
I really enjoyed the assignment. There were several interesting programming
 challenges I had to solve when creating my Monster and Rogue.