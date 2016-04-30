package com.mindvalley.app.mindvalley_arun_android_test.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.mindvalley.app.mindvalley_arun_android_test.R;
import com.mindvalley.app.mindvalley_arun_android_test.adapter.UserDetailAdapter;
import com.mindvalley.app.mindvalley_arun_android_test.fragments.UserUrlFragment;
import com.mindvalley.app.mindvalley_arun_android_test.misc.Constants;
import com.mindvalley.app.mindvalley_arun_android_test.rest.model.Urls;
import com.mindvalley.app.mindvalley_arun_android_test.rest.model.User;
import com.mindvalley.app.mindvalley_arun_android_test.rest.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arun on 4/28/2016.
 */
public class UserDetailActivity extends AppCompatActivity {

    UserModel uModel;
    Urls urls;
    User user;
    MyPageAdapter pageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetail);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle b = getIntent().getExtras();
        uModel = b.getParcelable(Constants.PARCEL_USERMODEL);
        urls = b.getParcelable(Constants.PARCEL_URL);
        user = b.getParcelable(Constants.PARCEL_USER);
        assert uModel != null;


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UserDetailAdapter adapter = new UserDetailAdapter(UserDetailActivity.this, uModel, user);
        recyclerView.setAdapter(adapter);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(user.getName());

        List<Fragment> fragments = getFragments(urls);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
    }

    private List<Fragment> getFragments(Urls urls) {
        List<Fragment> fList = new ArrayList<>();

        fList.add(UserUrlFragment.newInstance(getResources().getString(R.string.text_regular_image), urls.getRegular()));
        fList.add(UserUrlFragment.newInstance(getResources().getString(R.string.text_small_image), urls.getSmall()));
        fList.add(UserUrlFragment.newInstance(getResources().getString(R.string.text_thumb_image), urls.getThumb()));

        return fList;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
