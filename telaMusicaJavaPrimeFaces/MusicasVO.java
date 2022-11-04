package com.me.vo.genericos.app.vo;

public class MusicasVO {

	private Integer numSeq;
	private String desNome;
	private String desBanda;
	private Integer indNota;
	private String desAlbum;
	private Integer indAno;

	public String getDesAlbum() {
		return desAlbum;
	}

	public void setDesAlbum(String desAlbum) {
		this.desAlbum = desAlbum;
	}

	public Integer getIndAno() {
		return indAno;
	}

	public void setIndAno(Integer indAno) {
		this.indAno = indAno;
	}

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

}
