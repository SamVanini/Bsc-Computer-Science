package model;

public class Genitori {

    private static Genitori instance = new Genitori();
    private int id;
    private String nome;
    private String cognome;
    private String telefono;
    private String mail;

    public Genitori() {

    }

    public static Genitori getInstance(){
        if(instance == null){
            return new Genitori();
        }else{
            return instance;
        }
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
