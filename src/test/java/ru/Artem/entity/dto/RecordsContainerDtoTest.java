package ru.Artem.entity.dto;

import org.junit.jupiter.api.Test;
import ru.Artem.entity.Record;
import ru.Artem.entity.RecordStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecordsContainerDtoTest {

    @Test
    void constructor_WithValidParameters_ShouldCreateContainer() {
        List<Record> records = Arrays.asList(
            new Record("Test 1"),
            new Record("Test 2")
        );
        int numberOfDoneRecords = 1;
        int numberOfActiveRecords = 1;

        RecordsContainerDto container = new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);

        assertNotNull(container);
        assertEquals(records, container.getRecords());
        assertEquals(numberOfDoneRecords, container.getNumberOfDoneRecords());
        assertEquals(numberOfActiveRecords, container.getNumberOfActiveRecords());
    }

    @Test
    void constructor_WithEmptyRecords_ShouldCreateContainer() {
        List<Record> records = Collections.emptyList();
        int numberOfDoneRecords = 0;
        int numberOfActiveRecords = 0;

        RecordsContainerDto container = new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);

        assertNotNull(container);
        assertEquals(records, container.getRecords());
        assertEquals(numberOfDoneRecords, container.getNumberOfDoneRecords());
        assertEquals(numberOfActiveRecords, container.getNumberOfActiveRecords());
        assertTrue(container.getRecords().isEmpty());
    }

    @Test
    void constructor_WithNullRecords_ShouldCreateContainer() {
        List<Record> records = null;
        int numberOfDoneRecords = 0;
        int numberOfActiveRecords = 0;

        RecordsContainerDto container = new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);

        assertNotNull(container);
        assertNull(container.getRecords());
        assertEquals(numberOfDoneRecords, container.getNumberOfDoneRecords());
        assertEquals(numberOfActiveRecords, container.getNumberOfActiveRecords());
    }

    @Test
    void constructor_WithZeroCounts_ShouldCreateContainer() {
        List<Record> records = Arrays.asList(new Record("Test"));
        int numberOfDoneRecords = 0;
        int numberOfActiveRecords = 0;

        RecordsContainerDto container = new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);

        assertNotNull(container);
        assertEquals(records, container.getRecords());
        assertEquals(0, container.getNumberOfDoneRecords());
        assertEquals(0, container.getNumberOfActiveRecords());
    }

    @Test
    void constructor_WithLargeCounts_ShouldCreateContainer() {
        List<Record> records = Arrays.asList(new Record("Test"));
        int numberOfDoneRecords = 1000;
        int numberOfActiveRecords = 500;

        RecordsContainerDto container = new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);

        assertNotNull(container);
        assertEquals(records, container.getRecords());
        assertEquals(1000, container.getNumberOfDoneRecords());
        assertEquals(500, container.getNumberOfActiveRecords());
    }

    @Test
    void constructor_WithNegativeCounts_ShouldCreateContainer() {
        List<Record> records = Arrays.asList(new Record("Test"));
        int numberOfDoneRecords = -1;
        int numberOfActiveRecords = -5;

        RecordsContainerDto container = new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);

        assertNotNull(container);
        assertEquals(records, container.getRecords());
        assertEquals(-1, container.getNumberOfDoneRecords());
        assertEquals(-5, container.getNumberOfActiveRecords());
    }

    @Test
    void getRecords_ShouldReturnRecords() {
        List<Record> records = Arrays.asList(
            new Record("Test 1"),
            new Record("Test 2"),
            new Record("Test 3")
        );
        RecordsContainerDto container = new RecordsContainerDto(records, 1, 2);

        List<Record> result = container.getRecords();

        assertEquals(records, result);
        assertEquals(3, result.size());
        assertEquals("Test 1", result.get(0).getTitle());
        assertEquals("Test 2", result.get(1).getTitle());
        assertEquals("Test 3", result.get(2).getTitle());
    }

    @Test
    void getNumberOfDoneRecords_ShouldReturnNumberOfDoneRecords() {
        List<Record> records = Arrays.asList(new Record("Test"));
        RecordsContainerDto container = new RecordsContainerDto(records, 5, 3);

        int result = container.getNumberOfDoneRecords();

        assertEquals(5, result);
    }

    @Test
    void getNumberOfActiveRecords_ShouldReturnNumberOfActiveRecords() {
        List<Record> records = Arrays.asList(new Record("Test"));
        RecordsContainerDto container = new RecordsContainerDto(records, 2, 7);

        int result = container.getNumberOfActiveRecords();

        assertEquals(7, result);
    }

    @Test
    void container_ShouldBeImmutable() {
        List<Record> records = Arrays.asList(new Record("Test"));
        RecordsContainerDto container = new RecordsContainerDto(records, 1, 1);

        assertNotNull(container.getRecords());
        assertEquals(1, container.getNumberOfDoneRecords());
        assertEquals(1, container.getNumberOfActiveRecords());
    }

    @Test
    void container_WithRecordsContainingDifferentStatuses_ShouldWorkCorrectly() {
        Record activeRecord = new Record("Active Task");
        activeRecord.setStatus(RecordStatus.ACTIVE);
        
        Record doneRecord = new Record("Done Task");
        doneRecord.setStatus(RecordStatus.DONE);
        
        List<Record> records = Arrays.asList(activeRecord, doneRecord);
        RecordsContainerDto container = new RecordsContainerDto(records, 1, 1);

        assertEquals(2, container.getRecords().size());
        assertEquals(1, container.getNumberOfDoneRecords());
        assertEquals(1, container.getNumberOfActiveRecords());
        
        assertEquals(RecordStatus.ACTIVE, container.getRecords().get(0).getStatus());
        assertEquals(RecordStatus.DONE, container.getRecords().get(1).getStatus());
    }
}
