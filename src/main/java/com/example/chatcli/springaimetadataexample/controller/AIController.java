package com.example.chatcli.springaimetadataexample.controller;

import com.example.chatcli.springaimetadataexample.service.AIService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AIController {
    @Autowired
    AIService aiService;

    @GetMapping("/load")
    public String load() {
        aiService.loaddata();
        return "Product documents Data Loaded";
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam(defaultValue = "battery") String query) {
        List<Document> docs = aiService.search(query);
        List<String> docContents = docs.stream()
                .map(Document::getContent)
                .collect(Collectors.toList());
        return docContents;
    }
}
