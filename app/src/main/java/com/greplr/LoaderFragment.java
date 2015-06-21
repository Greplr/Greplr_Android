package com.greplr;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoaderFragment extends Fragment {


    public LoaderFragment() {
        // Required empty public constructor
    }

    public static LoaderFragment newInstance () {
        return new LoaderFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_loader, container, false);

        ImageView backgroundImage = (ImageView) rootView.findViewById(R.id.loader_container);
        int [] backImgs = {
                R.drawable.main_background_1,
                R.drawable.main_background_2,
        };
        Random rGen = new Random();
        int backImageResource = backImgs[rGen.nextInt(backImgs.length)];
        Picasso.with(getActivity()).load(backImageResource).fit().centerCrop().into(backgroundImage);

        return rootView;
    }


}
