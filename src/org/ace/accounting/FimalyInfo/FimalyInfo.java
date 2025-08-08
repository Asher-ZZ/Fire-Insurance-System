package org.ace.accounting.FimalyInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name= "FamilyInfo")
@TableGenerator(name = "FAMI_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "FAMI_GEN", allocationSize = 10)
@NamedQueries(value =  @NamedQuery(name = "Family.findAll", query = "SELECT f FROM FamilyInfo f"))
public class FimalyInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "FAMI_GEN")
	private String id;
	
	private String fatherName;
	private String motherName;
	private String phoneNumber;

}
