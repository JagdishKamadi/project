import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("github-activity ");
            String username = scanner.next();
            GitHubActivity gitHubActivity = new GitHubActivity(username);
            gitHubActivity.fetchGitHubActivity();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
