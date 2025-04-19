package com.finance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.finance.model.PessoaJuridica;

@Repository
public interface PessoaRepository  extends CrudRepository<PessoaJuridica, Long>{

}
