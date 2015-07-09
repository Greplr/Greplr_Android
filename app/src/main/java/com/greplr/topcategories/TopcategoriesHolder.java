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

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greplr.R;

/**
 * Created by championswimmer on 10/6/15.
 */
public class TopcategoriesHolder extends RecyclerView.ViewHolder {

    protected RelativeLayout cardContainer;
    protected ImageView cardIcon;
    protected TextView cardTitle;
    protected CardView cardView;

    public TopcategoriesHolder(View itemView) {
        super(itemView);

        cardView = (CardView) itemView.findViewById(R.id.topcategories_card_view);
        cardContainer = (RelativeLayout) itemView.findViewById(R.id.topcategories_card_container);
        cardIcon = (ImageView) itemView.findViewById(R.id.topcategories_card_icon);
        cardTitle = (TextView) itemView.findViewById(R.id.topcategories_card_title);

    }
}
