package co.com.cuemby.mobile.pruebatecnicacuemby.presenter;

import android.content.Context;

import co.com.cuemby.mobile.pruebatecnicacuemby.interfaces.InterfacesPublicas;
import co.com.cuemby.mobile.pruebatecnicacuemby.model.HeroApi;
import co.com.cuemby.mobile.pruebatecnicacuemby.model.HeroInteractor;

public class Presenter implements InterfacesPublicas.Presenter {

    InterfacesPublicas.View view;
    InterfacesPublicas.Interactor interactor;

    public Presenter(InterfacesPublicas.View view, Context context) {
        this.view = view;
        this.interactor = new HeroInteractor(this, context);
    }

    @Override
    public void emptyList(String message) {
        if(view != null)
            view.emptyList(message);
    }

    @Override
    public void showImageList(HeroApi imageList) {
        if(view != null)
            view.showImageList(imageList);
    }

    @Override
    public void addHeroRight(HeroApi.Results hero) {
        if(view != null)
            view.addHeroRight(hero);
    }

    @Override
    public void addHeroLeft(HeroApi.Results hero) {
        if(view != null)
            view.addHeroLeft(hero);
    }

    @Override
    public void getSearchHero(String search) {
        if(interactor != null)
            interactor.getSearchHero(search);
    }
}
