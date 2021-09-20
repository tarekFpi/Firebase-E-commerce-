package com.example.my_ecommerce_project;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class User_Address_Adapter extends RecyclerView.Adapter<User_Address_Adapter.MyViewHolder> {
    private static onItem_ClickLisiner  clickLisiner;
  private Context context;
  private List<User_Address_Model>user_modelList;

    public User_Address_Adapter(Context context, List<User_Address_Model> user_modelList) {
        this.context = context;
        this.user_modelList = user_modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
       View view= layoutInflater.inflate(R.layout.user_final_order_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
      User_Address_Model item=user_modelList.get(position);

      String m[]=item.getDataAll().split("/");
      StringBuilder sb=new StringBuilder();
        for (int i = 0,j=0; i <m.length ; i++) {j++;
            sb.append(j+".Order No:"+m[i]+".\n");
        }

        myViewHolder.textView_pdShowAll.setText(sb.toString());
        myViewHolder.text_UerName.setText("User Name:"+item.getUser_name());
        myViewHolder.textView_email.setText("Email Address:"+item.getUser_email());
        myViewHolder.textView_currentDate.setText("Order Date:"+item.getCurrent_date());
        myViewHolder.textView_city.setText("City Twon:"+item.getUser_city());
        myViewHolder.textView_flat.setText("Flat No,Building Name:"+item.getUser_flat_no());
        myViewHolder.textView_location.setText("Locatin:"+item.getUser_location());
        //myViewHolder.textView_flat.setText("Flat No,Building Name:"+item.getUser_flat_no());
        myViewHolder.textView_phon.setText("phon Number:"+ item.getUser_phon());
        myViewHolder.textView_TotalPrice.setText("Total:"+ item.getProd_TotalPrice());
         myViewHolder.textView_Shipping.setText("Shipping:"+ item.getShipping_rate());
    }

    @Override
    public int getItemCount() {
        return user_modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView textView_pdShowAll,textView_Shipping,text_UerName,
     textView_email,textView_location,textView_flat,textView_city,textView_phon,textView_currentDate,textView_TotalPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_currentDate=(TextView)itemView.findViewById(R.id.final_show_pd_orderDate);
            textView_pdShowAll=(TextView)itemView.findViewById(R.id.final_show_pd_all);
            text_UerName=(TextView)itemView.findViewById(R.id.final_show_pd_userName);

            textView_TotalPrice=(TextView)itemView.findViewById(R.id.final_show_pd_totalPrice);
            textView_email=(TextView)itemView.findViewById(R.id.final_show_pd_email);
            textView_location=(TextView)itemView.findViewById(R.id.final_show_pd_location);
            textView_flat=(TextView)itemView.findViewById(R.id.final_show_pd_flat);
            textView_city=(TextView)itemView.findViewById(R.id.final_show_pd_city);
            textView_phon=(TextView)itemView.findViewById(R.id.final_show_pd_phon);
            textView_Shipping=(TextView)itemView.findViewById(R.id.final_Flat_Shipping_rate_free);

          //  textView_pdCord=(TextView)itemView.findViewById(R.id.final_show_pd_cord);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            clickLisiner.onClick(position);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Do Your Action");
            MenuItem menuItem_update=menu.add(Menu.NONE,1,1,"Update");
            MenuItem menuIte_order_Category=menu.add(Menu.NONE,2,2,"Send Confirmation Order");
            MenuItem menuIte_order_delete=menu.add(Menu.NONE,3,3,"Delete");

            menuItem_update.setOnMenuItemClickListener(this);
            menuIte_order_Category.setOnMenuItemClickListener(this);
            menuIte_order_delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position=getAdapterPosition();
            switch (item.getItemId()){
                case 1:
               clickLisiner.Update(position);
               break;

                case 2:
                    clickLisiner.Order_CategorySelect(position);
                    break;

                case 3:
                    clickLisiner.on_Delete(position);
                    break;
            }
            return true;
        }
    }

    public interface onItem_ClickLisiner {
        void onClick(int position);
        void Update(int position);
        void Order_CategorySelect(int position);

        void on_Delete(int position);
    }
    void  setOnItemClickLisiner(onItem_ClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }

}
