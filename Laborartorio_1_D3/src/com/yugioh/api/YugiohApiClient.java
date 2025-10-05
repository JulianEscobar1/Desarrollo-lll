package com.yugioh.api;

import com.yugioh.model.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class YugiohApiClient {

    private static final String RANDOM_CARD_ENDPOINT = "https://db.ygoprodeck.com/api/v7/randomcard.php";

    public YugiohApiClient() {
    }

    public List<Card> getCards() throws IOException, InterruptedException {
        List<Card> cards = new ArrayList<>();
        while(cards.size()<3){
            setCardList(cards);
        }
        return cards;
    }

    private void setCardList(List<Card> cards) throws IOException, InterruptedException {
        HttpClient http = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(RANDOM_CARD_ENDPOINT))
                .GET()
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            JSONObject json = new JSONObject(response.body());
            json.getJSONArray("data").forEach(object ->{
                JSONObject cardJson = (JSONObject) object;
                if(cardJson.getString("type").contains("Monster")) {
                    JSONObject image = cardJson.getJSONArray("card_images").getJSONObject(0);
                    Card card = new Card();
                    card.setName(cardJson.getString("name"));
                    card.setAtk(cardJson.has("atk") && !cardJson.isNull("atk") ? cardJson.getInt("atk") : 0);
                    card.setDef(cardJson.has("def") && !cardJson.isNull("def") ? cardJson.getInt("def") : 0);
                    card.setImageUrl(image.getString("image_url_cropped"));
                    cards.add(card);
                }
            });
        }else {
            throw new IOException("Error al cargar lista de cartas: " + response.statusCode());
        }
    }
}
