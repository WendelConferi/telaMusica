package com.we3.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MUSICAS")
public class Musicas implements Serializable {

	private static final long serialVersionUID = 7449150780648800573L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NUM_SEQ", nullable = false)
	private Integer numSeq;

	@Column(name = "DES_NOME", nullable = false)
	private String desNome;

	@Column(name = "DES_BANDA", nullable = false)
	private String desBanda;

	@Column(name = "IND_NOTA", nullable = false)
	private Integer indNota;

	@Column(name = "NUM_ANO", nullable = false)
	private Integer indAno;

	@Column(name = "DES_ALBUM", nullable = false)
	private String desAlbum;

	public Integer getNumSeq() {
		return numSeq;
	}

	public void setNumSeq(Integer numSeq) {
		this.numSeq = numSeq;
	}

	public String getDesNome() {
		return desNome;
	}

	public void setDesNome(String desNome) {
		this.desNome = desNome;
	}

	public String getDesBanda() {
		return desBanda;
	}

	public void setDesBanda(String desBanda) {
		this.desBanda = desBanda;
	}

	public Integer getIndNota() {
		return indNota;
	}

	public void setIndNota(Integer indNota) {
		this.indNota = indNota;
	}

	public Integer getIndAno() {
		return indAno;
	}

	public void setIndAno(Integer indAno) {
		this.indAno = indAno;
	}

	public String getDesAlbum() {
		return desAlbum;
	}

	public void setDesAlbum(String desAlbum) {
		this.desAlbum = desAlbum;
	}

}