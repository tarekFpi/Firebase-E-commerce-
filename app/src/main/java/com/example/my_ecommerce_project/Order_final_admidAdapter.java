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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Order_final_admidAdapter extends RecyclerView.Adapter<Order_final_admidAdapter.MyViewHolder> {
    private static onItem_ClickLisiner clickLisiner;
private Context context;
private List<Order_final_admin_Model>finalAdminModels_List;
    private static int listposition=-1;
    public Order_final_admidAdapter(Context context, List<Order_final_admin_Model> finalAdminModels_List) {
        this.context = context;
        this.finalAdminModels_List = finalAdminModels_List;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.user_final_order_item,parent,false);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Order_final_admin_Model item_positin=finalAdminModels_List.get(position);

        String m[]=item_positin.getOrder_allData().split("/");
        StringBuilder sb=new StringBuilder();
        for (int i = 0,j=0; i <m.length ; i++) {j++;
            sb.append(j+".Order No:"+m[i]+".\n");
        }

        myViewHolder.textView_pdShowAll.setText(sb.toString());
        myViewHolder.text_UerName.setText("User Name:"+item_positin.getUser_name());
        myViewHolder.textView_email.setText("Email Address:"+item_positin.getUser_emali());
        myViewHolder.textView_currentDate.setText("Order Date:"+item_positin.getOrder_date());
        myViewHolder.textView_city.setText("City Twon:"+item_positin.getUser_city_twon());
        myViewHolder.textView_flat.setText("Flat No,Building Name:"+item_positin.getUser_flat_building());
        myViewHolder.textView_location.setText("Locatin:"+item_positin.getUser_location());
        myViewHolder.textView_phon.setText("phon Number:"+ item_positin.getUser_phon());
        myViewHolder.textView_TotalPrice.setText("Total:"+ item_positin.getOrder_Total_price());

        setAnimiton(myViewHolder.itemView, position);
    }
    void setAnimiton(View viewAnimition,int position) {
        if (position > listposition) {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(1500);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }

    @Override
    public int getItemCount() {
        return finalAdminModels_List.size();
    }

    public void Filter_changData(List<Order_final_admin_Model> filter_list) {
        finalAdminModels_List=filter_list;
        notifyDataSetChanged();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private TextView textView_pdName,textView_pdShowAll,text_UerName,
                textView_userName,textView_email,textView_location,textView_flat,textView_city,textView_phon,textView_currentDate,textView_TotalPrice;

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
            menu.setHeaderTitle("Do Your Any Action");
         MenuItem menu_Delete=menu.add(Menu.NONE,1,1,"Delete");

         menu_Delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position=getAdapterPosition();

            switch (item.getItemId()){
                case 1:
                    clickLisiner.onDelete(position);
                    break;
            }
            return true;
        }
    }
    public interface onItem_ClickLisiner{
        void onClick(int position);
        void onDelete(int position);
    }

    void setOnItemClickLisiner(onItem_ClickLisiner clickLisiner){
      this.clickLisiner=clickLisiner;
    }
}
