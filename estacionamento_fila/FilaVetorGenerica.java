public class FilaVetorGenerica <E> {
    private int primeiro;
    private int ultimo;
    private E[] dados;
    private int tamanho;

    public static final int CAPACIDADE_MINIMA = 10;

    public FilaVetorGenerica (int capacidade) {
        dados = (E[]) new Object[capacidade];
        ultimo = dados.length - 1;
        primeiro = 0;
        tamanho = 0;
    }
    public FilaVetorGenerica () {
        this(CAPACIDADE_MINIMA);
    }
    public boolean estaVazia() {
        return tamanho == 0;
    }
    public boolean estaCheia () {
        return tamanho == dados.length;
    }

    int proxima (int pos) {
        return (pos + 1) % dados.length;
    }
    public boolean enfileira (E e) {
        if (estaCheia()) return false;
        ultimo = proxima (ultimo);
        dados[ultimo] = e;
        tamanho++;
        return true;
    }
    public E desenfileira () {
        if (estaVazia()) return null;
        E temp = dados[primeiro];
        primeiro = proxima(primeiro);
        tamanho--;
        return temp;
    }
    @Override
    public String toString() {
        if (estaVazia()) return "fila vazia";
        String  s = "";
        int i=primeiro;
        do {
            s += dados[i] + "\n";
            i = proxima(i);
        } while (i!= proxima(ultimo));
        return s;
    }
}
