package FS_Based_on_modified_FF.Optimizers;

import FS_Based_on_modified_FF.Discreeting.TransferFunction;
import FS_Based_on_modified_FF.Evaluation.FitnessFunction;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.tribuo.Dataset;
import org.tribuo.ImmutableFeatureMap;
import org.tribuo.SelectedFeatureSet;
import org.tribuo.Trainer;
import org.tribuo.classification.Label;
import org.tribuo.classification.ensemble.VotingCombiner;
import org.tribuo.common.nearest.KNNModel;
import org.tribuo.common.nearest.KNNTrainer;
import org.tribuo.math.distance.L1Distance;
import org.tribuo.math.neighbour.NeighboursQueryFactoryType;
import org.tribuo.provenance.FeatureSelectorProvenance;
import org.tribuo.provenance.impl.FeatureSelectorProvenanceImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Select features based on Black Hole algorithm with binary transfer functions, a given classifier (default is KNN, K = 1) and 10-fold cross validation
 * <p>
 * see:
 * <pre>
 * Abdolreza Hatamlou.
 * "Black hole: A new heuristic optimization approach for data clustering", 2013.
 *
 * Santosh Kumar et al.
 * "Black Hole Algorithm and Its Applications", 2016.
 *
 * Laith Abualigah et al
 * "Black hole algorithm: A comprehensive survey", 2022
 * </pre>
 */
public final class BlachHoleOptimizer implements Optimizers {
    private final TransferFunction transferFunction;
    private final int populationSize;
    private int [][] setOfSolutions;
    private final FitnessFunction FN;
    private final double mutationThreshould;
    private final int maxIteration;
    private final SplittableRandom rng;
    private final int seed;
    private final double[] convergenceCurve;

    /**
     * The default constructor for feature selection based on Black Hole Algorithm
     */
    public BlachHoleOptimizer() {
        this.transferFunction = TransferFunction.V2;
        this.populationSize = 20;
        KNNTrainer<Label> KnnTrainer =  new KNNTrainer<>(3,
                new L1Distance(),
                Runtime.getRuntime().availableProcessors(),
                new VotingCombiner(),
                KNNModel.Backend.THREADPOOL,
                NeighboursQueryFactoryType.BRUTE_FORCE);
        FN = new FitnessFunction(KnnTrainer);
        this.mutationThreshould = 0.5;
        this.maxIteration = 10;
        this.seed = 12345;
        this.rng = new SplittableRandom(seed);
        this.convergenceCurve = new double[maxIteration];
    }

    /**
     * Constructs the wrapper feature selection based on cuckoo search algorithm
     * @param transferFunction The transfer function to convert continuous values to binary ones
     * @param populationSize The size of the solution in the initial population
     * @param maxIteration The number of times that is used to enhance generation
     * @param seed This seed is required for the SplittableRandom
     */
    public BlachHoleOptimizer(TransferFunction transferFunction, int populationSize, double mutationThreshould, int maxIteration, int seed) {
        this.transferFunction = transferFunction;
        this.populationSize = populationSize;
        KNNTrainer<Label> KnnTrainer =  new KNNTrainer<>(3,
                new L1Distance(),
                Runtime.getRuntime().availableProcessors(),
                new VotingCombiner(),
                KNNModel.Backend.THREADPOOL,
                NeighboursQueryFactoryType.BRUTE_FORCE);
        FN = new FitnessFunction(KnnTrainer);
        this.mutationThreshould = mutationThreshould;
        this.maxIteration = maxIteration;
        this.seed = seed;
        this.rng = new SplittableRandom(seed);
        this.convergenceCurve = new double[maxIteration];
    }

    /**
     * Constructs the wrapper feature selection based on cuckoo search algorithm
     * @param trainer The used trainer in the evaluation process
     * @param transferFunction The transfer function to convert continuous values to binary ones
     * @param populationSize The size of the solution in the initial population
     * @param maxIteration The number of times that is used to enhance generation
     * @param seed This seed is required for the SplittableRandom
     */
    public BlachHoleOptimizer(Trainer<Label> trainer, TransferFunction transferFunction, int populationSize, double mutationThreshould, int maxIteration, int seed) {
        this.transferFunction = transferFunction;
        this.populationSize = populationSize;
        FN = new FitnessFunction(trainer);
        this.mutationThreshould = mutationThreshould;
        this.maxIteration = maxIteration;
        this.seed = seed;
        this.rng = new SplittableRandom(seed);
        this.convergenceCurve = new double[maxIteration];
    }

    /**
     * Constructs the wrapper feature selection based on cuckoo search algorithm
     * @param dataPath The path of the dataset
     * @param trainer The used trainer in the evaluation process
     * @param correlation_id The used correlation function in the evaluation process
     * @param transferFunction The transfer function to convert continuous values to binary ones
     * @param populationSize The size of the solution in the initial population
     * @param maxIteration The number of times that is used to enhance generation
     * @param seed This seed is required for the SplittableRandom
     */
    public BlachHoleOptimizer(String dataPath, Trainer<Label> trainer, FitnessFunction.Correlation_Id correlation_id, TransferFunction transferFunction, int populationSize, double mutationThreshould, int maxIteration, int seed) {
        this.transferFunction = transferFunction;
        this.populationSize = populationSize;
        FN = new FitnessFunction(dataPath, trainer, correlation_id);
        this.mutationThreshould = mutationThreshould;
        this.maxIteration = maxIteration;
        this.seed = seed;
        this.rng = new SplittableRandom(seed);
        this.convergenceCurve = new double[maxIteration];
    }

    /**
     * Constructs the wrapper feature selection based on cuckoo search algorithm
     * @param dataPath The path of the dataset
     * @param correlation_id The used correlation function in the evaluation process
     * @param transferFunction The transfer function to convert continuous values to binary ones
     * @param populationSize The size of the solution in the initial population
     * @param maxIteration The number of times that is used to enhance generation
     * @param seed This seed is required for the SplittableRandom
     */
    public BlachHoleOptimizer(String dataPath, FitnessFunction.Correlation_Id correlation_id, TransferFunction transferFunction, int populationSize, double mutationThreshould, int maxIteration, int seed) {
        this.transferFunction = transferFunction;
        this.populationSize = populationSize;
        KNNTrainer<Label> KnnTrainer =  new KNNTrainer<>(3, new L1Distance(), Runtime.getRuntime().availableProcessors(), new VotingCombiner(), KNNModel.Backend.THREADPOOL, NeighboursQueryFactoryType.BRUTE_FORCE);
        FN = new FitnessFunction(dataPath, KnnTrainer, correlation_id);
        this.mutationThreshould = mutationThreshould;
        this.maxIteration = maxIteration;
        this.seed = seed;
        this.rng = new SplittableRandom(seed);
        this.convergenceCurve = new double[maxIteration];
    }

    /**
     * This method is used to generate the initial population (set of solutions)
     * @param totalNumberOfFeatures The number of features in the given dataset
     * @return The population of subsets of selected features
     */
    @Override
    public int[][] generatePopulation(int totalNumberOfFeatures) {
        //noinspection DuplicatedCode
        setOfSolutions = new int[this.populationSize][totalNumberOfFeatures];
        for (int[] subSet : setOfSolutions) {
            int[] values = new int[subSet.length];
            for (int i = 0; i < values.length; i++) {
                values[i] = rng.nextInt(2);
            }
            System.arraycopy(values, 0, subSet, 0, setOfSolutions[0].length);
        }
        return setOfSolutions;
    }

    /**
     * Does this feature selection algorithm return an ordered feature set?
     * @return True if the set is ordered.
     */
    @Override
    public boolean isOrdered() {
        return true;
    }

    /**
     * Selects features according to this selection algorithm from the specified dataset.
     * @param dataset The dataset to use.
     * @return A selected feature set.
     */
    @Override
    public SelectedFeatureSet select(Dataset<Label> dataset) {
        ImmutableFeatureMap FMap = new ImmutableFeatureMap(dataset.getFeatureMap());
        setOfSolutions = generatePopulation(FMap.size());
        List<StarsFeatureSet> subSet_fScores = Arrays.stream(setOfSolutions).map(subSet -> new StarsFeatureSet(subSet, FN.EvaluateSolution(this, dataset, FMap, subSet))).sorted(Comparator.comparing(StarsFeatureSet::score).reversed()).collect(Collectors.toList());
        int[] blackHole = new int[subSet_fScores.get(0).subSet.length];
        System.arraycopy(subSet_fScores.get(0).subSet, 0, blackHole, 0, blackHole.length);
        SelectedFeatureSet selectedFeatureSet;
        for (int iter = 0; iter < maxIteration; iter++) {
            int[] evolvedSolution = new int[setOfSolutions[0].length];
            // Update all stars according to the black hole star
            for (int[] setOfSolution : setOfSolutions) {
                for (int i = 0; i < setOfSolutions[0].length; i++) {
                    evolvedSolution[i] = (int) transferFunction.applyAsDouble(setOfSolution[i] + rng.nextDouble() * (blackHole[i] - setOfSolution[i]));
                }
                // Update each star after the modification and assign the best star as black hole
                System.arraycopy(retrieveBestAfterEvaluation(dataset, FMap, evolvedSolution, setOfSolution), 0, setOfSolution, 0, setOfSolution.length);
                System.arraycopy(retrieveBestAfterEvaluation(dataset, FMap, evolvedSolution, blackHole), 0, blackHole, 0, blackHole.length);
            }
            // Calculate the event horizon
            double sumOfTheFitnessOfAllStars = Arrays.stream(setOfSolutions).mapToDouble(subSet -> FN.EvaluateSolution(this, dataset, FMap, subSet)).sum();
            double eventHorizon = FN.EvaluateSolution(this, dataset, FMap, blackHole) / sumOfTheFitnessOfAllStars;
            // Eliminate all stars that crosse the event horizon and creat new ones in random position in the search space
            for (int[] ofSolution : setOfSolutions) {
                double[] solution = Arrays.stream(ofSolution).mapToDouble(x -> x).toArray();
                double[] BHole = Arrays.stream(blackHole).mapToDouble(x -> x).toArray();
                if (new EuclideanDistance().compute(solution, BHole) <= eventHorizon) {
                    int[] newStar = new int[blackHole.length];
                    for (int i = 0; i < blackHole.length; i++) {
                        newStar[i] = rng.nextInt(2);
                    }
                    System.arraycopy(retrieveBestAfterEvaluation(dataset, FMap, newStar, blackHole), 0, blackHole, 0, blackHole.length);
                }
                // Update the solution based on mutation according and set the best muted star as black hole if it is better than the current black hole
                if (rng.nextDouble() < mutationThreshould) {
                    evolvedSolution = this.inversionMutation(ofSolution);
                    System.arraycopy(retrieveBestAfterEvaluation(dataset, FMap, evolvedSolution, ofSolution), 0, ofSolution, 0, ofSolution.length);
                    System.arraycopy(retrieveBestAfterEvaluation(dataset, FMap, ofSolution, blackHole), 0, blackHole, 0, blackHole.length);
                }
            }
            Arrays.stream(setOfSolutions).map(subSet -> new StarsFeatureSet(subSet, FN.EvaluateSolution(this, dataset, FMap, subSet))).forEach(subSet_fScores::add);
            double sum = 0d;
            for (int[] setOfSolution : setOfSolutions) {
                sum += FN.EvaluateSolution(this, dataset, FMap, setOfSolution);
            }
            convergenceCurve[iter] = sum / populationSize;
        }
        selectedFeatureSet = FN.getSFS(this, dataset, FMap, blackHole);
        return selectedFeatureSet;
    }

    @Override
    public FeatureSelectorProvenance getProvenance() {
        return new FeatureSelectorProvenanceImpl(this);
    }

    /**
     * @param dataset The dataset to use
     * @param FMap The map of selected features
     * @param alteredSolution The modified solution
     * @param oldSolution The old solution
     */
    @Override
    public int[] retrieveBestAfterEvaluation(Dataset<Label> dataset, ImmutableFeatureMap FMap, int[] alteredSolution, int[] oldSolution) {
        if (FN.EvaluateSolution(this, dataset, FMap, alteredSolution) > FN.EvaluateSolution(this, dataset, FMap, oldSolution)) {
            return alteredSolution;
        }
        else
            return oldSolution;
    }

    /**
     * This method is used to return the average fitness score of all solutions among each iteration
     * @return the average fitness score of all solutions among each iteration
     */
    public double[] getAvgFitness() {
        return convergenceCurve;
    }

    /**
     * This record is used to hold subset of features with its corresponding fitness score
     */
    record StarsFeatureSet(int[] subSet, double score) { }
}
