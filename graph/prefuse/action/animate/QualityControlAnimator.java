package prefuse.action.animate;

import prefuse.Visualization;
import prefuse.action.Action;

/**
 * Animator that toggles rendering quality to allow for smooth animations but
 * high quality rendering of still images. At the beginning of an animation,
 * high quality rendering (if enabled) is disabled, and at the end the original
 * setting is restored.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class QualityControlAnimator extends Action {

    public QualityControlAnimator(Visualization vis) {
		super(vis);
	}

	/**
     * @see prefuse.action.Action#run(double)
     */
    public void run(double frac) {
        if ( getVisualization() == null ) return;
        if ( frac == 0.0 || frac == 1.0 ) {
            boolean quality = frac >= 1.0;
            for ( int i=0; i<getVisualization().getDisplayCount(); ++i ) {
                getVisualization().getDisplay(i).setHighQuality(quality);
            }
            qualityValue(quality);
        }
    }
    
    /**
     * Callback procedure that subclasses can override to execute
     * custom quality control measures.
     * @param quality true if high quality is desired, false otherwise
     */
    protected void qualityValue(boolean quality) {
        // do nothing
    }

} // end of class QualityControlAnimator
