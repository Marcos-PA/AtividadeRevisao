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

 /** Demonstração (com biblioteca utilitária de ordenação) da abstração obtida pelo uso de interface em Pedido e Comida*/
public class Util {

    //TODO: documentar biblioteca
    //TODO: teste completo

    public static void quicksort(Comparable[] dados, int inicio, int fim){        	
        if(inicio>=fim)            
		    return;        
	    else{
            int particao = particao(dados, inicio, fim);
            quicksort(dados, inicio, particao-1);            			
            quicksort(dados, particao+1, fim);        
	   }    
    }

private static int particao(Comparable[] dados, int inicio, int fim){
	int posicao = inicio-1;
	Comparable pivot = dados[fim];
       for (int i = inicio; i < fim; i++) {
            if(dados[i].compareTo(pivot)<0){
                posicao++;                
		 trocar(dados, posicao, i);            
	     }        
	}        
	posicao++;        
	trocar(dados, posicao, fim);        
	return posicao;
}

public static void trocar(Comparable[] dados, int posicao1, int posicao2){
    Comparable aux = dados[posicao1];
    dados[posicao1] = dados[posicao2];
    dados[posicao2] = aux;
}
}
