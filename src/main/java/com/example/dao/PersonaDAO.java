package com.example.dao;

import com.example.model.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    public void insertar(Persona p) {
        String sql = "{call PKG_PERSONA.SP_INSERTAR_PERSONA(?,?,?,?,?)}";
        try (Connection cn = OracleConnectionFactory.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cn.setAutoCommit(false);

            cs.setString(1, p.getNombre());
            cs.setString(2, p.getApellido());
            if (p.getEdad() != null) cs.setInt(3, p.getEdad()); else cs.setNull(4, Types.NUMERIC);
            cs.setString(4, p.getCorreo());
            if (p.getFechaRegistro() != null) cs.setDate(5, new java.sql.Date(p.getFechaRegistro().getTime()));
            else cs.setNull(5, Types.DATE);
            cs.execute();
            cn.commit();
        } catch (SQLException e) {

            throw new RuntimeException("Error al insertar persona", e);
        }
    }

    public void actualizar(Persona p) {
        if (p.getIdPersona() == null) {
            throw new IllegalArgumentException("El ID de la persona es requerido para actualizar.");
        }
        String sql = "{call PKG_PERSONA.SP_ACTUALIZAR_PERSONA(?,?,?,?,?,?)}";
        try (Connection cn = OracleConnectionFactory.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cn.setAutoCommit(false);
           // if (p.getIdPersona() != null) {
                cs.setInt(1, p.getIdPersona());
         //   } else {
          //      cs.setNull(1, Types.NUMERIC);
         //   }
            cs.setString(2, p.getNombre());
            cs.setString(3, p.getApellido());
            if (p.getEdad() != null) cs.setInt(4, p.getEdad()); else cs.setNull(4, Types.NUMERIC);
            cs.setString(5, p.getCorreo());
            if (p.getFechaRegistro() != null) cs.setDate(6, new java.sql.Date(p.getFechaRegistro().getTime()));
            else cs.setNull(6, Types.DATE);
            cs.execute();
            cn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar persona", e);
        }
    }

    public Persona buscarPorId(int id) {
        String sql = "{call PKG_PERSONA.SP_SELECCIONAR_PERSONA(?,?,?,?,?,?)}";
        try (Connection cn = OracleConnectionFactory.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cn.setAutoCommit(false);
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.registerOutParameter(4, Types.NUMERIC);
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.registerOutParameter(6, Types.DATE);
            cs.execute();
            String nombre = cs.getString(2);
            if (cs.wasNull()) return null; // no encontrado
            Persona p = new Persona();
            p.setIdPersona(id);
            p.setNombre(nombre);
            p.setApellido(cs.getString(3));
            int edad = cs.getInt(4);
            p.setEdad(cs.wasNull() ? null : edad);
            p.setCorreo(cs.getString(5));
            Date f = cs.getDate(6);
            p.setFechaRegistro(f);
            return p;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar persona", e);
        }
    }

    public void eliminar(int id) {
        String sql = "{call PKG_PERSONA.SP_ELIMINAR_PERSONA(?)}";

        try (Connection cn = OracleConnectionFactory.getConnection();
             CallableStatement cs = cn.prepareCall(sql)) {
            cn.setAutoCommit(false);
            cs.setInt(1, id);
            cs.execute();
            cn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar persona", e);
        }
    }

    public List<Persona> listar() {
        // Para listado general usamos SELECT directo
        String sql = "SELECT ID_PERSONA, NOMBRE, APELLIDO, EDAD, CORREO, FECHA_REGISTRO FROM PERSONA ORDER BY ID_PERSONA";
        try (Connection cn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Persona> out = new ArrayList<>();
            while (rs.next()) {
                Persona p = new Persona();
                p.setIdPersona(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setApellido(rs.getString(3));
                int edad = rs.getInt(4);
                p.setEdad(rs.wasNull() ? null : edad);
                p.setCorreo(rs.getString(5));
                p.setFechaRegistro(rs.getDate(6));
                out.add(p);
            }
            return out;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar personas", e);
        }
    }
}
