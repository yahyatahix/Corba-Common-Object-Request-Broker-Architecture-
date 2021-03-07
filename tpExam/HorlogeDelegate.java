
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class HorlogeDelegate implements HorlogeOperations{
	
	
	public String afficher(){
		LocalTime time=LocalTime.now(); 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String formattedTime=time.format(formatter);
		return formattedTime;
	}
	public String  alarme(String wakeup){
	
	
		
		return "hello";
	}


}
