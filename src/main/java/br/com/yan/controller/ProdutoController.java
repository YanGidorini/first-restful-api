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
import br.com.yan.domain.Produto;
import br.com.yan.repository.MaterialRepository;
import br.com.yan.repository.ProdutoRepository;

@RestController
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository dao;

    @Autowired
    private MaterialRepository materialDao;

    @GetMapping
    public List<Produto> buscaTodos(){
        List<Produto> produtos = dao.findAll();
        for (Produto p : produtos) {
            p.getCusto();
            p.getLucro();
        }
        return produtos;
    }

    @GetMapping("{id}")
    public Produto buscaProduto(@PathVariable int id){
        Produto p = null;
        try {
            p = dao.findById(id).get();
            p.getCusto();
            p.getLucro();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found");
        }        
        return p;
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Produto cadastraProduto(@RequestBody Produto produto){
        Produto p = dao.save(findMateriais(produto));
        p.getCusto();
        p.getLucro();
        return p;
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("varios")
    public List<Produto> cadastraVariosProdutos(@RequestBody List<Produto> produtos){
        for(Produto p : produtos){
            int index = produtos.indexOf(p);
            produtos.set(index, cadastraProduto(p));
        }
        return produtos;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("{id}")
    public Produto atualizaProduto(@RequestBody Produto produto, @PathVariable int id){
        produto.setId(id);
        Produto p = dao.save(produto);
        p.getCusto();
        p.getLucro();
        return p;
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void excluiProduto(@RequestBody Produto produto) {
        dao.delete(produto);
    }

    public Produto findMateriais(Produto produto){
        List<Material> materiais = produto.getMateriais();

        for(Material m : materiais){
            int index = materiais.indexOf(m);
            m = materialDao.findById(m.getId()).get();
            materiais.set(index, m);
        }

        produto.setMateriais(materiais);
        return produto;
    }

}
