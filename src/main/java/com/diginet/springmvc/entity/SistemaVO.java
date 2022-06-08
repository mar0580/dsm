package com.diginet.springmvc.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sistema")
public class SistemaVO{

	private long idSistema;
	private String nome;
	private String descricao;
	private byte status;
	private Date dataAtualizacao;
	private Set<GrupoVO> grupos = new HashSet<GrupoVO>(0);
	private Set<PerfilVO> perfils = new HashSet<PerfilVO>(0);
	private Set<ModuleVO> modulos = new HashSet<ModuleVO>(0);

	@Id
	@Column(name = "id_sistema", unique = true, nullable = false)
	public long getIdSistema() {
		return this.idSistema;
	}

	public void setIdSistema(long idSistema) {
		this.idSistema = idSistema;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sistema")
	public Set<GrupoVO> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(Set<GrupoVO> grupos) {
		this.grupos = grupos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sistema")
	public Set<PerfilVO> getPerfils() {
		return this.perfils;
	}

	public void setPerfils(Set<PerfilVO> perfils) {
		this.perfils = perfils;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sistema")
	public Set<ModuleVO> getModulos() {
		return this.modulos;
	}

	public void setModulos(Set<ModuleVO> modulos) {
		this.modulos = modulos;
	}

}
