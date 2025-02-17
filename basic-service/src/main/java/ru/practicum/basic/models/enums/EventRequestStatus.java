package ru.practicum.basic.models.enums;

public enum EventRequestStatus {
    CONFIRMED, REJECTED;

    public static EventRequestStatus fromString(String status) {
        if (status == null) {
            return null;
        }

        return EventRequestStatus.valueOf(status.toUpperCase());
    }
}
