package com.example.domain;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "Product")
@Table(name = "product")
public class Product extends BaseEntity<Long> {

    private static final long serialVersionUID = -967500715378841906L;

    @ApiModelProperty(notes = "The application specific product ID")
    @NotNull
    @Column(name = "product_id")
    private String productId;

    @ApiModelProperty(notes = "The product description")
    private String description;

    @ApiModelProperty(notes = "The image URL of the product")
    @Column(name = "image_url")
    private String imageUrl;

    @ApiModelProperty(notes = "The price of the product", required = true)
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) &&
                Objects.equals(description, product.description) &&
                Objects.equals(imageUrl, product.imageUrl) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, description, imageUrl, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", id=" + super.getId() +
                '}';
    }
}
