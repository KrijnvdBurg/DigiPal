package gsc.DigiPal.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import gsc.DigiPal.R;
import gsc.DigiPal.activities.MainActivity;


public class editProfile extends Fragment {
    public MainActivity MainActivity;
    private static final String TAG = viewProfile.class.getSimpleName();
    public editProfile(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(final View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        final String TAG = viewProfile.class.getSimpleName();
        MainActivity = (MainActivity) getActivity();
        RatingBar mRatingBar = (RatingBar) MainActivity.findViewById(R.id.strength_ratingBar);



    }



}