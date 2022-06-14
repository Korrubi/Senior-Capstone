package com.jjcc.dishdiscovery.activities.spoonacular.listeners;

import com.jjcc.dishdiscovery.activities.spoonacular.models.AnalyzedInstuctions.InstructionResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionResponse> response, String message);
    void didError(String message);
}
