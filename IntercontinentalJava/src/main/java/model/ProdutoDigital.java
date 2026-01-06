package model;
public class ProdutoDigital extends Produto {
    private double tamanhoArquivoMB;
    public ProdutoDigital(int id, String nome, double preco, double tamanhoArquivoMB){
        super(id, nome, preco);
        this.tamanhoArquivoMB = tamanhoArquivoMB;
        this.tamanhoArquivoMB = tamanhoArquivoMB;
    }
    @Override
    public void exibirInfo(){
        super.exibirInfo();
        System.out.println("Tamanho do arquivo: " + tamanhoArquivoMB + " MB");
    }
}
