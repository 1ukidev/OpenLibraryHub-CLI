package openlibraryhub.exceptions;

import openlibraryhub.entities.Entity;

public class FailedSaveException extends RuntimeException {
    public FailedSaveException(Class<? extends Entity> clazz) {
        super("Falha ao salvar " + clazz.getSimpleName() + "!");
    }
}
