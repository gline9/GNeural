package gneural.function;

public abstract class ActivationFunction {
	
	public abstract double normalize(double value);
	
	public abstract double derivate(double value);
	
	public abstract double inverse(double value);
}
