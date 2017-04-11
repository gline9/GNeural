package gneural.genetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import gneural.function.ActivationFunction;
import gneural.function.Sigmoid;
import gneural.genetic.NeuralGene.GeneType;
import gneural.network.BiasNeuron;
import gneural.network.InputNeuron;
import gneural.network.NeuralNetwork;
import gneural.network.Neuron;
import gneural.network.OutputNeuron;

public class GeneticNeuralNetwork extends NeuralNetwork {

	private final int inputs;

	private final int outputs;

	private ActivationFunction function = new Sigmoid();

	private final ArrayList<Neuron> neurons = new ArrayList<>();

	/**
	 * creates a new genetic neural network with the specified number of inputs
	 * 
	 * @param inputs
	 *            inputs for the network
	 */
	public GeneticNeuralNetwork(int inputs, int outputs) {
		// set how many inputs and outputs there are
		this.outputs = outputs;
		this.inputs = inputs;
		// create the input neurons
		for (int i = 0; i < inputs; i++) {
			InputNeuron in = new InputNeuron();
			neurons.add(in);
			addInputNeuron(in);
		}

		// create the bias neuron
		neurons.add(new BiasNeuron());

		// create the output neurons
		for (int i = 0; i < outputs; i++) {
			OutputNeuron out = new OutputNeuron();
			neurons.add(out);
			addOutputNeuron(out);
		}
	}

	public void setActivationFunction(ActivationFunction function) {
		this.function = function;
		for (Neuron n : neurons) {
			n.setActivationFunction(function);
		}
	}

	protected void loadGene(NeuralGene gene) {
		// check if it is a neuron
		if (gene.getType().equals(GeneType.NEURON)) {
			// if so add it to the array
			Neuron next = new Neuron();
			next.setActivationFunction(function);
			neurons.add(inputs, next);
			addHiddenNeuron(next);
		} else {
			// otherwise add the corresponding connection weight
			int firstConnection = gene.getFirstConnection();
			int secondConnection = gene.getSecondConnection();
			double weight = gene.getWeight();

			// add the connection
			int neuronA = firstConnection % (neurons.size() - outputs);
			int neuronB = secondConnection % (neurons.size() - inputs) + inputs;
			Neuron a = neurons.get(neuronA);
			Neuron b = neurons.get(neuronB);
			a.createLink(b, weight);
		}
	}

	@Override
	public void minimize() {
		// get all of the neurons that are mapped to each other
		Set<Integer> accessedIndicies = new HashSet<>();

		ArrayList<Neuron> wave = new ArrayList<>();

		// add the input neurons to the wave
		for (int i = 0; i < inputs; i++) {
			wave.add(neurons.get(i));
		}

		// continue until the wave has run out
		while (!wave.isEmpty()) {
			Neuron n = wave.get(0);

			// get the neuron's index
			int index = indexOf(n);
			// if index is in set, remove and go to next
			if (accessedIndicies.contains(index)) {
				wave.remove(n);
				continue;
			}

			// otherwise add all of it's linked nodes and add to the set
			ArrayList<Neuron> links = n.getLinks();
			wave.addAll(links);
			accessedIndicies.add(index);

		}

		// go through all of the indicies, if they aren't in the set, remove
		// from the neuron map this is in reverse order to avoid indicies moving
		// backward as items are removed

		for (int i = neurons.size() - 1; i >= 0; i++) {
			if (!accessedIndicies.contains(i)) {
				neurons.remove(i);
			}
		}

	}

	private int indexOf(Neuron neuron) {
		for (int i = 0; i < neurons.size(); i++) {
			Neuron n = neurons.get(i);
			if (n == neuron)
				return i;
		}
		return -1;
	}

}
