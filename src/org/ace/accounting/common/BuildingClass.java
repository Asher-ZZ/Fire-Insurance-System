package org.ace.accounting.common;

public enum BuildingClass {
	FIRST_CLASS("FIRST CLASS"), SECOND_CLASS("SECOND CLASS"), THIRD_CLASS("THIRD CLASS"), FOURTH_CLASS("FOURTH CLASS");

	private final String label;

	BuildingClass(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
