import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Main {
    /*
      Config parsing

      config.txt takes 3 params:
      elevator (int) - number of elevators in building
      floor (int) - number of floors in building
      capacity (int) - capacity of single elevator
     */
    public static HashMap<String, Integer> parseConfig() throws IOException {
        File file = new File("config.txt");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()));

        HashMap<String, Integer> config = new HashMap<>();

        String buffer;
        while ((buffer = bufferedReader.readLine()) != null) {
            if (buffer.contains("#")) {
                continue;
            }
            String[] temp = buffer.toLowerCase().trim().split("\\s+");
            if (temp.length >= 2) {
                try {
                    config.put(temp[0], Integer.parseInt(temp[1]));
                } catch (NumberFormatException e) {
                    bufferedReader.close();
                    throw new NumberFormatException("config.txt: Wrong parameters");
                }
            }
        }
        bufferedReader.close();

        return config;
    }

    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> config;
        try {
            config = parseConfig();
        } catch (IOException e) {
            throw new IOException("Problem with config.txt access");
        }

        Building building = new Building(
                config.get("floor"),
                config.get("elevator"),
                config.get("capacity")
        );
    }
}
