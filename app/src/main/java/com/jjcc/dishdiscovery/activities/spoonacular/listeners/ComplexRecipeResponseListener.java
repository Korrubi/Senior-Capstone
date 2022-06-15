package com.jjcc.dishdiscovery.activities.spoonacular.listeners;

import com.jjcc.dishdiscovery.activities.spoonacular.models.complexSearch.ComplexRecipeApiResponse;

public interface ComplexRecipeResponseListener {
    void didFetch(ComplexRecipeApiResponse response, String message);
    void didError(String message);
}
