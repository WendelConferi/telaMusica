package com.we3.web.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;
import com.me.vo.genericos.app.vo.MusicasVO;
import com.me.vo.genericos.requests.CadastroMusicaRequest;
import com.mm.view.backing.AbstractBacking;
import com.we3.web.utils.PerfilGlobal;
import com.we3.web.utils.UtilWs;

@ManagedBean
@ViewScoped
public class MusicasFormBacking extends AbstractBacking implements Serializable {

	private Integer numSeq;
	private String desNome;
	private String desBanda;
	private Integer indNota;
	private String desAlbum;
	private Integer indAno;

	@PostConstruct
	public void init() {

		// se tem alguma coisa na variavel timeXX da sessao
		if (PerfilGlobal.getSessao().getAttribute("musicaXX") != null) {

			MusicasVO musicaSelecionadoNaTelaDEAntes = (MusicasVO) PerfilGlobal.getSessao().getAttribute("musicaXX");
			numSeq = musicaSelecionadoNaTelaDEAntes.getNumSeq();
			desNome = musicaSelecionadoNaTelaDEAntes.getDesNome();
			desBanda = musicaSelecionadoNaTelaDEAntes.getDesBanda();
			indNota = musicaSelecionadoNaTelaDEAntes.getIndNota();
			desAlbum = musicaSelecionadoNaTelaDEAntes.getDesAlbum();
			indAno = musicaSelecionadoNaTelaDEAntes.getIndAno();

			PerfilGlobal.getSessao().setAttribute("musicaXX", null);
		}

	}

	public void actionSalvar() {

		try {
			// montar o objeto request
			CadastroMusicaRequest request = new CadastroMusicaRequest();
			request.setDesNome(desNome);
			request.setDesBanda(desBanda);
			request.setIndNota(indNota);
			request.setNumSeq(numSeq);
			request.setDesAlbum(desAlbum);
			request.setIndAno(indAno);

			// converter objeto para Json
			String jsonParEnviar = new Gson().toJson(request);
			System.out.println(jsonParEnviar);

			// enviar para o servidor
			String jsonRetornadoDoServidor = UtilWs
					.postRequest("http://localhost:8080/TesteWS/rest/musicas/cadastromusicas", jsonParEnviar);

			// chamar a tela de listagem
			PerfilGlobal.actionNavigate("/listaMusicas");

		} catch (Exception e) {
			System.out.println("erro");
		}

	}

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
