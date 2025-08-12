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
import javax.faces.event.AjaxBehaviorEvent;

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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


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
    private List<BuildingInfo> buildings;
    private Premium newPremium;
    private List<Premium> premiumList;
    private List<FireProposal> fireProposalList;
    private int periodMin;
    private int periodMax;

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
        buildings = new ArrayList<>();
        newPremium = new Premium();
        premiumList = new ArrayList<>();
        fireProposal.setPremiumList(premiumList);
        fireProposal.setBuildingList(buildings); // Initialize with the buildings list
        logger.debug("Initialized new FireProposal with multiple buildings support");
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
        String newStep = event.getNewStep();

        // When leaving buildingInfo step, add building info and validate dates
        if ("buildingInfo".equals(currentStep)) {
            if (fireProposal.getBuildingList() == null) {
                fireProposal.setBuildingList(new ArrayList<>());
            }
            
            // Add building only if valid and not already added this session
            if (buildingInfo != null && buildingInfo.isValid()) {
                fireProposal.getBuildingList().add(buildingInfo.clone());
                logger.debug("Added BuildingInfo: {}", buildingInfo.getBuildingName());
            } else {
                addErrorMessage(null, "Please fill in all mandatory building info fields before proceeding.");
                return currentStep; // prevent moving forward
            }
            
            // Validate dates after adding building info
            validateDates();
            if (hasErrors()) { // check if validation added errors
                return currentStep; // prevent moving forward
            }
        }

        // When moving to premiumInfo step, sync premium list
        if ("premiumInfo".equals(newStep)) {
            if (fireProposal.getPremiumList() == null) {
                fireProposal.setPremiumList(premiumList);
            } else {
                fireProposal.getPremiumList().clear();
                fireProposal.getPremiumList().addAll(premiumList);
            }
        }

        currentStep = newStep;
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
            // Set fireProposal reference in each building so FK can be persisted
            for (BuildingInfo b : buildings) {
                b.setFireProposal(fireProposal);
            }
            fireProposal.setBuildingList(buildings);
            calculatePolicyEndDate();
            logger.debug("Saving FireProposal with policyEndDate: {}", fireProposal.getPolicyEndDate());

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

    private void calculatePolicyEndDate() {
        if (fireProposal.getPolicyStartDate() == null 
            || fireProposal.getInsurancePeriodDays() == null 
            || fireProposal.getInsurancePeriodUnit() == null) {
            fireProposal.setPolicyEndDate(null);
            return;
        }

        LocalDate start = fireProposal.getPolicyStartDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        switch (fireProposal.getInsurancePeriodUnit().toUpperCase()) {
            case "DAY":
                start = start.plusDays(fireProposal.getInsurancePeriodDays() - 1);
                break;
            case "MONTH":
                start = start.plusMonths(fireProposal.getInsurancePeriodDays()).minusDays(1);
                break;
            case "YEAR":
                start = start.plusYears(fireProposal.getInsurancePeriodDays()).minusDays(1);
                break;
            default:
                start = start.plusDays(fireProposal.getInsurancePeriodDays() - 1);
                break;
        }

        fireProposal.setPolicyEndDate(Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    public ManageFireProposalActionBean() {
        fireProposal = new FireProposal();
        updatePeriodRange(null);
    }

    public void updatePeriodRange(AjaxBehaviorEvent event) {
        String unit = fireProposal.getInsurancePeriodUnit();
        if ("DAY".equalsIgnoreCase(unit)) {
            periodMin = 10;
            periodMax = 365;
        } else if ("MONTH".equalsIgnoreCase(unit)) {
            periodMin = 1;
            periodMax = 12;
        } else if ("YEAR".equalsIgnoreCase(unit)) {
            periodMin = 1;
            periodMax = 1;
        } else {
            periodMin = 0;
            periodMax = Integer.MAX_VALUE;
        }
        if (fireProposal.getInsurancePeriodDays() != null) {
            int value = fireProposal.getInsurancePeriodDays();
            if (value < periodMin || value > periodMax) {
                fireProposal.setInsurancePeriodDays(periodMin);
            }
        }
    }

    public void addBuilding() {
        if (buildingInfo != null) {
            if (buildingInfo.isValid()) {
                BuildingInfo cloned = buildingInfo.clone(); // Clone once
                // Add to only one list, since buildings and fireProposal.getBuildingList() are same
                buildings.add(cloned);
                // No need to add to fireProposal.getBuildingList() again
            } else {
                addErrorMessage(null, "Please fill building details before adding.");
                return;
            }
            buildingInfo = new BuildingInfo(); // Reset for next entry
        } else {
            addErrorMessage(null, "Building info is null, cannot add.");
        }
    }


    public boolean hasErrors() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getMessages().hasNext(); // Returns true if any message exists (including errors)
    }
    public void removeBuilding(BuildingInfo building) {
        buildings.remove(building);
        fireProposal.getBuildingList().remove(building);
    }

    public void setFireProposal(FireProposal fireProposal) {
        this.fireProposal = fireProposal;
    }

    public int getPeriodMin() {
        return periodMin;
    }

    public int getPeriodMax() {
        return periodMax;
    }

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

    public List<BuildingInfo> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<BuildingInfo> buildings) {
        this.buildings = buildings;
    }

    public void setBuildingInfo(BuildingInfo buildingInfo) {
        this.buildingInfo = buildingInfo;
    }
}