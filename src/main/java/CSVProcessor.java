import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CSVProcessor {

    private static String fileName = "src/main/resources/in.csv";
    public static void main(String args[] ) throws IOException {

        CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build();

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
                    int temp = Integer.parseInt(tmp);
                    cut.setInches(Integer.toString(temp * 12));

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
    }
}
