/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package falcofinder.android.fuehrerschein.chat;


import falcofinder.android.fuehrerschein.R;
import falcofinder.android.fuehrerschein.Util;
import falcofinder.android.fuehrerschein.chat.client.ChatRequestFactory;
import falcofinder.android.fuehrerschein.chat.client.ChatRequestFactory.ChatRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;




import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main activity - requests "Hello, World" messages from the server and provides
 * a menu item to invoke the accounts activity.
 */
public class FuehrerscheinChatActivity extends Activity {
    /**
     * Tag for logging.
     */
    private static final String TAG = "FuehrerscheinChatActivity";

    /**
     * The current context.
     */
    private Context mContext = this;
    EditText edittextsubj; 
    EditText edittextbody;
    EditText edittextqid;
    CheckBox chkhideemail;
    private AdView adView;
    
  
    
  
    /**
     * Begins the activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
      //  Log.i(TAG, "onCreate zzzzzzzzzzzzzz");
        super.onCreate(savedInstanceState);
        
        boolean netavail = Util.isNetworkAvailable(mContext);
        
        if (!netavail) {
        	Toast.makeText(this, getString(R.string.nonet), Toast.LENGTH_LONG).show();
        	this.finish();
        	return;
        }
        
       // System.out.println("---->onCreate");
        
        Bundle bundle = this.getIntent().getExtras();
        SharedPreferences prefs = Util.getSharedPreferences(mContext);
        String frage=bundle.getString("frage");
        String qid=bundle.getString("qid");
        
      //  Log.i(TAG, "onCreate qid" + qid+"/"); 
        if (!"".equals(""+qid)) {
      
      // System.out.println("--->frage"+frage);
      // System.out.println("--->qid"+qid);
     
       SharedPreferences.Editor editor = prefs.edit();
       editor.putString("frage", frage);
       if ("-".equals(qid)) qid="";
       editor.putString("qid", qid);
       editor.putString("message", "compose");
   
       editor.commit();
     
        } else if ("close".equals(prefs.getString("message", ""))) {
    	   
       
        } else {
          
            
        	
        	 SharedPreferences.Editor editor = prefs.edit();
            		 editor.putString("frage", "");
             editor.putString("qid", "");
             editor.putString("message", "forum");
             editor.commit();
        }
   
      
       
        // Register a receiver to provide register/unregister notifications
      //  registerReceiver(mUpdateUIReceiver, new IntentFilter(Util.UPDATE_UI_INTENT));
    }

    @Override
    public void onResume() {
        super.onResume();
      //  System.out.println("---->onResume");
        SharedPreferences prefs = Util.getSharedPreferences(mContext);
        String connectionStatus = prefs.getString(Util.CONNECTION_STATUS, Util.DISCONNECTED);
      
        if ("close".equals(prefs.getString("message", ""))) {
        	 SharedPreferences.Editor editor = prefs.edit();
            editor.putString("message", "");
            editor.commit();
            
        	super.finish();	
     		 return;
     		  
     	  }
        
  	    if (Util.DISCONNECTED.equals(connectionStatus)) {
  	    //	System.out.println("--->connectionStatus AccountsActivity");
            startActivity(new Intent(this, AccountsActivity.class));
            return;
        }
        
    //   System.out.println("--->onResume message " + prefs.getString("message", ""));
  	 
        if ("forum".equals(prefs.getString("message", ""))) {
        	
        	Intent i = new Intent(getApplicationContext(), ChatsActivity.class);
         	Bundle bundle = new Bundle();
 
		   	i.putExtras(bundle);
	        
		    startActivity(i);
		    
        	return;
        	
        } else {
        	setContentView(R.layout.compose);
        
        initComposeFields();
        }
        
        addAd();

        
    }

    private void addAd() {
		// TODO Auto-generated method stub
    	 adView = new AdView(this, AdSize.BANNER, getString(R.string.admobunitid));
         
         
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

	private void initComposeFields() {
		// TODO Auto-generated method stub
    	 SharedPreferences prefs = Util.getSharedPreferences(mContext);
    	 
    	 edittextsubj = (EditText) findViewById(R.id.editTextSubject);
         edittextbody = (EditText) findViewById(R.id.editTextBody);
         edittextqid = (EditText) findViewById(R.id.editTextQid);
         
         chkhideemail = (CheckBox) findViewById(R.id.checkBoxHide);
         
         ImageButton btnback = (ImageButton) findViewById(R.id.btnlogo);
         

         btnback.setImageResource(R.drawable.furlogochatb);

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
	}

	/**
     * Shuts down the activity.
     */
    @Override
    public void onDestroy() {
      //  unregisterReceiver(mUpdateUIReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        // Invoke the Register activity
       // menu.getItem(0).setIntent(new Intent(this, AccountsActivity.class));
        
        
        return true;
    }

    /**
     * 
     * Invoked when the user selects an item from the Menu.
     *
     * @param item the Menu entry which was selected
     * @return true if the Menu item was legit (and we consumed it), false
     *         otherwise
     */
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    	SharedPreferences prefs = Util.getSharedPreferences(this);
    	
        switch (item.getItemId()) {
        
        case R.id.menuaccounts:
        //	System.out.println("--->onOptionsItemSelected menuaccounts");
        	startActivityForResult(new Intent(this, AccountsActivity.class),0);
        	setContentView(R.layout.compose);
        	 
        	// System.out.println("--->onOptionsItemSelected R.layout.compose");
        	 return true;
        	 
        
        }
		return true;
        
       }
    // Manage UI Screens

   
    
	public final void onClick(View v) {
	 	
    	String selview = "";
    	if (v.getTag()!=null) selview = ""+v.getTag();
    	
    	//System.out.println("selview " + selview);
    	
    	  final TextView helloWorld = (TextView) findViewById(R.id.hello_world);
          final Button sayHelloButton = (Button) findViewById(R.id.post);
    //	System.out.println("--->selview"+selview);
          
          boolean netavail = Util.isNetworkAvailable(mContext);
	    	//System.out.println("--->isNetworkAvailable"+""+getResources().getString(R.string.url));
          
	    	if (selview.equals("back")) {
	            
	    		super.finish();	

	          
	    		return;
	    	}
	    	if (selview.equals("home")) {
	    	 SharedPreferences prefs = Util.getSharedPreferences(mContext);
	    	 SharedPreferences.Editor editor = prefs.edit();
	            editor.putString("message", "close");
	            editor.commit();
	            
	            super.finish();	
	        	return;
	    	}
	            
	    	if (!netavail) {
	    		Toast.makeText(this, getString(R.string.nonet), Toast.LENGTH_LONG).show();
	    		return;
	    	}
	    	

          
    	if (selview.equals("tagpost")) {
    		

            final JSONObject object = new JSONObject();
            
            try {
            	String msgval="";
            	if ((""+edittextsubj.getText()).length()<5) {
            		
            		msgval = getString(R.string.nosubject);
            	}
            	
            	if ((""+edittextbody.getText()).length()<10) {
            		
            		msgval = getString(R.string.nobody);
            	}
            	
            	if ((""+edittextqid.getText()).length()>0
            			&& ((""+edittextqid.getText()).indexOf(".")<0)
            			&& ((""+edittextqid.getText()).length()<10)
            					
            			) {
            		
            		msgval = getString(R.string.noqid);
            	}
            		
            	if (!msgval.equals(""))	{
            		
            		Toast.makeText(this, msgval, Toast.LENGTH_LONG).show();
            		return;	
            	} else {
				object.put("subject", ""+edittextsubj.getText());
				object.put("qid", ""+edittextqid.getText());
				object.put("body", ""+edittextbody.getText());
				object.put("hideemail", ""+chkhideemail.isChecked());
				object.put("keystring", "");
		
				
            	}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
    		sayHelloButton.setEnabled(false);
            helloWorld.setText(R.string.contacting_server);
            
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
					 imm.hideSoftInputFromWindow(edittextsubj.getWindowToken(), 0);
					 
                }
            }.execute();
    	}
    	
    	
    	if (selview.equals("tagforum") || selview.equals("goforum")) {

    		Intent i = new Intent(getApplicationContext(), ChatsActivity.class);
         	Bundle bundle = new Bundle();
 
		   	i.putExtras(bundle);
	        
		    startActivity(i);
    	return;
    	}
    	
    	if (selview.equals("neuesthema")) {

    		setContentView(R.layout.compose);
            
            initComposeFields();
    	return;
    	}
    	
    	
	 }

  
    
   
    
    
}
