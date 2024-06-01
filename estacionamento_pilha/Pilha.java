import java.time.LocalTime;

public class Pilha <Info>{
    private No<Info> topo;
    private int tamanho;
    //construtor padrão
    public boolean estaVazia() {
        return topo == null;
    }    
    public void push (Info info) {
        No<Info> novo = new No<>(info);
        if (!estaVazia()) novo.setProximo(topo);
        topo = novo;
        tamanho++;
    }
    public Info pop () {
        if (estaVazia()) return null;
        Info info = topo.getInfo();
        topo = topo.getProximo();
        tamanho--;
        return info;
    }
    public Info consultaTopo () {
        if (estaVazia()) return null;
        return topo.getInfo();
    }
    public int getTamanho () {
        return tamanho;
    }
    @Override
    public String toString () {
        if (estaVazia()) return "pilha vazia";
        String s = "";
        No<Info> aux = topo;
        while (aux != null) {
            s += aux + "\n";
            aux = aux.getProximo();
        }
        return s;
    }
}
