import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class App {
    static Scanner teclado = new Scanner(System.in);
    static final String MENU_PRINCIPAL = "dados/menuPrincipal";
    private static final int NUM_CLIENTES = 10;
    private static final String RELAT_PEDIDOS = "dados/Pedidos.txt";
    private static final String RELAT_PEDIDOS_DATA = "dados/PedidosPorData.txt";
    private static final String MENU_RELATORIOS = "dados/menuRelatorios";

    // #region controle
    static HashMap<String, Cliente> clientes;
    static LinkedList<Pedido> todosOsPedidos;
    static ComparableList<Pedido> listaPedidosPorData;

    // #endregion

    // #region Utilidades e menus

    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     */
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Menu para o restaurante
     * 
     * @return Opção do usuário (int)
     */
    public static int menu(String nomeArquivo) throws FileNotFoundException {
        limparTela();
        File arqMenu = new File(nomeArquivo);
        Scanner leitor = new Scanner(arqMenu, "UTF-8");
        System.out.println(leitor.nextLine());
        System.out.println("==========================");
        int contador = 1;
        while (leitor.hasNextLine()) {
            System.out.println(contador + " - " + leitor.nextLine());
            contador++;
        }
        System.out.println("0 - Sair");
        System.out.print("\nSua opção: ");
        int opcao = Integer.parseInt(teclado.nextLine());
        leitor.close();
        return opcao;
    }

    /**
     * Menu para o restaurante
     * 
     * @return Opção do usuário (int)
     */
    public static int subMenuAdicionais() {
        System.out.println("");
        System.out.println("==========================");
        System.out.println("O QUE DESEJA ADICIONAR? ");
        int opcao = -1;
        int item = 1;
        for (Ingrediente ing : Ingrediente.values()) {
            System.out.println(item + " - " + ing);
            item++;
        }
        System.out.println("0 - Sair");
        System.out.print("\nSua opção: ");
        opcao = Integer.parseInt(teclado.nextLine());

        return (opcao - 1);
    }

    /**
     * Menu para o restaurante
     * 
     * @return Opção do usuário (int)
     */
    public static int subMenuPedido() {
        limparTela();
        System.out.println("");
        System.out.println("==========================");
        System.out.println("1 - Pedido local");
        System.out.println("2 - Pedido para entrega");
        System.out.println("0 - Sair");
        System.out.print("\nSua opção: ");
        int opcao = Integer.parseInt(teclado.nextLine());
        return opcao;
    }

    /**
     * Menu para o restaurante
     * 
     * @return Opção do usuário (int)
     */
    public static int subMenuRelatorios() {
        limparTela();

        int opcao = Integer.parseInt(teclado.nextLine());
        return opcao;
    }

    /**
     * Menu para o restaurante
     * 
     * @return Opção do usuário (int)
     */
    public static int subMenuComidas() {
        System.out.println("");
        System.out.println("==========================");
        System.out.println("1 - Pizza");
        System.out.println("2 - Sanduíche");
        System.out.println("3 - Sobremesa");
        System.out.println("0 - Sair");
        System.out.print("\nSua opção: ");
        int opcao = Integer.parseInt(teclado.nextLine());
        return opcao;
    }

    /**
     * Menu para o restaurante
     * 
     * @return Opção do usuário (int)
     */
    public static int subMenuSanduiches() {

        System.out.println("==========================");
        System.out.println("Comprando um novo sanduíche:");
        System.out.println("01 - Básico");
        System.out.println("02 - Combo com refrigerante");
        System.out.println("03 - Vegano");
        System.out.println("04 - Vegano + combo com refrigerante");
        System.out.print("\nSua opção: ");
        int opcao = Integer.parseInt(teclado.nextLine());
        return opcao;
    }

    // #region sobremesa

    public static Sobremesa comprarSobremesa() {
        return (Sobremesa) criarComida(3);
    }

    /**
     * Menu para o restaurante
     * 
     * @return Opção do usuário (int)
     */
    public static Sobremesa criarSobremesa() {
        Sobremesa nova = null;
        System.out.println("\n==========================");
        System.out.println("Comprando uma nova sobremesa:");
        System.out.println("1 - Pavê de Morango");
        System.out.println("2 - Torta de Limão");
        System.out.print("\nSua opção: ");
        int opcao = Integer.parseInt(teclado.nextLine());
        switch (opcao) {
            case 1:
                nova = new PaveDeMorango();
                break;
            case 2:
                nova = new TortaDeLimao();
                break;
        }
        return nova;
    }

    public static void imprimirDadosSobremesa(String mensagem, Sobremesa qual) {
        if (qual != null) {
            System.out.println(mensagem);
            System.out.println(qual.toString());
        } else
            System.out.println("Sobremesa não está registrada. Favor verificar");
    }

    // #endregion

    /**
     * Menu para o restaurante
     * 
     * @return Opção do usuário (int)
     */
    public static int menuAlteracaoPedido(Pedido pedidoAtual) {
        limparTela();
        System.out.println("ALTERAR PEDIDO");
        System.out.println("O pedido atual tem " + pedidoAtual.quantidadeDeItens() + " itens.");

        System.out.println("1 - Acrescentar item");
        System.out.println("2 - Modificar item");
        System.out.println("3 - Cancelar item");
        System.out.print("\nSua opção: ");
        int opcao = Integer.parseInt(teclado.nextLine());
        return opcao;
    }

    /**
     * Pausa para leitura de mensagens em console
     * 
     * @param teclado Scanner de leitura
     */
    static void pausa() {
        System.out.println("Enter para continuar.");
        teclado.nextLine();
    }

    public static HashMap<String, Cliente> geraClientes(int quantos) {
        HashMap<String, Cliente> clientes = new HashMap<>();
        Cliente novo;
        for (int i = 0; i < quantos; i++) {
            novo = Gerador.gerarCliente();
            clientes.put(novo.CPF, novo);
        }
        return clientes;
    }

    public static void carregarDadosTeste() {
        clientes = geraClientes(NUM_CLIENTES);
        todosOsPedidos = new LinkedList<Pedido>(Gerador.gerarPedidos(NUM_CLIENTES * 10));
        listaPedidosPorData = new ComparableList<>(todosOsPedidos, Pedido::compareTo);

        listaPedidosPorData.sort();
        Iterator<Pedido> it = listaPedidosPorData.iterator();
        for (Cliente cli : clientes.values()) {
            for (int i = 0; i < 10; i++)
                cli.registrarPedido(it.next());
        }
    }
    // #endregion

    // #region métodos do app

    // #region Pizza

    public static Pizza comprarPizza() {
        return (Pizza) criarComida(1);
    }

    public static Comida comprarComida() {
        System.out.println("\n==========================");
        System.out.println("Comprando uma nova comida:");
        int opcao = -1;
        opcao = subMenuComidas();
        Comida novaComida = criarComida(opcao);
        return novaComida;
    }

    public static Comida criarComida(int opcao) {
        Comida nova = null;
        if (opcao == 1) {
            Pizza novaPizza = criarPizza();
            nova = novaPizza;
        } else if (opcao == 2) {
            Sanduiche novoSand = criarSanduiche();
            nova = novoSand;
        } else {
            Sobremesa novaSobre = criarSobremesa();
            nova = novaSobre;
            return nova;
        }
        alterarIngredientes(nova);
        return nova;

    }

    public static Pizza criarPizza() {
        String validos = "pmg";
        String tamanho;
        System.out.println("\n==========================");
        System.out.println("Comprando uma nova pizza:");
        System.out.print("Tamanho (p, m, g): ");
        tamanho = teclado.nextLine();
        if (!validos.contains(tamanho.toLowerCase()))
            tamanho = "m";
        Pizza pizza = new Pizza(tamanho);

        return pizza;
    }

    public static Sanduiche criarSanduiche() {
        int opcao = subMenuSanduiches();
        Sanduiche novo = new Sanduiche();
        switch (opcao) {

            case 2:
                novo = new Sanduiche(true);
                break;
            case 3:
                novo = new Sanduiche();
                novo.opcaoVegana(true);
                break;
            case 4:
                novo = new Sanduiche(true);
                novo.opcaoVegana(true);
                break;
        }
        return novo;
    }

    @Deprecated
    public static void alterarPizza(Pizza pizza) {
        alterarIngredientes(pizza);
    }

    public static void alterarIngredientes(Comida comida) {
        int opcao = subMenuAdicionais();
        while (opcao != -1) {
            int quantos = 0;
            Ingrediente ingrediente = Ingrediente.values()[opcao];
            System.out.print("Quantos? ");
            quantos = Integer.parseInt(teclado.nextLine());
            for (int i = 0; i < quantos; i++) {
                comida.adicionarIngrediente(ingrediente);
            }
            System.out.println("Adicionado: " + quantos + "x " + ingrediente);
            pausa();
            opcao = subMenuAdicionais();
        }
    }

    public static void acrescentarPizza(Pedido pedido) {
        adicionarComidas(pedido);

    }

    /**
     * Encapsula a impressão de dados de uma pizza no sistema.
     * 
     * @param mensagem Mensagem adicional para exibição (opcional)
     * @param qual     A pizza que terá seus dados impressos.
     */
    public static void imprimirDadosPizza(String mensagem, Pizza qual) {
        if (qual != null) {
            System.out.println(mensagem);
            System.out.println(qual.toString());
        } else
            System.out.println("Pizza não está registrada. Favor verificar");
    }
    // #endregion

    // #region Pedido

    public static Comida selecionarItemPedido(Pedido pedido, String mensagem) {
        int qualItem = -1;
        System.out.print(mensagem + " ");
        qualItem = Integer.parseInt(teclado.nextLine());
        Comida comidaParaAlterar = pedido.getComida(qualItem - 1);
        return comidaParaAlterar;
    }

    public static void alterarPedido(Pedido pedido) {
        Comida comidaParaAlterar = selecionarItemPedido(pedido, "Qual item você deseja alterar?");
        if (comidaParaAlterar != null) {
            alterarIngredientes(comidaParaAlterar);
            System.out.println("Comida alterada no pedido.");
        } else
            System.out.println("Pedido sem alterações.");
    }

    public static void cancelarItemPedido(Pedido pedido) {
        int qualItem = -1;
        System.out.print("Qual item você deseja cancelar? ");
        qualItem = Integer.parseInt(teclado.nextLine());
        pedido.cancelaItem(qualItem - 1);
        System.out.println("Item cancelado.");
    }

    /**
     * Encapsula a impressão de dados de um pedido no sistema.
     * 
     * @param mensagem Mensagem adicional para exibição (opcional)
     * @param qual     Pedido que terá seus dados impressos.
     */
    public static void imprimirDadosPedido(String mensagem, Pedido qual) {
        if (qual != null) {
            System.out.println(mensagem);
            System.out.println(qual);
        } else
            System.out.println("Não há pedido em andamento.");
    }

    /**
     * Antigo método para adicionar pizzas ao pedido. Será descontinuado na próxima
     * versão,
     * pois podemos usar o método adicionarComidas, mais genérico.
     * 
     * @param pedido O pedido que receberá a pizza.
     * @return O pedido com a pizza adicionada. Não modifica o pedido caso a pizza
     *         seja um objeto nulo
     * @deprecated Não recomendamos seu uso. O ambiente evoluiu para compra de
     *             comidas, não somente de pizzas.
     */
    @Deprecated
    public static Pedido adicionarPizzas(Pedido pedido) {
        Pizza novaPizza;
        System.out.print("Adicionar outra pizza (S/N)? ");
        String continuar = teclado.nextLine();
        while (continuar.toLowerCase().equals("s")) {
            novaPizza = comprarPizza();
            if (novaPizza != null)
                pedido.addComida(novaPizza);
            System.out.print("Adicionar outra pizza (S/N)? ");
            continuar = teclado.nextLine();
        }
        return pedido;
    }

    public static Pedido adicionarComidas(Pedido pedido) {
        Comida novaComida = null;

        int opcao = subMenuComidas();
        while (opcao != 0) {
            limparTela();
            System.out.println(pedido);
            System.out.print("\nAdicionar mais itens? ");
            novaComida = criarComida(opcao);

            if (novaComida != null) {
                pedido.addComida(novaComida);
                System.out.println("Comida acrescentada ao pedido.");
            }

            System.out.print("\nAdicionar mais itens? ");
            opcao = subMenuComidas();
        }
        return pedido;
    }

    public static Pedido criarNovoPedido(int modoPedido) {
        limparTela();
        System.out.println("NOVO PEDIDO:");
        Pedido novo = null;
        Comida novaComida = comprarComida();
        if (modoPedido == 1)
            novo = new Pedido(novaComida);
        else {
            System.out.print("Pedido para entrega. Distância da entrega(km): ");
            double distancia = Double.parseDouble(teclado.nextLine());
            novo = new Pedido(novaComida, distancia);
        }
        adicionarComidas(novo);
        return novo;
    }

    public static void modoRelatorio() throws FileNotFoundException {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        int opcao = -1;
        while (opcao != 0) {
            limparTela();
            opcao = menu(MENU_RELATORIOS);
            switch (opcao) {
                case 1:
                    BigDecimal valor = new BigDecimal(
                            todosOsPedidos.stream()
                                    .mapToDouble(Pedido::valorTotal)
                                    .sum());

                    System.out.println("Total vendido até hoje: " + nf.format(valor));
                    break;
                case 3:
                    BigDecimal media = new BigDecimal(
                            todosOsPedidos.stream()
                                    .mapToDouble(Pedido::valorTotal)
                                    .average().getAsDouble());
                    System.out.println("Temos um total de " + todosOsPedidos.size() + " pedidos.");
                    System.out.println("Valor médio de um pedido: " + nf.format(media));
                    break;
            }
            pausa();
        }
    }

    // #endregion

    // #region Cliente

    public static int menuLogin() {
        limparTela();
        System.out.println("XULAMBS PIZZA");
        System.out.println("==========================");
        System.out.println("1 - Login");
        System.out.println("2 - Cadastrar");
        System.out.println("0 - Sair");
        System.out.print("\nSua opção: ");
        int opcao = Integer.parseInt(teclado.nextLine());

        return opcao;
    }

    public static Cliente fazerLogin() {
        limparTela();
        System.out.println("LOGIN");
        System.out.println("==========================");
        System.out.print("Digite seu CPF: ");
        String cpf = teclado.nextLine();
        Cliente clienteAtual = clientes.get(cpf);
        if (clienteAtual != null) {
            System.out.println("Bem vindo, " + clienteAtual.nome + "!");
            pausa();
            return clienteAtual;
        } else
            System.out.println("Cliente não encontrado.");
        pausa();
        return null;
        
        
    }

    public static void cadastrarCliente() {
        limparTela();
        System.out.println("CADASTRO DE CLIENTE");
        System.out.println("==========================");
        System.out.print("Digite seu CPF: ");
        String cpf = teclado.nextLine();
        Cliente clienteAtual = clientes.get(cpf);
        if (clienteAtual == null) {
            System.out.print("Digite seu nome: ");
            String nome = teclado.nextLine();
            clienteAtual = new Cliente(cpf, nome);
            clientes.put(cpf, clienteAtual);
            System.out.println("Cliente cadastrado com sucesso.");
        } else
            System.out.println("Cliente já cadastrado.");
        pausa();
    }
    // #endregion

    public static void main(String[] args) throws Exception {

        carregarDadosTeste();

        int opcao = -1;
        Pedido pedidoAtual = null;
        Cliente clienteAtual = null;
        do {
            opcao = menuLogin();

            switch (opcao) {
                case 1:
                    clienteAtual = fazerLogin();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
            if (clienteAtual != null) {
                do {
                    opcao = menu(MENU_PRINCIPAL);

                    switch (opcao) {
                        case 1:
                            if (pedidoAtual == null) {
                                int modoPedido = subMenuPedido();
                                pedidoAtual = criarNovoPedido(modoPedido);
                            } else
                                System.out.println("Ainda há pedido em aberto.");
                            break;
                        case 2:
                            if (pedidoAtual != null) {
                                int alteracao = menuAlteracaoPedido(pedidoAtual);

                                switch (alteracao) {
                                    case 1:
                                        adicionarComidas(pedidoAtual);
                                        break;
                                    case 2:
                                        alterarPedido(pedidoAtual);
                                        break;
                                    case 3:
                                        cancelarItemPedido(pedidoAtual);
                                        break;
                                    default:
                                        System.out.println("Opção inválida");
                                        break;
                                }
                            } else
                                System.out.println("Não há pedido em andamento. Favor abrir pedido.");
                            break;
                        case 3:
                            imprimirDadosPedido("Pedido em aberto:", pedidoAtual);
                            break;
                        case 4:
                            imprimirDadosPedido("Finalizando o pedido:", pedidoAtual);
                            System.out.print("CPF do Cliente: ");
                            String cpf = teclado.nextLine();
                            clienteAtual = clientes.get(cpf);
                            if (clienteAtual != null) {
                                clienteAtual.registrarPedido(pedidoAtual);
                                todosOsPedidos.add(pedidoAtual);
                                listaPedidosPorData.addAtEnd(pedidoAtual);
                                pedidoAtual = null;
                            } else
                                System.out.println("Cliente não encontrado.");
                            break;
                        case 5:
                            limparTela();
                            System.out.println("Todos os pedidos no arquivo " + RELAT_PEDIDOS);
                            FileWriter pedidos = new FileWriter(RELAT_PEDIDOS);
                            for (Pedido pedido : todosOsPedidos) {
                                pedidos.append(pedido + "\n");
                            }
                            pedidos.close();
                            break;
                        case 6:
                            limparTela();
                            listaPedidosPorData.sort();
                            System.out.println("Todos os pedidos por data no arquivo " + RELAT_PEDIDOS_DATA);
                            FileWriter pedidosData = new FileWriter(RELAT_PEDIDOS_DATA);
                            for (Pedido pedido : listaPedidosPorData) {
                                pedidosData.append(pedido + "\n");
                            }
                            pedidosData.close();
                            break;
                        case 7:
                            limparTela();
                            for (Cliente cli : clientes.values()) {
                                System.out.println(cli);
                                System.out.println("---------");
                            }
                            break;
                        case 8:
                            limparTela();
                            System.out.print("CPF do Cliente: ");
                            clienteAtual = clientes.get(teclado.nextLine());
                            if (clienteAtual != null) {
                                for (Pedido ped : clienteAtual.todosOsPedidos()) {
                                    System.out.println(ped);
                                    System.out.println("---------");
                                }
                            }
                            break;
                        case 9:
                            modoRelatorio();
                            break;
                    }
                    pausa();
                } while (opcao != 0);
            }
        } while (opcao != 0);
        System.out.println("Vlw flw glr. T prx.");
    }
}
