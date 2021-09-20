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

public class Recipe_Category_Adapter extends RecyclerView.Adapter<Recipe_Category_Adapter.MyViewHolder> {
    private static  onItem_ClickLisiner clickLisiner;
    private Context context;
    private List<Recipe_CategoryModel>recipe_categoryModelList;
    private static int listposition=-1;

    public Recipe_Category_Adapter(Context context, List<Recipe_CategoryModel> recipe_categoryModelList) {
        this.context = context;
        this.recipe_categoryModelList = recipe_categoryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
       View view= layoutInflater.inflate(R.layout.recipe_category_simple_layout,parent,false);

    return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        Recipe_CategoryModel item_position=recipe_categoryModelList.get(position);

        myViewHolder.textView_recipe_id.setText("Id:"+item_position.getCategory_recipe_id());
        myViewHolder.textView_recipe_name.setText("Name:"+item_position.getCategory_recipe_name());
        myViewHolder.textView_recipe_price.setText("Price:"+item_position.getCategory_recipe_price());
        myViewHolder.textView_recipe_quantity.setText("Quantity:"+item_position.getCategory_recipe_quanity());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getCategory_recipe_discount());

        Picasso.get().load(item_position.getCategory_recipe_image()).into(myViewHolder.imageView);
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
        return recipe_categoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

     private ImageView imageView;
     private TextView textView_recipe_name,textView_recipe_id,textView_recipe_quantity,textView_recipe_price,textView_discount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_recipe_id=(TextView)itemView.findViewById(R.id.category_recipe_pd_id);
            textView_recipe_name=(TextView)itemView.findViewById(R.id.category_recipe_pd_name);
            textView_recipe_price=(TextView)itemView.findViewById(R.id.category_recipe_pd_Price);
            textView_recipe_quantity=(TextView)itemView.findViewById(R.id.category_price_pd_quan);
            textView_discount=(TextView)itemView.findViewById(R.id.category_reicpe_discout);
            imageView=(ImageView)itemView.findViewById(R.id.category_recipe_image);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            clickLisiner.onClick(position);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            int position=getAdapterPosition();
            switch (item.getItemId()){
                case 1:
                    clickLisiner.onDelet(position);
                    break;
            }
            return true;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Do Your Action");
            MenuItem menuItem_Delete=menu.add(Menu.NONE,1,1,"Remove");
            menuItem_Delete.setOnMenuItemClickListener(this);
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
