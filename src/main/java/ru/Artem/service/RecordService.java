package ru.Artem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Artem.dao.RecordDao;
import ru.Artem.entity.Record;
import ru.Artem.entity.RecordStatus;
import ru.Artem.entity.dto.RecordsContainerDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private final RecordDao recordDao;

    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public RecordsContainerDto findAllRecords(String filterMode) {
        List<Record> records = recordDao.findAllRecords();
        int numberOfDoneRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int) records.stream().filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();

        if (filterMode == null || filterMode.isEmpty()) {
            return new RecordsContainerDto(records,numberOfDoneRecords, numberOfActiveRecords);
        }

        String filterModeInUpperCase = filterMode.toUpperCase();
        List<String> allowedFilterModes = Arrays.stream(RecordStatus.values())
                .map(status -> status.name())
                .collect(Collectors.toList());
        if (allowedFilterModes.contains(filterModeInUpperCase)) {
            List<Record> filterRecords =  records.stream()
                    .filter(record -> record.getStatus() == RecordStatus.valueOf(filterModeInUpperCase))
                    .collect(Collectors.toList());
            return new RecordsContainerDto(filterRecords,numberOfDoneRecords, numberOfActiveRecords);
        } else {
            return new RecordsContainerDto(records,numberOfDoneRecords, numberOfActiveRecords);
        }

    }

    public void saveRecord(String title) {
        if (title != null && !title.trim().isEmpty()) {
            recordDao.saveRecord(new Record(title));
        }

    }

    public void updateRecordStatus(int id, RecordStatus newStatus) {
        recordDao.updateRecordStatus(id, newStatus);
    }

    public void deleteRecordStatus(int id) {
        recordDao.deleteRecord(id);
    }
}
