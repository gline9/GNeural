package gneural.network;

import gneural.function.ActivationFunction;

public class OutputLayer extends NeuronLayer {

	protected final OutputNeuron[] neurons;

	public OutputLayer(int neurons) {
		super(neurons);
		this.neurons = new OutputNeuron[neurons];
		for (int i = 0; i < this.neurons.length; i++) {
			this.neurons[i] = new OutputNeuron();
		}
	}

	public void setActivationFunction(ActivationFunction function) {
		for (int i = 0; i < this.neurons.length; i++) {
			this.neurons[i].setActivationFunction(function);
		}
	}

	/**
	 * gets the outputs from the layer after they have been computed
	 * 
	 * @return outputs from the neural layer
	 */
	public double[] getOutputs() {
		// store results in a double array
		double[] results = new double[neurons.length];

		// loop through and get the results from each neuron
		for (int i = 0; i < results.length; i++) {
			results[i] = neurons[i].getOutput();
		}

		// return the results
		return results;
	}

	public void activateLayer() {
		throw new RuntimeException("You cannot activate an output layer!");
	}

	public Neuron getNeuron(int index) {
		return neurons[index];
	}
}
