package com.greplr.subcategories;

import android.support.v4.app.Fragment;

/**
 * Created by championswimmer on 17/6/15.
 */
public class UnderSubCategory {
    public String name;
    public int backImgResId;
    public int logoResId;
    public Fragment frag;

    public UnderSubCategory(String name, int backImgResId, int logoResId, Fragment frag) {
        this.name = name;
        this.backImgResId = backImgResId;
        this.logoResId = logoResId;
        this.frag = frag;
    }
}
