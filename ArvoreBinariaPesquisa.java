import java.util.ArrayList;

public class ArvoreBinariaPesquisa {
    private no raiz;
    private int tamanho;

    ArvoreBinariaPesquisa() {
        raiz = null;
        tamanho = 0;
    }



    void plantar(int x) {   
        no planta = encontrar(x, raiz);
        if(planta != null) return;
        if(eVazio()) raiz = new no(x, null);
        else plantarRec(x, raiz);
        tamanho++;
    }

    void plantarRec(int x, no atual){
        int valor = atual.getval();
        no novo = new no(x, atual);

        if(eExterno(atual)){
            if(valor > x) atual.setesq(novo);
            if(valor < x) atual.setdir(novo);
        } else {

            if(valor < x){
                if(atual.getdir() != null) plantarRec(x, atual.getdir());
                else atual.setdir(novo);
            }

            if(valor > x){
                if(atual.getesq() != null) plantarRec(x, atual.getesq());
                else atual.setesq(novo);
            }

        }
    }



    boolean colher(int x) {
        no lixo = encontrar(x, raiz);

        if(lixo == null){return false;}
        if (tamanho == 1) raiz = null;


        if (eExterno(lixo)){                                        //não tem filhos
            if(eFilhoDireito (lixo)) lixo.getpai().setdir(null);
            if(eFilhoEsquerdo(lixo)) lixo.getpai().setesq(null);
        }

        if(eInterno(lixo)){
            if(lixo.getdir() == null && lixo.getesq() != null) {    //tem só o filho da esquerda
                if(eFilhoDireito (lixo)) lixo.getpai().setdir(lixo.getesq());
                if(eFilhoEsquerdo(lixo)) lixo.getpai().setesq(lixo.getesq());
            }

            if(lixo.getesq() == null && lixo.getdir() != null) {    //tem só o filho da direita
                if(eFilhoDireito (lixo)) lixo.getpai().setdir(lixo.getdir());
                if(eFilhoEsquerdo(lixo)) lixo.getpai().setesq(lixo.getdir());
            }

            if(lixo.getdir() != null && lixo.getesq() != null) {    //Tem ambos os filhos
                int troca = valorSucessor(lixo);
                colher(troca);
                lixo.setval(troca);
            }
        }

        tamanho--;
        return true;
    }




    public no encontrar(int valor, no atual) {
        if(atual == null || (eExterno(atual) && atual.getval() != valor)) return null;

        int v = atual.getval();
        if (valor > v)
            return encontrar(valor, atual.getdir());

        if (valor < v)
            return encontrar(valor, atual.getesq());

        if (valor == v)
            return atual;
        
        return null;
    }



    no getRaiz() {return raiz;}

    int getTamanho() {return tamanho;}

    boolean eFilhoEsquerdo(no n) {
        if(n.getpai() == null) return false;
        return n.getpai().getesq() == n;
    }

    boolean eFilhoDireito(no n) {
        if(n.getpai() == null) return false;
        return n.getpai().getdir() == n;
    }

    boolean eInterno(no n) {return !eExterno(n);}

    boolean eExterno(no n) {return n.getdir() == null && n.getesq() == null;}

    boolean eRaiz(no n) {return n.getpai() == null;}

    boolean eVazio() {return tamanho == 0;}




    int profundidade(no n) {
        if (eRaiz(n))
            return 0;
        return 1 + profundidade(n.getpai());
    }

    int altura(no n) {
        if (n == null || eExterno(n)) return 0;
        return 1 + Math.max(altura(n.getesq()), altura(n.getdir()));
    }




    public ArrayList<Integer> preOrdem(){
        ArrayList<Integer> caminhamento = new ArrayList<Integer>();
        preordem(raiz, caminhamento);
        return caminhamento;
    }
    private void preordem(no n, ArrayList<Integer> s){
        s.add(n.getval());

        if (n.getesq() != null) preordem(n.getesq(), s);

        if (n.getdir() != null) preordem(n.getdir(), s);
    }

    public ArrayList<Integer> emOrdem(){
        ArrayList<Integer> caminhamento = new ArrayList<Integer>();
        emordem(raiz, caminhamento);
        return caminhamento;
    }
    private void emordem(no n, ArrayList<Integer> s){
        
        if (n.getesq() != null) emordem(n.getesq(), s);

        s.add(n.getval());

        if (n.getdir() != null) emordem(n.getdir(), s);
    }

    public ArrayList<Integer> posOrdem(){
        ArrayList<Integer> caminhamento = new ArrayList<Integer>();
        posordem(raiz, caminhamento);
        return caminhamento;
    }
    private void posordem(no n, ArrayList<Integer> s){
        
        if (n.getesq() != null) posordem(n.getesq(), s);

        if (n.getdir() != null) posordem(n.getdir(), s);

        s.add(n.getval());
    }

    private int valorSucessor(no alvo){
        ArrayList<Integer> lista = new ArrayList<Integer>();
        emordem(alvo.getdir(), lista);
        return lista.get(0);
    }

    void imprimirArvoreRec(no atual, int espaco){
        final int ESPACAMENTO = 4;
        if(atual == null) return;
    
        espaco += ESPACAMENTO;
    
        imprimirArvoreRec(atual.getdir(), espaco);
        System.out.print("\n");
    
        for (int i = ESPACAMENTO; i < espaco; i++) System.out.print(" ");
    
        if (eFilhoEsquerdo(atual)) {
            System.out.print("|___");
        } 
        if(eFilhoDireito(atual)) {
            System.out.print("|‾‾‾");
        }
        if(eRaiz(atual)){
            System.out.print("    ");
        }
    
        System.out.println(atual.getval());
    
        imprimirArvoreRec(atual.getesq(), espaco);
    }
    
    void imprimirArvore() {
        System.out.println();
        imprimirArvoreRec(raiz, 0);
        System.out.println();
    }
    
    void plantarVarios(ArrayList<Integer> lista){
        for (Integer lInteger : lista) {
            plantar(lInteger);
        }
    }

    void rotacaoEsquerda(no rodar){
        no pai = rodar.getpai();
        rodar.setpai(pai.getpai());
        if(rodar.getpai() != null) {
            if(eFilhoEsquerdo(pai)) rodar.getpai().setesq(rodar);
            if(eFilhoDireito (pai)) rodar.getpai().setdir(rodar);
        }
        else this.raiz = rodar;
        pai.setdir(rodar.getesq());
        pai.setpai(rodar);
        rodar.setesq(pai);
    }

    void rotacaoDireita(no rodar){
        no pai = rodar.getpai();
        rodar.setpai(pai.getpai());
        if(rodar.getpai() != null) {
            if(eFilhoEsquerdo(pai)) rodar.getpai().setesq(rodar);
            if(eFilhoDireito (pai)) rodar.getpai().setdir(rodar);
        }
        else this.raiz = rodar;
        pai.setesq(rodar.getdir());
        pai.setpai(rodar);
        rodar.setdir(pai);
    }

    void rotacaoDireitaDupla(no rodar){
        rotacaoEsquerda(rodar.getdir());
        rotacaoDireita(rodar.getpai());
    }
    
    void rotacaoEsquerdaDupla(no rodar){
        rotacaoDireita(rodar.getesq());
        rotacaoEsquerda(rodar.getpai());
    }

}
