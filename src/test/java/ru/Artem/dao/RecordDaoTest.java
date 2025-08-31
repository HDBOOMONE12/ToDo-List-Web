package ru.Artem.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.Artem.entity.Record;
import ru.Artem.entity.RecordStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecordDaoTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static RecordDao recordDao;

    @BeforeAll
    static void setUp() {
        emf = Persistence.createEntityManagerFactory("test-pu");
        em = emf.createEntityManager();
        recordDao = new RecordDao();
        
        try {
            java.lang.reflect.Field emField = RecordDao.class.getDeclaredField("em");
            emField.setAccessible(true);
            emField.set(recordDao, em);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set EntityManager", e);
        }
    }

    @AfterAll
    static void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUpTest() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Record").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    @Order(1)
    void getRecords_WithEmptyDatabase_ShouldReturnEmptyList() {
        List<Record> result = recordDao.getRecords();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(2)
    void findAllRecords_WithEmptyDatabase_ShouldReturnEmptyList() {
        List<Record> result = recordDao.findAllRecords();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(3)
    void saveRecord_ShouldPersistRecord() {
        Record record = new Record("Test Record");

        em.getTransaction().begin();
        recordDao.saveRecord(record);
        em.getTransaction().commit();

        em.clear();
        Record savedRecord = em.find(Record.class, record.getId());
        assertNotNull(savedRecord);
        assertEquals("Test Record", savedRecord.getTitle());
        assertEquals(RecordStatus.ACTIVE, savedRecord.getStatus());
    }

    @Test
    @Order(4)
    void getRecords_WithSavedRecords_ShouldReturnAllRecords() {
        Record record1 = new Record("Test Record 1");
        Record record2 = new Record("Test Record 2");
        
        em.getTransaction().begin();
        em.persist(record1);
        em.persist(record2);
        em.getTransaction().commit();
        em.clear();

        List<Record> result = recordDao.getRecords();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getTitle().equals("Test Record 1")));
        assertTrue(result.stream().anyMatch(r -> r.getTitle().equals("Test Record 2")));
    }

    @Test
    @Order(5)
    void findAllRecords_WithSavedRecords_ShouldReturnAllRecords() {
        Record record1 = new Record("Test Record 1");
        Record record2 = new Record("Test Record 2");
        
        em.getTransaction().begin();
        em.persist(record1);
        em.persist(record2);
        em.getTransaction().commit();
        em.clear();

        List<Record> result = recordDao.findAllRecords();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getTitle().equals("Test Record 1")));
        assertTrue(result.stream().anyMatch(r -> r.getTitle().equals("Test Record 2")));
    }

    @Test
    @Order(6)
    void getRecordsByStatus_WithActiveRecords_ShouldReturnOnlyActiveRecords() {
        Record activeRecord = new Record("Active Record");
        Record doneRecord = new Record("Done Record");
        doneRecord.setStatus(RecordStatus.DONE);
        
        em.getTransaction().begin();
        em.persist(activeRecord);
        em.persist(doneRecord);
        em.getTransaction().commit();
        em.clear();

        List<Record> result = recordDao.getRecordsByStatus(RecordStatus.ACTIVE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Active Record", result.get(0).getTitle());
        assertEquals(RecordStatus.ACTIVE, result.get(0).getStatus());
    }

    @Test
    @Order(7)
    void getRecordsByStatus_WithDoneRecords_ShouldReturnOnlyDoneRecords() {
        Record activeRecord = new Record("Active Record");
        Record doneRecord = new Record("Done Record");
        doneRecord.setStatus(RecordStatus.DONE);
        
        em.getTransaction().begin();
        em.persist(activeRecord);
        em.persist(doneRecord);
        em.getTransaction().commit();
        em.clear();

        List<Record> result = recordDao.getRecordsByStatus(RecordStatus.DONE);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Done Record", result.get(0).getTitle());
        assertEquals(RecordStatus.DONE, result.get(0).getStatus());
    }

    @Test
    @Order(8)
    void getRecordsByStatus_WithNoMatchingRecords_ShouldReturnEmptyList() {
        Record activeRecord = new Record("Active Record");
        
        em.getTransaction().begin();
        em.persist(activeRecord);
        em.getTransaction().commit();
        em.clear();

        List<Record> result = recordDao.getRecordsByStatus(RecordStatus.DONE);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(9)
    void updateRecordStatus_WithExistingRecord_ShouldUpdateStatus() {
        Record record = new Record("Test Record");
        
        em.getTransaction().begin();
        em.persist(record);
        em.getTransaction().commit();
        em.clear();

        em.getTransaction().begin();
        recordDao.updateRecordStatus(record.getId(), RecordStatus.DONE);
        em.getTransaction().commit();
        em.clear();

        Record updatedRecord = em.find(Record.class, record.getId());
        assertNotNull(updatedRecord);
        assertEquals(RecordStatus.DONE, updatedRecord.getStatus());
        assertEquals("Test Record", updatedRecord.getTitle());
    }

    @Test
    @Order(10)
    void updateRecordStatus_WithNonExistingRecord_ShouldNotThrowException() {
        assertDoesNotThrow(() -> {
            em.getTransaction().begin();
            recordDao.updateRecordStatus(999, RecordStatus.DONE);
            em.getTransaction().commit();
        });
    }

    @Test
    @Order(11)
    void deleteRecord_WithExistingRecord_ShouldDeleteRecord() {
        Record record = new Record("Test Record");
        
        em.getTransaction().begin();
        em.persist(record);
        em.getTransaction().commit();
        em.clear();

        em.getTransaction().begin();
        recordDao.deleteRecord(record.getId());
        em.getTransaction().commit();
        em.clear();

        Record deletedRecord = em.find(Record.class, record.getId());
        assertNull(deletedRecord);
    }

    @Test
    @Order(12)
    void deleteRecord_WithNonExistingRecord_ShouldNotThrowException() {
        assertDoesNotThrow(() -> {
            em.getTransaction().begin();
            recordDao.deleteRecord(999);
            em.getTransaction().commit();
        });
    }

    @Test
    @Order(13)
    void saveRecord_WithMultipleRecords_ShouldPersistAllRecords() {
        Record record1 = new Record("Record 1");
        Record record2 = new Record("Record 2");
        Record record3 = new Record("Record 3");

        em.getTransaction().begin();
        recordDao.saveRecord(record1);
        recordDao.saveRecord(record2);
        recordDao.saveRecord(record3);
        em.getTransaction().commit();
        em.clear();

        List<Record> allRecords = recordDao.findAllRecords();
        assertEquals(3, allRecords.size());
        assertTrue(allRecords.stream().anyMatch(r -> r.getTitle().equals("Record 1")));
        assertTrue(allRecords.stream().anyMatch(r -> r.getTitle().equals("Record 2")));
        assertTrue(allRecords.stream().anyMatch(r -> r.getTitle().equals("Record 3")));
    }

    @Test
    @Order(14)
    void getRecords_ShouldReturnRecordsInOrderById() {
        Record record1 = new Record("First Record");
        Record record2 = new Record("Second Record");
        Record record3 = new Record("Third Record");
        
        em.getTransaction().begin();
        em.persist(record1);
        em.persist(record2);
        em.persist(record3);
        em.getTransaction().commit();
        em.clear();

        List<Record> result = recordDao.getRecords();

        assertNotNull(result);
        assertEquals(3, result.size());
        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getId() <= result.get(i + 1).getId());
        }
    }
}
