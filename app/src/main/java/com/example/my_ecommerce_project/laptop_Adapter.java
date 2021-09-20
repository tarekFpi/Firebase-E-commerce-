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

public class laptop_Adapter extends RecyclerView.Adapter<laptop_Adapter.MyviewHolder> {
    private onItemClickLisiner clickLisiner;
    private onItemClickListener_item_User User_Item_ClickLisiner;

    private Context context;
    private List<laptop_prodcut_Module>laptopProdcutList;
    private static int listposition=-1;
    public String mystatus="Admin";

    public laptop_Adapter(Context context, List<laptop_prodcut_Module> laptopProdcutList) {
        this.context = context;
        this.laptopProdcutList = laptopProdcutList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
       View view= layoutInflater.inflate(R.layout.t_shairt_rate_show_simplelayout,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {

        laptop_prodcut_Module item_positon=laptopProdcutList.get(position);

        myviewHolder.textView_name.setText("Name:"+item_positon.getBrand_name());
        myviewHolder.textView_discount.setText("Discount:"+item_positon.getLap_Discount());
        myviewHolder.textView_price.setText("price:"+item_positon.getLap_price());

        Picasso.get().load(item_positon.getLap_image()).into(myviewHolder.imageView);
        setAnimiton(myviewHolder.itemView,position);
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
   return laptopProdcutList.size();
    }

    public void filterdList(List<laptop_prodcut_Module> filterList) {
        laptopProdcutList=filterList;
        notifyDataSetChanged();
    }

    public  class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
  private ImageView imageView;
  private TextView textView_name,textView_price,textView_discount;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.lap_rateImage);
            textView_name=(TextView)itemView.findViewById(R.id.show_lap_Name_Id);
            textView_price=(TextView)itemView.findViewById(R.id.show_lap_pdPrice);
            textView_discount=(TextView)itemView.findViewById(R.id.show_lap_discount);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

      int position=getAdapterPosition();
            if (!mystatus.equals("User")) {
                clickLisiner.OnClick(position);
            }else{
           User_Item_ClickLisiner.onItemclick(position);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if(!mystatus.contains("User")){
                menu.setHeaderTitle("Do Your Any Aaction");
                MenuItem menuIte_order_delete=menu.add(Menu.NONE,1,1,"Delete");
                MenuItem menuItem_update=menu.add(Menu.NONE,2,2,"Update");

                menuIte_order_delete.setOnMenuItemClickListener(this);
                menuItem_update.setOnMenuItemClickListener(this);
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
                    clickLisiner.OnDelete(position);
                    break;

                case 2:
                    clickLisiner.OnUpdate(position);
                    break;
            }
            return true;
        }
    }

    public interface onItemClickLisiner{
        void OnClick(int position);
        void OnUpdate(int position);
        void OnDelete(int position);
    }
    void setOnItemClickLisiner(onItemClickLisiner clickLisiner){
    this.clickLisiner=clickLisiner;
    }

    public interface onItemClickListener_item_User {
        void onItemclick(int position);
    }
    void setUser_On_ClickListener(onItemClickListener_item_User clickListener) {
        this.User_Item_ClickLisiner = clickListener;
    }
}
