package com.mindvalley.app.mindvalley_arun_android_test.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mindvalley.app.mindvalley_arun_android_test.R;
import com.mindvalley.app.mindvalley_arun_android_test.misc.Constants;
import com.mindvalley.app.mindvalley_arun_android_test.adapter.UserListAdapter;
import com.mindvalley.app.mindvalley_arun_android_test.rest.model.UserModel;
import com.mindvalley.app.mindvalley_arun_android_test.rest.service.UserServiceAPI;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public RecyclerView mRecyclerView;
    public List<UserModel> userDetails;
    public SwipeRefreshLayout swipeRefreshLayout;
    UserListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        getUserDetails();
                                    }
                                }
        );
    }

    public void initViews() {
        final DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageOnLoading(R.drawable.user_loading_icon)
                .showImageForEmptyUri(android.R.drawable.ic_dialog_alert)
                .showImageOnFail(R.drawable.ic_no_internet)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(MainActivity.this);
    }

    @Override
    public void onRefresh() {
        getUserDetails();
    }

    public void fabOnClick(View view) {
        Snackbar.make(view, Constants.WELCOME_MSG, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void getUserDetails() {
        swipeRefreshLayout.setRefreshing(true);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.ROOT_URL)
                .build();

        UserServiceAPI api = restAdapter.create(UserServiceAPI.class);
        api.getUserDetails(new Callback<List<UserModel>>() {

            @Override
            public void success(List<UserModel> userModels, Response response) {
                swipeRefreshLayout.setRefreshing(false);
                userDetails = userModels;

                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter = new UserListAdapter(userDetails);
                mRecyclerView.setAdapter(adapter);

                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        int topRowVerticalPosition =
                                (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                        swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, Constants.RETROFIT_ERROR_MESSAGE, Toast.LENGTH_LONG).show();

            }
        });
    }
}
