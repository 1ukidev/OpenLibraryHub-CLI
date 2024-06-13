package openlibraryhub.exceptions;

import openlibraryhub.entities.Entity;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<? extends Entity> clazz) {
        super(clazz.getSimpleName() + " não encontrada!");
    }
}
