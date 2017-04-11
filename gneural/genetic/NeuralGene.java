package gneural.genetic;

import java.util.Random;

import ggenetic.genes.Gene;

public class NeuralGene extends Gene<NeuralGene> {
	protected enum GeneType {
		NEURON, CONNECTION;
	}

	private GeneType type;

	private int connectionA;

	private int connectionB;

	private double weight;

	private final double neuronProbability;

	/**
	 * creates a new neural gene seed with the given probability of a neuron
	 * being picked instead of a weight.
	 * 
	 * @param neuronProbability
	 *            probability of a neuron being created
	 */
	public NeuralGene(double neuronProbability) {
		type = GeneType.NEURON;
		connectionA = 1;
		connectionB = 2;
		weight = 0;
		this.neuronProbability = neuronProbability;
	}

	private NeuralGene(GeneType type, int connectionA, int connectionB, double weight, double neuronProbability) {
		this.type = type;
		this.connectionA = connectionA;
		this.connectionB = connectionB;
		this.weight = weight;
		this.neuronProbability = neuronProbability;
	}

	public GeneType getType() {
		return type;
	}

	public int getFirstConnection() {
		return connectionA;
	}

	public int getSecondConnection() {
		return connectionB;
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public NeuralGene copy() {
		return new NeuralGene(type, connectionA, connectionB, weight, neuronProbability);
	}

	@Override
	public void mutate(double mutationSeverity) {
		Random r = new Random();
		// the type of gene will only change if severity is greater than 90%
		if (mutationSeverity > .7) {
			type = type.equals(GeneType.NEURON) ? GeneType.CONNECTION : GeneType.NEURON;
		}

		// if the type is a connection, change some of the variables
		if (type.equals(GeneType.CONNECTION)) {
			// percent of the variables that are changed have to do with the
			// percent of the severity
			if (r.nextDouble() > mutationSeverity) {
				connectionA += (2 * r.nextInt(2) - 1) * r.nextInt((int) (mutationSeverity * 10) + 2);
				connectionA = Math.abs(connectionA);
			}
			if (r.nextDouble() > mutationSeverity) {
				connectionB += (2 * r.nextInt(2) - 1) * r.nextInt((int) (mutationSeverity * 10) + 2);
				connectionB = Math.abs(connectionB);
			}
			if (r.nextDouble() > mutationSeverity) {
				weight += (2 * r.nextInt(2) - 1) * r.nextDouble() * mutationSeverity * 10;
			}

		}
	}

	@Override
	public void randomize() {
		// set the type of neuron it is
		Random r = new Random();
		type = r.nextDouble() < neuronProbability ? GeneType.NEURON : GeneType.CONNECTION;

		// if it is a connection set the rest
		if (type.equals(GeneType.CONNECTION)) {
			connectionA = Math.abs(r.nextInt());
			connectionB = Math.abs(r.nextInt());

			// will create random weights with double exponential distribution
			// with standard deviation sqrt(2)
			weight = (2 * r.nextInt(2) - 1) * Math.log(1 - r.nextDouble());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + connectionA;
		result = prime * result + connectionB;
		long temp;
		temp = Double.doubleToLongBits(neuronProbability);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NeuralGene other = (NeuralGene) obj;
		if (connectionA != other.connectionA)
			return false;
		if (connectionB != other.connectionB)
			return false;
		if (Double.doubleToLongBits(neuronProbability) != Double.doubleToLongBits(other.neuronProbability))
			return false;
		if (type != other.type)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

}
