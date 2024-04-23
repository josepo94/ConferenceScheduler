import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

	 static Scheduler scheduler = new Scheduler();
	
	 
		public static List<String> leerDato() throws FileNotFoundException {
			List<String> talks = new ArrayList<>();

			Properties propiedades = new Properties();

			try (FileInputStream entrada = new FileInputStream("resources/config.properties")) {
				propiedades.load(entrada);
				BufferedReader br = new BufferedReader(new FileReader(propiedades.getProperty("url_archivo")));
				String linea;
				while ((linea = br.readLine()) != null) {
					talks.add(linea);
				}
			} catch (IOException e) {
				e.printStackTrace();

			}

			return talks;
		}
	

	public static void main(String[] args) {
		
			List<String> talks = new ArrayList<>();
			try {
				talks = leerDato();
				List<String> schedule = Scheduler.scheduleConference(talks);
				for (String talk : schedule) {
					System.out.println(talk);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

	}

}
