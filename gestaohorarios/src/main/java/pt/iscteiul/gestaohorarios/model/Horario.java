package pt.iscteiul.gestaohorarios.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe que representa uma linha(CSV) ou documento(JSON) de um horário.
 */

public class Horario {
    
    private String curso;
    private String unidadeCurricular;
    private String turno;
    private String turma;
    private int inscritosNoTurno;
    private String diaDaSemana;
    private LocalTime horaInicioAula;
    private LocalTime horaFimAula;
    private LocalDate dataDaAula;
    private String salaAtribuida;
    private int lotacaoDaSala;

    public Horario() {}
    
    public Horario(String curso, String unidadeCurricular, String turno, String turma, int inscritosNoTurno, String diaDaSemana, LocalTime horaInicioAula, LocalTime horaFimAula, LocalDate dataDaAula, String salaAtribuida, int lotacaoDaSala) {
        this.curso = curso;
        this.unidadeCurricular = unidadeCurricular;
        this.turno = turno;
        this.turma = turma;
        this.inscritosNoTurno = inscritosNoTurno;
        this.diaDaSemana = diaDaSemana;
        this.horaInicioAula = horaInicioAula;
        this.horaFimAula = horaFimAula;
        this.dataDaAula = dataDaAula;
        this.salaAtribuida = salaAtribuida;
        this.lotacaoDaSala = lotacaoDaSala;
    }

    /**
     * Devolve o curso de uma linha/documento do horário.
     * @return o curso
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Define o curso de uma linha/documento do horário.
     * @param curso
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Devolve o curso de uma linha/documento do horário.
     * @return o curso
     */
    public String getUnidadeCurricular() {
        return unidadeCurricular;
    }

    public void setUnidadeCurricular(String unidadeCurricular) {
        this.unidadeCurricular = unidadeCurricular;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public int getInscritosNoTurno() {
        return inscritosNoTurno;
    }

    public void setInscritosNoTurno(int inscritosNoTurno) {
        this.inscritosNoTurno = inscritosNoTurno;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public LocalTime getHoraInicioAula() {
        return horaInicioAula;
    }

    public void setHoraInicioAula(LocalTime horaInicioAula) {
        this.horaInicioAula = horaInicioAula;
    }

    public LocalTime getHoraFimAula() {
        return horaFimAula;
    }

    public void setHoraFimAula(LocalTime horaFimAula) {
        this.horaFimAula = horaFimAula;
    }

    public LocalDate getDataDaAula() {
        return dataDaAula;
    }

    public void setDataDaAula(LocalDate dataDaAula) {
        this.dataDaAula = dataDaAula;
    }

    public String getSalaAtribuida() {
        return salaAtribuida;
    }

    public void setSalaAtribuida(String salaAtribuida) {
        this.salaAtribuida = salaAtribuida;
    }

    public int getLotacaoDaSala() {
        return lotacaoDaSala;
    }

    public void setLotacaoDaSala(int lotacaoDaSala) {
        this.lotacaoDaSala = lotacaoDaSala;
    }
}