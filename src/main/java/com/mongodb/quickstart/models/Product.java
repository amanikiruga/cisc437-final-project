package com.mongodb.quickstart.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;
import java.util.Date;

public class Product {
    private ObjectId product_id;
    private String product_name;
    private String product_desc;
    private ObjectId product_status_id;
    private ObjectId product_crtd_id;
    private Date product_crtd_dt;
    private ObjectId product_updt_id;
    private Date product_updt_dt;
    private List<Inventory> inventory;

    // Constructor
    public Product(ObjectId product_id, String product_name, String product_desc, ObjectId product_status_id,
            ObjectId product_crtd_id, Date product_crtd_dt, ObjectId product_updt_id, Date product_updt_dt,
            List<Inventory> inventory) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_desc = product_desc;
        this.product_status_id = product_status_id;
        this.product_crtd_id = product_crtd_id;
        this.product_crtd_dt = product_crtd_dt;
        this.product_updt_id = product_updt_id;
        this.product_updt_dt = product_updt_dt;
        this.inventory = inventory;
    }

    // Getters and Setters
    public ObjectId getProductId() {
        return product_id;
    }

    public void setProductId(ObjectId product_id) {
        this.product_id = product_id;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getProductDesc() {
        return product_desc;
    }

    public void setProductDesc(String product_desc) {
        this.product_desc = product_desc;
    }

    public ObjectId getProductStatusId() {
        return product_status_id;
    }

    public void setProductStatusId(ObjectId product_status_id) {
        this.product_status_id = product_status_id;
    }

    public ObjectId getProductCrtdId() {
        return product_crtd_id;
    }

    public void setProductCrtdId(ObjectId product_crtd_id) {
        this.product_crtd_id = product_crtd_id;
    }

    public Date getProductCrtdDt() {
        return product_crtd_dt;
    }

    public void setProductCrtdDt(Date product_crtd_dt) {
        this.product_crtd_dt = product_crtd_dt;
    }

    public ObjectId getProductUpdtId() {
        return product_updt_id;
    }

    public void setProductUpdtId(ObjectId product_updt_id) {
        this.product_updt_id = product_updt_id;
    }

    public Date getProductUpdtDt() {
        return product_updt_dt;
    }

    public void setProductUpdtDt(Date product_updt_dt) {
        this.product_updt_dt = product_updt_dt;
    }

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(product_id, product.product_id) &&
                Objects.equals(product_name, product.product_name) &&
                Objects.equals(product_desc, product.product_desc) &&
                Objects.equals(product_status_id, product.product_status_id) &&
                Objects.equals(product_crtd_id, product.product_crtd_id) &&
                Objects.equals(product_crtd_dt, product.product_crtd_dt) &&
                Objects.equals(product_updt_id, product.product_updt_id) &&
                Objects.equals(product_updt_dt, product.product_updt_dt) &&
                Objects.equals(inventory, product.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, product_name, product_desc, product_status_id, product_crtd_id,
                product_crtd_dt, product_updt_id, product_updt_dt, inventory);
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", product_desc='" + product_desc + '\'' +
                ", product_status_id=" + product_status_id +
                ", product_crtd_id=" + product_crtd_id +
                ", product_crtd_dt=" + product_crtd_dt +
                ", product_updt_id=" + product_updt_id +
                ", product_updt_dt=" + product_updt_dt +
                ", inventory=" + inventory +
                '}';
    }
}
