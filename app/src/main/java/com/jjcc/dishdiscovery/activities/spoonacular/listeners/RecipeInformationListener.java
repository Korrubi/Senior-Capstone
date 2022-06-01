package com.jjcc.dishdiscovery.activities.spoonacular.listeners;

import com.jjcc.dishdiscovery.activities.spoonacular.models.RecipeInformation.RecipeInformationResponse;

public interface RecipeInformationListener {
    void didFetch(RecipeInformationResponse response, String message);
    void didError(String message);
}
