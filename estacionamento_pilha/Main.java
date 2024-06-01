import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        class Carro {
            private String placa;
            private LocalTime horarioEntrada;
            private LocalTime horarioSaida;
            private long tempoPermanencia;
            private int manobras;
            private Carro proximo;
        
            public Carro (String placa, LocalTime horarioEntrada) {
                setPlaca(placa);
                setHorarioEntrada(horarioEntrada);
                proximo = null;
            }
        
            public String getPlaca() {
                return placa;
            }
            public LocalTime getHorarioEntrada() {
                return horarioEntrada;
            }
        
        
            public void setPlaca (String placa) {
                this.placa = placa;
            }
            public void setHorarioEntrada(LocalTime horario) {
                this.horarioEntrada = horario;
            }
            public void setHorarioSaida(LocalTime horarioSaida) {
                this.horarioSaida = horarioSaida;
            }
            public void setTempoPermanencia(long tempoPermanencia) {
                this.tempoPermanencia = horarioEntrada.until(horarioSaida, ChronoUnit.MINUTES);
            }
            public void setManobras(int manobras) {
                this.manobras = manobras;
            }
        
        
            @Override
            public String toString () {
                return " [" + placa + "] »";
            }
        }

        class Estacionamento {
            private static final int CAPACIDADE = 20;
            private Pilha<Carro> pilha = new Pilha();
            private Pilha<Carro> pilhaAux = new Pilha();
            private Pilha<Carro> pilhaSaida = new Pilha();
            private int escolha;
            private int quantidade = 0;
        
            public void rodarEstacionamento() {
                boolean rodar = true;
                while (rodar) {
                    escolha = desenharMenuPrincipal();
                    switch (escolha) {
                        case 0:
                            rodar = false;
                            break;
                        case 1:
                            entradaCarro();
                            break;
                        case 2:
                            saidaCarro();
                            break;
                        case 3:
                            consultaCarro();
                            break;
                        case 4:
                            verificarCapacidade();
                            break;
                        default:
                            System.out.println("Opção inválida! Tente novamente...");
                            break;
                    }
                    System.out.println();
                }
            }
        
            private int desenharMenuPrincipal() {
                int escolha;
                Scanner s = new Scanner(System.in);
                System.out.println("Sistema de Estacionamento com Pilha");
                System.out.println("1 - Registrar Entrada de Carro");
                System.out.println("2 - Registrar Saída de Carro");
                System.out.println("3 - Consultar Carro");
                System.out.println("4 - Verificar Capacidade");
                System.out.println("0 - Sair do sistema");
                escolha = s.nextInt();
                return escolha;
            }
        
            private void entradaCarro() {
                if (quantidade < CAPACIDADE) {
                    String placa;
                    Scanner s = new Scanner(System.in);
                    System.out.println("Digite a placa do carro:");
                    placa = s.nextLine();
                    pilha.push(new Carro(placa, LocalTime.now()));
                    quantidade++;
                    System.out.println("Carro enpilhado com sucesso!");
                } else {
                    System.out.println("Capacidade máxima do estacionamento atingida.");
                }
            }
        
            private void saidaCarro() {
                if (pilha.estaVazia()) {
                    System.out.println("O estacionamento está vazio no momento.");
                    return;
                }
                String placa;
                int manobras = 0;
                LocalTime horarioSaida;
                long tempoPermanencia;
                Scanner s = new Scanner(System.in);
                System.out.println("Digite a placa do carro para sair:");
                placa = s.nextLine();
                while (true) {
                    if (pilha.consultaTopo().getPlaca().equals(placa)) {
                        horarioSaida = LocalTime.now();
                        tempoPermanencia = pilha.consultaTopo().getHorarioEntrada().until(horarioSaida, ChronoUnit.SECONDS);
                        pilhaSaida.push(new Carro(placa,pilha.consultaTopo().getHorarioEntrada()));
                        pilhaSaida.consultaTopo().setHorarioSaida(horarioSaida);
                        pilhaSaida.consultaTopo().setTempoPermanencia(tempoPermanencia);
                        pilhaSaida.consultaTopo().setManobras(manobras);
                        pilha.pop();
                        while (!pilhaAux.estaVazia()) {
                            pilha.push(new Carro(pilhaAux.consultaTopo().getPlaca(), pilhaAux.consultaTopo().getHorarioEntrada()));
                            pilhaAux.pop();
                        }
                        quantidade--;
                        System.out.println("Horário de saída: " + horarioSaida);
                        System.out.println("Tempo total de permanência: " + tempoPermanencia + " segundos");
                        System.out.println("Número de manobras realizadas: " + manobras);
                        System.out.println("Carro de placa: " + placa + " removido com sucesso.");
                        break;
                    }
                    if (pilha.estaVazia()) {
                        System.out.println("Carro de placa: " + placa + " não encontrado");
                        while (!pilhaAux.estaVazia()) {
                            pilha.push(new Carro(pilhaAux.consultaTopo().getPlaca(), pilhaAux.consultaTopo().getHorarioEntrada()));
                            pilhaAux.pop();
                        }
                        break;
                    }
                    pilhaAux.push(new Carro(pilha.consultaTopo().getPlaca(), pilha.consultaTopo().getHorarioEntrada()));
                    pilha.pop();
                    manobras++;
                }
            }
        
            private void consultaCarro() {
                if(pilha.estaVazia()) {
                    System.out.println("No momento, o estacionamento está vazio.");
                    return;
                }
                Scanner s = new Scanner(System.in);
                String placa;
                int pos = 1;
                System.out.println("Digite a placa do carro:");
                placa = s.nextLine();
                while (true) {
                    if (pilha.estaVazia() || pilha.consultaTopo().equals(null)) {
                        System.out.println("Carro de placa: " + placa + " não encontrado");
                        while (!pilhaAux.estaVazia()) {
                            pilha.push(new Carro(pilhaAux.consultaTopo().getPlaca(), pilhaAux.consultaTopo().getHorarioEntrada()));
                            pilhaAux.pop();
                        }
                        break;
                    }
                    if (pilha.consultaTopo().getPlaca().equals(placa)) {
                        System.out.println("Carro com a placa " + pilha.consultaTopo().getPlaca() + " encontrado!");
                        System.out.println("Posição do carro na pilha: " + pos);
                        System.out.println("Horário de entrada do carro: " + pilha.consultaTopo().getHorarioEntrada());
                        while (!pilhaAux.estaVazia()) {
                            pilha.push(new Carro(pilhaAux.consultaTopo().getPlaca(), pilhaAux.consultaTopo().getHorarioEntrada()));
                            pilhaAux.pop();
                        }
                        break;
                    }
                    pilhaAux.push(new Carro(pilha.consultaTopo().getPlaca(), pilha.consultaTopo().getHorarioEntrada()));
                    pilha.pop();
                    pos++;
                }
            }
        
            private void verificarCapacidade() {
                System.out.println("Capacidade atual: " + quantidade + "/" + CAPACIDADE);
            }
        }

        Estacionamento estacionamento = new Estacionamento();
        estacionamento.rodarEstacionamento();
    }
}
