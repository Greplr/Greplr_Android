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

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greplr.R;

/**
 * Created by championswimmer on 10/6/15.
 */
public class TopcategoriesHolder extends RecyclerView.ViewHolder {

    protected LinearLayout cardContainer;
    protected ImageView cardIcon;
    protected TextView cardTitle;
    protected CardView cardView;

    public TopcategoriesHolder(View itemView) {
        super(itemView);

        cardView = (CardView) itemView.findViewById(R.id.topcategories_card_view);
        cardContainer = (LinearLayout) itemView.findViewById(R.id.topcategories_card_container);
        cardIcon = (ImageView) itemView.findViewById(R.id.topcategories_card_icon);
        cardTitle = (TextView) itemView.findViewById(R.id.topcategories_card_title);

    }
}
