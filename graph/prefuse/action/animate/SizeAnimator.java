package prefuse.action.animate;

import prefuse.Visualization;
import prefuse.action.ItemAction;
import prefuse.visual.VisualItem;

/**
 * Animator that linearly interpolates the size of a VisualItems.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class SizeAnimator extends ItemAction {

    /**
     * Create a new SizeAnimator that processes all data groups.
     */
    public SizeAnimator(Visualization vis) {
        super(vis);
    }
    
    /**
     * Create a new SizeAnimator that processes the specified group.
     * @param group the data group to process.
     */
    public SizeAnimator(Visualization vis, String group) {
        super(vis, group);
    }

    /**
     * @see prefuse.action.ItemAction#process(prefuse.visual.VisualItem, double)
     */
    public void process(VisualItem item, double frac) {
        double ss = item.getStartSize();
        item.setSize(ss + frac*(item.getEndSize() - ss));       
    }

} // end of class SizeAnimator
