import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
/** 
 * MIT License
 *
 * Copyright(c) 2022-23  João Caram <caram@pucminas.br>
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
import java.util.List;

/**
 * Comida: tem um preço base e pode ter adicionais de preço fixo. Classe abstrata que recebe por parâmetro os valores necessários da classe filha.
 * Implementa a interface comparável para prover ordenação.
 */
public abstract class Comida implements Comparable<Comida>{
    //#region atributos
    private int maxAdicionais;
    private BigDecimal precoPorAdicional;
    private int quantAdicionais;
    private List<Ingrediente> ingredientes;
    protected String descricao;
    private BigDecimal precoBase;
    //#endregion

    //#region construtores

    /**
     * Inicializador para os construtores da comida. Note o alto acomplamento pela quantidade de 
     * parâmetros necessários das classes filhas. Note também o uso de if ternário.
     * @param maxAdicionais Quantidade máxima de adicionais desta comida
     * @param precoPorAdicional Preço de cada adicional desta comida
     * @param descricao Descrição/nome da comida
     * @param precoBase Preço básico da comida
     * @param quantAdicionais Quantidade de adicionais já incluídos na comida
     */
    private void init(int maxAdicionais, double precoPorAdicional,
                 String descricao,   double precoBase, int quantAdicionais){
        BigDecimal aux;

        this.maxAdicionais = maxAdicionais>=0?maxAdicionais:0;

        aux = new BigDecimal(precoPorAdicional);
       
        this.precoPorAdicional = 
            aux.compareTo(BigDecimal.ZERO)>0?aux:new BigDecimal(0.1);
        this.precoPorAdicional.setScale(2,RoundingMode.HALF_UP);

        aux = new BigDecimal(precoBase);
        this.precoBase =  
            aux.compareTo(BigDecimal.ONE)>0?aux:BigDecimal.ONE;
        this.precoBase.setScale(2,RoundingMode.HALF_UP);

        this.quantAdicionais = quantAdicionais>0?quantAdicionais:0;
        this.quantAdicionais = this.quantAdicionais>this.maxAdicionais?this.maxAdicionais:this.quantAdicionais;
       
        this.descricao = descricao;

        this.ingredientes = new ArrayList<>(this.maxAdicionais);

    }
    
    /**
     * Construtor para comida sem adicionais.
     * @param maxAdicionais Quantidade máxima de adicionais desta comida
     * @param precoPorAdicional Preço de cada adicional desta comida
     * @param descricao Descrição/nome da comida
     * @param precoBase Preço básico da comida
    */
    public Comida(int maxAdicionais, double precoPorAdicional,
                  String descricao, double precoBase){
        init(maxAdicionais, precoPorAdicional, descricao, 
                precoBase, 0);
    }

    /**
     * Construtor para comida com adicionais, que serão validados com o máximo.
     * @param maxAdicionais Quantidade máxima de adicionais desta comida
     * @param precoPorAdicional Preço de cada adicional desta comida
     * @param descricao Descrição/nome da comida
     * @param precoBase Preço básico da comida
     * @param quantAdicionais Quantidade de adicionais já incluídos na comida
     */
    public Comida(int maxAdicionais, double precoPorAdicional,
                  String descricao, double precoBase, int quantAdicionais){
        init(maxAdicionais, precoPorAdicional, descricao, 
                precoBase, quantAdicionais);
    }

    /**
     * Método privado para isolar a regra de valor dos adicionais da pizza.
     * @return O valor dos adicionais
     */
    protected BigDecimal valorIngredientes(){
        double valor = this.ingredientes.stream()
                            .mapToDouble(Ingrediente::getPreco)
                            .sum();
        return new BigDecimal(valor).setScale(2, RoundingMode.UP);
    } 

    /**
     * Método interno para validação da quantidade de ingredientes adicionais. Verifica se a quantidade
     * de ingredientes atuais somada/subtraída com os novos continua válida.
     * @param quantosAdicionais A quantidade a acrescentar/subtrair nos ingredientes atuais.
     * @return TRUE se continuar válido; FALSE caso contrário.
     */
    private boolean validarIngredientes(int quantosAdicionais){
        boolean resposta = false;
        int quantos = this.quantAdicionais + quantosAdicionais;
        if(quantos>=0 && quantos<=maxAdicionais)
            resposta = true;
        return resposta;
    }

    /**
     * Calcula o preço final: base + adicionais * preço dos adicionais. Método públic apesar de 
     * não haver requisito explícito pedindo o preço.
     * @return Preço final a pagar pela pizza
     */
    public BigDecimal precoFinal(){       
        return precoBase.add(valorIngredientes()).add(valorExtras());
    }

    /**
     * Tenta adicionar ingredientes à pizza. Só será executado se o total não exceder o limite, pois há validação interna.
     * Está obsoleto, pois agora temos ingredientes individualizados.
     * @param quantosAdicionais Quantidade de ingredientes a adicionar.
     * @deprecated Ingredientes agora são individualizados. Prefira usar a sobrecarga adicionarIngrediente(Ingrediente ingredienteNovo)
     */
    @Deprecated
    public void adicionarIngrediente(int quantosAdicionais){
        if(quantosAdicionais>0 && validarIngredientes(quantosAdicionais))
                this.quantAdicionais += quantosAdicionais;
    }

    /**
     * Tenta adicionar ingredientes à pizza. Só será executado se o total não exceder o limite, pois há validação interna.
     * @param quantosAdicionais Quantidade de ingredientes a adicionar.
     */
    public void adicionarIngrediente(Ingrediente ingredienteNovo){
        if(ingredienteNovo!=null && validarIngredientes(1)){
                this.ingredientes.add(ingredienteNovo);
                this.quantAdicionais++;
            }
    }

    /**
     * Tenta retirar ingredientes da pízza. Só será executado se o total não for negativo, pois há validação interna.
     * @param quantosAdicionais Quantidade de ingredientes a retirar.
     */
    public void retirarIngrediente(int quantosAdicionais){
        if(quantosAdicionais>0 && validarIngredientes(-quantosAdicionais))
                this.quantAdicionais -= quantosAdicionais;
    }

    
    /**
     * Retorna a nota de compra da pizza. Formato:
     * <descricao> com <qtdAdicionais> adicionais - R$ <precoFinal> 
     * @return String no formato indicado.
     */
    @Override
     public String toString(){
        return this.descricao + " com "+this.quantAdicionais+" adicionais";
    }

    /**
     * Rodapé comum para o relatório de comidas: valor+quantidade de ingredientes, preço final 
     * @return String com o rodapé informado acima.
     */
    protected String rodape(){
        DecimalFormat df=new DecimalFormat("#.##");
        StringBuilder sb = new StringBuilder();
        for (Ingrediente ingrediente : ingredientes) {
            sb.append("\n\t "+ingrediente.toString());
        }
        
        sb.append("\nTOTAL A PAGAR: R$ "+df.format(this.precoFinal()));
        return sb.toString();
    }

    /**
     * Comparação padrão entre duas comidas: preço final. Segue regra de Comparable
     * @param o A outra comida para comparar
     * @return -1, 0, 1, se o preço desta for menor, igual ou maior ao da outra, respectivamente.
     */
    @Override
    public int compareTo(Comida o) {
        return o.precoFinal().compareTo(this.precoFinal());
    }


    /**
     * Retorna o valor dos extras da comida (adicional não é extra)
     * @return BigDecimal de escala 2 com o valor dos extras.
     */
    protected abstract BigDecimal valorExtras();

    public void aplicarDesconto(double descontoRegular) {
        this.precoBase = this.precoBase.multiply(new BigDecimal(1-descontoRegular));
    }
    
}