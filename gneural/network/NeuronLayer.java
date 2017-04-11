package gneural.network;

import gneural.function.ActivationFunction;

public class NeuronLayer {

	protected final Neuron[] neurons;

	private final BiasNeuron biasNeuron = new BiasNeuron();

	public NeuronLayer(int neurons) {
		this.neurons = new Neuron[neurons];
		for (int i = 0; i < this.neurons.length; i++) {
			this.neurons[i] = new Neuron();
		}
	}

	public void setActivationFunction(ActivationFunction function) {
		for (int i = 0; i < this.neurons.length; i++) {
			this.neurons[i].setActivationFunction(function);
		}
	}

	/**
	 * links the current layer to the layer given using the given weight matrix.
	 * 
	 * @param n
	 *            neuron layer to link to
	 * @param weightMatrix
	 *            matrix with the weights, has as many rows as neurons in
	 *            current row plus one for the bias neuron, and columns as
	 *            neurons in ending row
	 */
	public void linkToLayer(NeuronLayer n, double[][] weightMatrix) {
		// loop through every neuron of the current layer and of the ending
		// layer and add a connection with the given weight matrix.
		for (int i = 0; i < neurons.length; i++) {
			for (int j = 0; j < n.neurons.length; j++) {

				// link the neurons
				neurons[i].createLink(n.getNeuron(j), weightMatrix[i][j]);
			}
		}

		// link the bias neuron
		int i = neurons.length;
		for (int j = 0; j < n.neurons.length; j++) {
			biasNeuron.createLink(n.getNeuron(j), weightMatrix[i][j]);
		}
	}

	/**
	 * activates the neuron layer
	 */
	public void activateLayer() {
		// activate the bias neuron
		biasNeuron.activateNeuron();

		// activate the rest of the neurons
		for (int i = 0; i < neurons.length; i++) {
			neurons[i].activateNeuron();
		}
	}

	public Neuron getNeuron(int index) {
		if (index == neurons.length)
			return biasNeuron;
		return neurons[index];
	}

	public int size() {
		return neurons.length;
	}
	
	public int weightedSize(){
		return neurons.length + 1;
	}

}
