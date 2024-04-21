package com.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "cupom_desconto")
@AllArgsConstructor
@Data
@SequenceGenerator(name = "seq_cupom_desconto", sequenceName = "seq_cupom_desconto", allocationSize = 1, initialValue = 1)
public class CupomDesconto implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cupom_desconto")
	private Long id;
	
	private String codDescricao;
	private BigDecimal valorRealDesc;
	private BigDecimal valorPercentDesc;
	@Temporal(TemporalType.DATE)
	private Date dtValidadeCupom;
}
