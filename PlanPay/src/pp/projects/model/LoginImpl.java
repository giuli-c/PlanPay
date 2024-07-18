package pp.projects.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LoginImpl implements Login{

	private BufferedReader reader;
	private BufferedWriter writer;
    private Map<String, UserCredentials> credentials;
    private String accountName;
    private String pathEvents;
    
    private String path;
	
	public LoginImpl() {
		this.credentials = new HashMap<>();
		this.accountName = "";
		this.path = "src/resource/credentials.txt";
		this.writer = null;
		this.pathEvents = "";
		// Alla creazione di un nuovo elemento autenticazione ricarico sempre i dati.
		// In tal modo se sono stati modificati ricarico sempre quelli corretti.
		loadCredential();
	}
	
	@Override
	public void loadCredential() {
		try {
			this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			
			String input;
			while ((input = reader.readLine()) != null) {
				String[] arrayCredentials = input.split("\\[\\]");
				credentials.put(arrayCredentials[1], new UserCredentials(arrayCredentials[2], arrayCredentials[0], arrayCredentials[3]));
			}
		} catch (FileNotFoundException e) {
			System.out.print("File non trovato: " + e);
        } catch (IOException e) {
            System.out.print("Problemi all'apertura del file: " + e);
        } finally {
        	// chiudo il reader se è stato aperto con successo
        	if (reader != null) {
        		try {
        			reader.close();
        		}catch (IOException e) {
        			System.out.println("Errore durante la chiusura del reader: " + e.getMessage());
        		}
        	}
        }
	}
	
	@Override
	public boolean valideAuthenticate(String utente, String password) {
		// In caso positivo prende la password da utente e la confronta con la password fornita come argomento.
		if (credentials.containsKey(utente)) {
			UserCredentials cred = credentials.get(utente);
            // Verifica se la password è corretta
            if (cred != null && cred.getPassword().equals(password)) {
                this.accountName = cred.getUserName();
                return true;
            }
		}
		return false;
	}
	
	@Override
	public boolean registration(String utente, String password, String nomeUser) {
		if(credentials.containsKey(utente)) {
			return false;
		}
		
		pathEvents = "src/resource/" + (nomeUser.contains(" ") ? nomeUser.replace(" ", "") : nomeUser) + "_events.txt";
		credentials.put(utente, new UserCredentials(password, nomeUser, pathEvents));
		saveCredential(utente, password, nomeUser);
		return true;
	}
	
	@Override
	public void saveCredential(String utente, String password, String nomeUser) {
		// Percorso relativo al file nel progetto
        File file = new File(path);

        // Mi assicuro che la directory esista
        file.getParentFile().mkdirs();
		try {
			this.writer = new BufferedWriter(new FileWriter(file, true));
			
			if(pathEvents.trim() == "")
				// sono nella fase di test e restituisco il percorso del file di test
				pathEvents = "test_events";
			writer.write(nomeUser + "[]" + utente + "[]" + password +  "[]" + pathEvents);
			writer.newLine();
		} catch (IOException e) {
            System.out.print("Problemi al salvataggio del file: " + e);
        } finally {
        	// Chiusura del writer, se è stato aperto con successo
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Errore durante la chiusura del writer: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }		
	}	
	
	@Override
	public String getAccountName() {
		return this.accountName;
	}
	
	@Override
	public Map<String, UserCredentials> getCredential() {
		return this.credentials;
	}
	
	@Override
	public void setPath(String tmp) {
		this.path = tmp;
	}
	
	@Override
    public String getEventsFilePath(String userName) {
		for (Map.Entry<String, UserCredentials> entry : credentials.entrySet()) {
	        UserCredentials value = entry.getValue();
	        if (value.getUserName().equals(userName)) {
	            return value.getEventsFilePath();
	        }
		}
        return null;
    }
}
