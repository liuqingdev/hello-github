package com.lq.drawertest;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DialogTitle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PopupActivity extends AppCompatActivity {
    private PopupActivity context = this;
    private static final String TAG = "PopupActivity";
    private TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwindow);
        tv = findViewById(R.id.tv);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               new MyDialog(context, new MyClickListener() {
                   @Override
                   public void doSure(String s) {
                        tv.setText(s);
                   }
               }).show();

                return true;
            }
        });

        String lastData = getIntent().getStringExtra("data");
        Log.d(TAG,"key: "+lastData);
        List<entity> user = (List<entity>) BaseApplication.getMap().get(lastData);
        for (entity e: user){
            Log.d(TAG,"user:"+e);
        }


    }

    public void showPop(View view){
        View contentView = LayoutInflater.from(context).inflate(R.layout.content_popup,null);
        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,false);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG,"onTouch:");
                return true;
            }
        });
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow_bg));
        popupWindow.showAsDropDown(view);
    }

    class MyDialog extends Dialog {
        private EditText editText;
        private Button sureBtn;
        private Button cancelBtn;
        private Context context;
        private MyClickListener listener;

        protected MyDialog(@NonNull Context context, final MyClickListener listener) {
            super(context,R.style.common_select_dialog_style);
            View view = LayoutInflater.from(context).inflate(R.layout.update_textview,null,false);
            setContentView(view);
            Window dialogWindow = getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();

            DisplayMetrics d = context.getResources().getDisplayMetrics();
            lp.width = (int) (d.widthPixels*0.75);
            dialogWindow.setAttributes(lp);

            editText = view.findViewById(R.id.edit);
            sureBtn = view.findViewById(R.id.sure_btn);
            cancelBtn = view.findViewById(R.id.cancel_btn);
            this.listener = listener;
            sureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = editText.getText().toString();
                    listener.doSure(text);
                    dismiss();
                }
            });
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }

    }

    interface MyClickListener{
        void doSure(String s);

    }
}
