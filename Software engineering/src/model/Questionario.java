package model;

public class Questionario {
    int vacanza;
    int voto;
    String commento;

    public Questionario(int vacanza, int voto, String commento) {
        this.vacanza = vacanza;
        this.voto = voto;
        this.commento = commento;
    }

    public Questionario(int vacanza, int voto) {
        this.vacanza = vacanza;
        this.voto = voto;
    }

    public int getVacanza() {
        return vacanza;
    }

    public void setVacanza(int vacanza) {
        this.vacanza = vacanza;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }
}
