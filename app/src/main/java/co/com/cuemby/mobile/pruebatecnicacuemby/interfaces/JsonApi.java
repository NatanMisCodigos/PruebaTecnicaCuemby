package co.com.cuemby.mobile.pruebatecnicacuemby.interfaces;

import co.com.cuemby.mobile.pruebatecnicacuemby.model.HeroApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApi {

    @GET(".")
    Call<HeroApi> getImageListDefault(@Query("key") String key, @Query("lang") String lang);

    @GET("search/{search}")
    Call<HeroApi> getHeroSearch(@Path("search") String search);

    @GET(".")
    Call<HeroApi> getImageListSpinner(@Query("key") String key, @Query("lang") String lang,
                                      @Query("category") String category);

    @GET(".")
    Call<HeroApi> getImageListSearchSpinner(@Query("key") String key, @Query("lang") String lang,
                                            @Query("q") String q, @Query("category") String category);

}
