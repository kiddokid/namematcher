package com.example.filterproject.namematcher.job.service;

import com.example.filterproject.namematcher.dao.NormilizedCustomerDataRepository;
import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.formatter.TextFormatter;
import com.example.filterproject.namematcher.model.NormilizedCustomerData;
import com.example.filterproject.namematcher.model.RiskCustomer;
import com.example.filterproject.namematcher.model.SystemProperty;
import com.example.filterproject.namematcher.service.SystemPropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class NormalizedTextSaveJobService {

    private static final String NORMALIZED_TEXT_SAVE_OFFSET = "NORMALIZED_TEXT_SAVE_OFFSET";

    private TextFormatter textFormatter;
    private SystemPropertyService systemPropertyService;
    private RiskCustomerRepository riskCustomerRepository;
    private NormilizedCustomerDataRepository normilizedCustomerDataRepository;

    private Integer batchSize = 300;

    public NormalizedTextSaveJobService(TextFormatter textFormatter,
                                 SystemPropertyService systemPropertyService,
                                 NormilizedCustomerDataRepository normilizedCustomerDataRepository,
                                 RiskCustomerRepository riskCustomerRepository) {
        this.textFormatter = textFormatter;
        this.systemPropertyService = systemPropertyService;
        this.normilizedCustomerDataRepository = normilizedCustomerDataRepository;
        this.riskCustomerRepository = riskCustomerRepository;
    }

    public void save() {
        List<NormilizedCustomerData> normalizedCustomerDataList = new ArrayList<>();
        Long offset = getOffset();
        List<RiskCustomer> customersToNormalize = riskCustomerRepository.getCustomersInBatchWithOffset(batchSize, offset);
        customersToNormalize.forEach(riskCustomer -> {
            normalizedCustomerDataList.add(textFormatter.normalize(riskCustomer));
        });
        if (normalizedCustomerDataList.size() > 0) {
            log.info("[{}] Trying to save {} entities - {}", this.getClass().getSimpleName(), normalizedCustomerDataList.size(), normalizedCustomerDataList.toString());
            normilizedCustomerDataRepository.saveAll(normalizedCustomerDataList);
            saveOffset(normalizedCustomerDataList);
        }
    }

    private Long getOffset() {
        Long offset = systemPropertyService.getLongValue(NORMALIZED_TEXT_SAVE_OFFSET);
        if (Objects.isNull(offset)) {
            systemPropertyService.save(SystemProperty.builder()
                    .property(NORMALIZED_TEXT_SAVE_OFFSET)
                    .value("0")
                    .build());
            return 0L;
        } else {
            return offset;
        }
    }

    private void saveOffset(List<NormilizedCustomerData> savedList) {
        NormilizedCustomerData maxIdCustomerData = savedList.stream()
                .max(Comparator.comparingLong(NormilizedCustomerData::getRiskCustomerId)).get();
        systemPropertyService.save(SystemProperty.builder()
                .property(NORMALIZED_TEXT_SAVE_OFFSET)
                .value(maxIdCustomerData.getRiskCustomerId().toString())
                .build());
    }
}
