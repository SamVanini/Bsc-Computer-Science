package model;

public class Utente {

    private static Utente instance = new Utente();
    private int id;
    private String nome;
    private String cognome;
    private int eta;
    private String via;
    private String numeroCivico;
    private String paese;
    private String telefono;
    private String mail;
    private String hobby;
    private String allergia1;
    private String allergia2;
    private int genitore;

    private Utente() {

    }

    public static Utente getInstance(){
        if(instance == null){
            return new Utente();
        }else{
            return instance;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAllergia1() {
        return allergia1;
    }

    public void setAllergia1(String allergia1) {
        this.allergia1 = allergia1;
    }

    public String getAllergia2() {
        return allergia2;
    }

    public void setAllergia2(String allergia2) {
        this.allergia2 = allergia2;
    }

    public int getGenitore() {
        return genitore;
    }

    public void setGenitore(int genitore) {
        this.genitore = genitore;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", eta=" + eta +
                ", via='" + via + '\'' +
                ", numeroCivico='" + numeroCivico + '\'' +
                ", paese='" + paese + '\'' +
                ", telefono='" + telefono + '\'' +
                ", mail='" + mail + '\'' +
                ", hobby='" + hobby + '\'' +
                ", allergia1='" + allergia1 + '\'' +
                ", allergia2='" + allergia2 + '\'' +
                ", genitore=" + genitore +
                '}';
    }

    public String Anni(){
        String result = "";
        result = result + eta;
        return result;
    }
}
