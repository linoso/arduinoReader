import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Container_file {
	public FileOutputStream file;
	public PrintStream output;
	private int counter;
	GregorianCalendar gr;
	public Container_file() 
	{
		gr = new GregorianCalendar();
		try 
		{
			file = new FileOutputStream(makeName());
			output = new PrintStream(file);
		}
		catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		counter = 0;
	}
public boolean aggiorna() 
	{
		try {
			output.close();
			file.close();
			file = new FileOutputStream(makeName());
			output = new PrintStream(file);
			output.println("time;temp1;temp2;temp3;temp4;volt;amp;watt;");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Errore di creazione");
			return false;
		}
		return true;
		
	}
private String  makeName() {
	counter++;
	gr = new GregorianCalendar();
	int hour=0;
	if(gr.get(Calendar.AM_PM)==0)
	{
		hour = gr.get(Calendar.HOUR);	
	}
	else
	{
		hour = gr.get(Calendar.HOUR)+12;
	}
	
	return new String("risultato_del_"+gr.get(Calendar.DAY_OF_MONTH)+"-"+(1+gr.get(Calendar.MONTH))+"_ore_"+hour+"-"+gr.get(Calendar.MINUTE)+".txt");
	
}
}


