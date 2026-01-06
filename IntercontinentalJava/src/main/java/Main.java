import java.util.Scanner;

import dao.PedidoDAO;
import dao.ProdutoDAO;
import model.Pedido;
import model.Produto;
import service.ProcessadorDePedidos;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ProdutoDAO pdao = new ProdutoDAO();
        PedidoDAO pedDAO = new PedidoDAO();

        pdao.criarTabela();
        pedDAO.criarTabelas();

        Pedido pedido = null; 

        OUTER:
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Cadastrar produto");
            System.out.println("2. Listar produtos");
            System.out.println("3. Criar pedido");
            System.out.println("4. Processar pedido (thread)");
            System.out.println("5. Salvar pedido no banco");
            System.out.println("6. Listar pedidos salvos");
            System.out.println("7. Sair");

            int op = sc.nextInt();

            switch (op) {

                case 1 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    System.out.print("Nome: ");
                    String nome = sc.next();
                    System.out.print("Preço: ");
                    double preco = sc.nextDouble();
                    pdao.inserir(new Produto(id, nome, preco));
                }

                case 2 -> {
                    pdao.listarTodos().forEach(p -> p.exibirInfo());
                }

                case 3 -> {
                    pedido = new Pedido(); 
                    System.out.println("Quantos produtos adicionar?");
                    int qtd = sc.nextInt();

                    for (int i = 0; i < qtd; i++) {
                        System.out.print("ID do produto: ");
                        int id = sc.nextInt();
                        Produto prod = pdao.buscarPorId(id);
                        if (prod != null) pedido.adicionarProduto(prod);
                    }
                    System.out.println("Pedido criado!");
                }

                case 4 -> {
                    if (pedido == null) {
                        System.out.println("Crie um pedido primeiro!");
                        break;
                    }
                    Thread t = new Thread(new ProcessadorDePedidos(pedido));
                    t.start();
                }

                case 5 -> {
                    if (pedido == null) {
                        System.out.println("Crie um pedido primeiro!");
                        break;
                    }
                    pedDAO.salvar(pedido);
                    System.out.println("Pedido salvo!");
                }

                case 6 -> {
                    pedDAO.listarPedidos().forEach(System.out::println);
                }

                default -> {
                    break OUTER;
                }
            }
        }
    }
}
