package com.lq.drawertest;

import android.util.SparseArray;
import android.view.View;

public class CommonViewHolder {
    public static <T extends View> T get(View view,int id){
        SparseArray<T> sparseArray = (SparseArray<T>) view.getTag();
        if (sparseArray == null){
            sparseArray = new SparseArray<>();
            view.setTag(sparseArray);
        }
        T childView = sparseArray.get(id);
        if (childView == null){
            childView = view.findViewById(id);
            sparseArray.put(id,childView);
        }
        return childView;

    }
}
