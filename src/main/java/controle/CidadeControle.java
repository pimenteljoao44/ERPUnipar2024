/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import converter.ConverterGenerico;
import converter.EstadoConverter;
import entidades.Cidade;
import entidades.Estado;
import facade.CidadeFacade;
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
public class CidadeControle implements Serializable{
    
    private Cidade cidade;
    @EJB
    private CidadeFacade cidadeFacade;
    @EJB
    private EstadoFacade estadoFacade;
    private ConverterGenerico estadoConverter;
    
    public List<Estado> getListaEstadosFiltrando(String filtro){
        return estadoFacade.listaFiltrando(filtro, "nome", "sigla");
    }

    public ConverterGenerico getEstadoConverter() {
        if(estadoConverter == null){
            estadoConverter = new ConverterGenerico(estadoFacade);
        }
        return estadoConverter;
    }

    public void setEstadoConverter(ConverterGenerico estadoConverter) {
        this.estadoConverter = estadoConverter;
    }
    
    public void novo(){
        cidade = new Cidade();
    }
    
    public void salvar(){
        cidadeFacade.salvar(cidade);
        cidade = new Cidade();
    }
    
    public void excluir(Cidade est){
        cidadeFacade.remover(est);
    }
    
    public void editar(Cidade est){
        this.cidade = est;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Cidade> getListaCidades() {
        return cidadeFacade.listaTodos();
    }    
    
}
