import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String search;
        search = scanner.nextLine();

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("action", "query");
        parameters.put("prop", "extracts");
        parameters.put("format", "json");
        parameters.put("exintro", "");
        parameters.put("titles", search);

        StringBuilder urlText = new StringBuilder("https://ru.wikipedia.org/w/api.php?");
        StringBuilder parsedParams = new StringBuilder();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (entry.getValue().isEmpty()) {
                parsedParams.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                parsedParams.append("&");
            } else {
                parsedParams.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
                parsedParams.append("=");
                parsedParams.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
                parsedParams.append("&");
            }
        }
        if (parsedParams.length() > 0) {
            urlText.append(parsedParams.substring(0, parsedParams.length() - 1));
        }

        URL url = new URL(urlText.toString());
        StringBuilder response = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line.trim());
        }
        bufferedReader.close();

        JSONObject json = new JSONObject(response.toString());
        JSONObject query = json.getJSONObject("query");
        JSONObject pages = query.getJSONObject("pages");
        for (String key : pages.keySet()) {
            if (key.equals("-1")) {
                System.out.println("Nothing found");
                break;
            }
            JSONObject page = pages.getJSONObject(key);
            String extract = page.getString("extract")
                    .replaceAll("<[^>]*>", "");
            System.out.println(extract);
        }

        scanner.close();
    }
}
