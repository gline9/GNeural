package gneural.network;

import java.util.ArrayList;
import java.util.stream.Collectors;

import gcore.tuples.Pair;
import gneural.function.ActivationFunction;
import gneural.function.Sigmoid;

public class Neuron {

	private double value = 0;

	private ActivationFunction activationFunction = new Sigmoid();

	protected ArrayList<Pair<Neuron, Double>> links = new ArrayList<>();

	protected double previousOutput = 0;

	protected double previousInput = 0;

	public void setActivationFunction(ActivationFunction function) {
		this.activationFunction = function;
	}

	public void createLink(Neuron n, double weight) {
		// just add the link to the links array list
		links.add(new Pair<>(n, weight));
	}

	public void activateNeuron() {
		// reset the previous activation record
		previousOutput = 0;
		previousInput = 0;

		// set the previous input
		previousInput = value;

		// use the activation function on the received value.
		value = activationFunction.normalize(value);
		
		// set the previous output
		previousOutput = value;

		// send the value multiplied by the respective weights to the linked
		// neurons
		for (Pair<Neuron, Double> link : links) {
			double weightedValue = link.getSecond() * value;

			link.getFirst().receive(weightedValue);
		}

		// reset the value back to 0
		value = 0;
	}

	/**
	 * method to receive a value from another neuron this can only be called by
	 * a neuron
	 * 
	 * @param value
	 *            value to be received
	 */
	protected void receive(double value) {
		this.value += value;
	}

	/**
	 * gets the activated value of the neuron, will reset the neuron's value
	 * afterwards.
	 * 
	 * @return activated value of the neuron
	 */
	protected double getActivatedValue() {
		// set the previous input to the value
		previousInput = value;
		
		double activatedValue = activationFunction.normalize(value);
		value = 0;
		return activatedValue;
	}
	
	protected double getPreviousInput(){
		return previousInput;
	}

	protected double backpropagate(double learningRate, double[] previousDelta) {

		// compute the error for this neuron to give to the next
		double error = 0;
		for (int i = 0; i < links.size(); i++){
			error += previousDelta[i] * links.get(i).getSecond();
		}
		
		// get the seed value that all of the future terms will be using, this
		// is the equivalent to the derivative of the error function with
		// respect to the input this neuron received
		double seedValue = -learningRate * previousOutput;

		// takes the seed value and times by the derivative of the input this
		// neuron received with respect to the weight connection to the next
		// neuron, combining this with the previous derivative we get the
		// derivative of the error function with respect to the weight to the
		// next layer this derivative is used as a gradient to adjust the weight
		for (int i = 0; i < links.size(); i++) {
			double adjustment = seedValue * previousDelta[i];
			double linkValue = links.get(i).getSecond() + adjustment;
			links.set(i, new Pair<>(links.get(i).getFirst(), linkValue));
		}
		
		// return the delta value for the next layer
		return activationFunction.derivate(previousInput) * error;
	}

	public ArrayList<Neuron> getLinks() {
		return links.stream()
				.map(p -> p.getFirst())
				.collect(Collectors.toCollection(() -> new ArrayList<>()));
	}

}
