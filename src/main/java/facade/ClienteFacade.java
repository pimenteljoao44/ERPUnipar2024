/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.Cliente;
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
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "ERPUnipar2024PU")
    private EntityManager em;

    public Cliente buscarPorRG(String rg) {
        try {
            Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.rg = :rg");
            query.setParameter("rg", rg);
            return (Cliente) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cliente buscarPorCPF(String cpf) {
        try {
            Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf");
            query.setParameter("cpf", cpf);
            return (Cliente) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }

}
