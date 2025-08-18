package org.ace.accounting.system.fire;

import java.io.Serializable;

import javax.persistence.*;

import org.ace.accounting.system.fire.enumTypes.BuildingClass;
import org.ace.accounting.system.fire.enumTypes.FloorType;
import org.ace.accounting.system.fire.enumTypes.RoofingType;
import org.ace.accounting.system.fire.enumTypes.WallType;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = "BUILDING_INFO")
@TableGenerator(name = "BUILDING_INFO_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "BUILDING_INFO_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class BuildingInfo implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BUILDING_INFO_GEN") // Changed to IDENTITY
	@Column(name = "BuildingInfoID")
	private String id;

	@Column(name = "BuildingName", length = 100)
	private String buildingName;

	@Enumerated(EnumType.STRING)
	@Column(name = "Floor", length = 50)
	private FloorType floor;

	@Enumerated(EnumType.STRING)
    @Column(name = "Wall", length = 50)
    private WallType wall;

	@Enumerated(EnumType.STRING)
	@Column(name = "Roofing", length = 50)
	private RoofingType roofing;

	@Enumerated(EnumType.STRING)
	@Column(name = "BuildingClass", length = 50)
	private BuildingClass buildingClass;

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
	private Boolean airCraftDamage;

	@Column(name = "EarthQuakeFire")
	private Boolean earthQuakeFire;

	@Column(name = "FloodAndInundation")
	private Boolean floodAndInundation;

	@Column(name = "ImpactDamage")
	private Boolean impactDamage;

	@Column(name = "RiotStrike")
	private Boolean riotStrike;

	@Column(name = "SpontaneousCombustion")
	private Boolean spontaneousCombustion;

	@Column(name = "StormTyphoon")
	private Boolean stormTyphoon;

	@Column(name = "WaterDamage")
	private Boolean waterDamage;

	@Column(name = "SubsidenceAndLandslide")
	private Boolean subsidenceAndLandslide;

	@Column(name = "WarRisk")
	private Boolean warRisk;

	@Column(name = "BasicPremiumPeriod")
	private Double basicPremiumPeriod;

	@Column(name = "BasicPremiumTerm")
	private Double basicPremiumTerm;

	@Column(name = "AddOnPremiumPeriod")
	private Double addOnPremiumPeriod;

	@Column(name = "AddOnPremiumTerm")
	private Double addOnPremiumTerm;

	@Column(name = "TotalPremiumPeriod")
	private Double totalPremiumPeriod;

	@ManyToOne
	@JoinColumn(name = "FIREPROPOSALID")
	private FireProposal fireProposal;

	// Default constructor
	public BuildingInfo() {
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuildingName() {
		return buildingName != null ? buildingName : "";
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public FloorType getFloor() {
	    return floor;
	}

	public void setFloor(FloorType floor) {
	    this.floor = floor;
	}

	public WallType getWall() {
        return wall;
    }

    public void setWall(WallType wall) {
        this.wall = wall;
    }
	

    public RoofingType getRoofing() {
        return roofing;
    }

    public void setRoofing(RoofingType roofing) {
        this.roofing = roofing;
    }

	public BuildingClass getBuildingClass() {
	    return buildingClass;
	}

	public void setBuildingClass(BuildingClass buildingClass) {
	    this.buildingClass = buildingClass;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness != null ? natureOfBusiness : "";
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getMainCover() {
		return mainCover != null ? mainCover : "";
	}

	public void setMainCover(String mainCover) {
		this.mainCover = mainCover;
	}

	public String getFloorName() {
		return floorName != null ? floorName : "";
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public Double getSumInsured() {
		return sumInsured != null ? sumInsured : 0.0;
	}

	public void setSumInsured(Double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public Double getLength() {
		return length != null ? length : 0.0;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width != null ? width : 0.0;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height != null ? height : 0.0;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getSquareFeet() {
		return squareFeet != null ? squareFeet : 0.0;
	}

	public void setSquareFeet(Double squareFeet) {
		this.squareFeet = squareFeet;
	}

	public Boolean getAirCraftDamage() {
		return airCraftDamage != null ? airCraftDamage : false;
	}

	public void setAirCraftDamage(Boolean airCraftDamage) {
		this.airCraftDamage = airCraftDamage;
	}

	public Boolean getEarthQuakeFire() {
		return earthQuakeFire != null ? earthQuakeFire : false;
	}

	public void setEarthQuakeFire(Boolean earthQuakeFire) {
		this.earthQuakeFire = earthQuakeFire;
	}

	public Boolean getFloodAndInundation() {
		return floodAndInundation != null ? floodAndInundation : false;
	}

	public void setFloodAndInundation(Boolean floodAndInundation) {
		this.floodAndInundation = floodAndInundation;
	}

	public Boolean getImpactDamage() {
		return impactDamage != null ? impactDamage : false;
	}

	public void setImpactDamage(Boolean impactDamage) {
		this.impactDamage = impactDamage;
	}

	public Boolean getRiotStrike() {
		return riotStrike != null ? riotStrike : false;
	}

	public void setRiotStrike(Boolean riotStrike) {
		this.riotStrike = riotStrike;
	}

	public Boolean getSpontaneousCombustion() {
		return spontaneousCombustion != null ? spontaneousCombustion : false;
	}

	public void setSpontaneousCombustion(Boolean spontaneousCombustion) {
		this.spontaneousCombustion = spontaneousCombustion;
	}

	public Boolean getStormTyphoon() {
		return stormTyphoon != null ? stormTyphoon : false;
	}

	public void setStormTyphoon(Boolean stormTyphoon) {
		this.stormTyphoon = stormTyphoon;
	}

	public Boolean getWaterDamage() {
		return waterDamage != null ? waterDamage : false;
	}

	public void setWaterDamage(Boolean waterDamage) {
		this.waterDamage = waterDamage;
	}

	public Boolean getSubsidenceAndLandslide() {
		return subsidenceAndLandslide != null ? subsidenceAndLandslide : false;
	}

	public void setSubsidenceAndLandslide(Boolean subsidenceAndLandslide) {
		this.subsidenceAndLandslide = subsidenceAndLandslide;
	}

	public Boolean getWarRisk() {
		return warRisk != null ? warRisk : false;
	}

	public void setWarRisk(Boolean warRisk) {
		this.warRisk = warRisk;
	}

	public FireProposal getFireProposal() {
		return fireProposal;
	}

	public void setFireProposal(FireProposal fireProposal) {
		this.fireProposal = fireProposal;
	}

	public Double getBasicPremiumPeriod() {
		return basicPremiumPeriod;
	}

	public void setBasicPremiumPeriod(Double basicPremiumPeriod) {
		this.basicPremiumPeriod = basicPremiumPeriod;
	}

	public Double getBasicPremiumTerm() {
		return basicPremiumTerm;
	}

	public void setBasicPremiumTerm(Double basicPremiumTerm) {
		this.basicPremiumTerm = basicPremiumTerm;
	}

	public Double getAddOnPremiumPeriod() {
		return addOnPremiumPeriod;
	}

	public void setAddOnPremiumPeriod(Double addOnPremiumPeriod) {
		this.addOnPremiumPeriod = addOnPremiumPeriod;
	}

	public Double getAddOnPremiumTerm() {
		return addOnPremiumTerm;
	}

	public void setAddOnPremiumTerm(Double addOnPremiumTerm) {
		this.addOnPremiumTerm = addOnPremiumTerm;
	}

	public Double getTotalPremiumPeriod() {
		return totalPremiumPeriod;
	}

	public void setTotalPremiumPeriod(Double totalPremiumPeriod) {
		this.totalPremiumPeriod = totalPremiumPeriod;
	}

	@Override
	public BuildingInfo clone() {
		BuildingInfo clone = new BuildingInfo();
		clone.setId(this.id);
		clone.setBuildingName(this.buildingName);
		clone.setFloor(this.floor);
		clone.setWall(this.wall);
		clone.setRoofing(this.roofing);
		clone.setBuildingClass(this.buildingClass);
		clone.setNatureOfBusiness(this.natureOfBusiness);
		clone.setMainCover(this.mainCover);
		clone.setFloorName(this.floorName);
		clone.setSumInsured(this.sumInsured);
		clone.setLength(this.length);
		clone.setWidth(this.width);
		clone.setHeight(this.height);
		clone.setSquareFeet(this.squareFeet);
		clone.setAirCraftDamage(this.airCraftDamage);
		clone.setEarthQuakeFire(this.earthQuakeFire);
		clone.setFloodAndInundation(this.floodAndInundation);
		clone.setImpactDamage(this.impactDamage);
		clone.setRiotStrike(this.riotStrike);
		clone.setSpontaneousCombustion(this.spontaneousCombustion);
		clone.setStormTyphoon(this.stormTyphoon);
		clone.setWaterDamage(this.waterDamage);
		clone.setSubsidenceAndLandslide(this.subsidenceAndLandslide);
		clone.setWarRisk(this.warRisk);
		clone.setFireProposal(this.fireProposal);
		clone.setBasicPremiumPeriod(this.basicPremiumPeriod);
		clone.setBasicPremiumTerm(this.basicPremiumTerm);
		clone.setAddOnPremiumPeriod(this.addOnPremiumPeriod);
		clone.setAddOnPremiumTerm(this.addOnPremiumTerm);
		clone.setTotalPremiumPeriod(this.totalPremiumPeriod);
		return clone;
	}

	// Handle relationship if needed
	public boolean isValid() {
		return buildingName != null && !buildingName.trim().isEmpty() && buildingClass != null
				&& natureOfBusiness != null && !natureOfBusiness.trim().isEmpty()
				&& sumInsured != null && sumInsured > 0 && squareFeet != null && squareFeet > 0;
	}
}