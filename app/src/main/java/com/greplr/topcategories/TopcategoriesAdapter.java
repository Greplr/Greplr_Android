package com.greplr.topcategories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greplr.R;

/**
 * Created by championswimmer on 10/6/15.
 */
public class TopcategoriesAdapter extends RecyclerView.Adapter<TopcategoriesHolder> {

    @Override
    public TopcategoriesHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.topcategories_cardview, viewGroup, false);

        return new TopcategoriesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopcategoriesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
