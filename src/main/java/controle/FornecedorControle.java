/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import converter.ConverterGenerico;

import entidades.Cidade;
import entidades.Fornecedor;
import facade.CidadeFacade;
import facade.FornecedorFacade;
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
public class FornecedorControle {

    @EJB
    private FornecedorFacade fornecedorFacade;

    @EJB
    private CidadeFacade cidadeFacade;
    private Fornecedor fornecedor;
    private String tipoPessoa;

    private ConverterGenerico converterGenerico;

    public ConverterGenerico converter() {
        if (converterGenerico == null) {
            converterGenerico = new ConverterGenerico(cidadeFacade);
        }
        return converterGenerico;
    }

    public void novo() {
        fornecedor = new Fornecedor();
    }

    public List<Cidade> completeCidade(String query) {
        List<Cidade> cidades = cidadeFacade.listaFiltrando(query, "nome");
        return cidades;
    }

    public void salvar() {
        Fornecedor fornecedorExistente = fornecedorFacade.buscarPorCPF(fornecedor.getCpf());
        if (fornecedorExistente != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um fornecedor cadastrado com este CPF.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }

        fornecedorExistente = fornecedorFacade.buscarPorRG(fornecedor.getRg());
        if (fornecedorExistente != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe um fornecedor cadastrado com este RG.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return;
        }

        fornecedorFacade.salvar(fornecedor);
        fornecedor = new Fornecedor();
    }

    public void excluir(Fornecedor f) {
        fornecedorFacade.remover(f);
    }

    public void editar(Fornecedor f) {
        this.fornecedor = f;
    }

    public List<Fornecedor> getListagem() {
        return fornecedorFacade.listaTodos();
    }

    public List<Cidade> getListaCidades() {
        return cidadeFacade.listaTodos();
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }
    
    
}
