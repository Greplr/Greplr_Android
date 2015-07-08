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
