package com.spata14.trelloproject.Notification.entity.enums;

/**
 * Enumeration representing various event types in the system.
 * <p>
 * Each event type is associated with a string identifier.
 * </p>
 */
public enum EventType {
    WORKSPACE("workspace"),
    CARD("card"),
    COMMENT("comment");

    private String type;

    /**
     * Constructs an {@link EventType} with the specified string identifier.
     *
     * @param type the string identifier for the event type
     */
    EventType(String type) {
        this.type = type;
    }

    /**
     * Returns the string identifier of the event type.
     *
     * @return the string identifier of the event type
     */
    public String getType() {
        return type;
    }
}