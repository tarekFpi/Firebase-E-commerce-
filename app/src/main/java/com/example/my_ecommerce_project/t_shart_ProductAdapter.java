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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class t_shart_ProductAdapter extends RecyclerView.Adapter<t_shart_ProductAdapter.MyViewHolder> {
  private static onItem_ClickLisiner clickLisiner;
    private static onItemClickListener_item_User User_Item_ClickLisiner;

private Context context;
private List<T_shirt_Moder>t_shirt_moderList;
    private static int listposition=-1;
    public String mystatus="Admin";


    public t_shart_ProductAdapter(Context context, List<T_shirt_Moder> t_shirt_moderList) {
        this.context = context;
        this.t_shirt_moderList = t_shirt_moderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.t_shairt_rate_show_simplelayout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        T_shirt_Moder item_position=t_shirt_moderList.get(position);

        myViewHolder.textView_name.setText("Name:"+item_position.getTshirt_pdName());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getTshirt_Discount());
        myViewHolder.textView_price.setText("Price:"+item_position.getTshirt_pdPrice());
//        myViewHolder.textView_size.setText("Size:"+item_position.getTshirt_size());

        Picasso.get().load(item_position.getTshirt_pdImage()).into(myViewHolder.imageView);
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
    return t_shirt_moderList.size();
    }

    public void filterdList(List<T_shirt_Moder> filterList) {
        t_shirt_moderList=filterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private ImageView imageView;
        private TextView textView_name,textView_price,textView_discount,textView_size;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.lap_rateImage);
            textView_name=itemView.findViewById(R.id.show_lap_Name_Id);
            textView_discount=itemView.findViewById(R.id.show_lap_discount);
            textView_price=itemView.findViewById(R.id.show_lap_pdPrice);
        // textView_size=itemView.findViewById(R.id.cate_tShairt_size);

             itemView.setOnClickListener(this);
             itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
         int position=getAdapterPosition();

            if (!mystatus.equals("User")) {
                clickLisiner.onClick(position);
            }else{
                User_Item_ClickLisiner.onItemclick(position);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if(!mystatus.contains("User")){
                menu.setHeaderTitle("Do Your Aany Action");
                MenuItem menu_Delete=menu.add(Menu.NONE,1,1,"Delete");
                MenuItem menu_Update=menu.add(Menu.NONE,2,2,"Update");

                menu_Delete.setOnMenuItemClickListener(this);
                menu_Update.setOnMenuItemClickListener(this);
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
                    clickLisiner.on_Delete(position);
                    break;
                case 2:
                    clickLisiner.on_Update(position);
                    break;
            }
            return true;
        }
    }

    public interface onItem_ClickLisiner {
        void onClick(int position);
        void on_Delete(int position);
        void on_Update(int position);
    }

    void  setOnItemClickLisiner (onItem_ClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }

    public interface onItemClickListener_item_User {
        void onItemclick(int position);
    }

    void setUser_On_ClickListener(onItemClickListener_item_User User_clickListener) {
        this.User_Item_ClickLisiner = User_clickListener;
    }
}
