package com.company;

// TODO: Phase2: uncomment this code

import java.io.Serializable;

/**
 * A class to make the file info. as an object.
 */
public class Note implements Serializable {
    private String title;
    private String content;
    private String date;

    /**
     * Constructor of the class
     * @param title Title of the note
     * @param content Content of the note
     * @param date Date of the note
     */
    public Note(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    /**
     * Returns the title of the note
     * @return Title of the note
     */
    public String getTitle(){ return title; }

    /**
     * Returns the content of the note
     * @return Content of the note
     */
    public String getContent(){ return content; }

    /**
     * Returns the date of the note
     * @return Date of the note
     */
    public String getDate(){ return date; }

    /**
     * Overrides the toString method
     * @return String of this class
     */
    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
