package hr.fer.zemris.java.raytracer.model;

/**
 * Razred koji predstvalja model grafičkog objekta kugle koji
 * se može dodati u scenu.
 * 
 * @author Antonio Kuzminski
 *
 */
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
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param center središte kugle
	 * @param radius radijus kugle
	 * @param kdr koeficijent difuzne komponente crvene boje
	 * @param kdg koeficijent difuzne komponente zelene boje
	 * @param kdb koeficijent difuzne komponente plave boje
	 * @param krr koeficijent zrcalne komponente crvene boje
	 * @param krg koeficijent zrcalne komponente zelene boje
	 * @param krb koeficijent zrcalne komponente plave boje
	 * @param krn koeficijent zrcalne komponente {@code n}
	 */
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
		
		// traži se presjek kugle i polupravca
		// formula preuzeta iz https://en.wikipedia.org/wiki/Line%E2%80%93sphere_intersection
		
		// vektor od početka zrake do centra kugle
		Point3D o_c = ray.start.sub(this.center);
		
		// normalizirani vektor smjera zrake
		Point3D direction = ray.direction;
		
		// izraz ispod korijena kod rješavanje kvadratne jednadžbe, ako je
		// manji od nula, nema sjecišta, inače postoje sjecišta ako je 
		// pozitivan broj ili ako je točno nula onda diralište
		double underSquare = Math.pow(direction.scalarProduct(o_c), 2) - 
				(Math.pow(o_c.norm(), 2) - Math.pow(this.radius, 2));
	    
	    if(underSquare < 0) {
	    	return null;
	    }else {
	    	
	    	// svaka točka može se napisati: x = o + d * l, gdje je "o" početak
	    	// zrake, "d" koeficijent koji se dobije kao rješenje kvadratne jednadžbe,
	    	// a "l" normirani vektor smjera zrake
	    	
	    	// koeficijenti rješenja kvadratne jednadžbe
	    	double d1 = -direction.scalarProduct(o_c) + Math.sqrt(underSquare);
	    	double d2 = -direction.scalarProduct(o_c) - Math.sqrt(underSquare);
	    	
	    	Point3D start = ray.start;
	    	
	    	// točke dobivene koeficijentima
	    	Point3D p1 = start.add(direction.scalarMultiply(d1));
	    	Point3D p2 = start.add(direction.scalarMultiply(d2));
	    	
	    	// udaljenosti pojedine točke
	    	double dist1 = start.difference(start, p1).norm();
	    	double dist2 = start.difference(start, p2).norm();
	    	
	    	Point3D closer;
	    	double closerDist = dist1;
	    	
	    	if(dist1 < dist2) {
	    		closer = p1;
	    	}else {
	    		closer = p2;
	    		closerDist = dist2;
	    	}
	    	
	    	return new RayIntersection(closer, closerDist, true) {
				
				@Override
				public Point3D getNormal() {
					return closer.sub(center).normalize();
				}
				
				@Override
				public double getKrr() {
					return krr;
				}
				
				@Override
				public double getKrn() {
					return krn;
				}
				
				@Override
				public double getKrg() {
					return krg;
				}
				
				@Override
				public double getKrb() {
					return krb;
				}
				
				@Override
				public double getKdr() {
					return kdr;
				}
				
				@Override
				public double getKdg() {
					return kdg;
				}
				
				@Override
				public double getKdb() {
					return kdb;
				}
			};
	    }
	}

}
