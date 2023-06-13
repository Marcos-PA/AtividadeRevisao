import java.math.BigDecimal;
import java.math.RoundingMode;


/** 
 * MIT License
 *
 * Copyright(c) 2022-23 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

 /** 
  * PedidoDelivery usando interface no lugar de herança. Repare que, na verdade, aqui pouco muda em relação ao anterior.
  */
public class PedidoDelivery implements IPedido{

    //#region atributos
    private static final int MAX_ITENS=10;       //por enquanto, pedido limitado
    private double distancia;
    private ComparableList<Comida> listaComidas;
    //#endregion

    /**
     * Inicializador para os construtores. Valida a distância mínima de 0.1
     * @param distanciaEntrega A distância para entrega (double positivo)
     */
    private void init(double distanciaEntrega){
        this.distancia = 0d;
        if(distanciaEntrega>0)
            this.distancia = distanciaEntrega;
    }

    
    /**
     * Construtor: uma lista de comidas para o pedido e a distância de entrega
     * @param listaItens Lista de comidas para abrir o pedido
     * @param distanciaEntrega Distância da entreg
     */
    public PedidoDelivery(ComparableList<Comida> listaItens, double distanciaEntrega) throws IllegalArgumentException{
        this.listaComidas = listaItens;
        init(distanciaEntrega);    
    }

    
    /**
     * Método para adicionar uma Comida com limitação de itens.
     * @param novoItem A comida para adicionar
     */
    @Override
    public void addComida(Comida novoItem){
        if(this.listaComidas.size()<MAX_ITENS)
             this.listaComidas.addAtEnd(novoItem);
     }

     /**
      * Taxa do pedido delivery: taxa de entrega. Até 5km, R$5. De 5.1 a 10km, R$8. Acima de 10km, R$10.
      * @return BigDecimal (escala 2) com valor da taxa
      */
     @Override
     public BigDecimal valorTaxa(){
        BigDecimal taxa = BigDecimal.ZERO;
        if(distancia<=5.0)
            taxa =  new BigDecimal(5.0);
        else if(distancia<=10)
                taxa =  new BigDecimal(8.0);
             else taxa = new BigDecimal(10.0);

        return taxa.setScale(2,RoundingMode.HALF_UP);
    }


    /**
     * Aplica o desconto no pedido. O desconto é aplicado em cada comida do pedido.
     * @param descontoRegular O desconto a ser aplicado
     */
    @Override
    public void aplicarDesconto(double descontoRegular){
        for(Comida comida: this.listaComidas){
            comida.aplicarDesconto(descontoRegular);
        }
    }
}
