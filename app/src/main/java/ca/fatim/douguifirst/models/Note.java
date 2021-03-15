package ca.fatim.douguifirst.models;

public class Note {
    //fieds

    private String numAtelier;
    private String noteAtelier;

    //constructor

    public Note() {
    }

    public Note(String numAtelier, String noteAtelier) {
        this.numAtelier = numAtelier;
        this.noteAtelier = noteAtelier;
    }

    //getter

    public String getNumAtelier() {
        return numAtelier;
    }

    public String getNoteAtelier() {
        return noteAtelier;
    }

    //setter

    public void setNumAtelier(String numAtelier) {
        this.numAtelier = numAtelier;
    }

    public void setNoteAtelier(String noteAtelier) {
        this.noteAtelier = noteAtelier;
    }
}


