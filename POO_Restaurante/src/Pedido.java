import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Random;

/**
 * Classe pedido: composição com "comida" e delegação para valores de taxa e
 * final. Implementa Comparable para prover ordenação.
 */
public class Pedido implements Comparable<Pedido> {
    private static Random sorteio = new Random(42); // somente para demonstração do Comparable
    private static final Data inicio = new Data(1, 1, 2023);

    private static int proximo_id = 1; // para id automático
    private int idPedido;
    private Data dataPedido;
    private ComparableList<Comida> itens;
    private IPedido categoriaPedido;

    /**
     * Apenas para encapsular a criação da lista de comidas
     */
    private void initLista() {
        this.itens = new ComparableList<>();
    }

    /**
     * Inicializador: recebe a comida para iniciar a lista e a categoria do Pedido
     * (delegação para a interface)
     *
     * @param primeira  Primeira comida para abrir o pedido
     * @param categoria Categoria do pedido, calcula taxa e valor final
     */
    private void init(Comida primeira, IPedido categoria) {
        this.idPedido = proximo_id;
        proximo_id++;
        this.dataPedido = inicio.acrescentaDias(sorteio.nextInt(120)); // somente para demonstração do Comparable;
        this.categoriaPedido = categoria;
        this.addComida(primeira);
    }

    /**
     * Construtor. Um pedido só pode ser iniciado se já houver uma comida para ele
     *
     * @param primeira Comida inicial do pedido
     * @throws IllegalArgumentException caso a comida seja nula
     */
    public Pedido(Comida primeira) throws IllegalArgumentException {
        if (primeira == null) {
            throw new IllegalArgumentException("A comida inicial do pedido não pode ser nula.");
        }
        initLista();
        init(primeira, new PedidoLocal(this.itens));
    }

    /**
     * Construtor. Um pedido só pode ser iniciado se já houver uma comida para ele
     *
     * @param primeira  Comida inicial do pedido
     * @param distancia Distância da entrega (somente para pedidos de entrega)
     * @throws IllegalArgumentException caso a comida seja nula
     */
    public Pedido(Comida primeira, double distancia) throws IllegalArgumentException {
        if (primeira == null) {
            throw new IllegalArgumentException("A comida inicial do pedido não pode ser nula.");
        }
        initLista();
        init(primeira, new PedidoDelivery(this.itens, distancia));
    }

    /**
     * Adiciona uma comida ao pedido.
     *
     * @param novoItem Comida a ser adicionada
     */
    public void addComida(Comida novoItem) {
        if (novoItem != null) {
            this.categoriaPedido.addComida(novoItem);
        }
    }

    /**
     * Remove uma comida do pedido, se não for a última.
     *
     * @param posicao Posição da comida a ser removida
     */
    public void cancelaItem(int posicao) {
        if (this.itens.size() > 1 && posicao >= 0 && posicao < this.itens.size()) {
            Comida saindo = this.itens.get(posicao);
            this.itens.remove(saindo);
        }
    }

    /**
     * Obtém uma comida do pedido na posição especificada.
     *
     * @param posicao Posição da comida a ser obtida
     * @return Comida na posição indicada (ou null se não houver)
     */
    public Comida getComida(int posicao) {
        if (posicao >= 0 && posicao < this.itens.size()) {
            return this.itens.get(posicao);
        }
        return null;
    }

    /**
     * Obtém o valor total dos itens do pedido.
     *
     * @return BigDecimal com o valor total dos itens
     */
    protected BigDecimal valorItens() {
        BigDecimal valor = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        for (Comida comida : itens) {
            valor = valor.add(comida.precoFinal());
        }
        return valor;
    }

    /**
     * Obtém o valor total do pedido, incluindo a taxa de serviço.
     *
     * @return Double com o valor total do pedido
     */
    public double valorTotal() {
        return valorItens().add(valorTaxa()).doubleValue();
    }

    /**
     * Gera o relatório do pedido.
     *
     * @return String com o detalhamento do pedido
     */
    @Override
    public String toString() {
        StringBuilder relat = new StringBuilder("");
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        this.itens.sort();
        relat.append("=====================\n");
        relat.append("Pedido nº " + this.idPedido + " - " + this.dataPedido + "\n");
        int cont = 1;
        for (Comida comida : this.itens) {
            relat.append(String.format("%02d", cont) + " - ");
            relat.append(comida.toString() + "\n");
            cont++;
        }
        relat.append("\nVALOR DOS ITENS:\t " + formatter.format(this.valorItens()));
        relat.append("\nVALOR DA TAXA:  \t " + formatter.format(this.valorTaxa()));
        relat.append("\nTOTAL DO PEDIDO:\t " + formatter.format(this.valorTotal()) + "\n");
        relat.append("=====================\n");
        return relat.toString();
    }

    /**
     * Obtém a quantidade total de itens no pedido.
     *
     * @return Inteiro com a quantidade de itens do pedido
     */
    public int quantidadeDeItens() {
        return this.itens.size();
    }

    /**
     * Obtém a taxa de serviço para pedidos no local.
     *
     * @return BigDecimal com a taxa de serviço atual
     */
    protected BigDecimal valorTaxa() {
        return categoriaPedido.valorTaxa();
    }

    /**
     * Implementação do comparador padrão do pedido: compara as datas dos pedidos.
     *
     * @param o Outro pedido para comparação
     * @return -1 se este pedido é anterior, 0 se são iguais, 1 se este pedido é
     *         posterior
     */
    @Override
    public int compareTo(Pedido o) {
        return this.dataPedido.compareTo(o.dataPedido);
    }

    public void aplicarDesconto(double descontoRegular) {
        this.categoriaPedido.aplicarDesconto(descontoRegular);
    }

    public Data getData() {
        return this.dataPedido;
    }

    public int getId() {
        return idPedido;
    }
}
