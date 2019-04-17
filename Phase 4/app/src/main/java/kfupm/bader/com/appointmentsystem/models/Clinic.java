package kfupm.bader.com.appointmentsystem.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clinic {

    @SerializedName("clinic_ID")
    @Expose
    private String clinicID;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("clinic_description")
    @Expose
    private String clinicDescription;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("rating")
    @Expose
    private Object rating;
    @SerializedName("clinicman_ID")
    @Expose
    private String clinicmanID;
    @SerializedName("status_ID")
    @Expose
    private String statusID;
    @SerializedName("clinic_phone")
    @Expose
    private Object clinicPhone;
    @SerializedName("status_name")
    @Expose
    private String statusName;
    @SerializedName("status_type")
    @Expose
    private String statusType;
    @SerializedName("status_description")
    @Expose
    private String statusDescription;

    public String getClinicID() {
        return clinicID;
    }

    public void setClinicID(String clinicID) {
        this.clinicID = clinicID;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicDescription() {
        return clinicDescription;
    }

    public void setClinicDescription(String clinicDescription) {
        this.clinicDescription = clinicDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public String getClinicmanID() {
        return clinicmanID;
    }

    public void setClinicmanID(String clinicmanID) {
        this.clinicmanID = clinicmanID;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public Object getClinicPhone() {
        return clinicPhone;
    }

    public void setClinicPhone(Object clinicPhone) {
        this.clinicPhone = clinicPhone;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

}
