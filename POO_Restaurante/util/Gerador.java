import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Gerador {
    static Random sorteador = new Random(42);
    static int ultimoCPF = 101;
    
    static List<Pedido> gerarPedidos(int quantos) {
        Comida comidaAtual = null;
        Pedido pedidoAtual = null;
        LinkedList<Pedido> lista = new LinkedList<>();
        for (int i = 0; i < quantos; i++) {
            
            int quantas = sorteador.nextInt(6) + 1;
            for (int j = 0; j < quantas; j++) {
                int qual = sorteador.nextInt(5);
                int adicionais = sorteador.nextInt(8);
                switch (qual) {
                    case 0:
                        comidaAtual = new Pizza("p", adicionais);
                        break;
                    case 1:
                        comidaAtual = new Pizza("m", adicionais);
                        break;
                    case 2:
                        comidaAtual = new Pizza("g", adicionais);
                        break;

                    case 3:
                        comidaAtual = new Sanduiche(true, adicionais);
                        break;
                    case 4:
                        comidaAtual = new Sanduiche(false, adicionais);
                        break;
                }
                if (j == 0)
                    pedidoAtual = new Pedido(comidaAtual);
                else
                    pedidoAtual.addComida(comidaAtual);
            }
            lista.add(pedidoAtual);
        }
        return lista;
    }

    static Cliente gerarCliente(){
        ultimoCPF+= sorteador.nextInt(15)+1;
        return new Cliente("100.000."+ultimoCPF+"-00", "cliente"+ultimoCPF);
    }
}
