package FS_Based_on_modified_FF.Main;

import FS_Based_on_modified_FF.Discreeting.TransferFunction;
import FS_Based_on_modified_FF.Evaluation.FitnessFunction;
import FS_Based_on_modified_FF.Optimizers.BlachHoleOptimizer;
import com.oracle.labs.mlrg.olcut.provenance.ProvenanceUtil;
import org.tribuo.MutableDataset;
import org.tribuo.Trainer;
import org.tribuo.classification.LabelFactory;
import org.tribuo.classification.ensemble.VotingCombiner;
import org.tribuo.classification.evaluation.LabelEvaluator;
import org.tribuo.common.nearest.KNNModel;
import org.tribuo.common.nearest.KNNTrainer;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.data.csv.CSVSaver;
import org.tribuo.dataset.SelectedFeatureDataset;
import org.tribuo.evaluation.CrossValidation;
import org.tribuo.math.distance.L1Distance;
import org.tribuo.math.neighbour.NeighboursQueryFactoryType;
import org.tribuo.util.Util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BHoleMain {
    public static void main(String... args) throws IOException {
        // read the data
        // noinspection DuplicatedCode
        var dataPath = "...csv";
        var data = new CSVLoader<>(new LabelFactory()).loadDataSource(Paths.get(dataPath), "Class");
        var dataSet = new MutableDataset<>(data);

        var optimizer = new BlachHoleOptimizer(dataPath,
                FitnessFunction.Correlation_Id.SpearmansCorrelation,
                TransferFunction.V2,
                10,
                0.5,
                25,
                12345);

        //noinspection DuplicatedCode
        var sDate = System.currentTimeMillis();
        var SFS = optimizer.select(dataSet);
        var eDate = System.currentTimeMillis();
        var SFDS = new SelectedFeatureDataset<>(dataSet, SFS);
       // display the selected subset provenance 
        var dataInfo = SFDS.getProvenance();
        System.out.println("The selected subset provenance is \n" + ProvenanceUtil.formattedProvenanceString(dataInfo.getSourceProvenance()));

        // use KNN classifier
        // noinspection DuplicatedCode
        var KnnTrainer =  new KNNTrainer<>(3,
                new L1Distance(),
                Runtime.getRuntime().availableProcessors(),
                new VotingCombiner(),
                KNNModel.Backend.THREADPOOL,
                NeighboursQueryFactoryType.BRUTE_FORCE);

        // disply the model provenance
        var modelProvenance = KnnTrainer.getProvenance();
        System.out.println("The model provenance is \n" + ProvenanceUtil.formattedProvenanceString(modelProvenance));

        // use crossvalidation
        // noinspection DuplicatedCode
        var crossValidation = new CrossValidation<>(KnnTrainer, SFDS, new LabelEvaluator(), 10, Trainer.DEFAULT_SEED);

        // get outputs
        // noinspection DuplicatedCode
        var avgAcc = 0d;
        var sensitivity = 0d;
        var macroAveragedF1 = 0d;
        var sTrain = System.currentTimeMillis();
        for (var acc: crossValidation.evaluate()) {
            avgAcc += acc.getA().accuracy();
            sensitivity += acc.getA().tp() / (acc.getA().tp() + acc.getA().fn());
            macroAveragedF1 += acc.getA().macroAveragedF1();
        }
        // noinspection DuplicatedCode
        var eTrain = System.currentTimeMillis();

        System.out.printf("The FS duration time is : %s\nThe number of selected features is : %d\nThe feature names are : %s\n",
                Util.formatDuration(sDate, eDate), SFS.featureNames().size(), SFS.featureNames());
        for (var stuff : List.of("The Training_Testing duration time is : " + Util.formatDuration(sTrain, eTrain),
                "The average accuracy is : " + (avgAcc / crossValidation.getK()),
                "The average sensitivity is : " + (sensitivity / crossValidation.getK()),
                "The average macroAveragedF1 is : " + (macroAveragedF1 / crossValidation.getK()))) {
            System.out.println(stuff);
        }
        // noinspection DuplicatedCode
        System.out.print("The average fitness scores are : ");
        Arrays.stream(optimizer.getAvgFitness()).forEach(i -> System.out.print(i + ", "));
        System.out.println("\b\b");

        // noinspection DuplicatedCode
        System.out.println("Please Type Y to save the new set of features or other to ignore that");
        var inputs = new Scanner(System.in);
        if (inputs.nextLine().equals("Y")) {
            System.out.println("Type the file name please");
            var saveToThisPath = System.getProperty("user.dir").concat("\\");
            System.out.println("Name the file please!");
            var dataName = inputs.nextLine();
            new CSVSaver().save(Paths.get(saveToThisPath + dataName + ".csv"), SFDS, "Class");
        }
    }
}
