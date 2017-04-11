package gneural.network;

public class InputLayer {

	protected final InputNeuron[] neurons;

	private final BiasNeuron biasNeuron = new BiasNeuron();

	public InputLayer(int neurons) {
		this.neurons = new InputNeuron[neurons];
		for (int i = 0; i < this.neurons.length; i++) {
			this.neurons[i] = new InputNeuron();
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
				neurons[i].createLink(n.neurons[j], weightMatrix[i][j]);
			}
		}

		// link the bias neuron
		int i = neurons.length;
		for (int j = 0; j < n.neurons.length; j++) {
			biasNeuron.createLink(n.neurons[j], weightMatrix[i][j]);
		}
	}

	/**
	 * takes the inputs for the input layer and activates all of the neurons
	 * 
	 * @param inputs
	 *            input values for the layer, must have the same size as the
	 *            layer
	 */
	public void takeInputs(double... inputs) {
		// loop through the inputs and give to each neuron
		for (int i = 0; i < inputs.length; i++) {
			neurons[i].takeInput(inputs[i]);
		}

		// activate the bias neuron
		biasNeuron.activateNeuron();
	}
	
	public Neuron getNeuron(int index) {
		if (index == neurons.length)
			return biasNeuron;
		return neurons[index];
	}

	public int size() {
		return neurons.length;
	}
}