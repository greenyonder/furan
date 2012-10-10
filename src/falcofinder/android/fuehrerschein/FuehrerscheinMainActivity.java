package falcofinder.android.fuehrerschein;



import java.io.IOException;
import java.util.ArrayList;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.AdRequest.ErrorCode;


import falcofinder.android.fuehrerschein.db.DataBaseHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class FuehrerscheinMainActivity extends Activity  implements AdListener {
    /** Called when the activity is first created. */
	 /** Called when the activity is first created. */
	  private static String MY_AD_UNIT_ID; 
		private AdView adView;
		/*ArrayList<Domanda> alDomande2;
		ArrayList<Domanda> alDomande3;
		ArrayList<Domanda> alDomande4;
		ArrayList<Domanda> alDomande5;
*/
	Dialog dialogHelp;
	int numeroquiz=0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        MY_AD_UNIT_ID = getString(R.string.admobunitid);
        
        Bundle bundle = this.getIntent().getExtras();
        if (bundle!=null) {
        	String modedefault=""+bundle.getString("modedefault");
        	
        	if ("10".equals(modedefault)) {
        	
        	Intent i = new Intent(getApplicationContext(), FuehrerscheinActivity.class);
         	
   
		   	i.putExtras(bundle);
		    startActivity(i);
		    
        	return;
        	}
        	
        }
        
        setContentView(R.layout.main_fuehrerschein);
        
       
	
		 numeroquiz = getNumeroquiz();
		
		// Toast.makeText(getApplicationContext(), 
		 //		 ""+numeroquiz, 
		 //        Toast.LENGTH_LONG).show();
		 
        /**
         * Creating all buttons instances
         * */
        // Dashboard button
        Button btn1 = (Button) findViewById(R.id.btn1);
 
        // Dashboard  button
        Button btn2 = (Button) findViewById(R.id.btn2);
 
        // Dashboard  button
        Button btn3 = (Button) findViewById(R.id.btn3);
 
        // Dashboard  button
        Button btn4 = (Button) findViewById(R.id.btn4);
 
        // Dashboard  button
        Button btn5 = (Button) findViewById(R.id.btn5);
 
        // Dashboard  button
        Button btn6 = (Button) findViewById(R.id.btn6);
        
        // Dashboard  button
        Button btn7 = (Button) findViewById(R.id.btn7);
 
        // Dashboard  button
        Button btn8 = (Button) findViewById(R.id.btn8);
        
        Button mnubtn1 = (Button) findViewById(R.id.mnubtn1);
        
        /**
         * Handling all button click events
         * */
 
        
        btn1.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                
            	Intent i = new Intent(getApplicationContext(), FuehrerscheinActivity.class);
             	Bundle bundle = new Bundle();
             	
		       // System.out.println("--->arrdomande prima A");
             	/*ArrayList<Domanda> al = new ArrayList();
             	al.addAll(alDomande2);
             	al.addAll(alDomande3);
             	al.addAll(alDomande4);
             	al.addAll(alDomande5);
             	*/
             	bundle.putString("modedefault","1");

             
             	//i.putParcelableArrayListExtra("arrdomande", al);
             	
    		   	i.putExtras(bundle);
    		   	// System.out.println("--->arrdomande prima di startActivity");
		        
    		    startActivity(i);
    		    
                
            }
        });
 
       
        btn2.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
               
            	Intent i = new Intent(getApplicationContext(), FuehrerscheinActivity.class);
             	Bundle bundle = new Bundle();
             	
             	bundle.putString("modedefault","2");
             	//i.putParcelableArrayListExtra("arrdomande", alDomande2);
             
    		   	i.putExtras(bundle);
                startActivity(i);
            }
        });
 
       
        btn3.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
             
            	Intent i = new Intent(getApplicationContext(), FuehrerscheinActivity.class);
             	Bundle bundle = new Bundle();
             	
             	bundle.putString("modedefault","3");
             	//i.putParcelableArrayListExtra("arrdomande", alDomande3);
    		   	i.putExtras(bundle);
                startActivity(i);
            }
        });
 
        
     
        
        btn4.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
               
             	Intent i = new Intent(getApplicationContext(), FuehrerscheinActivity.class);
             	Bundle bundle = new Bundle();
             	
             	bundle.putString("modedefault","4");
             	//i.putParcelableArrayListExtra("arrdomande", alDomande4);
    		   	i.putExtras(bundle);
                startActivity(i);
            }
        });
        
        
        
 
        btn5.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View view) {
               
             	Intent i = new Intent(getApplicationContext(), FuehrerscheinActivity.class);
             	Bundle bundle = new Bundle();
             	bundle.putString("modedefault","5");
             //	i.putParcelableArrayListExtra("arrdomande", alDomande5);
    		   	i.putExtras(bundle);
                startActivity(i);
            }
        });
 
        btn6.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View view) {
               
             	Intent i = new Intent(getApplicationContext(), FuehrerscheinActivity.class);
             	Bundle bundle = new Bundle();
             	bundle.putString("modedefault","0");
             	bundle.putString("numeroquiz", ""+numeroquiz);
             	//i.putParcelableArrayListExtra("arrdomande", alDomande5);
    		   	i.putExtras(bundle);
                startActivity(i);
            }
        });
      
        ActionItem chatAction = new ActionItem();
        
        chatAction.setTitle("Nie Mehr Fahrschule");
        chatAction.setIcon(getResources().getDrawable(R.drawable.forum));
 
        /// zeichenAction
        ActionItem zeichenAction = new ActionItem();
        
        zeichenAction.setTitle(getString(R.string.menu_zeichen));
        zeichenAction.setIcon(getResources().getDrawable(R.drawable.furiconfurzeichen));
        
        ActionItem motoAction = new ActionItem();
        
        motoAction.setTitle(getString(R.string.menu_moto));
        motoAction.setIcon(getResources().getDrawable(R.drawable.furiconfurmoto));
        
        ActionItem mofaAction = new ActionItem();
        
        mofaAction.setTitle(getString(R.string.menu_mofa));
        mofaAction.setIcon(getResources().getDrawable(R.drawable.furiconfurmofa));
        

        
        /// ERRORS
        ActionItem fehlerAction = new ActionItem();
        
        fehlerAction.setTitle(getString(R.string.menu_errori));
        fehlerAction.setIcon(getResources().getDrawable(R.drawable.fehler));
        
        ActionItem resetAction = new ActionItem();
        
        resetAction.setTitle(getString(R.string.menu_reset));
        resetAction.setIcon(getResources().getDrawable(R.drawable.reset));

        
       
        

   
       
        final QuickAction mQuickAction  = new QuickAction(this);
        mQuickAction.addActionItem(chatAction);
        mQuickAction.addActionItem(zeichenAction);
        mQuickAction.addActionItem(motoAction);
        mQuickAction.addActionItem(mofaAction);
        mQuickAction.addActionItem(fehlerAction);
        mQuickAction.addActionItem(resetAction);
       
       

      
       
        //setup the action item click listener
        mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {                
                @Override
                public void onItemClick(int pos) {
                      // System.out.println("--->onItemClick");
                	
                	if (pos == 0) {
                    	
                    	Intent intentChat = Util.getIntentChat(getApplicationContext(), "","");
               		
                    	
                 		    startActivity(intentChat);
                 		
                	}  else if  (pos == 1) {
                           	
                           	Intent intentZeichen = new Intent(getApplicationContext(), ZeichenActivity.class);;
                      		
                           	
                        		    startActivity(intentZeichen);               		    
                 		    
               	 }  else if  (pos == 2) { //Add item selected
                		Intent i = new Intent("android.intent.action.MAIN");
                		ComponentName n = new
                		ComponentName("falcofinder.android.fuehrerscheinmoto",
                		"falcofinder.android.fuehrerscheinmoto.FuehrerscheinMainActivity");
                		i.setComponent(n); 
                		

                		try {
                		    startActivity(i);
                		} 
                		catch (ActivityNotFoundException e) {
                		    Toast.makeText(getApplicationContext(), 
                		    		getString(R.string.ausdemmarket), 
                		        Toast.LENGTH_LONG).show();
                			Intent intent = new Intent(Intent.ACTION_VIEW
                   		         ,Uri.parse("market://details?id=falcofinder.android.fuehrerscheinmoto"));
                   		startActivity(intent);
                   		
                		}
                	 } else if (pos == 3) {
                		 Intent i = new Intent("android.intent.action.MAIN");
                 		ComponentName n = new
                 		ComponentName("falcofinder.android.fuehrerscheinmofa",
                 		"falcofinder.android.fuehrerscheinmofa.FuehrerscheinMainActivity");
                 		i.setComponent(n); 
                 		

                 		try {
                 		    startActivity(i);
                 		} 
                 		catch (ActivityNotFoundException e) {
                 		    Toast.makeText(getApplicationContext(), 
                 		    		getString(R.string.ausdemmarket), 
                 		        Toast.LENGTH_LONG).show();
                 			Intent intent = new Intent(Intent.ACTION_VIEW
                    		         ,Uri.parse("market://details?id=falcofinder.android.fuehrerscheinmofa"));
                    		startActivity(intent);
                    		
                 		}
                	 } else if (pos == 4) { //Add item selected
                        	dialogdomandeerrate();
                        } else if (pos == 5) { //Accept item selected
                        	
                        	reset();
                      
                        } 
                }
        });
        
        btn7.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
            	  mQuickAction.show(view);
                  mQuickAction.setAnimStyle(QuickAction.ANIM_GROW_FROM_CENTER);
            }
        });
     
        
        btn8.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View view) {
                // Launching News Feed Screen
            	 finish();
            	 System.exit(0);
            	 FuehrerscheinMainActivity.this.moveTaskToBack(true);
            }
        });
        
        mnubtn1.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View view) {
            	String version="";
            		
					version += "\nFalcoFinder " + getString(R.string.app_name)+ " v." + Util.getVersion(getPackageManager());
				            	
            	  openDialogHelp( getString(R.string.helptext) +version, null);
            }
        });
 
 
      
      

        
        adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
        
        
        LinearLayout layout = (LinearLayout) findViewById(R.id.footer);

       // Add the adView to it
         layout.addView(adView);


         AdRequest request = new AdRequest();

         request.addKeyword("Test für die theoretische Führerschein Prüfung Auto Quiz trainer Quiz Deutschland Germany");
         
       //request.setGender(AdRequest.Gender.UNKNOWN);
     //  request.setLocation(location);
     //  request.setBirthday("19850101");

       
          request.addTestDevice(AdRequest.TEST_EMULATOR);
         request.addTestDevice("HT08YPL02569");    // My T-Mobile G1 test phone
         request.addTestDevice("95CF54CD666F6634114A210B4B6B671B");
       // Initiate a generic request to load it with an ad
         adView.loadAd(request);
    }
    
    private int getNumeroquiz() {
		// TODO Auto-generated method stub
    	 DataBaseHelper mDbHelper = new DataBaseHelper(this);
 		try {
 			 
 			 mDbHelper.createDataBase();
 	 
 	 	} catch (IOException ioe) {
 	 		Toast.makeText(getApplicationContext(), 
 	 				"Cannot write \"Fragen database\" on the phone:( Please write to the developer.", 
     		        Toast.LENGTH_LONG).show();
 	 
 	 	}
 		mDbHelper.close();
 		
 		mDbHelper = new DataBaseHelper(this);
		
		
		 SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
	 	
		
		String sql = "";
		sql += "select count(*)  from pruf where nfrage=? ";
		
   	 Cursor cur = mDb.rawQuery(sql  , new String[] {"1"}) ;
     
   	int ret=0;
        
        cur.moveToFirst();
        
  
        while (cur.isAfterLast() == false) {        	 
       	
        	ret=cur.getInt(0);
       	 	 
       	 
       	    cur.moveToNext();
        }
        
        
        cur.close();
        
       
        mDb.close();
       
 		
 				
		return ret;
	}

	private void  dialogdomandeerrate() {
		// TODO Auto-generated method stub
    	DataBaseHelper mDbHelper = new DataBaseHelper(this);
		
		
		 SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
	 	
		
		String sql = "";
		sql += "select a.qid , b.domanda , a.answer2, a.answer3, a.answer4, a.correct2, a.correct3, a.correct4, b.weight , b.header, b.image, b.Risposte_campo2,b.Risposte_campo3,b.Risposte_campo4 ";
		sql += " from " + DataBaseHelper.TABLE_ERRORS + " a inner join " + DataBaseHelper.TABLE_FRAGEN + " b ";
		sql +="  on a.qid=b.qid ";
		sql +="  where (correct2=0) or (correct3=0) or (correct4=0)  ";
		
    	 Cursor cur = mDb.rawQuery(sql  , null) ;
      
    	//System.out.println("--->sql " + sql);
         
         cur.moveToFirst();
         
         ArrayList<Domanda> domandeerrate;

      	
 domandeerrate = new ArrayList<Domanda>();
         
         while (cur.isAfterLast() == false) {        	 
        	 
        	 
        	 	Domanda domanda = new Domanda();
        	 	domanda.iddom=""+cur.getString(0);
        	 	 domanda.domanda=""+cur.getString(1);
        	 	
        	 	//System.out.println("--->domanda " + domanda.domanda);
        	 	
       	 	
        	 domanda.antwort1=""+cur.getInt(2);
        	 domanda.antwort2=""+cur.getInt(3);
        	 domanda.antwort3=""+cur.getInt(4);
        	 domanda.correct1=""+cur.getInt(5);
        	 domanda.correct2=""+cur.getInt(6);
        	 domanda.correct3=""+cur.getInt(7);
        	 domanda.fehlerpunkt=""+cur.getInt(8);
        	 domanda.header=""+cur.getString(9);
         	 String image= cur.getString(10);
        	 image = Util.replace(image, "p_1_", "p1");
        	 image = Util.replace(image, "p_2_", "p2");
        	 
        	 domanda.image=image;
        	 
        	 domanda.frage1=""+cur.getString(11);
        	 domanda.frage2=""+cur.getString(12);
        	 domanda.frage3=""+cur.getString(13);
        	 
        	
        	 
        	
        	 domandeerrate.add(domanda);
        	 
        	 //System.out.println("--->dialogdomandeerrate"+d.image);
        	 
        	    cur.moveToNext();
         }
         
         
         cur.close();
         
        
         mDb.close();
        
         this.openDialogHelp("FEHLER\n", domandeerrate);

	}
    
   
    
    private void openDialogHelp(String testo, ArrayList<Domanda> domande) {
		// TODO Auto-generated method stub
		 dialogHelp = new Dialog(this);
		
		
       
		dialogHelp.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialogHelp.setCancelable(true);
        //there are a lot of settings, for dialog, check them all out!
		dialogHelp.setContentView(R.layout.errori_layout);
            
        //set up text
        TextView text = (TextView) dialogHelp.findViewById(R.id.TextView01);
        
        Spannable WordtoSpan = new SpannableString(testo);
        text.setTextColor(Color.BLACK);
        text.setText(testo);
        

        //set up button
        ImageButton button = (ImageButton) dialogHelp.findViewById(R.id.Button01);
        button.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
        	dialogHelp.hide();
            }
        });
        //now that the dialog is set up, it's time to show it
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogHelp.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
//System.out.println("--->"+lp.height);
        if (domande!=null){ 
 ListView lv = (ListView)dialogHelp.findViewById(R.id.listView1);
        
       // System.out.println("--->ListView"+lv);
        lv.setAdapter(new DomandeAdapter(this, android.R.layout.simple_list_item_1, domande));
      
        
        }
        
        dialogHelp.show();
	}
    
    public final void onClick(View v) {
	 	
    	String selview = "";
    	if (v.getTag()!=null) selview = ""+v.getTag();
    	
    //	System.out.println("--->selview"+selview);
		    	if (selview.indexOf("qid")==0) {
		    		
		    	    
		    		Intent intentChat = Util.getIntentChat(getApplicationContext(), ""+selview.substring(4, selview.indexOf("$")-1),""+selview.substring(selview.indexOf("$")+1));
	           		
	         		    startActivity(intentChat);
		    		
		    		return;
		    	}
    	}
    
    private void reset() {
		// TODO Auto-generated method stub
		DataBaseHelper mDbHelper = new DataBaseHelper(this);
		
		
		SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
		 int righe = mDb.delete(DataBaseHelper.TABLE_ERRORS, null ,null);
		 

	   mDb.close();
	}
    
    @Override
    protected void onPause() {
        super.onPause();
       
        if (dialogHelp!=null) dialogHelp.dismiss();
       
    }
    @Override
    public boolean onKeyDown(int keycode, KeyEvent event ) {
    	
    //	System.out.println("--->onKeyDown"+keycode);
    	if(keycode == KeyEvent.KEYCODE_BACK){
    		  if (dialogHelp!=null) dialogHelp.dismiss();
    		
    	}
		return super.onKeyDown(keycode,event); 
    	
 }
    
    
    public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}


	
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		//System.out.println("--->onFailedToReceiveAd"+arg0+arg1);
	}


	
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}


	
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}


	
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
		//System.out.println("--->onReceiveAd"+arg0);
		
	}
}