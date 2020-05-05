package com.openclassrooms.entrevoisins.ui.neighbour_profile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourProfileActivity extends AppCompatActivity {

    @BindView(R.id.picture)
    public ImageView mPicture;
    @BindView(R.id.arrow_back)
    public TextView mArrowBack;
    @BindView(R.id.name1)
    public TextView mName1;
    @BindView(R.id.name2)
    public TextView mName2;
    @BindView(R.id.favorite_button)
    public ImageButton mFavoriteButton;
    @BindView(R.id.city)
    public TextView mCity;
    @BindView(R.id.icon_Place)
    public ImageView mIconPlace;
    @BindView(R.id.icon_Phone)
    public ImageView mIconPhone;
    @BindView(R.id.icon_Social)
    public ImageView mIconSocial;
    @BindView(R.id.phone)
    public TextView mPhone;
    @BindView(R.id.social_page)
    public TextView mSocialPage;
    @BindView(R.id.about_me)
    public TextView mAboutMe;
    @BindView(R.id.messageUI)
    public TextView mMessageUI;
    public NeighbourApiService mApiService;

    Neighbour neighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profile);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();


        /** Get neighbour with Extra */
        Intent neighbourProfileActivityIntent = getIntent();
        if (neighbourProfileActivityIntent != null) {
            neighbour = neighbourProfileActivityIntent.getParcelableExtra("neighbour");
            String neighbourName = "";
            neighbourName = neighbour.getName();

            Glide.with(mPicture.getContext())
                    .load(neighbour.getAvatarUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(mPicture);
            mName1.setText(neighbourName);
            mName2.setText(neighbourName);

            updateImageFavorite();

            /** On click set Favorite Neighbour */
            mFavoriteButton.setOnClickListener(v -> {
                mApiService.updateNeighbour(neighbour);
                neighbour.setFavorite(!neighbour.isFavorite());
                updateImageFavorite();
            });


            /** On click back to neighbourList */
            mArrowBack.setOnClickListener(v -> {
                finish();
            });
        }
    }

    public void updateImageFavorite(){
        if (neighbour.isFavorite()) {

            mFavoriteButton.setImageResource(R.drawable.ic_star_black_24dp);
        } else {

            mFavoriteButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }
}

