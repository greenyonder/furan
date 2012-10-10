package falcofinder.android.fuehrerschein;

import java.io.IOException;
import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import falcofinder.android.fuehrerschein.db.DataBaseHelper;
import falcofinder.android.fuehrerschein.db.DataBaseHelperZeichen;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class ZeichenActivity extends Activity {

	 private static String MY_AD_UNIT_ID; 
		private AdView adView;
		
		private ArrayList alkat;
		
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.zeichen_layout);
	        MY_AD_UNIT_ID = getString(R.string.admobunitid);

						 
					        adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
					        
					        
					         LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayoutHeaderZeichen);

					        // Add the adView to it
					          layout.addView(adView);

					          alkat = getKat();
					          alkat.add(0,"Inhaltsverzeichnis");
					          //getZeichen();
					          Spinner spin = (Spinner) findViewById(R.id.spinnerZeichen);
					          
					          ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					        	        android.R.layout.simple_spinner_item, alkat);
					        	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					        	    spin.setAdapter(adapter);

					        	    spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					        	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
					        	           // Object item = parent.getItemAtPosition(pos);
					        	        	
					        	        		ArrayList alzeichen =  getZeichen(""+ (pos));	
					        	        	
					        	        		 if (alzeichen!=null){ 
					        	        			 ListView lv = (ListView) findViewById(R.id.listViewZeichen);
					        	        			 ZeichenAdapter za= new ZeichenAdapter(ZeichenActivity.this, android.R.layout.simple_list_item_1, alzeichen);
					        	        			 
				        	        			        lv.setAdapter(za);
   
					        	        		  }

					        	        		  

					        	        }
					        	        public void onNothingSelected(AdapterView<?> parent) {
					        	        }
					        	    });

					          
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
	    
	    
	   private ArrayList getKat() {
			// TODO Auto-generated method stub
				
						DataBaseHelperZeichen mDbHelper = new DataBaseHelperZeichen(this);
						
					 		try {
					 			 
					 			 mDbHelper.createDataBase();
					 	 
					 	 	} catch (IOException ioe) {
					 	 		Toast.makeText(getApplicationContext(), 
					 	 				"Cannot write \"Zeichen database\" on the phone:( Please write to the developer.", 
					     		        Toast.LENGTH_LONG).show();
					 	 
					 	 	}
					 		mDbHelper.close();
					 		
					 		mDbHelper = new DataBaseHelperZeichen(this);
					 		
						SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
				    	 Cursor cur = mDb.rawQuery("select  kat from " + DataBaseHelperZeichen.TABLE_KAT , null) ;
			 
				    	//System.out.println("--->getBookmark " + "select  quid,  bookmark from " + DataBaseHelper.TABLE_BOOKMARKS + " where quizmode=  " + this.modedefault );

				         cur.moveToFirst();
				         ArrayList kat = new ArrayList();
				         while (cur.isAfterLast() == false) {        	 
				        	
				        	 kat.add(""+cur.getString(0));
				        	

							 
				        	   cur.moveToNext();
				         }
							
				         
				         cur.close();
					
				         
				         mDb.close();
					  
				return kat;
			
		}

		private ArrayList getZeichen(String rowid) {
		// TODO Auto-generated method stub
			//System.out.println("-->rowid"+rowid);
					DataBaseHelperZeichen mDbHelper = new DataBaseHelperZeichen(this);
					
					SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
			    	 Cursor cur = mDb.rawQuery("select  img,  desc from " + DataBaseHelperZeichen.TABLE_ZEICHEN + " where rowidkat= ? ", new String[] { rowid }) ;
		 
			    	//System.out.println("--->getBookmark " + "select  quid,  bookmark from " + DataBaseHelper.TABLE_BOOKMARKS + " where quizmode=  " + this.modedefault );
			         
			         cur.moveToFirst();
			         ArrayList zeichen = new ArrayList();
			         while (cur.isAfterLast() == false) {        	 
			        	 String[] arrStr = new String[2];
			        	 arrStr[0] =""+cur.getString(0); 
			        	 arrStr[1] =""+cur.getString(1);
			        	 
			        	 zeichen.add(arrStr); //= cur.getString(arg0)(0);
			        	
			        	//System.out.println("--->arrStr"+arrStr.toString());
						 
			        	   cur.moveToNext();
			         }
						
			         
			         cur.close();
				
			         
			         mDb.close();
				  
			         
			return zeichen;
		
	}




		@Override
	    public void onResume() {
	        super.onResume();
	       
				 
	    }
		
	public final void onClick(View v) {
	 	
    	String selview = "";
    	if (v.getTag()!=null) selview = ""+v.getTag();
    	
    //	System.out.println("--->selview"+selview);
    	if (selview.equals("back")) {
    		
  
    		super.finish();
    			
    		return;
    	}
	}
}
