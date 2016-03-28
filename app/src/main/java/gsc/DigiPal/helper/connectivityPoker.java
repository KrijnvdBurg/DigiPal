package gsc.DigiPal.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Krijn-Laptop on 17-3-2016.
 */
public class connectivityPoker {

    Context context;
    boolean connection;

    public connectivityPoker(Context context) {
       // super(context);

        this.context = context;
    }

    public boolean poker() {

        //boolean connection;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                connection = true;
                //Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                connection = true;
                // connected to the mobile provider's data plan
               // Toast.makeText(context, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "no internet connection, aborting", Toast.LENGTH_SHORT).show();
            connection = false;
        }
        return connection;
    }

}
