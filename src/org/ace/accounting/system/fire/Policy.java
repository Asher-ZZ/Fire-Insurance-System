 package org.ace.accounting.system.fire;
 public class Policy {
		private String policyNo;
		private String proposalNo;
		private String saleChannel;
		private String customer;
		private String branch;
		private String paymentType;
		private double totalPremium;
		private double totalSumInsured;

		public Policy(String policyNo, String proposalNo, String saleChannel, String customer, String branch,
				String paymentType, double totalPremium, double totalSumInsured) {
			this.policyNo = policyNo;
			this.proposalNo = proposalNo;
			this.saleChannel = saleChannel;
			this.customer = customer;
			this.branch = branch;
			this.paymentType = paymentType;
			this.totalPremium = totalPremium;
			this.totalSumInsured = totalSumInsured;
		}

		// Getters for JSF EL
		public String getPolicyNo() {
			return policyNo;
		}

		public String getProposalNo() {
			return proposalNo;
		}

		public String getSaleChannel() {
			return saleChannel;
		}

		public String getCustomer() {
			return customer;
		}

		public String getBranch() {
			return branch;
		}

		public String getPaymentType() {
			return paymentType;
		}

		public double getTotalPremium() {
			return totalPremium;
		}

		public double getTotalSumInsured() {
			return totalSumInsured;
		}
	}