package falcofinder.android.fuehrerschein;	
	
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.InputStreamReader;
	import java.io.UnsupportedEncodingException;
	import java.text.SimpleDateFormat;
	import java.util.ArrayList;
	import java.util.Calendar;
	import java.util.Iterator;
	import java.util.Random;
	import java.util.StringTokenizer;

	import com.google.ads.Ad;
	import com.google.ads.AdListener;
	import com.google.ads.AdRequest;
	import com.google.ads.AdSize;
	import com.google.ads.AdView;
	import com.google.ads.AdRequest.ErrorCode;



	import falcofinder.android.fuehrerschein.db.DataBaseHelper;


	import android.app.Activity;
	import android.app.AlertDialog;
	import android.app.Dialog;
import android.content.ActivityNotFoundException;
	import android.content.ContentValues;
	import android.content.Intent;
	import android.content.SharedPreferences;
	import android.content.res.Resources;
	import android.database.Cursor;
	import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
	import android.os.Bundle;
	import android.text.Spannable;
	import android.text.SpannableString;
import android.view.KeyEvent;
	import android.view.Menu;
	import android.view.MenuItem;
import android.view.SubMenu;
	import android.view.View;
	import android.view.Window;
	import android.view.WindowManager;
	import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
	import android.view.animation.Animation;
	import android.view.animation.ScaleAnimation;
	import android.widget.AdapterView;
	import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
	import android.widget.ImageView;
	import android.widget.LinearLayout;
	import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
	import android.widget.TextView;
	import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


	public class FuehrerscheinActivity extends Activity implements AdListener  {
		
	    /** Called when the activity is first created. */
		  private static String MY_AD_UNIT_ID; 
			private AdView adView;
			
		private long _id;
		private int id_quiz;

		int sommapunti = 0;
		
		 ArrayList<Domanda> domandeerrate;
	

	    private static final int MENU_ERRORI = 6;
	    private static final int MENU_RESET = 7;
	    
	   
	    private Domanda d; // domanda selezionata
	    
	   
	    
	    private String modedefault="1";
		private int posizione=0;
		private int quid = 1;
		private int oldquid = 1; // serve per caancelllare i vecchi quiz. dopo che si e' iniziato gia' il quiz successivo
		private int numerodomande=0;
		private int numeroquiz=0;
	    
		private String qid=""; // passed by forum
	    
		 Dialog dialog;
		 

	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.fuehrerschein_layout);
	        MY_AD_UNIT_ID = getString(R.string.admobunitid);
	     
	        CheckBox b1 = (CheckBox)findViewById(R.id.button1);
	        CheckBox b2 = (CheckBox)findViewById(R.id.button2);
	        CheckBox b3 = (CheckBox)findViewById(R.id.button3);
	      
	        
	        b1.setTag("b1");
			 b2.setTag("b2");
			 b3.setTag("b3");
		
			 
			 
		          Bundle bundle = this.getIntent().getExtras();
			        modedefault=bundle.getString("modedefault");
			        qid=bundle.getString("qid");

			        	this.getBookmark();
			        
			
				 ProgressBar pbar = (ProgressBar)findViewById(R.id.pbar);
				        	if (modedefault.equals("0")) {
				        		pbar.setVisibility(ProgressBar.VISIBLE);
				        		numeroquiz = Integer.parseInt(bundle.getString("numeroquiz"));
				        	} else {
				        		pbar.setVisibility(ProgressBar.INVISIBLE);			        		
				        	}

				        	
				        caricaDomanda();
				        
				        
				        Button buttongo = (Button)findViewById(R.id.buttongo);
						 buttongo.setTag("buttongo1");
						 buttongo.setText(R.string.btngo1);
						 if (modedefault.equals("10")) {
						        LinearLayout linearLayoutButton = (LinearLayout)findViewById(R.id.linearLayoutButton);								
							 
							 
						        linearLayoutButton.setVisibility(LinearLayout.INVISIBLE);

							} 

						 
					        adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
					        
					        
					         LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutScroll);

					        // Add the adView to it
					          layout.addView(adView);


					          AdRequest request = new AdRequest();

					          request.addKeyword("Test für die theoretische Führerschein Prüfung Quiz trainer Quiz Deutschland Germany");
					          
					        //request.setGender(AdRequest.Gender.UNKNOWN);
					      //  request.setLocation(location);
					      //  request.setBirthday("19850101");

					        
					           request.addTestDevice(AdRequest.TEST_EMULATOR);
					          request.addTestDevice("HT08YPL02569");    // My T-Mobile G1 test phone
					          request.addTestDevice("95CF54CD666F6634114A210B4B6B671B");
					        // Initiate a generic request to load it with an ad
					          adView.loadAd(request);
			 
	    }
	    
	    
	 

		@Override
	    public void onResume() {
	        super.onResume();
	       
				 
	    }
	    
	    /**
	     * Invoked during init to give the Activity a chance to set up its Menu.
	     *
	     * @param menu the Menu to which entries may be added
	     * @return true
	     */
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        super.onCreateOptionsMenu(menu);

	        //System.out.println("--->onCreateOptionsMenu");

	    
	        menu.add(0, MENU_ERRORI, 5, R.string.menu_errori);
	        if (this.modedefault.equals("1")) {
	        	 menu.add(0, MENU_RESET, 2, R.string.menu_reset1);
	        } else if (this.modedefault.equals("2")) {
	        	 menu.add(0, MENU_RESET, 2, R.string.menu_reset2);
	        } else if (this.modedefault.equals("3")) {
	        	 menu.add(0, MENU_RESET, 2, R.string.menu_reset3);
	        } else if (this.modedefault.equals("4")) {
	        	 menu.add(0, MENU_RESET, 2, R.string.menu_reset4);
	        } else if (this.modedefault.equals("5")) {
	        	 menu.add(0, MENU_RESET, 2, R.string.menu_reset5);
	        	
	        } else if (this.modedefault.equals("0")) {
	        	// menu.add(0, MENU_RESET, 2, R.string.menu_reset6);
		        	
	        }
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
	               
	        
	        case MENU_ERRORI:
	            
	        	this.arrdomandeerrate();
	        	
	        	 if (modedefault.equals("0")) {
		        	 this.openDialogHelp("FEHLER\n"+sommapunti+" Punkte\n", domandeerrate);
		         }else if (modedefault.equals("1")) {
		        	 this.openDialogHelp("FEHLER\n", domandeerrate);
		         } else {
		        	 this.openDialogHelp("FEHLER "+modedefault+" Punkte\n", domandeerrate);	 
		         }
	        	 
	        	return true;	
	        	
		    case MENU_RESET:
	            
	        	reset();
	        	
	        	return true;
	            
	        }
	        

	        return true;
	    }

	    private void  arrdomandeerrate() {
			// TODO Auto-generated method stub
	    	DataBaseHelper mDbHelper = new DataBaseHelper(this);
	    	
			SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
			
	    	String sql ="";
	    	if (modedefault.equals("0")) {
//				sql=	"select iddom , id_quiz , antwort1, antwort2, antwort3, correct1, correct2, correct3, points from " + DataBaseHelper.TABLE_ERRORS + " where (correct1=0) or (correct2=0) or (correct3=0) ";
				

				sql += "select a.qid , b.domanda , a.answer2, a.answer3, a.answer4, a.correct2, a.correct3, a.correct4, b.weight , b.header, b.image, b.Risposte_campo2,b.Risposte_campo3,b.Risposte_campo4 , c.desc desckat";
				sql += " from " + DataBaseHelper.TABLE_ERRORS + " a inner join " + DataBaseHelper.TABLE_FRAGEN + " b ";
				sql +="  on a.qid=b.qid inner join " + DataBaseHelper.TABLE_KAT + " c on b.idkat=c.idkat ";
				sql +="  where a.quid=" + this.quid+ "  and ((correct2=0) or (correct3=0) or (correct4=0) ) ";
				
			} 	else if (modedefault.equals("1")) {
//				sql=	"select iddom , id_quiz , antwort1, antwort2, antwort3, correct1, correct2, correct3, points from " + DataBaseHelper.TABLE_ERRORS + " where (correct1=0) or (correct2=0) or (correct3=0) ";
				

				sql += "select a.qid , b.domanda , a.answer2, a.answer3, a.answer4, a.correct2, a.correct3, a.correct4, b.weight , b.header, b.image, b.Risposte_campo2,b.Risposte_campo3,b.Risposte_campo4 , c.desc desckat";
				sql += " from " + DataBaseHelper.TABLE_ERRORS + " a inner join " + DataBaseHelper.TABLE_FRAGEN + " b ";
				sql +="  on a.qid=b.qid inner join " + DataBaseHelper.TABLE_KAT + " c on b.idkat=c.idkat ";
				sql +="  where (correct2=0) or (correct3=0) or (correct4=0)  ";
				
			} else {
				
				
				sql += "select a.qid , b.domanda , a.answer2, a.answer3, a.answer4, a.correct2, a.correct3, a.correct4, b.weight , b.header, b.image, b.Risposte_campo2,b.Risposte_campo3,b.Risposte_campo4 , c.desc desckat";
				sql += " from " + DataBaseHelper.TABLE_ERRORS + " a inner join " + DataBaseHelper.TABLE_FRAGEN + " b ";
				sql +="  on a.qid=b.qid inner join " + DataBaseHelper.TABLE_KAT + " c on b.idkat=c.idkat ";
			//	sql +="  where b.weight = " + modedefault + " and ((correct2=0) or (correct3=0) or (correct4=0))  ";
				sql +="  where b.weight = '" + modedefault + "' and ((correct2=0) or (correct3=0) or (correct4=0))  ";
			}
			Cursor cur = mDb.rawQuery(sql  , null) ;
	      
	    	 //System.out.println("--->sql " + sql);
	         
	         cur.moveToFirst();
	        
	         
	        
	 domandeerrate = new ArrayList<Domanda>();
	          sommapunti=0;
	         
	         while (cur.isAfterLast() == false) {  
	 
	        	 	Domanda domanda  = new Domanda();
	        	 	
	        	 	domanda.iddom=""+cur.getString(0);
	        	 	 domanda.domanda=""+cur.getString(1);
	        	 	 
	        		 domanda.antwort1=""+cur.getInt(2);
	            	 domanda.antwort2=""+cur.getInt(3);
	            	 domanda.antwort3=""+cur.getInt(4);
	            	 domanda.correct1=""+cur.getInt(5);
	            	 domanda.correct2=""+cur.getInt(6);
	            	 domanda.correct3=""+cur.getInt(7);
	            	 int points = cur.getInt(8);
	            	 domanda.fehlerpunkt=""+ points;
	            	 sommapunti+=points;
	            	 domanda.header=""+cur.getString(9);
	            	 String image= ""+cur.getString(10);
		        	 image = Util.replace(image, "p_1_", "p1");
		        	 image = Util.replace(image, "p_2_", "p2");
		        	 
	            	 domanda.image=image;
	            	 
	            	 domanda.frage1=""+cur.getString(11);
	            	 domanda.frage2=""+cur.getString(12);
	            	 domanda.frage3=""+cur.getString(13);
	            	 domanda.desckat=""+cur.getString(14);
	            	 
	            	// System.out.println("---> domanda.desckat " + domanda.desckat + domanda.desckat.indexOf("-"));
//	            	 System.out.println("---> domanda.desckat.equals " + domanda.desckat.equals(""));
	            	 if (!domanda.desckat.equals("") && domanda.desckat.indexOf("-")>0) {
	//            		 System.out.println("--->DENTRO" + domanda.desckat.substring(d.desckat.indexOf("-")));
	            		 domanda.desckat = domanda.desckat.substring(domanda.desckat.indexOf("-")+1);
		        	 	}
	            //	 System.out.println("---> domanda.desckat " + domanda.desckat + domanda.desckat.indexOf("-"));
	        	
	        	 domandeerrate.add(domanda);
	        
	        	    cur.moveToNext();
	         }
	         
	         
	         cur.close();
	         
	        
	         mDb.close();
	        
	       //  System.out.println("--->modedefault"+modedefault);
	        
	         

		}
	    
	    private void openDialogHelp(String testo, ArrayList<Domanda> domande) {
		// TODO Auto-generated method stub
	    if(dialog != null)  dialog.dismiss();
	    	
		 dialog = new Dialog(this);
		
		
       
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);
        //there are a lot of settings, for dialog, check them all out!
        dialog.setContentView(R.layout.errori_layout);
            
        //set up text
        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        Spannable WordtoSpan = new SpannableString(testo);
        text.setText(testo);
        

        //set up button
        ImageButton button = (ImageButton) dialog.findViewById(R.id.Button01);
        button.setOnClickListener(new OnClickListener() {
        @Override
            public void onClick(View v) {
               dialog.hide();
            }
        });
        //now that the dialog is set up, it's time to show it
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
//System.out.println("--->"+lp.height);
        if (domande!=null){ 
 ListView lv = (ListView)dialog.findViewById(R.id.listView1);
        
       // System.out.println("--->ListView"+lv);
        lv.setAdapter(new DomandeAdapter(this, android.R.layout.simple_list_item_1, domande));
      
        
        }
        
        dialog.show();
	}
	    



		private void caricaDomanda() {
			// TODO Auto-generated method stub
			 TextView tv = (TextView) findViewById(R.id.textView1);
			 ImageView iv1 = (ImageView)findViewById(R.id.imageView1);
			 Button btngotoforum = (Button)findViewById(R.id.gotoforum);
			 TextView tvkat = (TextView) findViewById(R.id.kat);
			 
			  d = this.getDomanda();
			  
			  btngotoforum.setText("+forum:" + d.iddom);

			  
		//	 System.out.println("--->arrQuiz[2]aa"+arrQuiz[2]);
			 String testo = ""+d.domanda+" ";

			 String punktkat = " (" + d.fehlerpunkt + " Punkte)";

			  if (!d.desckat.trim().equals("")) punktkat +=" Kat." + d.desckat;
			  tvkat.setText(punktkat);
			 if (!d.header.trim().equals("")) testo += "\n" + d.header;

			 
			 
			 if (this.modedefault.equals("0")) {
				testo = d.nfrage + "/30)"+ testo;
				
				 TextProgressBar pbar = (TextProgressBar)findViewById(R.id.pbar);
				 pbar.setText(d.nfrage + "/30");
				 pbar.setProgress(d.nfrage);
			 }
			 
			 Spannable WordtoSpan = new SpannableString(testo);        
			  
				 tv.setText(WordtoSpan);
			//System.out.println("--->caricaDomanda"+d.image.substring(0, d.image.indexOf(".")).trim().replace(" ", "$")+"-"+Util.getPackageName());
				// System.out.println("--->testo"+testo);
			 if (!d.image.trim().equals("")) { 
					 
				 int resId = getResources().getIdentifier(d.image.trim(), "drawable", Util.getPackageName());
				 iv1.setImageResource(resId);
				 
				// System.out.println("--->resId"+resId);

			        
				 Animation animation = new AlphaAnimation(0.0f, 1.0f);
				  animation.setDuration(500);
			        animation.setRepeatCount(0);
			 
			        iv1.startAnimation(animation);
			        iv1.refreshDrawableState();
				

			 } else {
				 int resId = getResources().getIdentifier("vuoto", "drawable", Util.getPackageName());
				 iv1.setImageResource(resId);
				
			 }
			 CheckBox b1 = (CheckBox)findViewById(R.id.button1);
	    	 CheckBox b2 = (CheckBox)findViewById(R.id.button2);
	    	 CheckBox b3 = (CheckBox)findViewById(R.id.button3);
			// System.out.println("--->d.frage1"+d.frage1);
			 if (!d.frage1.trim().equals("")) { 
				
				 b1.setVisibility(CheckBox.VISIBLE);
				 b1.setChecked(false);
				// System.out.println("--->"+d.frage1);
				 b1.setText(new SpannableString(" " + d.frage1));
				 b1.setTextColor(Color.parseColor("#000000"));
			
			 
			 }
			 
			 if (!d.frage2.trim().equals("")) { 
				
				 b2.setVisibility(CheckBox.VISIBLE);
				 b2.setChecked(false);
				 b2.setText(new SpannableString(" " + d.frage2));
				 b2.setTextColor(Color.parseColor("#000000"));
			 	
			
			 	 
			 }
			 
		//	 System.out.println("--->vis d.frage3"+d.frage3);
			 if (!d.frage3.trim().equals("")) { 
				
				 b3.setVisibility(CheckBox.VISIBLE);
				 b3.setChecked(false);
			 	
				 b3.setText(new SpannableString(" " + d.frage3));
				 b3.setTextColor(Color.parseColor("#000000"));
			 } else {
				 
				 b3.setVisibility(CheckBox.INVISIBLE);
				// System.out.println("--->cxarica domanda frage3 inv");
			 }
		

				 if (modedefault.equals("10")) {
					
					 if (d.antwort1.trim().equals("1")) b1.setChecked(true);
					 if (d.antwort2.trim().equals("1")) b2.setChecked(true);
					 if (d.antwort3.trim().equals("1")) b3.setChecked(true);
							 
					 
				 }
			 RelativeLayout t = (RelativeLayout)findViewById(R.id.relativeLayoutFrage);
				t.setVisibility(RelativeLayout.VISIBLE);
		
		}


	

		
		
		
		
	 
	    
	    /***
	     * ant risposta (checked, unchecked)
	     * res = 0 sbagliato, 1 corretto
	     */
	    private void inserisciRisultato(String ant1,String ant2, String ant3, String res1,String res2, String res3, String iddom, String weight) {
	    	
	    	DataBaseHelper mDbHelper = new DataBaseHelper(this);
	    	
	    	SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
			
			 ContentValues initialValues = new ContentValues();
			 

			  initialValues.put("qid", ""+iddom);
			  initialValues.put("answer2", ""+ant1);
			  initialValues.put("answer3", ""+ant2);
			  initialValues.put("answer4", ""+ant3);
			  initialValues.put("correct2", ""+res1);
			  initialValues.put("correct3", ""+res2);
			  initialValues.put("correct4", ""+res3);
			  initialValues.put("mode", ""+this.modedefault);
			  initialValues.put("quid", ""+this.quid);
			  initialValues.put("weight", weight);

			 // System.out.println("--->initialValues"+initialValues);
			//  System.out.println("--->mode"+modedefault);
			  
		   long result = mDb.insert(DataBaseHelper.TABLE_ERRORS, null, initialValues);
		   
		 //  System.out.println("--->result"+result);
		   mDb.close();
		 	  
	   	
	   }
	    

	    /***
	     * clean quiz from previous attempts
	     * 
	     */
	    private void cleanErrorsQuiz() {
	    	
	    	DataBaseHelper mDbHelper = new DataBaseHelper(this);
			SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
			
			String where = "quid = ?";
			String[] whereArgs = {""+this.quid};
		
			
			 int righe = mDb.delete(DataBaseHelper.TABLE_ERRORS, where ,whereArgs);
			 

		   mDb.close();
		 	  
	   	
	   }
	    
	    private void aggiornaBookmark() {
	    	
	    	DataBaseHelper mDbHelper = new DataBaseHelper(this);
	    	
	    	
	    	SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
			
			
			 
			 ContentValues initialValues = new ContentValues();
			
			  initialValues.put("quid", this.quid);
			  initialValues.put("bookmark", posizione);

			  String where = "quizmode=?";
			  String[] whereArgs = {this.modedefault};

		   long result = mDb.update(DataBaseHelper.TABLE_BOOKMARKS, initialValues, where, whereArgs);
		//   System.out.println("--->aggiornaBookmark quid"+this.quid+" - bookmark " + posizione + " - quizmode"+modedefault);
		//   System.out.println("--->aggiornaBookmark result " + result);
		   mDb.close();
		 	  
	   	
	   }
	    
	    private void nextquestion() {
			// TODO Auto-generated method stub

			 posizione++; // = "" + (Integer.parseInt(posizione)+1);
			 
			// System.out.println("--->posizione"+posizione);
			 
					 if (posizione==numerodomande) {
						
						 posizione=0;
						 quid++;
						 if (quid>numeroquiz) quid=1; 
					 }


					 aggiornaBookmark();
				
					 
					 if (this.modedefault.equals("0") && posizione==0) cleanErrorsQuiz();
			 			 		   
		 	  
		}
	    
	   


		public final void onClick(View v) {
	 	
	    	String selview = "";
	    	if (v.getTag()!=null) selview = ""+v.getTag();
	    	
	    //	System.out.println("--->selview"+selview);
	    	if (selview.equals("back")) {
	    		
	    		if(dialog != null) dialog.dismiss();
	    		
	    		super.finish();
	    		
	    		
         		
	    		
	    		return;
	    	}
	    	
	    	if (selview.indexOf("qid")==0) {
	    		
	    	    
	    		Intent intentChat = Util.getIntentChat(getApplicationContext(), ""+selview.substring(4, selview.indexOf("$")-1),""+selview.substring(selview.indexOf("$")+1));
           		
         		    startActivity(intentChat);
	    		
	    		return;
	    	}
	    	
	    	if (selview.equals("gotoforum")) {
	    		
	    		if(dialog != null) dialog.dismiss();
	    		
	    
	    		Intent intentChat = Util.getIntentChat(getApplicationContext(), ""+d.iddom,""+d.domanda);
           		
         		    startActivity(intentChat);
	    		
	    		return;
	    	}

	    	
	    	
	    //	System.out.println("--->selview"+selview);
	    	
	    	if (selview.equals("btnfehler")) {
	    		
	    	//	System.out.println("--->modedefault"+modedefault);
	    	//	System.out.println("--->posizione"+posizione);
	    		
	    		if (modedefault.equals("0") && posizione==0) {
	    			// NIE 4EVO
	    		} else {
	    			arrdomandeerrate();	
	    		}
	    		
	    	
	    		if (modedefault.equals("0")) {
		        	 this.openDialogHelp("FEHLER\n"+sommapunti+" Punkte\n", domandeerrate);
		         }else if (modedefault.equals("1")) {
		        	 this.openDialogHelp("FEHLER\n", domandeerrate);
		         } else {
		        	 this.openDialogHelp("FEHLER "+modedefault+" Punkte\n", domandeerrate);	 
		         }
	    	return;
	    	}
	    	if (selview.equals("buttongo")){
	    		caricaDomanda();
	    		
	    		 Button buttongo = (Button)findViewById(R.id.buttongo);
				 buttongo.setTag("buttongo1");
				 buttongo.setText(R.string.btngo1);
	    	}
	    	
	    
	    	
	    	CheckBox b1 = (CheckBox)findViewById(R.id.button1);
	    	 CheckBox b2 = (CheckBox)findViewById(R.id.button2);
	    	 CheckBox b3 = (CheckBox)findViewById(R.id.button3);
	    	 
	    	
	    	 
	 	    if (selview.equals("buttongo1") ){
	 	    	
	 	    	String risultato1="";
	 	    	
	 	    	if (!d.frage1.trim().equals("")) {
	 	    	
	 	    	//	System.out.println("--->d.antwort1:"+d.antwort1+ "-b1.isChecked():"+b1.isChecked());
	 	    		
		 	    	if (d.antwort1.trim().equals("1") && b1.isChecked() || 
		 	    			d.antwort1.trim().equals("0") && !b1.isChecked()	
		 	    			) {
		 	    		risultato1="1";
		 	    	}
		 	    	else {
		 	    		
		 	    		b1.setText(b1.getText()+ " " + getString(R.string.msgerror));
		 	    		b1.setTextColor(Color.parseColor("#FF0000"));
		 	    	//	 TextView  tv1add = (TextView)findViewById(R.id.frage1add);

		 	    		
		 	    	//	tv1add.setText(R.string.msgerror);
		 	    		risultato1="0";		
		 	    	}
	 	    	}
	 	        
	 	    	String risultato2="";
	 	    	
	 	    	if (!d.frage2.trim().equals("")) {
	 	    		
	 	    		//System.out.println("--->d.antwort2:"+d.antwort2+ "-b2.isChecked():"+b2.isChecked());
	 	    		
	 	    		if (d.antwort2.trim().equals("1") && b2.isChecked() || 
		 	    			d.antwort2.trim().equals("0") && !b2.isChecked()	
		 	    			) {
		 	    		risultato2="1";
		 	    	}
		 	    	else {
		 	    		
		 	    		b2.setText(b2.getText()+ " " + getString(R.string.msgerror));
		 	    		b2.setTextColor(Color.parseColor("#FF0000"));
		 	    		
		 	    		
		 	    		risultato2="0";		
		 	    	}		
	 	    	}
	 	    	
	 	    	String risultato3="";
	 	    	
	 	    	if (!d.frage3.trim().equals("")) {
	 	    		
	 	    		//System.out.println("--->d.antwort3:"+d.antwort3+ "-b3.isChecked():"+b3.isChecked());
	 	    		
	 	    		if (d.antwort3.trim().equals("1") && b3.isChecked() || 
		 	    			d.antwort3.trim().equals("0") && !b3.isChecked()	
		 	    			) {
		 	    		risultato3="1";
		 	    	}
		 	    	else {
		 	    		b3.setText(b3.getText()+ " " + getString(R.string.msgerror));
		 	    		b3.setTextColor(Color.parseColor("#FF0000"));
		 	    		
		 	    		risultato3="0";		
		 	    	}		
	 	    	}
	 	    	
	 	    	String intb1="";
	 	    	String intb2="";
	 	    	String intb3="";
	 	    	
	 	    	if (b1.isChecked()) intb1="1";
	 	    	if (b2.isChecked()) intb2="1";
	 	    	if (b3.isChecked()) intb3="1";
	 			
	 				 //right
	 				inserisciRisultato(intb1,intb2,intb3,risultato1,risultato2,risultato3,d.iddom, d.fehlerpunkt);
//	 			
	 				if (this.modedefault.equals("0")) {
	 					Button btnfehler = (Button) findViewById(R.id.btnfehler);
	 					if (d.nfrage==30) {
	 						boolean passed = isPassed();
	 						
	 						
	 						if (passed) {
	 							btnfehler.setText(R.string.btnpassed);	
	 							btnfehler.setTextColor(Color.GREEN);	
	 						} else {
	 							btnfehler.setText(R.string.btnnotpassed);	
	 							btnfehler.setTextColor(Color.RED);	 							
	 						}
	 						Button buttongo = (Button)findViewById(R.id.buttongo);
		 					 buttongo.setText(R.string.btngo);
		 					 buttongo.setTag("buttongo");
		 					 
	 					} else {
	 						 

	 				 		 Button buttongo = (Button)findViewById(R.id.buttongo);
	 							 buttongo.setTag("buttongo2");
	 							 buttongo.setText(R.string.btngo2);
	 							 
	 							
	 					}
	 					
	 					nextquestion();
	 					
	 				} else {
	 				
	 				 Button buttongo = (Button)findViewById(R.id.buttongo);
	 				 buttongo.setTag("buttongo2");
	 				 buttongo.setText(R.string.btngo2);
	 				 
	 		 		   nextquestion();
	 		 		   
	 				}
	 	    }
	 	    
	 	    
	 	   if (selview.equals("buttongo2") ){
	 		  caricaDomanda();

	 		 Button buttongo = (Button)findViewById(R.id.buttongo);
				 buttongo.setTag("buttongo1");
				 buttongo.setText(R.string.btngo1);
				 
				 
	 	   }
	 	    	
	 	   
		 }
		
		 private boolean isPassed() {
			// TODO Auto-generated method stub
			 DataBaseHelper mDbHelper = new DataBaseHelper(this);
				
				SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
				String sql = " select a.weight from " + DataBaseHelper.TABLE_FRAGEN + " a inner join  " + DataBaseHelper.TABLE_ERRORS + " b on a.qid=b.qid where b.quid=? and ((correct2=0) or (correct3=0) or (correct4=0)) ";
				
				Cursor cur = mDb.rawQuery(sql, new String[] { ""+this.quid }) ;
	 
		    	//System.out.println("--->getBookmark " + "select  quid,  bookmark from " + DataBaseHelper.TABLE_BOOKMARKS + " where quizmode=  " + this.modedefault );
		         
		         cur.moveToFirst();
		         ArrayList questions = new ArrayList();
		         int sum = 0;
		         int punkte5 = 0;
		         while (cur.isAfterLast() == false) {        	 
		        	 int punkte = cur.getInt(0);
		        	 sum += punkte;
		        	
		        	 if (punkte==5) {
		        		 punkte5+=punkte;
		        	 }
		        	 
		        	   cur.moveToNext();
		         }		
		         cur.close();
		         
		         mDb.close();
		         
		         this.arrdomandeerrate();
		         
			 if (sum>10 || punkte5>5) return false;
			 else return true;
		}




		private void getBookmark() {
				// TODO Auto-generated method stub
					DataBaseHelper mDbHelper = new DataBaseHelper(this);
					
					SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
			    	 Cursor cur = mDb.rawQuery("select  quid,  bookmark from " + DataBaseHelper.TABLE_BOOKMARKS + " where quizmode= ? ", new String[] { this.modedefault }) ;
		 
			    	//System.out.println("--->getBookmark " + "select  quid,  bookmark from " + DataBaseHelper.TABLE_BOOKMARKS + " where quizmode=  " + this.modedefault );
			         
			         cur.moveToFirst();
			         ArrayList questions = new ArrayList();
			         while (cur.isAfterLast() == false) {        	 
			        	 
			        	 quid = cur.getInt(0);
			        	 posizione = cur.getInt(1);
			        	 
			        //	 System.out.println("---> getBookmark quid" + quid);
					//	 System.out.println("---> getBookmark posizione" + posizione);
						 
			        	   cur.moveToNext();
			         }
						
			         
			         cur.close();
				
			         
			         mDb.close();
				  
			  
			}
		
		 private Domanda getDomanda() {
				// TODO Auto-generated method stub
					DataBaseHelper mDbHelper = new DataBaseHelper(this);
					
					SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
					
					String filter="";
					
					if (this.modedefault.equals("0")) {
						filter = " where b.quid= '" + this.quid + "'";
					} else if (this.modedefault.equals("10") ) {
						filter = " where a.qid= '" + this.qid + "'";
						
					} else if (!this.modedefault.equals("1") ) {
					filter = " where weight= '" + this.modedefault + "'";
					
											
					} 
					
					String sql = "select qid, domanda, header, image, Risposte_Campo2, Risposte_Campo3, Risposte_Campo4, Furanswer_Campo2, Furanswer_Campo3, Furanswer_Campo4, weight, b.desc desckat  from " + DataBaseHelper.TABLE_FRAGEN + " a inner join " + DataBaseHelper.TABLE_KAT + " b on a.idkat=b.idkat " + filter + " order by Risposte_campo2 desc";
					
					if (this.modedefault.equals("0")) {
					
						sql = "select a.qid, a.domanda, a.header, a.image, a.Risposte_Campo2, a.Risposte_Campo3, a.Risposte_Campo4, a.Furanswer_Campo2, a.Furanswer_Campo3, a.Furanswer_Campo4, a.weight , b.nfrage from " + DataBaseHelper.TABLE_FRAGEN + " a inner join pruf b on a.qid=b.qid " + filter + " order by nfrage ";
					}
					
					
					
			    	 Cursor cur = mDb.rawQuery(sql , null) ;
		 
		 
			    //	System.out.println("--->sql " + sql);
			    //	System.out.println("--->this.posizione " + this.posizione);
			         
			         cur.moveToFirst();
			         Domanda d = new Domanda();
			         
			         d.domanda = getString(R.string.keine);
			         
			         numerodomande=0;
			         while (cur.isAfterLast() == false) {        	 
			        	 if (numerodomande==this.posizione) {
			        		
			        	 d.iddom = cur.getString(0);
			        	 d.domanda = cur.getString(1);
			       // 	 System.out.println("--->d.domanda"+d.domanda);
			        	 d.header = ""+cur.getString(2);
			        	 String image= ""+cur.getString(3);
			        	 image = Util.replace(image, "p_1_", "p1");
			        	 image = Util.replace(image, "p_2_", "p2");
			        		 
			        	 d.image = image; 
			        	 d.frage1 = ""+cur.getString(4);
			        	 d.frage2 = ""+cur.getString(5);
			        	 d.frage3 = ""+cur.getString(6);
			        	 d.antwort1 = ""+cur.getString(7);
			        	 d.antwort2 = ""+cur.getString(8);
			        	 d.antwort3 = ""+cur.getString(9);
			        	 d.fehlerpunkt = ""+cur.getString(10);
			        	 
			        	
			        	 d.desckat= "";
			        			 
			        	 if (this.modedefault.equals("0")) d.nfrage = cur.getInt(11);
			        	 else d.desckat= ""+cur.getString(11);
			        	 
			        	 	if (!d.desckat.equals("") && d.desckat.indexOf("-")>0) {
			        	 		d.desckat = d.desckat.substring(d.desckat.indexOf("-")+1);
			        	 	}
			        	 
			        	 }
			        	 numerodomande++;
			        	   cur.moveToNext();
			         }
						 
			         
			         cur.close();
				
			         
			         mDb.close();
				  
			         
			         
						Button btnfehler = (Button) findViewById(R.id.btnfehler);
						btnfehler.setText(R.string.menu_errori);
						btnfehler.setTextColor(Color.BLACK);
						
						
			  return d;
			}
		
		   @Override
		    public void onActivityResult(int requestCode,int resultCode,Intent data)
		    {
		     super.onActivityResult(requestCode, resultCode, data);
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
			System.out.println("--->onReceiveAd"+arg0);
			
		}

		private void reset() {
			// TODO Auto-generated method stub
			DataBaseHelper mDbHelper = new DataBaseHelper(this);
			SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
			
			String where = "weight = ?";
			String[] whereArgs = {this.modedefault};
		
			 if (this.modedefault.equals("1")) {
				 where = null;
				 whereArgs = null;
						 
			 }
			 int righe = mDb.delete(DataBaseHelper.TABLE_ERRORS, where ,whereArgs);
			 

		   mDb.close();
		}
		
		 @Override
		    public void onPause() {
		    	super.onPause();

		    	if(dialog != null)   		dialog.dismiss();
		    }

		 @Override
		    public void onDestroy() {
		    	super.onDestroy();

		    	if(dialog != null)   		dialog.dismiss();
		    }
	}