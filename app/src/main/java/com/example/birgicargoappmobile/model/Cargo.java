package com.example.birgicargoappmobile.model;

import com.google.gson.annotations.SerializedName;

public class Cargo {
    private int id;

    @SerializedName("ТипТС")
    private String vehicleType;

    @SerializedName("Вес")
    private double weight;

    @SerializedName("Объем")
    private double volume;

    @SerializedName("Товар")
    private String product;

    @SerializedName("Откуда")
    private String fromLocation;

    @SerializedName("Куда")
    private String toLocation;

    @SerializedName("ТипПогрузки")
    private String loadingType;

    @SerializedName("ДеталиПогрузки")
    private String loadingDetails;

    @SerializedName("Даты")
    private String dates;

    @SerializedName("ЦенаПоКарте")
    private double priceByCard;

    @SerializedName("ЦенаНДС")
    private double priceWithVat;

    @SerializedName("Торг/без_торга")
    private String bargainOrNoBargain;

    @SerializedName("КонтактныйТелефон")
    private String contactPhone;

    @SerializedName("заказчик_id")
    private int customerId;

    // Getters
    public int getId() { return id; }
    public String getVehicleType() { return vehicleType; }
    public double getWeight() { return weight; }
    public double getVolume() { return volume; }
    public String getProduct() { return product; }
    public String getFromLocation() { return fromLocation; }
    public String getToLocation() { return toLocation; }
    public String getLoadingType() { return loadingType; }
    public String getLoadingDetails() { return loadingDetails; }
    public String getDates() { return dates; }
    public double getPriceByCard() { return priceByCard; }
    public double getPriceWithVat() { return priceWithVat; }
    public String getBargainOrNoBargain() { return bargainOrNoBargain; }
    public String getContactPhone() { return contactPhone; }
    public int getCustomerId() { return customerId; }
}
