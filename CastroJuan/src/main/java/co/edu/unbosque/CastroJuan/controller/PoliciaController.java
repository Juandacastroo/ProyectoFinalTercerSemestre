package co.edu.unbosque.CastroJuan.controller;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.ui.Model;

import co.edu.unbosque.CastroJuan.model.Incidente;
import co.edu.unbosque.CastroJuan.model.Policia;
import co.edu.unbosque.CastroJuan.repository.IncidenteRepository;
import co.edu.unbosque.CastroJuan.repository.PoliciaRepository;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;

@Transactional
@Controller
@CrossOrigin(origins = "")
@RequestMapping
public class PoliciaController {
	@Autowired
	private PoliciaRepository porep;
	@Autowired
	private IncidenteRepository incirep;
	@Autowired
	private PoliciaService policiaService;
	@Autowired
	private IncidenteService incidenteService;

	@Autowired
	public void PDFGeneratorService(PoliciaService policiaService) {
		this.policiaService = policiaService;
	}

	@Autowired
	public void PDFService(PoliciaService agenteService, IncidenteService incidenteService) {
		this.policiaService = agenteService;
		this.incidenteService = incidenteService;
	}

	@GetMapping("/formp")
	public String mostrarFormulario(Model model) {
		model.addAttribute("agente", new Policia());
		return "agente-form";
	}

	@PostMapping("/guardar-agente")
	public String agregar(@RequestParam String nombre, @RequestParam String fechaNacimiento, @RequestParam String sexo,
			@RequestParam String fechaEntrada, @RequestParam String rangoActual,
			@RequestParam Integer numeroCasosAtendidos, Model model) {

		Policia temp = new Policia();

		temp.setNombre(nombre);
		temp.setFechaNacimiento(fechaNacimiento);
		temp.setSexo(sexo);
		temp.setFechaEntrada(fechaEntrada);
		temp.setNumeroCasosAtendidos(numeroCasosAtendidos);
		temp.setRangoActual(rangoActual);

		porep.save(temp);
		return ("index");

	}

	@GetMapping("/pdf-agente")
	public ResponseEntity<Resource> generarListaAgentesPDF() throws DocumentException {
		List<Policia> agentes = policiaService.obtenerTodo();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
		document.open();
		document.add(new Paragraph("\tLista de policias:\n "));
		for (Policia agente : agentes) {
			document.add(new Paragraph("Nombre: " + agente.getNombre()));
			document.add(new Paragraph("Fecha de Nacimiento: " + agente.getFechaNacimiento()));
			document.add(new Paragraph("Sexo: " + agente.getSexo()));
			document.add(new Paragraph("Fecha de Entrada: " + agente.getFechaEntrada()));
			document.add(new Paragraph("Rango: " + agente.getRangoActual()));
			document.add(new Paragraph("Número de Casos Atendidos: " + agente.getNumeroCasosAtendidos()));
			document.add(new Paragraph("----------------------------------------"));

		}

		document.close();
		byte[] pdfBytes = outputStream.toByteArray();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=lista_agentes_operaciones.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.contentLength(pdfBytes.length).body(new ByteArrayResource(pdfBytes));

	}

	public ResponseEntity<Resource> descargarAgentePDF() throws IOException {
		File pdfFile = new File("lista_agentes.pdf");
		Resource resource = new FileSystemResource(pdfFile);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=lista_agentes.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(resource);
	}

	@GetMapping("/pdf-agente-incidente")
	public ResponseEntity<Resource> descargarPDFAgentesOperaciones() throws DocumentException {
		List<Incidente> incidentes = incidenteService.obtenerTodo();
		List<Policia> policias = policiaService.obtenerTodo();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Document document = new Document();
		PdfWriter.getInstance(document, outputStream);
		document.open();

		for (Incidente incidente : incidentes) {
			Paragraph agenteInfo = new Paragraph();
			agenteInfo.add("Nombre: " + incidente.getEmpresaAfectada());
			agenteInfo.add("  Nombre: " + incidente.getFecha());

			agenteInfo.add("\nAgentes relacionados :");

			for (Policia policia : policias) {
				if (policia.getId() == incidente.getPolicia().getId()) {

					agenteInfo.add(policia.getNombre());

				}
			}

			document.add(agenteInfo);
			document.add(new Paragraph("\n"));
		}

		document.close();

		byte[] pdfBytes = outputStream.toByteArray();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=lista_agentes_operaciones.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.contentLength(pdfBytes.length).body(new ByteArrayResource(pdfBytes));
	}

	@GetMapping("/descargarPDFAgentesDiplomas")
	public ResponseEntity<Resource> descargarPDFAgentesDiplomas() throws DocumentException, IOException {
		List<Policia> agentes = policiaService.obtenerAgentes();

		// Ordenar agentes por número de casos atendidos de forma descendente
		List<Policia> agentesOrdenados = agentes.stream()
				.sorted(Comparator.comparingInt(Policia::getNumeroCasosAtendidos).reversed())
				.collect(Collectors.toList());

		ByteArrayOutputStream zipOutput = new ByteArrayOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(zipOutput);

		for (int i = 0; i <= 2; i++) {
			Policia agente = agentesOrdenados.get(i);

			ByteArrayOutputStream pdfOutput = new ByteArrayOutputStream();
			Document document = new Document();

			try {
				PdfWriter.getInstance(document, pdfOutput);
				document.open();

				// Generar el contenido del diploma
				generarDiploma(document, agente, i + 1);

				document.close();

				// Agregar el archivo PDF al archivo ZIP
				zipOutputStream.putNextEntry(new ZipEntry("diploma_" + (i + 1) + ".pdf"));
				zipOutputStream.write(pdfOutput.toByteArray());
				zipOutputStream.closeEntry();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

		zipOutputStream.close();

		byte[] zipBytes = zipOutput.toByteArray();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=diplomas.zip");

		// Enviar el archivo ZIP como respuesta para descarga
		ResponseEntity<Resource> response = ResponseEntity.ok().headers(headers)
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(zipBytes.length)
				.body(new ByteArrayResource(zipBytes));

		return response;
	}

	private void generarDiploma(Document document, Policia agente, int numeroDiploma) throws DocumentException {
		Paragraph paragraph = new Paragraph("Nombre: " + agente.getNombre());
		document.add(paragraph);

		// Agregar más detalles o información al diploma según sea necesario
		// Puedes personalizar el contenido del diploma aquí

		document.add(new Paragraph("Diploma de " + obtenerTipoDiploma(numeroDiploma)));
		document.add(new Paragraph("-----------------------------------"));
	}

	private String obtenerTipoDiploma(int numeroDiploma) {
		switch (numeroDiploma) {
		case 1:
			return "oro";
		case 2:
			return "plata";
		case 3:
			return "bronce";
		default:
			return "Agra";
		}
	}
}
