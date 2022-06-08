package com.diginet.springmvc.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class UsuarioVO{

	private Long idUsuario;
	private String nome;
	private String email;
	private String cpf;
	private String login;
	private String senha;
	private byte status;
	private Date dataCriacao;
	private Date dataExpiraSenha;
	private Date inicioJornada;
	private Date fimJornada;
	private short diasSemanaPermissao;
	private Date dataAtualizacao;
	private Set<GrupoVO> grupos = new HashSet<GrupoVO>(0);
	private Set<PerfilVO> perfils = new HashSet<PerfilVO>(0);
	private Set<FuncionalidadeVO> funcionalidades = new HashSet<FuncionalidadeVO>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_usuario", unique = true, nullable = false)
	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "nome", nullable = false, length = 50)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "cpf", nullable = false, length = 14)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "login", unique = true, nullable = false, length = 20)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "senha", nullable = false, length = 40)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false, length = 19)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_expira_senha", length = 19)
	public Date getDataExpiraSenha() {
		return this.dataExpiraSenha;
	}

	public void setDataExpiraSenha(Date dataExpiraSenha) {
		this.dataExpiraSenha = dataExpiraSenha;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "inicio_jornada", nullable = false, length = 19)
	public Date getInicioJornada() {
		return this.inicioJornada;
	}

	public void setInicioJornada(Date inicioJornada) {
		this.inicioJornada = inicioJornada;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fim_jornada", nullable = false, length = 19)
	public Date getFimJornada() {
		return this.fimJornada;
	}

	public void setFimJornada(Date fimJornada) {
		this.fimJornada = fimJornada;
	}

	@Column(name = "dias_semana_permissao", nullable = false)
	public short getDiasSemanaPermissao() {
		return this.diasSemanaPermissao;
	}

	public void setDiasSemanaPermissao(short diasSemanaPermissao) {
		this.diasSemanaPermissao = diasSemanaPermissao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_atualizacao", nullable = false, length = 19)
	public Date getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "usuarios")
	public Set<GrupoVO> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(Set<GrupoVO> grupos) {
		this.grupos = grupos;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_perfil", catalog = "diginet", joinColumns = {
			@JoinColumn(name = "id_usuario", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_perfil", nullable = false, updatable = false) })
	public Set<PerfilVO> getPerfils() {
		return this.perfils;
	}

	public void setPerfils(Set<PerfilVO> perfils) {
		this.perfils = perfils;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_funcionalidade", catalog = "diginet", joinColumns = {
			@JoinColumn(name = "id_usuario", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id_funcionalidade", nullable = false, updatable = false) })
	public Set<FuncionalidadeVO> getFuncionalidades() {
		return this.funcionalidades;
	}

	public void setFuncionalidades(Set<FuncionalidadeVO> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

}
