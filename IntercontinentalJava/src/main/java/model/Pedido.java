package model;
import java.util.*;
public class Pedido {
    private int id;
    private List<Produto> produtos = new ArrayList<>();
    public Pedido() {}
    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }
    public void adicionarProduto(Produto p){ produtos.add(p); }
    public List<Produto> getProdutos(){ return produtos; }
    public double calcularValorTotal() throws ListaVaziaException {
        if (produtos.isEmpty()) throw new ListaVaziaException("Pedido vazio");
        double t = 0;
        for (Produto p : produtos) t += p.getPreco();
        return t;
    }
    public void exibirResumo(){
        System.out.println("=== Resumo Pedido ===");
        for (Produto p : produtos) p.exibirInfo();
    }
}
