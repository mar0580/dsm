package com.diginet.springmvc.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "funcionalidade")
public class FuncionalidadeVO{

	private long idFuncionalidade;
	private ModuleVO modulo;
	private String nome;
	private String descricao;
	private Byte status;
	private Date dataAtualizacao;
	private Set<UsuarioVO> usuarios = new HashSet<UsuarioVO>(0);
	private Set<PerfilVO> perfils = new HashSet<PerfilVO>(0);

	@Id
	@Column(name = "id_funcionalidade", unique = true, nullable = false)
	public long getIdFuncionalidade() {
		return this.idFuncionalidade;
	}

	public void setIdFuncionalidade(long idFuncionalidade) {
		this.idFuncionalidade = idFuncionalidade;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_modulo", nullable = false)
	public ModuleVO getModulo() {
		return this.modulo;
	}

	public void setModulo(ModuleVO modulo) {
		this.modulo = modulo;
	}

	@Column(name = "nome", length = 50)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "status")
	public Byte getStatus() {
		return this.status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_atualizacao", length = 19)
	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "funcionalidades")
	public Set<UsuarioVO> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<UsuarioVO> usuarios) {
		this.usuarios = usuarios;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "funcionalidades")
	public Set<PerfilVO> getPerfils() {
		return this.perfils;
	}

	public void setPerfils(Set<PerfilVO> perfils) {
		this.perfils = perfils;
	}

}
