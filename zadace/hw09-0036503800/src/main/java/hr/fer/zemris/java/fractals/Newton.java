package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Newton {

	private static Complex[] roots;
	private static double threshold = 1E-3;
	private static double rootThreshold = 0.002;
	private static int maxIterations;

	public Newton(int maxIterations, List<Complex> roots) {

		Newton.maxIterations = maxIterations;

		Newton.roots = roots.toArray(new Complex[0]);
		
		FractalViewer.show(new Producer());
	}

	/**
	 * Funkcija za pretvorbu normalne koordinate u kompleksnu koordinatu.
	 * 
	 * @param x      trenutna x pozicija
	 * @param y      trenutna y pozicija
	 * @param reMin  minimalna vrijednost realne komponente kompleksnog broja
	 * @param reMax  maksimalna vrijednost realne komponente kompleksnog broja
	 * @param imMin  minimalna vrijednost imaginarne komponente kompleksnog broja
	 * @param imMax  maksimalna vrijednost imaginarne komponente kompleksnog broja
	 * @param width  širina prozora na kojem se crta
	 * @param height visina prozora na kojem se crta
	 * @return objekt tipa {@code Complex}
	 */
	private static Complex mapToComplexPlain(int x, int y, double reMin, double reMax, double imMin, double imMax,
			int width, int height) {

		double cre = x / (width-1.0) * (reMax - reMin) + reMin;
		double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;

		return new Complex(cre, cim);
	}

	public static class Job implements Callable<Void> {

		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		short[] data;
		int offset;
		ComplexRootedPolynomial crp;

		public Job(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax,
				short[] data, int offset, ComplexRootedPolynomial crp) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.offset = offset;
			this.crp = crp;
		}

		@Override
		public Void call() {

			ComplexPolynomial polynomial = crp.toComplexPolyNomial();
			
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++) {

					Complex c = mapToComplexPlain(x, y, reMin, reMax, imMin, imMax, width, height);

					Complex zn = c.copy();
					double module;
					int iter = 0;

					do {
						Complex znold = zn.copy();

						Complex numerator = polynomial.apply(zn);
						Complex denominator = polynomial.derive().apply(zn);

						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();

					} while (module > threshold && iter < maxIterations);

					int index = crp.indexOfClosestRootFor(zn, rootThreshold);
					data[offset] = (short) (index + 1);
					offset++;
				}
			}
			return null;
		}
	}

	public static class Producer implements IFractalProducer {

		ExecutorService pool;

		public Producer() {

			pool = Executors.newFixedThreadPool(
					Runtime.getRuntime().availableProcessors(), 
					new DaemonThreadFactory());
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, roots);
			ComplexPolynomial polynomial = crp.toComplexPolyNomial();
			
			short[] data = new short[width * height];

			// broj redaka u koliko će se podijeliti cijela visina prozora za crtanje
			final int numberOfJobs = 8 * Runtime.getRuntime().availableProcessors();
			// visina svakog retka
			int laneSize = height / numberOfJobs;

			List<Future<Void>> results = new ArrayList<>();
			
			int offset = 0;

			for (int i = 0; i < numberOfJobs; i++) {
				
				int yMin = i * laneSize;
				int yMax = (i + 1) * laneSize;

				if (i == numberOfJobs - 1)
					yMax = height - 1;

				Job job = new Job(
						reMin, 
						reMax, 
						imMin, 
						imMax, 
						width, 
						height, 
						yMin, 
						yMax, 
						data, 
						offset, 
						crp);
				
				results.add(pool.submit(job));
				
				offset += width * laneSize;
			}

			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

//			pool.shutdown();
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}
	
	

	public static void main(String[] args) {
		
//		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
//		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> roots = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int rowCounter = 0;
		
//		System.out.print("Root " + rowCounter + ">");
		
		roots.addAll(new ArrayList<>(Arrays.asList(Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG)));
		
//		while(sc.hasNextLine()) {
//			
//			rowCounter++;
//			
//			String line = sc.nextLine();
//			if(line.equals("done")) break;
//			
//			Complex root;
//			try {
//				
//				root = Complex.parse(line);
//				roots.add(root);
//				
//			}catch(NumberFormatException e) {
//				System.out.println("Pogrešno unesen kompleksni broj.");
//			}
//		}
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		Newton n = new Newton(16, roots);
		
	}
}
