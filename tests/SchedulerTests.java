import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;

class SchedulerTests {


    @Test
    public void testScheduleConference() {
       List<String> talks = Arrays.asList(
            "Writing Fast Tests Against Enterprise Rails 60min",
            "Overdoing it in Python 45min",
            "Lua for the Masses 30min",
            "Ruby Errors from Mismatched Gem Versions 45min",
            "Common Ruby Errors 45min",
            "Rails for Python Developers lightning",
            "Communicating Over Distance 60min",
            "Accounting-Driven Development 45min",
            "Woah 30min",
            "Sit Down and Write 30min",
            "Pair Programming vs Noise 45min",
            "Rails Magic 60min",
            "Ruby on Rails: Why We Should Move On 60min",
            "Clojure Ate Scala (on my project) 45min",
            "Programming in the Boondocks of Seattle 30min",
            "Ruby vs. Clojure for Back-End Development 30min",
            "Ruby on Rails Legacy App Maintenance 60min",
            "A World Without HackerNews 30min",
            "User Interface CSS in Rails Apps 30min"
        );

        List<String> schedule = Scheduler.scheduleConference(talks);
        
        // Verificamos que la cantidad de charlas sea correcta (incluye almuerzo y network event)
        	assertEquals(22, schedule.size());

//        // Verificamos que el evento de networking se programe después de todas las charlas
       assertTrue(schedule.get(schedule.size() - 1).contains("Networking Event"));
//        
//        // Verificamos que no se repitan charlas en el horario
        for (int i = 0; i < schedule.size() - 1; i++) {
        	if(!schedule.get(i).contains("Lunch")  ) {
        		schedule.remove(i);
        		  for (int j = i + 1; j < schedule.size(); j++) {
                      assertNotEquals(schedule.get(i), schedule.get(j));
                  }
        	}




        }
    }
}





