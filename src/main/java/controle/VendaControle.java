/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import converter.ConverterGenerico;
import entidades.Cliente;
import entidades.ItemVenda;
import entidades.Produto;
import entidades.Venda;
import facade.ClienteFacade;
import facade.ProdutoFacade;
import facade.VendaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author joao
 */
@ManagedBean
@SessionScoped
public class VendaControle implements Serializable {

    private Venda venda;
    private ItemVenda itemVenda;
    @EJB
    private VendaFacade vendaFacade;
    @EJB
    private ProdutoFacade produtoFacade;
    @EJB
    private ClienteFacade clienteFacade;
    private ConverterGenerico clienteConverter;
    private ConverterGenerico produtoconverter;

    public VendaControle() {
        this.itemVenda = new ItemVenda();
    }

    public List<Cliente> getListaClientesFiltrando(String filtro) {
        return clienteFacade.listaFiltrando(filtro, "nome", "cpf");
    }

    public List<Produto> getListaProdutosFiltrando(String filtro) {
        return produtoFacade.listaFiltrando(filtro, "nome");
    }

    public ConverterGenerico getClienteConverter() {
        if (clienteConverter == null) {
            clienteConverter = new ConverterGenerico(clienteFacade);
        }
        return clienteConverter;
    }

    public ConverterGenerico getProdutoConverter() {
        if (produtoconverter == null) {
            produtoconverter = new ConverterGenerico(produtoFacade);
        }
        return produtoconverter;
    }

    public void setClienteConverter(ConverterGenerico clienteConverter) {
        this.clienteConverter = clienteConverter;
    }

    public void atualizarTotal() {
        if (venda != null && venda.getItensVendas() != null) {
            double total = 0.0;
            for (ItemVenda item : venda.getItensVendas()) {
                total += item.getSubtotal();
            }
            venda.setTotal(total);
        }
    }

    public void novo() {
        venda = new Venda();
    }

    public void salvar() {
        atualizarTotal();
        vendaFacade.salvar(venda);
        venda = new Venda();
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().
                handleNavigation(FacesContext.getCurrentInstance(),
                        null, "vendalista.xhtml?faces-redirect=true");
    }

    public void excluir(Venda est) {
        vendaFacade.remover(est);
    }

    public void editar(Venda est) {
        this.venda = est;
    }

    public void adicionarItem() {
        Integer estoque = itemVenda.getProduto().getEstoque();
        ItemVenda itemTemp = null;
        for (ItemVenda it : venda.getItensVendas()) {
            if (it.getProduto().getId().equals(itemVenda.getProduto().getId())) {
                itemTemp = it;
                estoque = estoque - it.getQuantidade().intValue();
            }
        }

        if (venda.getItensVendas() == null) {
            venda.setItensVendas(new ArrayList<ItemVenda>());
        } else if (estoque < itemVenda.getQuantidade()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Estoque insuficiente! restam apenas " + estoque + " unidades!", null));
        } else {
            if (itemTemp == null) {
                itemVenda.setVenda(venda);
                venda.getItensVendas().add(itemVenda);
            } else {
                itemTemp.setQuantidade(itemTemp.getQuantidade() + itemVenda.getQuantidade());
            }
            itemVenda = new ItemVenda();
        }
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public ItemVenda getItemVenda() {
        return itemVenda;
    }

    public void setItemVenda(ItemVenda itemVenda) {
        this.itemVenda = itemVenda;
    }

    public List<Venda> getListaVendas() {
        return vendaFacade.listaTodos();
    }

}
