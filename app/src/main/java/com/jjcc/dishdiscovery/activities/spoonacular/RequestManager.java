package com.jjcc.dishdiscovery.activities.spoonacular;

import android.content.Context;

import com.jjcc.dishdiscovery.R;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.ComplexRecipeResponseListener;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.InstructionsListener;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RandomRecipeResponseListener;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.RecipeInformationListener;
import com.jjcc.dishdiscovery.activities.spoonacular.listeners.SimilarRecipesListener;
import com.jjcc.dishdiscovery.activities.spoonacular.models.AnalyzedInstuctions.InstructionResponse;
import com.jjcc.dishdiscovery.activities.spoonacular.models.RandomRecipeApiResponse;
import com.jjcc.dishdiscovery.activities.spoonacular.models.RecipeInformation.RecipeInformationResponse;
import com.jjcc.dishdiscovery.activities.spoonacular.models.SimilarRecipe.SimilarRecipeApiResponse;
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


    public void getComplexRecipes(ComplexRecipeResponseListener listener,  List<String> query, int offset) {
        CallComplexRecipes callComplexRecipes = retrofit.create(CallComplexRecipes.class);

        Call<ComplexRecipeApiResponse> call = callComplexRecipes.callComplexRecipe(context.getString(R.string.api_key),"10", query, offset);

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

    public void getComplexRecipesRecommend(ComplexRecipeResponseListener listener,  List<String> intolerance, List<String> diet, List<String> cuisine, int offset) {
        CallComplexRecipesRecommend callComplexRecipesRecommend = retrofit.create(CallComplexRecipesRecommend.class);

        Call<ComplexRecipeApiResponse> call = callComplexRecipesRecommend.callComplexRecipeRecommend(context.getString(R.string.api_key),"5", intolerance, diet, cuisine, offset );

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

    public void getSimilarRecipes(SimilarRecipesListener listener, int id) {
        CallSimilar callSimilar = retrofit.create(CallSimilar.class);

        Call<List<SimilarRecipeApiResponse>> call = callSimilar.callSimilar(id, "3", context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeApiResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipeApiResponse>> call, Response<List<SimilarRecipeApiResponse>> response) {
                if(!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipeApiResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getInstructions(InstructionsListener listener, int id){
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);

        Call<List<InstructionResponse>> call = callInstructions.callInstructions(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionResponse>> call, Response<List<InstructionResponse>> response) {
                if(!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }



    private interface CallComplexRecipes {
        @GET("recipes/complexSearch")
        Call<ComplexRecipeApiResponse> callComplexRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("query") List<String> query,
                @Query("offset") int offset
        );
    }

    private interface CallComplexRecipesRecommend {
        @GET("recipes/complexSearch")
        Call<ComplexRecipeApiResponse> callComplexRecipeRecommend(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("intolerances") List<String> intolerances,
                @Query("diet") List<String> diet,
                @Query("cuisine") List<String> cuisine,
                @Query("offset") int offset
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

    private interface CallSimilar{
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipeApiResponse>> callSimilar(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallInstructions{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionResponse>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
