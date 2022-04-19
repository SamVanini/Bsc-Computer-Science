package model;

public class Gite {
    private String destinazione;
    private int numeroOre;
    private int costo;
    private String descrizione;

    public Gite(String destinazione, int numeroOre, int costo, String descrizione) {
        this.destinazione = destinazione;
        this.numeroOre = numeroOre;
        this.costo = costo;
        this.descrizione = descrizione;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public int getNumeroOre() {
        return numeroOre;
    }

    public void setNumeroOre(int numeroOre) {
        this.numeroOre = numeroOre;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
