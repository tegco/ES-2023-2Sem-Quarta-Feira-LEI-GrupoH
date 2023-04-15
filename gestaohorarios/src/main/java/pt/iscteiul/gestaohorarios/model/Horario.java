/*
 * 
 */
package pt.iscteiul.gestaohorarios.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Classe que representa cada linha (de um ficheiro CSV) ou documento (de um ficheiro JSON) de um ficheiro.
 * Inclui informacao sobre o curso, unidade curricular, turno, turma, quantidade de alunos 
 * inscritos, dia da semana, hora de inicio da aula, hora de fim da aula, sala atribuida e
 * a capacidade da sala.
 * 
 * @author gabriel-bitrefill
 * @author tegco
 * 
 * @since 2023-4-11
 */


@JsonPropertyOrder({ "Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula", "Sala atribuída à aula", "Lotação da sala"})

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
	@JsonProperty("Sala atribuída à aula")
    private String salaAtribuida;
	@JsonProperty("Lotação da sala")
    private int lotacaoDaSala;

    /**
     * Construtor da classe Horario, por default
     */
    public Horario() {}
    
    /**
     * Cria um objeto Horario com todos os parametros submetidos
     *
     * @param curso o nome do curso
     * @param unidadeCurricular o nome da unidade curricular
     * @param turno o nome do turno
     * @param turma o nome da turma
     * @param inscritosNoTurno o numero de inscritos no turno
     * @param diaDaSemana o dia da semana
     * @param horaInicioAula a hora de inicio da aula
     * @param horaFimAula a hora de fim da aula
     * @param dataDaAula a data da aula
     * @param salaAtribuida a sala atribuida 
     * @param lotacaoDaSala a capacidade da sala
     */
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
     * Devolve o curso de uma linha/documento.
     * @return o nome do curso
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Define o curso de uma linha/documento.
     * @param curso o nome do curso
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Devolve a unidade curricular de uma linha/documento.
     * @return o nome da unidade curricular
     */
    public String getUnidadeCurricular() {
        return unidadeCurricular;
    }
    
    /**
     * Define a unidade curricular de uma linha/documento.
     * @param unidadeCurricular o nome da unidade curricular
     */
    public void setUnidadeCurricular(String unidadeCurricular) {
        this.unidadeCurricular = unidadeCurricular;
    }
    
    /**
     * Devolve o turno de uma linha/documento.
     * @return o nome do turno
     */
    public String getTurno() {
        return turno;
    }
    
    /**
     * Define o turno de uma linha/documento.
     * @param  turno o nome do turno
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    /**
     * Devolve a turma de uma linha/documento.
     * @return o nome da turma
     */
    public String getTurma() {
        return turma;
    }

    /**
     * Define a turma de uma linha/documento.
     * @param turma o nome da turma
     */
    public void setTurma(String turma) {
        this.turma = turma;
    }

    /**
     * Devolve o numero de alunos inscritos no turno de uma linha/documento.
     * @return o numero de inscritos no turno
     */
    public int getInscritosNoTurno() {
        return inscritosNoTurno;
    }
    
    /**
     * Define o numero de alunos inscritos no turno de uma linha/documento.
     * @param inscritosNoTurno o numero de inscritos no turno
     */
    public void setInscritosNoTurno(int inscritosNoTurno) {
        this.inscritosNoTurno = inscritosNoTurno;
    }

   /**
    * Devolve o dia da semana de uma linha/documento.
    * @return o dia da semana
    */
    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    /**
     * Define o dia da semana de uma linha/documento.
     * @param diaDaSemana o dia da semana
     */
    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    /**
     * Devolve a hora de inicio da aula de uma linha/documento.
     * @return a hora de inicio da aula
     */
    public LocalTime getHoraInicioAula() {
        return horaInicioAula;
    }

    /**
     * Define a hora de inicio da aula de uma linha/documento
     * @param horaInicioAula hora de inicio da aula
     */
    public void setHoraInicioAula(LocalTime horaInicioAula) {
        this.horaInicioAula = horaInicioAula;
    }

    /**
     * Devolve a hora de fim da aula de uma linha/documento.
     * @return a hora de fim da aula
     */
    public LocalTime getHoraFimAula() {
        return horaFimAula;
    }

    /**
     * Define a hora de fim da aula de uma linha/documento.
     * @param horaFimAula a hora de fim da aula
     */
    public void setHoraFimAula(LocalTime horaFimAula) {
        this.horaFimAula = horaFimAula;
    }

    /**
     * Devolve a data da aula de uma linha/documento.
     * @return a data da aula
     */
    public LocalDate getDataDaAula() {
        return dataDaAula;
    }

    /**
     * Define a data da aula de uma linha/documento.
     * @param dataDaAula a data da aula
     */
    public void setDataDaAula(LocalDate dataDaAula) {
        this.dataDaAula = dataDaAula;
    }

    /**
     * Devolve a sala atribuida de uma linha/documento.
     * @return a sala atribuida
     */
    public String getSalaAtribuida() {
        return salaAtribuida;
    }

    /**
     * Define a sala atribuida de uma linha/documento.
     * @param salaAtribuida a sala atribuida
     */
    public void setSalaAtribuida(String salaAtribuida) {
        this.salaAtribuida = salaAtribuida;
    }

    /**
     * Devolve a lotacao da sala de uma linha/documento.
     * @return a lotacao da sala
     */
    public int getLotacaoDaSala() {
        return lotacaoDaSala;
    }

    /**
     * Define a lotacao da sala de uma linha/documento.
     * @param lotacaoDaSala a lotacao da sala
     */
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