package com.todoapp.domain.model;

import java.util.Arrays;

public enum TodoStatus {
    PENDING("pending"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    REJECTED("rejected");

    private final String value;

    TodoStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TodoStatus fromValue(String value) {
        return Arrays.stream(TodoStatus.values())
                .filter(status -> status.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown TodoStatus value: " + value));
    }
    
    public boolean isCompleted() {
        return this == COMPLETED;
    }
    
    public boolean isPending() {
        return this == PENDING;
    }
    
    public boolean isInProgress() {
        return this == IN_PROGRESS;
    }
    
    public boolean isRejected() {
        return this == REJECTED;
    }
}