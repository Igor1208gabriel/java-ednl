import java.util.ArrayList;

public class ArvoreAVL {
    private no raiz;
    private int tamanho;

    ArvoreAVL() {
        raiz = null;
        tamanho = 0;
    }

    void fatorBalanceamentoColeguinha(no a, no b, int i) { // i = +1 se esquerda, i = -1 se direita
        int antigob = 0, antigoa = 0;
    
        if (b != null) antigob = b.getfab();
        if (a != null) antigoa = a.getfab();
    
        if (b != null) b.setfab(antigob + i - Math.min(antigoa, 0));
        if (a != null) a.setfab(antigoa + i + Math.max(b.getfab(), 0));
    }


    void rotacionar(no atual){
        if (atual == null) {
            System.out.println("Nó atual é null.");
            return;
        }

        int esquerda = 0, direita = 0;
        if(atual.getesq() != null) esquerda = atual.getesq().getfab();
        if(atual.getdir() != null) direita  = atual.getdir().getfab();

        System.out.println("Nó " + atual.getval() + " com fab = " + atual.getfab());

        if(atual.getfab() >= 2){
            if(esquerda >= 0) rotacaoDireita(atual);
            if(esquerda <  0) rotacaoDireitaDupla(atual);
        } else if (atual.getfab() <= -2) {
            if(direita <= 0) rotacaoEsquerda(atual);
            if(direita >  0) rotacaoEsquerdaDupla(atual);
        }
        
        if(atual.getpai() != null) rotacionar(atual.getpai());
    }  

    void rebalancear(no atual){
        if(eExterno(atual)) atual.setfab(0);
        no pai = atual.getpai();
        if(atual.getpai() == null) return;
        int fab = pai.getfab();
        
        if(eFilhoEsquerdo(atual)) pai.setfab(fab+1);
        if(eFilhoDireito (atual)) pai.setfab(fab-1);
        
        if(pai.getfab() == 0) return;
        if(pai.getfab() > 1 || pai.getfab() > 1) rotacionar(atual);
        rebalancear(pai);
    }

    void plantar(int x) {   
        no planta = encontrar(x, raiz);
        if(planta != null) return;
        if(eVazio()) raiz = new no(x, null);
        else plantarRec(x, raiz);
        tamanho++;

        no novo = encontrar(x, raiz);
        
        rebalancear(novo);
        rotacionar(novo);

        System.out.println("Plantou " + x);
        imprimirArvore();
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
        if (n == null) return 0;
        int esq = altura(n.getesq());
        int dir = altura(n.getdir());
        return 1 + Math.max(esq, dir);
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
            System.out.print("|---");
        }
        if(eRaiz(atual)){
            System.out.print("    ");
        }
    
        System.out.println(atual.getval() + " [" + atual.getfab() + "]");
    
        imprimirArvoreRec(atual.getesq(), espaco);
    }
    
    void imprimirArvore() {
        System.out.println();
        imprimirArvoreRec(raiz, 0);
        System.out.println();
    }
    
    void plantarVarios(ArrayList<Integer> lista){
        for (Integer lInteger : lista) {
            System.out.println("Plantando " + lInteger);
            plantar(lInteger);
        }
    }






    void rotacaoEsquerda(no pai){
        no esq, rodar, avo;
        rodar = pai.getdir();
        esq = rodar.getesq();
        avo = pai.getpai();

        System.out.println("Rotação esquerda no nó " + pai.getval());
        imprimirArvore();

        rodar.setesq(pai);
        pai.setpai(rodar);
        pai.setdir(esq);
        if(esq != null) esq.setpai(pai);
        rodar.setpai(avo);

        if(avo != null) {
            if(eFilhoEsquerdo(pai)) avo.setesq(rodar);
            if(eFilhoDireito (pai)) avo.setdir(rodar);
        } else {
            this.raiz = rodar;
        }
        //if(esq != null) rebalancear(esq);
        //if(esq == null) rebalancear(pai);
        fatorBalanceamentoColeguinha(rodar, pai, 1);

    }

    void rotacaoDireita(no pai){
        no dir, rodar, avo;
        rodar = pai.getesq();
        dir = rodar.getdir();
        avo = pai.getpai();

        System.out.println("Rotação direita no nó " + rodar.getval());
        imprimirArvore();


        rodar.setdir(pai);
        pai.setpai(rodar);
        pai.setesq(dir);
        if(dir != null) dir.setpai(pai);
        
        rodar.setpai(avo);
        if(avo != null) {
            if(eFilhoEsquerdo(avo)) avo.setesq(rodar);
            if(eFilhoDireito(avo))  avo.setdir(rodar);
        } else {
            this.raiz = rodar;
        }
        //if(dir != null) rebalancear(dir);
        //if(dir == null) rebalancear(pai);
        fatorBalanceamentoColeguinha(rodar, pai, -1);
    }

    void rotacaoDireitaDupla(no rodar){
        rotacaoEsquerda(rodar.getesq());
        rotacaoDireita(rodar);
    }
    
    void rotacaoEsquerdaDupla(no rodar){
        rotacaoDireita(rodar.getdir());
        rotacaoEsquerda(rodar);
    }

}
