package co.com.cuemby.mobile.pruebatecnicacuemby.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.com.cuemby.mobile.pruebatecnicacuemby.interfaces.JsonApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {

    private static JsonApi jsonApi;

    public static JsonApi getDataList() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (jsonApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Endpoints.URL_BASE + Endpoints.API_KEY)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            jsonApi = retrofit.create(JsonApi.class);
        }
        return jsonApi;
    }

}
