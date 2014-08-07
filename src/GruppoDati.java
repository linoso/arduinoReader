import java.util.LinkedList;


public class GruppoDati {
	int numero_letture;
	LinkedList<Long> dati_list;
	
	public GruppoDati() {
		this.numero_letture = 0;
		dati_list = new LinkedList<Long>();
	}
	
	public void aggiungi_dati(String lettura)
	{
		String[] dati_letti = lettura.split(";");
		//System.out.println("grupppo dati acquisizz" + dati_letti[]);
		for(int i = 0; i<dati_letti.length; i++)
		{
			//System.out.println("grupppo dati acquisizz" + dati_letti[i]);
			Long dato = Long.parseLong(dati_letti[i]);
			if(i>=dati_list.size())
			{
				dati_list.add(Long.parseLong(dati_letti[i]));
			}
			else
			{
				Long temp = dati_list.get(i);
				temp+= dato;
			}
		}
		numero_letture++;
	}
	
	public LinkedList<Long> getDati()
	{	
		for(int i=0; i>dati_list.size();i++)
		{	
			Long temp = dati_list.get(i);
			temp = temp / numero_letture;
			
		}
		return dati_list;
		
	}
}
