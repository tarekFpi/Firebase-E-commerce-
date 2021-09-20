package com.example.my_ecommerce_project;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class order_again_adapter extends RecyclerView.Adapter<order_again_adapter.MyViewHolder>{
    private static  onItem_ClickLisiner clickLisiner;
      private Context context;
    private List<order_againModel>order_list;

    public order_again_adapter(Context context, List<order_againModel> order_list) {
        this.context = context;
        this.order_list = order_list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.order_again_simple_layout,parent,false);

     return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
     order_againModel item_position=order_list.get(position);

    myViewHolder.textViewpd_name.setText("Name:"+item_position.getUser_pdName());
    myViewHolder.textViewpd_price.setText("price:"+item_position.getUser_pdprice());

        Picasso.get().load(item_position.getUser_pdimage()).into(myViewHolder.again_image);
    }

    @Override
    public int getItemCount() {
        return order_list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
  private ImageView again_image;
  private TextView textViewpd_name, textViewpd_price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewpd_name=(TextView)itemView.findViewById(R.id.again_pd_name);
            textViewpd_price=(TextView)itemView.findViewById(R.id.again_pd_price);
            again_image=(ImageView)itemView.findViewById(R.id.again_pd_image);

           itemView.setOnCreateContextMenuListener(this);
           itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int positon=getAdapterPosition();
            clickLisiner.onClick(positon);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Do Your Action");
            MenuItem menuIte_order_delete=menu.add(Menu.NONE,1,1,"Delete");
            MenuItem menuIte_order_again=menu.add(Menu.NONE,2,2,"Order Again");

            menuIte_order_again.setOnMenuItemClickListener(this);
            menuIte_order_delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int positon=getAdapterPosition();

            switch (item.getItemId()){
                case 1:
                    clickLisiner.on_Delete(positon);
                    break;

                case 2:
                    clickLisiner.Order_Again(positon);
                    break;
            }
            return true;
        }
    }

    public interface onItem_ClickLisiner {
        void onClick(int position);
        void on_Delete(int position);
        void Order_Again(int position);

    }
  void  setOnItemClickLisiner(onItem_ClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }

}
