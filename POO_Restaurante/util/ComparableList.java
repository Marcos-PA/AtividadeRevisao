import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

 /** Lista comparável e (por enquanto) não iterável: demonstração de polimorfismo paramétrico a partir da composição com List.
  *  Note que esta classe desobriga a classe T de implementar a interface Comparable.
 */
public class ComparableList<T> implements Iterable<T>{
    
    //#region atributos
    /**
     * Encapsula os dados originais em uma List do Java. 
     */
    private List<T> dadosOriginais;

    /** 
     * Comparador padrão da lista (opcional)
     */
    private Comparator<T> comparadorPadrao;
    //#endregion
    
    //#region construtores
    /**
     * Encapsula a ação dos diferentes construtores.
     * @param lista A lista que será armazenada.
     * @param comparador O comparador que será armazenado (ou nulo, caso não tenha)
     */
    private void init(List<T> lista, Comparator<T> comparador){
        this.dadosOriginais = lista;
        this.comparadorPadrao = comparador;
    }
    /**
     * Construtor para uma lista vazia, sem comparador padrão
     */
    public ComparableList(){
        init(new LinkedList<>(), null);
    }

    /**
     * Construtor para uma lista vazia, com comparador padrão
     * @param comparador O comparador para a lista criada.
     */
    public ComparableList(Comparator<T> comparador){
        init(new LinkedList<>(), comparador);
    }

    /**
     * Construtor que cria uma lista comparável com cópia dos objetos de uma lista pré-existente, sem comparador padrão.
     * @param listaOriginal A lista para ser copiada e encapsulada.
     */
    public ComparableList(List<T> listaOriginal){
        init(listaOriginal, null);
    }

    /**
     * Construtor que cria uma lista comparável com cópia dos objetos de uma lista pré-existente e com um comparador padrão.
     * @param listaOriginal A lista para ser copiada e encapsulada.
     * @param comparador O comparador para a lista criada.
     */
    public ComparableList(List<T> listaOriginal, Comparator<T> comparador){
        init(listaOriginal, comparador);
    }
    //#endregion
    
    //#region operações
    /**
     * Adiciona um elemento na posição <i>pos</i> da lista. Lança exceção em caso de posição inválida (maior que o tamanho da lista).
     * @param pos Posição para inserção.
     * @param elemento Elemento para inserção.
     * @throws IndexOutOfBoundsException Se a posição for menor que 0 ou maior que o tamanho atual da lista.
     */
    public void add(int pos, T elemento){
        this.dadosOriginais.add(pos, elemento);
    }

    /**
     * Adiciona um elemento no final da lista.
     * @param elemento Elemento a ser armazenado.
     */
    public void addAtEnd(T elemento){
        this.dadosOriginais.add(elemento);
    }

    /**
     * Adciona um elemento no início da lista (posição 0).
     * @param elemento Elemento a ser adicionado.
     */
    public void addFirst(T elemento){
        this.add(0, elemento);
    }

    /**
     * Verifica se um elemento existe na lista. A verificação se dá pelo polimorfismo paramétrico que, implicitamente, utiliza o
     * método <i>equals</i> do elemento para igualdade.
     * @param elemento Elemento a ser procurado. Pode ser um objeto <i>mock</i> com a chave de igualdade para busca.
     * @return TRUE/FALSE conforme o elemeto existe ou não.
     */
    public boolean contains(T elemento){
        return this.dadosOriginais.contains(elemento);
    }

    /**
     * Retorna o primeiro elemento da lista, sem retirar. Lança exceção em caso de lista vazia.
     * @return O primeiro elemento da lista.
     * @throws IndexOutOfBoundsException Caso a lista esteja vazia (posição 0 é inválida.)
     */
    public T getFirst(){
        return this.dadosOriginais.get(0);
    }

    /**
     * Retorna o último elemento da lista, sem retirar. Lança exceção em caso de lista vazia.
     * @return O último elemento da lista.
     * @throws IndexOutOfBoundsException Caso a lista esteja vazia.
     */
    public T getLast(){
        return this.dadosOriginais.get(dadosOriginais.size()-1);
    }

    /**
     * Retorna um elemento em posiução específica da lista, sem retirar. Lança exceção em caso de lista vazia ou posição inválida (<0 ou >=tamanho).
     * @return O elemento na posição especificada da lista.
     * @throws IndexOutOfBoundsException Em caso de posição inválida (<0 ou >=tamanho) ou lista vazia.
     */
    public T get(int pos){
        return this.dadosOriginais.get(pos);
    }

    /**
     * Localiza e retorna um elemento da lista, ser retirá-lo. A verificação se dá pelo polimorfismo paramétrico que, implicitamente, utiliza o
     * método <i>equals</i> do elemento para igualdade. Pode retornar null se o elemento não existir. O elemento para verificação pode ser <i>mock</i>,
     * isto é, conter somente a chave de busca para a igualdade.
     * @param elemento Elemento a ser procurado. Pode ser um objeto <i>mock</i> com a chave de igualdade para busca. 
     * @return Elemento encontrado na lista ou null, caso não exista.
     */
    public T find(T elemento){
        T retorno = null;
        int pos = this.dadosOriginais.indexOf(elemento);
        if(pos!=-1)
            retorno = this.get(pos);
        return retorno;
    }

    /**
     * Localiza, remove e retorna um elemento da lista. A verificação se dá pelo polimorfismo paramétrico que, implicitamente, utiliza o
     * método <i>equals</i> do elemento para igualdade. Pode retornar null se o elemento não existir. 
     * O elemento para verificação pode ser <i>mock</i>, isto é, conter somente a chave de busca para a igualdade.
     * @param elemento Elemento a ser procurado. Pode ser um objeto <i>mock</i> com a chave de igualdade para busca. 
     * @return  Elemento encontrado na lista ou null, caso não exista.
     */
    public T remove(T elemento){
        T retorno = this.find(elemento);
        this.dadosOriginais.remove(elemento);
        return retorno;
    }

    /**
     * Tamanho da lista.
     * @return Inteiro contendo o tamanho atual da lista.
     */
    public int size(){
        return this.dadosOriginais.size();
    }

 
    /**
     * Retorna o maior elemento da lista, em concordância com o comparador padrão da lista. 
     * Usa explicitamente o método "compare" da interface <i>Comparator</i> para a decisão.
     * Em caso de lista sem comparador padrão, lança exceção.
     * @param meuComparador Comparador a ser utilizado. Deve obedecer à interface <i>Comparator</i>.
     * @return Maior elemento da lista, de acordo com o comparador passado.
     * @throws IndexOutOfBoundsException Em caso de lista vazia.
     * @throws NullPointerException Em caso de comparador não existente.
     */
    public T greatest(){
        return this.greatest(this.comparadorPadrao);
    }

    /**
     * Retorna o menor elemento da lista, em concordância com o comparador padrão da lista. 
     * Usa explicitamente o método "compare" da interface <i>Comparator</i> para a decisão.
     * Em caso de lista sem comparador padrão, lança exceção.
     * @param meuComparador Comparador a ser utilizado. Deve obedecer à interface <i>Comparator</i>.
     * @return Menor elemento da lista, de acordo com o comparador passado.
     * @throws IndexOutOfBoundsException Em caso de lista vazia.
     * @throws NullPointerException Em caso de comparador não existente.
     */
    public T smallest(){
        return this.smallest(this.comparadorPadrao);
    }

    /**
     * Retorna o maior elemento da lista, em concordância com o comparador passado por parâmetro. 
     * Usa explicitamente o método "compare" da interface <i>Comparator</i> para a decisão.
     * @param meuComparador Comparador a ser utilizado. Deve obedecer à interface <i>Comparator</i>.
     * @return Maior elemento da lista, de acordo com o comparador passado.
     * @throws IndexOutOfBoundsException Em caso de lista vazia.
     * * @throws NullPointerException Em caso de comparador nulo.
     */
    public T greatest(Comparator<T> meuComparador){
        T greatest = this.dadosOriginais.get(0);
        for (T elemento : dadosOriginais) {
            if(meuComparador.compare(elemento, greatest)>0)
                greatest = elemento;
        }
        return greatest;
    }

    /**
     * Retorna o menor elemento da lista, em concordância com o comparador passado por parâmetro. 
     * Usa explicitamente o método "compare" da interface <i>Comparator</i> para a decisão.
     * @param meuComparador Comparador a ser utilizado. Deve obedecer à interface <i>Comparator</i>.
     * @return Menor elemento da lista, de acordo com o comparador passado.
     * @throws IndexOutOfBoundsException Em caso de lista vazia.
     * * @throws NullPointerException Em caso de comparador nulo.
     */
    public T smallest(Comparator<T> meuComparador){
        T smallest = this.dadosOriginais.get(0);
        for (T elemento : dadosOriginais) {
            if(meuComparador.compare(elemento, smallest)<0)
                smallest = elemento;
        }
        return smallest;
    }

    /**
     * Ordena a lista de acordo com o comparador padrão. Os dados têm suas posições modificadas.
     * Caso a lista não possua um comparador padrão, causa uma exceção de ponteiro nulo (ou seja,
     * neste momento só pode ser usado em listas com comparador definido)
     * @throws NullPointerException Em caso de comparador nulo
     */
    public void sort(){
        Collections.sort(this.dadosOriginais, this.comparadorPadrao);
    }
    
    /**
     * Iterador para uso em loops de coleção. Retorna o iterador padrão de uma List (Collection)
     * @return Iterador para uso em loops. É o iterador padrão das coleções do Java.
     */
    @Override
    public Iterator<T> iterator() {
        return this.dadosOriginais.iterator();
    }

    //#endregion
    
}

