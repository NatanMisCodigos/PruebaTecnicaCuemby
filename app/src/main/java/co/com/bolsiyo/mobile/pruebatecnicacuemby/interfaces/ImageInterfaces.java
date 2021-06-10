package co.com.bolsiyo.mobile.pruebatecnicacuemby.interfaces;

import co.com.bolsiyo.mobile.pruebatecnicabolsiyo.model.ImageApi;

public interface ImageInterfaces {

    interface View{
        void showImageList(ImageApi imageList);
        void emptyList(String message);
        void showDetailsImage(ImageApi.Hits image);
    }

    interface Interactor{
        void getImageListDefault();
        void getImageListSearch(String query);
        void getImageListSearchSpinner(String query, String category);
        void getImageListSpinner(String category);
    }

    interface Presenter{
        void emptyList(String message);
        void showImageList(ImageApi imageList);
        void showDetailsImage(ImageApi.Hits image);
        void getImageListDefault();
        void getImageListSearch(String query);
        void getImageListSearchSpinner(String query, String category);
        void getImageListSpinner(String category);
    }

}
