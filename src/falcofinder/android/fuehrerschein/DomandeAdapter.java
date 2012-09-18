package falcofinder.android.fuehrerschein;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class DomandeAdapter extends ArrayAdapter<Domanda> {
	
    private ArrayList<Domanda> domande;
    private final Context mContext;
    
    public DomandeAdapter(Context context, int textViewResourceId, ArrayList<Domanda> domande) {
        super(context, textViewResourceId, domande);
        mContext = context;
        this.domande = domande;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
  if (v == null) {
            LayoutInflater vi = (LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listitem, null);
  }
     
  Domanda d = domande.get(position);
  	if (d != null) {
            TextView domanda = (TextView) v.findViewById(R.id.txtdialogdomanda);
            ImageView immagine = (ImageView) v.findViewById(R.id.imgdialogodomanda);

         
            CheckBox cb1 = (CheckBox) v.findViewById(R.id.dialogdbutton1);
            CheckBox cb2 = (CheckBox) v.findViewById(R.id.dialogdbutton2);
            CheckBox cb3 = (CheckBox) v.findViewById(R.id.dialogdbutton3);
            
          
            Button gotoforum = (Button) v.findViewById(R.id.gotoforum);
            
            gotoforum.setTag("qid:"+d.iddom+"$"+d.domanda);
            gotoforum.setText("+forum:"+d.iddom);
            	
            
           // System.out.println(" d"+ d);
           // System.out.println(" d.correct1"+ d.correct1+"-"+(d.correct1.equals("0")));
           // System.out.println(" R.string.msgerror"+ R.string.msgerror);
           // System.out.println(" frage1add"+ frage1add);
	    		
            if (d.correct1.equals("0")) {
            	cb1.setText(d.frage1 + " " +  getContext().getString(R.string.msgerror));
            	cb1.setTextColor(Color.parseColor("#FF0000"));
   	    	} else {
   	    		cb1.setText(d.frage1);
            	cb1.setTextColor(Color.parseColor("#000000"));
   	    	}
            
            if (d.correct2.equals("0")) {
            	cb2.setText(d.frage2 + " " +  getContext().getString(R.string.msgerror));
            	cb2.setTextColor(Color.parseColor("#FF0000"));
   	    	} else {
   	    		cb2.setText(d.frage2);
            	cb2.setTextColor(Color.parseColor("#000000"));
   	    	}
            
            if (d.correct3.equals("0")) {
            	cb3.setText(d.frage3 + " " +  getContext().getString(R.string.msgerror));
            	cb3.setTextColor(Color.parseColor("#FF0000"));
   	    	} else {
   	    		cb3.setText(d.frage3);
            	cb3.setTextColor(Color.parseColor("#000000"));
   	    	}
     
            if (d.antwort1.equals("1")) cb1.setChecked(true);
            else cb1.setChecked(false);
            	
            if (d.antwort2.equals("1")) cb2.setChecked(true);
            else cb2.setChecked(false);
            
            if (d.antwort3.equals("1")) cb3.setChecked(true);
            else cb3.setChecked(false);
            
        
            if (d.frage2.trim().equals("")) {
            	cb2.setVisibility(CheckBox.INVISIBLE);
            	
            } 
            else {
            	cb2.setVisibility(CheckBox.VISIBLE);
            
            }
            
        //    System.out.println("--->domanda"+domanda.getText());
       //     System.out.println("--->frage3.getText()"+frage3.getText().toString()+"--");
            
	            if (d.frage3.trim().equals("")) {
	            	cb3.setVisibility(CheckBox.INVISIBLE);
	            	
	            } 
	            else {
	            	cb3.setVisibility(CheckBox.VISIBLE);
	            
	            }
            	
            
            
    
            
            
      if (domanda != null) {
    	  String testo = ""+d.domanda+" (" + d.fehlerpunkt + " Punkte)";
			 if (!d.header.trim().equals("")) testo += "\n" + d.header;
			 
    	  domanda.setText(new SpannableString(testo));
      }

     
   // System.out.println("--->d.image"+d.image.substring(0, d.image.indexOf(".")).trim() + "-");
      
 		 if (!d.image.trim().equals("")) { 
 			
 			 int resId = mContext.getResources().getIdentifier(d.image.trim(), "drawable", Util.getPackageName());
 			immagine.setImageResource(resId);
 			 //System.out.println("--->caricaDomandaresId"+arrQuiz[1].substring(0, arrQuiz[1].indexOf(".")));
 			// iv.setVisibility(TextView.VISIBLE);

 		 } else {
 			 int resId = mContext.getResources().getIdentifier("vuoto", "drawable", Util.getPackageName());
 			immagine.setImageResource(resId);

 		 }
    	  
      
  }
  return v;
    }
}

