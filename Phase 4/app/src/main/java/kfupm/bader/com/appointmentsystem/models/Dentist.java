package kfupm.bader.com.appointmentsystem.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dentist {
    @Override
    public String toString() {
        return getFname() + " " + getLname();
    }

    @SerializedName("dentist_ID")
    @Expose
    private String dentistID;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("start_career_date")
    @Expose
    private String startCareerDate;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("rating")
    @Expose
    private Object rating;
    @SerializedName("specialty_ID")
    @Expose
    private String specialtyID;
    @SerializedName("clinic_ID")
    @Expose
    private String clinicID;
    @SerializedName("clinic_office")
    @Expose
    private String clinicOffice;
    @SerializedName("clinic_number")
    @Expose
    private String clinicNumber;
    @SerializedName("status_ID")
    @Expose
    private String statusID;
    @SerializedName("dentist_phone")
    @Expose
    private Object dentistPhone;
    @SerializedName("status_name")
    @Expose
    private String statusName;
    @SerializedName("status_type")
    @Expose
    private String statusType;
    @SerializedName("status_description")
    @Expose
    private String statusDescription;
    @SerializedName("specialty_name")
    @Expose
    private String specialtyName;
    @SerializedName("description")
    @Expose
    private String description;

    public String getDentistID() {
        return dentistID;
    }

    public void setDentistID(String dentistID) {
        this.dentistID = dentistID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStartCareerDate() {
        return startCareerDate;
    }

    public void setStartCareerDate(String startCareerDate) {
        this.startCareerDate = startCareerDate;
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

    public String getSpecialtyID() {
        return specialtyID;
    }

    public void setSpecialtyID(String specialtyID) {
        this.specialtyID = specialtyID;
    }

    public String getClinicID() {
        return clinicID;
    }

    public void setClinicID(String clinicID) {
        this.clinicID = clinicID;
    }

    public String getClinicOffice() {
        return clinicOffice;
    }

    public void setClinicOffice(String clinicOffice) {
        this.clinicOffice = clinicOffice;
    }

    public String getClinicNumber() {
        return clinicNumber;
    }

    public void setClinicNumber(String clinicNumber) {
        this.clinicNumber = clinicNumber;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public Object getDentistPhone() {
        return dentistPhone;
    }

    public void setDentistPhone(Object dentistPhone) {
        this.dentistPhone = dentistPhone;
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

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
