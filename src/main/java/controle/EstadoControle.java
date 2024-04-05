/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import entidades.Estado;
import facade.EstadoFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jaimedias
 */
@ManagedBean
@SessionScoped
public class EstadoControle implements Serializable{
    
    private Estado estado;
    @EJB
    private EstadoFacade estadoFacade;
    
    public void novo(){
        estado = new Estado();
    }
    
    public void salvar(){
        estadoFacade.salvar(estado);
        estado = new Estado();
    }
    
    public void excluir(Estado est){
        estadoFacade.remover(est);
    }
    
    public void editar(Estado est){
        this.estado = est;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Estado> getListaEstados() {
        return estadoFacade.listaTodos();
    }    
    
}
