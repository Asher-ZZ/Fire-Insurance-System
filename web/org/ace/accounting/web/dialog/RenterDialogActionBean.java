package org.ace.accounting.web.dialog;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.accounting.system.customer.Renter;
import org.ace.accounting.system.customer.service.interfaces.IRenterService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "RenterDialogActionBean")
@ViewScoped
public class RenterDialogActionBean extends BaseBean {

	@ManagedProperty(value = "#{RenterService}")
	protected IRenterService renterService;

	
	private List<Renter> renterList;

	@PostConstruct
	public void init() {
		renterList =renterService.findAll();
	}

	
	public void goToCustomerInfo() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                .redirect("ManageCustomerInformation.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public List<Renter> getRenterList() {
		return renterList;
	}
	
	public void selectRenter(Renter renter) {
		PrimeFaces.current().dialog().closeDynamic(renter);
		/* RequestContext.getCurrentInstance().closeDialog(branch); */
	}

	public void setRenterService(IRenterService renterService) {
		this.renterService = renterService;
	}
}