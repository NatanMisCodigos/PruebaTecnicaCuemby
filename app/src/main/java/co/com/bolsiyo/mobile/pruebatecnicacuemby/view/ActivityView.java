package co.com.bolsiyo.mobile.pruebatecnicacuemby.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import co.com.bolsiyo.mobile.pruebatecnicacuemby.R;
import co.com.bolsiyo.mobile.pruebatecnicacuemby.interfaces.ImageInterfaces;
import co.com.bolsiyo.mobile.pruebatecnicacuemby.model.ImageAdapter;
import co.com.bolsiyo.mobile.pruebatecnicacuemby.model.ImageApi;
import co.com.bolsiyo.mobile.pruebatecnicacuemby.presenter.ImagePresenter;
import co.com.bolsiyo.mobile.pruebatecnicacuemby.rest.Utils;

public class ActivityView extends Activity implements ImageInterfaces.View {

    private Context context = this;
    private EditText editTextSearch;
    private RecyclerView recyclerViewSearchResults;
    private ImageInterfaces.Presenter presenter = new ImagePresenter(this, context);
    private ImageAdapter imageAdapter;
    private ImageApi usersList;
    private ImageView busqueda;
    private ConstraintLayout constraintSplash;
    private ConstraintLayout constraintLocation;
    private LottieAnimationView imageEmpty;
    private LottieAnimationView error404;
    private TextView messageEmpty;
    private Spinner spinnercategories;

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
    public void showImageList(ImageApi imageList) {
        this.usersList = imageList;
        error404.setVisibility(View.GONE);
        imageEmpty.setVisibility(View.GONE);
        messageEmpty.setVisibility(View.GONE);
        recyclerViewSearchResults.setVisibility(View.VISIBLE);
        imageAdapter = new ImageAdapter(this.usersList, presenter);
        recyclerViewSearchResults.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerViewSearchResults.setAdapter(imageAdapter);
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
    @Override
    public void showDetailsImage(ImageApi.Hits image) {
        Dialog dialogImage = new Dialog(this);
        dialogImage.setContentView(R.layout.image_details);
        dialogImage.setCancelable(false);

        ImageView cerrar = dialogImage.findViewById(R.id.cerrar);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImage.dismiss();
            }
        });

        ImageView imagen = dialogImage.findViewById(R.id.image);
        Picasso.get().load(image.getLargeImageURL()).into(imagen);

        TextView tags = dialogImage.findViewById(R.id.tags_image);
        tags.setText( image.getTags().replace(",", "\n") );

        TextView views = dialogImage.findViewById(R.id.views_image);
        views.setText( "" + image.getViews() );

        TextView like = dialogImage.findViewById(R.id.like_image);
        like.setText( "" + image.getLikes() );

        Utils.dialogSize(dialogImage);

        dialogImage.show();
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
                searchWithImage();
            }
        });

        initSpinner();
        presenter.getImageListDefault();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showConstrainLocation();
            }
        }, 2500);
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
                    searchWithImage();
                    hideSoftKeyboard();
                    return true;
                }
                return false;
            }
        });
    }

    /**********************************************
     * Enlazo el spinner de categorias y le doy
     * un comportamiento
     * *******************************************/
    private void initSpinner(){
        spinnercategories = findViewById(R.id.spinner_categorias);
        spinnercategories.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, Utils.categories));
        spinnercategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(editTextSearch.getText().toString().trim().isEmpty()){
                    presenter.getImageListSpinner(spinnercategories.getSelectedItem().toString());
                    return;
                }

                presenter.getImageListSearchSpinner(editTextSearch.getText().toString().trim(),
                        spinnercategories.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**********************************************
     * Con este metodo, realizo ciertas acciones
     * que necesito cuando el usuario presione
     * la imagen de busqueda o la tecla enter
     * *******************************************/
    private void searchWithImage(){
        if(editTextSearch.getText().toString().trim().isEmpty()){
            presenter.getImageListSpinner(spinnercategories.getSelectedItem().toString());
            return;
        }
        if(spinnercategories.getSelectedItemPosition() == 0){
            presenter.getImageListSearch(editTextSearch.getText().toString().trim());
            return;
        }
        presenter.getImageListSearchSpinner(editTextSearch.getText().toString().trim(),
                spinnercategories.getSelectedItem().toString());
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