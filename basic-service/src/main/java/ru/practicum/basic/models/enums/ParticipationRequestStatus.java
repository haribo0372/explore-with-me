package ru.practicum.basic.models.enums;

public enum ParticipationRequestStatus {
    PENDING, CONFIRMED, CANCELED, REJECTED;

    public static ParticipationRequestStatus fromString(String status) {
        if (status == null) {
            return null;
        }

        return ParticipationRequestStatus.valueOf(status.toUpperCase());
    }

    public static ParticipationRequestStatus fromEventRequestStatus(EventRequestStatus eventRequestStatus) {
        switch (eventRequestStatus) {
            case REJECTED -> {
                return REJECTED;
            }
            case CONFIRMED -> {
                return CONFIRMED;
            }
            default -> {
                return null;
            }
        }
    }
}
