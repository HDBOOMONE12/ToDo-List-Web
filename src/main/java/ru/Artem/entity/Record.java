package ru.Artem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="title", nullable=false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private RecordStatus status = RecordStatus.ACTIVE;

    // Default constructor for JPA
    public Record() {
    }
    
    public Record(String title) {
        this.title = title;
        this.status = RecordStatus.ACTIVE;
    }

    
    public int getId() {
        return id == null ? 0 : id;
    }

    public String getTitle() {
        return title;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }
}
