import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Cliente {
    public final String CPF;
    public String nome;
    private Stack<Pedido> pedidos;
    private double totalGasto;
    private int totalPedidos;
    private int pedidosMesAtual;
    private int pedidosUltimos30Dias;
    private int pedidosUltimos60Dias;

    // Constantes para os critérios de cada tipo de cliente
    private static final double LIMITE_GASTO_XULAMBS_JUNIOR = 130.0;
    private static final int LIMITE_PEDIDOS_XULAMBS_JUNIOR = 10;
    private static final double LIMITE_GASTO_XULAMBS_SENIOR = 350.0;
    private static final int LIMITE_PEDIDOS_XULAMBS_SENIOR = 30;
    private static final int LIMITE_PEDIDOS_ADICIONAIS_XULAMBS_SENIOR = 5;
    private static final double DESCONTO_REGULAR = 30.0;
    private static final double DESCONTO_XULAMBS_JUNIOR = 0.10;
    private static final double DESCONTO_XULAMBS_SENIOR = 0.20;
    private static final double DESCONTO_PEDIDOS_ADICIONAIS_XULAMBS_SENIOR = 15.0;

    /**
     * Construtor para cliente com nome e CPF. CPF é um atributo final.
     * Ainda precisa melhorar: validação dos dados.
     *
     * @param CPF  CPF do cliente (final)
     * @param nome Nome do cliente
     */
    public Cliente(String CPF, String nome) {
        this.CPF = CPF;
        this.nome = nome;
        this.pedidos = new Stack<>();
        this.totalGasto = 0.0;
        this.totalPedidos = 0;
        this.pedidosMesAtual = 0;
        this.pedidosUltimos30Dias = 0;
        this.pedidosUltimos60Dias = 0;
    }

    /**
     * Registra um pedido para o cliente, desde que não seja nulo. Neste caso,
     * ignora.
     *
     * @param pedido O pedido a ser registrado. Não deve ser nulo.
     */
    public void registrarPedido(Pedido pedido) {
        if (pedido != null) {
            this.pedidos.push(pedido);
            this.totalGasto += pedido.valorTotal();
            this.totalPedidos++;
            this.pedidosMesAtual++;
            this.pedidosUltimos30Dias++;
            this.pedidosUltimos60Dias++;

            verificarStatusCliente();
        }
    }

    /**
     * Verifica e atualiza o status do cliente com base nos critérios de cliente regular, Xulambs Júnior e Xulambs Sênior.
     */
    private void verificarStatusCliente() {
        // Verificar cliente regular
        if (totalPedidos % 20 == 0) {
            // Aplicar desconto de R$30 no próximo pedido
            // (considerando que o desconto seja aplicado imediatamente no próximo pedido)
            if (!pedidos.isEmpty()) {
                Pedido ultimoPedido = pedidos.peek();
                ultimoPedido.aplicarDesconto(DESCONTO_REGULAR);
            }
        }

        // Verificar cliente Xulambs Júnior
        if (totalGasto >= LIMITE_GASTO_XULAMBS_JUNIOR || totalPedidos >= LIMITE_PEDIDOS_XULAMBS_JUNIOR) {
            aplicarDescontoXulambsJunior();
        }

        // Verificar cliente Xulambs Sênior
        if (pedidosUltimos60Dias >= LIMITE_PEDIDOS_XULAMBS_SENIOR || totalGasto >= LIMITE_GASTO_XULAMBS_SENIOR) {
            aplicarDescontoXulambsSenior();
        }
    }

    /**
     * Aplica o desconto de Xulambs Júnior a todos os pedidos do cliente.
     */
    private void aplicarDescontoXulambsJunior() {
        for (Pedido pedido : pedidos) {
            pedido.aplicarDesconto(DESCONTO_XULAMBS_JUNIOR);
        }
    }

    /**
     * Aplica o desconto de Xulambs Sênior a todos os pedidos do cliente,
     * incluindo o desconto adicional a cada 5 pedidos.
     */
    private void aplicarDescontoXulambsSenior() {
        int descontoAdicional = pedidosUltimos60Dias / LIMITE_PEDIDOS_ADICIONAIS_XULAMBS_SENIOR;
        double descontoTotal = DESCONTO_XULAMBS_SENIOR + (descontoAdicional * DESCONTO_PEDIDOS_ADICIONAIS_XULAMBS_SENIOR);

        for (Pedido pedido : pedidos) {
            pedido.aplicarDesconto(descontoTotal);
        }
    }

    /**
     * Retorna um extrato resumido com todos os pedidos do cliente.
     * Cada pedido terá sua data, seu número e valor pago.
     *
     * @return String com as informações de data, número e valor pago para todos os
     *         pedidos do cliente.
     */
    public String extrato() {
        StringBuilder extrato = new StringBuilder();
        for (Pedido pedido : pedidos) {
            extrato.append("Data: ").append(pedido.getData())
                    .append(" - Número: ").append(pedido.getId())
                    .append(" - Valor pago: R$ ").append(pedido.valorTotal())
                    .append("\n");
        }
        return extrato.toString();
    }

    /**
     * Retorna um extrato resumido com os pedidos do cliente entre duas datas,
     * incluindo estas duas.
     * Cada pedido terá sua data, seu número e valor pago.
     *
     * @param inicio Data para o início do extrato (inclusiva)
     * @param fim    Data para o fim do extrato (inclusiva)
     * @return String com as informações de data, número e valor pago para todos os
     *         pedidos do cliente.
     */
    public String extrato(Data inicio, Data fim) {
        StringBuilder extrato = new StringBuilder();
        for (Pedido pedido : pedidos) {
            if (pedido.getData().compareTo(inicio) >= 0 && pedido.getData().compareTo(fim) <= 0) {
                extrato.append("Data: ").append(pedido.getData())
                        .append(" - Número: ").append(pedido.getId())
                        .append(" - Valor pago: R$ ").append(pedido.valorTotal())
                        .append("\n");
            }
        }
        return extrato.toString();
    }

    /**
     * Retorna uma lista imutável com a cópia de todos os pedidos do Cliente.
     * A lista é imutável para não permitir alteração de pedidos 'por fora' da
     * regra.
     *
     * @return Lista com a cópia de todos os pedidos do Cliente.
     */
    public List<Pedido> todosOsPedidos() {
        return Collections.unmodifiableList(pedidos);
    }

    /**
     * Igualdade do cliente: CPF iguais.
     *
     * @param o Objeto cliente a ser comparado
     * @return TRUE para clientes iguais, FALSE caso contrário
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return CPF.equals(cliente.CPF);
    }

    /**
     * Descrição do cliente em string: dados pessoais, total de pedidos e gasto
     * total.
     *
     * @return String com dados do cliente, total de pedidos e gasto total.
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "CPF='" + CPF + '\'' +
                ", nome='" + nome + '\'' +
                ", totalGasto=" + totalGasto +
                ", totalPedidos=" + totalPedidos +
                ", pedidosMesAtual=" + pedidosMesAtual +
                ", pedidosUltimos30Dias=" + pedidosUltimos30Dias +
                ", pedidosUltimos60Dias=" + pedidosUltimos60Dias +
                '}';
    }
}