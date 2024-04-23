import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;



public class Scheduler {

	static Properties propiedades = new Properties();

	private static final int MORNING_START_TIME = 9 * 60; // 9:00 AM en minutos
	private static final int AFTERNOON_START_TIME = 13 * 60; // 1:00 PM en minutos
	private static final int NETWORKING_EVENT_START_TIME_MAX = 17 * 60; // 5:00 PM en minutos
	private static final int NETWORKING_EVENT_START_TIME_MIN = 16 * 60; // 5:00 PM en minutos

	private static final int MAX_MORNING_DURATION = 180; // 3 horas en minutos

	public static List<String> scheduleConference(List<String> talks) {
		List<String> schedule = new ArrayList<>();

		int morningTime = MORNING_START_TIME;
		int afternoonTime = AFTERNOON_START_TIME;
		int morningTime2 = MORNING_START_TIME;
		int afternoonTime2 = AFTERNOON_START_TIME;
		boolean lunchTimeAlreayExists = true;

		// Organizamos las charlas por duración
		List<Talk> sortedTalks = new ArrayList<>();
		// Organizamos las charlas por duración
		List<String> talksExits = new ArrayList<>();

		for (String talk : talks) {
			sortedTalks.add(new Talk(talk));
		}
		Collections.sort(sortedTalks);

		

		
		// Programamos las charlas en la primera sala
		for (Talk talk : sortedTalks) {
			// Intentamos programar la charla en la mañana primero
			if (morningTime < AFTERNOON_START_TIME
					&& morningTime + talk.duration <= MORNING_START_TIME + MAX_MORNING_DURATION) {
				schedule.add(formatTalkTime(morningTime, talk.title));
				talksExits.add(talk.title);
				morningTime += talk.duration;
				// talksExits.add(talk.title);
			}
			// Si no hay tiempo disponible en la mañana, programamos en la tarde
			else if (afternoonTime < NETWORKING_EVENT_START_TIME_MIN
					&& afternoonTime + talk.duration <= NETWORKING_EVENT_START_TIME_MAX) {
				if (lunchTimeAlreayExists) {
					schedule.add("12:00PM Lunch");
					lunchTimeAlreayExists = false;
				}
				schedule.add(formatTalkTime(afternoonTime, talk.title));
				talksExits.add(talk.title);
				afternoonTime += talk.duration;
				// talksExits.add(talk.title);
			}

		}

		schedule.add(formatTalkTime(afternoonTime, "Networking Event"));

		// Si quedan charlas por programar, las programamos en la segunda sala
		if (morningTime < AFTERNOON_START_TIME || afternoonTime < NETWORKING_EVENT_START_TIME_MAX) {
			lunchTimeAlreayExists = true;

			schedule.add("Track 2:");


			for (Talk talk : sortedTalks) {
				try {
					if (!existeEvento(talksExits, talk.title)) {
						// Intentamos programar la charla en la mañana primero
						if (morningTime2 < AFTERNOON_START_TIME
								&& morningTime2 + talk.duration <= MORNING_START_TIME + MAX_MORNING_DURATION) {
							schedule.add(formatTalkTime(morningTime2, talk.title));
							morningTime2 += talk.duration;
						}

						// Si no hay tiempo disponible en la mañana, programamos en la tarde
						else if (afternoonTime2 < NETWORKING_EVENT_START_TIME_MIN
								&& afternoonTime2 + talk.duration <= NETWORKING_EVENT_START_TIME_MAX) {
							if (lunchTimeAlreayExists) {
								schedule.add("12:00PM Lunch");
								lunchTimeAlreayExists = false;
							}
							schedule.add(formatTalkTime(afternoonTime2, talk.title));
							afternoonTime2 += talk.duration;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		// Agregamos el evento de networking a las 5:00 PM en la primera sala
		schedule.add(formatTalkTime(afternoonTime2, "Networking Event"));

		return schedule;
	}

	private static boolean existeEvento(List<String> sortedTalks2, String dato) {
		boolean existe = false;
		for (int i = 0; i < sortedTalks2.size(); i++) {
			if (sortedTalks2.get(i).equals(dato)) {
				existe = true;
				break;
			}
		}
		return existe;

	}

	private static String formatTalkTime(int time, String title) {

		int hours = time / 60;
		int minutes = time % 60;
		String suffix = (hours >= 12) ? "PM" : "AM";
		hours = (hours == 0 || hours == 12) ? 12 : hours % 12;
		return String.format("%02d:%02d%s %s", hours, minutes, suffix, title);
	}

	static class Talk implements Comparable<Talk> {
		String title;
		int duration;

		public Talk(String talk) {
			try {
				String[] parts = talk.split("\\s+");
				this.title = talk;
				String lastWord = parts[parts.length - 1];
				this.duration = lastWord.equals("lightning") ? 5
						: Integer.parseInt(lastWord.substring(0, lastWord.length() - 3));
			} catch (Exception e) {
				System.out.println("Error al cambiar el formato de los datos");
			}

		}

		public int compareTo(Talk other) {
			try {
				return Integer.compare(this.duration, other.duration);
			} catch (Exception e) {
				System.out.println("Error al obtener el vaslor entero");
				return 0;
			}

		}
	}
}
	


