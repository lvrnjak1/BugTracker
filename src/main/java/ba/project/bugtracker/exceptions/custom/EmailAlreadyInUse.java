package ba.project.bugtracker.exceptions.custom;

public class EmailAlreadyInUse extends RuntimeException {
    public EmailAlreadyInUse(String s) {
        super(s);
    }
}
