package org.azul.telemetry.data.model;


public enum EventType {
    STARTUP(Names.STARTUP), SHUTDOWN(Names.SHUTDOWN), UPDATE(Names.UPDATE);

    private final String label;

    EventType(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }

    public static class Names {
        public static final String STARTUP = "STARTUP";
        public static final String SHUTDOWN = "SHUTDOWN";
        public static final String UPDATE = "UPDATE";
    }
}
