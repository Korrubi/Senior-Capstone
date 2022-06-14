package com.jjcc.dishdiscovery.activities.spoonacular.listeners;

import com.jjcc.dishdiscovery.activities.spoonacular.models.SimilarRecipe.SimilarRecipeApiResponse;

import java.util.List;

public interface SimilarRecipesListener {
    void didFetch(List<SimilarRecipeApiResponse> response, String message);
    void didError(String message);
}
