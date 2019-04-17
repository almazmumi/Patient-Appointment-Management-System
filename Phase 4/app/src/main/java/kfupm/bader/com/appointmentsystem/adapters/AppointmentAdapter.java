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
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kfupm.bader.com.appointmentsystem.AppointmentActivity;
import kfupm.bader.com.appointmentsystem.R;
import kfupm.bader.com.appointmentsystem.models.Appointment;


public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private List<Appointment> itemList;
    private Context c;

    //Constructor of the class
    public AppointmentAdapter(Context c, List<Appointment> itemList) {
        this.c = c;
        listItemLayout = R.layout.row_appointment;
        this.itemList = itemList;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
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
        TextView dentistName = holder.dentistName;
        TextView status = holder.status;
        TextView appointmentDateTV = holder.appointmentDate;
        TextView location = holder.location;
        TextView daysLeft = holder.daysLeft;
        TextView daysLeftTextView = holder.daysLeftTextView;
        TextView email = holder.email;
        TextView clinicName = holder.clinicName;
        ConstraintLayout parentLayout = holder.parentLayout;
        final ConstraintLayout detailsBox = holder.detailsBox;
        final ConstraintLayout primaryDetailsButton = holder.primaryDetailsButton;
        ConstraintLayout detailsBoxAddButton = holder.detailsBoxAddButton;
        ConstraintLayout detailsBoxDetailsButton = holder.detailsBoxDetailsButton;
        ImageView googleMap = holder.googleMap;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
            Date todayDate = new Date();
            Date appointmentDate = dateFormat.parse(itemList.get(listPosition).getApmDate());
            long difference = appointmentDate.getTime() - todayDate.getTime();
            float daysBetween = (difference / (1000*60*60*24));
            daysLeft.setText(daysBetween>0?(int)daysBetween+"":"");
            daysLeftTextView.setText(daysBetween>0?"days left":"");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        googleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates an Intent that will load a map of San Francisco
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                c.startActivity(mapIntent);
            }
        });


        primaryDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent i = new Intent(v.getContext(),AppointmentActivity.class);
                i.putExtra("appointment_ID",itemList.get(listPosition).getAppointmentID());
                c.startActivity(i);
            }
        });



        dentistName.setText("Dr. "+itemList.get(listPosition).getFname() + " " + itemList.get(listPosition).getLname());
        status.setText(itemList.get(listPosition).getStatusName());
        appointmentDateTV.setText(itemList.get(listPosition).getApmDate());
        location.setText("Office# " + itemList.get(listPosition).getClinicNumber() );
        clinicName.setText(itemList.get(listPosition).getClinicName());
        email.setText(itemList.get(listPosition).getCemail());

    }


    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dentistName,status,appointmentDate,location,daysLeft,daysLeftTextView;
        private TextView phoneNumber, email, clinicName;
        ConstraintLayout parentLayout,detailsBox;
        ConstraintLayout primaryDetailsButton,detailsBoxAddButton,detailsBoxDetailsButton;
        private ImageView googleMap;

        ViewHolder(View itemView) {
            super(itemView);
            dentistName = (TextView)itemView.findViewById(R.id.clinicRow_clinicName);
            status = (TextView)itemView.findViewById(R.id.clinicRow_status);
            appointmentDate = (TextView)itemView.findViewById(R.id.appointmentRow_date);
            location = (TextView)itemView.findViewById(R.id.appointmentRow_location);
            daysLeft = (TextView)itemView.findViewById(R.id.appointmentRow_daysLeft);
            daysLeftTextView = (TextView)itemView.findViewById(R.id.appointmentRow_appointmentDaysLeftTextView);
            email = (TextView)itemView.findViewById(R.id.appointmentRow_Email);
            parentLayout = (ConstraintLayout) itemView.findViewById(R.id.appointmentRow_parentLayout);
            detailsBox = (ConstraintLayout) itemView.findViewById(R.id.appointmentRow_detailsBox);
            googleMap = (ImageView)itemView.findViewById(R.id.apointmentRow_googleMap);
            primaryDetailsButton = (ConstraintLayout)itemView.findViewById(R.id.appointmentRow_primaryDetailsButton);
            detailsBoxDetailsButton = (ConstraintLayout)itemView.findViewById(R.id.appointmentRow_detailsBoxDetailsButton);
            clinicName = (TextView)itemView.findViewById(R.id.appointmentRow_clinicName);
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailsBox.getVisibility() == View.GONE){
                        detailsBox.setVisibility(View.VISIBLE);
                        primaryDetailsButton.setVisibility(View.GONE);
                    }else{
                        detailsBox.setVisibility(View.GONE);
                        primaryDetailsButton.setVisibility(View.VISIBLE);
                    }
                }
            });


        }


    }
}