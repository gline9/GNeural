package gneural.network;

public class OutputNeuron extends Neuron {

	public OutputNeuron() {
		super();
	}

	public void linkNeuron(Neuron n, double link) {
		// this is because output neurons don't link to anything else
		throw new RuntimeException("Cannot link an output neuron to another neuron!");
	}

	/**
	 * gets the output value from the neuron, assuming the neuron has the values
	 * sent to it.
	 * 
	 * @return output value for the output neuron
	 */
	public double getOutput() {
		return getActivatedValue();
	}
	
	public double getPreviousInput(){
		return super.getPreviousInput();
	}

	protected void receive(double value) {
		super.receive(value);
	}

}
