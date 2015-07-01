package com.greplr.models.shopping.search;

/**
 * Created by raghav on 01/07/15.
 */
public class Search {

    private ProductBaseInfo productBaseInfo;
    private ProductShippingBaseInfo productShippingBaseInfo;
    private Object offset;

    public ProductBaseInfo getProductBaseInfo() {
        return productBaseInfo;
    }

    public void setProductBaseInfo(ProductBaseInfo productBaseInfo) {
        this.productBaseInfo = productBaseInfo;
    }

    public ProductShippingBaseInfo getProductShippingBaseInfo() {
        return productShippingBaseInfo;
    }

    public void setProductShippingBaseInfo(ProductShippingBaseInfo productShippingBaseInfo) {
        this.productShippingBaseInfo = productShippingBaseInfo;
    }

    public Object getOffset() {
        return offset;
    }

    public void setOffset(Object offset) {
        this.offset = offset;
    }

}
