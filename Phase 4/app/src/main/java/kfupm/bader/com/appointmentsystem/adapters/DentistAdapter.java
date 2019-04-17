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

import java.util.List;
import java.util.Map;

import kfupm.bader.com.appointmentsystem.DentistActivity;
import kfupm.bader.com.appointmentsystem.R;
import kfupm.bader.com.appointmentsystem.models.Dentist;


public class DentistAdapter extends RecyclerView.Adapter<DentistAdapter.ViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private List<Dentist> itemList;
    private Context c;

    //Constructor of the class
    public DentistAdapter(Context c, List<Dentist> itemList) {
        this.c = c;
        listItemLayout = R.layout.row_doctor;
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

        TextView dentistName = holder.dentistName;
        TextView dentistSpecialty = holder.dentistSpecialty;
        TextView dentistStatus = holder.dentistStatus;
        ImageView websiteBTN = holder.websiteBTN;
        ImageView locationBTN = holder.locationBTN;
        ImageView phoneBTN = holder.phoneBTN;
        ImageView profilePicture = holder.profilePic;
        ConstraintLayout primaryBookButton = holder.primaryBookButton;
        ConstraintLayout primaryDetailsButton = holder.primaryDetailsButton;



        dentistName.setText("Dr. " + itemList.get(listPosition).getFname()+ " "+itemList.get(listPosition).getLname());
        dentistSpecialty.setText(itemList.get(listPosition).getSpecialtyName());
        dentistStatus.setText(itemList.get(listPosition).getStatusName());
        int j = (int)(Math.random()*3)+1;
        switch (j){
            case 1:
                profilePicture.setImageResource(R.drawable.doctors_profile1);
                break;
            case 2:
                profilePicture.setImageResource(R.drawable.doctors_profile_2);
                break;
            case 3:
                profilePicture.setImageResource(R.drawable.doctors_profile_3);
                break;
        }


        locationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates an Intent that will load a map of San Francisco
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                c.startActivity(mapIntent);
            }
        });

        websiteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://"+itemList.get(listPosition).getWebsite()));
                c.startActivity(intent);
            }
        });

        phoneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+966126020429", null)));
            }
        });


        primaryDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent i = new Intent(v.getContext(),DentistActivity.class);
                i.putExtra("dentist_ID",itemList.get(listPosition).getDentistID());
                c.startActivity(i);
            }
        });




    }


    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dentistName,dentistStatus,dentistSpecialty;
        private ImageView locationBTN,websiteBTN,phoneBTN,profilePic;


        ConstraintLayout primaryBookButton;
        ConstraintLayout primaryDetailsButton;
        ViewHolder(View itemView) {
            super(itemView);
            dentistName = (TextView)itemView.findViewById(R.id.dentistRow_dentistName);
            dentistStatus = (TextView)itemView.findViewById(R.id.dentistRow_dentistStatus);
            dentistSpecialty = (TextView)itemView.findViewById(R.id.dentistRow_dentistsSpeciality);

            locationBTN = (ImageView)itemView.findViewById(R.id.dentistRow_locationBTN);
            websiteBTN = (ImageView)itemView.findViewById(R.id.dentistRow_websiteBTN);
            phoneBTN = (ImageView)itemView.findViewById(R.id.dentistRow_phoneBTN);


            profilePic = (ImageView)itemView.findViewById(R.id.dentistRow_profilePic);
            primaryBookButton = (ConstraintLayout) itemView.findViewById(R.id.clinicActivity_primaryBookButton);
            primaryDetailsButton = (ConstraintLayout)itemView.findViewById(R.id.dentistRow_primaryDetailsButton);


        }


    }
}