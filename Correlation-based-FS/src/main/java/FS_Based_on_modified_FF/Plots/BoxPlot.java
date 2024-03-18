package FS_Based_on_modified_FF.Plots;

import org.knowm.xchart.*;
import org.knowm.xchart.style.BoxStyler;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

public class BoxPlot {
    private final BoxChart plot;

    /**
     * This is the constructor for displaying the box plot of the given data
     * @param firstVectorFS The data resulted from first algorithm
     * @param secondVectorFS The data resulted from second algorithm
     * @param firstVectorFSCorrelation The data resulted from first algorithm
     * @param secondVectorFSCorrelation The data resulted from second algorithm
     */
    public BoxPlot(String name, HashMap<String, Double[]> firstVectorFS, HashMap<String, Double[]> secondVectorFS, HashMap<String, Double[]> firstVectorFSCorrelation, HashMap<String, Double[]> secondVectorFSCorrelation) {
        this.plot = new BoxChartBuilder().
                title(name).
                width(600).
                height(400).
                yAxisTitle("Fitness Score").
                theme(Styler.ChartTheme.Matlab).
                build();
        plot.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        plot.getStyler().setBoxplotCalCulationMethod(BoxStyler.BoxplotCalCulationMethod.NP);
        // fill data to the chart object
        String s1 = firstVectorFS.keySet().toArray(String[]::new)[0];
        String s2 = secondVectorFS.keySet().toArray(String[]::new)[0];
        plot.addSeries(s1, Arrays.stream(firstVectorFS.get(s1)).mapToDouble(i -> i).toArray());
        plot.addSeries(s2, Arrays.stream(secondVectorFS.get(s2)).mapToDouble(i -> i).toArray());
        String s11 = firstVectorFSCorrelation.keySet().toArray(String[]::new)[0];
        String s22 = secondVectorFSCorrelation.keySet().toArray(String[]::new)[0];
        plot.addSeries(s11, Arrays.stream(firstVectorFSCorrelation.get(s11)).mapToDouble(i -> i).toArray());
        plot.addSeries(s22, Arrays.stream(secondVectorFSCorrelation.get(s22)).mapToDouble(i -> i).toArray());
    }

    /**
     * This method is used to display the box plot chart
     */
    public void Show() {
        new SwingWrapper<>(plot).displayChart();
    }

    /**
     * This method is used to save the box plot
     * @param path The path to save the box plot into
     */
    public void savePlot(Path path) {
        try {
            VectorGraphicsEncoder.saveVectorGraphic(this.plot, path.toString(), VectorGraphicsEncoder.VectorGraphicsFormat.SVG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
