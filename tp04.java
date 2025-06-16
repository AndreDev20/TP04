import java.text.DateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Data {

    private int dia;
    private int mes;
    private int ano;

    private static final Scanner SC = new Scanner(System.in);

    private static final String[] MESES = {
            "janeiro", "fevereiro", "março", "abril", "maio", "junho",
            "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"
    };

    public Data() {
        System.out.println("== Configurar Data ==");
        lerAno();
        lerMes();
        lerDia();
        System.out.println("Data inicializada com sucesso!\n");
    }

    public Data(int dia, int mes, int ano) {
        if (!dataValida(dia, mes, ano))
            throw new IllegalArgumentException("Data inválida: " + dia + "/" + mes + "/" + ano);
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    public void setDia(int dia)   { if (dataValida(dia, mes, ano)) this.dia = dia; }
    public void setMes(int mes)   { if (dataValida(dia, mes, ano)) this.mes = mes; }
    public void setAno(int ano)   { if (dataValida(dia, mes, ano)) this.ano = ano; }

    private void lerDia() {
        while (true) {
            try {
                System.out.print("Dia (1-31): ");
                int d = SC.nextInt();
                if (dataValida(d, mes, ano)) { dia = d; break; }
                erro("Dia fora do intervalo permitido.");
            } catch (InputMismatchException e) {
                erro("Informe um número inteiro.");
                SC.next();
            }
        }
    }

    private void lerMes() {
        while (true) {
            try {
                System.out.print("Mês (1-12): ");
                int m = SC.nextInt();
                if (m >= 1 && m <= 12) { mes = m; break; }
                erro("Mês fora de 1-12.");
            } catch (InputMismatchException e) {
                erro("Informe um número inteiro.");
                SC.next();
            }
        }
    }

    private void lerAno() {
        while (true) {
            try {
                System.out.print("Ano (>0): ");
                int a = SC.nextInt();
                if (a >= 1) { ano = a; break; }
                erro("Ano deve ser positivo.");
            } catch (InputMismatchException e) {
                erro("Informe um número inteiro.");
                SC.next();
            }
        }
    }

    public int getDia()  { return dia; }
    public int getMes()  { return mes; }
    public int getAno()  { return ano; }

    public String mostra1() { return String.format("%02d/%02d/%04d", dia, mes, ano); }
    public String mostra2() { return String.format("%02d/%s/%04d", dia, MESES[mes - 1], ano); }

    public boolean bissexto() {
        return (ano % 400 == 0) || (ano % 4 == 0 && ano % 100 != 0);
    }

    public int diasTranscorridos() {
        int total = dia;
        for (int m = 1; m < mes; m++)
            total += ultimoDiaDoMes(m, ano);
        return total;
    }

    public void apresentaDataAtual() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
        System.out.println("Data atual: " + df.format(new Date()));
    }

    private static boolean dataValida(int d, int m, int a) {
        return a >= 1 && m >= 1 && m <= 12 && d >= 1 && d <= ultimoDiaDoMes(m, a);
    }

    private static int ultimoDiaDoMes(int mes, int ano) {
        switch (mes) {
            case 4: case 6: case 9: case 11: return 30;
            case 2:  return ((ano % 400 == 0) || (ano % 4 == 0 && ano % 100 != 0)) ? 29 : 28;
            default: return 31;
        }
    }

    private static void erro(String msg) {
        System.err.println("Erro: " + msg);
    }

    public static void fecharScanner() {
        if (SC != null) SC.close();
    }

    public static void main(String[] args) {

        System.out.println("** Construtor interativo **");
        Data d1 = new Data();
        System.out.println("Formato 1: " + d1.mostra1());
        System.out.println("Formato 2: " + d1.mostra2());
        System.out.println("Bissexto?  " + d1.bissexto());
        System.out.println("Dias no ano: " + d1.diasTranscorridos());

        System.out.println("\n** Construtor parametrizado **");
        Data natal = new Data(25, 12, 2025);
        System.out.println("Formato 1: " + natal.mostra1());
        System.out.println("Formato 2: " + natal.mostra2());
        System.out.println("Bissexto?  " + natal.bissexto());
        System.out.println("Dias no ano: " + natal.diasTranscorridos());

        fecharScanner();
    }
}
