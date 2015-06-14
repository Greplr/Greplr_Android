package com.greplr.topcategories;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greplr.MainActivity;
import com.greplr.R;

import java.util.ArrayList;

/**
 * Created by championswimmer on 10/6/15.
 */
public class TopcategoriesAdapter extends RecyclerView.Adapter<TopcategoriesHolder> {

    private ArrayList<Topcategories.Category> categories;
    private Resources mResources;

    public TopcategoriesAdapter(Resources res) {
        categories = Topcategories.getTopCategories();
        mResources = res;
    }



    public Topcategories.Category getItem (int id) {
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
        try {
//            holder.cardIcon.setImageResource(cat.cardIcon);

            AnimationDrawable ad = (AnimationDrawable) mResources.getDrawable(cat.cardIcon);
            ad.setEnterFadeDuration(800);
            ad.setExitFadeDuration(800);
            holder.cardIcon.setImageDrawable(ad);
            holder.cardIcon.setAdjustViewBounds(true);
            holder.cardContainer.setBackgroundResource(cat.cardColor);
            holder.cardContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.switchFragment(cat.categoryFragment);
                }
            });
            holder.cardTitle.setText(cat.name);
        } catch (Exception e) {
            Log.d("Greplr", "Unable to setup category cards");
        }


    }

    @Override
    public int getItemCount() {
        return Topcategories.TOTAL_CATEGORIES;
    }
}
