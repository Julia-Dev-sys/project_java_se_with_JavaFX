package Controller;

import db.SQLite;
import entities.Cliente;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.SQLException;



public class ClientesController {

    @FXML
    TextField tfNomeCompleto;

    @FXML
    TextField tfcpf;

    @FXML
    TextField tftelefone;

    @FXML
    TextField tfnascimento;


    public void clickSalvarCliente(Event e) throws SQLException, ClassNotFoundException {

        Alert alertSalvarCliente = new Alert(Alert.AlertType.INFORMATION, "Clicou no salvar cliente");

        String nomecompleto = tfNomeCompleto.getText();

        if (nomecompleto.isEmpty()) {
            alertSalvarCliente.setContentText("Preencha o campo nome + sobrenome");
        } else {
            String[] nomesobrenome = nomecompleto.split(" ");
            int elementonomesobrenome = nomesobrenome.length;
            if (elementonomesobrenome == 1) {
                alertSalvarCliente.setContentText("Preencha seu sobrenome");
            } else {
                String cpf = tfcpf.getText();
                if (cpf == null) {
                    alertSalvarCliente.setContentText("Cpf incorreto, favor informar um cpf valido");
                } else {
                    cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
                    tfcpf.setText(cpf);
                }
                String telefone = tftelefone.getText();
                if (telefone.isEmpty()) {
                    alertSalvarCliente.setContentText("Preencha seu telefone");

                } else {
                    telefone = "(" + telefone.substring(0, 2) + ")"  + telefone.substring(2, 6) + "-" + telefone.substring(6, 10);
                    tftelefone.setText(telefone);
                }
                String nascimento = tfnascimento.getText();
                if (nascimento.isEmpty()) {
                    alertSalvarCliente.setContentText("Preencha a data de nascimento");
                } else {

                    Cliente cadcliente = new Cliente();
                    cadcliente.setNome(tfNomeCompleto.getText());
                    cadcliente.setCpf(tfcpf.getText());
                    cadcliente.setTel(tftelefone.getText());
                    cadcliente.setNascimento(tfnascimento.getText());

                    SQLite dbsqlite = new SQLite();

                    if (dbsqlite.checkCpf(cadcliente)) {
                        alertSalvarCliente.setContentText("O cpf " + cadcliente.getCpf() + " já existe");
                    } else {
                        dbsqlite.insertCliente(cadcliente);
                        alertSalvarCliente.setContentText("Cliente salvo com sucesso!");
                        tfNomeCompleto.setText("");
                        tfcpf.setText("");
                        tftelefone.setText("");
                        tfnascimento.setText("");

                    }
                }
            }
        }
        alertSalvarCliente.show();
    }
}










