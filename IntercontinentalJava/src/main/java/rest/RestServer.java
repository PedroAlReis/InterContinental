package rest;

import com.google.gson.Gson;

import dao.PedidoDAO;
import dao.ProdutoDAO;
import model.Pedido;
import model.Produto;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class RestServer {

    public static void main(String[] args) {

        port(4567);
        Gson gson = new Gson();
        
        ProdutoDAO produtoDAO = new ProdutoDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        // cria tabelas se não existirem
        produtoDAO.criarTabela();
        pedidoDAO.criarTabelas();

        // ============================
        // SERVIR ARQUIVOS ESTÁTICOS
        // ============================
        // aponta para src/main/resources
        staticFiles.externalLocation("src/main/resources");

        // redireciona a rota principal para o index.html
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });

        // ============================
        // LIBERAR CORS
        // ============================
        options("/*", (req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type");
            return "OK";
        });
        before((req, res) -> res.header("Access-Control-Allow-Origin", "*"));

        // ============================
        // ROTAS PRODUTOS
        // ============================
        get("/produtos", (req, res) -> {
            res.type("application/json");
            return gson.toJson(produtoDAO.listarTodos());
        });

        post("/produto", (req, res) -> {
            res.type("application/json");
            Produto p = gson.fromJson(req.body(), Produto.class);
            produtoDAO.salvar(p);
            return gson.toJson(p);
        });

        // ============================
        // ROTAS PEDIDOS
        // ============================
        post("/pedido", (req, res) -> {
            res.type("application/json");

            Pedido ped = gson.fromJson(req.body(), Pedido.class);
            Pedido novo = new Pedido();

            for (Produto p : ped.getProdutos()) {
                Produto prod = produtoDAO.buscarPorId(p.getId());
                if (prod != null) novo.adicionarProduto(prod);
            }

            pedidoDAO.salvar(novo);

            return gson.toJson("Pedido criado! ID: " + novo.getId());
        });

        get("/pedidos", (req, res) -> {
            res.type("application/json");
            return gson.toJson(pedidoDAO.listarPedidos());
        });

        System.out.println("Servidor rodando em http://localhost:4567/");
    }
}
