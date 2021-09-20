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

public class laptop_categorey_Adapter extends RecyclerView.Adapter<laptop_categorey_Adapter.MyViewHolder> {
    private static  onItem_ClickLisiner clickLisiner;
    private Context context;
    private List<laptop_CategoryModel>categoryModelList;
    private static int listposition=-1;

    public laptop_categorey_Adapter(Context context, List<laptop_CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view=  layoutInflater.inflate(R.layout.laptop_category_item,parent,false);
     return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
    laptop_CategoryModel item_position=categoryModelList.get(position);

     myViewHolder.textView_pdId.setText("Product Cord:"+item_position.getLaptop_Id());
     myViewHolder.textView_pdName.setText("Name:"+item_position.getLapt_name());
     myViewHolder.textView_discount.setText("Discount:"+item_position.getLapt_discount());
     myViewHolder.textView_pd_Quantity.setText("Quantity:"+item_position.getLapt_quatity());
     myViewHolder.textView_price.setText("Price:"+item_position.getLapt_price());

     Picasso.get().load(item_position.getLaptop_image()).into(myViewHolder.imageView);
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
        return categoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
     private ImageView imageView;
     private TextView textView_pdName,textView_pdId,textView_pd_Quantity,textView_price,textView_discount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.category_lap_image);

            textView_pdId=(TextView)itemView.findViewById(R.id.cate_lap_pd_id);
            textView_pdName=(TextView)itemView.findViewById(R.id.cate_lap_pd_name);
            textView_discount=(TextView)itemView.findViewById(R.id.cate_lap_discout);
            textView_pd_Quantity=(TextView)itemView.findViewById(R.id.cate_lap_pd_quan);
            textView_price=(TextView)itemView.findViewById(R.id.cate_lp_pd_Price);

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
            MenuItem menuItem_Delete=menu.add(Menu.NONE,1,1,"Remove");
            menuItem_Delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position=getAdapterPosition();
            switch (item.getItemId()){
                case 1:
                    clickLisiner.onDelet(position);
                   notifyDataSetChanged();
                    break;
            }
            return true;
        }
    }

    public interface onItem_ClickLisiner {
        void onClick(int position);
        void onDelet(int position);
    }
    void  setOnItemClickLisiner(onItem_ClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }
}
