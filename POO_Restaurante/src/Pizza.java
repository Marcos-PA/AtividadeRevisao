import java.math.BigDecimal;
import java.math.RoundingMode;
/** 
 * MIT License
 *
 * Copyright(c) 2023 João Caram <caram@pucminas.br>
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
  * Classe Pizza: usando especialização.
  * Pode ser bem melhorada:  refatorações.
  */
public class Pizza extends Comida{
    
    //#region constantes
    public static final int MAX_ADICIONAIS_PIZZA = 8;
    private static final BigDecimal PRECO_ADICIONAIS_PIZZA = new BigDecimal(4.0).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal PRECO_BASE_PIZZA = new BigDecimal(25.0).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal PROPORCAO_GRANDE = new BigDecimal(0.1).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal PROPORCAO_PEQUENA = new BigDecimal(-0.12).setScale(2, RoundingMode.HALF_UP);
    private static final String DESCRICAO_PIZZA = "Pizza com queijo e calabresa tamanho";
    //#endregion

    //#region atributos
    private int tamanho;  
    //#endregion 
    
    //#region construtores

    /**
     * Inicializador: só cria pizzas com adicionais válidos
     */
    private void init(String tamanho){
        this.tamanho = 2; //média
        switch(tamanho.toLowerCase()){
            case "m": 
                this.descricao+= " médio";
                break;
            case "g": this.tamanho = 3;
                this.descricao+= " grande";
                break;
            case "p": this.tamanho = 1;
                this.descricao+= " pequeno";
                break;
        };      
    }

    /**
     * Construtor padrão: pizza sem adicionais e média
     */
    public Pizza(){
        super(MAX_ADICIONAIS_PIZZA,PRECO_ADICIONAIS_PIZZA.doubleValue(),
                    DESCRICAO_PIZZA,PRECO_BASE_PIZZA.doubleValue());
        init("M");
    }

    /**
     * Construtor: pizza sem adicionais com escolha de tamanho
     */
    public Pizza(String tamanho){
        super(MAX_ADICIONAIS_PIZZA,PRECO_ADICIONAIS_PIZZA.doubleValue(),
                    DESCRICAO_PIZZA,PRECO_BASE_PIZZA.doubleValue());
        init(tamanho);
    }

    /**
     * Cria uma pizza com adicionais. Caso sejam mais que o máximo, fica com 0 adicionais.
     * @param quantosAdicionais Quantidade de adicionais a incluir.
     */
    public Pizza(int quantosAdicionais){
        super(MAX_ADICIONAIS_PIZZA,PRECO_ADICIONAIS_PIZZA.doubleValue(),
                DESCRICAO_PIZZA,PRECO_BASE_PIZZA.doubleValue(),quantosAdicionais);
        init("M");
    }

    /**
     * Cria uma pizza com adicionais e com escolha de tamanho.
     * @param quantosAdicionais Quantidade de adicionais a incluir.
     */
    public Pizza(String tamanho, int quantosAdicionais){
        super(MAX_ADICIONAIS_PIZZA,PRECO_ADICIONAIS_PIZZA.doubleValue(),
                DESCRICAO_PIZZA,PRECO_BASE_PIZZA.doubleValue(),quantosAdicionais);
        init(tamanho);
    }
    //#endregion

    //#region negócio
    /**
     * Calcula a proporção a ser acrescentada ou retirada do valor da pizza média para outros tamanhos.
     * @return Um valor double com o o valor que alteta o básico da pizza considerando o tamanho.
     */
    private BigDecimal valorPorTamanho(){
        BigDecimal valor = BigDecimal.ZERO;
        if(tamanho==1)
            valor = PROPORCAO_PEQUENA.multiply(PRECO_BASE_PIZZA);
        else if(tamanho==3)  
            valor =  PROPORCAO_GRANDE.multiply(PRECO_BASE_PIZZA);
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Método interno para encapsular o valor a pagar de todas as opções
     * na criação de uma pizza.
     */
    @Override
     protected BigDecimal valorExtras() {
        BigDecimal valor = valorPorTamanho();
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
    
    
    /**
     * Descrição da pizza em string: cabeçalho, detalhe do preço por tamanho, rodapé.
     * @return String com o formato descrito acima.
     */
    @Override
    public String toString(){
        StringBuilder desc = new StringBuilder(super.toString());
        
        desc.append(". R$ "+PRECO_BASE_PIZZA.add(valorPorTamanho()));
        desc.append(rodape());
        return desc.toString();
    }
    //#endregion     
}
