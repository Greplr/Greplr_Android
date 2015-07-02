package com.greplr.models.shopping.search;

/**
 * Created by raghav on 01/07/15.
 */
public class ProductBaseInfo {

    private ProductIdentifier productIdentifier;
    private ProductAttributes productAttributes;

    public ProductIdentifier getProductIdentifier() {
        return productIdentifier;
    }

    public void setProductIdentifier(ProductIdentifier productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public ProductAttributes getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(ProductAttributes productAttributes) {
        this.productAttributes = productAttributes;
    }
}
