package gneural.network;

import java.util.ArrayList;

public abstract class NeuralNetwork {

	// stores the input neurons for the network
	private ArrayList<InputNeuron> inputs = new ArrayList<>();

	// stores the middle neurons for the network
	private ArrayList<Neuron> middleNeurons = new ArrayList<>();

	// stores the output neurons for the network
	private ArrayList<OutputNeuron> outputs = new ArrayList<>();

	/**
	 * adds a neuron to the input neuron list, this addition will be in order.
	 * 
	 * @param n
	 *            input neuron to add to the network
	 */
	protected final void addInputNeuron(InputNeuron n) {
		inputs.add(n);
	}

	protected final void addHiddenNeuron(Neuron n) {
		middleNeurons.add(n);
	}

	protected final void addOutputNeuron(OutputNeuron n) {
		outputs.add(n);
	}

	protected final void addInputNeurons(InputNeuron... ns) {
		for (InputNeuron n : ns) {
			addInputNeuron(n);
		}
	}

	protected final void addHiddenNeurons(Neuron... ns) {
		for (Neuron n : ns) {
			addHiddenNeuron(n);
		}
	}

	protected final void addOutputNeurons(OutputNeuron... ns) {
		for (OutputNeuron n : ns) {
			addOutputNeuron(n);
		}
	}

	public double[] compute(double[] inputData) {
		// make sure there are the correct number of inputs
		if (inputData.length != inputs.size())
			throw new IllegalArgumentException("Invalid number of inputs for the neural network!");

		// feed each input their corresponding input data
		for (int i = 0; i < inputData.length; i++) {
			inputs.get(i).takeInput(inputData[i]);
		}

		// activate all of the hidden neurons in order
		for (Neuron n : middleNeurons) {
			n.activateNeuron();
		}

		// create the output array for the outputs
		double[] outputData = new double[outputs.size()];

		// add the data that the outputs have
		for (int i = 0; i < outputData.length; i++) {
			outputData[i] = outputs.get(i).getOutput();
		}

		// return the output
		return outputData;
	}

	public int hiddenSize() {
		return middleNeurons.size();
	}

	public int inputSize() {
		return inputs.size();
	}

	public int outputSize() {
		return outputs.size();
	}

	public int totalSize() {
		return hiddenSize() + inputSize() + outputSize();
	}

	public abstract void minimize();

}
