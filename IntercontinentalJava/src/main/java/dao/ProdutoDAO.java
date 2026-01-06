package dao;
import java.sql.*;
import java.util.*;
import db.ConexaoBD;
import model.Produto;

public class ProdutoDAO {
    public void criarTabela(){
        String sql = "CREATE TABLE IF NOT EXISTS produto(" +
                     "id INTEGER PRIMARY KEY, nome TEXT, preco REAL, tamanho_mb REAL, peso REAL)";
        try (Connection c = ConexaoBD.conectar(); Statement st = c.createStatement()){
            st.execute(sql);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void inserir(Produto p){
        String sql = "INSERT INTO produto(id,nome,preco,tamanho_mb,peso) VALUES(?,?,?,?,?)";
        try (Connection c = ConexaoBD.conectar(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, p.getId());
            ps.setString(2, p.getNome());
            ps.setDouble(3, p.getPreco());

            try {
                java.lang.reflect.Field f = p.getClass().getDeclaredField("tamanhoArquivoMB");
                f.setAccessible(true);
                ps.setObject(4, f.getDouble(p));
            } catch (Exception ex) { ps.setObject(4, null); }
            try {
                java.lang.reflect.Field f2 = p.getClass().getDeclaredField("peso");
                f2.setAccessible(true);
                ps.setObject(5, f2.getDouble(p));
            } catch (Exception ex) { ps.setObject(5, null); }
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Produto> listarTodos(){
        List<Produto> lista = new ArrayList<>();
        try (Connection c = ConexaoBD.conectar(); Statement st = c.createStatement()){
            ResultSet rs = st.executeQuery("SELECT * FROM produto");
            while (rs.next()){
                lista.add(new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public Produto buscarPorId(int id){
        try (Connection c = ConexaoBD.conectar();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM produto WHERE id = ?")){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void salvar(Produto p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
