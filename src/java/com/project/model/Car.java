/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ponlawatchangto
 */
public class Car {
    private int id;
    private String title;
    private int brandId;
    private int modelId;
    private int subModelId;
    private String brandName;
    private String modelName;
    private String subModelName;
    private int year;
    private int mileage;
    private float price;
    private int engineDisplacement;
    private int bodyTypeId;
    private String bodyTypeName;
    private String color;
    private int gearId;
    private String gearName;
    private int fuelId;
    private String fuelTypeName;
    private String status;
    private String carMainPic;
    private LocalDate createDate ;
    private LocalTime createTime ;
    private int creator_id ;

    public Car() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getBrandId() { return brandId; }
    public void setBrandId(int brandId) { this.brandId = brandId; }

    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public int getModelId() { return modelId; }
    public void setModelId(int modelId) { this.modelId = modelId; }

    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }

    public int getSubModelId() { return subModelId; }
    public void setSubModelId(int subModelId) { this.subModelId = subModelId; }

    public String getSubModelName() { return subModelName; }
    public void setSubModelName(String subModelName) { this.subModelName = subModelName; }

    public int getYear() { return year ; }
    public void setYear(int year) { this.year = year ; }
    
    public int getMileage() { return mileage ; }
    public void setMileage(int mileage) { this.mileage = mileage ; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    
    public int getEngineDisplacement() { return engineDisplacement ; }
    public void setEngineDisplacement(int engineDisplacement) { this.engineDisplacement = engineDisplacement ; }
    
    public int getBodyTypeId() { return bodyTypeId; }
    public void setBodyTypeId(int bodyTypeId) { this.bodyTypeId = bodyTypeId; }

    public String getBodyTypeName() { return bodyTypeName; }
    public void setBodyTypeName(String bodyTypeName) { this.bodyTypeName = bodyTypeName; }
    
    public String getColor() { return color ;} 
    public void setColor(String color) { this.color = color ; }
    
    public int getGearId() { return gearId; }
    public void setGearId(int gearId) { this.gearId = gearId; }

    public String getGearName() { return gearName; }
    public void setGearName(String gearName) { this.gearName = gearName; }    

    public int getFuelId() { return fuelId; }
    public void setFuelId(int fuelId) { this.fuelId = fuelId; }

    public String getFuelTypeName() { return fuelTypeName; }
    public void setFuelTypeName(String fuelTypeName) { this.fuelTypeName = fuelTypeName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCarMainPic() { return carMainPic; }
    public void setCarMainPic(String base64Image) { this.carMainPic = base64Image; }

    public LocalDate getCreateDate() { return createDate ; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate ; }
    
    public LocalTime getCreateTime() { return createTime ; }
    public void setCreateTime(LocalTime createTime) { this.createTime = createTime ; }

    public int getCreator_id() { return creator_id ; }
    public void setCreator_id(int creator_id) { this.creator_id = creator_id ; }
}
