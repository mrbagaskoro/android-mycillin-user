package com.mycillin.user.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mycillin.user.R;
import com.mycillin.user.list.FacilityList;

/**
 * Created by mrbagaskoro on 16-Dec-17.
 */

public class FacilityAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public FacilityAdapter(Context context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.maps_info_window, null);

        View line = view.findViewById(R.id.mapsInfoWindow_v_line);
        ImageView facilityPicture = view.findViewById(R.id.mapsInfoWindow_iv_facilityPicture);
        TextView facilityName = view.findViewById(R.id.mapsInfoWindow_tv_facilityName);
        TextView facilityDistance = view.findViewById(R.id.mapsInfoWindow_tv_facilityDistance);
        TextView facilityAddress = view.findViewById(R.id.mapsInfoWindow_tv_facilityAddress);
        TextView facilityPhone = view.findViewById(R.id.mapsInfoWindow_tv_facilityPhone);
        View line2 = view.findViewById(R.id.mapsInfoWindow_v_line2);
        TextView navigationInfo = view.findViewById(R.id.mapsInfoWindow_tv_navigationInfo);

        final FacilityList facilityList = (FacilityList) marker.getTag();
        if(facilityList != null) {
            if(!facilityList.getImage().equals("")) {

                line.setVisibility(View.VISIBLE);
                facilityAddress.setVisibility(View.VISIBLE);
                facilityName.setVisibility(View.VISIBLE);
                facilityDistance.setVisibility(View.VISIBLE);
                facilityPicture.setVisibility(View.VISIBLE);
                facilityPhone.setVisibility(View.VISIBLE);
                line2.setVisibility(View.VISIBLE);
                navigationInfo.setVisibility(View.VISIBLE);

                RequestOptions requestOptions = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.banner_default)
                        .fitCenter();

                Glide.with(context)
                        .load(facilityList.getImage())
                        .apply(requestOptions)
                        .into(facilityPicture);
            }

            if(!facilityList.getDistance().equals("")) {
                String distance = ((Math.round(Double.parseDouble(facilityList.getDistance()) * 100.0)) / 100.0) + "";
                facilityDistance.setText(distance + " KM");
            }

            facilityName.setText(facilityList.getName());
            facilityAddress.setText(facilityList.getAddress());
            facilityPhone.setText(facilityList.getPhone());
        }
        else {
            facilityName.setVisibility(View.VISIBLE);
            facilityName.setText(context.getResources().getString(R.string.serviceBookDoctorActivity_myLocationMarker));
            line.setVisibility(View.GONE);
            facilityAddress.setVisibility(View.GONE);
            facilityDistance.setVisibility(View.GONE);
            facilityPicture.setVisibility(View.GONE);
            facilityPhone.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            navigationInfo.setVisibility(View.GONE);
        }

        return view;
    }
}
