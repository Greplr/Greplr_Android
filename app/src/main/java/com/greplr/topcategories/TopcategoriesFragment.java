/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       Arnav Gupta, Abhinv Sinha, Raghav Apoorv,
 *       Shubham Dokania, Yogesh Balan
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.greplr.topcategories;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.greplr.MainActivity;
import com.greplr.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class TopcategoriesFragment extends Fragment {

    public TopcategoriesFragment() {
    }

    public static TopcategoriesFragment newInstance() {
        return new TopcategoriesFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.topcategories_fragment, container, false);
        RecyclerView categoryList = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_categories);
        //KenBurnsView backgroundImage = (KenBurnsView) rootView.findViewById(R.id.topcategories_background);
        ImageView backgroundImage = ((MainActivity) getActivity()).getBackgroundImage();
        int[] backImgs = {
                R.drawable.main_background_1,
                R.drawable.main_background_2,
        };
        Random rGen = new Random();
        int backImageResource = backImgs[rGen.nextInt(backImgs.length)];
        //backgroundImage.setImageResource(backImageResource);
        Picasso.with(getActivity()).load(backImageResource).fit().centerCrop().into(backgroundImage);
        categoryList.setHasFixedSize(true);

        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        glm.setSpanCount(screenWidth / (getResources().getDimensionPixelOffset(R.dimen.column_width_main_recyclerview)));
        categoryList.setLayoutManager(glm);

        categoryList.setAdapter(new TopcategoriesAdapter(getActivity()));
        return rootView;
    }
}
