package co.com.cuemby.mobile.pruebatecnicacuemby.model;

import android.content.Context;

import co.com.cuemby.mobile.pruebatecnicacuemby.interfaces.InterfacesPublicas;
import co.com.cuemby.mobile.pruebatecnicacuemby.rest.ApiAdapter;
import co.com.cuemby.mobile.pruebatecnicacuemby.rest.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeroInteractor implements InterfacesPublicas.Interactor, Callback<HeroApi> {

    private InterfacesPublicas.Presenter presenter;
    private HeroApi heroApiList;
    private Context context;

    public HeroInteractor(InterfacesPublicas.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    public void getSearchHero(String search) {
        Call<HeroApi> call = ApiAdapter.getDataList().getHeroSearch( search );
        System.out.println("REQUEST: " + call.request().toString());
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<HeroApi> call, Response<HeroApi> response) {
        if (!response.isSuccessful()) {
            presenter.emptyList("Error con el servidor:\nVerifique conexion a internet");
        } else {
            heroApiList = response.body();
            if(heroApiList.getResults() == null){
                presenter.emptyList("Sin resultados\nIntenta ser menos especifico");
                return;
            }

            presenter.showImageList((HeroApi) heroApiList);
        }
    }

    @Override
    public void onFailure(Call<HeroApi> call, Throwable t) {
        presenter.emptyList("Error con el servidor:\nVerifique conexion a internet");
    }
}
