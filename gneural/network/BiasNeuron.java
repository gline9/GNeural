package gneural.network;

public class BiasNeuron extends Neuron {

	public void activateNeuron() {
		// add 1 to the value
		receive(1);

		// activate using the super function
		super.activateNeuron();
	}
	
	protected double backpropagate(double learningRate, double[] previousErrorComps, double[] previousInputs) {
		System.out.println("error");
		return 0;
	}
}
