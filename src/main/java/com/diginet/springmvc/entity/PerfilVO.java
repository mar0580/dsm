package com.diginet.springmvc.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "perfil")
public class PerfilVO{

	private Long idPerfil;
	private SistemaVO sistema;
	private String nome;
	private String descricao;
	private byte status;
	private Date dataAtualizacao;
	private Set<GrupoVO> grupos = new HashSet<GrupoVO>(0);
	private Set<FuncionalidadeVO> funcionalidades = new HashSet<FuncionalidadeVO>(0);
	private Set<UsuarioVO> usuarios = new HashSet<UsuarioVO>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_perfil", unique = true, nullable = false)
	public Long getIdPerfil() {
		return this.idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_sistema", nullable = false)
	public SistemaVO getSistema() {
		return this.sistema;
	}

	public void setSistema(SistemaVO sistema) {
		this.sistema = sistema;
	}

	@Column(name = "nome", nullable = false, length = 50)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "descricao", nullable = false)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_atualizacao", nullable = false, length = 19)
	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "perfils")
	public Set<GrupoVO> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(Set<GrupoVO> grupos) {
		this.grupos = grupos;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "perfil_funcionalidade", catalog = "diginet", joinColumns = {
			@JoinColumn(name = "id_perfil", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_funcionalidade", nullable = false, updatable = false) })
	public Set<FuncionalidadeVO> getFuncionalidades() {
		return this.funcionalidades;
	}

	public void setFuncionalidades(Set<FuncionalidadeVO> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "perfils")
	public Set<UsuarioVO> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<UsuarioVO> usuarios) {
		this.usuarios = usuarios;
	}

}
