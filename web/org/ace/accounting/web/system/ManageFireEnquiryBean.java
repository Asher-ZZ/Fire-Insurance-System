package org.ace.accounting.web.system;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "ManageFireEnquiryBean")
@ViewScoped
public class ManageFireEnquiryBean implements Serializable {

    private Date startDateFrom;
    private Date startDateTo;
    private String policyNo;
    private List<Policy> policies;

    // Constructor
    public ManageFireEnquiryBean() {
        policies = new ArrayList<>();
        setDefaultDates();
    }

    private void setDefaultDates() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        startDateTo = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        startDateFrom = cal.getTime();
    }

    // Getters and Setters
    public Date getStartDateFrom() {
        return startDateFrom;
    }

    public void setStartDateFrom(Date startDateFrom) {
        this.startDateFrom = startDateFrom;
    }

    public Date getStartDateTo() {
        return startDateTo;
    }

    public void setStartDateTo(Date startDateTo) {
        this.startDateTo = startDateTo;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    // Search action
    public void search() {
        // Clear previous results
        policies.clear();

        // Simulate database query based on search criteria
        try {
            // Validate search criteria and query database
            if (isValidSearchCriteria()) {
                policies = fetchPoliciesFromDatabase();
                if (policies.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "No records found", null));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Search completed", policies.size() + " records found"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid search criteria", "Please provide valid dates or policy number"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error during search", e.getMessage()));
        }
    }

    // Reset action
    public void reset() {
        policyNo = null;
        policies.clear();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Form reset", null));
    }

    // Simulate database query
    private List<Policy> fetchPoliciesFromDatabase() {
        List<Policy> result = new ArrayList<>();
        // Mock data for demonstration
        if (policyNo != null && !policyNo.isEmpty()) {
            result.add(new Policy(policyNo, "PROP001", "Direct", "John Doe", "Jane Smith", "Main Branch", 1500.00, 100000.00, "Credit Card"));
        } else {
            result.add(new Policy("POL001", "PROP002", "Agent", "Alice Brown", "Bob Wilson", "East Branch", 2000.00, 150000.00, "Bank Transfer"));
            result.add(new Policy("POL002", "PROP003", "Broker", "Charlie Davis", "Emma Clark", "West Branch", 1800.00, 120000.00, "Cash"));
        }
        return result;
    }

    // Validate search criteria
    private boolean isValidSearchCriteria() {
        // Ensure dates are logical or policy number is provided
        if (startDateFrom != null && startDateTo != null) {
            return startDateFrom.before(startDateTo) || startDateFrom.equals(startDateTo);
        }
        return policyNo != null && !policyNo.isEmpty();
    }

    // Policy class to hold data
    public static class Policy {
        private String policyNo;
        private String proposalNo;
        private String saleChannel;
        private String salePerson;
        private String customer;
        private String branch;
        private double totalPremium;
        private double totalSumInsured;
        private String paymentType;

        public Policy(String policyNo, String proposalNo, String saleChannel, String salePerson,
                      String customer, String branch, double totalPremium, double totalSumInsured, String paymentType) {
            this.policyNo = policyNo;
            this.proposalNo = proposalNo;
            this.saleChannel = saleChannel;
            this.salePerson = salePerson;
            this.customer = customer;
            this.branch = branch;
            this.totalPremium = totalPremium;
            this.totalSumInsured = totalSumInsured;
            this.paymentType = paymentType;
        }

        // Getters and Setters
        public String getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(String policyNo) {
            this.policyNo = policyNo;
        }

        public String getProposalNo() {
            return proposalNo;
        }

        public void setProposalNo(String proposalNo) {
            this.proposalNo = proposalNo;
        }

        public String getSaleChannel() {
            return saleChannel;
        }

        public void setSaleChannel(String saleChannel) {
            this.saleChannel = saleChannel;
        }

        public String getSalePerson() {
            return salePerson;
        }

        public void setSalePerson(String salePerson) {
            this.salePerson = salePerson;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public double getTotalPremium() {
            return totalPremium;
        }

        public void setTotalPremium(double totalPremium) {
            this.totalPremium = totalPremium;
        }

        public double getTotalSumInsured() {
            return totalSumInsured;
        }

        public void setTotalSumInsured(double totalSumInsured) {
            this.totalSumInsured = totalSumInsured;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }
    }
}