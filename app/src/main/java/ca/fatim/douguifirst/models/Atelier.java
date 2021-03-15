package ca.fatim.douguifirst.models;
import com.google.firebase.firestore.GeoPoint;
public class Atelier {
    // fieds
    private String numAtelier;
    private String nomResponsable;
    private String nomAtelier;
    private String siteAtelier;
    private String contactAtelier;
    private String adresseAtelier;
    private GeoPoint coordonneesGPS;
    private String urlLogo;

    //constructor
    public Atelier(){};

    public Atelier(String numAtelier, String nomResponsable, String nomAtelier, String siteAtelier, String contactAtelier, String adresseAtelier, GeoPoint coordonneesGPS, String urlLogo) {
        this.numAtelier = numAtelier;
        this.nomResponsable = nomResponsable;
        this.nomAtelier = nomAtelier;
        this.siteAtelier = siteAtelier;
        this.contactAtelier = contactAtelier;
        this.adresseAtelier = adresseAtelier;
        this.coordonneesGPS = coordonneesGPS;
        this.urlLogo = urlLogo;
    }

    //methods

    public String getNumAtelier() {
        return numAtelier;
    }

    public String getNomResponsable() {
        return nomResponsable;
    }

    public String getNomAtelier() {
        return nomAtelier;
    }

    public String getSiteAtelier() {
        return siteAtelier;
    }

    public String getContactAtelier() {
        return contactAtelier;
    }

    public String getAdresseAtelier() {
        return adresseAtelier;
    }

    public GeoPoint getCoordonneesGPS() {
        return coordonneesGPS;
    }

    public String getUrlLogo() {
        return urlLogo;
    }
    // Setter

    public void setNumAtelier(String numAtelier) {
        this.numAtelier = numAtelier;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }

    public void setNomAtelier(String nomAtelier) {
        this.nomAtelier = nomAtelier;
    }

    public void setSiteAtelier(String siteAtelier) {
        this.siteAtelier = siteAtelier;
    }

    public void setContactAtelier(String contactAtelier) {
        this.contactAtelier = contactAtelier;
    }

    public void setAdresseAtelier(String adresseAtelier) {
        this.adresseAtelier = adresseAtelier;
    }

    public void setCoordonneesGPS(GeoPoint coordonneesGPS) {
        this.coordonneesGPS = coordonneesGPS;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }
}
