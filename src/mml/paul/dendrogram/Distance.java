package mml.paul.dendrogram;

public class Distance implements Comparable<Distance> {

	private String pairName;
	
	private Object source = -1;	
	private Object target = -1;
	
	private double distance;
	
	/** 
	 * @param source source object
	 * @param target target object
	 * @param distance distance between source and target
	 */
	public Distance(Object source, Object target, double distance) {
		this.source = source;
		this.target = target;
		this.distance = distance;
		
		//if ( source < 0 || target < 0 )
		//	return;		
		
		//if ( source < target )
		//	pairName = String.valueOf(source) + "_" + String.valueOf(target);
		//else
		//	pairName = String.valueOf(target) + "_" + String.valueOf(source);		
	}
	
	public Distance copy() {
		Distance ld = new Distance(source, target, distance);
		return ld;
	}
	
	public String getPairName() {
		return pairName;
	}

	public void setPairName(String pairName) {
		this.pairName = pairName;
	}


	public Object getSource() {
		return source;
	}


	public void setSource(int source) {
		this.source = source;
	}


	public Object getTarget() {
		return target;
	}


	public void setTarget(int target) {
		this.target = target;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}


	@Override
	public int compareTo(Distance ld) {
		if (this.distance > ld.distance) {
			return 1;
		} else if (this.distance < ld.distance) {
			return -1;
		}
		return 0;
	}
		
}
