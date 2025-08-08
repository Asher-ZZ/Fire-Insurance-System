package org.ace.accounting.system.fire;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "BUILDING_INFO")
@TableGenerator(
    name = "BUILDING_INFO_GEN",
    table = "ID_GEN",
    pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL",
    pkColumnValue = "BUILDING_INFO_GEN",
    allocationSize = 10
)
public class BuildingInfo implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BUILDING_INFO_GEN")
    @Column(name = "BuildingInfoID")
    private String id;

    @Column(name = "BuildingName", length = 100)
    private String buildingName;

    @Column(name = "Floor", length = 50)
    private String floor;

    @Column(name = "Wall", length = 50)
    private String wall;

    @Column(name = "Roofing", length = 50)
    private String roofing;

    @Column(name = "BuildingClass", length = 100)
    private String buildingClass;

    @Column(name = "NatureOfBusiness", length = 255)
    private String natureOfBusiness;

    @Column(name = "MainCover", length = 255)
    private String mainCover;

    @Column(name = "FloorName", length = 100)
    private String floorName;

    @Column(name = "SumInsured")
    private Double sumInsured;

    @Column(name = "Length", length = 50)
    private String length;

    @Column(name = "Width", length = 50)
    private String width;

    @Column(name = "Height", length = 50)
    private String height;

    @Column(name = "SquareFeet", length = 50)
    private String squareFeet;

    @Column(name = "AirCraftDamage")
    private boolean airCraftDamage;

    @Column(name = "EarthQuakeFire")
    private boolean earthQuakeFire;

    @Column(name = "FloodAndInundation")
    private boolean floodAndInundation;

    @Column(name = "ImpactDamage")
    private boolean impactDamage;

    @Column(name = "RiotStrike")
    private boolean riotStrike;

    @Column(name = "SpontaneousCombustion")
    private boolean spontaneousCombustion;

    @Column(name = "StormTyphoon")
    private boolean stormTyphoon;

    @Column(name = "WaterDamage")
    private boolean waterDamage;

    @Column(name = "SubsidenceAndLandslide")
    private boolean subsidenceAndLandslide;

    @Column(name = "WarRisk")
    private boolean warRisk;
    
    
    @OneToOne(mappedBy = "buildingInfo")
    private FireProposal fireProposal;

    public FireProposal getFireProposal() {
        return fireProposal;
    }

    public void setFireProposal(FireProposal fireProposal) {
        this.fireProposal = fireProposal;
    }


    // --- Constructors ---
    public BuildingInfo() {}

    // --- Getters & Setters ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getWall() {
        return wall;
    }

    public void setWall(String wall) {
        this.wall = wall;
    }

    public String getRoofing() {
        return roofing;
    }

    public void setRoofing(String roofing) {
        this.roofing = roofing;
    }

    public String getBuildingClass() {
        return buildingClass;
    }

    public void setBuildingClass(String buildingClass) {
        this.buildingClass = buildingClass;
    }

    public String getNatureOfBusiness() {
        return natureOfBusiness;
    }

    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    public String getMainCover() {
        return mainCover;
    }

    public void setMainCover(String mainCover) {
        this.mainCover = mainCover;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public Double getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(Double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(String squareFeet) {
        this.squareFeet = squareFeet;
    }

    public boolean isAirCraftDamage() {
        return airCraftDamage;
    }

    public void setAirCraftDamage(boolean airCraftDamage) {
        this.airCraftDamage = airCraftDamage;
    }

    public boolean isEarthQuakeFire() {
        return earthQuakeFire;
    }

    public void setEarthQuakeFire(boolean earthQuakeFire) {
        this.earthQuakeFire = earthQuakeFire;
    }

    public boolean isFloodAndInundation() {
        return floodAndInundation;
    }

    public void setFloodAndInundation(boolean floodAndInundation) {
        this.floodAndInundation = floodAndInundation;
    }

    public boolean isImpactDamage() {
        return impactDamage;
    }

    public void setImpactDamage(boolean impactDamage) {
        this.impactDamage = impactDamage;
    }

    public boolean isRiotStrike() {
        return riotStrike;
    }

    public void setRiotStrike(boolean riotStrike) {
        this.riotStrike = riotStrike;
    }

    public boolean isSpontaneousCombustion() {
        return spontaneousCombustion;
    }

    public void setSpontaneousCombustion(boolean spontaneousCombustion) {
        this.spontaneousCombustion = spontaneousCombustion;
    }

    public boolean isStormTyphoon() {
        return stormTyphoon;
    }

    public void setStormTyphoon(boolean stormTyphoon) {
        this.stormTyphoon = stormTyphoon;
    }

    public boolean isWaterDamage() {
        return waterDamage;
    }

    public void setWaterDamage(boolean waterDamage) {
        this.waterDamage = waterDamage;
    }

    public boolean isSubsidenceAndLandslide() {
        return subsidenceAndLandslide;
    }

    public void setSubsidenceAndLandslide(boolean subsidenceAndLandslide) {
        this.subsidenceAndLandslide = subsidenceAndLandslide;
    }

    public boolean isWarRisk() {
        return warRisk;
    }

    public void setWarRisk(boolean warRisk) {
        this.warRisk = warRisk;
    }

    @Override
    public BuildingInfo clone() {
        try {
            return (BuildingInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}