package com.tms.taskmanagementsystem.domain;

import lombok.Data;

@Data
public class StatusDTO {

    private Integer taskId;

    private Integer assigneeId;

    private Status status;
}
