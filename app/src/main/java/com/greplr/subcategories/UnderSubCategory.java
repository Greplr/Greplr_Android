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
