package pl.opinion_collector.backend.database_communication.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class EntityPreUpdater<T> {

    Logger logger = LoggerFactory.getLogger(EntityPreUpdater.class);

    @PersistenceContext
    private EntityManager entityManager;

    public void saveOldData(T oldEntity) {

        String className = oldEntity.getClass().getSimpleName();

        // Check if table exists
        var tableExists = entityManager.createNativeQuery(
                "SELECT tablename FROM pg_tables WHERE tablename = 'old_%s'"
                        .formatted(className.toLowerCase())
        ).getResultList();

        if (tableExists.isEmpty()) {

            entityManager.createNativeQuery(
                    "CREATE TABLE IF NOT EXISTS old_%s AS SELECT * FROM %s WHERE 1 = 0"
                            .formatted(className, className)
            ).executeUpdate();

            entityManager.createNativeQuery(
                    "ALTER TABLE old_%s ADD COLUMN modification_date TIMESTAMP"
                            .formatted(className)
            ).executeUpdate();

        }

        String getterMethod = "get" + className + "Id";

        // ID field name with its value
        Pair<String, Long> id;
        try {
            id = Pair.of(
                    className.toLowerCase() + "_id",
                    (Long) oldEntity.getClass().getMethod(getterMethod).invoke(oldEntity)
            );
        } catch (ReflectiveOperationException e) {
            logger.error("Unable to create table with old data", e);
            return;
        }

        // Insert old value to new table
        entityManager.createNativeQuery(
                        "INSERT INTO old_%s SELECT *, CURRENT_TIMESTAMP FROM %s WHERE %s = :%s"
                                .formatted(className, className, id.getFirst(), id.getFirst())
                ).setParameter(id.getFirst(), id.getSecond())
                .executeUpdate();

    }
}
