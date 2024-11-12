public class no {
    private int val;
    private no pai;
    private no esq;
    private no dir;
    private int fab;

    public no(int v, no p, no e, no d) {
        val = v;
        pai = p;
        esq = e;
        dir = d;
        fab = 0;
    }

    public no(int v, no p){
        val = v;
        pai = p;
        fab = 0;
    }

    public int getval() {
        return val;
    }

    public no getpai() {
        return pai;
    }

    public no getesq() {
        return esq;
    }

    public no getdir() {
        return dir;
    }

    public int getfab() {
        return fab;
    }

    public void setval(int v) {
        val = v;
    }

    public void setpai(no n) {
        pai = n;
    }

    public void setesq(no n) {
        esq = n;
    }

    public void setdir(no n) {
        dir = n;
    }

    public void setfab(int n) {
        fab = n;
    }

}
