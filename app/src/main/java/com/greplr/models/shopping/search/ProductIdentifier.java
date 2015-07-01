package com.greplr.models.shopping.search;

/**
 * Created by raghav on 01/07/15.
 */
public class ProductIdentifier {

    private String productId;
    private CategoryPaths categoryPaths;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public CategoryPaths getCategoryPaths() {
        return categoryPaths;
    }

    public void setCategoryPaths(CategoryPaths categoryPaths) {
        this.categoryPaths = categoryPaths;
    }
}
