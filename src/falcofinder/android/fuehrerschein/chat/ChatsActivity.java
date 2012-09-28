package falcofinder.android.fuehrerschein.chat;
import falcofinder.android.fuehrerschein.FuehrerscheinMainActivity;
import falcofinder.android.fuehrerschein.R;

import falcofinder.android.fuehrerschein.Util;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import falcofinder.android.fuehrerschein.chat.client.ChatRequestFactory;
import falcofinder.android.fuehrerschein.chat.client.ChatRequestFactory.ChatRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChatsActivity extends Activity implements OnItemClickListener {
	private Context mContext = this;
	 private static final String TAG = "ChatsActivity";
	 
	 ArrayList<ChatMessage> alTopics;
	 
	  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       // this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        
        setContentView(R.layout.chats);
      //  setProgressBarIndeterminateVisibility(true);
        ImageButton btnlogo = (ImageButton) findViewById(R.id.btnlogo);
        btnlogo.setImageResource(R.drawable.furlogochatb);
        btnlogo.setTag("back");
        
       
       
       AdView adView = new AdView(this, AdSize.BANNER, getString(R.string.admobunitid));
       
       
       LinearLayout layout = (LinearLayout) findViewById(R.id.footer);

      // Add the adView to it
        layout.addView(adView);


        AdRequest request = new AdRequest();

        request.addKeyword("Test für die theoretische Führerschein Prüfung Auto Quiz trainer Quiz Deutschland Germany");
        
 
      
         request.addTestDevice(AdRequest.TEST_EMULATOR);
        request.addTestDevice("HT08YPL02569");    // My T-Mobile G1 test phone
        request.addTestDevice("95CF54CD666F6634114A210B4B6B671B");
      // Initiate a generic request to load it with an ad
        adView.loadAd(request);
        
    }
    

    
   
    
   
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	//System.out.println("---ChatsActivity onResume");
    	
    	ProgressBar btnlogo = (ProgressBar) findViewById(R.id.progbar);
    	btnlogo.setVisibility(ProgressBar.VISIBLE);
    	loadChats();
    	
    	
    }
    private void loadChats() {
    	 final ListView lv = (ListView)findViewById(R.id.listView1);
    	 final TextView hello_world = (TextView)findViewById(R.id.hello_world);
    	 
    	 
       //  System.out.println("--->loadChats");
         lv.setOnItemClickListener(this);
         
		// TODO Auto-generated method stub
    	  new AsyncTask<Void, Void, String>() {
              private String message;

              @Override
              protected String doInBackground(Void... arg0) {
                  ChatRequestFactory requestFactory = Util.getRequestFactory(mContext,
                  		ChatRequestFactory.class);
                  final ChatRequest request = requestFactory.chatRequest();
                  Log.i(TAG, "Sending request to server");
                 // System.out.println("--->Sending request to server");
                  request.getChatList("", Util.getVersion(getPackageManager())).fire(new Receiver<String>() {
                      @Override
                      public void onFailure(ServerFailure error) {
                          message = "Failure: " + error.getMessage();
                          
                      }

                      @Override
                      public void onSuccess(String result) {
                          message = result;
                      }
                  });
                  
                  //System.out.println("--->loadChats");
                  
                  return message;
              }

              @Override
              protected void onPostExecute(String result) {
                 
            	  try {
            		  
            		 // System.out.println("--->onPostExecute result"+result+"---");
            		  
						JSONArray jsonarray = new JSONArray(result);
						//System.out.println("---> jsonarray " + jsonarray.toString(2));
						
						alTopics = new ArrayList<ChatMessage>();
						
						for (int i = 0; i < jsonarray.length(); i++) {
						    JSONObject row = jsonarray.getJSONObject(i);
						    
						    //System.out.println("---> row " + row.toString(2));
						    
						    ChatMessage chatmessage = new ChatMessage();
						    //System.out.println("--->row"+"qid"+row.getString("qid"));
						    if ("00000".equals(""+row.getString("qid"))) {
						    	
						    	hello_world.setText(R.string.msg0posts);
						    	break;
						    }
						    chatmessage.setQid(row.getString("qid"));
						    
						   // Log.i("DATE" , ""+row.getString("date"));
						    
						    chatmessage.setDate(new Date(""+row.getString("date")));
						    chatmessage.setEmail(row.getString("email"));
						    chatmessage.setEmailto(row.getString("emailto"));
						    chatmessage.setSubject(row.getString("subject"));
						    chatmessage.setBody(row.getString("body"));
						    chatmessage.setKeyfather(row.getString("keyfather"));
						    chatmessage.setKeystring(row.getString("keystring"));
						    
						    chatmessage.setVisibilityButtons(false);
						    
						    alTopics.add(chatmessage);
						  //  System.out.println("--->onPostExecute chatmessage"+chatmessage);
						 //   System.out.println("--->onPostExecute chatmessage"+chatmessage.getBody());
						 //   System.out.println("--->onPostExecute chatmessage getKey"+chatmessage.getKeystring());
						}
						
						ChatMessage empty = new ChatMessage();
						empty.empty=true;
						alTopics.add(empty);
						
						lv.setAdapter(new ChatsAdapter(ChatsActivity.this, android.R.layout.simple_list_item_1, alTopics ));
						
					//	setProgressBarIndeterminateVisibility(false);
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Toast.makeText(ChatsActivity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
						hello_world.setText(String.format(getString(R.string.msgerrretrive), e.getMessage()));
						e.printStackTrace();
						
					} finally {
				    	ProgressBar btnlogo = (ProgressBar) findViewById(R.id.progbar);
				    
				    	btnlogo.setVisibility(ProgressBar.INVISIBLE);
						
					}
                 
              }
          }.execute();
	}

	@Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		 boolean netavail = Util.isNetworkAvailable(mContext);
	    	//System.out.println("--->isNetworkAvailable"+""+getResources().getString(R.string.url));
	    	if (!netavail) {
	    		Toast.makeText(this, getString(R.string.nonet), Toast.LENGTH_LONG).show();
	    		return;
	    	}
	    	
	ChatMessage cm =  alTopics.get(arg2);
	
	if(arg2!=alTopics.size()-1) {//l'ultimo slot e' vuoto
	
	Intent intent = new Intent(this, ChatDetailsActivity.class);
	   Bundle bundle = new Bundle();
	   bundle.putString("keystring", ""+cm.getKeystring());
	   intent.putExtras(bundle);
	   
   	startActivity(intent);
	}
	
		
	}

		public final void onClick(View v) {
			 	
		    	String selview = "";
		    	if (v.getTag()!=null) selview = ""+v.getTag();
		    	
		    	   //System.out.println("--->selview"+selview);
		    	   
		    if (selview.equals("back") || selview.equals("home")) {
		    	  SharedPreferences prefs = Util.getSharedPreferences(mContext);
		    	 SharedPreferences.Editor editor = prefs.edit();
		            editor.putString("message", "close");
		            editor.commit();
		            
				super.finish();
				
				//Intent intent = new Intent(this, FuehrerscheinMainActivity.class);
				//startActivity(intent);
				
			}
		 
		    if (selview.equals("gotocompose")) {
				
		    	
				super.finish();
				
				Intent intent = new Intent(this, FuehrerscheinChatActivity.class);
				 Bundle bundle = new Bundle();
				   bundle.putString("qid", "-");
				   bundle.putString("nfrage", "");
				   intent.putExtras(bundle);
				   
				startActivity(intent);
				
			}
		    
		    
		}
}
