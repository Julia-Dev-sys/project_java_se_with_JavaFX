package Controller;

import db.SQLite;
import entities.Produto;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class ProdutosController {

    @FXML
    TextField tfProdutoDescricao;
    @FXML
    TextField tfProdutoCusto;
    @FXML
    TextField tfProdutoPreco;

    public void clickSalvarProduto(Event e) throws SQLException, ClassNotFoundException {
        Alert alertSalvarProduto = new Alert(Alert.AlertType.INFORMATION,"");

        if(tfProdutoDescricao.getText().isEmpty()){
            alertSalvarProduto.setContentText("Preencha a descricao do produto!");
            tfProdutoDescricao.requestFocus();
        }else{
            if(tfProdutoCusto.getText().isEmpty()){
                alertSalvarProduto.setContentText("Preencha o custo do produto!");
                tfProdutoCusto.requestFocus();
            }else{
                if(tfProdutoPreco.getText().isEmpty()){
                    alertSalvarProduto.setContentText("Preencha o preco da mercadoria!");
                    tfProdutoPreco.requestFocus();
                }else{
                    Produto produtoCadastro = new Produto();
                    produtoCadastro.setDescricao(tfProdutoDescricao.getText());
                    produtoCadastro.setCusto(Double.valueOf(tfProdutoCusto.getText()));
                    produtoCadastro.setPreco(Double.valueOf(tfProdutoPreco.getText()));

                    SQLite dbsqlite = new SQLite();

                    if(dbsqlite.checkDescricao(produtoCadastro)){
                        alertSalvarProduto.setContentText("O produto" + produtoCadastro.getDescricao()+  "já existe");
                    }else{

                        dbsqlite.insertProduto(produtoCadastro);
                        alertSalvarProduto.setContentText("Produto salvo com sucesso!");
                        limparCampos();

                    }
                }
            }
        }
        alertSalvarProduto.show();
    }

    public void clickCancelarProduto(Event e){

        limparCampos();
    }

    public void limparCampos(){

        tfProdutoDescricao.setText("");
        tfProdutoCusto.setText("");
        tfProdutoPreco.setText("");
        tfProdutoDescricao.requestFocus();

    }
}
