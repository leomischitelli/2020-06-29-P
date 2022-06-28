/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Connessione;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Integer> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	
    	try {
    		List<Connessione> connessioni = new ArrayList<>(this.model.getConnessioniDescrescenti());
    		txtResult.appendText("Coppie con connessione massima:\n\n");
    		Connessione prima = connessioni.get(0);
    		int i = 0;
    		while(prima.getGiocatori() == connessioni.get(i).getGiocatori()) {
    			txtResult.appendText(connessioni.get(i).toString() + "\n");
    			i++;
    		}
    	}catch(NullPointerException e) {
    		txtResult.appendText("Creare grafo prima di proseguire.\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	try {	
    		Integer mese = cmbMese.getValue();
	    	if(mese == null) {
	    		txtResult.appendText("Selezionare un mese prima di proseguire.\n");
	    		return;
	    	}
    	
    		int soglia = Integer.parseInt(txtMinuti.getText());
    		
    		if(soglia < 0 || soglia > 90) {
    			txtResult.appendText("Inserire un numero intero di minuti compreso fra 0 e 90\n");
        		return;
    		}
    		this.model.creaGrafo(soglia, mese);
    		txtResult.setText("Grafo creato!\nNumero vertici: " + model.getNumeroVertici());
    		txtResult.appendText("\nNumero archi: " + model.getNumeroArchi() + "\n");
    		cmbM1.getItems().clear();
    		cmbM2.getItems().clear();
    		cmbM1.getItems().addAll(this.model.getListaMatch());
    		cmbM2.getItems().addAll(this.model.getListaMatch());
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero intero di minuti compreso fra 0 e 90\n");
    		return;
    	}
		
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	try {
    		Match m1 = cmbM1.getValue();
    		Match m2 = cmbM2.getValue();
    		if(m1.equals(m2)){
        		txtResult.appendText("Selezionare due match diversi.\n");
        		return;
        	}
    		List<Match> result = new ArrayList<>(this.model.cercaCollegamento(m1, m2));
    		if(result.size() == 1) {
    			//solo m1, niente cammino
    			txtResult.appendText("\n\nNon esiste alcun cammino aciclico che colleghi i due match.\n");
    			return;
    		}
    		txtResult.appendText("\n\nTrovato cammino aciclico che collega i due match!\nDimensione " + result.size() + ", Peso " + this.model.getPesoMax() + "\n");
    		for(Match m : result)
    			txtResult.appendText(m.toString() + "\n");
    	}catch(NullPointerException e) {
    		txtResult.appendText("Selezionare due match dopo la creazione del grafo prima di procedere.\n");
    		return;
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(int i=1; i<=12; i++)
    		cmbMese.getItems().add(i);
  
    }
    
    
}
