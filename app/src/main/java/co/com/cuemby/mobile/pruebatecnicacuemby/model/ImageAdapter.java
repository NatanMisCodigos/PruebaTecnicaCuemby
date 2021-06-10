package co.com.cuemby.mobile.pruebatecnicacuemby.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import co.com.cuemby.mobile.pruebatecnicacuemby.R;
import co.com.cuemby.mobile.pruebatecnicacuemby.interfaces.ImageInterfaces;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.UserViewHolder> {

    private ImageApi imageList;
    private static ImageInterfaces.Presenter presenter;

    public ImageAdapter(ImageApi imageList, ImageInterfaces.Presenter presenter) {
        this.imageList = imageList;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.image_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bindData(imageList.getHits().get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.getHits().size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;

        public UserViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenUrl);
        }

        void bindData(final ImageApi.Hits hits) {
            if(!hits.getUserImageURL().isEmpty())
                Picasso.get().load(hits.getUserImageURL()).into(imagen);
            imagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.showDetailsImage(hits);
                }
            });
        }
    }
}
