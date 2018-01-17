package com.mycillin.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycillin.user.R;

/**
 * Created by 16003041 on 29/11/2017.
 */

public class DialogImagePickerAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;

    public DialogImagePickerAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.dialog_image_picker_content, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.dialogImagePickerContent_tv_textView);
            viewHolder.imageView = view.findViewById(R.id.dialogImagePickerContent_iv_imageView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Context context = parent.getContext();
        switch (position) {
            case 0:
                viewHolder.textView.setText(R.string.dialogImagePickerAdapter_galleryTitle);
                viewHolder.imageView.setImageResource(R.drawable.ic_menu_gallery);
                break;
            case 1:
                viewHolder.textView.setText(R.string.dialogImagePickerAdapter_cameraTitle);
                viewHolder.imageView.setImageResource(R.drawable.ic_menu_camera);
                break;
        }

        return view;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
