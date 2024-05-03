package com.mongodb.quickstart;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Create {

    private static final Random rand = new Random();

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(System.getProperty("mongodb.uri"))) {
            MongoDatabase database = mongoClient.getDatabase("demo-store");
            MongoCollection<Document> productCollection = database.getCollection("product");
            MongoCollection<Document> inventoryCollection = database.getCollection("inventory");

            // Insert one product
            Document singleProduct = generateRandomProductDocument();
            insertOneProduct(productCollection, singleProduct);

            // Insert multiple products
            List<Document> productList = generateRandomProductDocuments(5);
            insertManyProducts(productCollection, productList);

            // Insert one inventory item
            ObjectId productId = singleProduct.getObjectId("_id");
            insertOneInventoryItem(inventoryCollection, productId);

            // Insert multiple inventory items
            insertMultipleInventoryItems(inventoryCollection, productId, 3);
        }
    }

    private static Document generateRandomProductDocument() {
        ObjectId productId = new ObjectId();
        String productName = "Product " + rand.nextInt(100);
        String productDesc = "Description for " + productName;
        ObjectId statusId = new ObjectId();
        ObjectId crtdId = new ObjectId();
        Date crtdDate = new Date();
        ObjectId updtId = new ObjectId();
        Date updtDate = new Date();
        List<Document> inventories = generateInventoryDocuments(rand.nextInt(5) + 1, productId);

        return new Document("_id", productId)
                .append("product_name", productName)
                .append("product_desc", productDesc)
                .append("status_id", statusId)
                .append("crtd_id", crtdId)
                .append("crtd_date", crtdDate)
                .append("updt_id", updtId)
                .append("updt_date", updtDate)
                .append("inventories", inventories);
    }

    private static List<Document> generateRandomProductDocuments(int count) {
        List<Document> products = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            products.add(generateRandomProductDocument());
        }
        return products;
    }

    private static void insertOneProduct(MongoCollection<Document> collection, Document product) {
        collection.insertOne(product);
        System.out.println("Inserted one product with ID: " + product.get("_id"));
    }

    private static void insertManyProducts(MongoCollection<Document> collection, List<Document> products) {
        collection.insertMany(products);
        System.out.println("Inserted multiple products");
    }

    private static void insertOneInventoryItem(MongoCollection<Document> collection, ObjectId productId) {
        Document inventory = new Document("_id", new ObjectId())
                .append("product_id", productId)
                .append("serial_number", rand.nextInt(1000))
                .append("crtd_id", new ObjectId())
                .append("crtd_date", new Date())
                .append("updt_id", new ObjectId())
                .append("updt_date", new Date());
        collection.insertOne(inventory);
        System.out.println("Inserted one inventory item for Product ID: " + productId);
    }

    private static void insertMultipleInventoryItems(MongoCollection<Document> collection, ObjectId productId,
            int count) {
        List<Document> inventories = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Document inventory = new Document("_id", new ObjectId())
                    .append("product_id", productId)
                    .append("serial_number", rand.nextInt(1000))
                    .append("crtd_id", new ObjectId())
                    .append("crtd_date", new Date())
                    .append("updt_id", new ObjectId())
                    .append("updt_date", new Date());
            inventories.add(inventory);
        }
        collection.insertMany(inventories);
        System.out.println("Inserted " + count + " inventory items for Product ID: " + productId);
    }

    private static List<Document> generateInventoryDocuments(int count, ObjectId productId) {
        List<Document> inventories = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Document inventory = new Document("_id", new ObjectId())
                    .append("product_id", productId)
                    .append("serial_number", 1000 + rand.nextInt(9000))
                    .append("crtd_id", new ObjectId())
                    .append("crtd_date", new Date())
                    .append("updt_id", new ObjectId())
                    .append("updt_date", new Date());
            inventories.add(inventory);
        }
        return inventories;
    }
}
