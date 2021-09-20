package com.example.my_ecommerce_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class all_login_User_Adapter extends RecyclerView.Adapter<all_login_User_Adapter.MyViewHolder> {
    private Context context;
    private List<sig_inDataModel>modelList;

    public all_login_User_Adapter(Context context, List<sig_inDataModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view = layoutInflater.inflate(R.layout.all_login_simple_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        sig_inDataModel item_position=modelList.get(position);
        myViewHolder.textView_name.setText(""+item_position.getUser_name());
        myViewHolder.textView_email.setText(""+item_position.getUser_Email());

        Picasso.get().load(item_position.getUser_image()).into(myViewHolder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    private ImageView circleImageView;
    private TextView textView_name,textView_email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView=(ImageView) itemView.findViewById(R.id.user_show_image_id);
            textView_name=(TextView)itemView.findViewById(R.id.user_log_name);
            textView_email=(TextView)itemView.findViewById(R.id.user_log_email);
        }
    }
}
