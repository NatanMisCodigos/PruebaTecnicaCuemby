package co.com.cuemby.mobile.pruebatecnicacuemby.model;

import android.content.Context;

import co.com.cuemby.mobile.pruebatecnicacuemby.interfaces.ImageInterfaces;
import co.com.cuemby.mobile.pruebatecnicacuemby.rest.ApiAdapter;
import co.com.cuemby.mobile.pruebatecnicacuemby.rest.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageInteractor implements ImageInterfaces.Interactor, Callback<ImageApi> {

    private ImageInterfaces.Presenter presenter;
    private ImageApi imageApiList;
    private Context context;

    public ImageInteractor(ImageInterfaces.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public void getImageListDefault() {
        Call<ImageApi> call = ApiAdapter.getDataList().getImageListDefault(Utils.keyApi, Utils.langEs);
        System.out.println("REQUEST: " + call.request().toString());
        call.enqueue(this);
    }

    @Override
    public void getImageListSearch(String query) {
        Call<ImageApi> call = ApiAdapter.getDataList().getImageListSearch(Utils.keyApi,
                Utils.langEs, query );
        call.enqueue(this);
    }

    @Override
    public void getImageListSearchSpinner(String query, String category) {
        Call<ImageApi> call = ApiAdapter.getDataList().getImageListSearchSpinner(Utils.keyApi,
                Utils.langEs, query, category );
        call.enqueue(this);
    }

    @Override
    public void getImageListSpinner(String category) {
        Call<ImageApi> call = ApiAdapter.getDataList().getImageListSpinner(Utils.keyApi,
                Utils.langEs, category );
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ImageApi> call, Response<ImageApi> response) {
        System.out.println("MENSAJE: " + response.toString());
        if (!response.isSuccessful()) {
            presenter.emptyList("Error con el servidor:\nVerifique conexion a internet");
        } else {
            imageApiList = response.body();

            if(imageApiList.getHits().size() == 0){
                presenter.emptyList("Sin resultados");
                return;
            }

            if (imageApiList != null) {
                presenter.showImageList((ImageApi) imageApiList);
            } else {
                presenter.emptyList("Sin resultados");
            }
        }
    }

    @Override
    public void onFailure(Call<ImageApi> call, Throwable t) {
        presenter.emptyList("Error con el servidor:\nVerifique conexion a internet");
    }
}
