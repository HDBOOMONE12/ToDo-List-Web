package ru.Artem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor

public class Record {
    private static int counterSequence = 0;
    private final int id;
    private final String Title;
    private RecordStatus status;

    public Record(String title) {
        this.id = counterSequence++;
        this.Title = title;
        this.status = RecordStatus.ACTIVE;
    }

    public Record(String title, RecordStatus status) {
        this.id = counterSequence++;
        this.Title = title;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {

        return Title;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

}
