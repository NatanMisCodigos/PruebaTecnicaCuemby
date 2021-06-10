package co.com.cuemby.mobile.pruebatecnicacuemby.presenter;

import android.content.Context;

import co.com.cuemby.mobile.pruebatecnicacuemby.interfaces.ImageInterfaces;
import co.com.cuemby.mobile.pruebatecnicacuemby.model.ImageApi;
import co.com.cuemby.mobile.pruebatecnicacuemby.model.ImageInteractor;

public class ImagePresenter implements ImageInterfaces.Presenter {

    ImageInterfaces.View view;
    ImageInterfaces.Interactor interactor;

    public ImagePresenter(ImageInterfaces.View view, Context context) {
        this.view = view;
        this.interactor = new ImageInteractor(this, context);
    }

    @Override
    public void emptyList(String message) {
        if(view != null)
            view.emptyList(message);
    }

    @Override
    public void showImageList(ImageApi imageList) {
        if(view != null)
            view.showImageList(imageList);
    }

    @Override
    public void showDetailsImage(ImageApi.Hits image) {
        if(view != null)
            view.showDetailsImage(image);
    }

    @Override
    public void getImageListDefault() {
        if(interactor != null)
            interactor.getImageListDefault();
    }

    @Override
    public void getImageListSearch(String query) {
        if(interactor != null)
            interactor.getImageListSearch(query);
    }

    @Override
    public void getImageListSearchSpinner(String query, String category) {
        if(interactor != null)
            interactor.getImageListSearchSpinner(query, category);
    }

    @Override
    public void getImageListSpinner(String category) {
        if(interactor != null)
            interactor.getImageListSpinner(category);
    }
}
