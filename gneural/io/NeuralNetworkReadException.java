package gneural.io;

import java.io.IOException;

public class NeuralNetworkReadException extends IOException {
	private static final long serialVersionUID = 1L;

	public NeuralNetworkReadException() {
		super("Invalid format for neural network file!");
	}

	public NeuralNetworkReadException(String message) {
		super(message);
	}

}
