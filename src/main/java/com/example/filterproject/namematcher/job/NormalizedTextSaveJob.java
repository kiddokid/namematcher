package com.example.filterproject.namematcher.job;

import com.example.filterproject.namematcher.job.service.NormalizedTextSaveJobService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NormalizedTextSaveJob {

    private NormalizedTextSaveJobService service;

    public NormalizedTextSaveJob(NormalizedTextSaveJobService service) {
        this.service = service;
    }

    @Scheduled(fixedDelay = 60000)
    public void save() {
        service.save();
    }
}
