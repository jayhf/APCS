
/**
 * 
 * MoveFinder
 * - given a dungeon and graph choose
 * a new site
 * 
 * 2013:
 * renamed from Creature to MoveFinder
 * added getDisplayData 
 * added parameters for monster and rogue
 * 
 * @author J. Smith 
 * @version April 2012, May 2013
 */
public interface MoveFinder
{
    
    Site move (Site monster, Site rogue); // return the Site of the next move
    String getDisplayData (); // return image name (no extension) or "" if no image

}
