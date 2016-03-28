package gsc.DigiPal.aSyncTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Krijn on 27-3-2016.
 */


public class fetchImagePath_aSyncTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public fetchImagePath_aSyncTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        //pd.show();
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
       // pd.dismiss();
        //Bitmap resizedBitmap = Bitmap.createScaledBitmap(result, 90, 90, false);

        //return resizedBitmap;
        bmImage.setImageBitmap(scaleBitmap(result));
    }


    private Bitmap scaleBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int maxWidth = 400;
        int maxHeight = 90;
        Log.v("Pictures", "Width and height are " + width + "--" + height);

        if (width > height) {
            float ratio = (float) width / maxWidth;
            width = maxWidth;
            height = (int)(height / ratio);
        } else if (height > width) {
            float ratio = (float) height / maxHeight;
            height = maxHeight;
            width = (int)(width / ratio);
        } else {
            height = maxHeight;
            width = maxWidth;
        }
        Log.v("Pictures", "after scaling Width and height are " + width + "--" + height);
        bm = Bitmap.createScaledBitmap(bm, width, height, true);
        return bm;
    }
}