package ru.Artem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor

public class Record {
    private final String Title;
    private RecordStatus status;

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
