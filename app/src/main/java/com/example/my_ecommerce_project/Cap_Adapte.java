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
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Cap_Adapte extends RecyclerView.Adapter<Cap_Adapte.MyViewHolder> {
    private  static onItemClickLisiner clickLisiner;
 private Context context;
 private List<Cap_product_Model>modelList;
    private static int listposition=-1;
    public String mystatus="Admin";
    private onItemClickListener_item_User User_Item_ClickLisiner;

    public Cap_Adapte(Context context, List<Cap_product_Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view=  layoutInflater.inflate(R.layout.simple_caps_layout,parent,false);

   return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
      Cap_product_Model item=modelList.get(position);

      myViewHolder.textView_pdName.setText("Name:"+item.getCap_pdName());
      myViewHolder.textView_price.setText("Price:"+item.getCap_pdPrice());

      Picasso.get().load(item.getCap_pdImage()).into(myViewHolder.imageView);
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
        return modelList.size();
    }

    public void filterdList(List<Cap_product_Model> filterList) {
        modelList=filterList;
      notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
    private  ImageView imageView;
    private TextView textView_pdName,textView_price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_pdName=(TextView)itemView.findViewById(R.id.show_cap_pdName_Id);
            textView_price=(TextView)itemView.findViewById(R.id.show_cap_pdPrice);
            imageView=(ImageView)itemView.findViewById(R.id.cap_rateImage);

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

                menu.setHeaderTitle("Do your Any Action");
                MenuItem menuIte_Update=menu.add(Menu.NONE,1,1,"Update");
                MenuItem menuIte_Delete=menu.add(Menu.NONE,2,2,"Delete");

                menuIte_Update.setOnMenuItemClickListener(this);
                menuIte_Delete.setOnMenuItemClickListener(this);
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
                    clickLisiner.OnUpdate(position);
                    break;

                case 2:
                    clickLisiner.OnDelete(position);
                    break;
            }
            return true;
        }
    }
    public interface onItemClickLisiner{
        void onClick(int position);
        void OnUpdate(int position);
        void OnDelete(int position);
    }

    void setOnItemClick(onItemClickLisiner clickLisiner){
       this. clickLisiner=clickLisiner;
    }

    public interface onItemClickListener_item_User {
        void onItemclick(int position);
    }

    void setUser_On_ClickListener(onItemClickListener_item_User clickListener) {
        this.User_Item_ClickLisiner = clickListener;
    }
}
