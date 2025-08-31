package ru.Artem.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecordTest {

    @Test
    void constructor_WithTitle_ShouldCreateRecordWithActiveStatus() {
        String title = "Test Record";

        Record record = new Record(title);

        assertEquals(title, record.getTitle());
        assertEquals(RecordStatus.ACTIVE, record.getStatus());
        assertEquals(0, record.getId());
    }

    @Test
    void constructor_WithEmptyTitle_ShouldCreateRecordWithEmptyTitle() {
        Record record = new Record("");

        assertEquals("", record.getTitle());
        assertEquals(RecordStatus.ACTIVE, record.getStatus());
    }

    @Test
    void constructor_WithNullTitle_ShouldCreateRecordWithNullTitle() {
        Record record = new Record(null);

        assertNull(record.getTitle());
        assertEquals(RecordStatus.ACTIVE, record.getStatus());
    }

    @Test
    void getId_WhenIdIsNull_ShouldReturnZero() {
        Record record = new Record("Test");

        int id = record.getId();

        assertEquals(0, id);
    }

    @Test
    void getId_WhenIdIsSet_ShouldReturnId() {
        Record record = new Record("Test");
        try {
            java.lang.reflect.Field idField = Record.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(record, 123);
        } catch (Exception e) {
            fail("Failed to set ID via reflection: " + e.getMessage());
        }

        int id = record.getId();

        assertEquals(123, id);
    }

    @Test
    void getTitle_ShouldReturnTitle() {
        String title = "Test Record";
        Record record = new Record(title);

        String result = record.getTitle();

        assertEquals(title, result);
    }

    @Test
    void getStatus_ShouldReturnStatus() {
        Record record = new Record("Test");
        RecordStatus expectedStatus = RecordStatus.ACTIVE;

        RecordStatus result = record.getStatus();

        assertEquals(expectedStatus, result);
    }

    @Test
    void setStatus_ShouldUpdateStatus() {
        Record record = new Record("Test");
        RecordStatus newStatus = RecordStatus.DONE;

        record.setStatus(newStatus);

        assertEquals(newStatus, record.getStatus());
    }

    @Test
    void setStatus_WithNull_ShouldSetNullStatus() {
        Record record = new Record("Test");

        record.setStatus(null);

        assertNull(record.getStatus());
    }

    @Test
    void setStatus_WithActive_ShouldSetActiveStatus() {
        Record record = new Record("Test");
        record.setStatus(RecordStatus.DONE);

        record.setStatus(RecordStatus.ACTIVE);

        assertEquals(RecordStatus.ACTIVE, record.getStatus());
    }

    @Test
    void setStatus_WithDone_ShouldSetDoneStatus() {
        Record record = new Record("Test");

        record.setStatus(RecordStatus.DONE);

        assertEquals(RecordStatus.DONE, record.getStatus());
    }

    @Test
    void record_ShouldMaintainTitleAfterStatusChange() {
        String title = "Test Record";
        Record record = new Record(title);

        record.setStatus(RecordStatus.DONE);

        assertEquals(title, record.getTitle());
        assertEquals(RecordStatus.DONE, record.getStatus());
    }

    @Test
    void record_ShouldMaintainStatusAfterMultipleChanges() {
        Record record = new Record("Test");

        assertEquals(RecordStatus.ACTIVE, record.getStatus());
        
        record.setStatus(RecordStatus.DONE);
        assertEquals(RecordStatus.DONE, record.getStatus());
        
        record.setStatus(RecordStatus.ACTIVE);
        assertEquals(RecordStatus.ACTIVE, record.getStatus());
        
        record.setStatus(RecordStatus.DONE);
        assertEquals(RecordStatus.DONE, record.getStatus());
    }
}
