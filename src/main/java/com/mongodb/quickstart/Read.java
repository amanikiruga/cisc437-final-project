package com.mongodb.quickstart;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class Read {

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(System.getProperty("mongodb.uri"))) {
            MongoDatabase database = mongoClient.getDatabase("demo-store");
            MongoCollection<Document> collection = database.getCollection("product");

            // Find all documents in the collection
            findAllDocuments(collection);

            // Find documents where product name starts with "P"
            findProductsWithNameStartingWithP(collection);

            // Find documents where product description contains "phone"
            findProductsWithDescriptionContainingPhone(collection);

            // Find documents where inventory serial number is greater than 100
            findProductsWithInventorySerialNumberGreaterThan100(collection);

            // Find documents sorted by product creation date in descending order
            findProductSortedByCreationDateDescending(collection);
        }
    }

    private static void findAllDocuments(MongoCollection<Document> collection) {
        System.out.println("All Products:");
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }

    private static void findProductsWithNameStartingWithP(MongoCollection<Document> collection) {
        System.out.println("\nProducts with name starting with 'P':");
        try (MongoCursor<Document> cursor = collection.find(regex("product_name", "^P")).iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }

    private static void findProductsWithDescriptionContainingPhone(MongoCollection<Document> collection) {
        System.out.println("\nProducts with description containing 'phone':");
        try (MongoCursor<Document> cursor = collection.find(regex("product_desc", "phone", "i")).iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }

    private static void findProductsWithInventorySerialNumberGreaterThan100(MongoCollection<Document> collection) {
        System.out.println("\nProducts with inventory serial number greater than 100:");
        try (MongoCursor<Document> cursor = collection.find(gt("inventory.inventory_serial_nbr", 100)).iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }

    private static void findProductSortedByCreationDateDescending(MongoCollection<Document> collection) {
        System.out.println("\nProducts sorted by creation date in descending order:");
        try (MongoCursor<Document> cursor = collection.find()
                .sort(descending("product_crtd_dt"))
                .projection(fields(include("product_name", "product_crtd_dt"), excludeId()))
                .iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }
}
