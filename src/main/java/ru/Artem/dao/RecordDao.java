package ru.Artem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.Artem.entity.Record;
import ru.Artem.entity.RecordStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RecordDao {

    @PersistenceContext
    private EntityManager em;

    public List<Record> getRecords() {
        return em.createQuery("select r from Record r order by r.id", Record.class)
                 .getResultList();
    }

    public List<Record> findAllRecords() {
        return getRecords();
    }

    public List<Record> getRecordsByStatus(RecordStatus status) {
        return em.createQuery("select r from Record r where r.status = :st order by r.id", Record.class)
                 .setParameter("st", status)
                 .getResultList();
    }

    @Transactional
    public void saveRecord(Record record) {
        em.persist(record);
    }

    @Transactional
    public void updateRecordStatus(int id, RecordStatus newStatus){
        Record r = em.find(Record.class, id);
        if (r != null) {
            r.setStatus(newStatus);
        }
    }

    @Transactional
    public void deleteRecord(int id){
        Record r = em.find(Record.class, id);
        if (r != null) {
            em.remove(r);
        }
    }
}
