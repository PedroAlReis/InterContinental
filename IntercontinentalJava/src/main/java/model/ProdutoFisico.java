package model;
public class ProdutoFisico extends Produto {
    private double peso;
    public ProdutoFisico(int id, String nome, double preco, double peso){
        super(id, nome, preco);
        this.peso = peso;
    }
    @Override
    public void exibirInfo(){
        super.exibirInfo();
        System.out.println("Peso: " + peso + " kg");
    }
}
