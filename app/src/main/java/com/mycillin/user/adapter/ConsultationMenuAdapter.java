package com.mycillin.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycillin.user.R;
import com.mycillin.user.fragment.HomeFragment;
import com.mycillin.user.list.ConsultationMenuList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 16003041 on 10/11/2017.
 */

public class ConsultationMenuAdapter extends RecyclerView.Adapter<ConsultationMenuAdapter.MyViewHolder> {
    private List<ConsultationMenuList> consultationMenuLists;
    private ArrayList<ConsultationMenuList> arrayConsultationMenuLists;
    private Context context;

    public ConsultationMenuAdapter(List<ConsultationMenuList> consultationMenuLists, Context context) {
        this.consultationMenuLists = consultationMenuLists;
        this.arrayConsultationMenuLists = new ArrayList<>();
        this.arrayConsultationMenuLists.addAll(consultationMenuLists);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_consultation_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ConsultationMenuList resultList = consultationMenuLists.get(position);
        holder.title.setText(resultList.getTitle());

        switch (resultList.getId()) {
            case HomeFragment.KEY_ID_MENU_ALLERGIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_allergist));
                break;
            case HomeFragment.KEY_ID_MENU_CARDIOLOGIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_cardiologist));
                break;
            case HomeFragment.KEY_ID_MENU_DENTIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_dentist));
                break;
            case HomeFragment.KEY_ID_MENU_DERMATOLOGIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_dermatologist));
                break;
            case HomeFragment.KEY_ID_MENU_GENERAL_PRACTITIONER:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_general_practicioners));
                break;
            case HomeFragment.KEY_ID_MENU_INTERNIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_internist));
                break;
            case HomeFragment.KEY_ID_MENU_NEUROLOGIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_neurologist));
                break;
            case HomeFragment.KEY_ID_MENU_OBGYN:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_obstetrician_gynecologist));
                break;
            case HomeFragment.KEY_ID_MENU_OPHTHALMOLOGIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_ophthalmologist));
                break;
            case HomeFragment.KEY_ID_MENU_ORTHOPAEDIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_orthopaedist));
                break;
            case HomeFragment.KEY_ID_MENU_OTOLARYNGOLOGIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_otolaryngologist));
                break;
            case HomeFragment.KEY_ID_MENU_PEDIATRICIAN:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_pediatrician));
                break;
            case HomeFragment.KEY_ID_MENU_PSYCHIATRIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_psychiatrist));
                break;
            case HomeFragment.KEY_ID_MENU_PULMONOLOGIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_pulmonologist));
                break;
            case HomeFragment.KEY_ID_MENU_RADIOLOGIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_radiologist));
                break;
            case HomeFragment.KEY_ID_MENU_GENERAL_SURGEON:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_surgeon));
                break;
            case HomeFragment.KEY_ID_MENU_UROLOGIST:
                holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_consultation_urologist));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return consultationMenuLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rowMenuConsultationList_tv_title);
            icon = itemView.findViewById(R.id.rowMenuConsultationList_iv_icon);
        }
    }
}
