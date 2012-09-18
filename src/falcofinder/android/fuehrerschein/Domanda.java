package falcofinder.android.fuehrerschein;

import android.os.Parcel;


public class Domanda   {
	
	

	
	public Domanda() {
		
	}
	
	 public Domanda(String domanda,String image,String frage1,String frage2,String frage3,String correct1,String correct2,String correct3,String antwort1,String antwort2,String antwort3, String fehlerpunkt,String header, String iddom) {
	        this.domanda = domanda;
	        this.image = image;
	        this.frage1 = frage1;
	        this.frage2 = frage2;
	        this.frage3 = frage3;
	        this.correct1 = correct1;
	        this.correct2 = correct2;
	        this.correct3 = correct3;
	        this.antwort1 = antwort1;
	        this.antwort2 = antwort2;
	        this.antwort3 = antwort3;	       
	        
	        this.fehlerpunkt = fehlerpunkt;
	        this.header = header;
	        this.iddom = iddom;
	        
	    }
	 
	 
	 public String image="";
	 public String domanda="";
	 public String frage1="";
	 public String frage2="";
	 public String frage3="";
	 public String correct1="";
	 public String correct2="";
	 public String correct3="";
	 public String antwort1="";
	 public String antwort2="";
	 public String antwort3="";
	 public String fehlerpunkt="";
	 public String header="";	 
	 public String iddom="";
	 
	 public int nfrage;
	 
	

 
}

