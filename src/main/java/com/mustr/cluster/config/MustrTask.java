package com.mustr.cluster.config;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MustrTask {

    private JobDetail jobDetail;
    private Trigger trigger;
}
