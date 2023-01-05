package com.example.wsprojetajnakibougrine;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.impl.StatementImpl;

import org.springframework.stereotype.Service;

@Service
public class SPARQLQuery {
    public static Statement[] executeQuery(String object, String[] properties) {
        // Connexion à la base de données DBpedia
        String service = "http://dbpedia.org/sparql";
        String queryString =
                "PREFIX dbpedia: <http://dbpedia.org/resource/>" +
                        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                        "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>" +
                        "SELECT ?s ?p ?o WHERE {" +
                        "  ?s ?p ?o." +
                        "  ?s rdf:type dbpedia-owl:Restaurant." +
                        "  ?s rdfs:label \"" + object + "\"@en.";
        for (int i = 0; i < properties.length; i++) {
            queryString += "  ?s dbpedia-owl:" + properties[i] + " ?o" + i + ".";
        }
        queryString += "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec =  QueryExecution.service(service).query(query).build();
        ResultSet results = qexec.execSelect();

        // Récupération des triplets
        Statement[] triplets = new Statement[properties.length];
        int i = 0;
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            Resource s = solution.getResource("s");
            Resource p = solution.getResource("p");
            RDFNode o = solution.get("o");
            Statement triplet = new StatementImpl(s, (Property) p, o);
            System.out.println(triplet);
            triplets[i] = triplet;
            i++;
        }
        System.out.println(triplets);
        for (Statement triplet : triplets) {
            System.out.println(triplet);
        }
        return triplets;
    }
}
