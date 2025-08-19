package org.ace.accounting.web.report;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.ace.accounting.system.fire.BuildingInfo;
import org.ace.accounting.system.fire.FireProposal;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@ManagedBean(name = "FireReportActionBean")
@ViewScoped
public class FireReportActionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dirPath = "/var/reports/fire/"; // adjust as needed
    private String fileName = "FireProposalReport";

    private FireProposal fireProposal;

    // ================== PDF GENERATION ==================
    
    public StreamedContent generateReport() {
        try {
            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("firePolicyReport.jrxml");

            Map<String, Object> parameters = prepareParameters();

            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(fireProposal.getBuildingList());

            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);

            // Export to PDF in memory
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

            // Build StreamedContent for PrimeFaces
            return DefaultStreamedContent.builder()
                    .name(fileName.concat(".pdf"))
                    .contentType("application/pdf")
                    .stream(() -> new ByteArrayInputStream(baos.toByteArray()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // ================== DOWNLOAD (Excel) ==================
    public StreamedContent getDownload() {
        if (fireProposal == null || fireProposal.getBuildingList().isEmpty()) {
            return null;
        }
        return getDownloadValue();
    }

    private StreamedContent getDownloadValue() {
        try {
            List<JasperPrint> prints = new ArrayList<>();

            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("fireProposalLetter.jrxml");

            Map<String, Object> parameters = prepareParameters();

            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(fireProposal.getBuildingList());

            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
            prints.add(jasperPrint);

            FileUtils.forceMkdir(new File(dirPath));

            File destFile = new File(dirPath + fileName.concat(".xls"));

            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(SimpleExporterInput.getInstance(prints));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFile));

            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setDetectCellType(true);
            configuration.setIgnoreCellBorder(false);
            configuration.setAutoFitPageHeight(true);
            configuration.setCollapseRowSpan(true);
            configuration.setFontSizeFixEnabled(true);
            configuration.setColumnWidthRatio(1.5F);

            exporter.setConfiguration(configuration);
            exporter.exportReport();

            File file = new File(dirPath + fileName.concat(".xls"));
            InputStream input = new FileInputStream(file);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            return new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ================== PARAMETER BUILDER ==================
    private Map<String, Object> prepareParameters() {
        Map<String, Object> parameters = new HashMap<>();

        String runDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        parameters.put("CustomerName", fireProposal.getCustomer());
        parameters.put("PolicyNo", fireProposal.getPolicyNumber());
        parameters.put("ProposalNo", fireProposal.getProposalNo());
        parameters.put("PropertyLocation", fireProposal.getPropertyLocation());
        parameters.put("CurrencyType", fireProposal.getCurrencyType().toString());
        parameters.put("BasicSumInsured", BigDecimal.valueOf(fireProposal.calculateTotalSumInsured()).setScale(2, RoundingMode.HALF_UP));
        parameters.put("TotalPremium", BigDecimal.valueOf(getTotalPremium()).setScale(2, RoundingMode.HALF_UP));
        parameters.put("RunDate", runDate);

        String logoPath = FacesContext.getCurrentInstance().getExternalContext()
                .getRealPath("/resources/images/logo.png");
        parameters.put("Logo", logoPath);

        return parameters;
    }

    private double getTotalPremium() {
        return fireProposal.getBuildingList().stream()
                .mapToDouble(b -> b.getTotalPremiumPeriod() != null ? b.getTotalPremiumPeriod() : 0.0)
                .sum();
    }

    // ================== GETTERS/SETTERS ==================
    public FireProposal getFireProposal() {
        return fireProposal;
    }

    public void setFireProposal(FireProposal fireProposal) {
        this.fireProposal = fireProposal;
    }
}
