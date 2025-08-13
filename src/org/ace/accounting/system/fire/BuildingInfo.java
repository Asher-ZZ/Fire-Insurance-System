package org.ace.accounting.system.fire;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "BUILDING_INFO")
public class BuildingInfo implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BuildingInfoID")
    private Long id;

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

    @Column(name = "SumInsured", precision = 18, scale = 2)
    private Double sumInsured;

    @Column(name = "Length", precision = 10, scale = 2)
    private Double length;

    @Column(name = "Width", precision = 10, scale = 2)
    private Double width;

    @Column(name = "Height", precision = 10, scale = 2)
    private Double height;

    @Column(name = "SquareFeet", precision = 10, scale = 2)
    private Double squareFeet;

    @Column(name = "AirCraftDamage")
    private Boolean airCraftDamage; // Changed to Boolean

    @Column(name = "EarthQuakeFire")
    private Boolean earthQuakeFire; // Changed to Boolean

    @Column(name = "FloodAndInundation")
    private Boolean floodAndInundation; // Changed to Boolean

    @Column(name = "ImpactDamage")
    private Boolean impactDamage; // Changed to Boolean

    @Column(name = "RiotStrike")
    private Boolean riotStrike; // Changed to Boolean

    @Column(name = "SpontaneousCombustion")
    private Boolean spontaneousCombustion; // Changed to Boolean

    @Column(name = "StormTyphoon")
    private Boolean stormTyphoon; // Changed to Boolean

    @Column(name = "WaterDamage")
    private Boolean waterDamage; // Changed to Boolean

    @Column(name = "SubsidenceAndLandslide")
    private Boolean subsidenceAndLandslide; // Changed to Boolean

    @Column(name = "WarRisk")
    private Boolean warRisk; // Changed to Boolean

    @OneToMany(mappedBy = "buildingInfo")
    private FireProposal fireProposal;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBuildingName() { return buildingName != null ? buildingName : ""; }
    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }
    public String getFloor() { return floor != null ? floor : ""; }
    public void setFloor(String floor) { this.floor = floor; }
    public String getWall() { return wall != null ? wall : ""; }
    public void setWall(String wall) { this.wall = wall; }
    public String getRoofing() { return roofing != null ? roofing : ""; }
    public void setRoofing(String roofing) { this.roofing = roofing; }
    public String getBuildingClass() { return buildingClass != null ? buildingClass : ""; }
    public void setBuildingClass(String buildingClass) { this.buildingClass = buildingClass; }
    public String getNatureOfBusiness() { return natureOfBusiness != null ? natureOfBusiness : ""; }
    public void setNatureOfBusiness(String natureOfBusiness) { this.natureOfBusiness = natureOfBusiness; }
    public String getMainCover() { return mainCover != null ? mainCover : ""; }
    public void setMainCover(String mainCover) { this.mainCover = mainCover; }
    public String getFloorName() { return floorName != null ? floorName : ""; }
    public void setFloorName(String floorName) { this.floorName = floorName; }
    public Double getSumInsured() { return sumInsured != null ? sumInsured : 0.0; }
    public void setSumInsured(Double sumInsured) { this.sumInsured = sumInsured; }
    public Double getLength() { return length != null ? length : 0.0; }
    public void setLength(Double length) { this.length = length; }
    public Double getWidth() { return width != null ? width : 0.0; }
    public void setWidth(Double width) { this.width = width; }
    public Double getHeight() { return height != null ? height : 0.0; }
    public void setHeight(Double height) { this.height = height; }
    public Double getSquareFeet() { return squareFeet != null ? squareFeet : 0.0; }
    public void setSquareFeet(Double squareFeet) { this.squareFeet = squareFeet; }
    public Boolean getAirCraftDamage() { return airCraftDamage != null ? airCraftDamage : false; } // Changed getter
    public void setAirCraftDamage(Boolean airCraftDamage) { this.airCraftDamage = airCraftDamage; }
    public Boolean getEarthQuakeFire() { return earthQuakeFire != null ? earthQuakeFire : false; }
    public void setEarthQuakeFire(Boolean earthQuakeFire) { this.earthQuakeFire = earthQuakeFire; }
    public Boolean getFloodAndInundation() { return floodAndInundation != null ? floodAndInundation : false; }
    public void setFloodAndInundation(Boolean floodAndInundation) { this.floodAndInundation = floodAndInundation; }
    public Boolean getImpactDamage() { return impactDamage != null ? impactDamage : false; }
    public void setImpactDamage(Boolean impactDamage) { this.impactDamage = impactDamage; }
    public Boolean getRiotStrike() { return riotStrike != null ? riotStrike : false; }
    public void setRiotStrike(Boolean riotStrike) { this.riotStrike = riotStrike; }
    public Boolean getSpontaneousCombustion() { return spontaneousCombustion != null ? spontaneousCombustion : false; }
    public void setSpontaneousCombustion(Boolean spontaneousCombustion) { this.spontaneousCombustion = spontaneousCombustion; }
    public Boolean getStormTyphoon() { return stormTyphoon != null ? stormTyphoon : false; }
    public void setStormTyphoon(Boolean stormTyphoon) { this.stormTyphoon = stormTyphoon; }
    public Boolean getWaterDamage() { return waterDamage != null ? waterDamage : false; }
    public void setWaterDamage(Boolean waterDamage) { this.waterDamage = waterDamage; }
    public Boolean getSubsidenceAndLandslide() { return subsidenceAndLandslide != null ? subsidenceAndLandslide : false; }
    public void setSubsidenceAndLandslide(Boolean subsidenceAndLandslide) { this.subsidenceAndLandslide = subsidenceAndLandslide; }
    public Boolean getWarRisk() { return warRisk != null ? warRisk : false; }
    public void setWarRisk(Boolean warRisk) { this.warRisk = warRisk; }
    public FireProposal getFireProposal() { return fireProposal; }
    public void setFireProposal(FireProposal fireProposal) { this.fireProposal = fireProposal; }

    @Override
    public BuildingInfo clone() {
        try {
            return (BuildingInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
	public Double getBasicPremiumPeriod() {
		// TODO Auto-generated method stub
		return null;
	}
	public Double getBasicPremiumTerm() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setBasicPremiumPeriod(Double basicPremiumPeriod) {
		// TODO Auto-generated method stub
		
	}
	public void setBasicPremiumTerm(Double basicPremiumTerm) {
		
		
	}
}