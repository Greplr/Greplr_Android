/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       1. Arnav Gupta
 *       2. Abhinav Sinha
 *       3. Prempal Singh
 *       4. Raghav Apoorv
 *       5. Shubham Dokania
 *       6. Yogesh Balan
 *
 *     The source code of this program is confidential and proprietary. If you are not part of the
 *     Greplr Team (one of the above 6 named individuals) you should not be viewing this code.
 *
 *     You should immediately close your copy of code, and destroy the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr.subcategories.events;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.App;
import com.greplr.R;
import com.greplr.adapters.ErrorAdapter;
import com.greplr.adapters.LoaderAdapter;
import com.greplr.api.Api;
import com.greplr.common.utils.Utils;
import com.greplr.models.events.Plays;
import com.greplr.subcategories.UnderSubCategoryFragment;
import com.parse.ParseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raghav on 21/06/15.
 */
public class EventPlayFragment extends UnderSubCategoryFragment {

    public static final String LOG_TAG = "Greplr/Event/Play";

    private RecyclerView mRecyclerView;
    private List<Plays> playList;

    public static EventPlayFragment newInstance() {
        return new EventPlayFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_events_plays;
    }

    @Override
    public String getPageTitle() {
        return "Plays";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_events_play;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG_TAG, "EventPlaysFragment onCreateView");
        return inflater.inflate(R.layout.fragment_events_plays, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_plays);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new LoaderAdapter()));
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

        Api apiHandler = ((App) getActivity().getApplication()).getApiHandler();
        apiHandler.getEventPlays(
                new Callback<List<Plays>>() {

                    @Override
                    public void success(List<Plays> plays, Response response) {
                        Log.d(LOG_TAG, "success" + response.getUrl() + response.getStatus());
                        playList = plays;
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new PlayAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                        sendParseAnalytics("true");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                        mRecyclerView.setAdapter(new RecyclerViewMaterialAdapter(new ErrorAdapter()));
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
                        sendParseAnalytics("false");
                    }
                }
        );
    }

    private void sendParseAnalytics(String success) {
        Map<String, String> params = new HashMap<>();
        params.put("success", success);
        ParseAnalytics.trackEventInBackground("events/plays/search", params);
    }

    public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            CardView v = (CardView) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cardview_play_list_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
            viewHolder.eventTitle.setText(playList.get(i).getEventTitle());
            viewHolder.director.setText(playList.get(i).getDirector());
            viewHolder.length.setText(playList.get(i).getLength());
            viewHolder.actors.setText(playList.get(i).getActors());
            Picasso.with(getActivity()).load(playList.get(i).getBannerURL()).
                    fit().centerCrop().into(viewHolder.playBanner);
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog customDialog = new Dialog(getActivity(), R.style.AppTheme_SearchDialog);
                    customDialog.setContentView(R.layout.dialog_plays_venues);
//                    customDialog.setTitle("Venue");
                    customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getActivity().getResources().getColor(android.R.color.white)));
                    customDialog.setCancelable(true);
                    ListView listView = (ListView) customDialog.findViewById(R.id.listView_plays_venues);
                    listView.setAdapter(new PlaysVenueAdapter(getActivity(), playList.get(i).getArrVenues()));
                    customDialog.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return playList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView eventTitle;
            TextView director;
            TextView length;
            TextView actors;
            ImageView playBanner;
            View view;

            public ViewHolder(CardView v) {
                super(v);

                view = v;
                eventTitle = (TextView) v.findViewById(R.id.play_name);
                director = (TextView) v.findViewById(R.id.Director_name);
                length = (TextView) v.findViewById(R.id.play_length);
                actors = (TextView) v.findViewById(R.id.play_actors);
                playBanner = (ImageView) v.findViewById(R.id.play_banner);
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        TextView director;
        TextView length;
        TextView actors;
        ImageView playBanner;
        View view;

        public ViewHolder(CardView v) {
            super(v);

            view = v;
            eventTitle = (TextView) v.findViewById(R.id.play_name);
            director = (TextView) v.findViewById(R.id.Director_name);
            length = (TextView) v.findViewById(R.id.play_length);
            actors = (TextView) v.findViewById(R.id.play_actors);
            playBanner = (ImageView) v.findViewById(R.id.play_banner);
        }
    }

    public class PlaysVenueAdapter extends BaseAdapter {

        private Activity activity;
        private LayoutInflater inflater;
        private List<Plays.Venues> venueItems;

        public PlaysVenueAdapter(Activity activity, List<Plays.Venues> arrVenues) {
            this.activity = activity;
            this.venueItems = arrVenues;
        }

        @Override
        public int getCount() {
            return venueItems.size();
        }

        @Override
        public Object getItem(int position) {
            return venueItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (inflater == null)
                inflater = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.plays_details_list_item, null);

            TextView venueName = (TextView) convertView.findViewById(R.id.venue_name);
            TextView venueLocation = (TextView) convertView.findViewById(R.id.venue_location);
            TextView venueDistance = (TextView) convertView.findViewById(R.id.venue_distance);
            ImageView venueMapLocation = (ImageView) convertView.findViewById(R.id.iv_venue_location);

            venueName.setText(venueItems.get(position).getVenueName());
            venueLocation.setText(venueItems.get(position).getRegion_strName());
            venueDistance.setText(Utils.friendlyDistance(String.valueOf(Utils.calDistanceFromCoordinates(venueItems.get(position).getVenueLatitude(), venueItems.get(position).getVenueLongitude()))));

            venueMapLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<lat>,<long>?q=" + venueItems.get(position).getVenueLatitude() + ","
                            + venueItems.get(position).getVenueLongitude() + "(" + venueItems.get(position).getVenueName() + ")"));

                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
