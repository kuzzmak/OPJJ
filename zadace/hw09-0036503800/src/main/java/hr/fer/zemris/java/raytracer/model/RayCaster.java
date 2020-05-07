package hr.fer.zemris.java.raytracer.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

public class RayCaster {

	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), 
				new Point3D(10, 0, 0),
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10),
				20,
				20);
	}
	
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		
		List<GraphicalObject> objects = scene.getObjects();
		
		double minDistance = Double.MAX_VALUE;
		RayIntersection closest = null;
		
		for(GraphicalObject go: objects) {
			
			RayIntersection intersection = go.findClosestRayIntersection(ray);
			
			if(intersection != null) {
				if(intersection.getDistance() < minDistance) closest = intersection;
			}
		}
		
		if(closest != null) return closest;
		return null;
	}
	
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		
		RayIntersection closest = findClosestIntersection(scene, ray);
		
		if(closest == null) return;
		
		rgb[0] = 255;
		rgb[1] = 255;
		rgb[2] = 255;
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
		        
		        Point3D screenCorner = new Point3D(
		        		view.x, 
		        		view.y - horizontal / 2, 
		        		view.z + vertical / 2);
		        
//		        Point3D screenCorner = new Point3D(
//		        		view.x - horizontal / 2, 
//		        		view.y + vertical / 2, 
//		        		view.z);
		        
				Point3D zAxis = null;
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				
				short[] rgb = new short[3];
				int offset = 0;
				
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						
						Point3D screenPoint = new Point3D(screenCorner.x,
				        		screenCorner.y  + (double)x / (width - 1) * horizontal,
				        		screenCorner.z - (double)y / (height - 1) * vertical);
						
//						Point3D screenPoint = new Point3D(screenCorner.x + (double)x / (width - 1) * horizontal,
//								screenCorner.y - (double)y / (height - 1) * vertical,
//								screenCorner.z);
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						Point3D direction = ray.direction;
						
//						System.out.println(screenPoint.x + ", " + screenPoint.y + ", " + screenPoint.z);
						
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
