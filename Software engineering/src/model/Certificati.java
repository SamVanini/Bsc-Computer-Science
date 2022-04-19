package model;

public class Certificati {

    int vacanza;
    String certificatoAcquisito;

    public Certificati(int vacanza, String certificatoAcquisito) {
        this.vacanza = vacanza;
        this.certificatoAcquisito = certificatoAcquisito;
    }

    public int getVacanza() {
        return vacanza;
    }

    public void setVacanza(int vacanza) {
        this.vacanza = vacanza;
    }

    public String getCertificatoAcquisito() {
        return certificatoAcquisito;
    }

    public void setCertificatoAcquisito(String certificatoAcquisito) {
        this.certificatoAcquisito = certificatoAcquisito;
    }
}
