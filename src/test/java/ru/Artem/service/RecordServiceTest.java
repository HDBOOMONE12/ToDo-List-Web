package ru.Artem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.Artem.dao.RecordDao;
import ru.Artem.entity.Record;
import ru.Artem.entity.RecordStatus;
import ru.Artem.entity.dto.RecordsContainerDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private RecordDao recordDao;

    @InjectMocks
    private RecordService recordService;

    private Record activeRecord;
    private Record doneRecord;
    private List<Record> records;

    @BeforeEach
    void setUp() {
        activeRecord = new Record("Test Active Task");
        activeRecord.setStatus(RecordStatus.ACTIVE);
        
        doneRecord = new Record("Test Done Task");
        doneRecord.setStatus(RecordStatus.DONE);
        
        records = Arrays.asList(activeRecord, doneRecord);
    }

    @Test
    void findAllRecords_WithNullFilter_ShouldReturnAllRecords() {
        when(recordDao.findAllRecords()).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords(null);

        assertNotNull(result);
        assertEquals(2, result.getRecords().size());
        assertEquals(1, result.getNumberOfDoneRecords());
        assertEquals(1, result.getNumberOfActiveRecords());
        verify(recordDao, times(1)).findAllRecords();
    }

    @Test
    void findAllRecords_WithEmptyFilter_ShouldReturnAllRecords() {
        when(recordDao.findAllRecords()).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords("");

        assertNotNull(result);
        assertEquals(2, result.getRecords().size());
        assertEquals(1, result.getNumberOfDoneRecords());
        assertEquals(1, result.getNumberOfActiveRecords());
        verify(recordDao, times(1)).findAllRecords();
    }

    @Test
    void findAllRecords_WithActiveFilter_ShouldReturnOnlyActiveRecords() {
        when(recordDao.findAllRecords()).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords("ACTIVE");

        assertNotNull(result);
        assertEquals(1, result.getRecords().size());
        assertEquals(RecordStatus.ACTIVE, result.getRecords().get(0).getStatus());
        assertEquals(1, result.getNumberOfDoneRecords());
        assertEquals(1, result.getNumberOfActiveRecords());
        verify(recordDao, times(1)).findAllRecords();
    }

    @Test
    void findAllRecords_WithDoneFilter_ShouldReturnOnlyDoneRecords() {
        when(recordDao.findAllRecords()).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords("DONE");

        assertNotNull(result);
        assertEquals(1, result.getRecords().size());
        assertEquals(RecordStatus.DONE, result.getRecords().get(0).getStatus());
        assertEquals(1, result.getNumberOfDoneRecords());
        assertEquals(1, result.getNumberOfActiveRecords());
        verify(recordDao, times(1)).findAllRecords();
    }

    @Test
    void findAllRecords_WithLowerCaseFilter_ShouldWorkCorrectly() {
        when(recordDao.findAllRecords()).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords("active");

        assertNotNull(result);
        assertEquals(1, result.getRecords().size());
        assertEquals(RecordStatus.ACTIVE, result.getRecords().get(0).getStatus());
        verify(recordDao, times(1)).findAllRecords();
    }

    @Test
    void findAllRecords_WithInvalidFilter_ShouldReturnAllRecords() {
        when(recordDao.findAllRecords()).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords("INVALID");

        assertNotNull(result);
        assertEquals(2, result.getRecords().size());
        assertEquals(1, result.getNumberOfDoneRecords());
        assertEquals(1, result.getNumberOfActiveRecords());
        verify(recordDao, times(1)).findAllRecords();
    }

    @Test
    void findAllRecords_WithEmptyRecordsList_ShouldReturnEmptyContainer() {
        when(recordDao.findAllRecords()).thenReturn(Collections.emptyList());

        RecordsContainerDto result = recordService.findAllRecords(null);

        assertNotNull(result);
        assertEquals(0, result.getRecords().size());
        assertEquals(0, result.getNumberOfDoneRecords());
        assertEquals(0, result.getNumberOfActiveRecords());
        verify(recordDao, times(1)).findAllRecords();
    }

    @Test
    void findAllRecords_WithOnlyActiveRecords_ShouldReturnCorrectCounts() {
        List<Record> onlyActiveRecords = Arrays.asList(activeRecord, new Record("Another Active"));
        when(recordDao.findAllRecords()).thenReturn(onlyActiveRecords);

        RecordsContainerDto result = recordService.findAllRecords(null);

        assertNotNull(result);
        assertEquals(2, result.getRecords().size());
        assertEquals(0, result.getNumberOfDoneRecords());
        assertEquals(2, result.getNumberOfActiveRecords());
        verify(recordDao, times(1)).findAllRecords();
    }

    @Test
    void findAllRecords_WithOnlyDoneRecords_ShouldReturnCorrectCounts() {
        List<Record> onlyDoneRecords = Arrays.asList(doneRecord, new Record("Another Done"));
        onlyDoneRecords.get(1).setStatus(RecordStatus.DONE);
        when(recordDao.findAllRecords()).thenReturn(onlyDoneRecords);

        RecordsContainerDto result = recordService.findAllRecords(null);

        assertNotNull(result);
        assertEquals(2, result.getRecords().size());
        assertEquals(2, result.getNumberOfDoneRecords());
        assertEquals(0, result.getNumberOfActiveRecords());
        verify(recordDao, times(1)).findAllRecords();
    }

    @Test
    void saveRecord_WithValidTitle_ShouldCallDao() {
        String title = "New Task";

        recordService.saveRecord(title);

        verify(recordDao, times(1)).saveRecord(any(Record.class));
    }

    @Test
    void saveRecord_WithNullTitle_ShouldNotCallDao() {
        recordService.saveRecord(null);

        verify(recordDao, never()).saveRecord(any(Record.class));
    }

    @Test
    void saveRecord_WithEmptyTitle_ShouldNotCallDao() {
        recordService.saveRecord("");

        verify(recordDao, never()).saveRecord(any(Record.class));
    }

    @Test
    void saveRecord_WithWhitespaceTitle_ShouldNotCallDao() {
        recordService.saveRecord("   ");

        verify(recordDao, never()).saveRecord(any(Record.class));
    }

    @Test
    void saveRecord_WithTabAndSpaceTitle_ShouldNotCallDao() {
        recordService.saveRecord("\t \n");

        verify(recordDao, never()).saveRecord(any(Record.class));
    }

    @Test
    void updateRecordStatus_ShouldCallDao() {
        int id = 1;
        RecordStatus newStatus = RecordStatus.DONE;

        recordService.updateRecordStatus(id, newStatus);

        verify(recordDao, times(1)).updateRecordStatus(id, newStatus);
    }

    @Test
    void deleteRecordStatus_ShouldCallDao() {
        int id = 1;

        recordService.deleteRecordStatus(id);

        verify(recordDao, times(1)).deleteRecord(id);
    }
}
