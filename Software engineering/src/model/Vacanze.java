package model;

public class Vacanze {

    String destinazione;
    String dataPartenza;
    int numeroSettimane;
    String linguaStudiata;
    String gita1;
    String gita2;
    int costo;

    public Vacanze(String destinazione, String dataPartenza, int numeroSettimane, String linguaStudiata, String gita1, String gita2, int costo) {
        this.destinazione = destinazione;
        this.dataPartenza = dataPartenza;
        this.numeroSettimane = numeroSettimane;
        this.linguaStudiata = linguaStudiata;
        this.gita1 = gita1;
        this.gita2 = gita2;
        this.costo = costo;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public String getDataPartenza() {
        return dataPartenza;
    }

    public void setDataPartenza(String dataPartenza) {
        this.dataPartenza = dataPartenza;
    }

    public int getNumeroSettimane() {
        return numeroSettimane;
    }

    public void setNumeroSettimane(int numeroSettimane) {
        this.numeroSettimane = numeroSettimane;
    }

    public String getLinguaStudiata() {
        return linguaStudiata;
    }

    public void setLinguaStudiata(String linguaStudiata) {
        this.linguaStudiata = linguaStudiata;
    }

    public String getGita1() {
        return gita1;
    }

    public void setGita1(String gita1) {
        this.gita1 = gita1;
    }

    public String getGita2() {
        return gita2;
    }

    public void setGita2(String gita2) {
        this.gita2 = gita2;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}
