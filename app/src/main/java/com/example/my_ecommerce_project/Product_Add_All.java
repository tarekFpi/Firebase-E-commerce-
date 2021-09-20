package com.example.my_ecommerce_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class Product_Add_All extends AppCompatActivity {
private CardView cardView;
private ImageSlider imageSlider;

    private CardView cardView_femal_dress,cardView_AllCap,
            cardView_lapTop,cardView_T_shairt,cardView_recipe,cardView_shose,cardView_android_phon,
            cardView_babyProduct,cardView_keybor_mouse,cardView_user_allOrder,cardView_order_accept_Notification
            ,cardView_user_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__add__all);

        imageSlider=(ImageSlider) findViewById(R.id.admin_imageSlider);
        List<SlideModel> slideModels=new ArrayList<>();
       slideModels.add(new SlideModel(R.drawable.dress2s,"Ladis", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.kirill_martynov,"Desktop", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.den_trushtin,"T-Shirt", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.john,"Man", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.andre,"Cap", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.keybord,"keybord", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.hadphon,"Hardphon", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.test5,"Tuna Patties Recipe", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.test5,"Tuna Patties Recipe", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mouse1,"MOuse", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.redmie,"Redmie", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        cardView_femal_dress=(CardView)findViewById(R.id.Card_femal_add);
        cardView_AllCap=(CardView)findViewById(R.id.card_cap_add);
        cardView_android_phon=(CardView)findViewById(R.id.phon_card_add);
        cardView_babyProduct=(CardView)findViewById(R.id.baby_card_add);
        cardView_keybor_mouse=(CardView)findViewById(R.id.keybord_mouse_add);
        cardView_lapTop=(CardView)findViewById(R.id.laptop_card_add);
        cardView_recipe=(CardView)findViewById(R.id.card_recipe_add);
        cardView_shose=(CardView)findViewById(R.id.card_shose_add);
        cardView_T_shairt=(CardView)findViewById(R.id.card_t_shairt_add);

        cardView_user_allOrder=(CardView)findViewById(R.id.oreder_card_show);
        cardView_user_information=(CardView)findViewById(R.id.user_login_card_Id);

        cardView_order_accept_Notification=(CardView)findViewById(R.id.order_accept_Card);

        cardView_femal_dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Femal_DressAdd.class);
                startActivity(intent);
            }
        });


        cardView_T_shairt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),T_shirt_Product_Add.class);
                startActivity(intent);
            }
        });
        cardView_shose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Shoes_product_Add.class);
                startActivity(intent);
            }
        });

        cardView_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RecipeAdd.class);
                startActivity(intent);
            }
        });

        cardView_lapTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Laptop_keybordAdd.class);
                startActivity(intent);
            }
        });

        cardView_babyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Baby_product_Add.class);
                startActivity(intent);
            }
        });

        cardView_keybor_mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),keybord_Mouse_Add.class);
                startActivity(intent);
            }
        });

        cardView_lapTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Laptop_keybordAdd.class);
                startActivity(intent);
            }
        });

        cardView_AllCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),cap_prductAdd.class);
                startActivity(intent);
            }
        });

        cardView_android_phon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),phon_pd_add.class);
                startActivity(intent);
            }
        });

        cardView_user_allOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Show_all_user_Order.class);
                startActivity(intent);
            }
        });

        cardView_order_accept_Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Order_accept_andNotification_show.class);
                startActivity(intent);
            }
        });

        cardView_user_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),All_User_longin_Show.class);
                startActivity(intent);
            }
        });
    }
}