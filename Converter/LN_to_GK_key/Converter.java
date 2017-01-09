import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Converter {
    public static void main(String[] args) {

        final String fileName = "conversion.txt";

        FileReader inputStream = null;
        FileWriter outputStream = null;

        try {
            inputStream = new FileReader(fileName);
            outputStream = new FileWriter("output.txt");
            String output = "foo";

            Scanner s = new Scanner(inputStream).useDelimiter("\\A");

            if (s.hasNext()) {
                output = s.next();
            }

            //System.out.println(output);
            //System.out.println(output.replaceAll("[\\n\\r]+", "\"\n\"\":\""));

            output = output.replaceAll("[\\n\\r]+", "\",\n\"\":\"");
            output = "{\"\":\"" + output + "\"}";

            System.out.println(output);

            outputStream.write(output);

            if (outputStream != null) {
                outputStream.close();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}