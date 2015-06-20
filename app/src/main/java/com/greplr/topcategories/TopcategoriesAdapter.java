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
//        try {

            AnimationDrawable ad = (AnimationDrawable) ContextCompat.getDrawable(mContext, cat.cardIcon);
            ad.setEnterFadeDuration(800);
            ad.setExitFadeDuration(800);
            holder.cardIcon.setImageDrawable(ad);
            ad.start();
            holder.cardIcon.setAdjustViewBounds(true);
            holder.cardContainer.setBackgroundResource(cat.drawable);
            holder.cardContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.switchFragment(cat.categoryFragment, view, "mTransition");
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
