package it.f2informatica.consulenteCurriculum.domain;

import java.io.Serializable;


public class Consulente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private double ral;
	private String titolo;
	private int anniDiEsperienza;
	private String seniority;
	private String skills;
	private Curriculum cv;
	
	public Consulente() {

	}

	public Consulente(
			String nome, String cognome, String codiceFiscale, double ral, 
			String titolo, String skills, Curriculum cv) 
	{
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.ral = ral;
		this.titolo = titolo;
		this.skills = skills;
		this.cv = cv;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public double getRal() {
		return ral;
	}

	public void setRal(double ral) {
		this.ral = ral;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public Curriculum getCv() {
		return cv;
	}

	public void setCv(Curriculum cv) {
		this.cv = cv;
	}

	public int getAnniDiEsperienza() {
		return anniDiEsperienza;
	}

	public void setAnniDiEsperienza(int anniDiEsperienza) {
		this.anniDiEsperienza = anniDiEsperienza;
	}

	public String getSeniority() {
		return seniority;
	}

	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}
}
