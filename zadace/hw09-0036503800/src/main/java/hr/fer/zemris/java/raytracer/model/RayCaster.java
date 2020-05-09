package hr.fer.zemris.java.raytracer.model;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;
import hr.fer.zemris.math.Vector3;

public class RayCaster {
	
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), 
				new Point3D(10, 0, 0),
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10),
				20,
				20);
	}
	
	/**
	 * Funkcija za pronalazak sjecišta trenutne zrake {@code ray} i objekata
	 * scene {@code scene}. Ako ih postoji više, uzima se bliže.
	 * 
	 * @param scene scene u kojoj se nalaze objekti
	 * @param ray zraka kojoj se nalazi potencijalno sjecište
	 * @return sjecište ako postoji, {@code null} inače
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		
		List<GraphicalObject> objects = scene.getObjects();
		
		double minDistance = Double.MAX_VALUE;
		RayIntersection closest = null;
		
		for(GraphicalObject go: objects) {
			
			RayIntersection intersection = go.findClosestRayIntersection(ray);
			
			if(intersection != null) {
				if(intersection.getDistance() < minDistance) {
					closest = intersection;
					minDistance = intersection.getDistance();
				}
			}
		}
		
		return closest;
	}
	
	/**
	 * Funkcija za izračun doprinosa difuzne komponente.
	 * 
	 * @param ls svjetlosni izvor
	 * @param closestObjectIntersection točka na objektu koju vidi promatrač
	 * @param rgb polje kojem se doda doprinos difuzne komponente
	 * @return doprinos difuzne komponente
	 */
	private static void diffuseComponent(LightSource ls, RayIntersection closestObjectIntersection, short[] rgb) {
		
		// vektor smjera od točke na objektu prema izvoru svjetla
		Point3D lightSourceDirection = ls.getPoint().sub(closestObjectIntersection.getPoint());
		// normala u točki objekta koji se promatra
		Point3D n = closestObjectIntersection.getNormal();
		
		Vector3 v1 = new Vector3(lightSourceDirection.x, lightSourceDirection.y, lightSourceDirection.z);
		Vector3 v2 = new Vector3(n.x, n.y, n.z);
		
		// kosinus kuta između vektora: od izvor do najbliže točke i normale objekta u točki
		double cosfi = v1.cosAngle(v2);
		
		short r = (short) (ls.getR() * closestObjectIntersection.getKdr() * cosfi);
		short g = (short) (ls.getG() * closestObjectIntersection.getKdg() * cosfi);
		short b = (short) (ls.getB() * closestObjectIntersection.getKdb() * cosfi);
		
		rgb[0] += r > 0 ? r : 0;
		rgb[1] += g > 0 ? g : 0;
		rgb[2] += b > 0 ? b : 0;
	}
	
	/**
	 * Funkcija za izračun doprinosa zrcalne komponente.
	 * 
	 * @param ls svjetlosni izvor
	 * @param closestObjectIntersection točka na objektu koju vidi promatrač
	 * @param ray zraka od točke promatrača do točke na objektu
	 * @param rgb doprinos zrcalne komponente
	 */
	private static void reflectiveComponent(LightSource ls, RayIntersection closestObjectIntersection, Ray ray, short[] rgb) {
		
		Point3D p1 = ls.getPoint();
		Point3D p2 = closestObjectIntersection.getNormal();
		Point3D p3 = ray.direction;
		// vektor od točke na objektu do izvora svjetla
		Point3D lv = p1.sub(closestObjectIntersection.getPoint()).modifyNormalize();
		
		// vektor od točke na objektu do izvora svjetla
		Vector3 l = new Vector3(lv.x, lv.y, lv.z);
		// normala u točki na objektu
		Vector3 normal = new Vector3(p2.x, p2.y, p2.z);
		// vektor od promatrača do točke na objektu
		Vector3 v = new Vector3(-p3.x, -p3.y, -p3.z);
		// reflektirana zraka/vektor
		Vector3 r = normal.scale(2).scale(l.dot(normal)).sub(l);
		
		double cosa = r.cosAngle(v);
		double n = closestObjectIntersection.getKrn();
		
		rgb[0] += (short) (ls.getR() * closestObjectIntersection.getKrr() * Math.pow(cosa, n));
		rgb[1] += (short) (ls.getG() * closestObjectIntersection.getKrg() * Math.pow(cosa, n));
		rgb[2] += (short) (ls.getB() * closestObjectIntersection.getKrb() * Math.pow(cosa, n));
	}
	
	/**
	 * Metoda za provjeru siječe li zraka objekt i bojanje piksela ako ga siječe.
	 * 
	 * @param scene scena u kojoj se nalaze objekti
	 * @param ray zraka za koju se provjerava siječe li koji objekt
	 * @param rgb crvena, zelena i plava komponenta piksela
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		
		// ambijentna komponenta
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		RayIntersection closestObjectIntersection = findClosestIntersection(scene, ray);
		
		if(closestObjectIntersection == null) return;
		
		List<LightSource> lightSources = scene.getLights();
		
		for(LightSource ls: lightSources) {
			
			Ray fromLStoObject = Ray.fromPoints(ls.getPoint(), closestObjectIntersection.getPoint());
			
			RayIntersection closestLightSourceIntersection = findClosestIntersection(scene, fromLStoObject);
			
			if(closestLightSourceIntersection != null) {
				
				// udaljenost od izvora svjetla do najbliže točke
				double d1 = closestLightSourceIntersection.getDistance();
				
				// točka najbliža izvoru svjetla
				Point3D lsPoint = ls.getPoint();
				// točka najbliža gledatelju
				Point3D objectPoint = closestLightSourceIntersection.getPoint();
				
				// udaljenost od izvora svjetla do najbliže točke na objektu koja
				// je izračunata kao najbliža na nekom objektu
				double d2 = Math.sqrt(Math.pow(lsPoint.x - objectPoint.x, 2) + 
						Math.pow(lsPoint.y - objectPoint.y, 2) +
						Math.pow(lsPoint.z - objectPoint.z, 2));
				
				// objekt zaklonjen
				if(d1 - d2 < 0) {
					continue;
				}else {
					diffuseComponent(ls, closestObjectIntersection, rgb);
					reflectiveComponent(ls, closestObjectIntersection, ray, rgb);
				}
			}
		}
	}
	
	/**
	 * Metoda za stvaranje objekta za ray tracing.
	 * 
	 * @return objekt tipa {@code IRayTracerProducer}
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
					int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
//				Point3D g_o = view.sub(eye);
//				Point3D og = g_o.scalarMultiply(1 / g_o.norm());
//				Point3D viewNormalized = viewUp.normalize();

//				Point3D yAxis = viewNormalized.sub(og.scalarMultiply(og.scalarProduct(viewNormalized)));
//		        yAxis.modifyNormalize();
//
//		        Point3D xAxis = og.vectorProduct(yAxis);
//		        xAxis.modifyNormalize();
//		        
//		        Point3D zAxis = og.copy();
				
		        Point3D screenCorner = new Point3D(
		        		view.x, 
		        		view.y - horizontal / 2, 
		        		view.z + vertical / 2);
		        
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;

				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						
						Point3D screenPoint = new Point3D(screenCorner.x,
				        		screenCorner.y  + (double)x / (width - 1) * horizontal,
				        		screenCorner.z - (double)y / (height - 1) * vertical);
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						
						offset++;
					}
				}
						
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
}
