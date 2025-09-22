package org.ace.accounting.web.system;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
import java.util.List;

import org.ace.accounting.common.Utils;
import org.ace.accounting.dto.ReservationDTO;
import org.ace.accounting.system.car.Car;
import org.ace.accounting.system.car.enumTypes.ReserveStatus;
import org.ace.accounting.system.car.service.interfaces.ICarService;
import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.FireProposal;
import org.ace.accounting.system.fire.service.interfaces.IFireProposalService;
import org.ace.accounting.system.reservation.Reservation;
import org.ace.accounting.system.reservation.persistence.interfaces.IReservationDAO;
import org.ace.accounting.system.reservation.service.interfaces.IEnquiryReservationService;
import org.ace.accounting.system.reservation.service.interfaces.IReservationService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@ManagedBean(name = "ReservationEnquiryBean")
@ViewScoped
public class ReservationEnquiryBean extends BaseBean implements Serializable {

	  private static final long serialVersionUID = 1L;

	    // ---- Search criteria ----
	    private Date startDateFrom;
	    private Date endDateTo;
	    private String customerName;
	    private String selectedCarType;
	    private String selectedStatus;
	    private List<ReservationDTO> reservations;
	    private List<String> carTypes;
	    private List<Car> carList;
	    private Reservation reservation=new Reservation();
	    private boolean createNew = true;
		private List<Reservation> reservationList;
		private List<Reservation> reserveList;
		private ReserveStatus selectedStatus1;
		private Reservation selectedReservation;
		private ReservationDTO selectReject;
		private String rejectionReason;
		public Reservation getSelectedReservation() { return selectedReservation; }
		public void setSelectedReservation(Reservation selectedReservation) { this.selectedReservation = selectedReservation; }
		private List<ReservationDTO> results;
		
		public List<ReservationDTO> getResults() {
			return results;
		}
		public void setResults(List<ReservationDTO> results) {
			this.results = results;
		}
		private final String reportName = "CarRentalLetter";
		private final String fileName = "CarRentalLetter";
		private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
		private final String dirPath = getWebRootPath() + pdfDirPath;
		
	@ManagedProperty(value = "#{ReservationService}")
	private IReservationService reservationService;
	
	@ManagedProperty(value = "#{ReservationDAO}")
	private IReservationDAO reservationDAO;
	

	public IReservationDAO getReservationDAO() {
		return reservationDAO;
	}


	public void setReservationDAO(IReservationDAO reservationDAO) {
		this.reservationDAO = reservationDAO;
	}


	@ManagedProperty(value = "#{EnquiryReservationService}")
	private IEnquiryReservationService enquiryReservationService;
	
	@ManagedProperty(value = "#{CarService}")
	private ICarService carService;
	

	public ICarService getCarService() {
		return carService;
	}


	public void setCarService(ICarService carService) {
		this.carService = carService;
	}


	public IReservationService getReservationService() {
		return reservationService;
	}
	

	public void setReservationService(IReservationService reservationService) {
		this.reservationService = reservationService;
	}

	// Constructor
	public ReservationEnquiryBean() {
		
	}

	@PostConstruct
	public void init() {
		prepareCarTypes();
		carList = carService.findAll(); 	
	}
	
	
	public void printReservation(ReservationDTO res) {
        this.selectedReservation = reservationService.findById(res.getId());
        System.out.println("Printing reservation: " + res.getId());
    }
	
	public void openRejectDialog(ReservationDTO res) {
	    this.selectReject = res;
	    this.rejectionReason = null;
	}
	 
	public void loadReservations() {
	    try {
	        reservations = enquiryReservationService.findByCriteria(
	            startDateFrom,
	            endDateTo,
	            customerName,
	            selectedCarType,
	            selectedStatus
	        );
	    } catch (SystemException e) {
	        e.printStackTrace();
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                             "Error loading reservations",
	                             e.getMessage()));
	    }
	}

	public List<Car> getCarList() {
		return carList;
	}

	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}

	private void prepareCarTypes() {
	    carTypes = new ArrayList<>();
	    carList = carService.findAll(); 
	    for (Car c : carList) {
	        if (!carTypes.contains(c.getType())) {
	            carTypes.add(c.getType());
	        }
	    }
	}
	
	public void approve(ReservationDTO resDto) {
	    try {
	        reservationService.approveReservation(resDto.getId()); // call service directly
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage("Reservation approved"));
	        loadReservations(); // refresh table
	    } catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error approving reservation", e.getMessage()));
	        e.printStackTrace();
	    }
	}
	
	public void reject() {
	    try {
	        if (rejectionReason == null || rejectionReason.trim().isEmpty()) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_WARN,
	                "Please provide a rejection reason!", ""));
	            return;
	        }

	        reservationService.rejectReservation(selectReject.getId(), rejectionReason.trim());

	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage("Reservation rejected with reason: " + rejectionReason));

	        rejectionReason = null;
	        loadReservations();
	       
	    } catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR,
	            "Error rejecting reservation", e.getMessage()));
	        e.printStackTrace();
	    }
	}


	/*
	 * public void reject(ReservationDTO resDto) { try {
	 * reservationService.rejectReservation(resDto.getId());
	 * FacesContext.getCurrentInstance().addMessage(null, new
	 * FacesMessage("Reservation rejected")); loadReservations(); } catch (Exception
	 * e) { FacesContext.getCurrentInstance().addMessage(null, new
	 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Error rejecting reservation",
	 * e.getMessage())); e.printStackTrace(); } }
	 */

	public void deleteReservation(ReservationDTO resDto) {
		
	    try {
	    	Reservation res = reservationService.findById(resDto.getId());
	    	 reservationService.deleteReservation(res);
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_INFO, "Reservation deleted successfully!", ""));
	        loadReservations();
	    } catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error deleting reservation!", e.getMessage()));
	        e.printStackTrace();
	    }
	}
	
	public void searchReservations() {
	    try {
	        reservations = enquiryReservationService.findByCriteria(
	            startDateFrom,
	            endDateTo,
	            customerName,
	            selectedCarType,
	            selectedStatus
	        );
	    } catch (SystemException e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(FacesMessage.SEVERITY_ERROR,
	                             "Error searching reservations",
	                             e.getMessage()));
	    }
	}
	
	public byte[] generateReport(ReservationDTO dto) {
		String template;
		if (dto.getreserveStatus() == ReserveStatus.APPROVED) {
		    template = "ReservationApprovalForm.jrxml";
		} else if (dto.getreserveStatus() == ReserveStatus.REJECTED) {
		    template = "ReservationRejectionForm.jrxml";
		} else {
	        throw new IllegalArgumentException("Unknown status: " + dto.getreserveStatus());
	    }
		System.out.println("generateReport: Writing PDF to " + dirPath + fileName + ".pdf");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

	    try (InputStream inputStream = Thread.currentThread()
	             .getContextClassLoader()
	             .getResourceAsStream(template)) {

	        if (inputStream == null) {
	            addErrorMessage(null, "Report template not found!");
	            return null;
	        }

	        JasperDesign design = JRXmlLoader.load(inputStream);
	        JasperReport report = JasperCompileManager.compileReport(design);

	        Map<String, Object> params = new HashMap<>();
	        params.put("customerName", dto.getCustomerName());
	        params.put("email", dto.getEmail());
	        params.put("phoneNumber", dto.getPhoneNumber());
	        params.put("carType", dto.getCarType());
	        params.put("totalCost", dto.getTotalCost());
	       params.put("startDate", dto.getStartDate());
	        params.put("endDate", dto.getEndDate());

	        if (dto.getreserveStatus() == ReserveStatus.REJECTED) {
	            params.put("reason", dto.getReason());
	        }
	        JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
	        JasperExportManager.exportReportToPdfStream(print, baos);

	        FileUtils.forceMkdir(new File(dirPath));
	        JasperExportManager.exportReportToPdfFile(print, dirPath + fileName + ".pdf");

	        addInfoMessage(null, "Report generated successfully");
	    } catch (Exception e) {
	        e.printStackTrace();
	        addErrorMessage(null, "Report generation failed: " + e.getMessage());
	    }
		return baos.toByteArray();
	}

	
public StreamedContent getDownload() {
	try {
		String pdfFilePath = dirPath + fileName + ".pdf";
		System.out.println("getDownload: Looking for PDF at " + pdfFilePath);
		File file = new File(pdfFilePath);

		// Generate report if PDF does not exist
		if (!file.exists()) {
			generateReport(selectReject);
		}

		if (!file.exists()) {
			addErrorMessage(null, "Download Failed: PDF file could not be generated.");
			return null;
		}

		InputStream input = new FileInputStream(file);
		ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();

		return new DefaultStreamedContent(input, ext.getMimeType(file.getName()), file.getName());

	} catch (Exception e) {
		e.printStackTrace();
		addErrorMessage(null, "Download Failed: " + e.getMessage());
		return null;
	}
}

public void generateAndStoreReport(Reservation reservation) {
    byte[] pdfBytes = generateReport(new ReservationDTO(reservation));
    if (pdfBytes != null) {
        reservationDAO.saveReportPDF(reservation, pdfBytes);;
        addInfoMessage("PDF report generated and stored successfully.");
    }
}

public void exportToExcel() {
    try {
        if (reservations == null || reservations.isEmpty()) {
            reservations = enquiryReservationService.findByCriteria(
                null, null, null, null, null
            );
        }

        String fileName = "reservations_" + 
            new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date()) + ".xlsx";

        ReservationExcelExport.exportReservationsToExcel(reservations, fileName);

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Export Successful", "File exported successfully."));
    } catch (Exception e) {
        e.printStackTrace();
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Export Failed", e.getMessage()));
    }
}


	
	public Date getStartDateFrom() {
		return startDateFrom;
	}

	public void setStartDateFrom(Date startDateFrom) {
		this.startDateFrom = startDateFrom;
	}

	public Date getEndDateTo() {
		return endDateTo;
	}

	public void setEndDateTo(Date endDateTo) {
		this.endDateTo = endDateTo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSelectedCarType() {
		return selectedCarType;
	}

	public void setSelectedCarType(String selectedCarType) {
		this.selectedCarType = selectedCarType;
	}

	public String getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public List<ReservationDTO> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationDTO> reservations) {
		this.reservations = reservations;
	}

	public List<String> getCarTypes() {
		return carTypes;
	}

	public void setCarTypes(List<String> carTypes) {
		this.carTypes = carTypes;
	}
	 public List<Reservation> getReservationList() {
			return reservationList;
		}

		public void setReservationList(List<Reservation> reservationList) {
			this.reservationList = reservationList;
		}


		public List<Reservation> getReserveList() {
			return reserveList;
		}


		public void setReserveList(List<Reservation> reserveList) {
			this.reserveList = reserveList;
		}

		public Reservation getReservation() {
			return reservation;
		}


		public void setReservation(Reservation reservation) {
			this.reservation = reservation;
		}


		public boolean isCreateNew() {
			return createNew;
		}


		public void setCreateNew(boolean createNew) {
			this.createNew = createNew;
		}

		public IEnquiryReservationService getEnquiryReservationService() {
			return enquiryReservationService;
		}


		public void setEnquiryReservationService(IEnquiryReservationService enquiryReservationService) {
			this.enquiryReservationService = enquiryReservationService;
		}

		

		public ReserveStatus getSelectedStatus1() {
		    return selectedStatus1;
		}

		public void setSelectedStatus(ReserveStatus selectedStatus1) {
		    this.selectedStatus1 = selectedStatus1;
		}

		public ReserveStatus[] getAllStatuses() {
		    return ReserveStatus.values();
		}
		public String getRejectionReason() {
			return rejectionReason;
		}
		public void setRejectionReason(String rejectionReason) {
			this.rejectionReason = rejectionReason;
		}
		public ReservationDTO getSelectReject() {
			return selectReject;
		}
		public void setSelectReject(ReservationDTO selectReject) {
			this.selectReject = selectReject;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getReportName() {
			return reportName;
		}
		public String getFileName() {
			return fileName;
		}
		public String getPdfDirPath() {
			return pdfDirPath;
		}
		public String getDirPath() {
			return dirPath;
		}
		public void setSelectedStatus1(ReserveStatus selectedStatus1) {
			this.selectedStatus1 = selectedStatus1;
		}
    
}