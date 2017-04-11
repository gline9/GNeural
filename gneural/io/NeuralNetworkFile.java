package gneural.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import gfiles.file.VirtualFile;
import gfiles.text.CSVFile;
import gfiles.text.CSVFileReader;
import gneural.function.ActivationFunction;
import gneural.function.HyperbolicTangent;
import gneural.function.Sigmoid;
import gneural.network.FeedForwardNeuralNetwork;
import gneural.network.NeuronLayer;

public class NeuralNetworkFile extends CSVFile {

	private final CSVFileReader csv = new CSVFileReader(this);

	private FeedForwardNeuralNetwork net;

	public NeuralNetworkFile(InputStream in) throws NeuralNetworkReadException, IOException {
		super(in);
		init();
	}

	public NeuralNetworkFile() throws NeuralNetworkReadException {
		init();
	}

	public NeuralNetworkFile(VirtualFile vf) throws NeuralNetworkReadException {
		super(vf);
		init();
	}
	
	public NeuralNetworkFile(byte[] data) throws NeuralNetworkReadException{
		super(data);
		init();
	}

	public NeuralNetworkFile(FeedForwardNeuralNetwork net) {
		// just call itself with the input stream made from the csv of the
		// network
		super(net.toCSVString().getBytes());

		this.net = net;
	}

	/**
	 * private helper method that initializes the neural network file by
	 * creating the corresponding neural network
	 * 
	 * @throws NeuralNetworkReadException
	 *             if the file is improperly set up
	 * @since Apr 19, 2016
	 */
	private void init() throws NeuralNetworkReadException {
		try {
			ArrayList<Integer> neurons = new ArrayList<Integer>();
			csv.setTitles(false);
			int next, x = 0;

			// get the type of activation function
			ActivationFunction f = null;
			switch (Integer.valueOf(csv.getEntry(x++, 0))) {
			case 0:
				f = new Sigmoid();
				break;
			case 1:
				f = new HyperbolicTangent();
				break;
			default:
				throw new NeuralNetworkReadException();
			}
			while ((next = Integer.valueOf(csv.getEntry(x++, 0))) != 0) {
				neurons.add(next);
			}

			// make sure there are at least two neural layers
			if (neurons.size() < 2)
				throw new NeuralNetworkReadException();

			// init the neural network
			FeedForwardNeuralNetwork net = new FeedForwardNeuralNetwork();

			// set the activation function
			net.setActivationFunction(f);

			// make the input and output layers
			net.setInputNeurons(neurons.get(0));
			net.setOutputNeurons(neurons.get(neurons.size() - 1));

			// add the input weights
			double[][] weights = new double[neurons.get(0) + 1][neurons.get(1)];
			for (int i = 0; i < neurons.get(0) + 1; i++) {
				for (int j = 0; j < neurons.get(1); j++) {
					weights[i][j] = Double.valueOf(csv.getEntry(x++, 0));
				}
			}
			net.setInputWeights(weights);

			// set the rest of the weights
			for (int n = 1; n < neurons.size() - 1; n++) {
				weights = new double[neurons.get(n) + 1][neurons.get(n + 1)];
				for (int i = 0; i < neurons.get(n) + 1; i++) {
					for (int j = 0; j < neurons.get(n + 1); j++) {
						weights[i][j] = Double.valueOf(csv.getEntry(x++, 0));
					}
				}
				net.addLayer(new NeuronLayer(neurons.get(n)), weights);
			}

			// finalize the network
			net.finalize();

			// set this net to the network created
			this.net = net;
		} catch (NumberFormatException e) {
			throw new NeuralNetworkReadException();
		}

	}

	/**
	 * accessor method for the neural network defined by the underlying virtual
	 * file
	 * 
	 * @return neural network as is saved in the file
	 * @since Apr 19, 2016
	 */
	public FeedForwardNeuralNetwork getNetworkFromFile() {
		return net;
	}

}
