package db;

import entities.Cliente;
import entities.Produto;
import entities.Usuario;
import entities.VendaProduto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// CONEXÃO COM BANCO DE DADOS //

public class SQLite {

    private Connection conn;
    private Statement stm;

    public SQLite() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        this.conn = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
        this.stm = this.conn.createStatement();
    }

    // METODOS DE CHECAGENS //

    public boolean checkLogin(Usuario usuario){
        List<Usuario> listUsuarioLogin = new ArrayList<>();
        ResultSet resultUsuarioLogin;

        try{
            resultUsuarioLogin = this.stm.executeQuery("select * from usuario " +
                    "where email = '"+usuario.getEmail()+"'  and senha = '"+usuario.getSenha()+"'");

            while(resultUsuarioLogin.next()){
                resultUsuarioLogin.close();
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


        return false;
    }

    public boolean checkEmail(Usuario usuario){
        List<Usuario> listUsuarioLogin = new ArrayList<>();
        ResultSet resultUsuarioLogin;

        try{
            resultUsuarioLogin = this.stm.executeQuery("select * from usuario " +
                    "where email = '"+usuario.getEmail()+"'");

            while(resultUsuarioLogin.next()){
                resultUsuarioLogin.close();
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


        return false;
    }

    public boolean checkCpf(Cliente cliente){
        List<Cliente> listCliente = new ArrayList<>();
        ResultSet resultSetCliente;

        try {
            resultSetCliente = this.stm.executeQuery("select * from cliente " + "where cpf = '" +cliente.getCpf()+"'");

            while(resultSetCliente.next()){
                resultSetCliente.close();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean checkDescricao(Produto produto){
        List<Produto> listProduto = new ArrayList<>();
        ResultSet resultSetProduto;

        try {
            resultSetProduto = this.stm.executeQuery("select * from produto " + "where descricao = '" +produto.getDescricao()+"'");

            while(resultSetProduto.next()){
                resultSetProduto.close();
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    // METODOS DE INSERÇÃO NO BANCO DE DADOS //

    public void insertUsuario(Usuario cadastro){
        try {
            this.stm = this.conn.createStatement();
            String sqlInsertUsuario = "insert into usuario (nome, email, senha) " +
                    "values ('"+cadastro.getNome()+"', '"+cadastro.getEmail()+"', '"+cadastro.getSenha()+"')";
            this.stm.executeUpdate(sqlInsertUsuario);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void insertCliente(Cliente cadastro){

        try {
            this.stm = this.conn.createStatement();
            String sqlInsertCliente = "insert into cliente(nome,cpf,telefone,nascimento)" +
                    "values('"+cadastro.getNome()+"','"+cadastro.getCpf()+"','"+cadastro.getTel()+"','"+cadastro.getNascimento()+"')";
            this.stm.executeUpdate(sqlInsertCliente);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertProduto(Produto produto){
        try {
            this.stm = this.conn.createStatement();
            String sqlInsertProduto = "insert into produto" + "(descricao,custo,preco)"+
                                      "values" + "('"+produto.getDescricao()+"',"+produto.getCusto()+","+produto.getPreco()+")";
            this.stm.executeUpdate(sqlInsertProduto);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertVendaProduto(VendaProduto vendaProduto){
        try {
            this.stm = this.conn.createStatement();
            String sqlInsertVendaProduto = "insert into venda_produto" + "(codigo_produto,codigo_cliente,quantidade,unitario,total)"+
                    "values" + "('"+vendaProduto.getCodigo()+"','"+vendaProduto.getCodigo_cliente()+"','"+vendaProduto.getQuantidade()+"','"+vendaProduto.getUnitario()+"','"+vendaProduto.getTotal()+"')";
            this.stm.executeUpdate(sqlInsertVendaProduto);

            /*this.stm = this.conn.createStatement();
            String sqlInsertVenda = "insert into venda" + "(codigo_produto,codigo_cliente)" + "values" + "('"+vendaProduto.getCodigo()+"',"+vendaProduto.getCodigo_cliente()+")";
            this.stm.executeUpdate(sqlInsertVenda);*/

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // MÉTODO PARA RECUPERAR TODOS OS DADOS //

    public List<Usuario> getAllUsuarios(){
        List<Usuario> listUsuario = new ArrayList<>();
        ResultSet resultSetUsuario;

        try {
            String sql = "select nome,email from usuario order by nome";
            resultSetUsuario = this.stm.executeQuery(sql);

            while (resultSetUsuario.next()){
                Usuario usuarioCadastrado = new Usuario();
                usuarioCadastrado.setNome(resultSetUsuario.getString("nome"));
                usuarioCadastrado.setEmail(resultSetUsuario.getString("email"));
                listUsuario.add(usuarioCadastrado);
            }

        }catch (SQLException e ){
            e.printStackTrace();
        }
        return listUsuario;
    }

    public List<Produto> getAllProdutos(){
        List<Produto> listProduto = new ArrayList<>();
        ResultSet resultSetProduto;

        try {
            String sql = "select * from produto order by codigo";
            resultSetProduto = this.stm.executeQuery(sql);

            while(resultSetProduto.next()){
                Produto produtoCadastrado = new Produto();
                produtoCadastrado.setCodigo(resultSetProduto.getInt("codigo"));
                produtoCadastrado.setDescricao(resultSetProduto.getString("descricao"));
                produtoCadastrado.setCusto(resultSetProduto.getDouble("custo"));
                produtoCadastrado.setPreco(resultSetProduto.getDouble("preco"));
                listProduto.add(produtoCadastrado);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listProduto;
    }

    public List<Cliente> getAllClientes(){
        List<Cliente> listCliente = new ArrayList<>();
        ResultSet resultSetCliente;

        try {
            String sql = "select * from cliente order by nome";
            resultSetCliente = this.stm.executeQuery(sql);

            while (resultSetCliente.next()){
                Cliente clienteCadastrado = new Cliente();
                clienteCadastrado.setNome(resultSetCliente.getString("nome"));
                clienteCadastrado.setCpf(resultSetCliente.getString("cpf"));
                clienteCadastrado.setTel(resultSetCliente.getString("telefone"));
                clienteCadastrado.setNascimento(resultSetCliente.getString("nascimento"));
                listCliente.add(clienteCadastrado);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listCliente;
    }

    public List<VendaProduto> getAllVendaProduto(){
        List<VendaProduto> listVendaProduto = new ArrayList<>();
        ResultSet resultSetVendaProduto;
        try {

            String sql = "select * from venda_produto";
            resultSetVendaProduto = this.stm.executeQuery(sql);

            while (resultSetVendaProduto.next()){
                VendaProduto vendaCadastrada = new VendaProduto();
                vendaCadastrada.setCodigo(resultSetVendaProduto.getInt("codigo_produto"));
                vendaCadastrada.setCodigo_cliente(resultSetVendaProduto.getInt("codigo_cliente"));
                vendaCadastrada.setQuantidade(resultSetVendaProduto.getInt("quantidade"));
                vendaCadastrada.setUnitario(resultSetVendaProduto.getDouble("unitario"));
                vendaCadastrada.setTotal(resultSetVendaProduto.getDouble("total"));
                listVendaProduto.add(vendaCadastrada);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listVendaProduto;
    }

    // MÉTODOS PARA ELIMINAR DADOS //

    public void eliminarUsuario(Usuario usuario){

        try {
            this.stm = this.conn.createStatement();
            String excluir = "delete from usuario where email = '"+usuario.getEmail()+"'";
            this.stm.executeUpdate(excluir);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void eliminarProduto(Produto produto){

        try {
            this.stm = this.conn.createStatement();
            String excluir = "delete from produto where codigo = '"+produto.getCodigo()+"'";
            this.stm.executeUpdate(excluir);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void eliminarCliente(Cliente cliente){

        try {
            this.stm = this.conn.createStatement();
            String excluir = "delete from cliente where cpf = '"+cliente.getCpf()+"'";
            this.stm.executeUpdate(excluir);

            this.stm = this.conn.createStatement();
            String insertClienteExcluido = "insert into cliente_excluido (nome,cpf,telefone,nascimento) values ('"+cliente.getNome()+"','"+cliente.getCpf()+"','"+cliente.getTel()+"','"+cliente.getNascimento()+"')";
            this.stm.executeUpdate(insertClienteExcluido);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // MÉTODOS PARA ATUALIZAR DADOS ///

    public void atualizarUsuario(Usuario usuario, String emailantigo){
        try {
            this.stm = this.conn.createStatement();
            String update = "update usuario " +
                    "        set nome  = '"+usuario.getNome()+"', " +
                    "            email = '"+usuario.getEmail()+"' " +
                    "        where email = '"+emailantigo+"'";
            this.stm.executeUpdate(update);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void atualizarProduto(Produto produto){
        try {
            this.stm = this.conn.createStatement();
            String update = "update produto " + "set descricao = '"+produto.getDescricao()+"',"
                    + "custo = '"+produto.getCusto()+"'," +
                    " preco = '"+produto.getPreco()+"'" + " where codigo = '"+ produto.getCodigo()+"'";
            this.stm.executeUpdate(update);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void atualizarCliente(Cliente cliente){
        try {
            this.stm = this.conn.createStatement();
            String update = "update cliente " +
                    " set nome = '"+cliente.getNome()+"'," +
                    " cpf = '"+cliente.getCpf()+"'," +
                    " telefone = '"+cliente.getTel()+"'," +
                    " nascimento = '"+cliente.getNascimento()+"'" +
                    "where cpf ='"+cliente.getCpf()+"'";
            this.stm.executeUpdate(update);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /////////// MÉTODOS PARA PROCURAR NO BANCO DE DADOS ////

    public Cliente getClienteporCpf(Integer codigo) throws SQLException {
        Cliente clientecadastrado = new Cliente();
        ResultSet resultSetCliente;

        try {
            String sql = "select codigo,nome,cpf,telefone,nascimento from cliente where codigo = "+codigo+"";
            resultSetCliente = this.stm.executeQuery(sql);
            while (resultSetCliente.next()){
                clientecadastrado.setNome(resultSetCliente.getString("nome"));
                clientecadastrado.setCpf(resultSetCliente.getString("cpf"));
                clientecadastrado.setTel(resultSetCliente.getString("telefone"));
                clientecadastrado.setNascimento(resultSetCliente.getString("nascimento"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return clientecadastrado;
    }

    public Produto getProdutoForCodigo(Integer codigo){
        Produto produtoCadastrado = new Produto();
        ResultSet resultSetProduto;

        try {
            String sql = "select codigo,descricao,custo,preco from produto where codigo = "+codigo+"";
            resultSetProduto = this.stm.executeQuery(sql);

            while(resultSetProduto.next()){
                produtoCadastrado.setDescricao(resultSetProduto.getString("descricao"));
                produtoCadastrado.setCusto(resultSetProduto.getDouble("custo"));
                produtoCadastrado.setPreco(resultSetProduto.getDouble("preco"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return produtoCadastrado;
    }
}
