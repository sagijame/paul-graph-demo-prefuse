package prefuse.action.animate;

import prefuse.Visualization;
import prefuse.action.ItemAction;
import prefuse.visual.VisualItem;


/**
 * Animator that linearly interpolates between two positions. This
 * is useful for performing animated transitions.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class LocationAnimator extends ItemAction {

    /**
     * Create a new LocationAnimator that processes all data groups.
     */
    public LocationAnimator(Visualization vis) {
        super(vis);
    }   
    
    /**
     * Create a new LocationAnimator that processes the specified group.
     * @param group the data group to process.
     */
    public LocationAnimator(Visualization vis, String group) {
        super(vis, group);
    }

    /**
     * @see prefuse.action.ItemAction#process(prefuse.visual.VisualItem, double)
     */
    public void process(VisualItem item, double frac) {
        double sx = item.getStartX();
        double sy = item.getStartY();
        item.setX(sx + frac*(item.getEndX()-sx));
        item.setY(sy + frac*(item.getEndY()-sy));
    }

} // end of class LocationAnimator
