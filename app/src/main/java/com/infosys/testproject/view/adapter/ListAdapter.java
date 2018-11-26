package com.infosys.testproject.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.infosys.testproject.R;
import com.infosys.testproject.databinding.CountryDataListItemBinding;
import com.infosys.testproject.service.model.CountryProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LOPA on 11/23/2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<CountryProfile> list;
    final Handler handler = new Handler();
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CountryDataListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.listitem_view,
                        parent, false);
        ListViewHolder viewHolder = new ListViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.mBinding.setCountrydata(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public void setData(final List<CountryProfile> profilelist)
    {
        if(this.list == null) {
            list = profilelist;
            notifyItemRangeInserted(0, profilelist.size());
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return ListAdapter.this.list.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return profilelist.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            String oldItemTitle = ListAdapter.this.list.get(oldItemPosition).getTitle();
                            String newItemTitle = profilelist.get(newItemPosition).getTitle();
                            return oldItemTitle.equals(
                                    newItemTitle);
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            CountryProfile newdata = profilelist.get(newItemPosition);
                            CountryProfile olddata = ListAdapter.this.list.get(oldItemPosition);

                            return newdata.getTitle().equals(olddata.getTitle())
                                    && newdata.getDescription().equals(olddata.getDescription())
                                    && newdata.getImageHref().equals(olddata.getImageHref());
                        }
                    });
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            applyDiffResult(profilelist, result);
                        }
                    });
                }}).start();


        }
    }
   private void applyDiffResult(List<CountryProfile> newItems, DiffUtil.DiffResult diffResult)
   {
       diffResult.dispatchUpdatesTo(ListAdapter.this);
       this.list.clear();
       this.list.addAll(newItems);
   }


    public static class ListViewHolder extends RecyclerView.ViewHolder{

        CountryDataListItemBinding mBinding;
        public ListViewHolder(CountryDataListItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
