package com.mindvalley.app.mindvalley_arun_android_test.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindvalley.app.mindvalley_arun_android_test.R;
import com.mindvalley.app.mindvalley_arun_android_test.activities.WebViewActivity;
import com.mindvalley.app.mindvalley_arun_android_test.misc.Constants;
import com.mindvalley.app.mindvalley_arun_android_test.rest.model.User;
import com.mindvalley.app.mindvalley_arun_android_test.rest.model.UserModel;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.OnClick;

/**
 * Created by Admin on 4/28/2016.
 */
public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.DetailView> {

    private User user;
    private UserModel uModel;
    private Context context;

    public UserDetailAdapter(Context context, UserModel uModel, User user) {
        this.context = context;
        this.uModel = uModel;
        this.user = user;
    }

    @Override
    public DetailView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_detail, parent, false);
        DetailView detailView = new DetailView(layoutView);
        return detailView;
    }

    @Override
    public void onBindViewHolder(DetailView holder, int position) {
        ImageLoader.getInstance().displayImage(user.getProfile_image().getLarge(), holder.imageView_img);
        holder.textView_id_value.setText(user.getId());
        holder.textView_name_value.setText(user.getName());
        holder.textView_username_value.setText(user.getUsername());
        holder.textView_likes.setText(uModel.getLikes());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class DetailView extends RecyclerView.ViewHolder {
        ImageView imageView_img;
        TextView textView_name_value;
        TextView textView_id_value;
        TextView textView_username_value;
        ImageButton button_user_profile, button_share;
        TextView textView_likes;

        public DetailView(View itemView) {
            super(itemView);
            imageView_img = (ImageView) itemView.findViewById(R.id.imageView_image);
            textView_name_value = (TextView) itemView.findViewById(R.id.textView_name_value);
            textView_id_value = (TextView) itemView.findViewById(R.id.textView_id_value);
            textView_username_value = (TextView) itemView.findViewById(R.id.textView_username_value);
            button_user_profile = (ImageButton) itemView.findViewById(R.id.imageButton_profile_link);
            button_share = (ImageButton) itemView.findViewById(R.id.imageButton_share);
            textView_likes = (TextView) itemView.findViewById(R.id.textView_likes);

            button_user_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(Constants.INTENT_WEBVIEW_URL, user.getLinks().getHtml());
                    context.startActivity(intent);
                }
            });

            button_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    onShareClick(context);
                }
            });
        }


        public void onShareClick(Context context) {
            StringBuilder sb = new StringBuilder();
            sb.append("Hey there!!..\nPlease find the user details below \n");
            sb.append("Id: ").append(user.getId());
            sb.append("\nUser Name: ").append(user.getUsername());
            sb.append("\nName: ").append(user.getName());
            sb.append("\nProfile url: ").append(user.getLinks().getHtml());
            sb.append("\nLikes: ").append(uModel.getLikes());
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        }
    }


}
