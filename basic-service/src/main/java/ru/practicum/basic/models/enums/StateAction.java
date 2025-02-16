package ru.practicum.basic.models.enums;

import ru.practicum.basic.exception.models.WrongStateAction;

public enum StateAction {
    SEND_TO_REVIEW,
    CANCEL_REVIEW,
    PUBLISH_EVENT,
    REJECT_EVENT;

    public static StateAction fromString(String status, boolean isAdmin) {
        if (status == null) {
            return null;
        }
        return isAdmin ? adminValueOf(status) : userValueOf(status);
    }

    private static StateAction adminValueOf(String status) {
        switch (status.toUpperCase()) {
            case "PUBLISH_EVENT" -> {
                return PUBLISH_EVENT;
            }
            case "REJECT_EVENT" -> {
                return REJECT_EVENT;
            }
            default -> throw new WrongStateAction(
                    String.format("%s не валидный. Возможные значения: ['PUBLISH_EVENT', 'REJECT_EVENT']", status));
        }
    }

    private static StateAction userValueOf(String status) {
        switch (status.toUpperCase()) {
            case "SEND_TO_REVIEW" -> {
                return SEND_TO_REVIEW;
            }
            case "CANCEL_REVIEW" -> {
                return CANCEL_REVIEW;
            }
            default -> throw new WrongStateAction(
                    String.format("%s не валидный. Возможные значения: ['SEND_TO_REVIEW', 'CANCEL_REVIEW']", status));
        }
    }
}