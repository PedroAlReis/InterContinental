package ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.ProdutoDAO;
import dao.PedidoDAO;
import model.Produto;
import model.Pedido;

public class LojaGUI {
    private JFrame frame;
    private JTable table;
    private ProdutoDAO pdao = new ProdutoDAO();
    private Pedido pedidoAtual = new Pedido();

    public LojaGUI() {
        pdao.criarTabela();
        PedidoDAO pedDAO = new PedidoDAO();
        pedDAO.criarTabelas();

        frame = new JFrame("Loja Virtual - GUI");
        frame.setSize(700,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new Object[] {"ID","Nome","Preço"}, 0);
        table = new JTable(model);
        refreshTable();

        JPanel top = new JPanel();
        JButton btnRefresh = new JButton("Atualizar");
        JButton btnAdd = new JButton("Adicionar ao pedido");
        JButton btnVerPedido = new JButton("Ver pedido");
        JButton btnSalvarPedido = new JButton("Salvar pedido");

        top.add(btnRefresh); top.add(btnAdd); top.add(btnVerPedido); top.add(btnSalvarPedido);
        frame.add(top, java.awt.BorderLayout.NORTH);
        frame.add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

        btnRefresh.addActionListener(e -> refreshTable());
        btnAdd.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row==-1) { JOptionPane.showMessageDialog(frame, "Selecione um produto"); return; }
            int id = Integer.parseInt(table.getValueAt(row,0).toString());
            Produto p = pdao.buscarPorId(id);
            if(p!=null) {
                pedidoAtual.adicionarProduto(p);
                JOptionPane.showMessageDialog(frame, "Adicionado: "+p.getNome());
            }
        });

        btnVerPedido.addActionListener(e -> {
            pedidoAtual.exibirResumo();
            try {
                double t = pedidoAtual.calcularValorTotal();
                JOptionPane.showMessageDialog(frame, "Total do pedido: R$ "+t);
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, "Pedido vazio");
            }
        });

        btnSalvarPedido.addActionListener(e -> {
            try {
                PedidoDAO pdao2 = new PedidoDAO();
                pdao2.salvar(pedidoAtual);
                JOptionPane.showMessageDialog(frame, "Pedido salvo com ID: "+pedidoAtual.getId());
                pedidoAtual = new Pedido();
                refreshTable();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao salvar: "+ex.getMessage());
            }
        });

        frame.setVisible(true);
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        for(Produto p : pdao.listarTodos()) {
            model.addRow(new Object[] {p.getId(), p.getNome(), p.getPreco()});
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new LojaGUI());
    }
}
