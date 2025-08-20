/*
 * package org.ace.accounting.web.report;
 * 
 * import java.io.ByteArrayInputStream; import java.io.ByteArrayOutputStream;
 * import java.io.File; import java.io.InputStream; import java.io.Serializable;
 * import java.math.BigDecimal; import java.text.SimpleDateFormat; import
 * java.util.ArrayList; import java.util.HashMap; import java.util.List; import
 * java.util.Map;
 * 
 * import javax.annotation.PostConstruct; import javax.faces.bean.ManagedBean;
 * import javax.faces.bean.ManagedProperty; import javax.faces.bean.ViewScoped;
 * import javax.faces.context.FacesContext;
 * 
 * import org.ace.accounting.system.fire.BuildingInfo; import
 * org.ace.accounting.system.fire.FireProposal; import
 * org.ace.accounting.system.fire.service.interfaces.IFireProposalService;
 * import org.ace.java.web.common.BaseBean; import
 * org.apache.commons.io.FileUtils; import
 * org.primefaces.model.DefaultStreamedContent; import
 * org.primefaces.model.StreamedContent;
 * 
 * import net.sf.jasperreports.engine.JREmptyDataSource; import
 * net.sf.jasperreports.engine.JasperCompileManager; import
 * net.sf.jasperreports.engine.JasperExportManager; import
 * net.sf.jasperreports.engine.JasperFillManager; import
 * net.sf.jasperreports.engine.JasperPrint; import
 * net.sf.jasperreports.engine.JasperReport; import
 * net.sf.jasperreports.engine.data.JRBeanCollectionDataSource; import
 * net.sf.jasperreports.engine.design.JasperDesign; import
 * net.sf.jasperreports.engine.xml.JRXmlLoader;
 * 
 * 
 * 
 * @ManagedBean(name = "FireReportActionBean")
 * 
 * @ViewScoped public class FireReportActionBean extends BaseBean { private
 * static final long serialVersionUID = 1L;
 * 
 * @ManagedProperty(value = "#{FireProposalService}") private
 * IFireProposalService fireProposalService;
 * 
 * public void setFireProposalService(IFireProposalService fireProposalService)
 * { this.fireProposalService = fireProposalService; }
 * 
 * private FireProposal fireProposal; private StreamedContent letter; private
 * final String reportName = "FirePolicyReport"; private final String pdfDirPath
 * = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
 * private final String dirPath = getWebRootPath() + pdfDirPath; private final
 * String fileName = "Fire Policy Report";
 * 
 * @PostConstruct public void init() { if (fireProposal == null) { fireProposal
 * = new FireProposal(); } if (fireProposal.getBuildingList() == null) {
 * fireProposal.setBuildingList(new ArrayList<>()); } }
 * 
 * public void generateLetter() { try { if (fireProposal == null ||
 * fireProposal.getBuildingList() == null) { throw new
 * IllegalStateException("FireProposal or Building list is null. Save it first."
 * ); }
 * 
 * // ---- Totals using double ---- double totalSumInsured = 0.0; double
 * basicPremiumTerm = 0.0; double addOnPremiumTerm = 0.0; double
 * totalPremiumPeriod = 0.0;
 * 
 * for (BuildingInfo b : fireProposal.getBuildingList()) { if (b == null)
 * continue; if (b.getSumInsured() != null) totalSumInsured +=
 * b.getSumInsured().doubleValue(); if (b.getBasicPremiumTerm() != null)
 * basicPremiumTerm += b.getBasicPremiumTerm().doubleValue(); if
 * (b.getAddOnPremiumTerm() != null) addOnPremiumTerm +=
 * b.getAddOnPremiumTerm().doubleValue(); if (b.getTotalPremiumPeriod() != null)
 * totalPremiumPeriod += b.getTotalPremiumPeriod().doubleValue(); }
 * 
 * // ---- Parameters for JasperReports ---- Map<String, Object> params = new
 * HashMap<String, Object>();
 * System.out.println("Customer Name"+fireProposal.getCustomer());
 * System.out.println("Policy Number"+fireProposal.getPolicyNumber());
 * System.out.println("ProposalNo"+fireProposal.getProposalNo());
 * params.put("Customer", fireProposal.getCustomer());
 * params.put("Policy Number", fireProposal.getPolicyNumber());
 * params.put("ProposalNo", fireProposal.getProposalNo());
 * params.put("SumInsured", totalSumInsured); params.put("BasicPremiumTerm",
 * basicPremiumTerm); params.put("AddOnPremiumTerm", addOnPremiumTerm);
 * params.put("TotalPremiumPeriod", totalPremiumPeriod); params.put("RunDate",
 * new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()));
 * 
 * InputStream jrxml = Thread.currentThread() .getContextClassLoader()
 * .getResourceAsStream("FirePolicyReport.jrxml"); JasperDesign design =
 * JRXmlLoader.load(jrxml); JasperReport report =
 * JasperCompileManager.compileReport(design); JasperPrint print =
 * JasperFillManager.fillReport(report, params, new JREmptyDataSource());
 * FileUtils.forceMkdir(new File(dirPath));
 * JasperExportManager.exportReportToPdfFile(print, dirPath +
 * fileName.concat(".pdf"));; // ---- Export to PDF ----
 * 
 * System.out.println("PDF generated successfully!");
 * 
 * } catch (Exception e) { e.printStackTrace(); this.letter = null; throw new
 * RuntimeException("Error generating Fire Proposal Letter", e); } }
 * 
 * public FireProposal getFireProposal() { return fireProposal; }
 * 
 * public void setFireProposal(FireProposal fireProposal) { this.fireProposal =
 * fireProposal; }
 * 
 * public StreamedContent getLetter() { return letter; } }
 */