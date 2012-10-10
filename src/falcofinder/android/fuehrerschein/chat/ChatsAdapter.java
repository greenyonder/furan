package falcofinder.android.fuehrerschein.chat;




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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import falcofinder.android.fuehrerschein.R;




public class ChatsAdapter extends ArrayAdapter<ChatMessage> {
	 private ArrayList<ChatMessage> chatmessages;
	    private final Context mContext;
	    
	    public ChatsAdapter(Context context, int textViewResourceId, ArrayList<ChatMessage> chatmessages) {
	        super(context, textViewResourceId, chatmessages);
	        mContext = context;
	        this.chatmessages = chatmessages;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	      
	      
	    	ViewHolder holder = null;
	        
	  if (convertView == null) {
		  
		// Log.i("getView","getView convertView == null position" + position);
		  
	            LayoutInflater vi = (LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = vi.inflate(R.layout.listitemchat, null);
	            
	            holder = new ViewHolder();
	            holder.txtdate = (TextView) convertView.findViewById(R.id.txtdate);
	            holder.txtemail = (TextView) convertView.findViewById(R.id.txtemail);
	            holder.txtqid = (TextView) convertView.findViewById(R.id.txtqid);
	            holder.txtsubject = (TextView) convertView.findViewById(R.id.txtsubject);
	            holder.txtbody = (TextView) convertView.findViewById(R.id.txtbody);
	            holder.gotoqid = (Button) convertView.findViewById(R.id.gotoqid);
	            holder.gotoqidib = (ImageButton) convertView.findViewById(R.id.gotoqidib);
	            convertView.setTag(holder);
	  } else {
		  holder = (ViewHolder) convertView.getTag();
			// Log.i("getView","getView convertView != null position" + position);
	  }
	     
	  ChatMessage c = chatmessages.get(position);
	  
	  	if (c!= null) {
	  	
	            
	  		holder.txtdate.setVisibility(TextView.VISIBLE);
	  		holder.txtemail.setVisibility(TextView.VISIBLE);
	  		holder.txtqid.setVisibility(TextView.VISIBLE);
	  		holder.txtsubject.setVisibility(TextView.VISIBLE);
	  		holder.txtbody.setVisibility(TextView.VISIBLE);
	            
	            
	  		holder.txtqid.setText(R.string.qid);
	            
	  		holder.txtdate.setText(""+c.getDate());
	            if (c.getEmail().equals("")) {
	            	holder.txtemail.setText(mContext.getString(R.string.from) + ":" + mContext.getString(R.string.anon));
	            } else {
	            	holder.txtemail.setText(mContext.getString(R.string.from) + ":" + c.getEmail());
	            }

	            
            	//System.out.println("--->c.isShowqidbtn()"+c.isShowqidbtn());
            	
	           
	            
	            //System.out.println("--->c.getQid().equals()"+c.getQid().equals(""));
	         //   System.out.println("--->txtsubject"+c.getSubject().equals("")+c.getSubject());
            	
	            if (c.empty) {
	            	holder.txtdate.setText("");
	            	holder.txtemail.setText("");
	            	holder.txtqid.setVisibility(View.GONE);
	            	//holder.txtqid.setHeight(0);
	            	//holder.btnqid.setHeight(0);
	            	holder.gotoqid.setVisibility(View.GONE);
	            	holder.gotoqidib.setVisibility(View.GONE);
	            	//holder.txtsubject.setHeight(0);
	            	holder.txtbody.setVisibility(View.GONE);
	            	//txtbody.setHeight(0);
	            	holder.txtsubject.setVisibility(View.GONE);
            		
            		
	            	
	  			} else if (c.getSubject().equals("")) {
	  				
	  				
	  				holder.txtqid.setVisibility(View.GONE);
	  				//holder.txtqid.setHeight(0);
	  				//holder.btnqid.setHeight(0);
	  				holder.gotoqid.setVisibility(View.GONE);
	  				holder.gotoqidib.setVisibility(View.GONE);
	            //	txtsubject.setHeight(0);
            		holder.txtsubject.setVisibility(View.GONE);
	            } else {
	            	
	
	            	
	            	
	            	if (!c.getQid().equals("") && c.getKeyfather().equals("") && c.isVisibilityButtons()) {
	            //	System.out.println("---> BOTTONI qid");
	            		holder.gotoqid.setVisibility(Button.VISIBLE);
	            		holder.gotoqidib.setVisibility(ImageButton.VISIBLE);
	            		holder.txtqid.setText(mContext.getString(R.string.qid) +": ");	
	            		holder.txtqid.setVisibility(TextView.VISIBLE);	
	            		holder.gotoqid.setText(c.getQid());
	            		holder.gotoqid.setTag(mContext.getString(R.string.qid)+ ":"+ c.getQid());
	            		
	            	} else {
	            		//txtqid.setText(mContext.getString(R.string.qid)+ ":"+ c.getQid());
	            		//holder.txtqid.setHeight(0);
	            		//holder.btnqid.setHeight(0);
	            		holder.gotoqid.setVisibility(View.GONE);
	            		holder.gotoqidib.setVisibility(View.GONE);
	            		holder.txtqid.setVisibility(View.GONE);
	            		
	            	}
	            	
	           	 
	            	holder.txtsubject.setText(mContext.getString(R.string.subj) + ":" + c.getSubject());
	            	holder.txtsubject.setVisibility(TextView.VISIBLE);
        		 
        		 
	            }
	            
	           
	            holder.txtbody.setText(mContext.getString(R.string.body) + ":" +c.getBody());
	      //System.out.println("--->holder txtsubject"+holder.txtsubject.getText());
	  	}
	  return convertView;
	    }
	    
	    public static class ViewHolder {

	    	TextView txtemail; 
	        TextView txtqid; 
	        TextView txtsubject; 
	        TextView txtdate;
	        TextView txtbody; 
	        Button gotoqid; 
	        ImageButton gotoqidib;
	        
	    }
}
