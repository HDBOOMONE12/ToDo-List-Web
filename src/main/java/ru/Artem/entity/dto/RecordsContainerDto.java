package ru.Artem.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.Artem.entity.Record;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class RecordsContainerDto {
    private final List<Record> records;
    private final int numberOfDoneRecords;
    private final int numberOfActiveRecords;
}
