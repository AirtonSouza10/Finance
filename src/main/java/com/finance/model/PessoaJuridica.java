package com.finance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
@Data
@NoArgsConstructor
public class PessoaJuridica extends Pessoa{

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String cnpj;
	private String inscricaoEstadual;
	private String inscricaoMunicipal;
	private String nomeFantasia;
	@Column(nullable = false)
	private String razaoSocial;
	private String categoria;

}
