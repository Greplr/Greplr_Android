package com.greplr.topcategories;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greplr.MainActivity;
import com.greplr.R;

/**
 * Created by championswimmer on 10/6/15.
 */
public class TopcategoriesHolder extends RecyclerView.ViewHolder {

    protected LinearLayout cardContainer;
    protected ImageView cardIcon;
    protected TextView cardTitle;

    public TopcategoriesHolder(View itemView) {
        super(itemView);

        cardContainer = (LinearLayout) itemView.findViewById(R.id.topcategories_card_container);
        cardIcon = (ImageView) itemView.findViewById(R.id.topcategories_card_icon);
        cardTitle = (TextView) itemView.findViewById(R.id.topcategories_card_title);

    }
}
