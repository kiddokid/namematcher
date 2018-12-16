package com.example.filterproject.namematcher.job;

import com.example.filterproject.namematcher.dao.NormilizedCustomerDataRepository;
import com.example.filterproject.namematcher.dao.SystemPropertyRepository;
import com.example.filterproject.namematcher.formatter.TextFormatter;
import com.example.filterproject.namematcher.model.SystemProperty;
import com.example.filterproject.namematcher.service.SystemPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class NormalizedTextSaveJob {

    public static final String NORMALIZED_TEXT_SAVE_OFFSET = "NORMALIZED_TEXT_SAVE_OFFSET";

    private TextFormatter textFormatter;
    private SystemPropertyService systemPropertyService;
    private NormilizedCustomerDataRepository normilizedCustomerDataRepository;

    public NormalizedTextSaveJob(TextFormatter textFormatter,
                                 SystemPropertyService systemPropertyService,
                                 NormilizedCustomerDataRepository normilizedCustomerDataRepository) {
        this.textFormatter = textFormatter;
        this.systemPropertyService = systemPropertyService;
        this.normilizedCustomerDataRepository = normilizedCustomerDataRepository;
    }

    @Scheduled(fixedDelay = 60000)
    public void save() {
        Long offset = getOffset();
    }

    public Long getOffset() {
        Long offset = systemPropertyService.getLongValue(NORMALIZED_TEXT_SAVE_OFFSET);
        if (Objects.isNull(offset)) {
            systemPropertyService.save(SystemProperty.builder()
                    .property(NORMALIZED_TEXT_SAVE_OFFSET)
                    .value("0")
                    .build());
            return 0L;
        }
        else {
            return offset;
        }
    }
}
