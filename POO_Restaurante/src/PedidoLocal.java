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
  * PedidoLocal substitui o sistema de herança do Pedido pela interface IPedido.
  * Note que toda a lógica aqui presente foi transferida da classe Pedido original,
  * sem perda de funcionalidade nem mudança de interface.
  */
public class PedidoLocal implements IPedido{
    private static final BigDecimal TAXA_SERVICO = new BigDecimal(0.1).setScale(2, RoundingMode.HALF_UP);
    private ComparableList<Comida> listaComidas;
    
    /**
     * Construtor: recebe a lista de comidas para abrir o pedido.
     * @param itens Lista de pedidos para abrir o pedido
     */
    public PedidoLocal(ComparableList<Comida> itens){
        this.listaComidas = itens;
    }


    /**
     * Adiciona uma comida ao pedido, usando JCF
     * @param novoItem Comida a ser adicionada
     */
    @Override
    public void addComida(Comida novaComida) {
        this.listaComidas.addAtEnd(novaComida);
    }

    /**
     * Valor dos itens em separado, para computar no preço final do pedido.
     * @return BigDecimal de escala 2 com o valor somente dos itens do pedido.
     */
    private BigDecimal valorItens(){
        BigDecimal valor = new BigDecimal(0.0);
        for(Comida comida : listaComidas) {
            valor = valor.add(comida.precoFinal());
        }
        return valor.setScale(2, RoundingMode.HALF_UP);
     }

     /**
     * Retorna taxa de serviço para pedidos no local (atualmente em 10%).
     * @return Valor da com a taxa de serviço atual, considerando o valor dos itens. BigDecimal de escala 2.
     */
    @Override
    public BigDecimal valorTaxa() {
        return valorItens().multiply(TAXA_SERVICO).setScale(2, RoundingMode.HALF_UP);
    }


    @Override
    public void aplicarDesconto(double descontoRegular) {
        for(Comida comida : listaComidas) {
            comida.aplicarDesconto(descontoRegular);
        }
    }
    
}
