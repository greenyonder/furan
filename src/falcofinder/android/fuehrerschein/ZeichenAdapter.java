package falcofinder.android.fuehrerschein;




import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import falcofinder.android.fuehrerschein.R;




public class ZeichenAdapter extends ArrayAdapter<String[]> {
	 private ArrayList<String[]> alZeichen;
	    private final Context mContext;
	    
	
	    
	    public ZeichenAdapter(Context context, int textViewResourceId, ArrayList<String[]> alZeichen) {
	        super(context, textViewResourceId, alZeichen);
	        mContext = context;
	        this.alZeichen = alZeichen;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	      
	      
	    	ViewHolder holder = null;
	        
	  if (convertView == null) {
		  
		// Log.i("getView","getView convertView == null position" + position);
		  
	            LayoutInflater vi = (LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = vi.inflate(R.layout.listitemzeichen, null);
	            
	            holder = new ViewHolder();
	            holder.txtzeichen = (TextView) convertView.findViewById(R.id.txtzeichen);
	            holder.imgzeichen = (ImageView) convertView.findViewById(R.id.imgzeichen);
	           
	            convertView.setTag(holder);
	  } else {
		  holder = (ViewHolder) convertView.getTag();
			// Log.i("getView","getView convertView != null position" + position);
	  }
	     
	  String[] s = alZeichen.get(position);
	  
	  	if (s!= null) {

	  		holder.txtzeichen.setVisibility(TextView.VISIBLE);
	  		holder.imgzeichen.setVisibility(TextView.VISIBLE);

	    
	            if (s.length==0) {
	            	
	            	holder.txtzeichen.setText("");
	            	holder.txtzeichen.setVisibility(View.GONE);
	            	//holder.txtqid.setHeight(0);
	            	//holder.btnqid.setHeight(0);
	            	holder.imgzeichen.setVisibility(View.GONE);
	            	
            	
	            } else {
	
	            	holder.txtzeichen.setText(s[1]);
	            	holder.txtzeichen.setVisibility(TextView.VISIBLE);
        		 
	            	//System.out.println("--->" + "z" + s[0].trim());
	            	s[0] = s[0].replace("-", "_");
	            	s[0] = s[0].replace(".", "_");
	            	 int resId = mContext.getResources().getIdentifier("z" + s[0].trim(), "drawable", Util.getPackageName());
	            	 
	            	 if (resId<=0) System.out.println("--->resId" + resId + "-" + s[0].trim());
	            	 holder.imgzeichen.setImageResource(resId);
	            }
	            
	           
	          
	  	}
	  return convertView;
	    }
	    
	    public static class ViewHolder {

	    	TextView txtzeichen; 
	        ImageView imgzeichen; 
	       
	    }
}
