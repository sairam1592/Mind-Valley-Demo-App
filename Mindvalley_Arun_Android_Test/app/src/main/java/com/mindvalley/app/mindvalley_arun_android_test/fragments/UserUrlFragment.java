package com.mindvalley.app.mindvalley_arun_android_test.fragments;

/**
 * Created by Admin on 10/20/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindvalley.app.mindvalley_arun_android_test.R;
import com.mindvalley.app.mindvalley_arun_android_test.misc.ConnectionHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserUrlFragment extends Fragment {
    public static final String EXTRA_URL = "EXTRA_URL";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    Intent i;

    public static final UserUrlFragment newInstance(String message, String url) {
        UserUrlFragment f = new UserUrlFragment();
        Bundle bdl = new Bundle();
        bdl.putString(EXTRA_URL, url);
        bdl.putString(EXTRA_MESSAGE, message);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        String url = getArguments().getString(EXTRA_URL);
        String message = getArguments().getString(EXTRA_MESSAGE);
        View v = inflater.inflate(R.layout.user_url_fragment, container, false);
        TextView messageTextView = (TextView) v.findViewById(R.id.textView_message);
        messageTextView.setText(message);
        ImageView imageview_img = (ImageView) v.findViewById(R.id.imageView_image);

        if (ConnectionHelper.isConnected(getActivity())) {
            ImageLoader.getInstance().displayImage(url, imageview_img);
        } else {
            imageview_img.setImageResource(R.drawable.ic_no_internet);
        }
        return v;
    }
}