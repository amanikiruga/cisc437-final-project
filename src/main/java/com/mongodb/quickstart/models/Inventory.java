package com.mongodb.quickstart.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;
import java.util.Date;

public class Inventory {

    private ObjectId id;
    private ObjectId inventory_product_id;
    private Integer inventory_serial_nbr;
    private ObjectId inventory_crtd_id;
    private Date invenotry_crtd_dt;
    private ObjectId inventory_updt_id;
    private Date inventory_updt_dt;

    public Inventory() {
    }

    public Inventory(ObjectId id, ObjectId inventory_product_id, Integer inventory_serial_nbr,
            ObjectId inventory_crtd_id, Date invenotry_crtd_dt, ObjectId inventory_updt_id, Date inventory_updt_dt) {
        this.id = id;
        this.inventory_product_id = inventory_product_id;
        this.inventory_serial_nbr = inventory_serial_nbr;
        this.inventory_crtd_id = inventory_crtd_id;
        this.invenotry_crtd_dt = invenotry_crtd_dt;
        this.inventory_updt_id = inventory_updt_id;
        this.inventory_updt_dt = inventory_updt_dt;
    }

    public ObjectId getInventory_id() {
        return this.id;
    }

    public void setInventory_id(ObjectId id) {
        this.id = id;
    }

    public ObjectId getInventory_product_id() {
        return this.inventory_product_id;
    }

    public void setInventory_product_id(ObjectId inventory_product_id) {
        this.inventory_product_id = inventory_product_id;
    }

    public Integer getInventory_serial_nbr() {
        return this.inventory_serial_nbr;
    }

    public void setInventory_serial_nbr(Integer inventory_serial_nbr) {
        this.inventory_serial_nbr = inventory_serial_nbr;
    }

    public ObjectId getInventory_crtd_id() {
        return this.inventory_crtd_id;
    }

    public void setInventory_crtd_id(ObjectId inventory_crtd_id) {
        this.inventory_crtd_id = inventory_crtd_id;
    }

    public Date getInvenotry_crtd_dt() {
        return this.invenotry_crtd_dt;
    }

    public void setInvenotry_crtd_dt(Date invenotry_crtd_dt) {
        this.invenotry_crtd_dt = invenotry_crtd_dt;
    }

    public ObjectId getInventory_updt_id() {
        return this.inventory_updt_id;
    }

    public void setInventory_updt_id(ObjectId inventory_updt_id) {
        this.inventory_updt_id = inventory_updt_id;
    }

    public Date getInventory_updt_dt() {
        return this.inventory_updt_dt;
    }

    public void setInventory_updt_dt(Date inventory_updt_dt) {
        this.inventory_updt_dt = inventory_updt_dt;
    }

    public Inventory inventory_id(ObjectId inventory_id) {
        setInventory_id(inventory_id);
        return this;
    }

    public Inventory inventory_product_id(ObjectId inventory_product_id) {
        setInventory_product_id(inventory_product_id);
        return this;
    }

    public Inventory inventory_serial_nbr(Integer inventory_serial_nbr) {
        setInventory_serial_nbr(inventory_serial_nbr);
        return this;
    }

    public Inventory inventory_crtd_id(ObjectId inventory_crtd_id) {
        setInventory_crtd_id(inventory_crtd_id);
        return this;
    }

    public Inventory invenotry_crtd_dt(Date invenotry_crtd_dt) {
        setInvenotry_crtd_dt(invenotry_crtd_dt);
        return this;
    }

    public Inventory inventory_updt_id(ObjectId inventory_updt_id) {
        setInventory_updt_id(inventory_updt_id);
        return this;
    }

    public Inventory inventory_updt_dt(Date inventory_updt_dt) {
        setInventory_updt_dt(inventory_updt_dt);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Inventory)) {
            return false;
        }
        Inventory inventory = (Inventory) o;
        return Objects.equals(id, inventory.id) && Objects.equals(inventory_product_id, inventory.inventory_product_id)
                && Objects.equals(inventory_serial_nbr, inventory.inventory_serial_nbr)
                && Objects.equals(inventory_crtd_id, inventory.inventory_crtd_id)
                && Objects.equals(invenotry_crtd_dt, inventory.invenotry_crtd_dt)
                && Objects.equals(inventory_updt_id, inventory.inventory_updt_id)
                && Objects.equals(inventory_updt_dt, inventory.inventory_updt_dt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, inventory_product_id, inventory_serial_nbr, inventory_crtd_id, invenotry_crtd_dt,
                inventory_updt_id, inventory_updt_dt);
    }

    @Override
    public String toString() {
        return "{" +
                " inventory_id='" + getInventory_id() + "'" +
                ", inventory_product_id='" + getInventory_product_id() + "'" +
                ", inventory_serial_nbr='" + getInventory_serial_nbr() + "'" +
                ", inventory_crtd_id='" + getInventory_crtd_id() + "'" +
                ", invenotry_crtd_dt='" + getInvenotry_crtd_dt() + "'" +
                ", inventory_updt_id='" + getInventory_updt_id() + "'" +
                ", inventory_updt_dt='" + getInventory_updt_dt() + "'" +
                "}";
    }

}