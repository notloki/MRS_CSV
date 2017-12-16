import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class CSVProcessor {

    private static String fileName = "src/main/resources/in.csv";
    public static void main(String args[] ) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

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
                    float temp = Float.parseFloat(tmp);
                    cut.setInches(Float.toString(temp * 12));

                } else if(fixed.length == 4) { // if feet and inches are represented
                    cut.setLength(fixed[fixed.length -2] + fixed[fixed.length -1]);
                    String tmp = fixed[2];
                    tmp = tmp.replace(tmp.substring(tmp.length()-1), "");
                    int temp = Integer.parseInt(tmp);
                    String tmp2 = fixed[3];
                    tmp2 = tmp2.replace(tmp2.substring(tmp2.length()-1), "");
                    cut.setInches(Integer.toString((temp * 12) + Integer.parseInt(tmp2)));
                }
                //System.out.println(Arrays.toString(fixed));

                cuts.add(cut);
            }
        }
        String[] header = {"Qty", "Length", "Stop", "QtyMade", "ProductCode", "CoilCode", "Inch"};
        System.out.println(Arrays.toString(header));
        System.out.println(cuts);

        reader.close();

        String outFilename = "src/main/resources/out.csv";

        CSVWriter csvWriter = new CSVWriter(new FileWriter(outFilename), ',', ' ');
        //csvWriter.writeNext(header, false);

        List<String[]> data = toStringArray(cuts);
        csvWriter.writeAll(data, false);
        csvWriter.close();

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
