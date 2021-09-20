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

public class baby_Category_Adapter extends RecyclerView.Adapter<baby_Category_Adapter.MyViewHolder> {
    private  onItemClickListener click_listener;
    private Context context;
    private List<babyCategory_Model>babyCategory_models;
    private static int listposition=-1;

    public String mystatus="Admin";

    public baby_Category_Adapter(Context context, List<babyCategory_Model> babyCategory_models) {
        this.context = context;
        this.babyCategory_models = babyCategory_models;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
       View view=  layoutInflater.inflate(R.layout.baby_category_simple,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        babyCategory_Model item_position=babyCategory_models.get(position);

        myViewHolder.textView_pdId.setText("ID:"+item_position.getBaby_pd_id());
        myViewHolder.textView_pdName.setText("Name:"+item_position.getBaby_pd_Name());
        myViewHolder.textView_quantity.setText("Quantity:"+item_position.getBaby_pd_quantity());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getBaby_pd_discount());
        myViewHolder.textView_price.setText("Price:"+item_position.getBaby_pd_price());
        myViewHolder.textView_pdSize.setText("Size:"+item_position.getBaby_pd_size());

        Picasso.get().load(item_position.getBaby_pd_image()).into(myViewHolder.imageView);
        setAnimiton(myViewHolder.itemView,position);
    }

    void setAnimiton(View viewAnimition,int position) {
        if (position >listposition) {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(1000);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }

    @Override
    public int getItemCount() {
        return babyCategory_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
   private ImageView imageView;
   private TextView textView_pdName,textView_pdId,textView_price,textView_discount,textView_quantity,textView_pdSize;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //cate_baby_Show_size
            imageView=(ImageView)itemView.findViewById(R.id.category_baby_Show_image);
            textView_pdId=(TextView) itemView.findViewById(R.id.cate_babyShow_pd_id);
            textView_pdName=(TextView) itemView.findViewById(R.id.cate_baby_Show_name);
            textView_price=(TextView) itemView.findViewById(R.id.cate_baby_pd_Price);
            textView_discount=(TextView) itemView.findViewById(R.id.cate_baby_Show_discout);
            textView_quantity=(TextView) itemView.findViewById(R.id.cate_baby_Show_pd_quan);
            textView_pdSize=(TextView) itemView.findViewById(R.id.cate_baby_Show_size);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
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
