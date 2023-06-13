import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PedidoTest {
    
    Pedido meuPedido;
    static Pizza pizzaBasica;

    @BeforeAll
    static void prepareAll(){
        pizzaBasica = new Pizza();
    }

    @BeforeEach
    public void prepare(){
        meuPedido = new Pedido(pizzaBasica);
    }

    @Test
    public void podeAdicionarNovaPizza(){
        meuPedido.addItem(new Pizza());
        assertEquals(2, meuPedido.quantidadeDeItens());
    }

    

    @Test
    public void podeRetirarPizza(){
        for(int i=0; i<5; i++)
            meuPedido.addItem(new Pizza());
        meuPedido.cancelaItem(0);
        assertEquals(5, meuPedido.quantidadeDeItens());
    }

    @Test
    public void naoPodeRetirarPizzaSeForAUnica(){
        Pedido pedido1Pizza = new Pedido(pizzaBasica);
        pedido1Pizza.cancelaItem(0);
        assertEquals(1, pedido1Pizza.quantidadeDeItens());
    }
    
    @Test
    public void naoPodeRetirarPizzaQueNaoExiste(){
        meuPedido.addItem(new Pizza());
        meuPedido.cancelaItem(2);
        assertEquals(2, meuPedido.quantidadeDeItens());
    }

    @Test
    public void sabeCalcularOPrecoComVariasPizzas(){
        meuPedido.addItem(new Pizza());
        assertEquals(55.0, meuPedido.valorTotal());
    }

    @Test
    public void sabeCalcularOPrecoComTaxa(){
        assertEquals(27.5, meuPedido.valorTotal());
    }

    @Test
    public void localizaCorretamenteUmaPizza(){
        
        for(int i=1; i<=5; i++){
            Pizza temp = new Pizza(i);
            meuPedido.addItem(temp);
        }
        Pizza localizada = meuPedido.getItem(3);
        assertTrue(localizada.toString().contains("3 adicionais")); 
    }

}
