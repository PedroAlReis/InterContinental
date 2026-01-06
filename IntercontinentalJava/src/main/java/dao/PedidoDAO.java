package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConexaoBD;
import model.Pedido;
import model.Produto;

public class PedidoDAO {

    public void criarTabelas(){
        String pedido = "CREATE TABLE IF NOT EXISTS pedido(id INTEGER PRIMARY KEY AUTOINCREMENT, valor_total REAL)";
        String interm = "CREATE TABLE IF NOT EXISTS pedido_produto(id_pedido INTEGER, id_produto INTEGER)";
        try (Connection c = ConexaoBD.conectar(); Statement st = c.createStatement()){
            st.execute(pedido);
            st.execute(interm);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void salvar(Pedido p) throws Exception {
        double total = p.calcularValorTotal();
        String sql = "INSERT INTO pedido(valor_total) VALUES(?)";
        try (Connection c = ConexaoBD.conectar(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setDouble(1, total);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) p.setId(rs.getInt(1));

            for (Produto prod : p.getProdutos()){
                PreparedStatement ps2 = c.prepareStatement("INSERT INTO pedido_produto(id_pedido,id_produto) VALUES(?,?)");
                ps2.setInt(1, p.getId());
                ps2.setInt(2, prod.getId());
                ps2.executeUpdate();
            }
        }
    }

    public List<String> listarPedidos(){
        List<String> lista = new ArrayList<>();
        try (Connection c = ConexaoBD.conectar(); Statement st = c.createStatement()){
            ResultSet rs = st.executeQuery("SELECT * FROM pedido");
            while (rs.next()){
                lista.add("Pedido " + rs.getInt("id") + " | Total: " + rs.getDouble("valor_total"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }
}
