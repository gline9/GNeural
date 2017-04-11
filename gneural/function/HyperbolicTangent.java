package gneural.function;

public class HyperbolicTangent extends ActivationFunction{

	@Override
	public double normalize(double value) {
		return Math.tanh(value);
	}

	@Override
	public double derivate(double value) {
		// derivative of tanh is sech^2
		return Math.pow(1 / Math.cosh(value), 2);
	}

	@Override
	public double inverse(double value) {
		return 1.0/2 * Math.log((1 + value) / (1 - value));
	}

}
