
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.TextArea;
import java.awt.TextField;
import java.lang.Math;;



public class Write_thread extends Thread {
	
    Container_file file;
	LinkedList<GruppoDati> buffer;
	LinkedList<String> testo_finestra;
	ListIterator<String> iteratore;
	Long tempo;
	TextArea finestra_testo;
	static Double cont_voltaggio = new Double(5D/1024D);
	static Double resistenza_amp = new Double(0.055);
	static Double rapporto_res = new Double(96.7D/2.7D);
	static Double rapporto_pressione = new Double(60D/820D);
	public Write_thread(Container_file file, LinkedList<GruppoDati> buffer) {
		
		testo_finestra = new LinkedList<String>();
		ListIterator<String> iteratore = testo_finestra.listIterator();
		this.file = file;
		this.buffer = buffer;
		tempo = 0L;
	}


	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//super.run();
		
		GruppoDati temp ;
		while(true)
		{
			synchronized (buffer) {
				while(buffer.isEmpty())
				{
					try {
						buffer.wait();
						//System.out.println("mi sono svegliato");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				temp = buffer.removeFirst();		
			}
			
			Long istante=0L;
			if(tempo==0L)
			{
				tempo = System.currentTimeMillis();
			}
			else
			{
				istante = (System.currentTimeMillis()-tempo)/1000;
			}
			String daScrivere=istante.toString()+";";
			String val;
			int count = 0;
			LinkedList<Long> lista = temp.getDati();
			for(int i = 0; i<2 ;i++ )
			{
				if(lista.size()>0)
				{
					count++;
					val = toTemp1(lista.removeFirst()).toString();
					daScrivere += val+";";
				}
			}
			for(int i = 0; i<2 ;i++ )
			{
				if(lista.size()>0)
				{
					
					count++;
					val = toTemp2(lista.removeFirst()).toString();
					daScrivere += val+";";
				}
			}
			Double volt = new Double(0d);
			Double amp = new Double(0d);
			Double pres = new Double(0d);
			if(lista.size()>0)
			{
				volt = toVolt(lista.removeFirst());
				daScrivere += volt.toString()+";";
			}
			if(lista.size()>0)
			{
				amp = toAmp(lista.removeFirst());
				daScrivere += amp.toString()+";";
			}

			daScrivere += Double.toString(Math.round(volt*amp*100.0D)/100.0D)+";";
			
			if(lista.size()>0)
			{
				pres = toAtm(lista.removeFirst());;
			
			}
			while(lista.size()>0)
			{
				daScrivere += lista.removeFirst().toString()+";";
			}
			synchronized (file) {
				file.output.println(daScrivere);
			}
		}
	}
	
	private Double toTemp1 (Long media)
	{
		
		return new Double(Math.round(((media.doubleValue()*cont_voltaggio)/0.005D)*10.0D)/10.0D);
	}
	private Double toTemp2 (Long media)
	{
		
		return new Double(Math.round(((media.doubleValue()*cont_voltaggio)/0.010D)*10.0D)/10.0D);
	}
	private Double toAmp (Long media)
	{
		double temp = (media.doubleValue()*cont_voltaggio)/resistenza_amp;
		temp = Math.round(temp*100.0D)/100.0D;
		return new Double(temp);
	}
	private Double toAtm(Long media)
	{
		double temp = media - 102D;
		temp = temp*rapporto_pressione;
		return new Double(temp);
	}
	private Double toVolt(Long media) {
		double temp = media.doubleValue()*cont_voltaggio*rapporto_res;
		temp = Math.round(temp*100.0D)/100.0D;
		return new Double(temp);
		
	}

}
