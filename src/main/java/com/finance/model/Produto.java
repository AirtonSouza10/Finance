package com.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "produto")
@AllArgsConstructor
@Data
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cupom_desconto")
	private Long id;

	private String tipoUnidade;
	private String nome;
	@Column(columnDefinition = "text", length = 2000)
	private String descricao;

	// TODO acrescentar item nota produto associar

	private Double peso;
	private Double lagura;
	private Double altura;
	private Double profundidade;

	private BigDecimal valorVenda;
	private Integer qtdEstoque;
	private Integer qtdeAlertaEstoque;
	private String linkYoutube;
	private Boolean alertaQtdeEstoque;
	private Integer qtdeClique;
	
	private Boolean ativo;
}
