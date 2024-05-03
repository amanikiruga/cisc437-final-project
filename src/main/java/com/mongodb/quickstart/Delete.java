package com.mongodb.quickstart;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Delete {

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(System.getProperty("mongodb.uri"))) {
            MongoDatabase database = mongoClient.getDatabase("demo-store");
            MongoCollection<Document> productCollection = database.getCollection("product");

            // Fetch a product ID from the database
            ObjectId productId = fetchProductId(productCollection);
            if (productId != null) {
                // Delete one document by ID
                deleteOneDocument(productCollection, productId);

                // Attempt to find and delete another document
                ObjectId anotherProductId = fetchProductId(productCollection); // Fetch another ID if available
                if (anotherProductId != null) {
                    findOneAndDeleteDocument(productCollection, anotherProductId);
                } else {
                    System.out.println("No additional document found for findOneAndDelete operation.");
                }
            }

            // Delete many documents by a generic condition, for example, product_name
            deleteManyDocuments(productCollection, "Description");

            // Optionally, drop the entire collection
            dropCollection(database, "product");
        }
    }

    private static ObjectId fetchProductId(MongoCollection<Document> collection) {
        Document firstProduct = collection.find().first();
        if (firstProduct != null) {
            return firstProduct.getObjectId("_id");
        }
        System.out.println("No products found in the collection.");
        return null;
    }

    private static void deleteOneDocument(MongoCollection<Document> collection, ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        System.out.println("Number of documents deleted: " + result.getDeletedCount());
    }

    private static void findOneAndDeleteDocument(MongoCollection<Document> collection, ObjectId id) {
        Document deletedDocument = collection.findOneAndDelete(Filters.eq("_id", id));
        if (deletedDocument != null) {
            System.out.println("Deleted document: " + deletedDocument.toJson());
        } else {
            System.out.println("No document found with the specified ID.");
        }
    }

    private static void deleteManyDocuments(MongoCollection<Document> collection, String product_desc) {
        DeleteResult result = collection.deleteMany(Filters.eq("product_desc", product_desc));
        System.out.println("Number of documents deleted: " + result.getDeletedCount());
    }

    private static void dropCollection(MongoDatabase database, String collectionName) {
        database.getCollection(collectionName).drop();
        System.out.println("Collection dropped successfully.");
    }
}
