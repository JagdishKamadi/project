import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class GitHubActivity {
    private final String username;
    private final HttpClient client;

    public GitHubActivity(String username) {
        this.username = username;
        client = HttpClient.newHttpClient();
    }

    public void fetchGitHubActivity() {
        String gitHubAPIUrl = "https://api.github.com/users/" + username + "/events";
        try {
            // created request
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(gitHubAPIUrl))
                    .GET()
                    .build();

            // got the response
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (404 == response.statusCode()) {
                throw new UserNotFoundException(username);
            }
            if (200 == response.statusCode()) {
                displayActivity(response);

            } else {
                System.out.println("Error:" + response.statusCode());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println(ex.getMessage());
        }

    }

    public void displayActivity(HttpResponse<String> response) {
        Map<String, Integer> map = new HashMap<>();
        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        jsonArray.forEach(jsonElement -> {
            String key = jsonElement.getAsJsonObject().get("type").getAsString();
            map.put(key, map.getOrDefault(key, 0) + 1);
        });

        System.out.println("Output:");

        // Get the branch name in below
        String branchName = jsonArray.get(0).getAsJsonObject().get("repo").getAsJsonObject().get("name").getAsString();
        if (map.containsKey("PushEvent")) {
            System.out.println("Pushed " + map.get("PushEvent") + " commits to /" + branchName);
        }
        if (map.containsKey("IssuesEvent")) {
            System.out.println("Opened a new issue in /" + branchName);
        }

        if (map.containsKey("WatchEvent")) {
            System.out.println("Starred " + branchName);
        }

        if (map.containsKey("CreateEvent")) {
            System.out.println("Created " + map.get("CreateEvent") + " in /" + branchName);
        }

        if (map.containsKey("DeleteEvent")) {
            System.out.println("Deleted " + map.get("DeleteEvent") + " in /" + branchName);
        }

        if (map.containsKey("ForkEvent")) {
            System.out.println("Forked " + branchName);
        }
    }
}

