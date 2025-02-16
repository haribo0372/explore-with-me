package ru.practicum.basic.models.enums;

public enum EventsLifeCycle {
    PENDING, PUBLISHED, CANCELED;

    public static EventsLifeCycle fromString(String state) {
        if (state == null) {
            return null;
        }

        return EventsLifeCycle.valueOf(state.toUpperCase());
    }
}
