package com.greplr.subcategories.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.greplr.MainActivity;
import com.greplr.R;
import com.greplr.adapters.NumberedAdapter;
import com.greplr.api.Api;
import com.greplr.models.events.Movies;
import com.greplr.subcategories.UnderSubCategoryFragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by raghav on 21/06/15.
 */
public class EventMovieFragment extends UnderSubCategoryFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Movies> movieList;

    public static EventMovieFragment newInstance() {
        return new EventMovieFragment();
    }

    @Override
    public int getFragmentIcon() {
        return R.drawable.cardicon_food_cafe;
    }

    @Override
    public String getPageTitle() {
        return "Movies";
    }

    @Override
    public int getBackgroundResId() {
        return R.drawable.background_food_cafe;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Greplr", "EventMovieFragment onCreateView");

        View rootView =  inflater.inflate(R.layout.fragment_events_movies, container, false);

        Api apiHandler = ((MainActivity) getActivity()).getApiHandler();
        apiHandler.getEventMovies("",
                new Callback<List<Movies>>() {

                    @Override
                    public void success(List<Movies> movies, Response response) {
                        Log.d("Greplr", "success" + response.getUrl() + response.getStatus());
                        movieList = movies;
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Greplr", "failure" + error.getUrl() + error.getMessage());

                    }
                }
        );

        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_movies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewMaterialAdapter(new NumberedAdapter(10));
        mRecyclerView.setAdapter(mAdapter);
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);

    }
}
