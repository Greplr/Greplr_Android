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

package com.greplr.common.ui;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.greplr.App;
import com.greplr.R;

/**
 * Created by championswimmer on 17/6/15.
 */
public class GlassCardView extends CardView {

    private int cardBackgroundColor = App.DARK_CARDS ?
            R.color.cardview_dark_background : R.color.cardview_light_background;

    private int theme = App.DARK_CARDS ?
            R.style.AppTheme : R.style.AppTheme_LightCards;

    private float cardViewAlpha = 0.94f;

    public GlassCardView(Context context) {
        super(context);
        context.setTheme(theme);
        setAlpha(cardViewAlpha);
        setCardBackgroundColor(getResources().getColor(cardBackgroundColor));
    }

    public GlassCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        context.setTheme(theme);
        setAlpha(cardViewAlpha);
        setCardBackgroundColor(getResources().getColor(cardBackgroundColor));
    }

    public GlassCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        context.setTheme(theme);
        setAlpha(cardViewAlpha);
        setCardBackgroundColor(getResources().getColor(cardBackgroundColor));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        MarginLayoutParams margins = MarginLayoutParams.class.cast(getLayoutParams());
        int horizontalMargin = getResources().getDimensionPixelSize(R.dimen.cardMarginHorizontal);
        int verticalMargin = getResources().getDimensionPixelSize(R.dimen.cardMarginVertical);
        margins.topMargin = verticalMargin;
        margins.bottomMargin = verticalMargin;
        margins.leftMargin = horizontalMargin;
        margins.rightMargin = horizontalMargin;
        setLayoutParams(margins);
    }


}
