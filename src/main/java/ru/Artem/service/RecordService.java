package ru.Artem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Artem.dao.RecordDao;
import ru.Artem.entity.Record;

import java.util.List;

@Service
public class RecordService {
    private final RecordDao recordDao;

    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public List<Record> findAllRecords() {
        return recordDao.findAllRecords();
    }
}
