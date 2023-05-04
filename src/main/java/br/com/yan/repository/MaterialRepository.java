package br.com.yan.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.yan.domain.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    
}
