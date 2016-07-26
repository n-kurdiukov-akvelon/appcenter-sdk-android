package avalanche.core.persistence;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import avalanche.core.ingestion.models.Log;
import avalanche.core.ingestion.models.json.LogSerializer;

/**
 * Abstract class for AvalanchePersistence service.
 */
/* TODO (jaelim): Interface vs Abstract class. Need to revisit to finalize. */
public abstract class AvalanchePersistence {

    /**
     * Storage capacity in number of logs
     */
    static final int DEFAULT_CAPACITY = 300;

    /**
     * Log serializer override.
     */
    private LogSerializer mLogSerializer;

    /**
     * Writes a log to the storage with the given {@code group}.
     *
     * @param group The group of the storage for the log.
     * @param log   The log to be placed in the storage.
     * @throws PersistenceException Exception will be thrown if persistence cannot write a log to the storage.
     */
    public abstract void putLog(@NonNull String group, @NonNull Log log) throws PersistenceException;

    /**
     * Deletes a log with the give ID from the {@code group}.
     *
     * @param group The group of the storage for the log.
     * @param id    The ID for a set of logs.
     */
    public abstract void deleteLogs(@NonNull String group, @NonNull String id);

    /**
     * Deletes all logs for the given {@code group}
     *
     * @param group The group of the storage for the log.
     */
    public abstract void deleteLogs(String group);

    /**
     * Gets an array of logs for the given {@code group}.
     *
     * @param group   The group of the storage for the log.
     * @param limit   The max number of logs to be returned. {@code 0} for all logs in the storage.
     * @param outLogs A list to receive {@link Log} objects.
     * @return An ID for {@code outLogs}. {@code null} if no logs exist.
     */
    @Nullable
    public abstract String getLogs(@NonNull String group, @IntRange(from = 0) int limit, @NonNull List<Log> outLogs);

    /**
     * Clears all associations between logs and ids returned by {@link #getLogs(String, int, List)} ()}.
     */
    public abstract void clearPendingLogState();

    /**
     * Clears all logs.
     */
    public abstract void clear();

    /**
     * Gets a {@link LogSerializer}.
     *
     * @return The log serializer instance.
     */
    LogSerializer getLogSerializer() {
        if (mLogSerializer == null)
            throw new IllegalStateException("logSerializer not configured");
        return mLogSerializer;
    }

    /**
     * Sets a {@link LogSerializer}.
     *
     * @param logSerializer The log serializer instance.
     */
    public void setLogSerializer(@NonNull LogSerializer logSerializer) {
        mLogSerializer = logSerializer;
    }

    /**
     * Thrown when {@link AvalanchePersistence} cannot write a log to the storage.
     */
    public static class PersistenceException extends Exception {
        public PersistenceException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }
    }
}