package br.com.yan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yan.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
    
}
