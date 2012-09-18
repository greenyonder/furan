package falcofinder.android.fuehrerschein;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import falcofinder.android.fuehrerschein.chat.AndroidRequestTransport;
import falcofinder.android.fuehrerschein.chat.ChatDetailsActivity;
import falcofinder.android.fuehrerschein.chat.ChatsActivity;
import falcofinder.android.fuehrerschein.chat.FuehrerscheinChatActivity;
import falcofinder.android.fuehrerschein.chat.Setup;
import falcofinder.android.fuehrerschein.db.DataBaseHelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;


import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.vm.RequestFactorySource;



public class Util {
	  /**
     * Key for shared preferences.
     */
    private static final String SHARED_PREFS = "fuehrerschein_PREFS";
	 /**
     * Helper method to get a SharedPreferences instance.
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFS, 0);
    }
    
    public static void setSharedPreferences(Context context, String key, String value) {
    	  
    	SharedPreferences prefs = Util.getSharedPreferences(context);
    	    prefs.edit().putString(key, value).commit();
    	    
    }
    
    
  
    /*
     * p = punti
     * alquiz al  from strin
     */
    protected static ArrayList<Domanda> getQuiz(String  p, ArrayList alquiz) {
		// TODO Auto-generated method stub

		//int resId = getResources().getIdentifier("quiz"+m, "raw", Util.getPackageName());

		
		
		ArrayList<Domanda> ald = new ArrayList();
		StringBuffer buf = new StringBuffer();			
	
			String str;
		
			
		//	System.out.println("--->alquiz.size()" + alquiz.size());
			
					Iterator<String> it = alquiz.iterator();

	
				while (it.hasNext()) {
					
					Domanda d = new Domanda();
					str = ""+it.next();
					//str = "714$Wer muss warten ?$$p13_01_101$Ich muss warten$Der Pkw, der aus dem Feldweg kommt$$0$1$";
							
					
					str = str.replace("$", "$ ");
					
					
					StringTokenizer st = new StringTokenizer(str,"$");
					d.iddom=(""+st.nextElement()).trim();
					d.fehlerpunkt=""+p;
					d.domanda=""+st.nextElement();
					d.header=""+st.nextElement();
					d.image=""+st.nextElement();
					d.frage1=""+st.nextElement();
					d.frage2=""+st.nextElement();
					d.frage3=""+st.nextElement();
				//	System.out.println("--->quiz  frahe3"+d.image);
					
					
					//System.out.println("--->quiz  d.image"+d.image);
					
					d.antwort1=(""+st.nextElement()).trim();
					//System.out.println("--->d.antwort1 antwort1 "+d.antwort1);
					
					d.antwort2=(""+st.nextElement()).trim();
					//System.out.println("--->d.antwort1 antwort2 "+d.antwort2);
					
					d.antwort3=(""+st.nextElement()).trim();
					

						
					
					ald.add(d);
					
				}
						
				
			
		
   	 return ald;
		
		
	}
    
    public static String replace(String str, String pattern, String replace) {
		int s = 0;
		int e = 0;
		StringBuffer result = new StringBuffer();
    
		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e+pattern.length();
		}
        
 
		result.append(str.substring(s));
		return result.toString();
	}

    	
    protected static Domanda getDomanda(ArrayList<Domanda> domande, int iddom) {
		// TODO Auto-generated method stub
		for (int i=0;i<domande.size();i++) {
			Domanda d = domande.get(i);
			//System.out.println("--->getDomandad.iddom"+d.iddom + "-" + iddom);
			if (d.iddom.equals(""+iddom)) {
				return d;
			}
			
			
		}
		return null;
	}

	public static Intent getIntentChat(Context context, String qid, String frage) {
		
		 Intent i;
		 
		
			i = new Intent(context, FuehrerscheinChatActivity.class);
			i.putExtra("frage", frage);
	 		i.putExtra("qid", qid);
		
 		
		return i;
	}
    
    
	

	    /**
	     * Tag for logging.
	     */
	    private static final String TAG = "Util";

	    // Shared constants

	    /**
	     * Key for account name in shared preferences.
	     */
	    public static final String ACCOUNT_NAME = "accountName";

	    /**
	     * Key for auth cookie name in shared preferences.
	     */
	    public static final String AUTH_COOKIE = "authCookie";

	    /**
	     * Key for connection status in shared preferences.
	     */
	    public static final String CONNECTION_STATUS = "connectionStatus";

	    /**
	     * Value for {@link #CONNECTION_STATUS} key.
	     */
	    public static final String CONNECTED = "connected";

	    /**
	     * Value for {@link #CONNECTION_STATUS} key.
	     */
	    public static final String CONNECTING = "connecting";

	    /**
	     * Value for {@link #CONNECTION_STATUS} key.
	     */
	    public static final String DISCONNECTED = "disconnected";

	    /**
	     * Key for device registration id in shared preferences.
	     */
	    public static final String DEVICE_REGISTRATION_ID = "deviceRegistrationID";

	    /*
	     * URL suffix for the RequestFactory servlet.
	     */
	    public static final String RF_METHOD = "/gwtRequest";

	    /**
	     * An intent name for receiving registration/unregistration status.
	     */
	    public static final String UPDATE_UI_INTENT = getPackageName() + ".UPDATE_UI";

	    // End shared constants



	    /**
	     * Cache containing the base URL for a given context.
	     */
	    private static final Map<Context, String> URL_MAP = new HashMap<Context, String>();

	  

	    /**
	     * Returns the (debug or production) URL associated with the registration
	     * service.
	     */
	    public static String getBaseUrl(Context context) {
	        String url = URL_MAP.get(context);
	        if (url == null) {
	            // if a debug_url raw resource exists, use its contents as the url
	            url = getDebugUrl(context);
	            // otherwise, use the production url
	            if (url == null) {
	                url = Setup.PROD_URL;
	            }
	            URL_MAP.put(context, url);
	        }
	        return url;
	    }

	    /**
	     * Creates and returns an initialized {@link RequestFactory} of the given
	     * type.
	     */
	    public static <T extends RequestFactory> T getRequestFactory(Context context,
	            Class<T> factoryClass) {
	        T requestFactory = RequestFactorySource.create(factoryClass);

	        SharedPreferences prefs = getSharedPreferences(context);
	        String authCookie = prefs.getString(Util.AUTH_COOKIE, null);

	        String uriString = Util.getBaseUrl(context) + RF_METHOD;
	        URI uri;
	        try {
	            uri = new URI(uriString);
	        } catch (URISyntaxException e) {
	            Log.w(TAG, "Bad URI: " + uriString, e);
	            return null;
	        }
	       // System.out.println("--->cookie requestFactory.initialize  "+authCookie);
	        
	        requestFactory.initialize(new SimpleEventBus(),
	                new AndroidRequestTransport(uri, authCookie));

	        
	        
	        return requestFactory;
	    }

	  

	    /**
	     * Returns true if we are running against a dev mode appengine instance.
	     */
	    public static boolean isDebug(Context context) {
	        // Although this is a bit roundabout, it has the nice side effect
	        // of caching the result.
	        return !Setup.PROD_URL.equals(getBaseUrl(context));
	    }

	    /**
	     * Returns a debug url, or null. To set the url, create a file
	     * {@code assets/debugging_prefs.properties} with a line of the form
	     * 'url=http:/<ip address>:<port>'. A numeric IP address may be required in
	     * situations where the device or emulator will not be able to resolve the
	     * hostname for the dev mode server.
	     */
	    private static String getDebugUrl(Context context) {
	        BufferedReader reader = null;
	        String url = null;
	        try {
	            AssetManager assetManager = context.getAssets();
	            InputStream is = assetManager.open("debugging_prefs.properties");
	            reader = new BufferedReader(new InputStreamReader(is));
	            while (true) {
	                String s = reader.readLine();
	                if (s == null) {
	                    break;
	                }
	                if (s.startsWith("url=")) {
	                    url = s.substring(4).trim();
	                    break;
	                }
	            }
	        } catch (FileNotFoundException e) {
	            // O.K., we will use the production server
	            return null;
	        } catch (Exception e) {
	            Log.w(TAG, "Got exception " + e);
	            Log.w(TAG, Log.getStackTraceString(e));
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    Log.w(TAG, "Got exception " + e);
	                    Log.w(TAG, Log.getStackTraceString(e));
	                }
	            }
	        }

	        return url;
	    }

	    /**
	     * Returns the package name of this class.
	     */
	    public static String getPackageName() {
	        return Util.class.getPackage().getName();
	    }
	    
	    public static boolean isNetworkAvailable(Context context) {
	    	  
	    	//if (Util.isDebug(context)) return true;
	    	
	  	   ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	  	   if (connectivity == null) {
	  		  return false;
	  	   } else {
	  	      NetworkInfo[] info = connectivity.getAllNetworkInfo();
	  	      if (info != null) {
	  	         for (int i = 0; i < info.length; i++) {
	  	        	 
	  	        	// System.out.println("--->info[i].getTypeName()"+info[i].getTypeName());
	  	        	 
	  	            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
	  	            	
	  	            	//System.out.println("--->info[i].getTypeName()"+info[i].getTypeName() + " connected");
	  	    	        
	  	            	
	  	               return true;
	  	            }
	  	         }
	  	      }
	  	   }
	  	   return false;
	  	}

		public static String getVersion(PackageManager pm) {
			// TODO Auto-generated method stub
			PackageInfo pInfo;
			try {
				pInfo = pm.getPackageInfo(getPackageName(), 0);
				return pInfo.versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "";
			
			
		}
		
		 /**
	     * Display a notification containing the given string.
	     */
	    public static void generateNotification(Context context, String message) {
	        int icon = R.drawable.status_icon;
	        long when = System.currentTimeMillis();

	        Notification notification = new Notification(icon, message, when);
	        notification.setLatestEventInfo(context, "C2DM Example", message,
	                PendingIntent.getActivity(context, 0, null, PendingIntent.FLAG_CANCEL_CURRENT));
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;

	        SharedPreferences settings = Util.getSharedPreferences(context);
	        int notificatonID = settings.getInt("notificationID", 0);

	        NotificationManager nm = (NotificationManager) context
	                .getSystemService(Context.NOTIFICATION_SERVICE);
	        nm.notify(notificatonID, notification);

	        SharedPreferences.Editor editor = settings.edit();
	        editor.putInt("notificationID", ++notificatonID % 32);
	        editor.commit();
	    }
}
