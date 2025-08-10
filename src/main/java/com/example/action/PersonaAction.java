package com.example.action;

import com.example.dao.PersonaDAO;
import com.example.model.Persona;
import com.opensymphony.xwork2.ActionSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PersonaAction extends ActionSupport {

    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
    private PersonaDAO dao = new PersonaDAO();

    // Campos del formulario
    //hola
    private Integer idPersona;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String correo;
    private String fechaRegistroStr;

    // Para la vista
    private List<Persona> personas;

    public String list() {
        personas = dao.listar();
        return SUCCESS;
    }

    public String createInput() {
        return INPUT;
    }

    public String create() {
        try {
            Persona p = new Persona();
            p.setIdPersona(idPersona);
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setEdad(edad);
            p.setCorreo(correo);
            p.setFechaRegistro(DF.parse(fechaRegistroStr));
            dao.insertar(p);
            addActionMessage("Insertado correctamente");
            return SUCCESS;
        } catch (ParseException e) {
            addFieldError("fechaRegistroStr", "Formato de fecha inválido (yyyy-MM-dd)");
            return INPUT;
        } catch (Exception e) {
            addActionError(e.getMessage());
            return INPUT;
        }
    }

    public String editInput() {
        if (idPersona == null) {
            addActionError("ID requerido");
            return INPUT;
        }
        Persona p = dao.buscarPorId(idPersona);
        if (p == null) {
            addActionError("No encontrado");
            return INPUT;
        }
        this.nombre = p.getNombre();
        this.apellido = p.getApellido();
        this.edad = p.getEdad();
        this.correo = p.getCorreo();
        this.fechaRegistroStr = DF.format(p.getFechaRegistro());
        return INPUT;
    }

    public String update() {
        try {
            Persona p = new Persona();
            p.setIdPersona(idPersona);
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setEdad(edad);
            p.setCorreo(correo);
            p.setFechaRegistro(DF.parse(fechaRegistroStr));
            dao.actualizar(p);
            addActionMessage("Actualizado correctamente");
            return SUCCESS;
        } catch (ParseException e) {
            addFieldError("fechaRegistroStr", "Formato de fecha inválido (yyyy-MM-dd)");
            return INPUT;
        } catch (Exception e) {
            addActionError(e.getMessage());
            return INPUT;
        }
    }

    public String delete() {
        if (idPersona != null) {
            dao.eliminar(idPersona);
        }
        return SUCCESS;
    }
    // Getters y setters
    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getFechaRegistroStr() { return fechaRegistroStr; }
    public void setFechaRegistroStr(String fechaRegistroStr) { this.fechaRegistroStr = fechaRegistroStr; }

    public List<Persona> getPersonas() { return personas; }
    public void setPersonas(List<Persona> personas) { this.personas = personas; }

}
