package com.example.my_ecommerce_project;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class femal_pd_rate_Adapter extends RecyclerView.Adapter<femal_pd_rate_Adapter.MyViewHolder> {
 private onItemClickListener click_listener;
 public String mystatus="Admin";

 private onItemClickListener_item_User User_Item_ClickLisiner;

 private Context context;
 private List<femal_product_Model> myModellist;
 private static int listposition=-1;



 public femal_pd_rate_Adapter(Context context, List<femal_product_Model> myModellist) {
  this.context = context;
  this.myModellist = myModellist;
 }

 @NonNull
 @Override
 public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
  LayoutInflater layoutInflater = LayoutInflater.from(context);
  View view = layoutInflater.inflate(R.layout.ladis_product_rate, viewGroup, false);

  return new MyViewHolder(view);
   }

  @Override
  public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

  final femal_product_Model item = myModellist.get(position);

  myViewHolder.textView_pdname.setText("product Name:" + item.getProduct_name());
  //myViewHolder.textView_pd_desription.setText("Desription:"+item.getProduct_Desrcip());
  myViewHolder.textView_pd_price.setText("price:" + item.getProduct_price());

  Picasso.get().load(item.getProduct_Image()).into(myViewHolder.imageView);
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
  return myModellist.size();
 }

    public void filterdList(List<femal_product_Model> filterList) {
  myModellist=filterList;
  notifyDataSetChanged();
    }

  public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
   private ImageView imageView;
   private TextView textView_pdname, textView_pd_desription, textView_pd_price;


   public MyViewHolder(@NonNull View itemView) {
    super(itemView);

    imageView = itemView.findViewById(R.id.femal_rateImage);
    textView_pdname = itemView.findViewById(R.id.text_pdName_Id);
    // textView_pd_desription=itemView.findViewById(R.id.text_desript_Id);
    textView_pd_price = itemView.findViewById(R.id.text_pdPrice);

   /*imageView_delete = itemView.findViewById(R.id.image_delete_id);
   imageView_update = itemView.findViewById(R.id.pd_Update_image);*/

    itemView.setOnClickListener(this);
    itemView.setOnCreateContextMenuListener(this);

   }

   @Override
   public void onClick(View v) {
    int position = getAdapterPosition();
    if (!mystatus.equals("User")) {
     click_listener.onItemclick(position);
    }else{
     User_Item_ClickLisiner.onItemclick(position);
    }
   }

   @Override
   public boolean onMenuItemClick(MenuItem item) {
    int menu_position = getAdapterPosition();
   /* if(mystatus.equals("User")) {
     onMenuItemClick(item.setEnabled(false));
  */
    switch (item.getItemId()) {
     case 1:
      click_listener.onUpdate(menu_position);
      break;

     case 2:
      click_listener.onDelete(menu_position);
      break;
    }

    return true;
   }

   @Override
   public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    if (!mystatus.equals("User")) {
     menu.setHeaderTitle("Do Your Any Action");
     MenuItem onUpdate = menu.add(menu.NONE, 1, 1, "Update");
     MenuItem DeleteTask = menu.add(menu.NONE, 2, 2, "Delete");

     DeleteTask.setOnMenuItemClickListener(this);
     onUpdate.setOnMenuItemClickListener(this);

    } else {

     menu.setHeaderTitle("Do Your Any Action");
     menu.setGroupEnabled(1, false);
     menu.setGroupEnabled(2, false);
    }

   }
  }

   public interface onItemClickListener {
    void onItemclick(int position);
    void onUpdate(int position);
    void onDelete(int position);
   }

   public interface onItemClickListener_item_User {
    void onItemclick(int position);
   }
 void setUser_On_ClickListener(onItemClickListener_item_User clickListener) {
  this.User_Item_ClickLisiner = clickListener;
 }

   void setOnItemClickListener(onItemClickListener clickListener) {
    this.click_listener = clickListener;
   }



}
