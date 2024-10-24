package com.example.demo;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyControllerCarrello {

    ArrayList<prodottoUrl> listaProdotti = new ArrayList<>();
    ArrayList<prodottoSelected> prodottiSelezionati = new ArrayList<>();
    //meglio istanziare le arrayList globalmente
  
   
   @PostMapping("/submit")  // gestiamo la chiamata di tipo POST del form
    public String getDati(@RequestParam("nome") String nome, @RequestParam("marca") String marca,
                          @RequestParam("prezzo") int prezzo ,@RequestParam("url")String url, Model m1) {

	 
        // Creazione oggetto prodotto
        prodottoUrl p1 = new prodottoUrl(nome, marca, prezzo,url);
        listaProdotti.add(p1);  // Aggiungo il prodotto alla lista
        
        // Passo il prodotto al model per stamparlo
        m1.addAttribute("prodotto", p1);
        
        // Ritorniamo una pagina per visualizzare il prodotto (potresti aver bisogno di un file HTML "stampaProdotto.html")
        return "stampaProdotto";  
        
        ///NOTA IMP se volessi che questo metodo mi prendesse i dati senza dover stampare nella pagina stampaProdotto posso fare cosi
        //1. public void getDati.. quindi metodo void e pooi
        //2.  return "redirect:/"; // Bisogna  avere un metodo mappato per "/" che mi reindirizza al index dove ce appunto il form
    }
    
    
    
    ///prendiamo un numero di prodotti selezionati tramite il form 
    ///numeri è il numerdo di prodotti che abbiamo schelto con quel nome. lo mettiamo in un array list di integer cosi verra stampato 
    //faccio richiesta dei parametri di nome e numero (quantita di prodotto) gli altri sono superflui 
   
   
   @PostMapping("/process")
    public String getProdottiSelezionati(@RequestParam("nome") ArrayList<String> nomi, 
                                       @RequestParam("num") ArrayList<Integer> numeri,
                                       Model m1) {
    	//nomi e numeri hanno lo stesso indice di prodotti. perche derivano da prodotti. nomi è array di nomi dei prodotti e numeri è l'array di quanti prodotti selezionati 
       
        //prodottiSelezionati avrai un numero di indici(grandezza)diversa da prodotti e quindi da nome e numeri 
       //  itero sull array list nomi che mettero nell array di prodotti selezionati 
        
        //potrei creare una nuova classe prodottiSelezionati con un int quantita. e qui nel controller faccio arraylist listaS di prodottiSelezionati
        
        //e istanzio dentro l'if listaS.add(new prodottiSelezionati(lista.geti).nome, lista.get(i).marca ,lista.get(i).prezzo ,lista.get(i)numeri)
     
    	
   ///NOTA BENE 
    	  //svuoto  la lista dei prodotti selezionati per evitare che si sommino a quelli della selezione precedente
	     // prodottiSelezionati.clear(); e prodottiSelezionati.removeAll(prodottiSelezionati); sono simili anche se il secondo ha 
	     //bisogno dell'argomento  ed è quindi piu macchinoso
	     // info https://codegym.cc/it/groups/posts/it.625.metodo-arraylist-removeall-in-java
        prodottiSelezionati.clear();
    	
    	
    	int somma=0;
        for (int i = 0; i < nomi.size(); i++) {
        	//posso aggiungere controllo sulle quanta
            if (numeri.get(i) >0) {
                // Trova il prodotto corrispondente aggiungendolo ai prodotti selezionati
            	//qui potrei fare la somma del costo dei prodotti selezionati somma+=numeri.get(i)*nomi.get(i)  dopo aver istanziato somam
                somma += numeri.get(i)*listaProdotti.get(i).prezzo;
                //stampa in console 
                System.out.println("il costo totale è : " +somma);
                
                //se aggiungo  somma al modello in modo che sia accessibile nella pagina
                //m1.addAttribute("sommaTotale", somma); //fiori dal ciclo for 
                //e in stampaCard aggiungo  h2 che mi stampa la somma h2 th:text="'Costo Totale: ' + ${sommaTotale}">Costo Totale</h2>
                //nota bene  . le quantita lo prendiamo dall'arrray NUMERI 
               
                prodottiSelezionati.add(new prodottoSelected(listaProdotti.get(i).nome,listaProdotti.get(i).marca, listaProdotti.get(i).prezzo, listaProdotti.get(i).url,numeri.get(i)));
               
            }
        }
        
       m1.addAttribute("sommaTotale", somma);
        
        m1.addAttribute("prodottiSelezionati", prodottiSelezionati);
        //aggionho al model listaProdotti tramite la chiave(?) lista nel caso in cui voglia stampare anche il catalogo di prodotti
       m1.addAttribute("lista", listaProdotti);
        return "stampaCarrello"; 
        
    }

    // Stampiamo la lista dei prodotti
    //mappiamo /listaProdottiform che è collegato al"bottone" che premuto mi deve restiture la lista di prodotti. 
    //lo fa chiamando la lista come attributo del model e ritornando una view , una pagina html stampaLista 
    
   
    @GetMapping("/listaProdottiForm") // mapping alla rotta listaProdottiForm
    public String getLista(Model m1) {
    	
    	 prodottiSelezionati.removeAll(prodottiSelezionati);
          m1.addAttribute("lista", listaProdotti);
        ///dovrei svuotare l'array list di listaProdotti per evitare che si sommino a quelli precedenti ogni volta che faccio cambiamenti!?
        
  
        
      //rimanda alla pagina di stampa tramite semplici ul  return "stampaLista";
        
        
        
        //rimando alla pagina che stampa le card di materialize
        return "stampaCard";
        
    }
    
    
    @GetMapping("/")   // metodo per reindirizzare al index, ovvero dove si trova il form . mappando "/"
    public String getIndex(Model m1) {
    	
    	  //pulisco la lista prodotti cosi non la aggiunge ogni volta che refresh
 	   listaProdotti.removeAll(listaProdotti);
 	   
 	   //creiamo una stringa
 	   String nome= "prodotti";
 	   
 	   //istanzio due prodotti 
 	   //le immagini vanno in static 
 	    listaProdotti.add(new prodottoUrl("pane","pane", 1,"pane.jpeg"));
 	    listaProdotti.add(new prodottoUrl("cell","samsung",200,"samsung.jpeg"));
 	   /*
		 * La passiamo al model con la dicitura nome
		 * assegniamo alla variabile nome il suo valore
		 */
		m1.addAttribute("nome", nome);  
	//perche? forse perche al model serve attaccare qualcosa???
		//ritorna alla pagina index stessa 
		
        return "index";
    }

}
