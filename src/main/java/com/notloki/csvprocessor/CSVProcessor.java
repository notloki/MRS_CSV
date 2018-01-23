package com.notloki.csvprocessor;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;



public class CSVProcessor {


    // private static String fileName = "src/main/resources/in.csv";
    public static void main(String args[] ) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {



        FileDialog dialog = new FileDialog((Frame)null, "Select CSV File to Open");
        dialog.setFile("*.csv");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);

        String fileName = dialog.getDirectory() + dialog.getFile();
        if(fileName.equals("nullnull")) {
            System.out.println("You failed to pick");
            System.exit(0);
        }
        System.out.println(fileName + " chosen.");

        CSVReader reader = new CSVReader(new FileReader(fileName), ',','`',  1);


        List<CutList> cuts = new ArrayList<>();

        String[] record = null;

        while((record = reader.readNext()) != null) {

            CutList cut = new CutList();
                String[] fixed = record[0].split(" "); // Split record by spaces
                if(Character.isDigit(fixed[0].charAt(0))) { // if 2nd Character is a number
                cut.setQty(fixed[0]);
                if(fixed.length == 3) { // If only feet are represented
                    cut.setLength(fixed[2]);
                    String tmp = fixed[2];
                    tmp = tmp.replace(tmp.substring(tmp.length()-1), "");
                    DecimalFormat decimalFormat = new DecimalFormat("#.000");

                    float temp = Float.parseFloat(tmp);
                    cut.setInches(decimalFormat.format(temp * 12));

                } else if(fixed.length == 4) { // if feet and inches are represented
                    cut.setLength(fixed[fixed.length -2] + fixed[fixed.length -1]);
                    String tmp = fixed[2];
                    tmp = tmp.replace(tmp.substring(tmp.length()-1), "");
                    float temp = Float.parseFloat(tmp);
                    String tmp2 = fixed[3];
                    tmp2 = tmp2.replace(tmp2.substring(tmp2.length()-1), "");
                    DecimalFormat decimalFormat = new DecimalFormat("#.000");
                    cut.setInches(decimalFormat.format((temp * 12) + Float.parseFloat(tmp2)));
                }

                cuts.add(cut);
            }
        }
        String[] header = {"Qty", "Length", "Stop", "QtyMade", "ProductCode", "CoilCode", "Inch"};
        System.out.println(Arrays.toString(header));
        System.out.println(cuts);

        reader.close();

        FileDialog writeDialog = new FileDialog((Frame)null, "Save");
        writeDialog.setMode(FileDialog.SAVE);
        writeDialog.setVisible(true);
        writeDialog.setDirectory("\\\\10.100.89.245\\mrs\\");
        String outFileName = writeDialog.getDirectory() + writeDialog.getFile();
        if(outFileName.equals("nullnull")) {
            System.out.println("Failed to Choose FileName");
            System.exit(0);
        }
        System.out.println(outFileName + " chosen.");
        if(!(outFileName.endsWith(".csv"))) {
            outFileName = outFileName + ".csv";
        }


        CSVWriter csvWriter = new CSVWriter(new FileWriter(outFileName), ',', ' ', ' ', "\n");

        List<String[]> data = toStringArray(cuts);
        csvWriter.writeAll(data, false);
        csvWriter.close();
        System.exit(0);

    }

    private static List<String[]> toStringArray(List<CutList> cutLists) {
        List<String[]> records = new ArrayList<String[]>();
        records.add(new String[] {"Qty", "Length", "Stop", "QtyMade", "ProductCode", "CoilCode", "Inch"});

        Iterator<CutList> it = cutLists.iterator();
        while(it.hasNext()) {
            CutList cutList = it.next();
            records.add(new String[] { cutList.toString()});
        }
        return records;
    }

}
