package com.stp.stayzilla.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import com.stp.stayzilla.R;
import com.stp.stayzilla.activity.DetailsActivity;
import com.stp.stayzilla.model.CardViewBean;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Halyson on 19/12/14.
 */
public class RecyclerViewCardsAdapter extends RecyclerView.Adapter<RecyclerViewCardsAdapter.ViewHolder> {
    private final JSONArray mListItemsCard;
    private Activity mActivity;

    public RecyclerViewCardsAdapter(Activity activity, JSONArray listItemsCard) {
        this.mListItemsCard = listItemsCard;
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mActivity,LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recycler_view_comp, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
        final JSONObject itemCardView = (JSONObject)mListItemsCard.get(position);

             // Split path into segments
            String segments[] = itemCardView.getString("imageURL").split("_");
            segments[0] = "400" ; segments[1] = "300"; segments[2] = segments[2].substring(segments[2].lastIndexOf("."));
            String baseURL = itemCardView.getString("imageURL").substring(0, itemCardView.getString("imageURL").indexOf("_"));
            String imageURL= baseURL+'_'+segments[0]+'_'+segments[1]+segments[2];

            // To escape from small images
            if(imageURL.endsWith(".gif")) imageURL="http://img4.stayzilla.com/image/hotel/137869_400_300.jpg";
            holder.itemView.setTag(itemCardView);
            Picasso.with(holder.imageView.getContext())
                    .load(imageURL)
                    .error(R.drawable.placeholder_card_view)
                    .placeholder(R.drawable.placeholder_card_view)
                    .into(holder.imageView);

            holder.hotelNameView.setText(itemCardView.getString("displayName"));
        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public int getItemCount() {
        return mListItemsCard.length();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        private Activity mActivity;
        private TextView hotelNameView;

        public ViewHolder(Activity activity , View itemView) {
            super(itemView);
            mActivity = activity;
            imageView = (ImageView) itemView.findViewById(R.id.material_com_card_view_image);
            hotelNameView = (TextView) itemView.findViewById(R.id.hotel_name_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, DetailsActivity.class));
                }
            });
        }

    }
}
