package com.greplr.topcategories;

import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.greplr.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TopcategoriesFragment extends Fragment {

    public TopcategoriesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.topcategories_fragment, container, false);
        RecyclerView categoryList = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_categories);
//        categoryList.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        glm.setSpanCount(screenWidth/(getResources().getDimensionPixelOffset(R.dimen.column_width_main_recyclerview)));
        categoryList.setLayoutManager(glm);
        categoryList.setAdapter(new TopcategoriesAdapter());
        return rootView;
    }
}