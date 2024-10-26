package com.example.chatcli.springaimetadataexample.service;

import com.example.chatcli.springaimetadataexample.model.Product;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AIService {
    @Autowired
    VectorStore vectorStore;

    @Value("classpath:products.json")
    Resource resource;

    public List<Document> loaddata() {
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> documents = readAndPrintJsonFile();
        // Sleep for 1 second
        for (Document document : documents) {
            List <Document> splitteddocs = textSplitter.split(document);
            try {
                vectorStore.add(splitteddocs);
                System.out.println("Added document: " + document.getContent());
                Thread.sleep(20000);
            } catch (
                    InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        System.out.println("Transformed documents: " + documents);
        return documents;
    }

    public List<Document> search(String query) {
        //List<Document> results = vectorStore.similaritySearch(SearchRequest.query(query).withTopK(3));
        FilterExpressionBuilder filter = new FilterExpressionBuilder();
        List<Document> results = vectorStore.similaritySearch(SearchRequest.query(query)
                .withTopK(3)
                .withFilterExpression(filter.eq("brand", "Apple").build()));

        return results;
    }

    public List<Document> readAndPrintJsonFile() {
        List<Document> documents = new ArrayList<>();
        List<Product> products = getProducts();

        for (Product product : products) {
            Document document = new Document(
                    product.getBrand() + " " + product.getDescription(),
                    Map.of("product_name", product.getProductName(),
                            "brand", product.getBrand())
            );
            documents.add(document);
        }

        return documents;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try (InputStream inputStream = resource.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            for (JsonNode node : jsonNode) {
                Product product = objectMapper.treeToValue(node, Product.class);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
