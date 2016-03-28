package gsc.DigiPal.constructors.fetchImagePath_constr;

import java.util.ArrayList;

/**
 * Created by Krijn on 27-3-2016.
 */
public class fetchImagePath_root {
        public boolean error;
        public ArrayList<fetchImagePath_additionalPhoto> additionalPhoto = new ArrayList();

        public boolean getError() { return error; }
        public void setError(boolean error) {  this.error = error; }

        public fetchImagePath_additionalPhoto additionalPhoto(int pos) {
            return additionalPhoto.get(pos);
        }

}

