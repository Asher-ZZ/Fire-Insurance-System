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
import org.primefaces.event.TabChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.faces.event.AjaxBehaviorEvent;

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

	    if ("buildingInfo".equals(currentStep)) {
	        // Validate required fields
	        if (buildingInfo == null || !buildingInfo.isValid()) {
	            addErrorMessage(null, "Please fill in all mandatory building info fields before proceeding.");
	            return currentStep; // prevent moving forward
	        }

	        validateDates();
	        if (hasErrors()) {
	            return currentStep; // prevent moving forward if errors exist
	        }
	    }

	    if ("premiumInfo".equals(newStep)) {
	        // Populate premiumList from buildings if empty
	        if (premiumList == null) {
	            premiumList = new ArrayList<>();
	        } else {
	            premiumList.clear();
	        }

	        double divisor = getPaymentDivisor(fireProposal.getPaymentType());

	        for (BuildingInfo b : buildings) {
	            Premium p = new Premium();
	            p.setBuildingName(b.getBuildingName());
	            p.setSumInsured(b.getSumInsured());
	            p.setFireProposal(fireProposal);

	            p.setBasicPremiumPeriod(0.0);
	            p.setAddOnPremiumPeriod(0.0);
	            p.setBasicPremiumTerm(0.0);
	            p.setAddOnPremiumTerm(0.0);
	            p.setTotalPremiumPeriod(0.0);

	            premiumList.add(p);
	        }

	        fireProposal.setPremiumList(premiumList);
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

	public void removePremium(Premium premium) {
		if (premium != null && premiumList.contains(premium)) {
			premiumList.remove(premium);
			refreshTotals();
			logger.debug("Removed premium: BuildingName={}", premium.getBuildingName());
		}
	}

	private void refreshTotals() {
		fireProposal.setTotalSumInsured(fireProposal.calculateTotalSumInsured());
		fireProposal.setTotalPremiumPeriod(premiumList.stream().mapToDouble(Premium::getTotalPremiumPeriod).sum());
		logger.debug("Refreshed totals: TotalSumInsured={}, TotalPremiumPeriod={}", fireProposal.getTotalSumInsured(),
				fireProposal.getTotalPremiumPeriod());
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

			Premium premiumToAdd = new Premium();
			premiumToAdd.setBuildingName(cloned.getBuildingName());
			premiumToAdd.setSumInsured(cloned.getSumInsured());
			premiumToAdd.setFireProposal(fireProposal);

			premiumList.add(premiumToAdd);
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
		// ensure paymentType list and recalculation
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
	 * Recalculate all derived premium values for every row and overall total. This
	 * uses double arithmetic (keeps compatibility with existing Premium
	 * getters/setters).
	 */
	public void recalculatePremiums() {
		if (premiumList == null)
			return;

		PaymentType pt = fireProposal.getPaymentType();
		double divisor = getPaymentDivisor(pt);

		double grandTotal = 0.0;

		for (Premium p : premiumList) {
			// defensive null -> treat as 0.0
			double basicPeriod = (p.getBasicPremiumPeriod() != null) ? p.getBasicPremiumPeriod() : 0.0;
			double addonPeriod = (p.getAddOnPremiumPeriod() != null) ? p.getAddOnPremiumPeriod() : 0.0;

			// Compute terms = period / divisor
			// We do step-by-step arithmetic to avoid floating surprises
			double basicTerm = 0.0;
			if (divisor != 0.0)
				basicTerm = basicPeriod / divisor;
			double addonTerm = 0.0;
			if (divisor != 0.0)
				addonTerm = addonPeriod / divisor;

			// optional: round to 2 decimals
			BigDecimal bt = BigDecimal.valueOf(basicTerm).setScale(2, RoundingMode.HALF_UP);
			BigDecimal at = BigDecimal.valueOf(addonTerm).setScale(2, RoundingMode.HALF_UP);
			double basicTermRounded = bt.doubleValue();
			double addonTermRounded = at.doubleValue();

			p.setBasicPremiumTerm(basicTermRounded); // ensure your Premium has setter
			p.setAddOnPremiumTerm(addonTermRounded);

			double totalPeriod = basicTermRounded + addonTermRounded;
			BigDecimal totalBd = BigDecimal.valueOf(totalPeriod).setScale(2, RoundingMode.HALF_UP);
			p.setTotalPremiumPeriod(totalBd.doubleValue());

			grandTotal += p.getTotalPremiumPeriod();
		}

		// Save grand total into fireProposal
		// round grand total to 2 decimals
		BigDecimal g = BigDecimal.valueOf(grandTotal).setScale(2, RoundingMode.HALF_UP);
		fireProposal.setTotalPremiumPeriod(g.doubleValue());
	}

	/**
	 * Helper used while the user is typing in the Add new premium fields before
	 * adding (keeps preview consistent). You can call recalculatePremiums() or
	 * implement small logic if needed for newPremium.
	 */
	public void tempCalcForNewPremium(AjaxBehaviorEvent evt) {
		// If you want to calculate newPremium terms on the fly before the user clicks
		// Add:
		PaymentType pt = fireProposal.getPaymentType();
		double divisor = getPaymentDivisor(pt);
		double basicPeriod = (newPremium.getBasicPremiumPeriod() != null) ? newPremium.getBasicPremiumPeriod() : 0.0;
		double addonPeriod = (newPremium.getAddOnPremiumPeriod() != null) ? newPremium.getAddOnPremiumPeriod() : 0.0;

		double basicTerm = divisor != 0.0 ? basicPeriod / divisor : 0.0;
		double addonTerm = divisor != 0.0 ? addonPeriod / divisor : 0.0;

		newPremium.setBasicPremiumTerm(BigDecimal.valueOf(basicTerm).setScale(2, RoundingMode.HALF_UP).doubleValue());
		newPremium.setAddOnPremiumTerm(BigDecimal.valueOf(addonTerm).setScale(2, RoundingMode.HALF_UP).doubleValue());
		newPremium.setTotalPremiumPeriod(
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

		// Clear previous premium list so we rebuild all rows
		premiumList.clear();

		double divisor = getPaymentDivisor(fireProposal.getPaymentType());
		double basicPeriod = (newPremium.getBasicPremiumPeriod() != null ? newPremium.getBasicPremiumPeriod() : 0.0);
		double addonPeriod = (newPremium.getAddOnPremiumPeriod() != null ? newPremium.getAddOnPremiumPeriod() : 0.0);

		for (BuildingInfo b : buildings) {
			Premium p = new Premium();
			p.setBuildingName(b.getBuildingName());
			p.setSumInsured(b.getSumInsured());
			p.setFireProposal(fireProposal);

			p.setBasicPremiumPeriod(basicPeriod);
			p.setAddOnPremiumPeriod(addonPeriod);

			double basicTerm = divisor != 0 ? basicPeriod / divisor : 0.0;
			double addonTerm = divisor != 0 ? addonPeriod / divisor : 0.0;

			p.setBasicPremiumTerm(round(basicTerm));
			p.setAddOnPremiumTerm(round(addonTerm));
			p.setTotalPremiumPeriod(round(basicTerm + addonTerm));

			premiumList.add(p);
		}

		fireProposal.setPremiumList(premiumList);

		// Reset newPremium input
		newPremium = new Premium();

		// Recalculate grand totals
		recalculatePremiums();
	}
	
	public void onTabChange(TabChangeEvent event) {
	    if ("premiumInfo".equals(event.getTab().getId())) {
	        // Only populate if premiumList is empty
	        if (premiumList == null) {
	            premiumList = new ArrayList<>();
	        } else {
	            premiumList.clear();
	        }

	        double divisor = getPaymentDivisor(fireProposal.getPaymentType());

	        for (BuildingInfo b : buildings) {
	            Premium p = new Premium();
	            p.setBuildingName(b.getBuildingName());
	            p.setSumInsured(b.getSumInsured());
	            p.setFireProposal(fireProposal);

	            // Initialize premiums to 0
	            p.setBasicPremiumPeriod(0.0);
	            p.setAddOnPremiumPeriod(0.0);
	            p.setBasicPremiumTerm(0.0);
	            p.setAddOnPremiumTerm(0.0);
	            p.setTotalPremiumPeriod(0.0);

	            premiumList.add(p);
	        }

	        fireProposal.setPremiumList(premiumList);
	    }
	}



	private void loadPremiumsFromBuildings() {
	    if (buildings == null || buildings.isEmpty()) return;

	    premiumList.clear();
	    double divisor = getPaymentDivisor(fireProposal.getPaymentType());

	    for (BuildingInfo b : buildings) {
	        Premium p = new Premium();
	        p.setBuildingName(b.getBuildingName());
	        p.setSumInsured(b.getSumInsured());
	        p.setFireProposal(fireProposal);

	        p.setBasicPremiumPeriod(0.0);
	        p.setAddOnPremiumPeriod(0.0);
	        p.setBasicPremiumTerm(0.0);
	        p.setAddOnPremiumTerm(0.0);
	        p.setTotalPremiumPeriod(0.0);

	        premiumList.add(p);
	    }

	    fireProposal.setPremiumList(premiumList);
	}


	private double round(double value) {
		return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	/*
	 * private void addPremiumForBuilding(BuildingInfo b) { Premium p = new
	 * Premium(); p.setBuildingName(b.getBuildingName());
	 * p.setSumInsured(b.getSumInsured()); // Set default values for demo
	 * 
	 * p.setBasicPremiumPeriod(1.0); p.setBasicPremiumTerm(1.0);
	 * p.setTotalPremiumPeriod(p.getSumInsured() * p.getPremiumRate()); // example
	 * calculation premiumList.add(p);
	 * 
	 * }
	 */

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

	public void setNewPremium(Premium newPremium) {
		this.newPremium = newPremium;
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