package gneural.network;

import gcore.tuples.Pair;

public class InputNeuron extends Neuron {
	
	/**
	 * puts the input value into the input neuron and then activates the lower
	 * neurons that are connected to it
	 * 
	 * @param input
	 *            input value for the neuron
	 */
	public void takeInput(double input) {
		
		previousOutput = input;
		previousInput = input;

		// activate the neurons below the input neuron

		// send the value multiplied by the respective weights to the linked
		// neurons
		for (Pair<Neuron, Double> link : links) {
			link.getFirst().receive(link.getSecond() * input);
		}
	}

	public void activateNeuron() {
		throw new RuntimeException("Input neuron cannot be activated!");
	}

}

