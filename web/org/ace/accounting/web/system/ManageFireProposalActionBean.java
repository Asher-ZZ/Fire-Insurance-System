package org.ace.accounting.web.system;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.accounting.common.Branch;
import org.ace.accounting.common.CurrencyType1;
import org.ace.accounting.common.SaleChannel;
import org.ace.accounting.common.validation.MessageId;
import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.FireProposal;
import org.ace.accounting.system.fire.Premium;
import org.ace.accounting.system.fire.service.interfaces.IFireProposalService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.FlowEvent;

@ManagedBean(name = "ManageFireProposalActionBean")
@ViewScoped
public class ManageFireProposalActionBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{FireProposalService}")
    private IFireProposalService fireProposalService;

    private boolean createNew;
    private FireProposal fireProposal;
    private BuildingInfo buildingInfo;
    private Premium newPremium;
    private List<Premium> premiumList;
    private List<FireProposal> fireProposalList;

    private String currentStep = "proposalInfo";

    private Date minDate = toDate(LocalDate.of(1990, 1, 1));
    private Date maxDate = toDate(LocalDate.now());

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @PostConstruct
    public void init() {
        createNewFireProposal();
        loadFireProposals();
    }

    private void createNewFireProposal() {
        createNew = true;
        fireProposal = new FireProposal();
        buildingInfo = new BuildingInfo();
        newPremium = new Premium();
        premiumList = new ArrayList<>();
        fireProposal.setPremiumList(premiumList);
        fireProposal.setBuildingInfo(buildingInfo); // Explicitly link
    }

    private void loadFireProposals() {
        if (fireProposalService != null) {
            fireProposalList = fireProposalService.findAllFireProposals();
        } else {
            fireProposalList = new ArrayList<>();
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if ("buildingInfo".equals(currentStep)) {
            if (fireProposal.getBuildingInfo() == null) {
                fireProposal.setBuildingInfo(buildingInfo);
            } else {
                // Sync UI changes to the linked BuildingInfo
                BuildingInfo linkedBuildingInfo = fireProposal.getBuildingInfo();
                linkedBuildingInfo.setBuildingName(buildingInfo.getBuildingName());
                linkedBuildingInfo.setFloor(buildingInfo.getFloor());
                linkedBuildingInfo.setWall(buildingInfo.getWall());
                linkedBuildingInfo.setRoofing(buildingInfo.getRoofing());
                linkedBuildingInfo.setBuildingClass(buildingInfo.getBuildingClass());
                linkedBuildingInfo.setNatureOfBusiness(buildingInfo.getNatureOfBusiness());
                linkedBuildingInfo.setMainCover(buildingInfo.getMainCover());
                linkedBuildingInfo.setFloorName(buildingInfo.getFloorName());
                linkedBuildingInfo.setSumInsured(buildingInfo.getSumInsured());
                linkedBuildingInfo.setLength(buildingInfo.getLength());
                linkedBuildingInfo.setWidth(buildingInfo.getWidth());
                linkedBuildingInfo.setHeight(buildingInfo.getHeight());
                linkedBuildingInfo.setSquareFeet(buildingInfo.getSquareFeet());
                linkedBuildingInfo.setAirCraftDamage(buildingInfo.getAirCraftDamage());
                linkedBuildingInfo.setEarthQuakeFire(buildingInfo.getEarthQuakeFire());
                linkedBuildingInfo.setFloodAndInundation(buildingInfo.getFloodAndInundation());
                linkedBuildingInfo.setImpactDamage(buildingInfo.getImpactDamage());
                linkedBuildingInfo.setRiotStrike(buildingInfo.getRiotStrike());
                linkedBuildingInfo.setSpontaneousCombustion(buildingInfo.getSpontaneousCombustion());
                linkedBuildingInfo.setStormTyphoon(buildingInfo.getStormTyphoon());
                linkedBuildingInfo.setWaterDamage(buildingInfo.getWaterDamage());
                linkedBuildingInfo.setSubsidenceAndLandslide(buildingInfo.getSubsidenceAndLandslide());
                linkedBuildingInfo.setWarRisk(buildingInfo.getWarRisk());
            }
        }
        if ("premiumInfo".equals(event.getNewStep())) {
            if (fireProposal.getPremiumList() == null) {
                fireProposal.setPremiumList(premiumList);
            } else {
                fireProposal.getPremiumList().clear();
                fireProposal.getPremiumList().addAll(premiumList);
            }
        }
        currentStep = event.getNewStep();
        return currentStep;
    }

    public void addPremium() {
        if (newPremium.getSumInsured() != null && newPremium.getPremiumRate() != null) {
            newPremium.setFireProposal(fireProposal);
            premiumList.add(newPremium);
            newPremium = new Premium();
            refreshTotals();
        }
    }

    public void removePremium(Premium premium) {
        if (premium != null && premiumList.contains(premium)) {
            premiumList.remove(premium);
            refreshTotals();
        }
    }

    private void refreshTotals() {
        fireProposal.setTotalSumInsured(fireProposal.calculateTotalSumInsured());
        fireProposal.setTotalPremiumPeriod(
            premiumList.stream().mapToDouble(Premium::getTotalPremiumPeriod).sum()
        );
    }

    public void saveAll() {
        try {
            if (fireProposal.getBuildingInfo() == null) {
                fireProposal.setBuildingInfo(buildingInfo);
            }
            if (fireProposal.getPremiumList() == null) {
                fireProposal.setPremiumList(premiumList);
            } else if (!fireProposal.getPremiumList().equals(premiumList)) {
                fireProposal.getPremiumList().clear();
                fireProposal.getPremiumList().addAll(premiumList);
            }
            refreshTotals();

            if (createNew) {
                fireProposalService.addNewFireProposal(fireProposal);
                addInfoMessage(null, MessageId.INSERT_SUCCESS, fireProposal.getCustomer());
            } else {
                fireProposalService.updateFireProposal(fireProposal);
                addInfoMessage(null, MessageId.UPDATE_SUCCESS, fireProposal.getCustomer());
            }

            createNewFireProposal();
            loadFireProposals();

        } catch (SystemException ex) {
            handleSysException(ex);
        }
    }

    public String cancel() {
        createNewFireProposal();
        return null;
    }

    public FireProposal getFireProposal() { return fireProposal; }
    public BuildingInfo getBuildingInfo() { return buildingInfo; }
    public Premium getNewPremium() { return newPremium; }
    public List<Premium> getPremiumList() { return premiumList; }
    public List<FireProposal> getFireProposalList() { return fireProposalList; }
    public Date getMinDate() { return minDate; }
    public Date getMaxDate() { return maxDate; }
    public String getCurrentStep() { return currentStep; }
    public SaleChannel[] getSaleChannels() { return SaleChannel.values(); }
    public Branch[] getBranches() { return Branch.values(); }
    public CurrencyType1[] getCurrencyTypes() { return CurrencyType1.values(); }
    public void setFireProposalService(IFireProposalService fireProposalService) { this.fireProposalService = fireProposalService; }
}