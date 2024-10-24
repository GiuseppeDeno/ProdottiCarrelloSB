package com.example.demo;
//Creare una classe prodotto che ha come proprietà un nome, una marca, un prezzo.
//All’interno del controller creare a livello globale un arrayList di prodotti.
//Creare una rotta che restituisce un form per aggiungere un nuovo prodotto.
//Questo form è collegato a sua volta a una rotta con il metodo post, e viene gestito da un metodo all’interno del controller che restituisce un template dove vengono stampati i dati inseriti
//
//Aggiungere una proprietà url al prodotto.
//E andarla a stampare sia nella stampa del prodotto singolo, che nella stampa della lista
//per stampa l’url dal templare
//<img th:src = "${item.url}">



public class prodottoUrl {
     String nome;
     String marca; 
     int prezzo;
     String url;

    public prodottoUrl(String nome, String marca, int prezzo, String url) {
        this.nome = nome;
        this.marca = marca;
        this.prezzo = prezzo;
        this.url = url;
    }

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public int getPrezzo() { return prezzo; }
    public void setPrezzo(int prezzo) { this.prezzo = prezzo; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @Override
    public String toString() {
        return "ProdottoUrl [nome=" + nome + ", marca=" + marca + ", prezzo=" + prezzo + ", url=" + url + "]";
    }
}