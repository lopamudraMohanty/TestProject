package com.infosys.testproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.infosys.testproject.service.model.Country;
import com.infosys.testproject.service.model.CountryProfile;
import com.infosys.testproject.utility.Util;
import com.infosys.testproject.view.adapter.ListAdapter;
import com.infosys.testproject.view.ui.MainActivity;
import com.infosys.testproject.view.ui.MainFragment;
import com.infosys.testproject.viewmodel.CountryViewModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


/**
 * Created by LOPA on 11/26/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainFragmentTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Fragment fragment;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);


    @Mock
    public ListAdapter listAdapter;

    @Mock
    private CountryViewModel viewModel;


    public MainFragmentTest() {

        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        viewModel = ViewModelProviders.of(rule.getActivity()).get(CountryViewModel.class);
        //mockStatic(Util.class);
    }

    @Test
    public void checkLoaderisVisible() throws Exception{
        List<Fragment> fragmentList = rule.getActivity().getSupportFragmentManager().getFragments();
        fragment = (Fragment) fragmentList.get(0);

        RecyclerView recyclerView = fragment.getView().findViewById(R.id.recycler_view);
        listAdapter = (ListAdapter) recyclerView.getAdapter();


        List<CountryProfile> emptylist  = new ArrayList<>();
        listAdapter.setData(emptylist);
        assertEquals(fragment.getView().findViewById(R.id.loading_data).getVisibility(), View.VISIBLE);
    }

    @Test
    public void ensureDataisLoadinOffline()
    {
        List<Fragment> fragmentList = rule.getActivity().getSupportFragmentManager().getFragments();
        fragment = (Fragment) fragmentList.get(0);

        viewModel = ViewModelProviders.of(rule.getActivity()).get(CountryViewModel.class);
        viewModel.getObservableProject().observe(rule.getActivity(), new Observer<Country>() {
            @Override
            public void onChanged(@Nullable Country country) {
                when(Util.hasNetwork(rule.getActivity())).thenReturn(false);
                assertTrue(viewModel.getObservableProject().getValue().getProfile().size() > 0);
                assertTrue(fragment.getView().findViewById(R.id.recycler_view).getVisibility() == View.VISIBLE);
            }

        });

    }
}
