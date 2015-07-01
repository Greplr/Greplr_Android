package com.greplr.models.shopping.search;

import java.util.List;

/**
 * Created by raghav on 01/07/15.
 */
public class CategoryPaths {
    private List<List<CategoryPath>> categoryPath;

    public List<List<CategoryPath>> getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(List<List<CategoryPath>> categoryPath) {
        this.categoryPath = categoryPath;
    }
}
