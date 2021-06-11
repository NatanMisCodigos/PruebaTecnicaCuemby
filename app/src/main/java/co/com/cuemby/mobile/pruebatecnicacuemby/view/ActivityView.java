package co.com.cuemby.mobile.pruebatecnicacuemby.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import co.com.cuemby.mobile.pruebatecnicacuemby.R;
import co.com.cuemby.mobile.pruebatecnicacuemby.interfaces.InterfacesPublicas;
import co.com.cuemby.mobile.pruebatecnicacuemby.model.HeroAdapter;
import co.com.cuemby.mobile.pruebatecnicacuemby.model.HeroApi;
import co.com.cuemby.mobile.pruebatecnicacuemby.presenter.Presenter;
import co.com.cuemby.mobile.pruebatecnicacuemby.rest.Utils;

public class ActivityView extends Activity implements InterfacesPublicas.View {

    private Context context = this;
    private EditText editTextSearch;
    private RecyclerView recyclerViewSearchResults;
    private InterfacesPublicas.Presenter presenter = new Presenter(this, context);
    private HeroAdapter heroAdapter;
    private ImageView busqueda;
    private ConstraintLayout constraintSplash;
    private ConstraintLayout constraintLocation;
    private LottieAnimationView imageEmpty;
    private LottieAnimationView error404;
    private TextView messageEmpty;
    // Elementos del heroe de la izquierda
    private TextView nameHeroLeft;
    private LottieAnimationView imageHeroLeft;
    private HeroApi.Results heroLeft;
    // Elementos del heroe de la derecha
    private TextView nameHeroRight;
    private LottieAnimationView imageHeroRight;
    private HeroApi.Results heroRight;
    // Elemento del cardView para ver el enfrentamiento
    private CardView cardViewFight;
    private LottieAnimationView viewFight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        initItems();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utils.hideNotificationBar(this);
    }

    /**********************************************
     * Modifico el estado de algunos elmentos, para
     * mostrar al usuario los resultados de la
     * satisfactoria busqueda
     * *******************************************/
    @Override
    public void showImageList(HeroApi imageList) {
        error404.setVisibility(View.GONE);
        imageEmpty.setVisibility(View.GONE);
        messageEmpty.setVisibility(View.GONE);
        recyclerViewSearchResults.setVisibility(View.VISIBLE);
        heroAdapter = new HeroAdapter(imageList, presenter);
        recyclerViewSearchResults.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewSearchResults.setAdapter(heroAdapter);
    }

    /**********************************************
     * En caso de no haber recibido una respuesta
     * satisfactoria, oculto y muestro ciertos
     * elementos de la activity, para notifcar
     * al usuario lo que ocurrio
     * *******************************************/
    @Override
    public void emptyList(String message) {
        messageEmpty.setText(message);
        imageEmpty.setVisibility(View.VISIBLE);
        messageEmpty.setVisibility(View.VISIBLE);
        recyclerViewSearchResults.setVisibility(View.GONE);
    }

    /**********************************************
     * Aqui recibo los datos que necesito para
     * mostrar los detalles de la imagen
     * seleccionada por el usuario, para luego
     * mostrarlos en un dialog
     * *******************************************/
    void showFight() {
        Dialog dialogHeroFight = new Dialog(this);
        dialogHeroFight.setContentView(R.layout.fight_hero);
        dialogHeroFight.setCancelable(false);

        ImageView close = dialogHeroFight.findViewById(R.id.cerrar);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHeroFight.dismiss();
            }
        });

        // Elementos del heroe izquierdo
        CardView cardHeroLeft = dialogHeroFight.findViewById(R.id.dialog_hero_left);
        TextView nameHeroLeft = dialogHeroFight.findViewById(R.id.dialog_name_hero_left);
        nameHeroLeft.setText( this.heroLeft.getName() );
        ImageView imageHeroLeft = dialogHeroFight.findViewById(R.id.dialog_image_hero_left);
        Picasso.get().load( this.heroLeft.getImage().getUrl() ).into( imageHeroLeft );
        TextView speedHeroLeft = dialogHeroFight.findViewById(R.id.speed_hero_left);
        speedHeroLeft.setText( "Speed: " + this.heroLeft.getPowerstats().getSpeed() );
        TextView powerHeroLeft = dialogHeroFight.findViewById(R.id.power_hero_left);
        powerHeroLeft.setText( "Power: " + this.heroLeft.getPowerstats().getPower() );

        // Elementos del heroe derecho
        CardView cardHeroRight = dialogHeroFight.findViewById(R.id.dialog_hero_right);
        TextView nameHeroRight = dialogHeroFight.findViewById(R.id.dialog_name_hero_right);
        nameHeroRight.setText( this.heroRight.getName() );
        ImageView imageHeroRight = dialogHeroFight.findViewById(R.id.dialog_image_hero_right);
        Picasso.get().load( this.heroRight.getImage().getUrl() ).into( imageHeroRight );
        TextView speedHeroRight = dialogHeroFight.findViewById(R.id.speed_hero_right);
        speedHeroRight.setText( "Speed: " + this.heroRight.getPowerstats().getSpeed() );
        TextView powerHeroRight = dialogHeroFight.findViewById(R.id.power_hero_right);
        powerHeroRight.setText( "Power: " + this.heroRight.getPowerstats().getPower() );

        // Speed y Power
        TextView resulFight = dialogHeroFight.findViewById(R.id.result_fight);
        CardView fightBySpeed = dialogHeroFight.findViewById(R.id.dialog_card_speed);
        fightBySpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Integer.parseInt( heroLeft.getPowerstats().getSpeed()) >
                        Integer.parseInt( heroRight.getPowerstats().getSpeed()) ){
                    cardHeroLeft.setCardBackgroundColor( Color.parseColor("#C4FA95") );
                    cardHeroRight.setCardBackgroundColor( Color.parseColor("#FFFFFF") );
                    resulFight.setText( "Gana " + heroLeft.getName() +"\nen Velocidad" );
                    return;
                }
                if( Integer.parseInt( heroLeft.getPowerstats().getSpeed()) <
                        Integer.parseInt( heroRight.getPowerstats().getSpeed()) ){
                    cardHeroLeft.setCardBackgroundColor( Color.parseColor("#FFFFFF") );
                    cardHeroRight.setCardBackgroundColor( Color.parseColor("#C4FA95") );
                    resulFight.setText( "Gana " + heroRight.getName() +"\nen Velocidad" );
                    return;
                }
                cardHeroLeft.setCardBackgroundColor( Color.parseColor("#FFFEB7") );
                cardHeroRight.setCardBackgroundColor( Color.parseColor("#FFFEB7") );
                resulFight.setText( "En velocidad son iguales" );
            }
        });
        CardView fightByPower = dialogHeroFight.findViewById(R.id.dialog_card_power);
        fightByPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Integer.parseInt( heroLeft.getPowerstats().getPower()) >
                        Integer.parseInt( heroRight.getPowerstats().getPower()) ){
                    cardHeroLeft.setCardBackgroundColor( Color.parseColor("#C4FA95") );
                    cardHeroRight.setCardBackgroundColor( Color.parseColor("#FFFFFF") );
                    resulFight.setText( "Gana " + heroLeft.getName() +"\nen Poder" );
                    return;
                }
                if( Integer.parseInt( heroLeft.getPowerstats().getPower()) <
                        Integer.parseInt( heroRight.getPowerstats().getPower()) ){
                    cardHeroLeft.setCardBackgroundColor( Color.parseColor("#FFFFFF") );
                    cardHeroRight.setCardBackgroundColor( Color.parseColor("#C4FA95") );
                    resulFight.setText( "Gana " + heroRight.getName() +"\nen Poder" );
                    return;
                }
                cardHeroLeft.setCardBackgroundColor( Color.parseColor("#FFFEB7") );
                cardHeroRight.setCardBackgroundColor( Color.parseColor("#FFFEB7") );
                resulFight.setText( "En poder sin duda son iguales" );
            }
        });

        Utils.dialogSize(dialogHeroFight);
        dialogHeroFight.show();
    }

    @Override
    public void addHeroLeft(HeroApi.Results hero) {
        this.heroLeft = hero;

        if(this.heroLeft.getPowerstats().getSpeed().equals("null"))
            this.heroLeft.getPowerstats().setSpeed("0");
        if(this.heroLeft.getPowerstats().getPower().equals("null"))
            this.heroLeft.getPowerstats().setPower("0");

        nameHeroLeft.setText( this.heroLeft.getName() );
        imageHeroLeft.pauseAnimation();
        Picasso.get().load( this.heroLeft.getImage().getUrl() ).into(imageHeroLeft);
        if(this.heroRight != null)
            cardViewFight.setVisibility(View.VISIBLE);
    }

    @Override
    public void addHeroRight(HeroApi.Results hero) {
        this.heroRight = hero;

        if(this.heroRight.getPowerstats().getSpeed().equals("null"))
            this.heroRight.getPowerstats().setSpeed("0");
        if(this.heroRight.getPowerstats().getPower().equals("null"))
            this.heroRight.getPowerstats().setPower("0");

        nameHeroRight.setText( this.heroRight.getName() );
        imageHeroRight.pauseAnimation();
        Picasso.get().load( this.heroRight.getImage().getUrl() ).into(imageHeroRight);
        if(this.heroLeft != null)
            cardViewFight.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**********************************************
     * Metodo que se ejecuta con el onCreate() para
     * enlazar elementos que se usaran a lo largo de
     * la vida util de la aplicacion
     * *******************************************/
    private void initItems() {

        initEditTextSearch();
        // Elementos del heroe de la izquierda
        nameHeroLeft = findViewById(R.id.dialog_name_hero_left);
        imageHeroLeft = findViewById(R.id.dialog_image_hero_left);
        // Elementos del heroe de la derecha
        nameHeroRight = findViewById(R.id.dialog_name_hero_right);
        imageHeroRight = findViewById(R.id.dialog_image_hero_right);
        // CardView para ver la pelea
        cardViewFight = findViewById(R.id.cardViewFight);
        viewFight = findViewById(R.id.view_fight);
        viewFight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFight();
            }
        });

        constraintSplash = findViewById(R.id.constrain_splash);
        constraintLocation = findViewById(R.id.constrain_location);
        imageEmpty = findViewById(R.id.image_empty);
        error404 = findViewById(R.id.error);
        messageEmpty = findViewById(R.id.message_empty);
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        busqueda = findViewById(R.id.busqueda);
        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                presenter.getSearchHero( editTextSearch.getText().toString().trim() );
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showConstrainLocation();
            }
        }, 4700);
    }

    /**********************************************
     * Con este metodo enlazo el editText de
     * busqueda y le doy un comportamiento
     * *******************************************/
    private void initEditTextSearch(){

        editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideSoftKeyboard();
                    presenter.getSearchHero( editTextSearch.getText().toString().trim() );
                    return true;
                }
                return false;
            }
        });
    }

    /**********************************************
     * Metodo que se ejecuta despues de haber
     * mostrado por unos segundos un splash
     * screen
     * *******************************************/
    void showConstrainLocation(){
        constraintSplash.setVisibility(View.GONE);
        constraintLocation.setVisibility(View.VISIBLE);
    }

    public void hideSoftKeyboard() {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

}