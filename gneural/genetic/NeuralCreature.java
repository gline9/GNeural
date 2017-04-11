package gneural.genetic;

import ggenetic.creature.Creature;
import ggenetic.genes.Genome;
import gneural.network.NeuralNetwork;

public class NeuralCreature extends Creature<NeuralCreature, NeuralGene> {

	private final int numberOfGenes;

	private final double probabilityOfNeuron;

	public NeuralCreature(int numberOfGenes, double probabilityOfNeuron) {
		super(new NeuralGene(probabilityOfNeuron), 1, numberOfGenes);
		this.numberOfGenes = numberOfGenes;
		this.probabilityOfNeuron = probabilityOfNeuron;
	}

	private NeuralCreature(Genome<NeuralGene> genome, int numberOfGenes, double probabilityOfNeuron) {
		super(genome);
		this.numberOfGenes = numberOfGenes;
		this.probabilityOfNeuron = probabilityOfNeuron;
	}

	@Override
	public NeuralCreature asexuallyReproduce(double mutationRate, double mutationSeverity) {
		NeuralCreature results = new NeuralCreature(genes.copyWithMutation(mutationRate, mutationSeverity),
				numberOfGenes, probabilityOfNeuron);
		return results;
	}

	@Override
	public NeuralCreature breed(NeuralCreature other, double mutationRate, double mutationSeverity) {
		NeuralCreature results = new NeuralCreature(
				genes.breedWithMutation(other.genes, mutationRate, mutationSeverity), numberOfGenes,
				probabilityOfNeuron);
		return results;
	}

	@Override
	public NeuralCreature randomize() {
		return new NeuralCreature(numberOfGenes, probabilityOfNeuron);
	}

	public NeuralNetwork getNetwork(int inputs, int outputs) {

		GeneticNeuralNetwork net = new GeneticNeuralNetwork(inputs, outputs);
		for (int i = 0; i < numberOfGenes; i++) {
			net.loadGene(genes.getChromosome(0).getGene(i));
		}

		return net;
	}

}
