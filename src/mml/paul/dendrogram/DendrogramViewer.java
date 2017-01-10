package mml.paul.dendrogram;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.PriorityQueue;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.activity.Activity;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tree;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.PolygonRenderer;
import prefuse.render.Renderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.AggregateItem;
import prefuse.visual.AggregateTable;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

@SuppressWarnings("serial")
public class DendrogramViewer extends Display {

    public static final String GRAPH = "graph";
    public static final String NODES = "graph.nodes";
    public static final String EDGES = "graph.edges";
    public static final String AGGR = "aggregates";
    
    public Visualization mVis = null;
    
    public DendrogramViewer() {
        // initialize display and data
        super(new Visualization("Dendrogram"));
        mVis = this.getVisualization();
        
        AffineTransform at = new AffineTransform();
        
        
        Point2D pt = new Point2D.Double();
        pt.setLocation(100, 0);
        
        //at.translate(, 0);
        //at.transform(pt, pt);
        at.rotate(45*(Math.PI/180.0f));
        at.transform(pt, pt);
        //at.translate(256, 256);
        
        initDataGroups();
                
        // set up the renderers
        // draw the nodes as basic shapes
        Renderer nodeR = new ShapeRenderer(20);
        // draw aggregates as polygons with curved edges
        Renderer polyR = new PolygonRenderer(Constants.POLY_TYPE_CURVE);
        ((PolygonRenderer)polyR).setCurveSlack(0.15f);
        EdgeRenderer edgeR = new EdgeRenderer(Constants.EDGE_TYPE_LINE, Constants.EDGE_ARROW_FORWARD);
        //edgeR.setArrowHeadSize(5, 8);
        
        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeR);
        drf.setDefaultEdgeRenderer(edgeR);
        drf.add("ingroup('aggregates')", polyR);
        mVis.setRendererFactory(drf);
        
        // set up the visual operators
        // first set up all the color actions
        ColorAction nStroke = new ColorAction(mVis, NODES, VisualItem.STROKECOLOR);
        nStroke.setDefaultColor(ColorLib.gray(100));
        nStroke.add("_hover", ColorLib.gray(50));
        
        ColorAction nFill = new ColorAction(mVis, NODES, VisualItem.FILLCOLOR);
        nFill.setDefaultColor(ColorLib.gray(255));
        nFill.add("_hover", ColorLib.gray(200));
        
        ColorAction nEdges = new ColorAction(mVis, EDGES, VisualItem.STROKECOLOR);
        nEdges.setDefaultColor(ColorLib.gray(100));
        
        ColorAction edgeFillColor = new ColorAction(mVis, EDGES, VisualItem.FILLCOLOR, ColorLib.gray(0));
        
        ColorAction aStroke = new ColorAction(mVis, AGGR, VisualItem.STROKECOLOR);
        aStroke.setDefaultColor(ColorLib.gray(200));
        aStroke.add("_hover", ColorLib.rgb(255,100,100));
        
        int[] palette = new int[] {
            ColorLib.rgba(255,200,200,150),
            ColorLib.rgba(200,255,200,150),
            ColorLib.rgba(200,200,255,150)
        };
        ColorAction aFill = new DataColorAction(mVis, AGGR, "id", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
        
        // bundle the color actions
        ActionList colors = new ActionList(mVis);
        colors.add(nStroke);        
        colors.add(nFill);
        colors.add(edgeFillColor);
        colors.add(nEdges);        
        colors.add(aStroke);
        colors.add(aFill);
        
        // now create the main layout routine
        ActionList layout = new ActionList(mVis, Activity.INFINITY);
        layout.add(colors);
        //layout.add(new ForceDirectedLayout(mVis, GRAPH, true));
        //layout.add(new AggregateLayout(mVis, AGGR));
        layout.add(new RepaintAction(mVis));
        mVis.putAction("layout", layout);
        
        // set up the display
        setSize(500,500);
        pan(250, 250);
        setHighQuality(true);
        //addControlListener(new AggregateDragControl());
        addControlListener(new ZoomControl());
        addControlListener(new PanControl());
        
        // set things running
        mVis.run("layout");
    }
    
    @SuppressWarnings("unused")
	private void initDataGroups() {
    	PriorityQueue<Distance> q = new PriorityQueue<Distance>();
    	q.add(new Distance(-1, -1, 1));
    	q.add(new Distance(2, 4, 0.83));
    	q.add(new Distance(5, 2, 0.83));
    	q.add(new Distance(6, 4, 0.25));
    	q.add(new Distance(5, 4, 0.00));
    	q.add(new Distance(0, 1, 0.00));
    	q.add(new Distance(5, 6, 0.25));
    	q.add(new Distance(0, 2, 0.83));
    	q.add(new Distance(0, 3, 0.25));
    	q.add(new Distance(2, 1, 0.83));
    	q.add(new Distance(1, 3, 0.83));
    	    
    	Tree tree = new Tree();
    	    	    	
    	Hierarchy root = new Hierarchy();
    	while ( q.size() != 0) {
    		Distance d = q.remove(); 
    		Object s = d.getSource();
    		Object t = d.getTarget();
    		
    		Hierarchy sHierarchy = root.getHierarchy(s);
    		Hierarchy tHierarchy = root.getHierarchy(t);
    		
    		if ( sHierarchy != null & tHierarchy != null ) {
    			if ( sHierarchy.equals(tHierarchy) )
    				System.out.println("????");    			
    		} else if ( sHierarchy != null && tHierarchy == null ) {
    			
    		} else if ( sHierarchy == null && tHierarchy != null ) {
    			
    		} else {
    			Hierarchy newCluster = new Hierarchy();
    			newCluster.addElement(s);
    			newCluster.addElement(t);
    			root.addElement(newCluster);
    		}
    		
    		System.out.println("Pair " + d.getSource() + "_" + d.getTarget() + " : " + d.getDistance());
    	}
    	
    	
        // create sample graph
        // 9 nodes broken up into 3 interconnected cliques
        Graph g = new Graph(true);
        for ( int i=0; i<3; ++i ) {
            Node n1 = g.addNode();
            Node n2 = g.addNode();
            Node n3 = g.addNode();
            g.addEdge(n1, n2);            
            g.addEdge(n1, n3);
            g.addEdge(n2, n3);
        }
        g.addEdge(0, 3);
        g.addEdge(3, 6);
        g.addEdge(6, 0);
        
        g.isDirected();
                
        // add visual data groups
        VisualGraph vg = mVis.addGraph(GRAPH, g);
        mVis.setInteractive(EDGES, null, false);
        mVis.setValue(NODES, null, VisualItem.SHAPE, new Integer(Constants.SHAPE_ELLIPSE));
        
        AggregateTable at = mVis.addAggregates(AGGR);
        at.addColumn(VisualItem.POLYGON, float[].class);
        at.addColumn("id", int.class);
        
        // add nodes to aggregates
        // create an aggregate for each 3-clique of nodes
        Iterator<?> nodes = vg.nodes();
        for ( int i=0; i<3; ++i ) {
            AggregateItem aitem = (AggregateItem)at.addItem();
            aitem.setInt("id", i);
            for ( int j=0; j<3; ++j ) {
                aitem.addItem((VisualItem)nodes.next());
            }
        }
    }
	
	public static void main(String[] argv) {
        JFrame frame = demo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static JFrame demo() {
        DendrogramViewer v = new DendrogramViewer();
        JFrame frame = new JFrame("p r e f u s e  |  d e n d r o g r a m");
        frame.getContentPane().add(v);
        frame.pack();
        return frame;
    }
}
