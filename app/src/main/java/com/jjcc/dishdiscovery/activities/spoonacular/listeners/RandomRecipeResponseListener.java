package com.jjcc.dishdiscovery.activities.spoonacular.listeners;

import com.jjcc.dishdiscovery.activities.spoonacular.models.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
