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
@Table(name = "grupo")
public class GrupoVO {

	private Long idGrupo;
	private SistemaVO sistema;
	private String nome;
	private String descricao;
	private String status;
	private Date dataAtualizacao;
	private Set<UsuarioVO> usuarios = new HashSet<UsuarioVO>(0);
	private Set<PerfilVO> perfils = new HashSet<PerfilVO>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_grupo", unique = true, nullable = false)
	public Long getIdGrupo() {
		return this.idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
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

	@Column(name = "status", nullable = false, length = 45)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "grupo_usuario", catalog = "diginet", joinColumns = {
			@JoinColumn(name = "id_grupo", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_usuario", nullable = false, updatable = false) })
	public Set<UsuarioVO> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<UsuarioVO> usuarios) {
		this.usuarios = usuarios;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "grupo_perfil", catalog = "diginet", joinColumns = {
			@JoinColumn(name = "id_grupo", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_perfil", nullable = false, updatable = false) })
	public Set<PerfilVO> getPerfils() {
		return this.perfils;
	}

	public void setPerfils(Set<PerfilVO> perfils) {
		this.perfils = perfils;
	}

}
