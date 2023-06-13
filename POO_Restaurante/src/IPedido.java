import java.math.BigDecimal;

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
  * Interface pedido para exemplificação: herança x composição
  */
public interface IPedido {
    /**
     * Adiciona uma nova comida ao pedido. Classes que implementam podem criar regras de limitação,
     * verificação de duplicidade, entre outras.
     * @param novaComida Comida a ser adicionada
     */
    public void addComida(Comida novaComida);

    /**
     * Todo pedido tem uma taxa. Este método deve retornar a taxa correspondente à categoria do Pedido, em BigDecimal com escala 2
     * @return BigDecimal em escala 2 com o valor da taxa do Pedido
     */
    public BigDecimal valorTaxa();

    public void aplicarDesconto(double descontoRegular);
    
}
