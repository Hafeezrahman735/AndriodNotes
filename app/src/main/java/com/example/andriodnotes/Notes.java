package com.example.andriodnotes;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Notes implements Serializable {

    private String title;
    //private final long empId;
    private String notes;

    private static int ctr = 1;

    Notes(String title, String description) {
        this.title = title;
        this.notes = description;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    //long getEmpId() { return empId;  }

    String getMessage() {
        return notes;
    }

    @NonNull
    @Override
    public String toString() {
        return title + notes;
    }


}
