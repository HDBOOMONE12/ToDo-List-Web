package ru.Artem.dao;

import org.springframework.stereotype.Repository;
import ru.Artem.entity.Record;
import ru.Artem.entity.RecordStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RecordDao {
    private final List<Record> records = new ArrayList<>(
            Arrays.asList(
                    new Record("Take a shower", RecordStatus.ACTIVE),
//                    new Record("Buy flowers", RecordStatus.ACTIVE),
                    new Record("Go to", RecordStatus.ACTIVE)

            )
    );

    public List<Record> findAllRecords() {
        return new ArrayList<>(records);
    }

}
