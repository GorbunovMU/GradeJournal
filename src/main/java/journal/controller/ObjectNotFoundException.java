package journal.controller;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(Integer id) {
        super("Could not find object by id = " + id);
    }
}
