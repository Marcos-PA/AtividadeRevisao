import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PedidoDeliveryTest {
    Pedido meuPedido;
    static Pizza pizzaBasica;

    @BeforeAll
    static void prepareAll(){
        pizzaBasica = new Pizza();
    }

    @BeforeEach
    public void prepare(){
        meuPedido = new Pedido(pizzaBasica, 2.5);
    }

    @Test
    public void naoPodeAdicionarAcimaDoLimite(){
        for(int i=0; i<11; i++)
            meuPedido.addItem(new Pizza());
        assertEquals(10, meuPedido.quantidadeDeItens());
    }

    @Test
    public void calculaValorTotalComEntrega(){
        assertEquals(30.0, meuPedido.valorTotal());
    }

    @Test
    public void calculaValorTotalComEntrega10Km(){
        Pedido pedido10km = new Pedido(pizzaBasica, 10.0);
        assertEquals(33.0, pedido10km.valorTotal());
    }

    @Test
    public void calculaValorTotalComEntrega12Km(){
        Pedido pedido12km = new Pedido(pizzaBasica, 10.1);
        assertEquals(37.0, pedido12km.valorTotal());
    }

}
