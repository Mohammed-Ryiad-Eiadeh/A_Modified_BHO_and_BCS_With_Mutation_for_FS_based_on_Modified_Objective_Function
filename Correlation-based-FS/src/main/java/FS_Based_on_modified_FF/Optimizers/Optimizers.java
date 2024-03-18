package FS_Based_on_modified_FF.Optimizers;

import org.tribuo.Dataset;
import org.tribuo.FeatureSelector;
import org.tribuo.ImmutableFeatureMap;
import org.tribuo.classification.Label;

import java.util.Random;

/**
 * This in an interface which contains several methods a cross all optimization algorithm we used
 */
public sealed interface Optimizers extends FeatureSelector<Label> permits BlachHoleOptimizer, CuckooSearchOptimizer {
    int[][] generatePopulation(int totalNumberOfFeatures);
    int[] retrieveBestAfterEvaluation(Dataset<Label> dataset, ImmutableFeatureMap FMap, int[] alteredSolution, int[] oldSolution);
    double[] getAvgFitness();

    /**
     * The inversion mutation
     * <p>
     * see:
     * <pre>
     * Nitashs Soni and Tapsa Kumar.
     * "Study of Various Mutation Operators in Genetic Algorithms", 2014.
     * </pre>
     * @param currentSolution The solution to be altered by the mutation operator
     * @return The altered solution after inversion mutation
     */
    default int[] inversionMutation(int[] currentSolution) {
        int[] solution = new int[currentSolution.length];
        System.arraycopy(currentSolution, 0, solution, 0, solution.length);
        int rand1 = new Random().nextInt(solution.length);
        int rand2 = new Random().nextInt(solution.length);
        while (rand1 >= rand2) {
            rand1 = new Random().nextInt(solution.length);
            rand2 = new Random().nextInt(solution.length);
        }
        for (; rand1 < rand2; rand1++) {
            solution[rand1] = 1 - solution[rand1];
        }
        return solution;
    }
}
