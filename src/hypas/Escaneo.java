package hypas;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

import aplicacion.HyPASActivator;
import jp.co.kyoceramita.box.document.attribute.FileImageQuality;
import jp.co.kyoceramita.box.document.attribute.FileName;
import jp.co.kyoceramita.box.document.attribute.PDFFileFormat;
import jp.co.kyoceramita.box.document.attribute.StoringSize;
import jp.co.kyoceramita.job.ImageStatus;
import jp.co.kyoceramita.job.Job;
import jp.co.kyoceramita.job.JobException;
import jp.co.kyoceramita.job.JobService;
import jp.co.kyoceramita.job.JobType;
import jp.co.kyoceramita.job.attribute.JobCreationAttributeSet;
import jp.co.kyoceramita.job.attribute.MediaSizeName;
import jp.co.kyoceramita.job.attribute.Orientation;
import jp.co.kyoceramita.job.attribute.ScanColorMode;
import jp.co.kyoceramita.job.attribute.ScanToAppSendImageJobCreationAttributeSet;
import jp.co.kyoceramita.log.AppLogger;
import jp.co.kyoceramita.log.Logger;
import jp.co.kyoceramita.scan.ScanToAppJob;
import jp.co.kyoceramita.scan.attribute.ScanResolution;
import jp.co.kyoceramita.scan.attribute.ScanSize;
import jp.co.kyoceramita.send.attribute.SuffixType;
import jp.co.kyoceramita.box.document.attribute.PDFCompatibility;
import jp.co.kyoceramita.box.document.attribute.PDFPermission;

public class Escaneo implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static String ESCANEO_PREPARADO = "Escaneo preparado";
	public final static String ESCANEO_INICIADO = "Escaneo iniciado";
	public final static String ESCANEO_CANCELADO = "Escaneo cancelado";
	public final static String ESCANEO_FINALIZADO = "Escaneo finalizado";
	private final String EXTENSION_PDF = ".pdf";
	
	private static Escaneo instance;
	private ByteArrayOutputStream byteData;
	private String filename = "DefaultName"; 
	private String extension = EXTENSION_PDF;
	private Job escaneo;
	private ScanToAppSendImageJobCreationAttributeSet jcas;
	private String statusMessage;
	boolean stillProcessing;
	private Logger logger;

	public static Escaneo getInstance(){
		if(instance == null){
			return new Escaneo();
		}
		else {
			return instance;
		}
	}
	
	private Escaneo(){
		byteData = new ByteArrayOutputStream();
		statusMessage = "";
		logger = HyPASActivator.getDefault().getLogger();
	}
	
	private void setParamsEscaneo() throws Exception{
		logger.info("-- Seteo de parámetros de escaneo --");
		// The manuscript size is set to A4. 
		if (jcas.containsCategory(ScanSize.class)) { 
			jcas.set(new ScanSize(MediaSizeName.ISO_A4, Orientation.E, false)); 
		} 
		// The resolution is set. 
		if (jcas.containsCategory(ScanResolution.class)) { 
			jcas.set(ScanResolution.TYPE_300x300); 
		}
		// The color setting of the manuscript reading is set full-color. 
		if (jcas.containsCategory(ScanColorMode.class)) { 
			jcas.set(ScanColorMode.FULL_COLOR); 
		}
		// The paper size is set to A4. 
		if (jcas.containsCategory(StoringSize.class)) { 
			jcas.set(StoringSize.A4); 
		}
		// The document name is set (The date is added behind the file name). 
		if (jcas.containsCategory(FileName.class)) {
			FileName file = new FileName("AplicacionScan", SuffixType.DATE);
			jcas.set(file);
			filename = file.getFileName();
		}
		// The file format is set to PDF. 
		if (jcas.containsCategory(PDFFileFormat.class)) { 
			PDFCompatibility c = PDFCompatibility.ACROBAT_5_0; 
			FileImageQuality q = FileImageQuality.PDF_IMAGE_QUALITY_1; 
			PDFPermission p = new PDFPermission(); 
			PDFFileFormat form = new PDFFileFormat(c, q, p);
			jcas.set(form); 
		}
	}

	public String getStatus(){
		return statusMessage;
	}
	
	public ByteArrayOutputStream getArchivoEscaneado(){
		return byteData;
	}
	
	public String getArchivoEscaneadoFileName(){
		return filename + extension;
	}
	
	public String comenzarEscaneo(){
		// Generate job (scanning job transmission) setting.
		jcas = (ScanToAppSendImageJobCreationAttributeSet) JobCreationAttributeSet.newInstance(JobType.SCAN_TO_APP_SEND_IMAGE);
		try{
			//Setting parameters for the scan
			setParamsEscaneo();
			// Run the job (scanning job transmission).
			this.escaneo = JobService.getInstance().create(jcas);
			statusMessage = ESCANEO_PREPARADO;
			stillProcessing = true;
		}
		catch (JobException e) {
		// Return the reason why JobException is generated.
			statusMessage = "Error JobException "+ e.getMessage()+" "+e.getReasonCode()+" "+e.getLocalizedMessage(); 
			logger.error(statusMessage);
		} catch (Exception e) {
			e.printStackTrace();
			statusMessage = e.toString();
		}

		if(statusMessage.equals(ESCANEO_PREPARADO)){
			try {
				this.escaneo.start();
				statusMessage = ESCANEO_INICIADO;
				ObtenerImagenThread imagen = new ObtenerImagenThread();
				imagen.run();
			} catch (JobException e) {
				e.printStackTrace();
			}
		}
		return statusMessage;
	}
	
	public void cancelarEscaneo(){
		try {
			this.escaneo.cancel();
			statusMessage = ESCANEO_CANCELADO;
		} catch (JobException e) {
			e.printStackTrace();
		}
	}
	
	private class ObtenerImagenThread extends Thread {
		
		public void run(){
			System.out.println("-- Iniciando obtención de imagen --");
			try{
//				TestReader test = new TestReader(); // TODO: TEST DATA. eliminar para pruebas en MFP.
				while(stillProcessing){
					ScanToAppJob scanJob = (ScanToAppJob) escaneo;
					byte[] data = new byte[0];
					data = scanJob.getImageData();  // TODO: TEST DATA. descomentar para pruebas en MFP.
					ImageStatus status = scanJob.getImageStatus(); // TODO: TEST DATA. descomentar para pruebas en MFP.
//					data = test.readTestFile(); // TODO: TEST DATA. eliminar para pruebas en MFP.
//					ImageStatus status = test.getStatus(); // TODO: TEST DATA. eliminar para pruebas en MFP. 
					byteData.write(data);
					if(status.equals(ImageStatus.DOCUMENT_END)){
						stillProcessing = false;
						statusMessage = ESCANEO_FINALIZADO;
					}
					else if(status.equals(ImageStatus.FAILED)){
						stillProcessing = false;
						cancelarEscaneo();
					}
					else {
						statusMessage = status.toString();
					}
				}
//				test.close(); // TODO: TEST DATA. eliminar para pruebas en MFP.
				System.out.println("-- Data: " + byteData.toString());
				byteData.close();
				System.out.println("-- Fin obtención de imagen: " + filename);
				logger.info("-- Fin obtención de imagen: " + filename);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private class TestReader extends DataInputStream{

		private static final String filepath ="C:\\Users\\Avature\\workspace\\Aplicacion\\testpdf.pdf";
		int readStatus;

		public TestReader() throws IOException{
			super(new FileInputStream(filepath));
		}
		
		public byte[] readTestFile() throws IOException{
			byte[] b = new byte[8192];
			readStatus = read(b);
			return b;
		}
		
		public ImageStatus getStatus(){
			if(readStatus == -1){
				return ImageStatus.DOCUMENT_END;
			}
			else {
				return ImageStatus.PROCESSING;
			}
		}
	}
	
}
