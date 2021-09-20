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
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Order_Accept_Adapter extends RecyclerView.Adapter<Order_Accept_Adapter.MyViewHolder>{
    private Context context;
    private List<Order_Accept_Model>modelList;
   private onItem_ClickLisiner clickLisiner;
    private static int listposition=-1;
    public Order_Accept_Adapter(Context context, List<Order_Accept_Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.order_accept_for_notificaton,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Order_Accept_Model item=modelList.get(position);

       String m[]=item.getOrder_getAllData().split("/");
        StringBuilder sb=new StringBuilder();
        for (int i = 0,j=0; i <m.length ; i++) {j++;
            sb.append(j+".Order No:"+m[i]+".\n");
        }

        myViewHolder.textView_orderAlldata.setText(sb.toString());
         myViewHolder.textView_name.setText("User Name:"+item.getAcc_user_name());
        myViewHolder.textView_email.setText("Email:"+item.getAcc_user_emali());
        myViewHolder.textView_locat.setText("Location:"+item.getAcc_user_location());
        myViewHolder.textView_flat.setText("Flat No,Building Name:"+item.getAcc_user_flat_building());

         myViewHolder.textView_twon.setText("City,Twon:"+item.getAcc_user_city_twon());
         myViewHolder.textView_phon.setText("Phon Number:"+item.getAcc_user_phon());
         myViewHolder.textView_order_Date.setText("Order Date:"+item.getOrder_date());

        myViewHolder.textView_totalPrice.setText("Total Price:"+item.getAccept_total());

        myViewHolder.textView_statusData.setText("Send Status:"+item.getOrder_status());
        myViewHolder.textView_delivary_date.setText("Delivary Date:"+item.getAccept_date());

        setAnimiton(myViewHolder.itemView,position);
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
   return modelList.size();
    }

    public void filterdList(List<Order_Accept_Model> filterList) {
      this.modelList=filterList;
      notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

     TextView textView_orderAlldata, textView_name,textView_email,textView_locat,textView_flat,textView_twon,textView_phon,textView_order_Date,
        textView_delivary_date,textView_statusData,textView_totalPrice;
        public MyViewHolder(@NonNull View itemView) {
        super(itemView);

       textView_orderAlldata=(TextView)itemView.findViewById(R.id.accpt_pd_all);
         textView_name=(TextView)itemView.findViewById(R.id.accpt_userName);
        textView_email=(TextView)itemView.findViewById(R.id.accpt_pd_email);
        textView_locat=(TextView)itemView.findViewById(R.id.accept_pd_location);
        textView_flat=(TextView)itemView.findViewById(R.id.accept_pd_flat);

        textView_twon=(TextView)itemView.findViewById(R.id.accept_pd_city);

        textView_order_Date=(TextView)itemView.findViewById(R.id.order_pd_date);

        textView_phon=(TextView)itemView.findViewById(R.id.accept_pd_phon);
        textView_statusData=(TextView)itemView.findViewById(R.id.accept_send_status);
        textView_delivary_date=(TextView)itemView.findViewById(R.id.accept_Delivary_current_Date);
        textView_totalPrice=(TextView)itemView.findViewById(R.id.accept_totalPrice);

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
            MenuItem menuIte_order_delete=menu.add(Menu.NONE,1,1,"Delete");

            menuIte_order_delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position=getAdapterPosition();

            switch (item.getItemId()){
                case 1:
                    clickLisiner.on_Delete(position);
                    break;
            }
            return true;
        }
    }

    public interface onItem_ClickLisiner {
        void onClick(int position);
        void on_Delete(int position);
    }

    void  setOnItemClickLisiner (onItem_ClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }
}
