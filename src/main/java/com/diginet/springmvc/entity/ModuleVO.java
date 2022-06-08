package com.diginet.springmvc.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "modulo")
public class ModuleVO{

	private long idModulo;
	private SistemaVO sistema;
	private String nome;
	private String descricao;
	private byte status;
	private Date dataAtualizacao;
	private Date dateLicense;
	private Date dateSolicitation;
	private String licenseKey;
	private String requisitionHash;
	private Set<FuncionalidadeVO> funcionalidades = new HashSet<FuncionalidadeVO>(0);

	@Id
	@Column(name = "id_modulo", unique = true, nullable = false)
	public long getIdModulo() {
		return this.idModulo;
	}

	public void setIdModulo(long idModulo) {
		this.idModulo = idModulo;
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


	@Temporal(TemporalType.DATE)
	@Column(name = "date_license", length = 10)
	public Date getDateLicense() {
		return this.dateLicense;
	}

	public void setDateLicense(Date dateLicense) {
		this.dateLicense = dateLicense;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_solicitation", length = 10)
	public Date getDateSolicitation() {
		return this.dateSolicitation;
	}

	public void setDateSolicitation(Date dateSolicitation) {
		this.dateSolicitation = dateSolicitation;
	}

	@Column(name = "license_key")
	public String getLicenseKey() {
		return this.licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	@Column(name = "requisition_hash")
	public String getRequisitionHash() {
		return this.requisitionHash;
	}

	public void setRequisitionHash(String requisitionHash) {
		this.requisitionHash = requisitionHash;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modulo")
	public Set<FuncionalidadeVO> getFuncionalidades() {
		return this.funcionalidades;
	}

	public void setFuncionalidades(Set<FuncionalidadeVO> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

}
