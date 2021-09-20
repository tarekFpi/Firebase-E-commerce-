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

public class T_shairt_Category_Adapte extends RecyclerView.Adapter<T_shairt_Category_Adapte.MyViewHolder> {
    private static  onItem_ClickLisiner clickLisiner;
    private Context context;
    private List<T_shairt_Category_Model> categoryModelList;
    private static int listposition=-1;
    public T_shairt_Category_Adapte(Context context, List<T_shairt_Category_Model> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.t_shairt_categorey_show_simple,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        T_shairt_Category_Model item_position=categoryModelList.get(position);

        myViewHolder.textView_pd_id.setText("id:"+item_position.getT_Shairt_Category_Cord());
        myViewHolder.textView_pd_name.setText("Name:"+item_position.getT_Shairt_Category_Name());
        myViewHolder.textView_pd_price.setText("price:"+item_position.getT_Shairt_Category_price());
        myViewHolder.textView_pd_quantity.setText("Quantity:"+item_position.getT_Shairt_Category_quantity());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getT_Shairt_Category_discount());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getT_Shairt_Category_discount());
        myViewHolder.textView_size.setText("Size:"+item_position.getSelect_Size());

        Picasso.get().load(item_position.getProduct_image()).into(myViewHolder.imageView);
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
      private ImageView imageView;
      private TextView textView_pd_id,textView_pd_name,textView_pd_price,textView_pd_quantity,
              textView_discount,textView_size;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

         textView_pd_id=itemView.findViewById(R.id.cate_tShairt_pd_id);
         textView_pd_name=itemView.findViewById(R.id.cate_tShairt_pd_name);
         textView_pd_price=itemView.findViewById(R.id.cate_tShairt_pd_Price);
         textView_discount=itemView.findViewById(R.id.cate_tShairt_discout);
         textView_pd_quantity=itemView.findViewById(R.id.cate_tShairt_pd_quan);
            textView_size=itemView.findViewById(R.id.cate_tShairt_size);
         imageView=itemView.findViewById(R.id.category_t_shirt_image);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
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
    }
    public interface onItem_ClickLisiner {
        void onClick(int position);
        void onDelet(int position);
    }
    void  setOnItemClickLisiner(onItem_ClickLisiner clickLisiner){
        this.clickLisiner=clickLisiner;
    }
}
