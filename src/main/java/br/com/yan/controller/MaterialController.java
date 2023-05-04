package br.com.yan.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.yan.domain.Material;
import br.com.yan.repository.MaterialRepository;


@RestController
@RequestMapping("material")
public class MaterialController {
    
    @Autowired
    private MaterialRepository dao;

    @GetMapping
    public List<Material> buscaTodos() {
        return dao.findAll();
    }

    @GetMapping("{id}")
    public Material buscaMaterial(@PathVariable int id) {
        Material m = null;
        try {
            m = dao.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Material Not Found");
        }        
        return m;
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Material cadastraMaterial(@RequestBody Material material){
        return dao.save(material);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("varios")
    public List<Material> cadastraVariosMateriais(@RequestBody List<Material> materiais){
        return dao.saveAll(materiais);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("{id}")
    public Material atualizaMaterial(@RequestBody Material material, @PathVariable int id){
        material.setId(id);
        return dao.save(material);
    }

    @DeleteMapping
    public void excluiMaterial(@RequestBody Material material){
        dao.delete(material);
    }
}
