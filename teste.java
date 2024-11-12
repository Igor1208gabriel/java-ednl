import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class teste {

    public static ArrayList<Integer> leitura_arquivo(String filePath) throws NumberFormatException, IOException {

        ArrayList<Integer> numeros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                for (String part : parts) numeros.add(Integer.parseInt(part.trim()));
            }
        }

            return numeros;
    }
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        ArvoreBinariaPesquisa abp = new ArvoreBinariaPesquisa();
        ArvoreAVL avl = new ArvoreAVL();
        ArrayList<Integer> lista = leitura_arquivo("Entrada.txt");
        
        //abp.plantarVarios(lista);
        avl.plantarVarios(lista);
        //System.out.println("Árvore Binária de pesquisa");
        //abp.imprimirArvore();
        //System.out.println("\nÁrvore AVL");
        //avl.imprimirArvore();

    }
    
}