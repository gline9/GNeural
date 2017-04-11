package gneural.test;

import java.util.Random;
import java.util.function.BiFunction;

import gneural.function.HyperbolicTangent;
import gneural.genetic.NeuralCreature;
import gneural.network.FeedForwardNeuralNetwork;
import gneural.network.NeuralNetwork;

public class Tester {
	public static void main(String[] args) {

		FeedForwardNeuralNetwork ffnn = new FeedForwardNeuralNetwork(2, 1000, 20, 1);
		BiFunction<Double, Double, Double> f = (a,b) -> a*b;
		Random r = new Random();
		for (int i = 0; i < 1000000; i++) {
			double input = r.nextDouble() / 2;
			double input2 = r.nextDouble() / 2;
			ffnn.backpropagate(new double[] { input, input2 }, new double[] { f.apply(input, input2) }, .5);
		}

		for (int i = 0; i < 10; i++) {
			double input = r.nextDouble() / 2;
			double input2 = r.nextDouble() / 2;
			double output = ffnn.compute(new double[] { input, input2 })[0];
			System.out.println(f.apply(input, input2) + ", " + output);
		}

		// FeedForwardNeuralNetwork ffnn = new FeedForwardNeuralNetwork(1, 300,
		// 1000, 1);
		// System.out.println("initial test");
		// byte[] data = ffnn.toCSVString().getBytes();
		// System.out.println("initial test finished");
		// VirtualFile vf = new VirtualFile(data);
		// System.out.println("Second test finished");
		// ffnn.setActivationFunction(new HyperbolicTangent());
		//
		// ffnn.backpropagate(new double[] { 1 }, new double[] { 1 }, 10);
		//
		// Random r = new Random();
		//
		// ArrayList<Pair<Double, Double>> results = new ArrayList<>();
		//
		// for (int i = 0; i < 1000; i++) {
		// double next = r.nextDouble();
		// results.add(new Pair<>(next, ffnn.compute(new double[] { next
		// })[0]));
		// }
		//
		// System.out.println("attempting save");
		// NeuralNetworkFile file = new NeuralNetworkFile(ffnn);
		// try {
		// System.out.println("Saving... " + file.getBytes());
		//
		// file.save(new File("test.ffnn"));
		//
		// System.out.println("Saved");
		//
		// FeedForwardNeuralNetwork copy = new
		// NeuralNetworkFile(VirtualFile.load(new File("test.ffnn")))
		// .getNetworkFromFile();
		//
		// boolean error = false;
		// for (int i = 0; i < 1000; i++) {
		// double next = results.get(i).getFirst();
		// double result = results.get(i).getSecond();
		// double temp = 0;
		// if ((temp = copy.compute(new double[] { next })[0]) != result) {
		// System.out.println("Error:\n\tExpected: " + result + "\n\tReceived: "
		// + temp);
		// error = true;
		// }
		// }
		//
		// if (!error) {
		// System.out.println("All tests passed sucessfully!");
		// } else {
		// System.out.println("Test failed!");
		// }
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}

	public static double fitness(NeuralCreature creature) {
		NeuralNetwork net = creature.getNetwork(2, 1);
		double a = net.compute(new double[] { 0, 0 })[0];
		double b = net.compute(new double[] { 0, 1 })[0];
		double c = net.compute(new double[] { 1, 0 })[0];
		double d = net.compute(new double[] { 1, 1 })[0];
		double distance = 0;
		distance += a;
		distance += 1 - b;
		distance += 1 - c;
		distance += d;
		return distance;
	}
}
