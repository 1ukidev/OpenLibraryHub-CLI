package openlibraryhub.exceptions;

import openlibraryhub.annotations.EntityName;
import openlibraryhub.entities.Entity;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<? extends Entity> clazz) {
        super(getNotFoundMessage(clazz));
    }

    private static String getNotFoundMessage(Class<? extends Entity> clazz) {
        String entityName = getEntityName(clazz);

        if (entityName.endsWith("a") | entityName.endsWith("de")) {
            return entityName + " não encontrada!";
        }

        return entityName + " não encontrado!";
    }

    private static String getEntityName(Class<? extends Entity> clazz) {
        EntityName entityNameAnnotation = clazz.getAnnotation(EntityName.class);

        if (entityNameAnnotation != null) {
            return entityNameAnnotation.value();
        }

        return "Entidade";
    }
}
