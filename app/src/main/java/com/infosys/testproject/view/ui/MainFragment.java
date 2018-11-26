package com.infosys.testproject.view.ui;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infosys.testproject.R;
import com.infosys.testproject.databinding.FragmentListBinding;
import com.infosys.testproject.service.model.CountryProfile;
import com.infosys.testproject.view.adapter.ListAdapter;

import java.util.ArrayList;

/**
 * Created by LOPA on 11/23/2018.
 */

public class MainFragment extends Fragment{

    ListAdapter mAdapter;
    FragmentListBinding binding ;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ListAdapter();
        binding.recyclerView.setAdapter(mAdapter);
        binding.setIsLoading(true);
        
    }

    public void setData(ArrayList<CountryProfile> list)
    {
        if(list != null && list.size() != 0) {
            mAdapter.setData(list);
            binding.setIsLoading(false);
        }else {
            //show toast for no data
            binding.loadingData.setText(getActivity().getResources().getString(R.string.nodata_found_msg));
        }

    }



}
