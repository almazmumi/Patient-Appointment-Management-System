package kfupm.bader.com.appointmentsystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import kfupm.bader.com.appointmentsystem.AppointmentActivity;
import kfupm.bader.com.appointmentsystem.ClinicActivity;
import kfupm.bader.com.appointmentsystem.R;
import kfupm.bader.com.appointmentsystem.models.Clinic;


public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private List<Clinic> itemList;
    private Context c;

    //Constructor of the class
    public ClinicAdapter(Context c, List<Clinic> itemList) {
        this.c = c;
        listItemLayout = R.layout.row_clinic;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {

        TextView clinicName = holder.clinicName;
        TextView clinicLocation = holder.clinicLocation;
        TextView clinicStatus = holder.clinicStatus;
        ImageView websiteButton = holder.websiteButton;
        ImageView locationButton = holder.locationButton;
        ImageView phoneButton = holder.phoneButton;
        ConstraintLayout parentLayout = holder.parentLayout;
        final ConstraintLayout primaryDetailsButton = holder.primaryDetailsButton;

        clinicName.setText(itemList.get(listPosition).getClinicName());
        clinicLocation.setText(itemList.get(listPosition).getLocation().split(",")[0]);
        clinicStatus.setText(itemList.get(listPosition).getStatusName());


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates an Intent that will load a map of San Francisco
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                c.startActivity(mapIntent);
            }
        });

        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://"+itemList.get(listPosition).getWebsite()));
                c.startActivity(intent);
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+966126020429", null)));
            }
        });







        primaryDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent i = new Intent(v.getContext(),ClinicActivity.class);
                i.putExtra("clinic_ID",itemList.get(listPosition).getClinicID());
                c.startActivity(i);
            }
        });




    }


    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView clinicName,clinicStatus,clinicLocation;
        private ImageView locationButton,websiteButton,phoneButton;
        ConstraintLayout parentLayout;
        ConstraintLayout primaryDetailsButton;

        ViewHolder(View itemView) {
            super(itemView);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.clinicRow_parentLayout);
            primaryDetailsButton = (ConstraintLayout)itemView.findViewById(R.id.clinicRow_primaryDetailsButton);
            clinicName = (TextView)itemView.findViewById(R.id.clinicRow_clinicName);
            clinicStatus = (TextView)itemView.findViewById(R.id.clinicRow_status);
            clinicLocation = (TextView)itemView.findViewById(R.id.clinicRow_locationTV);

            locationButton = (ImageView)itemView.findViewById(R.id.clinicRow_locationButton);
            websiteButton = (ImageView)itemView.findViewById(R.id.clinicRow_websiteButton);
            phoneButton = (ImageView)itemView.findViewById(R.id.clinicRow_phoneButton);



        }


    }
}