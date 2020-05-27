package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Enumeracija koja sadrži osnovne operatore.
 * 
 * @author Antonio Kuzminski
 *
 */
public enum Operator {

	ADD {
		@Override
		public double apply(double x1, double x2) {
			return x1 + x2;
		}

		@Override
		public int apply(int x1, int x2) {
			return x1 + x2;
		}

		@Override
		public double apply(double x1, int x2) {
			return x1 + x2;
		}

		@Override
		public double apply(int x1, double x2) {
			return x1 + x2;
		}
	},
	SUB {
		@Override
		public double apply(double x1, double x2) {
			return x1 - x2;
		}

		@Override
		public int apply(int x1, int x2) {
			return x1 - x2;
		}

		@Override
		public double apply(double x1, int x2) {
			return x1 - x2;
		}

		@Override
		public double apply(int x1, double x2) {
			return x1 - x2;
		}
	},
	MUL {
		@Override
		public double apply(double x1, double x2) {
			return x1 * x2;
		}

		@Override
		public int apply(int x1, int x2) {
			return x1 * x2;
		}

		@Override
		public double apply(double x1, int x2) {
			return x1 * x2;
		}

		@Override
		public double apply(int x1, double x2) {
			return x1 * x2;
		}
	},
	DIV {
		@Override
		public double apply(double x1, double x2) {
			return x1 / x2;
		}

		@Override
		public int apply(int x1, int x2) {
			return x1 / x2;
		}

		@Override
		public double apply(double x1, int x2) {
			return x1 / x2;
		}

		@Override
		public double apply(int x1, double x2) {
			return x1 / x2;
		}
	};

	// metode za izračun gdje su svi moguće vrste argumenata
	public abstract double apply(double x1, double x2);

	public abstract int apply(int x1, int x2);

	public abstract double apply(double x1, int x2);

	public abstract double apply(int x1, double x2);

}