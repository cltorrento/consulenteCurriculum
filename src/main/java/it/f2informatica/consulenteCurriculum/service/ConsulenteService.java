package it.f2informatica.consulenteCurriculum.service;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.i;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.LegacyDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.AsyncN1qlQueryRow;
import com.google.gson.Gson;

import it.f2informatica.consulenteCurriculum.database.DBManager;
import it.f2informatica.consulenteCurriculum.domain.Consulente;
import rx.Observable;

@Service
public class ConsulenteService {
	
	private final static Logger logger = LoggerFactory.getLogger(ConsulenteService.class);
	static ArrayList<Consulente> list;
	
	public static List<Consulente> getAllConsultants() {
		logger.debug("Get the Consultants List");
		AsyncBucket bucket = DBManager.getBucket().async();
		executeQuery(bucket);
		return list;
	}
	
	public static boolean upsertConsultant(Consulente consulente) {
		boolean result = false;
		logger.debug("insert the consultant in the documento" + consulente.toString());
		Gson gson = new Gson();
		Bucket bucket = DBManager.getBucket();
		LegacyDocument document = LegacyDocument.create(consulente.getCodiceFiscale(), gson.toJson(consulente));
		Object obj = bucket.upsert(document);
		if (obj != null) {
			logger.debug("Docuement Inserted Succesfully");
			result = true;
		} else {
			logger.debug("Error to Insert the document, ConsulenteService.java: ln:45");
			result = false;
		}
		return result;
	}
	
	public static boolean deleteConsultant(String codiceFiscale) {
		boolean result = false;
		logger.debug("Delete from bucket the document with CodiceFiscale: " + codiceFiscale);
		
		Bucket bucket = DBManager.getBucket();
		Object obj = bucket.remove(codiceFiscale);
		if (obj != null) {
			logger.debug("Document Deleted.");
			result = true;
		} else {
			logger.debug("Error to delete the document");
			result = false;
		}
		return result;
	}
	
	public static Consulente findConsulente(String codiceFiscale) {
		logger.debug("Search Consultant with codice Fiscale= " + codiceFiscale);
		Consulente consulente = null;
		Bucket bucket =DBManager.getBucket();
		Gson gson = new Gson();
		LegacyDocument legacy = bucket.get(codiceFiscale, LegacyDocument.class);
		consulente = gson.fromJson((String) legacy.content(),Consulente.class);
		return consulente;
	}
	
	private static void executeQuery(AsyncBucket bucket) {
		list = new ArrayList<Consulente>();
		logger.debug("Execute the script...");
		bucket
		.query(
				select("*")
				.from(i("test"))
				.where("codiceFiscale is not null"))
		.flatMap(result -> result.errors()
				.flatMap(
				e -> Observable.<AsyncN1qlQueryRow>error(new CouchbaseException("N1QL Error/Warning: " + e)))
				.switchIfEmpty(result.rows()))
		.map(AsyncN1qlQueryRow::value).toBlocking()
		.subscribe(rowContent -> addConsultantToList(rowContent), runtimeError -> runtimeError.printStackTrace());
	}

	private static void addConsultantToList(JsonObject obj) {
		logger.debug("Read: "+obj.getObject("test").toString());
		Consulente l = new Consulente();
		Gson gson = new Gson();
		String json = obj.getObject("test").toString();
		l = gson.fromJson(json, Consulente.class);
		list.add(l);
	}
}
