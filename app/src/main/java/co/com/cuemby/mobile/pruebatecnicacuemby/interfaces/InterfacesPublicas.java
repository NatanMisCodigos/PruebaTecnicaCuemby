package co.com.cuemby.mobile.pruebatecnicacuemby.interfaces;


import co.com.cuemby.mobile.pruebatecnicacuemby.model.HeroApi;

public interface InterfacesPublicas {

    interface View{
        void showImageList(HeroApi imageList);
        void emptyList(String message);
        void addHeroLeft(HeroApi.Results hero);
        void addHeroRight(HeroApi.Results hero);
    }

    interface Interactor{
        void getSearchHero(String search);
    }

    interface Presenter{
        void emptyList(String message);
        void showImageList(HeroApi imageList);
        void addHeroRight(HeroApi.Results hero);
        void addHeroLeft(HeroApi.Results hero);
        void getSearchHero(String search);
    }

}
