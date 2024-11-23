package util;

import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.PanelUI;

public class Lista<T> {

    private No<T> inicio;
    private No<T> fim;
    private int tamanho = 0;

    private final int NAO_ENCONTRADO = -1;
    private final String NAO_EXISTE = "POSICAO NAO ENCONTRADA";
   public int getTamanho() {
       return this.tamanho;
   }

    public void adicionar (T elemento) {

        No<T> celula = new No<>(elemento);

        if (this.tamanho == 0) {
            this.inicio = celula;
        } else {
            this.fim.setProximo(celula);
        }
        this.fim = celula;
        this.tamanho++;
   }

   private void adicionaInicio (T elemento) {

        if (this.tamanho == 0) {
            No<T> celula = new No<>(elemento);
            this.inicio = celula;
            this.fim = celula;
        } else {
            No<T> celula = new No<>(elemento);
            celula.setProximo(this.inicio);
            this.inicio = celula;
        }
        this.tamanho++;
   }

   public void adicionar (int posicao, T elemento) {

       if (posicao < 0 || posicao > this.tamanho) {
           throw new IllegalArgumentException(NAO_EXISTE);
       }

        if (posicao == 0) {
            this.adicionaInicio(elemento);
        } else if (posicao == this.tamanho) {
            this.adicionar(elemento);
        } else { // meio
           No<T> noAnterior = buscaPosicao(posicao -1);
           No<T> proximoNo = noAnterior.getProximo();
           No<T> celula = new No<>(elemento);
           celula.setProximo(proximoNo);
           noAnterior.setProximo(celula);
           this.tamanho++;
        }
   }

  public void removeInicio () {
       if (this.tamanho == 0) {
           throw new RuntimeException(" TA VAZIA");
       }

       this.inicio = this.inicio.getProximo();
       this.tamanho--;

        if (this.tamanho == 0) {
            this.fim = null;
        }
   }

   public void remover (int posicao) {

       if (this.tamanho== 0) {
           throw new RuntimeException(" TA VAZXIA");
       }

       if (posicao == 1) {
           removeInicio();
       }

       if (posicao == this.tamanho) {
           removeFinal();
       }

       No<T> penultimoNO = this.inicio;

       for (int i = 0; i < posicao -1; i++) {
           penultimoNO = penultimoNO.getProximo();
       }
       No<T> NoAtual = penultimoNO.getProximo();
       penultimoNO.setProximo(NoAtual.getProximo());
       this.tamanho--;
   }

   public void removeFinal () {

       if (this.tamanho == 0) {
           throw new RuntimeException(" TA VAZIA");
       }

       if (this.tamanho == 1) {
           removeInicio();
       }

       No<T> penultimoNO = this.inicio;
       for (int i = 0; i < this.tamanho -2; i++) {
           penultimoNO = penultimoNO.getProximo();
       }
       penultimoNO.setProximo(null);
       this.fim = penultimoNO;
       this.tamanho--;
   }


   public int busca (T elemento) {

       No<T> atual = this.inicio;
        int posicao = 0;

       while (atual != null) {

           if (atual.getElemento().equals(elemento)) {
               return posicao;
           }
           atual = atual.getProximo();
           posicao++;
       }
       return NAO_ENCONTRADO;
   }
   private No<T> buscaPosicao (int posicao) {

      if (!(posicao >= 0 && posicao <= this.tamanho)) {
          throw new IllegalArgumentException(NAO_EXISTE);
      }

      No<T> atual = this.inicio;
      for (int i = 0; i < posicao; i++) {
          atual = atual.getProximo();
      }
      return atual;
   }

   public T busca (int posicao) {
        return this.buscaPosicao(posicao).getElemento();
   }

    public void limparLista () {

       No<T> atual = this.inicio;

       for (int i = 0; i < this.tamanho; i++) {
           No<T> proximo = atual.getProximo();
           atual.setElemento(null);
           atual.setProximo(null);
           atual = proximo;
       }

        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
   }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        No<T> atual = this.inicio;
        if (atual == null) {
            return sb.append("[]").toString();
        }

        sb.append("[ ");
        while (atual.getProximo() != null) {
            sb.append(atual).append(",");
            atual = atual.getProximo();
        }
        return sb.append(atual).append(" ]").toString();
    }
}
