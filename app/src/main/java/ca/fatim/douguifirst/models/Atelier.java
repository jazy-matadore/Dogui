package ca.fatim.douguifirst.models;

public class Atelier {
    // fieds
    private String nomAtelier;
    private String adresseAtelier;
    private Double noteAtelier;

    //constructor

    public Atelier(String nomAtelier, String adresseAtelier, Double noteAtelier) {
        this.nomAtelier= nomAtelier;
        this.adresseAtelier= adresseAtelier;
        this.noteAtelier= noteAtelier;
    }

    //methods

    public String getNomAtelier() {return nomAtelier; }
    public String getAdresseAtelier() {return adresseAtelier; }
    public Double getNoteAtelier() {return noteAtelier; }
}
