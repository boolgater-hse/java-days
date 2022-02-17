import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        Path path = Paths.get(scanner.next());

        if (Files.isDirectory(path.toAbsolutePath()) || !Files.exists(path.toAbsolutePath())) {
            throw new FileNotFoundException("File not found or directory entered");
        }

        File in = new File(String.valueOf(path.toAbsolutePath()));

        InputStream inputStream = new FileInputStream(in);
        int size = inputStream.available();

        HashMap<Character, Integer> distributions = new HashMap<>();
        for (int i = 0; i < size; ++i) {
            char temp = (char) inputStream.read();
            if (Character.isLetter(temp)) {
                distributions.putIfAbsent(temp, 0);
                distributions.put(temp, distributions.get(temp) + 1);
            }
        }

        File out = new File(path.getParent() + "\\out.txt");
        OutputStream outputStream = new FileOutputStream(out);
        for (Character key : distributions.keySet()) {
            outputStream.write((key + " = " + distributions.get(key) + '\n').getBytes(StandardCharsets.UTF_8));
        }

        inputStream.close();
        outputStream.close();
    }
}
