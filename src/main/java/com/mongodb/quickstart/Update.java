package com.mongodb.quickstart;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Update {

    private static final Random rand = new Random();

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(System.getProperty("mongodb.uri"))) {
            MongoDatabase database = mongoClient.getDatabase("demo-store");
            MongoCollection<Document> productCollection = database.getCollection("product");

            // Assume we are updating the first product inserted by Create.java
            Document firstProduct = productCollection.find().first();
            if (firstProduct != null) {
                ObjectId productId = firstProduct.getObjectId("_id");

                // Update one document
                updateOneDocument(productCollection, productId, "Updated description");

                // Upsert - insert if not exists, update if exists
                ObjectId newProductId = new ObjectId(); // For upsert example
                upsertDocument(productCollection, newProductId, "Upserted Product",
                        "Newly upserted product description");

                // Update many documents
                updateManyDocuments(productCollection, "Product", "Batch updated description");

                // Find one and update - return old document
                Document oldDocument = findOneAndUpdate(productCollection, productId, "Updated via findOneAndUpdate",
                        false);
                System.out.println(
                        "Old document: " + (oldDocument != null
                                ? oldDocument.toJson(JsonWriterSettings.builder().indent(true).build())
                                : "Document not found."));

                // Find one and update - return new document
                Document newDocument = findOneAndUpdate(productCollection, productId,
                        "Finally updated via findOneAndUpdate", true);
                System.out.println(
                        "New document: " + (newDocument != null
                                ? newDocument.toJson(JsonWriterSettings.builder().indent(true).build())
                                : "Document not found."));

                updateWithOneInventoryItem(productCollection, productId);

                // Insert multiple inventory items
                updateWithMultipleInventoryItems(productCollection, productId, 3);
            } else {
                System.out.println("No products found to update.");
            }
        }
    }

    private static void updateOneDocument(MongoCollection<Document> collection, ObjectId id, String newDescription) {
        Bson filter = eq("_id", id);
        Bson update = set("product_desc", newDescription);
        UpdateResult result = collection.updateOne(filter, update);
        System.out.println("Documents updated: " + result.getModifiedCount());
    }

    private static void upsertDocument(MongoCollection<Document> collection, ObjectId id, String name,
            String description) {
        Bson filter = eq("_id", id);
        Bson update = new Document("$set", new Document("product_name", name).append("product_desc", description));
        UpdateOptions options = new UpdateOptions().upsert(true);
        UpdateResult result = collection.updateOne(filter, update, options);
        System.out.println("Upserted document count: " + (result.getUpsertedId() != null ? "1" : "0"));
    }

    private static void updateManyDocuments(MongoCollection<Document> collection, String name, String newDescription) {
        Bson filter = eq("product_name", name + " " + 1); // Assuming "Product 1" is a name pattern used in Create.java
        Bson update = set("product_desc", newDescription);
        UpdateResult result = collection.updateMany(filter, update);
        System.out.println("Documents updated: " + result.getModifiedCount());
    }

    private static Document findOneAndUpdate(MongoCollection<Document> collection, ObjectId id, String newDescription,
            boolean returnNew) {
        Bson filter = eq("_id", id);
        Bson update = set("product_desc", newDescription);
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
                .returnDocument(returnNew ? ReturnDocument.AFTER : ReturnDocument.BEFORE);
        return collection.findOneAndUpdate(filter, update, options);
    }

    private static void updateWithOneInventoryItem(MongoCollection<Document> collection, ObjectId productId) {
        Document inventory = new Document("_id", new ObjectId())
                .append("serial_number", rand.nextInt(1000))
                .append("crtd_id", new ObjectId())
                .append("crtd_date", new Date())
                .append("updt_id", new ObjectId())
                .append("updt_date", new Date());
        collection.updateOne(new Document("_id", productId), Updates.push("inventories", inventory));
        System.out.println("Updated one inventory item for Product ID: " + productId);
    }

    private static void updateWithMultipleInventoryItems(MongoCollection<Document> collection, ObjectId productId,
            int count) {
        List<Document> inventories = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Document inventory = new Document("_id", new ObjectId())
                    .append("serial_number", 1000 + rand.nextInt(9000))
                    .append("crtd_id", new ObjectId())
                    .append("crtd_date", new Date())
                    .append("updt_id", new ObjectId())
                    .append("updt_date", new Date());
            inventories.add(inventory);
        }
        collection.updateOne(new Document("_id", productId), Updates.pushEach("inventories", inventories));
        System.out.println("Updated " + count + " inventory items for Product ID: " + productId);
    }
}
