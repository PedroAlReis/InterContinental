package service;
import model.Pedido;
import model.Produto;

public class ProcessadorDePedidos implements Runnable {
    private Pedido pedido;
    public ProcessadorDePedidos(Pedido pedido){ this.pedido = pedido; }
    @Override
    public void run(){
        System.out.println("Iniciando processamento do pedido " + pedido.getId());
        int i = 1;
        for (Produto p : pedido.getProdutos()){
            System.out.println("Processando item " + i + ": " + p.getNome());
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            i++;
        }
        System.out.println("Pedido finalizado!");
    }
}
