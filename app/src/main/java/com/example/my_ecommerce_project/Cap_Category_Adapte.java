package com.example.my_ecommerce_project;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
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

public class Cap_Category_Adapte extends RecyclerView.Adapter<Cap_Category_Adapte.MyViewHolder> {
    private  onItemClickListener click_listener;
    private Context context;
    private List<Cap_Category_Model> cap_categoryList;
    private static int listposition=-1;

    public Cap_Category_Adapte(Context context, List<Cap_Category_Model> cap_categoryList) {
        this.context = context;
        this.cap_categoryList = cap_categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view=  layoutInflater.inflate(R.layout.category_item_show,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myviewHolder, int position) {
        Cap_Category_Model item_position=cap_categoryList.get(position);

        myviewHolder.textView_pdCord.setText("Product Cord:"+item_position.getCapCategory_id());
        myviewHolder.textView_pd_Name.setText("Name:"+item_position.getCapCategory_name());
        myviewHolder.textView_quantity.setText("Quantity:"+item_position.getCapCategory_quantity());
        myviewHolder.textView_total_price.setText("price:"+item_position.getCapCategory_price());
        myviewHolder.textView_pd_size.setText("Size:"+item_position.getCapCategory_Size());

        Picasso.get().load(item_position.getCapCategory_image()).into(myviewHolder.imageView_pd);
        setAnimiton(myviewHolder.itemView,position);
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
        return cap_categoryList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView textView_pdCord,textView_pd_Name,textView_quantity,textView_total_price,textView_pd_size;
        private ImageView imageView_pd;

      public MyViewHolder(@NonNull View itemView) {
          super(itemView);

          textView_pdCord=itemView.findViewById(R.id.cate_show_pd_cord);
          textView_pd_Name=itemView.findViewById(R.id.cate_show_pd_name);
          textView_quantity=itemView.findViewById(R.id.cate_show_pd_quan);
          textView_total_price=itemView.findViewById(R.id.cate_show_pd_totoalPrice);
          textView_pd_size=(TextView)itemView.findViewById(R.id.cate_show_pd_size);
          imageView_pd=(ImageView)itemView.findViewById(R.id.category_image);

          itemView.setOnCreateContextMenuListener(this);
          itemView.setOnClickListener(this);
      }

        @Override
        public void onClick(View v) {
           int position=getAdapterPosition();
           click_listener.onItemclick(position);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Do Your Any Action");
            MenuItem DeleteTask = menu.add(menu.NONE, 1, 1, "Delete");
            DeleteTask.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position=getAdapterPosition();
            switch (item.getItemId()){

                case 1:
                    click_listener.onDelete(position);
                    break;
            }
            return true;
        }
    }
    public interface onItemClickListener {
        void onItemclick(int position);
        void onDelete(int position);
    }
    void setOnItemClickListener(onItemClickListener clickListener) {
        this.click_listener = clickListener;
    }
}
