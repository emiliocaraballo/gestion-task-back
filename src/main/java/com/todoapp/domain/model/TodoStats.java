package com.todoapp.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoStats {
    private int pendingTasks;
    private int completedTasks;
    private int urgentTasks;
    private double progressPercentage;
}


