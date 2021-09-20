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

public class femal_Category_Adapter extends RecyclerView.Adapter<femal_Category_Adapter.MyviewHolder>{
private static onItem_ClickLisiner clickLisiner;
 private Context context;
 private List<femal_category_Model>modelList;
    private static int listposition=-1;

    public femal_Category_Adapter(Context context, List<femal_category_Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
         View view= layoutInflater.inflate(R.layout.category_item_show,viewGroup,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {
        femal_category_Model item_position =modelList.get(position);

      myviewHolder.textView_pdCord.setText("Product Cord:"+item_position.getProduct_Cord());
      myviewHolder.textView_pd_Name.setText("Name:"+item_position.getProduct_Name());
      myviewHolder.textView_quantity.setText("Quantity:"+item_position.getProduct_quantity());
      myviewHolder.textView_total_price.setText("price:"+item_position.getProduct_price());
      myviewHolder.textView_pd_size.setText("Size:"+item_position.getProduct_Size());

   Picasso.get().load(item_position.getProduct_image()).into(myviewHolder.imageView_pd);
   setAnimiton(myviewHolder.itemView,position);
    }
    void setAnimiton(View viewAnimition,int position) {
        if (position >listposition) {
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

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
             View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
    private TextView textView_pdCord,textView_pd_Name,textView_quantity,textView_total_price,textView_pd_size;
  private ImageView imageView_pd;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_pdCord=itemView.findViewById(R.id.cate_show_pd_cord);
            textView_pd_Name=itemView.findViewById(R.id.cate_show_pd_name);
            textView_quantity=itemView.findViewById(R.id.cate_show_pd_quan);
            textView_total_price=itemView.findViewById(R.id.cate_show_pd_totoalPrice);
             textView_pd_size=(TextView)itemView.findViewById(R.id.cate_show_pd_size);
            imageView_pd=(ImageView)itemView.findViewById(R.id.category_image);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
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
