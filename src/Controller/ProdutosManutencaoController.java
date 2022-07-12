package Controller;

import db.SQLite;
import entities.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProdutosManutencaoController implements Initializable {

    @FXML
    private TableView<Produto> twproduto;
    @FXML
    private TableColumn<Produto,Integer> codigocolumn;
    @FXML
    private TableColumn<Produto,String> descricaocolumn;
    @FXML
    private TableColumn<Produto,Double> custocolumn;
    @FXML
    private TableColumn<Produto,Double> precocolumn;
    @FXML
    private TextField tfcodigo;
    @FXML
    private TextField tfdescricao;
    @FXML
    private TextField tfcusto;
    @FXML
    private TextField tfpreco;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codigocolumn.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        descricaocolumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        custocolumn.setCellValueFactory(new PropertyValueFactory<>("custo"));
        precocolumn.setCellValueFactory(new PropertyValueFactory<>("preco"));

        try {
            twproduto.setItems(listaTodosProdutos());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private ObservableList<Produto> listaTodosProdutos() throws SQLException, ClassNotFoundException {

        SQLite dbprodutos = new SQLite();

        return FXCollections.observableArrayList(dbprodutos.getAllProdutos());

    }

    public void atualizarTextFieldProduto(Event e){

        ObservableList produtoselecionado = twproduto.getSelectionModel().getSelectedItems();

        Produto produtoAtualizar = new Produto();
        produtoAtualizar.setCodigo(((Produto) produtoselecionado.get(0)).getCodigo());
        produtoAtualizar.setDescricao(((Produto) produtoselecionado.get(0)).getDescricao());
        produtoAtualizar.setCusto(((Produto) produtoselecionado.get(0)).getCusto());
        produtoAtualizar.setPreco(((Produto) produtoselecionado.get(0)).getPreco());

        tfcodigo.setText(produtoAtualizar.getCodigo().toString());
        tfdescricao.setText(produtoAtualizar.getDescricao());
        tfcusto.setText(produtoAtualizar.getCusto().toString());
        tfpreco.setText(produtoAtualizar.getPreco().toString());


    }


    public void excluirProduto(Event e ){

        ObservableList produtoselecionado = twproduto.getSelectionModel().getSelectedItems();

        Produto produtoEliminar = new Produto();
        produtoEliminar.setCodigo(((Produto) produtoselecionado.get(0)).getCodigo());
        produtoEliminar.setDescricao(((Produto) produtoselecionado.get(0)).getDescricao());
        produtoEliminar.setCusto(((Produto) produtoselecionado.get(0)).getCusto());
        produtoEliminar.setPreco(((Produto) produtoselecionado.get(0)).getPreco());

        try {

            SQLite dbExcluirProduto = new SQLite();
            dbExcluirProduto.eliminarProduto(produtoEliminar);

            twproduto.getItems().clear();
            twproduto.setItems(listaTodosProdutos());

            Alert alertEliminacao = new Alert(Alert.AlertType.INFORMATION,"Produto excluido com sucesso!");
            alertEliminacao.show();
            limparCampo();


        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void atualizarProduto(Event e){

        ObservableList<Produto> produtoselecionado = twproduto.getSelectionModel().getSelectedItems();


        String codigo = tfcodigo.getText();
        String descricao = tfdescricao.getText();
        String custo = tfcusto.getText();
        String preco = tfpreco.getText();

        Produto produtoAtualizar2 = new Produto();
        produtoAtualizar2.setCodigo(Integer.parseInt(codigo));
        produtoAtualizar2.setDescricao(descricao);
        produtoAtualizar2.setCusto(Double.parseDouble(custo));
        produtoAtualizar2.setPreco(Double.parseDouble(preco));

        try {
            SQLite dbAtualizaProduto = new SQLite();
            dbAtualizaProduto.atualizarProduto(produtoAtualizar2);

            twproduto.getItems().clear();
            twproduto.setItems(listaTodosProdutos());

            Alert alertAtualizar = new Alert(Alert.AlertType.INFORMATION,"Produto atualizado com sucesso!");
            alertAtualizar.show();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void limparCampo(){

        tfcodigo.clear();
        tfdescricao.clear();
        tfcusto.clear();
        tfpreco.clear();
    }


}
