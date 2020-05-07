package hr.fer.zemris.java.raytracer.model;

public class Sphere extends GraphicalObject {
	
	private Point3D center; 
	private double radius; 
	private double kdr; 
	private double kdg; 
	private double kdb; 
	private double krr; 
	private double krg; 
	private double krb;
	private double krn;
	
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		
		Point3D o_c = ray.start.sub(this.center);
		Point3D direction = ray.direction;
		
//		double a = ray.direction.scalarProduct(ray.direction);
//		double b = 2 * ray.direction.scalarProduct(o_c);
//	    double c = Math.pow(o_c.norm(), 2) - Math.pow(this.radius, 2);
		
//	    double underSquare = Math.pow(b, 2) - 4 * c;
		
		double underSquare = Math.pow(direction.scalarProduct(o_c), 2) - 
				(Math.pow(o_c.norm(), 2) - Math.pow(this.radius, 2));
	    
	    if(underSquare < 0) {
	    	
	    	return null;
	    	
	    }else {
	    	
	    	double d1 = -direction.scalarProduct(o_c) + Math.sqrt(underSquare);
	    	double d2 = -direction.scalarProduct(o_c) - Math.sqrt(underSquare);
	    	
	    	Point3D start = ray.start;
	    	
	    	Point3D p1 = new Point3D(start.x + direction.x * d1,
	    			start.y + direction.y * d1,
	    			start.z + direction.z * d1);
	    	
	    	Point3D p2 = new Point3D(start.x + direction.x * d2,
	    			start.y + direction.y * d2,
	    			start.z + direction.z * d2);
	    	
	    	double dist1 = Math.sqrt(Math.pow(start.x - p1.x, 2) + 
	    			Math.pow(start.z - p1.z, 2) + 
	    			Math.pow(start.z - p1.z, 2));
	    	
	    	double dist2 = Math.sqrt(Math.pow(start.x - p2.x, 2) + 
	    			Math.pow(start.z - p2.z, 2) + 
	    			Math.pow(start.z - p2.z, 2));
	    	
	    	Point3D closer;
	    	
	    	if(dist1 < dist2) closer = p1;
	    	else closer = p2;
	    	
	    	return new RayIntersection(closer, dist1, true) {
				
				@Override
				public Point3D getNormal() {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public double getKrr() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public double getKrn() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public double getKrg() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public double getKrb() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public double getKdr() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public double getKdg() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				@Override
				public double getKdb() {
					// TODO Auto-generated method stub
					return 0;
				}
			};
	    }
		
	}

}
