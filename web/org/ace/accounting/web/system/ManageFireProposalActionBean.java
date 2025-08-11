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
import org.ace.accounting.common.PaymentType;
import org.ace.accounting.common.SaleChannel;
import org.ace.accounting.common.validation.MessageId;
import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.FireProposal;
import org.ace.accounting.system.fire.Premium;
import org.ace.accounting.system.fire.service.interfaces.IFireProposalService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.FlowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name = "ManageFireProposalActionBean")
@ViewScoped
public class ManageFireProposalActionBean extends BaseBean {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ManageFireProposalActionBean.class);

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
    private Date maxDate = toDate(LocalDate.now(ZoneId.of("Australia/Sydney")));

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
        fireProposal.setBuildingInfo(buildingInfo);
        logger.debug("Initialized new FireProposal and BuildingInfo");
    }

    private void loadFireProposals() {
        if (fireProposalService != null) {
            fireProposalList = fireProposalService.findAllFireProposals();
            logger.debug("Loaded {} fire proposals", fireProposalList.size());
        } else {
            fireProposalList = new ArrayList<>();
            logger.warn("FireProposalService is null, initialized empty fire proposal list");
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if ("buildingInfo".equals(currentStep)) {
            if (fireProposal.getBuildingInfo() == null) {
                fireProposal.setBuildingInfo(buildingInfo);
            } else {
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
                logger.debug("Synced BuildingInfo data: BuildingName={}", buildingInfo.getBuildingName());
            }
            validateDates();
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

    private void validateDates() {
        Date submittedDate = fireProposal.getSubmittedDate();
        Date policyStartDate = fireProposal.getPolicyStartDate();
        if (submittedDate != null && (submittedDate.before(minDate) || submittedDate.after(maxDate))) {
            addErrorMessage(null, "Submitted date must be between " + minDate + " and " + maxDate);
            return;
        }
        if (policyStartDate != null && (policyStartDate.before(minDate) || policyStartDate.after(maxDate))) {
            addErrorMessage(null, "Policy start date must be between " + minDate + " and " + maxDate);
            return;
        }
        logger.debug("Validated dates: SubmittedDate={}, PolicyStartDate={}", submittedDate, policyStartDate);
    }

    public void addPremium() {
        if (newPremium.getSumInsured() != null && newPremium.getPremiumRate() != null) {
            newPremium.setFireProposal(fireProposal);
            premiumList.add(newPremium);
            newPremium = new Premium();
            refreshTotals();
            logger.debug("Added new premium: SumInsured={}, PremiumRate={}", newPremium.getSumInsured(), newPremium.getPremiumRate());
        } else {
            addErrorMessage(null, "Sum insured and premium rate are required to add a premium.");
        }
    }

    public void removePremium(Premium premium) {
        if (premium != null && premiumList.contains(premium)) {
            premiumList.remove(premium);
            refreshTotals();
            logger.debug("Removed premium: BuildingName={}", premium.getBuildingName());
        }
    }

    private void refreshTotals() {
        fireProposal.setTotalSumInsured(fireProposal.calculateTotalSumInsured());
        fireProposal.setTotalPremiumPeriod(
            premiumList.stream().mapToDouble(Premium::getTotalPremiumPeriod).sum()
        );
        logger.debug("Refreshed totals: TotalSumInsured={}, TotalPremiumPeriod={}", fireProposal.getTotalSumInsured(), fireProposal.getTotalPremiumPeriod());
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
			/*
			 * refreshTotals(); logger.
			 * debug("Saving FireProposal: Customer={}, PaymentType={}, BuildingName={}",
			 * fireProposal.getCustomer(), fireProposal.getPaymentType(),
			 * fireProposal.getBuildingInfo().getBuildingName());
			 */
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
            logger.error("Failed to save FireProposal", ex);
            handleSysException(ex);
        }
    }

    public String cancel() {
        createNewFireProposal();
        return null;
    }

    // Remove these methods as they conflict with the direct binding to fireProposal.paymentType
    /*
    public PaymentType getPaymentType() {
        return fireProposal != null ? fireProposal.getPaymentType() : null;
    }

    public void setPaymentType(PaymentType paymentType) {
        if (fireProposal != null) {
            fireProposal.setPaymentType(paymentType);
        }
    }
    */

    public FireProposal getFireProposal() {
        return fireProposal != null ? fireProposal : (fireProposal = new FireProposal());
    }

    public BuildingInfo getBuildingInfo() {
        return buildingInfo != null ? buildingInfo : (buildingInfo = new BuildingInfo());
    }

    public Premium getNewPremium() {
        return newPremium != null ? newPremium : (newPremium = new Premium());
    }

    public List<Premium> getPremiumList() {
        return premiumList != null ? premiumList : (premiumList = new ArrayList<>());
    }

    public List<FireProposal> getFireProposalList() {
        return fireProposalList != null ? fireProposalList : (fireProposalList = new ArrayList<>());
    }

    public Date getMinDate() {
        return minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public SaleChannel[] getSaleChannels() {
        return SaleChannel.values();
    }

    public Branch[] getBranches() {
        return Branch.values();
    }

    public CurrencyType1[] getCurrencyTypes() {
        return CurrencyType1.values();
    }

    public PaymentType[] getPaymentTypes() {
        return PaymentType.values();
    }

    public void setFireProposalService(IFireProposalService fireProposalService) {
        this.fireProposalService = fireProposalService;
    }
}