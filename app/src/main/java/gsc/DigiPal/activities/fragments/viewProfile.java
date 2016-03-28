package gsc.DigiPal.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gsc.DigiPal.R;
import gsc.DigiPal.activities.MainActivity;
import gsc.DigiPal.app.AppConfig;
import gsc.DigiPal.app.AppController;
import gsc.DigiPal.constructors.fetchImagePath_constr.fetchImagePath_root;

public class viewProfile extends Fragment {
    private static final String TAG = viewProfile.class.getSimpleName();
    public MainActivity MainActivity;
    public LinearLayout POImages_horizontalScrollView_LinearLayout;



    public viewProfile(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_profile, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(final View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        final String TAG = viewProfile.class.getSimpleName();
        MainActivity = (MainActivity) getActivity();
        defineWidgets();
        fetchPOphotos();
        checkIfOwner("2");
        //reverseOrder();
    }

    public void reverseOrder(){
       // LinearLayout POImages_horizontalScrollView_LinearLayout = // inflate
                ArrayList<View> views = new ArrayList<View>();
        for(int x = 0; x < POImages_horizontalScrollView_LinearLayout.getChildCount(); x++) {
            views.add(POImages_horizontalScrollView_LinearLayout.getChildAt(x));
        }
        POImages_horizontalScrollView_LinearLayout.removeAllViews();
        for(int x = views.size() - 1; x >= 0; x--) {
            POImages_horizontalScrollView_LinearLayout.addView(views.get(x));
        }
    }


    public void defineWidgets(){
        POImages_horizontalScrollView_LinearLayout = (LinearLayout) getActivity().findViewById(R.id.POImages_horizontalScrollView_LinearLayout);
    }

    //PO = profile owner
    public void fetchPOphotos(){
        System.out.println(AppConfig.URL_FETCHIMAGEPATH);
        try {
            StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_FETCHIMAGEPATH, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    System.out.println("response" + response);
                    Gson gson = new Gson();
                    fetchImagePath_root fetchImagePath_root = gson.fromJson(response, fetchImagePath_root.class);
                    boolean error = fetchImagePath_root.getError();
                    if (!error) {
                        for(int i = 0; i < fetchImagePath_root.additionalPhoto.size(); i++) {

                            ContextThemeWrapper newContext = new ContextThemeWrapper(getActivity(), R.style.po_additionalphotos);
                            ImageView addPOphoto_additionalPhoto = new ImageView(newContext);

                            addPOphoto_additionalPhoto.setImageResource(R.drawable.add);
                                String url = AppConfig.PROJECT_ROOT + "/" +
                                        fetchImagePath_root.additionalPhoto(i).getPath() + "/" +
                                        fetchImagePath_root.additionalPhoto(i).getName() +
                                        fetchImagePath_root.additionalPhoto(i).getExtension();
                                Picasso.with(getActivity())
                                        .load(url)
                                        .resize(0, 135)
                                       //.centerInside()
                                .into(addPOphoto_additionalPhoto);

                                POImages_horizontalScrollView_LinearLayout.addView(addPOphoto_additionalPhoto);
                        }
                    } else {
                        System.out.println("fetchImagePath_root returned JSON object error");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onErrorResponse: " + error.getMessage());
                    Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", "3");
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(strReq, "req_main");
        } catch(Exception e) {
            System.out.println("catch: "  + e );
            Toast.makeText(getActivity().getApplicationContext(), "This is most likely due to faulty internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void checkIfOwner(String pageOwnerUUID){

        //maak profile edit imagebutton
        if(pageOwnerUUID.equals(MainActivity.globalUser.getLocalUser_UUID())){
            ImageButton addPOphoto_imageButton = new ImageButton(getActivity());
            addPOphoto_imageButton.setBackground(null);
            addPOphoto_imageButton.setImageResource(R.drawable.add);
            addPOphoto_imageButton.setId(R.id.addPOphoto_imageButton);

            Picasso.with(getActivity())
                    .load(R.drawable.add)
                    .resize(0, 90)
                    .into(addPOphoto_imageButton);

            POImages_horizontalScrollView_LinearLayout.addView(addPOphoto_imageButton);
            addPOphoto_imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

}