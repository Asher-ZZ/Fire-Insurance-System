package org.ace.accounting.system.fire;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.ace.accounting.common.BuildingClass;
import org.ace.accounting.common.FloorType;
import org.ace.accounting.common.RoofingType;
import org.ace.accounting.common.WallType;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = "BUILDING_INFO")
@TableGenerator(name = "BUILDING_INFO_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "BUILDING_INFO_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class BuildingInfo implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "BUILDING_INFO_GEN") 
	@Column(name = "BUILDINGINFOID")
	private String id;

	@Column(name = "BUILDINGNAME", length = 100)
	private String buildingName;

	@Enumerated(EnumType.STRING)
	@Column(name = "BUILDINGCLASS", length = 50)
	private BuildingClass buildingClass;

	@Column(name = "NATUREOFBUSINESS", length = 255)
	private String natureOfBusiness;

	@Column(name = "MAINCOVER", length = 255)
	private String mainCover;

	@Column(name = "FLOORNAME", length = 100)
	private String floorName;

	@Column(name = "SUMINSURED", precision = 18, scale = 2)
	private Double sumInsured;

	@Column(name = "LENGTH", precision = 10, scale = 2)
	private Double length;

	@Column(name = "WIDTH", precision = 10, scale = 2)
	private Double width;

	@Column(name = "HEIGHT", precision = 10, scale = 2)
	private Double height;

	@Column(name = "SQUAREFEET", precision = 10, scale = 2)
	private Double squareFeet;

	@Column(name = "AIRCRAFTDAMAGE")
	private Boolean airCraftDamage;

	@Column(name = "EARTHQUAKEFIRE")
	private Boolean earthQuakeFire;

	@Column(name = "FLOODANDINUNDATION")
	private Boolean floodAndInundation;

	@Column(name = "IMPACTDAMAGE")
	private Boolean impactDamage;

	@Column(name = "RIOTSTRIKE")
	private Boolean riotStrike;

	@Column(name = "SPONTANEOUSCOMBUSTION")
	private Boolean spontaneousCombustion;

	@Column(name = "STORMTYPHOON")
	private Boolean stormTyphoon;

	@Column(name = "WATERDAMAGE")
	private Boolean waterDamage;

	@Column(name = "SUBSIDENCEANDLANDSLIDE")
	private Boolean subsidenceAndLandslide;

	@Column(name = "WARRISK")
	private Boolean warRisk;

	@Column(name = "BASICPREMIUMPERIOD")
	private Double basicPremiumPeriod;

	@Column(name = "BASICPREMIUMTERM")
	private Double basicPremiumTerm;

	@Column(name = "ADDONPREMIUMPERIOD")
	private Double addOnPremiumPeriod;

	@Column(name = "ADDONPREMIUMTERM")
	private Double addOnPremiumTerm;

	@Column(name = "TOTALPREMIUMPERIOD")
	private Double totalPremiumPeriod;

	@ManyToOne
	@JoinColumn(name = "FIREPROPOSALID")
	private FireProposal fireProposal;

	@Enumerated(EnumType.STRING)
	@Column(name = "FLOOR", length = 50)
	private FloorType floor;

	@Enumerated(EnumType.STRING)
	@Column(name = "WALL", length = 50)
	private WallType wall;

	public FloorType getFloor() {
	    return floor;
	}

	public WallType getWall() {
	    return wall;
	}

	public RoofingType getRoofing() {
	    return roofing;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "ROOFING", length = 50)
	private RoofingType roofing;


	public void setFloor(FloorType floor) {
		this.floor = floor;
	}

	public void setWall(WallType wall) {
		this.wall = wall;
	}

	public void setRoofing(RoofingType roofing) {
		this.roofing = roofing;
	}

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

	public BuildingClass getBuildingClass() {
		return buildingClass;
	}

	public void setBuildingClass(BuildingClass buildingClass) {
		this.buildingClass = buildingClass;
	}

	// Handle relationship if needed
	public boolean isValid() {
		return buildingName != null && !buildingName.trim().isEmpty() && buildingClass != null
		/* && !buildingClass.trim().isEmpty() */ && natureOfBusiness != null && !natureOfBusiness.trim().isEmpty()
				&& sumInsured != null && sumInsured > 0 && squareFeet != null && squareFeet > 0;
	}

	public void setTotalSumInsured(double totalSumInsured) {
		// TODO Auto-generated method stub

	}

	// Inside FireProposal.java




}