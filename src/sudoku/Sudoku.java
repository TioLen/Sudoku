package sudoku;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

// [Entrega de trabalho - SUDOKU]
//   GABRIEL DA SILVA BARROS
//   BJD 2021.3 - ALGORITMOS E PROGRAMAÇÃO II
//   Prof.: FABIO LUBACHESKI


/*

    Eu,
    Gabriel da Silva Barros / Bacharelado em Jogos Digitais | BJD 2021.3
    declaro que
    todas as respostas são fruto de meu próprio trabalho,
    não copiei respostas de colegas externos à equipe,
    não disponibilizei minhas respostas para colegas externos à equipe e
    não realizei quaisquer outras atividades desonestas para me beneficiar ou prejudicar
    outros.

*/

public class Sudoku
{   
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException{
        titleScreenMatrix();
        game();
    }
    
    public static void game() throws IOException, InterruptedException{
         
        Scanner read = new Scanner (System.in);
        TimeUnit.MILLISECONDS.sleep(100);
        
        System.out.println("            Bem-vindo ao Sudoku!"
                        +"\n    Aqui você pode jogar sem problemas!"
                        +"\n    -----------------------------------");
        TimeUnit.MILLISECONDS.sleep(50);
        
        System.out.println("       Escolha um nível de dificuldade:"
                        +"\n   [1] Fácil  |  [2] Médio  | [3] Difícil"
                        +"\n                 [4] Insano                 ");
        
        int dificuldade = -1;
      //  -1 = indefinido
        // 0 = facil
        // 1 = medio
        // 2 = dificil
        
        dificuldade = read.nextInt();
        
        int stepState = -2;
        
        char [][] matrizTabela = new char [9][9];
        int  [][] matrizLocker = new int  [9][9];
        
        boolean tip = true; // variável que indica a primeira execução, para mostrar dicas ao jogador.
        
        boolean status = false; // true = ganhou o jogo.
        
        int action2; // action que serve unicamente para caso o jogador queira jogar novamente.
        
        matrizTabela = initialize(dificuldade);
        
        matrizLocker(matrizTabela, matrizLocker);
        // funcao que bloqueia as posicoes iniciais dos numeros da tabela
        
        print(matrizTabela);
        
        TimeUnit.MILLISECONDS.sleep(250);
        while (status == false){
            int x=0;
            int y=0;
            char numero = 0; // 0 = false: ou seja, o número ñ foi digitado, logo action = 2 (apagar nro);
            
            int action= -1; // 0 = sair,  1 = preencher,  2 = apagar
            
            
            TimeUnit.MILLISECONDS.sleep(50);
            System.out.println("         O que deseja fazer agora?"
                            +"\n   [1] Preencher  |  [2] Apagar  | [0] Sair");
            System.out.print("> ");
            action = read.nextInt();

            // Preencher
            if (action == 1)
            {
                if(tip == true){
                    System.out.println(
                    "\n*****************************************************************"
                +   "\n**     Digite as coordenadas X e Y, e o NÚMERO separados       **"
                +   "\n**  por espaço para preencher uma posição.  Ex.: \"5 5 7\".      **"
                +   "\n**                                                | | |        **"
                +   "\n**                                                x y número.  **"
                +   "\n*****************************************************************");
                    tip = false;
                }
                else{
                    System.out.println(
                        "\n*********************************************************"
                    +   "\n**  Digite as coordenadas X e Y, e o NÚMERO separados  **"
                    +   "\n**       por espaço para preencher uma posição.        **"
                    +   "\n*********************************************************");
                }
                System.out.print("> ");
                x = read.nextInt() -1;                        //  linha
                y = read.nextInt() -1;                       //   coluna
                
                numero = read.next().charAt(0);            //    numero  (char)
                
                stepState = step(matrizTabela, matrizLocker, x, y, numero, action);
                
                System.out.println    ("\n#################################################################");
                TimeUnit.MILLISECONDS.sleep(250);
                
                if (stepState == -1){
                    System.out.println(  " •----------------------------------------•");
                    System.out.print    (" |  [A posição está ocupada ou inválida]  |");
                    System.out.println("\n •----------------------------------------•");
                }
                else if (stepState == 1){
                    System.out.println("\n    •---------------------------------•");
                    System.out.print    ("    |  [Número inserido com sucesso]  |");
                    System.out.println("\n    •---------------------------------•");
                }
                
                else if (stepState == 0){
                    System.out.println("\n•---------------------------------------------------------•");
                    System.out.print    ("|  [O valor ja está inserido na linha, coluna ou região]  |");
                    System.out.println("\n•---------------------------------------------------------•");
                }
                else if (stepState == 2){ // valor inválido
                    System.out.println("\n•---------------------------------------------------------•");
                    System.out.print    ("|             [Digita um valor entre 1 e 9.]              |");
                    System.out.println("\n•---------------------------------------------------------•");
                }
            }
            
            // Apagar
            else if (action == 2)
            {
                System.out.println(
                        "\n*********************************************************"
                    +   "\n**  Digite as coordenadas X e Y, referentes à posição  **"
                    +   "\n**                 que deseja apagar.                  **"
                    +   "\n*********************************************************");
            
                System.out.print("> ");
                x = read.nextInt() -1; // linha
                y = read.nextInt() -1; // coluna
                
                stepState = step(matrizTabela, matrizLocker, x, y, numero, action);
                
                if (stepState == 2){
                    System.out.println(  " •----------------------------------------•");
                    System.out.print    (" |         [A posição é inválida]         |");
                    System.out.println("\n •----------------------------------------•");
                }
                else if (stepState == 1){
                    System.out.println("\n    •---------------------------------•");
                    System.out.print    ("    |   [Número apagado com sucesso]  |");
                    System.out.println("\n    •---------------------------------•");
                }
                else if (stepState == -1){
                    System.out.println("\n•---------------------------------------------------------•");
                    System.out.print    ("| [A posição já é nativa da matriz, não pode ser apagada] |");
                    System.out.println("\n•---------------------------------------------------------•");
                }
            }

            // Sair
            else if (action == 0)
                break;
            
            TimeUnit.MILLISECONDS.sleep(250);
                
            print(matrizTabela);
            
            status(matrizTabela);
            
        
        }
        
        if (status == true){
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.print("\n         Parabéns, ");
            
            TimeUnit.MILLISECONDS.sleep(1000);
            System.out.print(  "você ganhou o jogo!");
            
            youWin();
            TimeUnit.MILLISECONDS.sleep(1000);
            
            System.out.println("\n            Deseja jogar novamente?");
            TimeUnit.MILLISECONDS.sleep(1500);
            System.out.println("\n [1] Novo Jogo  |  ou qualquer outro número para sair");
            
            action2 = read.nextInt();
            
            if (action2 == 1){
                status = false;
                game();
            }
        }
    }
    
    public static char [][] initialize(int dificuldade) throws FileNotFoundException, IOException
    {
        char matriz[][] = new char[9][9];
        
        FileReader lerArquivo = null;
        
        // verifica dificuldade
        if (dificuldade == 1)
            lerArquivo = new FileReader("sudoku0.txt");
        else if (dificuldade ==  2)
            lerArquivo = new FileReader("sudoku1.txt");

        else if (dificuldade ==  3)
            lerArquivo = new FileReader("sudoku2.txt");
        else if (dificuldade ==  4)
            lerArquivo = new FileReader("sudoku3.txt");
        
        BufferedReader lerBufferizado = new BufferedReader (lerArquivo);

        int j=0;
        while(true)
        {
            String linha = lerBufferizado.readLine();
            if (linha == null)
                break;

            String [] vetorStrings = linha.split(" ");

            for (int i = 0; i < matriz[0].length; i++)
                matriz[j][i] = vetorStrings[i].charAt(0);
            j++;
        }
        lerBufferizado.close();
        
        return matriz;
    }
    
    public static void titleScreenMatrix() throws FileNotFoundException, IOException, InterruptedException{
        
        FileReader lerArquivo = new FileReader("sudokuExample.txt");
        BufferedReader lerBufferizado = new BufferedReader (lerArquivo); 
        
        while(true)
        {
            String linha = lerBufferizado.readLine();
            
            if (linha == null)
                break;
            System.out.println(linha);
            TimeUnit.MILLISECONDS.sleep(50);
        }
        lerBufferizado.close();
        
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.println("                   Autor:                   \n" +
                           "          Gabriel da Silva Barros           ");
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("            (github.com/TioLen)             ");
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("                                            \n" +
                           "********************************************");
        TimeUnit.MILLISECONDS.sleep(500);
    }
    
    public static void youWin() throws FileNotFoundException, IOException, InterruptedException{
        
        FileReader lerArquivo = new FileReader("cake.txt");
        BufferedReader lerBufferizado = new BufferedReader (lerArquivo); 
        
        while(true)
        {
            String linha = lerBufferizado.readLine();
            
            if (linha == null)
                break;
            System.out.println(linha);
            TimeUnit.MILLISECONDS.sleep(50);
        }
        lerBufferizado.close();
    }
    
    public static int[][] matrizLocker(char tabela[][], int mLock[][]){
        
        // m[][] - matriz original, tabela do sudoku.
        
        // mLock diz ao programa quais posições não podem ser alteradas
        
        // se matrizLock = 1, a posição está bloqueada
        // se matrizLock = 0, a posição está livre
        
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                
                if (tabela[k][i] != '_')
                    mLock[k][i] = 1;
                else
                    mLock[k][i] = 0;
                    
            }
        }
        
        return mLock;
        
    }
    
    public static void print(char m[][]){
        
        System.out.print(  "                      [Y]          ");
        System.out.print("\n             1 2 3   4 5 6   7 8 9\n"+
                           "             | | |   | | |   | | |" );
                              
        
        System.out.print("\n          ##========================##");
        for (int i = 0; i < m.length; i++) {
            
            System.out.println();
            if (i == 4)
                System.out.print("[X]  "+(i+1) + " ──║ ");
            else
                System.out.print("     "+(i+1) + " ──║ ");
            
            for (int j = 0; j < m.length; j++){
                System.out.print(m[i][j] + " ");
                if (j == 2 || j == 5)
                    System.out.print("| ");
            }
            System.out.print(" ║");
            
            if (i == 2 || i == 5)
                    System.out.print("\n          ║-------x-------x--------║");
            
        }
        
        System.out.println(          "\n          ##========================##");
        
    }
    
    public static void printInt(int m[][]){
        
        for (int i = 0; i < m.length; i++) {
            System.out.println();
            System.out.print(" ");
            for (int j = 0; j < m.length; j++)
            {
                System.out.print(m[i][j] + " ");
                if (j == 2 || j == 5)
                    System.out.print("| ");
                
            }
            System.out.print(" ──" + i);
            
            if (i == 2 || i == 5)
                    System.out.print("\n────────x────────x─────────");
            if (i == 4)
                System.out.print("   X");
            
        }
        System.out.println();
    }
    
    public static int step(char m[][], int [][] matrizLocker, int x, int y, char numero, int action)
    {
        // inserir numero
        // action = 1, significa que o usuário escolheu a ação 1, que é preencher um nro.
        if (action == 1)
        {
            if (matrizLocker[x][y] == 0)
            { // ou seja, se matrizLocker = livre (0)

                m[x][y] = numero; // matriz na posição x,y recebe 'n'
                
                // para se der errado
                for (int i = 0; i < 9; i++)
                {
                    if (m[x][i] == m[x][y]){
                        if (i != y){
                            m[x][y] = '_';
                            return 0;
                            //0
                        }
                    }
                }
                
                // para se der errado
                for (int i = 0; i < 9; i++)
                {
                    if (m[i][y] == m[x][y]){
                        if (x != i){
                            m[x][y] = '_';
                            return 0;
                        }
                    }
                }
                
              //  [ Verifica a grade ]
              //   fiz de forma que seja preciso apenas um FOR,
              //   pois a cada 1 linha verificada, se verificam também 3 colunas.
                
                // 'i' funciona com o WHILE
                int i = ((x/3) * 3);
                int iL = i+3; // iL = limitador de I - i+3 não estava dando certo, pois i era atualizado a cada rodada do loop e isso alterava o limite)
                
                // 'j' funciona com um FOR
                int jFormuled = ((y/3) * 3); // J após aplicada a formula
                int jL = jFormuled+3; //jL = limitador de J
                
                
                // para se der errado
                while (i < iL)
                {
                    for (int j = jFormuled; j < jL; j++)
                    {
                        if (y != j){
                            if (m[i][j] == m[x][y]){
                                m[x][y] = '_';
                                return 0;
                                
                                
                            }
                        }
                    }
                    i++;
                }
                
            }
            
            // se a posição já for nativa da tabela
            else if (matrizLocker[x][y] == 1){
                return -1;
            }
            else if ((  x>8 || y>8  )  ||  (  x<0 || y<0  )){
                return -1;
            }
            
            
            m[x][y] = numero;
            
            if (numero == '0'){
                m[x][y] = '_';
                return 2;
            }
        }
        
        if (action == 2)
        {
            if (matrizLocker[x][y] == 1)
            {  // se posição = ocupada
                return -1;
            }
            else if (x > 8 || y > 8 || x < 0 || y < 0){
                // se posicao = invalida
            }
            else if (matrizLocker[x][y] == 0){
                // se der pra apagar
                if(m[x][y] != '_'){
                    m[x][y] = '_';
                    return 1;
                }
            }
        }
        return 1;
        
    }
        
    public static boolean status(char m[][]){
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (m[i][j] == '_')
                    return false;
            }
        }
        return true;
    }
}// fecha a classe Sudoku
