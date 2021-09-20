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

public class Shose_CategoryAdapter extends RecyclerView.Adapter<Shose_CategoryAdapter.MyviewHolder> {
    private static  onItem_ClickLisiner clickLisiner;
    private Context context;
    private List<shose_category_Add> shosePdModelList;
    private static int listposition=-1;
    public Shose_CategoryAdapter(Context context, List<shose_category_Add> shosePdModelList) {
        this.context = context;
        this.shosePdModelList = shosePdModelList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
      View view=  layoutInflater.inflate(R.layout.t_shairt_categorey_show_simple,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myViewHolder, int position) {
        shose_category_Add item_position=shosePdModelList.get(position);
        myViewHolder.textView_pd_id.setText("id:"+item_position.getPd_id());
        myViewHolder.textView_pd_name.setText("Name:"+item_position.getCategory_Shose_name());
        myViewHolder.textView_pd_price.setText("price:"+item_position.getCategory_Shose_price());
        myViewHolder.textView_pd_quantity.setText("Quantity:"+item_position.getCategory_Shose_quantity());
        myViewHolder.textView_discount.setText("Discount:"+item_position.getCategory_Shose_discount());
        myViewHolder.textView_pd_size.setText("Size:"+item_position.getCategory_Shose_size());

        Picasso.get().load(item_position.getCategory_Shose_image()).into(myViewHolder.imageView);
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
        return shosePdModelList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private ImageView imageView;
        private TextView textView_pd_id,textView_pd_name,textView_pd_price,textView_pd_quantity,
                textView_discount,textView_pd_size;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_pd_id=itemView.findViewById(R.id.cate_tShairt_pd_id);
            textView_pd_name=itemView.findViewById(R.id.cate_tShairt_pd_name);
            textView_pd_price=itemView.findViewById(R.id.cate_tShairt_pd_Price);
            textView_discount=itemView.findViewById(R.id.cate_tShairt_discout);
            textView_pd_quantity=itemView.findViewById(R.id.cate_tShairt_pd_quan);
            textView_pd_size=itemView.findViewById(R.id.cate_tShairt_size);

            imageView=itemView.findViewById(R.id.category_t_shirt_image);

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
