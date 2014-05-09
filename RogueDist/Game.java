import java.util.Scanner;
import java.io.File;
import java.util.*;
/*************************************************************************
 *  Legacy comments from first authors
 *  Compilation:  javac Game.java
 *  Execution:    java Game < input.txt
 *  Dependencies: Dungeon.java Site.java In.java Monster.java Rogue.java
 *
 *  April 2011
 *   J. Smith
 *   -- fixed variable names, dependencies (removed N, this is stored in dungeon)
 *   
 *  May 2012 by J. Smith
 *  Fudge for this year:  Added methods to add a Monster and Rogue to the game
 *  Note:  I really want to undo the relationship between Creatures and Game where
 *  Game has an instance of Creature and vice versa.  AFTER class presentations and
 *  BEFORE distributing next year MAKE THIS CHANGE.
 *  
 *  
 *  May 2013 by J. Smith
 *  - switched to 2013 GraphInterface class from SmallWorld problem
 *  - use a  MoveFinder instead of Creature 
 *  - change moveMonster and moveRogue to moveCreature(Creature x)
 *  
 *  May 2014 by J. Smith
 *   - ?? last year's note 3 above about moveCreature is not accurate
 *   - other notes are in place.
 *************************************************************************/

public class Game {

    // portable newline
    private final static String NEWLINE = System.getProperty("line.separator");

    private Dungeon dungeon;     // the dungeon
    private char monsterDisp;        // name of the monster (A - Z)
    private static final char ROGUE = '@';    // name of the rogue
    private Site monsterSite;    // location of monster
    private Site rogueSite;      // location of rogue
    private MoveFinder monster;     // the monster
    private MoveFinder rogue;         // the rogue
   
    // initialize board from file
    public Game(Scanner in) {

        // read in data
        int size = Integer.parseInt(in.nextLine());
        char[][] board = new char[size][size];
        for (int i = 0; i < size; i++) {
            String s = in.nextLine();
            for (int j = 0; j < size; j++) {
                board[i][j] = s.charAt(2*j);

                // check for monster's location
                if (board[i][j] >= 'A' && board[i][j] <= 'Z') {
                    monsterDisp = board[i][j];
                    board[i][j] = '.';
                    monsterSite = new Site(i, j);
                }

                // check for rogue's location
                if (board[i][j] == ROGUE) {
                    board[i][j] = '.';
                    rogueSite  = new Site(i, j);
                }
            }
        }
        dungeon = new Dungeon(board);
        //monster = new JSMonster(this);
        //System.out.println("Completed creating monster");
        //rogue   = new JSRogue(this);
        //System.out.println("Completed creating rogue");
        monster = null;
        rogue = null;
        System.out.println("Completed constructing dungeon");
    }

    // return position of monster and rogue
    public Site getMonsterSite() { return monsterSite; }

    public Site getRogueSite()   { return rogueSite;   }

    public Dungeon getDungeon()  { return dungeon;     }

    // play until monster catches the rogue
    public void play() {
        Scanner user = new Scanner(System.in);
        for (int t = 1; true; t++) {
            System.out.println("Move " + t);
            System.out.println();

            // monster moves
            if (monsterSite.equals(rogueSite)) break;
            Site next = monster.move(monsterSite, rogueSite);
            if (dungeon.isLegalMove(monsterSite, next)) monsterSite = next;
            else throw new RuntimeException("Monster caught cheating");
            System.out.println(this);

            // rogue moves
            if (monsterSite.equals(rogueSite)) break;
            next = rogue.move(monsterSite, rogueSite);

            if (dungeon.isLegalMove(rogueSite, next)) rogueSite = next;
            else throw new RuntimeException("Rogue caught cheating");
            System.out.println(this);
            user.nextLine();
        }

        System.out.println("Caught by monster");

    }

    /**
     * Add by J. Smith
     * May 2012
     * Methods to add monster and rogue to game
     * NOTE:  be sure to call these before moving them!
     * Helps support GUI using ClassLoader to swap out players
     */
    public void addMonster (MoveFinder monster)
    {
        this.monster = monster;
    }
    
    public void addRogue (MoveFinder rogue)
    {
        this.rogue = rogue;
    }
    
    /*
     * May 2012
     * added for GUI to check that a monster and rogue are ready to play
     */
    public boolean readyToStart ()
    {
        return monster != null && rogue != null;
    }
    /*
     * May 2012:  added for GUI play
     */
    public void moveMonster()
    {
        if (monster == null)
            throw new IllegalStateException("You must add a monster to play");
        Site next = monster.move(monsterSite, rogueSite);
        
        if (dungeon.isLegalMove(monsterSite, next)) monsterSite = next;
        else throw new RuntimeException("Monster caught cheating");
    }

    public void moveRogue ()
    {
        if (rogue == null)
            throw new IllegalStateException("You must add a rogue to play");
        Site next = rogue.move(monsterSite, rogueSite);
        if (dungeon.isLegalMove(rogueSite, next)) rogueSite = next;
        else throw new RuntimeException("Rogue caught cheating");
    }
    // string representation of game state (inefficient because of Site and string concat)
    public String toString() {
        String s = "";
        for (int i = 0; i < dungeon.size(); i++) {
            for (int j = 0; j < dungeon.size(); j++) {
                Site site = new Site(i, j);
                if (rogueSite.equals(monsterSite) && (rogueSite.equals(site))) s += "* ";
                else if (rogueSite.equals(site))                               s += ROGUE   + " ";
                else if (monsterSite.equals(site))                             s += monsterDisp + " ";
                else if (dungeon.isRoom(site))                                 s += ". ";
                else if (dungeon.isCorridor(site))                             s += "+ ";
                else if (dungeon.isWall(site))                                 s += "  ";
            }
            s += NEWLINE;
        }
        return s;
    }

    /**
     * Run by entering a file name for arg[0] in the form dungeonX where X is A..Z
     */
    public static void main(String[] args) throws Exception {
        Scanner stdin = new Scanner(new File("../dungeons/" + args[0] + ".txt"));
        Game game = new Game(stdin);
        // uncomment lines below and supply your own monsters and rogues
        //game.addMonster(new JSMonster(game.getDungeon()));
        //game.addRogue(new JSRogue(game.getDungeon()));
        System.out.println(game);
        game.play();
    }

}
