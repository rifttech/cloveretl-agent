package ru.krista.etl.cloveretl.agent.identifier;

import java.util.prefs.Preferences;

/**
 * Stores Pump Identifier
 */
public class IdentifierStorage {
    private Preferences preferences;
    private static final String RUN_ID = "RUN_ID";
    private static final long MIN_RUN_ID = 0L;

    public IdentifierStorage() {
        this.preferences = Preferences.userRoot().node(this.getClass().getName());
        long value = preferences.getLong(RUN_ID, -1L);
        if (value == -1){
            preferences.putLong(RUN_ID, value + 1L);
        } else if (value == Long.MAX_VALUE){
            preferences.putLong(RUN_ID, MIN_RUN_ID);
        }

    }

    public void reset() {
        preferences.putLong(RUN_ID, MIN_RUN_ID);
    }

    public long nextLong(){
        long value = preferences.getLong(RUN_ID, -1L);
        if (value == -1L){
            preferences.putLong(RUN_ID, MIN_RUN_ID);
            return MIN_RUN_ID;
        }

        preferences.putLong(RUN_ID, value + 1L);
        return value;
    }

}
