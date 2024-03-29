package FS_Based_on_modified_FF.Optimizers;

import FS_Based_on_modified_FF.Discreeting.TransferFunction;
import FS_Based_on_modified_FF.Evaluation.FitnessFunction;
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

/**
 * Select features based on Cuckoo Search algorithm with binary transfer functions, a given classifier (default is KNN, K = 1) and 10-fold cross validation
 * <p>
 * see:
 * <pre>
 * Xin-She Yang and Suash Deb.
 * "Cuckoo Search via L´evy Flights", 2010.
 *
 * L. A. M. Pereira et al.
 * "A Binary Cuckoo Search and its Application for Feature Selection", 2014.
 * </pre>
 */
public  final class CuckooSearchOptimizer implements Optimizers {
    private final TransferFunction transferFunction;
    private final double stepSizeScaling;
    private final double lambda;
    private final double worstNestProbability;
    private final double delta;
    private final int populationSize;
    private int [][] setOfSolutions;
    private final FitnessFunction FN;
    private final double mutationThreshould;
    private final int maxIteration;
    private final SplittableRandom rng;
    private final int seed;
    private final double[] convergenceCurve;

    /**
     * The default constructor for feature selection based on Cuckoo Search Algorithm
     */
    public CuckooSearchOptimizer() {
        this.transferFunction = TransferFunction.V2;
        this.populationSize = 50;
        KNNTrainer<Label> KnnTrainer =  new KNNTrainer<>(3,
                new L1Distance(),
                Runtime.getRuntime().availableProcessors(),
                new VotingCombiner(),
                KNNModel.Backend.THREADPOOL,
                NeighboursQueryFactoryType.BRUTE_FORCE);
        FN = new FitnessFunction(KnnTrainer);
        this.stepSizeScaling = 2d;
        this.lambda = 2d;
        this.worstNestProbability = 0.1d;
        this.delta = 1.5d;
        this.mutationThreshould = 0.5;
        this.maxIteration = 30;
        this.seed = 12345;
        this.rng = new SplittableRandom(seed);
        this.convergenceCurve = new double[maxIteration];
    }

    /**
     * Constructs the wrapper feature selection based on cuckoo search algorithm
     * @param transferFunction The transfer function to convert continuous values to binary ones
     * @param populationSize The size of the solution in the initial population
     * @param stepSizeScaling The cuckoo step size
     * @param lambda The lambda of the levy flight function
     * @param worstNestProbability The fraction of the nests to be abandoned
     * @param delta The delta that is used in the abandon nest function
     * @param mutationThreshould The probability to decide wheather to apply mutation on this solution or not
     * @param maxIteration The number of times that is used to enhance generation
     * @param seed This seed is required for the SplittableRandom
     */
    public CuckooSearchOptimizer(TransferFunction transferFunction, int populationSize, double stepSizeScaling, double lambda, double worstNestProbability, double delta, double mutationThreshould, int maxIteration, int seed) {
        this.transferFunction = transferFunction;
        this.populationSize = populationSize;
        KNNTrainer<Label> KnnTrainer =  new KNNTrainer<>(3,
                new L1Distance(),
                Runtime.getRuntime().availableProcessors(),
                new VotingCombiner(),
                KNNModel.Backend.THREADPOOL,
                NeighboursQueryFactoryType.BRUTE_FORCE);
        FN = new FitnessFunction(KnnTrainer);
        this.stepSizeScaling = stepSizeScaling;
        this.lambda = lambda;
        this.worstNestProbability = worstNestProbability;
        this.delta = delta;
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
    public CuckooSearchOptimizer(Trainer<Label> trainer, TransferFunction transferFunction, int populationSize, int maxIteration, int seed) {
        this.transferFunction = transferFunction;
        this.populationSize = populationSize;
        FN = new FitnessFunction(trainer);
        this.stepSizeScaling = 2d;
        this.lambda = 2d;
        this.worstNestProbability = 1.5d;
        this.delta = 1.5d;
        this.mutationThreshould = 0.5;
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
     * @param stepSizeScaling The cuckoo step size
     * @param lambda The lambda of the levy flight function
     * @param worstNestProbability The fraction of the nests to be abandoned
     * @param delta The delta that is used in the abandon nest function
     * @param mutationThreshould The probability to decide wheather to apply mutation on this solution or not
     * @param maxIteration The number of times that is used to enhance generation
     * @param seed This seed is required for the SplittableRandom
     */
    public CuckooSearchOptimizer(Trainer<Label> trainer, TransferFunction transferFunction, int populationSize, double stepSizeScaling, double lambda, double worstNestProbability, double delta, double mutationThreshould, int maxIteration, int seed) {
        this.transferFunction = transferFunction;
        this.populationSize = populationSize;
        FN = new FitnessFunction(trainer);
        this.stepSizeScaling = stepSizeScaling;
        this.lambda = lambda;
        this.worstNestProbability = worstNestProbability;
        this.delta = delta;
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
     * @param stepSizeScaling The cuckoo step size
     * @param lambda The lambda of the levy flight function
     * @param worstNestProbability The fraction of the nests to be abandoned
     * @param delta The delta that is used in the abandon nest function
     * @param mutationThreshould The probability to decide wheather to apply mutation on this solution or not
     * @param maxIteration The number of times that is used to enhance generation
     * @param seed This seed is required for the SplittableRandom
     */
    public CuckooSearchOptimizer(String dataPath, Trainer<Label> trainer, FitnessFunction.Correlation_Id correlation_id, TransferFunction transferFunction, int populationSize, double stepSizeScaling, double lambda, double worstNestProbability, double delta, double mutationThreshould, int maxIteration, int seed) {
        this.transferFunction = transferFunction;
        this.populationSize = populationSize;
        FN = new FitnessFunction(dataPath, trainer, correlation_id);
        this.stepSizeScaling = stepSizeScaling;
        this.lambda = lambda;
        this.worstNestProbability = worstNestProbability;
        this.delta = delta;
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
     * @param stepSizeScaling The cuckoo step size
     * @param lambda The lambda of the levy flight function
     * @param worstNestProbability The fraction of the nests to be abandoned
     * @param delta The delta that is used in the abandon nest function
     * @param mutationThreshould The probability to decide wheather to apply mutation on this solution or not
     * @param maxIteration The number of times that is used to enhance generation
     * @param seed This seed is required for the SplittableRandom
     */
    public CuckooSearchOptimizer(String dataPath, FitnessFunction.Correlation_Id correlation_id, TransferFunction transferFunction, int populationSize, double stepSizeScaling, double lambda, double worstNestProbability, double delta, double mutationThreshould, int maxIteration, int seed) {
        this.transferFunction = transferFunction;
        this.populationSize = populationSize;
        KNNTrainer<Label> KnnTrainer =  new KNNTrainer<>(3,
                new L1Distance(),
                Runtime.getRuntime().availableProcessors(),
                new VotingCombiner(),
                KNNModel.Backend.THREADPOOL,
                NeighboursQueryFactoryType.BRUTE_FORCE);
        FN = new FitnessFunction(dataPath, KnnTrainer, correlation_id);
        this.stepSizeScaling = stepSizeScaling;
        this.lambda = lambda;
        this.worstNestProbability = worstNestProbability;
        this.delta = delta;
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
     *
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
        List<CuckooSearchFeatureSet> subSet_fScores = new ArrayList<>();
        SelectedFeatureSet selectedFeatureSet;
        for (int iter = 0; iter < maxIteration; iter++) {
            for (int solution = 0; solution < setOfSolutions.length; solution++) {
                int[] evolvedSolution = new int[setOfSolutions[0].length];
                // Update the solution based on the levy flight function 0.5 + 0.8 * (new Random().nextGaussian() / Math.pow(Math.abs(new Random().nextGaussian()), 1.0 / 3.0))
                for (int i = 0; i < setOfSolutions[0].length; i++) {
                    evolvedSolution[i] = (int) transferFunction.
                            applyAsDouble(setOfSolutions[solution][i] + stepSizeScaling * Math.pow(solution + 1, -lambda));
                }
                int randomCuckooIndex = rng.nextInt(setOfSolutions.length);
                System.arraycopy(retrieveBestAfterEvaluation(dataset, FMap, evolvedSolution, setOfSolutions[randomCuckooIndex]),
                        0, setOfSolutions[randomCuckooIndex], 0, setOfSolutions[randomCuckooIndex].length);
                // Update the solution based on the abandone nest function
                if (new Random().nextDouble() < worstNestProbability) {
                    int r1 = rng.nextInt(setOfSolutions.length);
                    int r2 = rng.nextInt(setOfSolutions.length);
                    for (int j = 0; j < setOfSolutions[0].length; j++) {
                        evolvedSolution[j] = (int) transferFunction.
                                applyAsDouble(setOfSolutions[solution][j] + delta * (setOfSolutions[r1][j] - setOfSolutions[r2][j]));
                    }
                    System.arraycopy(retrieveBestAfterEvaluation(dataset, FMap, evolvedSolution, setOfSolutions[solution]),
                            0, setOfSolutions[solution], 0, setOfSolutions[solution].length);
                }
                // Update the solution based on mutation according to some mutation rate
                if (rng.nextDouble() < mutationThreshould) {
                    evolvedSolution = this.inversionMutation(setOfSolutions[solution]);
                    System.arraycopy(retrieveBestAfterEvaluation(dataset, FMap, evolvedSolution, setOfSolutions[solution]),
                            0, setOfSolutions[solution], 0, setOfSolutions[solution].length);
                }
            }
            Arrays.stream(setOfSolutions).
                    map(subSet -> new CuckooSearchFeatureSet(subSet, FN.EvaluateSolution(this, dataset, FMap, subSet))).
                    forEach(subSet_fScores::add);
            double sum = 0d;
            for (int[] setOfSolution : setOfSolutions) {
                sum += FN.EvaluateSolution(this, dataset, FMap, setOfSolution);
            }
            convergenceCurve[iter] = sum / populationSize;
        }
        subSet_fScores.sort(Comparator.comparing(CuckooSearchFeatureSet::score).reversed());
        selectedFeatureSet = FN.getSFS(this, dataset, FMap, subSet_fScores.get(0).subSet);
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
        double scoreOfSolution = FN.EvaluateSolution(this, dataset, FMap, alteredSolution);
        double scoreOfModifiedSolution = FN.EvaluateSolution(this, dataset, FMap, oldSolution);
        if (scoreOfSolution > scoreOfModifiedSolution) {
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
    record CuckooSearchFeatureSet(int[] subSet, double score) { }
}