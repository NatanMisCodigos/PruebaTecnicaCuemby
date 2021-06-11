package co.com.cuemby.mobile.pruebatecnicacuemby.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import co.com.cuemby.mobile.pruebatecnicacuemby.R;
import co.com.cuemby.mobile.pruebatecnicacuemby.interfaces.InterfacesPublicas;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.UserViewHolder> {

    private HeroApi imageList;
    private static InterfacesPublicas.Presenter presenter;

    public HeroAdapter(HeroApi imageList, InterfacesPublicas.Presenter presenter) {
        this.imageList = imageList;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.hero_items, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bindData(imageList.getResults().get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.getResults().size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView cardRight, cardLeft;

        public UserViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_hero);
            cardLeft = itemView.findViewById(R.id.card_red);
            cardRight = itemView.findViewById(R.id.card_yellow);
        }

        void bindData(final HeroApi.Results results) {
            name.setText( results.getName() );
            cardLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.addHeroLeft(results);
                }
            });
            cardRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.addHeroRight(results);
                }
            });
        }
    }
}
