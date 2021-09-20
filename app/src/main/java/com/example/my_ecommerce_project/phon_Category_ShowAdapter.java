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

public class phon_Category_ShowAdapter extends RecyclerView.Adapter<phon_Category_ShowAdapter.MyViewHolder> {
    private static  onItem_ClickLisiner clickLisiner;
    private Context context;
    private List<phon_Category_Model>phon_category_models;
    private static int listposition=-1;

    public phon_Category_ShowAdapter(Context context, List<phon_Category_Model> phon_category_models) {
        this.context = context;
        this.phon_category_models = phon_category_models;
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
        phon_Category_Model item_position=phon_category_models.get(position);
        myViewHolder.textView_pdName.setText("Name:"+item_position.getPhon_category_Name());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getPhon_category_discount());
        myViewHolder.textView_quanity.setText("Quantity:"+item_position.getPhon_category_quantity());
        myViewHolder.textView_price.setText("Price:"+item_position.getPhon_category_price());
        myViewHolder.textView_id.setText("Id:"+item_position.getPhon_category_id());

        myViewHolder.textView_size.setVisibility(View.INVISIBLE);

        Picasso.get().load(item_position.getPhon_category_image()).into(myViewHolder.imageView);
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
        return phon_category_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
private ImageView imageView;
private TextView textView_id,textView_pdName,textView_price,textView_discount,textView_quanity,textView_size;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.category_baby_Show_image);
            textView_id=itemView.findViewById(R.id.cate_babyShow_pd_id);
            textView_pdName=itemView.findViewById(R.id.cate_baby_Show_name);
            textView_price=itemView.findViewById(R.id.cate_baby_pd_Price);
            textView_quanity=itemView.findViewById(R.id.cate_baby_Show_pd_quan);
            textView_discount=itemView.findViewById(R.id.cate_baby_Show_discout);
            textView_size=itemView.findViewById(R.id.cate_baby_Show_size);


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
