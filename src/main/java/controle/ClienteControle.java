/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import converter.ConverterGenerico;
import converter.EstadoConverter;
import entidades.Cliente;
import entidades.Cidade;
import facade.ClienteFacade;
import facade.CidadeFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jaimedias
 */
@ManagedBean
@SessionScoped
public class ClienteControle {

    @EJB
    private ClienteFacade clienteFacade;

    @EJB
    private CidadeFacade cidadeFacade;
    private Cliente cliente;
    private String tipoPessoa;

    private ConverterGenerico converterGenerico;

    public ConverterGenerico converter() {
        if (converterGenerico == null) {
            converterGenerico = new ConverterGenerico(cidadeFacade);
        }
        return converterGenerico;
    }

    public void novo() {
        cliente = new Cliente();
    }

    public List<Cidade> completeCidade(String query) {
        List<Cidade> cidades = cidadeFacade.listaFiltrando(query, "nome");
        return cidades;
    }

  public void salvar() {
    Cliente clienteExistente = clienteFacade.buscarPorCPF(cliente.getCpf());
    if (clienteExistente != null) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um cliente cadastrado com este CPF.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return;
    }

    clienteExistente = clienteFacade.buscarPorRG(cliente.getRg());
    if (clienteExistente != null) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um cliente cadastrado com este RG.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return;
    }

    clienteFacade.salvar(cliente);
    cliente = new Cliente();
}

    public void excluir(Cliente c) {
        clienteFacade.remover(c);
    }

    public void editar(Cliente cli) {
        this.cliente = cli;
    }

    public List<Cliente> getListagem() {
        return clienteFacade.listaTodos();
    }

    public List<Cidade> getListaCidades() {
        return cidadeFacade.listaTodos();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
