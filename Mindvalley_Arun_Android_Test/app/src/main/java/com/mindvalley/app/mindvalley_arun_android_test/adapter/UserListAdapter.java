package com.mindvalley.app.mindvalley_arun_android_test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindvalley.app.mindvalley_arun_android_test.R;
import com.mindvalley.app.mindvalley_arun_android_test.activities.UserDetailActivity;
import com.mindvalley.app.mindvalley_arun_android_test.misc.Constants;
import com.mindvalley.app.mindvalley_arun_android_test.rest.model.Urls;
import com.mindvalley.app.mindvalley_arun_android_test.rest.model.User;
import com.mindvalley.app.mindvalley_arun_android_test.rest.model.UserModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by arun on 4/28/2016.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MasonryView> {

    private List<UserModel> userDetails;

    public UserListAdapter(List<UserModel> userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
        return new MasonryView(layoutView);
    }

    @Override
    public void onBindViewHolder(MasonryView holder, int position) {
        ImageLoader.getInstance().displayImage(userDetails.get(position).getUser().getProfile_image().getLarge(), holder.imageView_img);
        holder.textView_name.setText(userDetails.get(position).getUser().getName());
        holder.textView_id.setText(userDetails.get(position).getId());
        holder.textView_likes.setText(userDetails.get(position).getLikes());
    }

    @Override
    public int getItemCount() {
        return userDetails.size();
    }

    class MasonryView extends RecyclerView.ViewHolder {
        ImageView imageView_img;
        TextView textView_name;
        TextView textView_id;
        TextView textView_likes;

        public MasonryView(View itemView) {
            super(itemView);
            imageView_img = (ImageView) itemView.findViewById(R.id.imageView_image);
            textView_name = (TextView) itemView.findViewById(R.id.textView_name);
            textView_id = (TextView) itemView.findViewById(R.id.textView_id);
            textView_likes = (TextView) itemView.findViewById(R.id.textView_likes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    for (int i = 0; i < userDetails.size(); i++) {
                        if (textView_id.getText().toString().equalsIgnoreCase(userDetails.get(i).getId())) {
                            UserModel uModel = userDetails.get(i);
                            Urls url = userDetails.get(i).getUrls();
                            User user = userDetails.get(i).getUser();
                            Intent intent = new Intent(context, UserDetailActivity.class);
                            intent.putExtra(Constants.PARCEL_USERMODEL, uModel);
                            intent.putExtra(Constants.PARCEL_URL, url);
                            intent.putExtra(Constants.PARCEL_USER, user);
                            context.startActivity(intent);
                        }
                    }

                }
            });
        }
    }

}
