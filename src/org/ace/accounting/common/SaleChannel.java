package org.ace.accounting.common;

public enum SaleChannel {
	ONLINE("Online"), RETAIL("Retail"), WHOLESALE("Wholesale"), PARTNER("Partner"), OTHER("Other"),
	AGENT("Agent")
	;
	// Added to match the default in FireProposal
	// Add more as needed

	private String label;

	private SaleChannel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}