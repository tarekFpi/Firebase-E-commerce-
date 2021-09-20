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

public class keybord_mouseCategory_Adapter extends RecyclerView.Adapter<keybord_mouseCategory_Adapter.MyViewHolder> {
    public static  onItem_ClickLisiner clickLisiner;

    private Context context;
    private List<keybord_CategoryModel>categoryModelList;
    private static int listposition=-1;

    public keybord_mouseCategory_Adapter(Context context, List<keybord_CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
     View view=  layoutInflater.inflate(R.layout.keybord_mouse_simple_show,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        keybord_CategoryModel item_position=categoryModelList.get(position);

        myViewHolder.textView_id.setText("Id:"+item_position.getCategory_key_mouse_id());
        myViewHolder.textView_name.setText("Name:"+item_position.getCategory_key_mouse_name());
        myViewHolder.textView_quantity.setText("Quantity:"+item_position.getCategory_key_mouse_quantity());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getCategory_key_mouse_discount());
        myViewHolder.textView_price.setText("price:"+item_position.getCategory_key_mouse_price());

        Picasso.get().load(item_position.getCategory_key_mouse_Image()).into(myViewHolder.imageView);
        setAnimiton(myViewHolder.itemView,position);
    }

    void setAnimiton(View viewAnimition,int position) {
        if (position >listposition) {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(1500);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }
    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
    private ImageView imageView;
    private TextView textView_name,textView_price,textView_quantity,textView_id,textView_discount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_id=itemView.findViewById(R.id.cate_key_mouseShow_pd_id);
            textView_name=itemView.findViewById(R.id.cate_key_mouseShow_name);
            textView_price=itemView.findViewById(R.id.cate_key_mouseShow_pd_Price);
            textView_quantity=itemView.findViewById(R.id.cate_key_mouse_Show_pd_quan);
            textView_discount=itemView.findViewById(R.id.cate_key_mouseShow_discout);
            imageView=itemView.findViewById(R.id.category_key_mouseShow_image);

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
