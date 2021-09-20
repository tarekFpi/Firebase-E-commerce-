package com.example.my_ecommerce_project;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class baby_rateAdapter extends RecyclerView.Adapter<baby_rateAdapter.MyViewHolder> {
    private static  onItem_ClickLisiner clickLisiner;
    private Context context;
    private List<baby_product_Model>baby_product_models;
    private static int listposition=-1;
    public String mystatus="Admin";
    private onItemClickListener_item_User User_Item_ClickLisiner;

    public baby_rateAdapter(Context context, List<baby_product_Model> baby_product_models) {
        this.context = context;
        this.baby_product_models = baby_product_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view= layoutInflater.inflate(R.layout.baby_rate_simple_show,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        baby_product_Model item_position=baby_product_models.get(position);

        myViewHolder.textView_name.setText("Name:"+item_position.getBaby_pd_Name());
        myViewHolder.textView_price.setText("price:"+item_position.getBaby_pd_price());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getBaby_pd_discount());

        Picasso.get().load(item_position.getBaby_pd_image()).into(myViewHolder.imageView);
        setAnimiton(myViewHolder.itemView,position);
    }
    void setAnimiton(View viewAnimition,int position) {
        if (position > listposition) {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(2000);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }
    @Override
    public int getItemCount() {
        return baby_product_models.size();
    }

    public void filter_chang(List<baby_product_Model> filterList) {
        baby_product_models=filterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
    View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{

    private ImageView imageView;
    private TextView textView_name,textView_price,textView_discount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.baby_rate_show_Image);
            textView_name=itemView.findViewById(R.id.baby_rate_show_Name);
            textView_discount=itemView.findViewById(R.id.baby_rate_show_discount);
            textView_price=itemView.findViewById(R.id.baby_price_pdPrice);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }



        @Override
        public void onClick(View v) {
            try {
                int position=getAdapterPosition();

                if (!mystatus.equals("User")) {
                    clickLisiner.onClick(position);
                }else{
                    User_Item_ClickLisiner.onItemclick(position);
                }

            }catch (Exception exception){
                Toast.makeText(context, "Error:"+exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if(!mystatus.contains("User")){
                menu.setHeaderTitle("Do Your Action");
                MenuItem menuItem_update=menu.add(Menu.NONE,1,1,"Update");
                MenuItem menuIte_order_delete=menu.add(Menu.NONE,2,2,"Delete");

                menuItem_update.setOnMenuItemClickListener(this);
                menuIte_order_delete.setOnMenuItemClickListener(this);
            }else{

                menu.setHeaderTitle("Do Your Any Action");
                menu.setGroupEnabled(1, false);
                menu.setGroupEnabled(2, false);
            }

        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position=getAdapterPosition();
            switch (item.getItemId()){
                case 1:
                    clickLisiner.Update(position);
                    break;

                case 2:
                    clickLisiner.on_Delete(position);
                    break;
            }
            return true;
        }
    }
    public interface onItem_ClickLisiner {

        void onClick(int position);
        void Update(int position);
        void on_Delete(int position);
    }
    void  setOnItemClickLisiner(onItem_ClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }

    public interface onItemClickListener_item_User {
        void onItemclick(int position);
    }
    void setUser_On_ClickListener(onItemClickListener_item_User clickListener) {
        this.User_Item_ClickLisiner = clickListener;
    }
}
