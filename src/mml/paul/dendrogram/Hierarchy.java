package mml.paul.dendrogram;

import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class Hierarchy {

	private int mID;
	private Object mCreator;
	private Set<Object> mElements = new HashSet<Object>();
	private double mModularity;

	public Hierarchy() {
		mID = -1;
		mModularity = 0;
	}
		
	public void setID(int id) { mID = id; }	
	public int getID() { return mID; }
	
	public void setCreator(int c) { mCreator = c; }
	public Object getCreator() { return mCreator; }
	
	public void addElement(Object o) {
		if ( mElements.size() == 0 )
			mCreator = o;
		mElements.add(o);
	}
	
	public void merge(Hierarchy c) {
		mElements.addAll(c.getElements());
	}
	
	public Set<Object> getElements() {
		return mElements;
	}
	
	public Hierarchy copy() {
		Hierarchy c = new Hierarchy();
		for ( Object o : mElements )
			c.addElement(o);		
		c.setID(mID);
		return c;
	}
	
	public int getElementCount() {
		return mElements.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( !(obj instanceof Hierarchy) )
			return false;
		Hierarchy c = (Hierarchy)obj;
		if ( mElements.size() != c.getElementCount() )
			return false;
		for ( Object o : mElements ) {
			if ( !c.getElements().contains(o)) {
				return false;
			}
		}
		return true; 
	}
	
	public Iterator<Object> iterator() {
		PriorityQueue<Object> pq = new PriorityQueue<Object>();
		pq.addAll(mElements);
		return pq.iterator();
	}

	public boolean contains(Object o) {
		for ( Object e : mElements ) {
			if ( e instanceof Hierarchy ) {
				((Hierarchy)e).contains(o);
			} else {
				if ( e.equals(o) ) {
					return true;
				}
			}
		}
		return mElements.contains(o);
	}

	public Hierarchy getHierarchy(Object o) {
		for ( Object e : mElements ) {
			if ( e instanceof Hierarchy ) {
				return ((Hierarchy)e).getHierarchy(o);
			} else {
				if ( e.equals(o) ) {					
					return this;
				}
			}
		}
		return null;
	}
	
	public void setModularity(double m) {
		mModularity = m;
	}
	
	public double getModularity() {
		return mModularity;
	}

}
