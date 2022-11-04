package com.we3.common.rest.ws;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.me.logger.MLogger;
import com.me.vo.genericos.app.response.BuscarMusicaResponse;
import com.me.vo.genericos.app.response.CadastroMusicaResponse;
import com.me.vo.genericos.app.response.RemoverMusicaResponse;
import com.me.vo.genericos.app.vo.MusicasVO;
import com.me.vo.genericos.requests.BuscarMusicaRequest;
import com.me.vo.genericos.requests.CadastroMusicaRequest;
import com.me.vo.genericos.requests.RemoverMusicaRequest;
import com.we3.common.entity.Musicas;

@Stateless
@LocalBean
@Path("/musicas")
public class MusicasEJB {

	private static MLogger log = MLogger.getLogger(MusicasEJB.class);

	// @PersistenceContext(unitName = "exemplo-pu")
	@PersistenceContext(unitName = "minhaEscolaCommons")
	private EntityManager em;

	public MusicasEJB() {
	}

	@POST
	@Path("/cadastromusicas")
	@Produces("application/json")
	@Consumes("application/json")
	public CadastroMusicaResponse cadastrarMusicas(CadastroMusicaRequest request) {

		CadastroMusicaResponse response = new CadastroMusicaResponse();

		if (request.getNumSeq() != null) {

			// edicao

			Musicas musicajasalva = em.find(Musicas.class, request.getNumSeq());

			musicajasalva.setNumSeq(request.getNumSeq());
			musicajasalva.setDesNome(request.getDesNome());
			musicajasalva.setDesBanda(request.getDesBanda());
			musicajasalva.setIndNota(request.getIndNota());
			musicajasalva.setIndAno(request.getIndAno());
			musicajasalva.setDesAlbum(request.getDesAlbum());

			em.merge(musicajasalva);

		} else {
			// insercao

			Musicas musicanova = new Musicas();
			musicanova.setDesNome(request.getDesNome());
			musicanova.setDesBanda(request.getDesBanda());
			musicanova.setIndNota(request.getIndNota());
			musicanova.setIndAno(request.getIndAno());
			musicanova.setDesAlbum(request.getDesAlbum());

			em.persist(musicanova);

		}
		response.setMensagemInformativa(request.getDesNome() + " Cadastrado");
		return response;
	}

	@POST
	@Path("/removermusicas")
	@Produces("application/json")
	@Consumes("application/json")
	public RemoverMusicaResponse deletar(RemoverMusicaRequest request) {

		Integer numMusica = request.getNumMusica();

		Musicas musicaremovida = em.find(Musicas.class, numMusica);

		em.remove(musicaremovida);

		RemoverMusicaResponse response = new RemoverMusicaResponse();
		response.setMensagemInformativa("Foi removido!!");
		return response;
	}

	@POST
	@Path("/listarmusicas")
	@Produces("application/json")
	@Consumes("application/json")
	public BuscarMusicaResponse listar(BuscarMusicaRequest request) {

		String sql = "SELECT num_seq, des_nome, des_banda, ind_nota, des_album, num_ano FROM musicas WHERE 1=1 ";

		BuscarMusicaResponse response = new BuscarMusicaResponse();

		if (request.getNome() != null) {
			sql = sql + " AND upper(des_nome) like :desNome";
		}

		if (request.getBanda() != null) {
			sql = sql + " AND upper(des_banda) like :desBanda";
		}

		if (request.getAlbum() != null) {
			sql = sql + " AND upper(des_album) like :desAlbum";
		}

		// aqui vc fez certo
		// if (request.getAno1() != null && request.getAno2() != null) {
		// sql = sql + " AND num_ano BETWEEN :numAno1 AND :numAno2";
		// }

		// mas faz assim:
		if (request.getAno1() != null) {
			sql = sql + " AND num_ano >= :numAno1";
		}

		if (request.getAno2() != null) {
			sql = sql + " AND num_ano <= :numAno2";
		}

		sql = sql + " ORDER BY des_nome";

		Query query = em.createNativeQuery(sql);

		if (request.getNome() != null) {
			query.setParameter("desNome", "%" + request.getNome().toUpperCase().trim() + "%");
		}
		if (request.getBanda() != null) {
			query.setParameter("desBanda", "%" + request.getBanda().toUpperCase().trim() + "%");
		}
		if (request.getAlbum() != null) {
			query.setParameter("desAlbum", "%" + request.getAlbum().toUpperCase().trim() + "%");
		}

		if (request.getAno1() != null) {
			query.setParameter("numAno1", request.getAno1());
		}

		if (request.getAno2() != null) {
			query.setParameter("numAno2", request.getAno2());
		}

		List resultados = query.getResultList();

		if (resultados != null) {

			for (Object linha : resultados) {

				Object[] aux = (Object[]) linha;

				MusicasVO musicaEncontrada = new MusicasVO();

				musicaEncontrada.setNumSeq(Integer.valueOf(aux[0].toString()));
				musicaEncontrada.setDesNome(aux[1].toString());
				musicaEncontrada.setDesBanda(aux[2].toString());
				musicaEncontrada.setIndNota(Integer.valueOf(aux[3].toString()));
				if (aux[4] != null) {
					musicaEncontrada.setDesAlbum(aux[4].toString());
				}
				if (aux[5] != null) {
					musicaEncontrada.setIndAno(Integer.valueOf(aux[5].toString()));
				}

				response.getMusicas().add(musicaEncontrada);
			}

		}
		return response;
	}

}
