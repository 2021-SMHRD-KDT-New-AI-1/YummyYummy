package com.chj.yummyproject;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CustomDialog {
    private Context context;

    public CustomDialog(Context context) {
        this.context = context;
    }

    public void callFunction(int[] member_avoid, int[] food_avoid) {
        Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.custom_dialog);
        dlg.show();

        ConstraintLayout li_halar = dlg.findViewById(R.id.li_halar);
        ConstraintLayout li_vegan = dlg.findViewById(R.id.li_vegan);
        ConstraintLayout li_egg = dlg.findViewById(R.id.li_egg);
        ConstraintLayout li_nut = dlg.findViewById(R.id.li_nut);
        ConstraintLayout li_fish = dlg.findViewById(R.id.li_fish);
        ConstraintLayout li_bean = dlg.findViewById(R.id.li_bean);

        ConstraintLayout[] cons_list = {li_halar, li_vegan, li_egg, li_nut, li_fish, li_bean};
        for (int i=0;i<member_avoid.length;i++) {
            if (member_avoid[i] == 1 && food_avoid[i] == 1) {
                cons_list[i].setVisibility(View.VISIBLE);
            } else {
                cons_list[i].setVisibility(View.GONE);
            }
        }

        Button btn_yes = dlg.findViewById(R.id.btn_yes);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }
}
