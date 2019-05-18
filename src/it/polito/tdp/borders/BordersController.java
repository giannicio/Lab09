/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

	Model model;
	

	public void setModel(Model model) {
		this.model = model;
		List<Country> allCountries = model.getAllCountries();
		for(Country c: allCountries) {
			comboBox.getItems().add(c);
		}
	}


    @FXML
    private ComboBox<Country> comboBox;
    
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCalcolaConfini(ActionEvent event) {
		txtResult.clear();
		try {
			Integer.parseInt(txtAnno.getText());
		} catch(Exception e) {
			txtResult.setText("ERRORE, Inserire un valore numerico!");
		}
		int anno = Integer.parseInt(txtAnno.getText());
		if(anno < 1816 || anno > 2016) {
			txtResult.setText("ERRORE, inserire un anno tra 1816 e 2016!");
			return;
		}
		
		List<String> result = model.trovaConfini(anno);
		txtResult.clear();
		txtResult.appendText("CONFINI: \n");
		for(String s: result) {
			txtResult.appendText(s + "\n");
		}
		
		List<Country> countryList = model.ottieniCountryList(anno);
		List<String> degreeList = model.getListaGradi(countryList, anno);
		txtResult.appendText("\nSTATO - NUMERO DI STATI CONFINANTI VIA TERRA: \n");
		for(String s: degreeList) {
			txtResult.appendText(s + "\n");
		}
		String componenti = model.ottieniNumComponenti(anno);
		txtResult.appendText("\nCOMPONENTI DEL GRAFO: " + componenti);
		
	}
	
    @FXML
    void doTrovaVicini(ActionEvent event) {
    	txtResult.clear();
    	try {
			Integer.parseInt(txtAnno.getText());
		} catch(Exception e) {
			txtResult.setText("ERRORE, Inserire un valore numerico!");
		}
		int anno = Integer.parseInt(txtAnno.getText());
		if(anno < 1816 || anno > 2016) {
			txtResult.setText("ERRORE, inserire un anno tra 1816 e 2016!");
			return;
		}
		if(comboBox.getValue() == null) {
			txtResult.setText("ERRORE: selezionare una nazione!");
		}
		Country nazione = comboBox.getValue();
		List<Country> vicini = model.trovaViciniDi(nazione, anno);
		txtResult.appendText("NAZIONI CONFINANTI A "+ nazione.getStateNme().toUpperCase() +": \n");
		for(Country c: vicini) {
			txtResult.appendText(c + "\n");
		}
		txtResult.appendText("\nNAZIONI RAGGIUNGIBILI DA " + nazione.getStateNme().toUpperCase() +": \n");
		List<Country> raggiungibili = model.countryRaggiundibiliDa(nazione);
		
		for(Country c: raggiungibili) {
			txtResult.appendText(c + "\n");
		}
    }

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";
		
	}
}
