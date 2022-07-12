package Controller;

import db.SQLite;
import entities.Cliente;
import entities.Produto;
import entities.VendaProduto;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class VendasController {

    @FXML
    private TextField tfcliente;
    @FXML
    private Label lbclienteselecionado;
    @FXML
    private TextField tfproduto;
    @FXML
    private Label lbprodutoselecionado;
    @FXML
    private TableView twProdutoVenda;
    @FXML
    private TableColumn<VendaProduto,Integer>tccodigo;
    @FXML
    private TableColumn<VendaProduto,Integer>tccodigocliente;
    @FXML
    private TableColumn<VendaProduto,String>tcdescricao;
    @FXML
    private TableColumn<VendaProduto,Integer>tcquantidade;
    @FXML
    private TableColumn<VendaProduto,Double>tcunitario;
    @FXML
    private TableColumn<VendaProduto,Double>tctotal;



    public void clickProcurarCliente(Event e) throws SQLException, ClassNotFoundException {
        Alert alertProcurarCliente = new Alert(Alert.AlertType.INFORMATION,"");

        Integer codigo = parseInt(tfcliente.getText());

        SQLite db = new SQLite();
        Cliente cliente = db.getClienteporCpf(codigo);

        if(cliente.getNome() == null){

            lbclienteselecionado.setText("...");
            tfcliente.setText("");
            tfcliente.requestFocus();
            alertProcurarCliente.setContentText("Cliente não encontrado!");
            alertProcurarCliente.show();

        }else {
            lbclienteselecionado.setText(cliente.getNome());
            tfproduto.requestFocus();
        }
    }

    public void clickProcurarProduto(Event e) throws SQLException, ClassNotFoundException {
        Alert alertProcurarProduto = new Alert(Alert.AlertType.INFORMATION,"");

        Integer codigo = parseInt(tfproduto.getText());
        SQLite db = new SQLite();
        Produto produto = db.getProdutoForCodigo(codigo);

        if(produto.getDescricao().isEmpty()){
            lbprodutoselecionado.setText("...");
            tfproduto.requestFocus();
            tfproduto.setText("");
            alertProcurarProduto.setContentText("Produto não encontrado!");
            alertProcurarProduto.show();
        }else{
            lbprodutoselecionado.setText(produto.getDescricao());
        }



    }

    public void clickIncluirProduto(Event e) throws SQLException, ClassNotFoundException {
        Alert alertIncluirProduto = new Alert(Alert.AlertType.INFORMATION,"");

        if(lbprodutoselecionado.getText().equals("...")){

            alertIncluirProduto.setContentText("Selecione um produto antes de incluir a venda!");
            alertIncluirProduto.show();
            tfproduto.requestFocus();

        }else {
            VendaProduto vendaProduto = new VendaProduto();
            vendaProduto.setCodigo_cliente(Integer.valueOf(tfcliente.getText()));
            vendaProduto.setCodigo(Integer.valueOf(tfproduto.getText()));
            vendaProduto.setDescricao(lbprodutoselecionado.getText());

            TextInputDialog tiQuantidade = new TextInputDialog("1");
            tiQuantidade.setHeaderText("Informe a quantidade desejada!");
            tiQuantidade.showAndWait();
            vendaProduto.setQuantidade(valueOf(tiQuantidade.getEditor().getText()));

            SQLite db = new SQLite();
            Produto produtoSelecionado = db.getProdutoForCodigo(valueOf(tfproduto.getText()));
            vendaProduto.setUnitario(produtoSelecionado.getPreco());

            vendaProduto.setTotal(vendaProduto.calcularTotal());
            tccodigo.setCellValueFactory(new PropertyValueFactory<VendaProduto,Integer>("codigo"));
            tccodigocliente.setCellValueFactory(new PropertyValueFactory<VendaProduto,Integer>("codigo_cliente"));
            tcdescricao.setCellValueFactory(new PropertyValueFactory<VendaProduto,String>("descricao"));
            tcquantidade.setCellValueFactory(new PropertyValueFactory<VendaProduto,Integer>("quantidade"));
            tcunitario.setCellValueFactory(new PropertyValueFactory<VendaProduto,Double>("unitario"));
            tctotal.setCellValueFactory(new PropertyValueFactory<VendaProduto,Double>("total"));

            twProdutoVenda.getItems().add(vendaProduto);
            twProdutoVenda.refresh();

            lbprodutoselecionado.setText("...");
            tfproduto.clear();
            tfproduto.requestFocus();

        }
    }

    public void clickRemoverProduto(Event e){

        twProdutoVenda.getItems().remove(twProdutoVenda.getSelectionModel().getSelectedItem());
        twProdutoVenda.refresh();
    }

    public void clickCancelarVenda(Event e){
        twProdutoVenda.getItems().clear();
        tfproduto.clear();
        tfcliente.clear();
        lbprodutoselecionado.setText("...");
        lbclienteselecionado.setText("...");
        tfcliente.requestFocus();
    }

    public void clickRegistrarVenda(Event e) throws SQLException, ClassNotFoundException {

        ObservableList list = twProdutoVenda.getItems();

        VendaProduto vendaProduto = new VendaProduto();
        vendaProduto.setCodigo(((VendaProduto) list.get(0)).getCodigo());
        vendaProduto.setCodigo_cliente(((VendaProduto) list.get(0)).getCodigo_cliente());
        vendaProduto.setDescricao(((VendaProduto) list.get(0)).getDescricao());
        vendaProduto.setQuantidade(((VendaProduto) list.get(0)).getQuantidade());
        vendaProduto.setUnitario(((VendaProduto) list.get(0)).getUnitario());
        vendaProduto.setTotal(((VendaProduto) list.get(0)).getTotal());

        SQLite dbvendaproduto = new SQLite();
        dbvendaproduto.insertVendaProduto(vendaProduto);

        twProdutoVenda.getItems().clear();
        lbclienteselecionado.setText("...");
        tfcliente.clear();
        tfcliente.requestFocus();

        Alert alertregistrar = new Alert(Alert.AlertType.INFORMATION,"Registro feito com sucesso!");
        alertregistrar.show();



    }

}



