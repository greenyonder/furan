package falcofinder.android.fuehrerschein.chat;

import falcofinder.android.fuehrerschein.FuehrerscheinActivity;
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
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class ChatDetailsActivity extends Activity {

	private Context mContext = this;

	 private static final String TAG = "ChatDetailsActivity";
	 private String keystring;
	 
	 private String qid;
	
	 ArrayList<ChatMessage> alChats;
	 
	   EditText edittextsubj; 
	    EditText edittextbody;
	    EditText edittextqid;
	    CheckBox chkhideemail;
	     ListView lv;
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.chatdetails);
	        Bundle bundle = this.getIntent().getExtras();
	        keystring =  bundle.getString("keystring");
	        
	        edittextsubj = (EditText) findViewById(R.id.editTextSubject);
	         edittextbody = (EditText) findViewById(R.id.editTextBody);
	         edittextqid = (EditText) findViewById(R.id.editTextQid);
	         
	         chkhideemail = (CheckBox) findViewById(R.id.checkBoxHide);
	         
	         lv = (ListView)findViewById(R.id.listView1);
	        
	         ImageButton btnlogo = (ImageButton) findViewById(R.id.btnlogo);
	         btnlogo.setImageResource(R.drawable.furlogochatb);
	         btnlogo.setTag("back");
	         
	       loadChatDetails();
	       initViews();
	        
	    }

	private void initViews() {
		// TODO Auto-generated method stub
	
		edittextsubj.setVisibility(EditText.INVISIBLE);
		

         
		TextView tvsubj = (TextView) findViewById(R.id.subj);
		tvsubj.setVisibility(TextView.INVISIBLE);
		tvsubj.setHeight(0);
		EditText etqid = (EditText) findViewById(R.id.editTextQid);
		edittextqid.setVisibility(EditText.INVISIBLE);
		edittextqid.setHeight(0);
		
		TextView tvqid = (TextView) findViewById(R.id.qid);
		tvqid.setVisibility(TextView.INVISIBLE);
		tvqid.setHeight(0);
		
		LinearLayout linearLayoutsubj = (LinearLayout) findViewById(R.id.linearLayoutsubj);
		
		linearLayoutsubj.setLayoutParams(new LinearLayout.LayoutParams(0,0));
		
		Button postbnt = (Button) findViewById(R.id.post);
		
		postbnt.setText(R.string.antworten);
		
		 initComposeFields();
	}

	 private void initComposeFields() {
			// TODO Auto-generated method stub
	    	 SharedPreferences prefs = Util.getSharedPreferences(mContext);
	    	 
	    	 edittextsubj = (EditText) findViewById(R.id.editTextSubject);
	         edittextbody = (EditText) findViewById(R.id.editTextBody);
	         edittextqid = (EditText) findViewById(R.id.editTextQid);
	         
	         chkhideemail = (CheckBox) findViewById(R.id.checkBoxHide);
	         
	         
	        String frage = prefs.getString("frage", "");
	        String qid = prefs.getString("qid", "");
	    
			       Spannable subj = new SpannableString(frage);   
			       edittextsubj.setText(subj);
			       
			       Spannable spqid = new SpannableString(qid);   
			       edittextqid.setText(qid);
			       
						// kill keyboard when enter is pressed
			       edittextsubj.setOnKeyListener(new OnKeyListener()
						{
						/**
						* This listens for the user to press the enter button on
						* the keyboard and then hides the virtual keyboard
						*/
										 @Override
										 public boolean onKey(View arg0, int arg1, KeyEvent event) {
										 // If the event is a key-down event on the "enter" button
												 if ( (event.getAction() == KeyEvent.ACTION_DOWN ) &&
												 (arg1 == KeyEvent.KEYCODE_ENTER) )
												 {
												 InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
												 imm.hideSoftInputFromWindow(edittextsubj.getWindowToken(), 0);
												 return true;
												 }
												 return false;
										 }
						
						
						
						} );
						
			    // kill keyboard when enter is pressed
			       edittextqid.setOnKeyListener(new OnKeyListener()
						{
						/**
						* This listens for the user to press the enter button on
						* the keyboard and then hides the virtual keyboard
						*/
										 @Override
										 public boolean onKey(View arg0, int arg1, KeyEvent event) {
										 // If the event is a key-down event on the "enter" button
												 if ( (event.getAction() == KeyEvent.ACTION_DOWN ) &&
												 (arg1 == KeyEvent.KEYCODE_ENTER) )
												 {
												 InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
												 imm.hideSoftInputFromWindow(edittextqid.getWindowToken(), 0);
												 return true;
												 }
												 return false;
										 }
						
						
						
						} );
			       
					// kill keyboard when enter is pressed
			       edittextbody.setOnKeyListener(new OnKeyListener()
						{
						/**
						* This listens for the user to press the enter button on
						* the keyboard and then hides the virtual keyboard
						*/
										 @Override
										 public boolean onKey(View arg0, int arg1, KeyEvent event) {
										 // If the event is a key-down event on the "enter" button
												 if ( (event.getAction() == KeyEvent.ACTION_DOWN ) &&
												 (arg1 == KeyEvent.KEYCODE_ENTER) )
												 {
												 InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
												 imm.hideSoftInputFromWindow(edittextbody.getWindowToken(), 0);
												 return true;
												 }
												 return false;
										 }
						
						
						
						} );
			       
			   

         		  AdView adView = new AdView(this,  AdSize.BANNER, getString(R.string.admobunitid));
         	         
         	         LinearLayout footer = (LinearLayout) findViewById(R.id.footer);
         	        
         	        // Add the adView to it
         	        footer.addView(adView);


         	          AdRequest request = new AdRequest();

         	          request.addKeyword("Test für die theoretische Führerschein Prüfung Auto Quiz trainer Quiz Deutschland Germany Deutsch");
         	          
         	   
         	        
         	           request.addTestDevice(AdRequest.TEST_EMULATOR);
         	          request.addTestDevice("HT08YPL02569");    // My T-Mobile G1 test phone
         	          request.addTestDevice("95CF54CD666F6634114A210B4B6B671B");
         	        // Initiate a generic request to load it with an ad
         	          adView.loadAd(request);
		}
	 
	private void loadChatDetails() {
		// TODO Auto-generated method stub

         
         //System.out.println("--->loadChats");
        
         
		// TODO Auto-generated method stub
    	  new AsyncTask<Void, Void, String>() {
              private String message;

              @Override
              protected String doInBackground(Void... arg0) {
                  ChatRequestFactory requestFactory = Util.getRequestFactory(mContext,
                  		ChatRequestFactory.class);
                  final ChatRequest request = requestFactory.chatRequest();
                  Log.i(TAG, "Sending request to server");
                  request.getChatDetailsList(keystring, Util.getVersion(getPackageManager())).fire(new Receiver<String>() {
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
						JSONArray jsonarray = new JSONArray(result);
						
						 alChats = new ArrayList<ChatMessage>();
						
						for (int i = 0; i < jsonarray.length(); i++) {
						    JSONObject row = jsonarray.getJSONObject(i);
						    ChatMessage chatmessage = new ChatMessage();
						    
						   // System.out.println("--->onPostExecute keyfather"+row.getString("keyfather"));
						   
						    qid = row.getString("qid");
						  //  System.out.println("--->onPostExecute setShowqidbtn(true);");
						    	
						    	chatmessage.setQid(qid);
						    	 chatmessage.setVisibilityButtons(true);
						    
						    chatmessage.setDate(new Date(""+row.getString("date")));
						    chatmessage.setEmail(row.getString("email"));
						    chatmessage.setEmailto(row.getString("emailto"));
						    chatmessage.setSubject(row.getString("subject"));
						    chatmessage.setBody(row.getString("body"));
						    chatmessage.setKeyfather(""+row.getString("keyfather"));
						    /*
						     don't need this. In case  I need it in the future, remember to check ou the
						     part where a new chat is added as that should be corrected as well
						    chatmessage.setKeyfather(row.getString("keyfather"));
						    chatmessage.setKeystring(row.getString("keystring"));
						    */
						    alChats.add(chatmessage);
						   // System.out.println("--->onPostExecute chatmessage"+chatmessage);
						    //System.out.println("--->onPostExecute chatmessage"+chatmessage.getBody());
						    // System.out.println("--->onPostExecute chatmessage getKey"+chatmessage.getKeystring());
						}
						//System.out.println("---> alChats.size() " + alChats.size());
						
						
						for (int l=0;l<4;l++) {
							ChatMessage c = new ChatMessage();
							
							c.setQid("");
							c.empty = true;
							
							 alChats.add(c);
							
						}
						
							lv.setAdapter(new ChatsAdapter(ChatDetailsActivity.this, android.R.layout.simple_list_item_1, alChats ));
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                 
              }
          }.execute();
	
	}
	
	public final void onClick(View v) {
	 	
    	String selview = "";
    	if (v.getTag()!=null) selview = ""+v.getTag();
    	
    	//System.out.println("--->selview"+selview);
    	  if (selview.equals("back") || selview.equals("tagforum") ) {
				
				super.finish();
				return;
			}
    	  
    	  if (selview.equals("home")) {
				
    		  super.finish();
    		  
    		  Intent i = new Intent(getApplicationContext(), FuehrerscheinMainActivity.class);
           
              startActivity(i);
              
				return;
			}
    	  
    	  if (selview.indexOf("qid")==0 || selview.indexOf("ibq")==0) {
    		  
    	 
    		  Intent i = new Intent(getApplicationContext(), FuehrerscheinActivity.class);
    		
    	 		
    	 		i.putExtra("modedefault", "10");
    	 		i.putExtra("qid", selview.substring(4));
      	
   		    startActivity(i);
   		
   		    return;
    	  }
    	  
    	 boolean netavail = Util.isNetworkAvailable(mContext);
	    	//System.out.println("--->isNetworkAvailable"+""+getResources().getString(R.string.url));
	    	if (!netavail) {
	    		Toast.makeText(this, getString(R.string.nonet), Toast.LENGTH_LONG).show();
	    		return;
	    	}
    	
    	 final TextView helloWorld = (TextView) findViewById(R.id.hello_world);
         final Button sayHelloButton = (Button) findViewById(R.id.post);
         
         
         
         
	if (selview.equals("tagpost")) {
    		
    		sayHelloButton.setEnabled(false);
            helloWorld.setText(R.string.contacting_server);

            final JSONObject object = new JSONObject();
            
            try {
            	object.put("qid", "");
            	object.put("subject", "");
            	
				object.put("body", ""+edittextbody.getText());
				object.put("hideemail", ""+chkhideemail.isChecked());
				object.put("keystring", keystring);
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            // Use an AsyncTask to avoid blocking the UI thread
            new AsyncTask<Void, Void, String>() {
                private String message;

                @Override
                protected String doInBackground(Void... arg0) {
                    ChatRequestFactory requestFactory = Util.getRequestFactory(mContext,
                    		ChatRequestFactory.class);
                    final ChatRequest request = requestFactory.chatRequest();
                    Log.i(TAG, "Sending request to server");
                    request.getChat(object.toString(), Util.getVersion(getPackageManager())).fire(new Receiver<String>() {
                        @Override
                        public void onFailure(ServerFailure error) {
                            message = "Failure: " + error.getMessage();
                        }

                        @Override
                        public void onSuccess(String result) {
                            message = result;
                        }
                    });
                    return message;
                }

                @Override
                protected void onPostExecute(String result) {
                    helloWorld.setText(result);
                    sayHelloButton.setEnabled(true);
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
					 imm.hideSoftInputFromWindow(edittextbody.getWindowToken(), 0);
					 
                    loadChatDetails();
				
                }
            }.execute();
    	}
	}
	  
}
