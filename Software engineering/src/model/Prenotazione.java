package model;

public class Prenotazione {

    private static Prenotazione instance = new Prenotazione();
    private String dataprenotazione;
    private int ragazzo;
    private int vacanza;
    private int college;
    private int famiglia;
    private String metodoPagamento;
    private String nomeAmico;
    private String cognomeAmico;
    private String mailAmico;
    private String certficatoAcquisito;

    public Prenotazione() {

    }

    public static Prenotazione getInstance(){
        if(instance == null){
            return new Prenotazione();
        }else{
            return instance;
        }
    }

    public String getDataprenotazione() {
        return dataprenotazione;
    }

    public void setDataprenotazione(String dataprenotazione) {
        this.dataprenotazione = dataprenotazione;
    }

    public int getRagazzo() {
        return ragazzo;
    }

    public void setRagazzo(int ragazzo) {
        this.ragazzo = ragazzo;
    }

    public int getVacanza() {
        return vacanza;
    }

    public void setVacanza(int vacanza) {
        this.vacanza = vacanza;
    }

    public int getCollege() {
        return college;
    }

    public void setCollege(int college) {
        this.college = college;
    }

    public int getFamiglia() {
        return famiglia;
    }

    public void setFamiglia(int famiglia) {
        this.famiglia = famiglia;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getNomeAmico() {
        return nomeAmico;
    }

    public void setNomeAmico(String nomeAmico) {
        this.nomeAmico = nomeAmico;
    }

    public String getCognomeAmico() {
        return cognomeAmico;
    }

    public void setCognomeAmico(String cognomeAmico) {
        this.cognomeAmico = cognomeAmico;
    }

    public String getMailAmico() {
        return mailAmico;
    }

    public void setMailAmico(String mailAmico) {
        this.mailAmico = mailAmico;
    }

    public String getCertficatoAcquisito() {
        return certficatoAcquisito;
    }

    public void setCertficatoAcquisito(String certficatoAcquisito) {
        this.certficatoAcquisito = certficatoAcquisito;
    }
}
