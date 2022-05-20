package org.azul.telemetry.data.model;


/**
 * Different event types that can be received from agent.
 */
public enum EventType {
    STARTUP(Names.STARTUP), SHUTDOWN(Names.SHUTDOWN), UPDATE(Names.UPDATE), CLASSLOAD(Names.CLASSLOAD);

    private final String label;

    EventType(String label) {
        this.label = label;
    }

    public String toString() {
        return this.label;
    }

    /**
     * Event names constants.
     */
    public static class Names {
        public static final String STARTUP = "STARTUP";
        public static final String SHUTDOWN = "SHUTDOWN";
        public static final String UPDATE = "UPDATE";
        public static final String CLASSLOAD = "CLASSLOAD";
    }
}
