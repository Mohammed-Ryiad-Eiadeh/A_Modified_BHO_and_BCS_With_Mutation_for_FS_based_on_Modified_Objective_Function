package FS_Based_on_modified_FF.Main;

import FS_Based_on_modified_FF.ConvertArffToCSV.ArffToCsvConverter;

import java.io.File;
import java.io.IOException;

public class Conver_Arff_To_CSV_Main implements ArffToCsvConverter {
    public static void main(String... args) throws IOException {
        var arffFileUrl_To_Read = "";
        var csvFileUrl_To_Save = "";
        new Conver_Arff_To_CSV_Main().convert(new File(arffFileUrl_To_Read),
                new File(csvFileUrl_To_Save));
    }
}
