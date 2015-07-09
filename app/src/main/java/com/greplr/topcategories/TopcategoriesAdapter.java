/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       1. Arnav Gupta
 *       2. Abhinav Sinha
 *       3. Prempal Singh
 *       4. Raghav Apoorv
 *       5. Shubham Dokania
 *       6. Yogesh Balan
 *
 *     The source code of this program is confidential and proprietary. If you are not part of the
 *     Greplr Team (one of the above 6 named individuals) you should not be viewing this code.
 *
 *     You should immediately close your copy of code, and destory the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.topcategories;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.common.utils.ColorUtils;

import java.util.ArrayList;

/**
 * Created by championswimmer on 10/6/15.
 */
public class TopcategoriesAdapter extends RecyclerView.Adapter<TopcategoriesHolder> {

    private ArrayList<Topcategories.Category> categories;
    private Context mContext;

    public TopcategoriesAdapter(Context c) {
        categories = Topcategories.getTopCategories();
        mContext = c;
    }


    public Topcategories.Category getItem(int id) {
        return categories.get(id);
    }

    @Override
    public TopcategoriesHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.topcategories_cardview, viewGroup, false);

        return new TopcategoriesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopcategoriesHolder holder, int position) {
        final Topcategories.Category cat = getItem(position);
//        try {

        AnimationDrawable ad = (AnimationDrawable) ContextCompat.getDrawable(mContext, cat.cardIcon);
        ad.setEnterFadeDuration(1000);
        ad.setExitFadeDuration(1000);
        holder.cardIcon.setImageDrawable(ad);
        ad.start();
        holder.cardIcon.setAdjustViewBounds(true);
        int cardColor = mContext.getResources().getColor(cat.cardColor);
        holder.cardView.setCardBackgroundColor(ColorUtils.adjustAlpha(cardColor, 0.85f));
        holder.cardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.switchFragment(cat.categoryFragment, true);
            }
        });
        holder.cardTitle.setText(cat.name);

//        } catch (Exception e) {
//            Log.d("Greplr", "Unable to setup category cards");
//        }


    }

    @Override
    public int getItemCount() {
        return Topcategories.TOTAL_CATEGORIES;
    }
}
