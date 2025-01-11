public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found for this username");
    }

    public UserNotFoundException(String username) {
        super("User not found for " + username);
    }
}
