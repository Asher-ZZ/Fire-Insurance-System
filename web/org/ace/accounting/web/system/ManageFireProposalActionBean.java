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

import org.ace.accounting.common.BuildingClass;
import org.ace.accounting.common.CurrencyType1;
import org.ace.accounting.common.FloorType;
import org.ace.accounting.common.PaymentType;
import org.ace.accounting.common.RoofingType;
import org.ace.accounting.common.SaleChannel;
import org.ace.accounting.common.WallType;
import org.ace.accounting.common.validation.MessageId;
import org.ace.accounting.system.branch.Branch;
import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.FireProposal;
import org.ace.accounting.system.fire.service.interfaces.IFireProposalService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
	private BuildingInfo tempPremium; // Temporary object for premium input
	private List<FireProposal> fireProposalList;
	private int periodMin;
	private int periodMax;

	private String currentStep = "proposalInfo";

	private Date minDate = toDate(LocalDate.of(1990, 1, 1));
	private Date maxDate = toDate(LocalDate.now(ZoneId.of("Australia/Sydney")));
	private Double totalSumInsured;
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
		tempPremium = new BuildingInfo(); // Initialize tempPremium for UI input
		fireProposal.setBuildingList(buildings);
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
        String oldStep = event.getOldStep(); // current step
        String newStep = event.getNewStep();

        // Only block if going forward from buildingInfo to premiumInfo
        if ("buildingInfo".equals(oldStep) && "premiumInfo".equals(newStep)) {
            
        }

        if ("premiumInfo".equals(newStep)) {
            fireProposal.setBuildingList(buildings);
            recalculatePremiums();
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

	public String saveAll() {
		try {
			for (BuildingInfo b : buildings) {
				b.setFireProposal(fireProposal);
			}
			fireProposal.setBuildingList(buildings);
			calculatePolicyEndDate();
			logger.debug("Saving FireProposal with policyEndDate: {}", fireProposal.getPolicyEndDate());

			if (fireProposal.getProposalNo() == null || fireProposal.getProposalNo().isEmpty()) {
				String generatedNo = fireProposalService.generateProposalNo();
				fireProposal.setProposalNo(generatedNo);
			}

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
		return "/ui/system/home.xhtml?faces-redirect=true";

	}

	public String cancel() {
		// createNewFireProposal();
		return "/ui/system/home.xhtml?faces-redirect=true";
	}

	private void calculatePolicyEndDate() {
		if (fireProposal.getPolicyStartDate() == null || fireProposal.getInsurancePeriodDays() == null
				|| fireProposal.getInsurancePeriodUnit() == null) {
			fireProposal.setPolicyEndDate(null);
			return;
		}

		LocalDate start = fireProposal.getPolicyStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

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
		if (buildingInfo != null && buildingInfo.isValid()) {
			BuildingInfo cloned = buildingInfo.clone();
			buildings.add(cloned);
			buildingInfo = new BuildingInfo(); // reset input
		} else {
			addErrorMessage(null, "Please fill building details before adding.");
		}
	}

	// Return available payment types based on current insurance period
	public PaymentType[] getAvailablePaymentTypes() {
		Integer days = fireProposal.getInsurancePeriodDays();
		String unit = fireProposal.getInsurancePeriodUnit();

		if (days == null || unit == null) {
			return new PaymentType[] { PaymentType.LUMPSUM };
		}

		boolean isFullYear = ("DAY".equalsIgnoreCase(unit) && days == 365)
				|| ("MONTH".equalsIgnoreCase(unit) && days == 12) || ("YEAR".equalsIgnoreCase(unit) && days == 1);

		if (isFullYear) {
			// return all options
			return new PaymentType[] { PaymentType.LUMPSUM, PaymentType.SEMI_ANNUAL, PaymentType.QUARTER,
					PaymentType.MONTHLY };
		} else {
			// only lumpsum
			// ensure selected payment type is valid
			if (fireProposal.getPaymentType() != PaymentType.LUMPSUM) {
				fireProposal.setPaymentType(PaymentType.LUMPSUM);
			}
			return new PaymentType[] { PaymentType.LUMPSUM };
		}
	}

	// Called when user changes period unit/value (wired in XHTML)
	public void updatePeriodRange() {
		updatePeriodRange((AjaxBehaviorEvent) null); // reuse your existing method that sets periodMin/periodMax
		recalculatePremiums();
	}

	// Called when PaymentType changes (via p:ajax)
	public void onPaymentTypeChange(AjaxBehaviorEvent event) {
		recalculatePremiums();
	}

	// Calculate divisor for the payment type (divide by this)
	private double getPaymentDivisor(PaymentType paymentType) {
		if (paymentType == null)
			return 1.0;
		switch (paymentType) {
		case LUMPSUM:
			return 1.0;
		case SEMI_ANNUAL:
			return 2.0;
		case QUARTER:
			return 4.0;
		case MONTHLY:
			return 12.0;
		default:
			return 1.0;
		}
	}

	/**
	 * Recalculate all derived premium values for every row and overall total.
	 */
	public void recalculatePremiums() {
	    if (buildings == null)
	        return;

	    PaymentType pt = fireProposal.getPaymentType();
	    double divisor = getPaymentDivisor(pt);
	    double grandTotal = 0.0;
	    double totalSumInsured = 0.0;

	    for (BuildingInfo b : buildings) {
	        double basicPeriod = (b.getBasicPremiumPeriod() != null) ? b.getBasicPremiumPeriod() : 0.0;
	        double addonPeriod = (b.getAddOnPremiumPeriod() != null) ? b.getAddOnPremiumPeriod() : 0.0;

	        double basicTerm = divisor != 0.0 ? basicPeriod / divisor : 0.0;
	        double addonTerm = divisor != 0.0 ? addonPeriod / divisor : 0.0;

	        b.setBasicPremiumTerm(round(basicTerm));
	        b.setAddOnPremiumTerm(round(addonTerm));
	        b.setTotalPremiumPeriod(round(basicTerm + addonTerm));

	        grandTotal += b.getTotalPremiumPeriod();
	        totalSumInsured += (b.getSumInsured() != null) ? b.getSumInsured() : 0.0;
	    }

	    // update FireProposal fields for UI
	    fireProposal.setTotalPremiumPeriod(round(grandTotal));
	    fireProposal.setTotalSumInsured(totalSumInsured);
	}


	/**
	 * Helper used while the user is typing in the Add new premium fields before
	 * adding (keeps preview consistent).
	 */
	public void tempCalcForNewPremium(AjaxBehaviorEvent evt) {
		PaymentType pt = fireProposal.getPaymentType();
		double divisor = getPaymentDivisor(pt);
		double basicPeriod = (tempPremium.getBasicPremiumPeriod() != null) ? tempPremium.getBasicPremiumPeriod() : 0.0;
		double addonPeriod = (tempPremium.getAddOnPremiumPeriod() != null) ? tempPremium.getAddOnPremiumPeriod() : 0.0;

		double basicTerm = divisor != 0.0 ? basicPeriod / divisor : 0.0;
		double addonTerm = divisor != 0.0 ? addonPeriod / divisor : 0.0;

		tempPremium.setBasicPremiumTerm(BigDecimal.valueOf(basicTerm).setScale(2, RoundingMode.HALF_UP).doubleValue());
		tempPremium.setAddOnPremiumTerm(BigDecimal.valueOf(addonTerm).setScale(2, RoundingMode.HALF_UP).doubleValue());
		tempPremium.setTotalPremiumPeriod(
				BigDecimal.valueOf(basicTerm + addonTerm).setScale(2, RoundingMode.HALF_UP).doubleValue());
	}

	/**
	 * Update addPremium to compute terms for the new row before adding, then recalc
	 * totals.
	 */
	public void addPremium() {
		if (buildings.isEmpty()) {
			addErrorMessage(null, "Please add at least one building before adding premium.");
			return;
		}

		double divisor = getPaymentDivisor(fireProposal.getPaymentType());
		double basicPeriod = (tempPremium.getBasicPremiumPeriod() != null) ? tempPremium.getBasicPremiumPeriod() : 0.0;
		double addonPeriod = (tempPremium.getAddOnPremiumPeriod() != null) ? tempPremium.getAddOnPremiumPeriod() : 0.0;

		for (BuildingInfo b : buildings) {
			b.setBasicPremiumPeriod(basicPeriod);
			b.setAddOnPremiumPeriod(addonPeriod);
			b.setBasicPremiumTerm(round(basicPeriod / divisor));
			b.setAddOnPremiumTerm(round(addonPeriod / divisor));
			b.setTotalPremiumPeriod(round((basicPeriod + addonPeriod) / divisor));
		}

		tempPremium = new BuildingInfo(); // Reset tempPremium input
		recalculatePremiums();
	}

	public void onTabChange(TabChangeEvent event) {
		if ("premiumInfo".equals(event.getTab().getId())) {
			fireProposal.setBuildingList(buildings);
			recalculatePremiums();
		}
	}

	public void returnBranch(SelectEvent event) {
		Branch branch = (Branch) event.getObject();
		fireProposal.setBranch(branch);
	}

	private double round(double value) {
		return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
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

	public FireProposal getFireProposal() {
		return fireProposal != null ? fireProposal : (fireProposal = new FireProposal());
	}

	public BuildingInfo getBuildingInfo() {
		return buildingInfo != null ? buildingInfo : (buildingInfo = new BuildingInfo());
	}

	public BuildingInfo getTempPremium() {
		return tempPremium != null ? tempPremium : (tempPremium = new BuildingInfo());
	}

	public void setTempPremium(BuildingInfo tempPremium) {
		this.tempPremium = tempPremium;
	}

	public List<FireProposal> getFireProposalList() {
		return fireProposalList != null ? fireProposalList : (fireProposalList = new ArrayList<>());
	}

	public int getPeriodMin() {
		return periodMin;
	}

	public void setPeriodMin(int periodMin) {
		this.periodMin = periodMin;
	}

	public int getPeriodMax() {
		return periodMax;
	}

	public void setPeriodMax(int periodMax) {
		this.periodMax = periodMax;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public SaleChannel[] getSaleChannels() {
		return SaleChannel.values();
	}

	public CurrencyType1[] getCurrencyTypes() {
		return CurrencyType1.values();
	}

	public PaymentType[] getPaymentTypes() {
		return PaymentType.values();
	}

	 public WallType[] getWallTypes() {
	          return WallType.values();
	      }
	   
	   public RoofingType[] getRoofingTypes() {
	        return RoofingType.values();
	    }
	   public BuildingClass[] getBuildingClasses() {
	        return BuildingClass.values();
	    }
	   public FloorType[] getFloorTypes() {
	        return FloorType.values();
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
	public Double getTotalSumInsured() {
	    return fireProposal != null ? fireProposal.calculateTotalSumInsured() : 0.0;
	}


	public void setTotalSumInsured(Double totalSumInsured) {
		this.totalSumInsured = totalSumInsured;
	}
	
	public void calculateSquareFeet(AjaxBehaviorEvent event) {
        double length = buildingInfo.getLength() != null ? buildingInfo.getLength() : 0.0;
        double width = buildingInfo.getWidth() != null ? buildingInfo.getWidth() : 0.0;
        buildingInfo.setSquareFeet(length * width); // Calculate area as length × width
    }
}