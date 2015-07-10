package com.greplr.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.greplr.R;
import com.greplr.common.ui.GlassCardView;

/**
 * Created by prempal on 10/7/15.
 */
public class LoaderAdapter extends RecyclerView.Adapter<LoaderAdapter.ViewHolder> {

    @Override
    public LoaderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GlassCardView v = (GlassCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_loader, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final LoaderAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private GlassCardView cardView;

        public ViewHolder(GlassCardView v) {
            super(v);
            cardView = v;
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }
}