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
	
	private static short[] diffuseComponent(LightSource ls, Ray fromLStoS, RayIntersection closestObjectIntersection) {
		
		// polje doprinosa difuzne komponente
		short[] rgb = new short[3];
		
		Point3D lightSourceDirection = ls.getPoint().sub(closestObjectIntersection.getPoint());
		Point3D n = closestObjectIntersection.getNormal();
		
		Vector3 v1 = new Vector3(lightSourceDirection.x, lightSourceDirection.y, lightSourceDirection.z);
		Vector3 v2 = new Vector3(n.x, n.y, n.z);
		
		
		// kosinus kuta između vektora: od izvor do najbliže točke i normale objekta u točki
		double cosfi = v1.cosAngle(v2);
		
//		System.out.println(closestObjectIntersection.getKdr());
		
		rgb[0] = (short) (ls.getR() * closestObjectIntersection.getKdr() * cosfi);
		rgb[1] = (short) (ls.getG() * closestObjectIntersection.getKdg() * cosfi);
		rgb[2] = (short) (ls.getB() * closestObjectIntersection.getKdb() * cosfi);
		
		return rgb;
	}
	
	private static short[] reflectiveComponent(LightSource ls, Ray fromLStoS, RayIntersection closestObjectIntersection, Ray ray) {
		
		short[] rgb = new short[3];
		
		Point3D p1 = ls.getPoint();
		Point3D p2 = closestObjectIntersection.getNormal();
		Point3D p3 = ray.direction;
		
		Vector3 l = new Vector3(p1.x, p1.y, p1.z);
		Vector3 normal = new Vector3(p2.x, p2.y, p2.z);
		Vector3 v = new Vector3(p3.x, p3.y, p3.z);
		
		Vector3 r = normal.scale(2).scale(l.dot(normal)).sub(l);
		
		double cosa = r.cosAngle(v);
		double n = closestObjectIntersection.getKrn();
		
		rgb[0] = (short) (ls.getR() * closestObjectIntersection.getKrr() * Math.pow(cosa, n));
		rgb[1] = (short) (ls.getG() * closestObjectIntersection.getKrg() * Math.pow(cosa, n));
		rgb[2] = (short) (ls.getB() * closestObjectIntersection.getKrb() * Math.pow(cosa, n));
		
		return rgb;
	}
	
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;
		
		RayIntersection closestObjectIntersection = findClosestIntersection(scene, ray);
		
		if(closestObjectIntersection == null) return;
		
		List<LightSource> lightSources = scene.getLights();
		
		for(LightSource ls: lightSources) {
			
			Ray fromLStoS = Ray.fromPoints(ls.getPoint(), closestObjectIntersection.getPoint());
			
			RayIntersection closestLightSourceIntersection = findClosestIntersection(scene, fromLStoS);
			
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
				if(d2 - d1 > 1E-6) {
					continue;
				}else {
					
					short[] diff = diffuseComponent(ls, fromLStoS, closestObjectIntersection);
					rgb[0] += diff[0];
					rgb[1] += diff[1];
					rgb[2] += diff[2];
					
					short[] ref = reflectiveComponent(ls, fromLStoS, closestObjectIntersection, ray);
					rgb[0] += ref[0];
					rgb[1] += ref[1];
					rgb[2] += ref[2];
				}
			}
		}
	}
	
	
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
					int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				
				Point3D g_o = view.sub(eye);
				Point3D og = g_o.scalarMultiply(1 / g_o.norm());
				Point3D viewNormalized = viewUp.normalize();

				Point3D yAxis = viewNormalized.sub(og.scalarMultiply(og.scalarProduct(viewNormalized)));
		        yAxis.modifyNormalize();

		        Point3D xAxis = og.vectorProduct(yAxis);
		        xAxis.modifyNormalize();
		        
		        Point3D zAxis = og.copy();
		        
		        Point3D screenCorner = new Point3D(
		        		view.x, 
		        		view.y - horizontal / 2, 
		        		view.z + vertical / 2);
		        
//		        Point3D screenCorner = new Point3D(
//		        		view.x - horizontal / 2, 
//		        		view.y + vertical / 2, 
//		        		view.z);
		        
//				System.out.println("z: " + zAxis.x + ", " + zAxis.y + ", " + zAxis.z);
//				System.out.println("screen corner: " + screenCorner.x + ", " + screenCorner.y + ", " + screenCorner.z);
				
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				
//				int x = 250;
//				int y = 166;
				
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						
						
						
						Point3D screenPoint = new Point3D(screenCorner.x,
				        		screenCorner.y  + (double)x / (width - 1) * horizontal,
				        		screenCorner.z - (double)y / (height - 1) * vertical);
						
//						Point3D screenPoint = new Point3D(screenCorner.x + (double)x / (width - 1) * horizontal,
//								screenCorner.y - (double)y / (height - 1) * vertical,
//								screenCorner.z);
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
//						System.out.println("screen point: " + screenPoint.x + ", " + screenPoint.y + ", " + screenPoint.z);
//						System.out.println("ray start: " + ray.start.x + ", " + ray.start.y + ", " + ray.start.z);
//						System.out.println("direction: " + ray.direction.x + ", " + ray.direction.y + ", " + ray.direction.z);
						
						tracer(scene, ray, rgb);
						
//						System.out.println(Arrays.toString(rgb));
						
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
