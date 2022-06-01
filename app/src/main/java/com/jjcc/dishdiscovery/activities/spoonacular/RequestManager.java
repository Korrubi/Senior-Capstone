package com.jjcc.dishdiscovery.activities.spoonacular;

import android.content.Context;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.ComplexRecipeResponseListener;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RandomRecipeResponseListener;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RecipeInformationListener;
import com.jjcc.dishdiscovery.activities.spoonacular.models.RandomRecipeApiResponse;
import com.jjcc.dishdiscovery.activities.spoonacular.models.RecipeInformation.RecipeInformationResponse;
import com.jjcc.dishdiscovery.activities.spoonacular.models.complexSearch.ComplexRecipeApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> cuisine) {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "10", cuisine);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if(!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }



    public void getRecipeInformation(RecipeInformationListener listener, int id) {
        CallRecipeInformation callRecipeInformation = retrofit.create(CallRecipeInformation.class);

        Call<RecipeInformationResponse> call = callRecipeInformation.callRecipeInformation(id, context.getString(R.string.api_key));

        call.enqueue(new Callback<RecipeInformationResponse>() {
            @Override
            public void onResponse(Call<RecipeInformationResponse> call, Response<RecipeInformationResponse> response) {
                if(!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeInformationResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


    public void getComplexRecipes(ComplexRecipeResponseListener listener, List<String> query) {
        CallComplexRecipes callComplexRecipes = retrofit.create(CallComplexRecipes.class);

        Call<ComplexRecipeApiResponse> call = callComplexRecipes.callComplexRecipe(context.getString(R.string.api_key),"10", query);

        call.enqueue(new Callback<ComplexRecipeApiResponse>() {
            @Override
            public void onResponse(Call<ComplexRecipeApiResponse> call, Response<ComplexRecipeApiResponse> response) {
                if(!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<ComplexRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }



    private interface CallComplexRecipes {
        @GET("recipes/complexSearch")
        Call<ComplexRecipeApiResponse> callComplexRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("query") List<String> query
        );
    }

    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    private interface CallRecipeInformation {
        @GET("recipes/{id}/information")
        Call<RecipeInformationResponse> callRecipeInformation(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
