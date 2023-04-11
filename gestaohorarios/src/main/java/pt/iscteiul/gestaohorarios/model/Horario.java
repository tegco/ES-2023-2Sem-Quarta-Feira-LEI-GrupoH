package pt.iscteiul.gestaohorarios.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana", "Hora início da aula","Hora fim da aula","Data da aula", "Sala atribuída", "Lotação da sala"})
public class Horario {
    
	@JsonProperty("Curso")
    private String curso;
	@JsonProperty("Unidade Curricular")
    private String unidadeCurricular;
	@JsonProperty("Turno")
    private String turno;
	@JsonProperty("Turma")
    private String turma;
	@JsonProperty("Inscritos no turno")
    private int inscritosNoTurno;
	@JsonProperty("Dia da semana")
    private String diaDaSemana;
	@JsonProperty("Hora início da aula") @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horaInicioAula;
	@JsonProperty("Hora fim da aula") @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horaFimAula;
	@JsonProperty("Data da aula")
	@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDaAula;
	@JsonProperty("Sala atribuída")
    private String salaAtribuida;
	@JsonProperty("Lotação da sala")
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

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

	@Override
	public String toString() {
		return "Horario [curso=" + curso + ", unidadeCurricular=" + unidadeCurricular + ", turno=" + turno + ", turma="
				+ turma + ", inscritosNoTurno=" + inscritosNoTurno + ", diaDaSemana=" + diaDaSemana
				+ ", horaInicioAula=" + horaInicioAula + ", horaFimAula=" + horaFimAula + ", dataDaAula=" + dataDaAula
				+ ", salaAtribuida=" + salaAtribuida + ", lotacaoDaSala=" + lotacaoDaSala + "]";
	}
    
    
}