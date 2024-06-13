package openlibraryhub.exceptions;

import openlibraryhub.entities.Entity;

public class FailedUpdateException extends RuntimeException {
    public FailedUpdateException(Class<? extends Entity> clazz) {
        super("Falha ao atualizar " + clazz.getSimpleName() + "!");
    }
}
