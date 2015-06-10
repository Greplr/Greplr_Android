package com.greplr;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by championswimmer on 10/6/15.
 */
public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryHolder> {

    @Override
    public MainCategoryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_main_categories, viewGroup, false);

        return new MainCategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainCategoryHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
