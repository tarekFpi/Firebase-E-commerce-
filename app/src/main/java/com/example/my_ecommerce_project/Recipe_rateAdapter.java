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

public class Recipe_rateAdapter extends RecyclerView.Adapter<Recipe_rateAdapter.MyviewHolder> {
    private static onItemClickLisiner clickLisiner;
    private onItemClickListener_item_User User_Item_ClickLisiner;
 private Context context;
 private List<Recipe_ModelAdd>recipe_modelAddList;
  private static int listposition=-1;
    public String mystatus="Admin";



    public Recipe_rateAdapter(Context context, List<Recipe_ModelAdd> recipe_modelAddList) {
        this.context = context;
        this.recipe_modelAddList = recipe_modelAddList;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view= layoutInflater.inflate(R.layout.recipe_rate_simple_layout,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {
        Recipe_ModelAdd item_position=recipe_modelAddList.get(position);
        myviewHolder.textView_name.setText("Name:"+item_position.getRecipe_pd_Name());
        myviewHolder.textView_discount.setText("Discount:"+item_position.getRecipe_pd_discount());
        myviewHolder.textView_price.setText("price:"+item_position.getRecipe_pd_price());

        Picasso.get().load(item_position.getRecipe_pd_image()).into(myviewHolder.imageView);
        setAnimiton(myviewHolder.itemView,position);
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
        return recipe_modelAddList.size();
    }

    public void filterdList(List<Recipe_ModelAdd> filterList) {
        recipe_modelAddList=filterList;
        notifyDataSetChanged();
    }

    public  class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
    private ImageView imageView;
    private TextView textView_name,textView_discount,textView_price;

      public MyviewHolder(@NonNull View itemView) {
          super(itemView);

          textView_name=(TextView)itemView.findViewById(R.id.show_recipe_Name_Id);
          textView_discount=(TextView)itemView.findViewById(R.id.show_recipe_discount);
          textView_price=(TextView)itemView.findViewById(R.id.show_recipe_pdPrice);
          imageView =itemView.findViewById(R.id.recipe_rateImage);

          itemView.setOnClickListener(this);
          itemView.setOnCreateContextMenuListener(this);
      }

        @Override
        public void onClick(View v) {
          int position=getAdapterPosition();

            if (!mystatus.equals("User")) {
                clickLisiner.OnClick(position);
            }else{
             User_Item_ClickLisiner.onItemclick(position);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if(!mystatus.contains("User")){
                menu.setHeaderTitle("Do Your Any Aaction");
                MenuItem menuIte_order_delete=menu.add(Menu.NONE,1,1,"Delete");
                MenuItem menuItem_update=menu.add(Menu.NONE,2,2,"Update");

                menuIte_order_delete.setOnMenuItemClickListener(this);
                menuItem_update.setOnMenuItemClickListener(this);
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
                    clickLisiner.OnDelete(position);
                    break;

                case 2:
                    clickLisiner.OnUpdate(position);
                    break;
            }
            return true;
        }
    }

    public interface onItemClickLisiner{
        void OnClick(int position);
        void OnUpdate(int position);
        void OnDelete(int position);
    }

    void setOnItemClickLisiner(onItemClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }

    public interface onItemClickListener_item_User {
        void onItemclick(int position);
    }

    void setUser_On_ClickListener(onItemClickListener_item_User clickListener) {
        this.User_Item_ClickLisiner = clickListener;
    }
}
