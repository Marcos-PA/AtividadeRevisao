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
  * Classe Sanduiche: usando especialização.
  * Pode ser bem melhorada:  refatorações.
  */
 public class Sanduiche extends Comida {

   //#region constantes
    private static final int MAX_ADICIONAIS_SANDUICHE = 5;
    private static final BigDecimal PRECO_ADICIONAIS_SANDUICHE = new BigDecimal(2.5).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal PRECO_BASE_SANDUICHE = new BigDecimal(13).setScale(2, RoundingMode.HALF_UP);
    private static final BigDecimal ADICIONAL_COMBO = new BigDecimal(3).setScale(2, RoundingMode.HALF_UP);
    private static String DESCRICAO_SANDUICHE = "Sanduíche ";
    //#endregion

    //#region
    private boolean ehCombo;
    private boolean ehVegano;
    //#endregion

    /**
     * Inicializador: configura o combo (ou não). Vegano é sempre falso na construção do sanduíche.
     * @param combo Booleano indicando se o sanduíche faz parte de combo (TRUE) ou não (FALSE)
     */
    private void init(boolean combo){
        this.ehCombo = combo;
        this.ehVegano = false;
    }
    
    /**
     * Construtor básico: sanduíche sem combo, sem adicionais, com carne
     */
    public Sanduiche(){
        super(MAX_ADICIONAIS_SANDUICHE, PRECO_ADICIONAIS_SANDUICHE.doubleValue(), DESCRICAO_SANDUICHE, PRECO_BASE_SANDUICHE.doubleValue());
        init(false);
    }

    /**
     * Construtor para combo. O sanduíche é criado sem adicionais e com carne
     * @param combo TRUE se o sanduíche está no combo, FALSE caso contrario;
     */
    public Sanduiche(boolean combo){
        super(MAX_ADICIONAIS_SANDUICHE, PRECO_ADICIONAIS_SANDUICHE.doubleValue(), DESCRICAO_SANDUICHE, PRECO_BASE_SANDUICHE.doubleValue());
        init(combo);
    }

    /**
     * Construtor para sanduíche sem combo, com adicionais, com carne.
     * @param adicionais A quantidade de adicionais do sanduíche (validado na classe mãe)
     */
    public Sanduiche(int adicionais){
        super(MAX_ADICIONAIS_SANDUICHE, PRECO_ADICIONAIS_SANDUICHE.doubleValue(), DESCRICAO_SANDUICHE, PRECO_BASE_SANDUICHE.doubleValue(), adicionais);
        init(false);
    }

    /**
     * Construtor que permite incluir o sanduíche num combo e configurar os ingredientes adicionais
     * @param combo TRUE se o sanduíche é combo, FALSE caso contrário
     * @param adicionais A quantidade de adicionais do sanduíche (validado na classe mãe)
     */
    public Sanduiche(boolean combo, int adicionais){
        super(MAX_ADICIONAIS_SANDUICHE, PRECO_ADICIONAIS_SANDUICHE.doubleValue(), DESCRICAO_SANDUICHE, PRECO_BASE_SANDUICHE.doubleValue(), adicionais);
        init(combo);
    }

    /**
     * Inclui ou retira o status de combo do sanduíche
     * @param temCombo TRUE se é um combo, FALSE se não é.
     */
    public void incluirCombo(boolean temCombo){
        this.ehCombo = temCombo;
    }

    /**
     * Permite configurar o sanduíche como vegano, ou reverter para sanduíche de carne
     * @param ehVegano TRUE para sanduíche vegano, FALSE para carne
     */
    public void opcaoVegana(boolean ehVegano){
        this.ehVegano = ehVegano;
    }

    /**
     * Calcula o valor dos extras do sanduíche. No momento só é cobrado extra para combo com refrigerante
     * @return BigDecimal de escala 2 com o valor dos extras
     */
    @Override
    protected BigDecimal valorExtras() {
        BigDecimal valorExtra = BigDecimal.ZERO;
        if(ehCombo)
            valorExtra = ADICIONAL_COMBO;
        return valorExtra;
    }

    /**
     * Descrição em string do sanduíche: cabeçalho de Comida, características de vegano e/ou combo, rodapé de comida.
     * @return String/relatório com a descrição acima. 
     */
    @Override
    public String toString(){
        StringBuilder desc = new StringBuilder(super.toString());
        if(ehVegano)
            desc.append(" vegano");
        desc.append(". R$ "+PRECO_BASE_SANDUICHE);
        if(ehCombo)
            desc.append("\n\t Combo: R$ "+ADICIONAL_COMBO);
       desc.append(rodape());
        return desc.toString();
    }
}