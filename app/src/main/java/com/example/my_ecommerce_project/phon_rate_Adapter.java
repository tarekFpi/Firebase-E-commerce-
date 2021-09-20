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

public class phon_rate_Adapter extends RecyclerView.Adapter<phon_rate_Adapter.MyViewHolder> {
    private static  onItem_ClickLisiner clickLisiner;
    private Context context;
    private List<phon_Model>phon_modelList;
    private static int listposition=-1;
    public String mystatus="Admin";

    private onItemClickListener_item_User User_Item_ClickLisiner;

    public phon_rate_Adapter(Context context, List<phon_Model> phon_modelList) {
        this.context = context;
        this.phon_modelList = phon_modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view= layoutInflater.inflate(R.layout.phon_rate_show_simple,parent,false);

     return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        phon_Model item_position=phon_modelList.get(position);

        myViewHolder.textView_name.setText("Name:"+item_position.getPhon_pdName());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getPhon_pdDiscount());
        myViewHolder.textView_price.setText("price:"+item_position.getPhon_pdprice());
        Picasso.get().load(item_position.getPhon_pdimage()).into(myViewHolder.imageView);
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
        return phon_modelList.size();
    }

    public void filterdList(List<phon_Model> filterList) {
        phon_modelList=filterList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
   private ImageView imageView;
   private TextView textView_name,textView_price,textView_discount;
        public MyViewHolder(@NonNull View itemView) {
          super(itemView);

            imageView=itemView.findViewById(R.id.phon_rateShow_Image);
            textView_name=itemView.findViewById(R.id.show_phon_rate_Name);
            textView_price=itemView.findViewById(R.id.show_phon_rate_pdPrice);
            textView_discount=itemView.findViewById(R.id.show_phon_rate_discount);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
           // clickLisiner.onClick(position);

            if (!mystatus.equals("User")) {
                clickLisiner.onClick(position);
            }else{
                User_Item_ClickLisiner.onItemclick(position);
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
