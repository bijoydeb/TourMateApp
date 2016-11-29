package com.summons.tourmateapp.DetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by engrb on 24-Nov-16.
 */

public class Result {

    @SerializedName("adr_address")
    @Expose
    private String adrAddress;
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("formatted_phone_number")
    @Expose
    private String formattedPhoneNumber;

    @SerializedName("international_phone_number")
    @Expose
    private String internationalPhoneNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("opening_hours")
    @Expose
    private OpeningHours openingHours;

    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("vicinity")
    @Expose
    private String vicinity;
    @SerializedName("website")
    @Expose
    private String website;


    /**
     *
     * @return
     * The adrAddress
     */
    public String getAdrAddress() {
        return adrAddress;
    }

    /**
     *
     * @param adrAddress
     * The adr_address
     */
    public void setAdrAddress(String adrAddress) {
        this.adrAddress = adrAddress;
    }

    /**
     *
     * @return
     * The formattedAddress
     */
    public String getFormattedAddress() {
        return formattedAddress;
    }

    /**
     *
     * @param formattedAddress
     * The formatted_address
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    /**
     *
     * @return
     * The formattedPhoneNumber
     */
    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    /**
     *
     * @param formattedPhoneNumber
     * The formatted_phone_number
     */
    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }



    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    /**
     *
     * @param internationalPhoneNumber
     * The international_phone_number
     */
    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The openingHours
     */
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    /**
     *
     * @param openingHours
     * The opening_hours
     */
    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }


    /**
     *
     * @return
     * The placeId
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     *
     * @param placeId
     * The place_id
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
    /**
     *
     * @return
     * The vicinity
     */
    public String getVicinity() {
        return vicinity;
    }

    /**
     *
     * @param vicinity
     * The vicinity
     */
    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    /**
     *
     * @return
     * The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     *
     * @param website
     * The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }
}
