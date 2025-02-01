package ru.practicum.basic.models.enums;

public enum ParticipationRequestStatus {
    PENDING, REVIEWED;

    public static ParticipationRequestStatus fromString(String status) {
        if (status == null) {
            return null;
        }

        return ParticipationRequestStatus.valueOf(status.toUpperCase());
    }
}
