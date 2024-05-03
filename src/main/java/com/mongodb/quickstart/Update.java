package com.mongodb.quickstart;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class Update {

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(System.getProperty("mongodb.uri"))) {
            MongoDatabase database = mongoClient.getDatabase("demo-store");
            MongoCollection<Document> productCollection = database.getCollection("products");

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
                        "Old document: " + (oldDocument != null ? oldDocument.toJson() : "Document not found."));

                // Find one and update - return new document
                Document newDocument = findOneAndUpdate(productCollection, productId,
                        "Finally updated via findOneAndUpdate", true);
                System.out.println(
                        "New document: " + (newDocument != null ? newDocument.toJson() : "Document not found."));
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
}
