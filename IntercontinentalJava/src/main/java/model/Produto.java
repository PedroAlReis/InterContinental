package model;
public class Produto {
    private int id;
    private String nome;
    private double preco;
    // campos opcionais para persistência
    public double tamanhoArquivoMB = 0;
    public double peso = 0;

    public Produto() {}
    public Produto(int id, String nome, double preco) {
        this.id = id; this.nome = nome; this.preco = preco;
    }
    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }
    public String getNome(){ return nome; }
    public void setNome(String n){ this.nome = n; }
    public double getPreco(){ return preco; }
    public void setPreco(double p){ this.preco = p; }

    public void exibirInfo(){
        System.out.println("ID:" + id + " Nome:" + nome + " Preço:" + preco);
    }
}
