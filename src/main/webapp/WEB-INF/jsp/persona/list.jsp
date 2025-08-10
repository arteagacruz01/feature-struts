<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
  <title>Personas</title>
  <link rel="stylesheet" href="<s:url value='/assets/css/style.css'/>">
</head>
<body>
  <h1>Personas</h1>
  <p><a href="<s:url action='persona-create-input'/>">Nueva Persona</a></p>
  <table border="1" cellpadding="6" cellspacing="0">
    <tr>
      <th>ID</th><th>Nombre</th><th>Apellido</th><th>Edad</th><th>Correo</th><th>Fecha Registro</th><th>Acciones</th>
    </tr>
    <s:iterator value="personas">
      <tr>

        <td><s:property value="idPersona"/></td>
        <td><s:property value="nombre"/></td>
        <td><s:property value="apellido"/></td>
        <td><s:property value="edad"/></td>
        <td><s:property value="correo"/></td>
        <td><s:date name="fechaRegistro" format="yyyy-MM-dd"/></td>
       <td>
         <s:url var="editUrl" action="persona-edit-input">
           <s:param name="idPersona" value="%{idPersona}"/>
         </s:url>
         <a href="${editUrl}">Editar</a>
         &nbsp;|&nbsp;
         <s:url var="delUrl" action="persona-delete">
           <s:param name="idPersona" value="%{idPersona}"/>
         </s:url>
         <a href="${delUrl}" onclick="return confirm('¿Eliminar?');">Eliminar</a>
       </td>
      </tr>
    </s:iterator>
  </table>
</body>
</html>
