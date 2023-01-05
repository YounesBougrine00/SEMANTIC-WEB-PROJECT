package com.example.wsprojetajnakibougrine;

import org.apache.jena.rdf.model.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    private SPARQLQuery sparqlQuery;

    @GetMapping("/query")
    public Statement[] index(Model model) {

        // Définition des données (si nécessaire)
        String object = "PrimosChicken";
        String[] properties = {"openingHours", "servesCuisine", "hasMenu", "starRating"};

        // Exécution de la requête
        Statement[] triplets = sparqlQuery.executeQuery(object, properties);

        // Ajout des résultats au modèle
        model.addAttribute("triplets", triplets);

        return triplets;
    }
}
