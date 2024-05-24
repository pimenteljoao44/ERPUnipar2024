/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Cliente;
import entidades.Fornecedor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jaimedias
 */
@Stateless
public class FornecedorFacade extends AbstractFacade<Fornecedor> {

    @PersistenceContext(unitName = "ERPUnipar2024PU")
    private EntityManager em;

    public Fornecedor buscarPorRG(String rg) {
        try {
            Query query = em.createQuery("SELECT f FROM Fornecedor f WHERE f.rg = :rg");
            query.setParameter("rg", rg);
            return (Fornecedor) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Fornecedor buscarPorCPF(String cpf) {
        try {
            Query query = em.createQuery("SELECT f FROM Fornecedor f WHERE f.cpf = :cpf");
            query.setParameter("cpf", cpf);
            return (Fornecedor) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FornecedorFacade() {
        super(Fornecedor.class);
    }

}
