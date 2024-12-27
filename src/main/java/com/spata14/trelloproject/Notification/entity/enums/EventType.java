package com.spata14.trelloproject.Notification.entity.enums;

/**
 * Enumeration representing various event types in the system.
 * <p>
 * Each event type is associated with a string identifier.
 * </p>
 */
public enum EventType {

    /**
     * Represents an event related to a workspace.
     */
    WORKSPACE("workspace"),

    /**
     * Represents an event related to a card.
     */
    CARD("card"),

    /**
     * Represents an event related to a comment.
     */
    COMMENT("comment");

    /**
     * The string identifier associated with the event type.
     */
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