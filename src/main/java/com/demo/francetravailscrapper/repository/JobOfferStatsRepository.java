package com.demo.francetravailscrapper.repository;

import com.demo.francetravailscrapper.repository.models.JobOfferDocument;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Repository
public class JobOfferStatsRepository {

    private final MongoTemplate mongoTemplate;

    public JobOfferStatsRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Map<String, Integer> countByTypeContrat() {
        return aggregateField("typeContrat");
    }

    public Map<String, Integer> countByEntreprise() {
        return aggregateField("entreprise");
    }

    public Map<String, Integer> countByPays() {
        return aggregateField("pays");
    }

    private Map<String, Integer> aggregateField(final String fieldName) {
        Aggregation aggregation = Aggregation.newAggregation(
                group(fieldName).count().as("count"),
                project("count").and("_id").as("value")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation, JobOfferDocument.class, Document.class
        );

        Map<String, Integer> stats = new HashMap<>();
        for (Document doc : results) {
            stats.put(doc.getString("value"), doc.getInteger("count"));
        }

        return stats;
    }
}
