package it.f2informatica.consulenteCurriculum.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.f2informatica.consulenteCurriculum.domain.Consulente;
import it.f2informatica.consulenteCurriculum.service.ConsulenteService;


@RestController
@RequestMapping("/consulente/")
public class ConsulenteController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "list/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Consulente>> listAllRecords() {
		List<Consulente> list = ConsulenteService.getAllConsultants();	
		if (list.isEmpty()) {
			return new ResponseEntity<List<Consulente>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Consulente>>(list, HttpStatus.OK);
	}

	
	@RequestMapping(method = RequestMethod.POST, value = "insert/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> insertConsultant(@RequestBody Consulente consulente) {
		logger.debug("Get the Object: " + consulente.toString());

		boolean result = ConsulenteService.upsertConsultant(consulente);
		if (result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "delete/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteConsultant(@RequestBody Consulente consulente) {
		logger.debug("Get the Object: " + consulente.toString());

		boolean result = ConsulenteService.deleteConsultant(consulente.getCodiceFiscale());				
		if (result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "update/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateConsultant(@RequestBody Consulente consulente) {
		logger.debug("Get the Object: " + consulente.toString());

		boolean result = ConsulenteService.upsertConsultant(consulente);				
		if (result) {
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "getConsulente/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Consulente> readConsultant(@RequestParam String codiceFiscale) {
		Consulente consulente = ConsulenteService.findConsulente(codiceFiscale);
		if (consulente == null) {
			return new ResponseEntity<Consulente>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Consulente>(consulente, HttpStatus.OK);
	}
}
