package com.we3.web.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;
import com.me.vo.genericos.app.response.BuscarMusicaResponse;
import com.me.vo.genericos.app.vo.MusicasVO;
import com.me.vo.genericos.requests.BuscarMusicaRequest;
import com.me.vo.genericos.requests.RemoverMusicaRequest;
import com.mm.view.backing.AbstractBacking;
import com.we3.web.utils.PerfilGlobal;
import com.we3.web.utils.UtilWs;
import com.we3.web.utils.UtilsAddMensagem;

@ManagedBean
@ViewScoped
public class MusicasBacking extends AbstractBacking implements Serializable {

	private static final long serialVersionUID = -3993025663774002613L;

	private List<MusicasVO> musicasParaSerMostradoNaTela;

	String buscaNome;
	String buscaBanda;
	String buscaAlbum;
	Integer buscaAno1;
	Integer buscaAno2;

	public String getBuscaAlbum() {
		return buscaAlbum;
	}

	public void setBuscaAlbum(String buscaAlbum) {
		this.buscaAlbum = buscaAlbum;
	}

	public Integer getBuscaAno1() {
		return buscaAno1;
	}

	public void setBuscaAno1(Integer buscaAno1) {
		this.buscaAno1 = buscaAno1;
	}

	public Integer getBuscaAno2() {
		return buscaAno2;
	}

	public void setBuscaAno2(Integer buscaAno2) {
		this.buscaAno2 = buscaAno2;
	}

	public String getBuscaNome() {
		return buscaNome;
	}

	public void setBuscaNome(String buscaNome) {
		this.buscaNome = buscaNome;
	}

	public String getBuscaBanda() {
		return buscaBanda;
	}

	public void setBuscaBanda(String buscaBanda) {
		this.buscaBanda = buscaBanda;
	}

	@PostConstruct
	public void init() {
		try {
			actionBuscarNome();
		} catch (Exception e) {
			e.printStackTrace();
			UtilsAddMensagem.addMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar musicas.", null);
		}
	}

	public List<MusicasVO> getMusicasParaSerMostradoNaTela() {
		return musicasParaSerMostradoNaTela;
	}

	public void setMusicasParaSerMostradoNaTela(List<MusicasVO> musicasParaSerMostradoNaTela) {
		this.musicasParaSerMostradoNaTela = musicasParaSerMostradoNaTela;
	}

	public void actionForm() {
		try {
			PerfilGlobal.actionNavigate("/formMusicas");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void actionFormEdicao(MusicasVO musicaSelecionado) {
		try {
			PerfilGlobal.getSessao().setAttribute("musicaXX", musicaSelecionado);
			PerfilGlobal.actionNavigate("/formMusicas");
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}

	public void actionRemover(MusicasVO musicaRemovida) {

		try {
			// monta a request
			RemoverMusicaRequest request = new RemoverMusicaRequest();
			request.setNumMusica(musicaRemovida.getNumSeq());

			// converte para json
			String jsonParaEnvio = new Gson().toJson(request);
			System.out.println(jsonParaEnvio);

			// chamar o servidor
			String jsonRetornadoDoServidor = UtilWs
					.postRequest("http://localhost:8080/TesteWS/rest/musicas/removermusicas", jsonParaEnvio);

			// recarregar a lista

			for (int i = 0; i < musicasParaSerMostradoNaTela.size(); i++) {
				if (musicasParaSerMostradoNaTela.get(i).getNumSeq().equals(musicaRemovida.getNumSeq())) {

					musicasParaSerMostradoNaTela.remove(i);
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("erro");
		}

	}

	public void actionBuscarNome() throws Exception {

		BuscarMusicaRequest request = new BuscarMusicaRequest();
		request.setNome(buscaNome);
		request.setBanda(buscaBanda);
		request.setAlbum(buscaAlbum);
		request.setAno1(buscaAno1);
		request.setAno2(buscaAno2);

		String jsonParaEnvio = new Gson().toJson(request);

		String jsonRetornadoDoServidor = UtilWs.postRequest("http://localhost:8080/TesteWS/rest/musicas/listarmusicas",
				jsonParaEnvio);

		BuscarMusicaResponse response = new Gson().fromJson(jsonRetornadoDoServidor, BuscarMusicaResponse.class);
		musicasParaSerMostradoNaTela = response.getMusicas();

		System.out.println(jsonRetornadoDoServidor);
	}

	public void limparSearch() {
		buscaNome = null;
		buscaBanda = null;
		buscaAlbum = null;
		buscaAno1 = null;
		buscaAno2 = null;
	}

}
