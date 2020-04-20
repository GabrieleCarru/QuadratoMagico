package it.polito.tdp.quadrato.model;

import java.util.ArrayList;
import java.util.List;

public class RisolviQuadrato {
	private int N ; // lato del quadrato
	private int N2 ; // numero di caselle (N^2)
	private int magica ; // costante magica
	
	private List<List<Integer>> soluzioni;
	
	public RisolviQuadrato (int N){
		this.N = N;
		this.N2 = N*N;
		this.magica = N*(N2+1)/2; // calcoliamo quando viene istanziata la classe
	}
	
	// Calcola tutti i quadrati magici
	public List<List<Integer>> quadrati() {
		
		// livello 0 della ricorsione
		List<Integer> parziale = new ArrayList<>();
		int livello = 0;
		
		this.soluzioni = new ArrayList<List<Integer>>();
		
		cerca(parziale, livello);
		return this.soluzioni;
	}
	
	// Procedura ricorsiva
	private void cerca(List<Integer> parziale, int livello) {
		
		if(livello == N2) {
			// caso terminale?
			if(controlla(parziale)) {
				// e` magico!!
//				System.out.println(parziale);	soluzione di prova
//				this.soluzioni.add(parziale); // errore perche` sto stampando il riferimento!
				this.soluzioni.add(new ArrayList<>(parziale)); // cosi creo un nuovo oggetto 
			}
			return;
		}
		
		// controlli intermedi, quando il livello e` multiplo di N (righe complete)
		if(livello%N == 0 && livello != 0) {	//livello%N == 0 ---> livello e` multiplo di N?
			if(!controllaRiga(parziale, livello/N-1)); {	// livello/N-1 perche` le righe cominciano con 0
				return;		// in questo ramo non trovero` nulla di utile
			}
		}	
		
		// caso intemedio
		for(int valore = 1; valore <= N2; valore++) {
			if(!parziale.contains(valore)) {
				parziale.add(valore);
				cerca(parziale, livello+1);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	
	/**
	 * Verifica se una soluzione rispetta tutte le somme
	 * @param parziale
	 * @return
	 */
	private boolean controlla(List<Integer> parziale) {
		if(parziale.size()!=this.N*this.N)
			throw new IllegalArgumentException("Numero di elementi insufficiente") ;
		
		// Fai la somma delle righe
		for(int riga=0; riga<this.N; riga++) {
			int somma = 0 ;
			for(int col=0; col<this.N; col++) {
				somma += parziale.get(riga*this.N+col) ;
			}
			if(somma!=this.magica)
				return false ;
		}
		
		// Fai la somma delle colonne
		for(int col=0; col<this.N; col++) {
			int somma = 0 ;
			for(int riga=0; riga<this.N; riga++) {
				somma += parziale.get(riga*this.N+col) ;
			}
			if(somma!=this.magica)
				return false ;
		}
		
		// diagonale principale
		int somma = 0;
		for(int riga=0; riga<this.N; riga++) {
			somma += parziale.get(riga*this.N+riga) ;
		}
		if(somma!=this.magica)
			return false ;
		
		// diagonale inversa
		somma = 0;
		for(int riga=0; riga<this.N; riga++) {
			somma += parziale.get(riga*this.N+(this.N-1-riga)) ;
		}
		if(somma!=this.magica)
			return false ;

		return true ;
	}
	
	// Funzione creata per migliorare l'efficienza dell'algoritmo
	private boolean controllaRiga(List<Integer> parziale, int riga) {
		int somma = 0;
		for(int col = 0; col < N ; col++) 
			somma += parziale.get(riga*N + col);
		return somma==magica;
	}
}
