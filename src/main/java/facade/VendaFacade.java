/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facade;

import entidades.ItemVenda;
import entidades.Produto;
import entidades.Venda;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author joao
 */
@Stateless
public class VendaFacade extends AbstractFacade<Venda> {

    @PersistenceContext(unitName = "ERPUnipar2024PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VendaFacade() {
        super(Venda.class);
    }

@Override
    public void  salvar(Venda entity){
        for(ItemVenda it : entity.getItensVendas()){
            Produto p = it.getProduto();
            p.setEstoque(p.getEstoque() - it.getQuantidade().intValue());
            em.merge(p);
        }
    }

}