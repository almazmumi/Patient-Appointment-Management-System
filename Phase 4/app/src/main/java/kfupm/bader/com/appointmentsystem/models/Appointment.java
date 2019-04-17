package kfupm.bader.com.appointmentsystem.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {

    @SerializedName("appointment_ID")
    @Expose
    private String appointmentID;
    @SerializedName("recept_comment")
    @Expose
    private String receptComment;

    public String getReceptComment() {
        return receptComment;
    }




    public String getDentistPhone() {
        return dentistPhone;
    }

    public String getClinicPhone() {
        return clinicPhone;
    }

    @SerializedName("dentist_phone")
    @Expose
    private String dentistPhone;

    @SerializedName("clinic_phone")
    @Expose
    private String clinicPhone;

    @SerializedName("apm_date")
    @Expose
    private String apmDate;
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
    @SerializedName("dwebsite")
    @Expose
    private String dwebsite;
    @SerializedName("demail")
    @Expose
    private String demail;
    @SerializedName("drating")
    @Expose
    private Object drating;
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
    @SerializedName("status_name")
    @Expose
    private String statusName;
    @SerializedName("status_type")
    @Expose
    private String statusType;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("clinic_description")
    @Expose
    private String clinicDescription;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("cwebsite")
    @Expose
    private String cwebsite;
    @SerializedName("cemail")
    @Expose
    private String cemail;
    @SerializedName("crating")
    @Expose
    private Object crating;

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getApmDate() {
        return apmDate;
    }

    public void setApmDate(String apmDate) {
        this.apmDate = apmDate;
    }

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

    public String getDwebsite() {
        return dwebsite;
    }

    public void setDwebsite(String dwebsite) {
        this.dwebsite = dwebsite;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public Object getDrating() {
        return drating;
    }

    public void setDrating(Object drating) {
        this.drating = drating;
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

    public String getCwebsite() {
        return cwebsite;
    }

    public void setCwebsite(String cwebsite) {
        this.cwebsite = cwebsite;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public Object getCrating() {
        return crating;
    }

    public void setCrating(Object crating) {
        this.crating = crating;
    }

}