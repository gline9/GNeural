package gneural.function;

public class Sigmoid extends ActivationFunction{
	@Override
	public double normalize(double value) {
		return 1.0 / (Math.exp(-value) + 1);
	}

	@Override
	public double derivate(double value) {
		return normalize(value) * (1 - normalize(value));
	}

	@Override
	public double inverse(double value) {
		return Math.log(value / (1 - value));
	}

}
