package FS_Based_on_modified_FF.ConvertArffToCSV;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.CSVSaver;

import java.io.File;
import java.io.IOException;

/**
 * This interface has one default method that is used to convert .arff file to .csv file
 */
public interface ArffToCsvConverter {
    /**
     * This method is used to convert .arff to .csv
     * @param arrf_URL The url of the arff file
     * @param csvL_URL The url of the csv file
     * @throws IOException Throws IOException once there is no such file (arrf_URL) to read
     */
    default void convert(File arrf_URL, File csvL_URL) throws IOException {
        // Read and Load the Arff file
        ArffLoader loader = new ArffLoader();
        loader.setSource(arrf_URL);
        Instances data = loader.getDataSet();
        // Save the converted file (CSV format)
        CSVSaver saver = new CSVSaver();
        saver.setInstances(data);
        saver.setFile(csvL_URL);
        saver.writeBatch();
    }
}
