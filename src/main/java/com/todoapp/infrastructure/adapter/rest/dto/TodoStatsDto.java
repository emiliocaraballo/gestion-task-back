package com.todoapp.infrastructure.adapter.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoStatsDto {
    private long pendingTasks;
    private long completedTasks;
    private long urgentTasks;
    private double progressPercentage;
}