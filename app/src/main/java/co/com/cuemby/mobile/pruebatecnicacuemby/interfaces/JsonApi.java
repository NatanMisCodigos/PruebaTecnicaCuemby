package co.com.cuemby.mobile.pruebatecnicacuemby.interfaces;

import co.com.cuemby.mobile.pruebatecnicacuemby.model.ImageApi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonApi {

    @GET(".")
    Call<ImageApi> getImageListDefault(@Query("key") String key, @Query("lang") String lang);

    @GET(".")
    Call<ImageApi> getImageListSearch(@Query("key") String key, @Query("lang") String lang,
                                      @Query("q") String q);

    @GET(".")
    Call<ImageApi> getImageListSpinner(@Query("key") String key, @Query("lang") String lang,
                                      @Query("category") String category);

    @GET(".")
    Call<ImageApi> getImageListSearchSpinner(@Query("key") String key, @Query("lang") String lang,
                                       @Query("q") String q, @Query("category") String category);

}
