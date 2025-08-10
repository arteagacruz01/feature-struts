<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
  <title>Persona</title>
  <link rel="stylesheet" href="<s:url value='/assets/css/style.css'/>">
</head>
<body>
  <h1><s:if test="idPersona == null">Nueva Persona</s:if><s:else>Editar Persona</s:else></h1>
  <s:form action="%{idPersona == null ? 'persona-create' : 'persona-update'}" method="post">

    <s:if test="idPersona != null">
      <s:hidden name="idPersona"/>
    </s:if>
    <s:textfield name="nombre" label="Nombre" requiredLabel="true"/>
    <s:textfield name="apellido" label="Apellido" requiredLabel="true"/>
    <s:textfield name="edad" label="Edad" requiredLabel="true"/>
    <s:textfield name="correo" label="Correo" requiredLabel="true"/>
    <s:textfield name="fechaRegistroStr" label="Fecha Registro (yyyy-MM-dd)" requiredLabel="true"/>
    <s:submit value="Guardar"/>
    <s:a action="persona">Cancelar</s:a>
  </s:form>
  <s:if test="hasActionErrors()">
    <div class="errors"><s:actionerror/></div>
  </s:if>
  <s:if test="hasFieldErrors()">
    <div class="errors"><s:fielderror/></div>
  </s:if>
</body>
</html>
