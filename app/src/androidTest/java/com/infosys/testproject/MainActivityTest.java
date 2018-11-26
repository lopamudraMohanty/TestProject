package com.infosys.testproject;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import com.infosys.testproject.service.model.Country;
import com.infosys.testproject.utility.Util;
import com.infosys.testproject.view.ui.MainActivity;
import com.infosys.testproject.view.ui.MainFragment;
import com.infosys.testproject.viewmodel.CountryViewModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
//import org.mockito.Mock;

import java.util.List;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/*import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;*/

/**
 * Created by LOPA on 11/25/2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Mock
    private CountryViewModel viewModel;

    @Test
    public void ensureFrmelayoutisPresent()
    {
        MainActivity activity = rule.getActivity();
        List<Fragment> fragmentList = activity.getSupportFragmentManager().getFragments();
        Fragment fragment = (Fragment) fragmentList.get(0);
        assertThat(fragmentList.get(0), instanceOf(MainFragment.class));
    }

    @Test
    public void showErrorMessageonNoInternetConnection()
    {
     viewModel = ViewModelProviders.of(rule.getActivity()).get(CountryViewModel.class);

     viewModel.getObservableProject().observe(rule.getActivity(), new Observer<Country>() {
         @Override
         public void onChanged(@Nullable Country country) {
             when(country.getProfile()).thenReturn(null);
             when(Util.hasNetwork(rule.getActivity())).thenReturn(false);
             verify(Util.alertDialog).isShowing();
             int titleId = Util.alertDialog.getContext().getResources().getIdentifier( "alertTitle", "id", "android" );
             if (titleId > 0) {
                 TextView dialogTitle = (TextView) Util.alertDialog.findViewById(titleId);
                 verify(dialogTitle).equals(R.string.internet_connectivity_error);
             }
         }
     });;
    }

}
