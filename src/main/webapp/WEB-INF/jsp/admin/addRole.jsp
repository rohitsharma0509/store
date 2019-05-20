<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.app.ecom.store.constants.RequestUrls"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String action = "Save";
%>
<ol class="breadcrumb">
  <li class="breadcrumb-item"><a href="${contextPath}/admin"><spring:message code="Admin" text="Admin" /></a></li>
  <li class="breadcrumb-item"><a href="${contextPath}<%=RequestUrls.ROLES %>"><spring:message code="Roles" text="Roles" /></a></li>
  <c:choose>
    <c:when test="${empty role.id}">
      <li class="breadcrumb-item active"><spring:message code="Add Product" text="Add Role" /></li>
    </c:when>
    <c:otherwise>
      <li class="breadcrumb-item active"><spring:message code="Edit Product" text="Edit Role" /></li>
      <% action = "Edit"; %>
    </c:otherwise>
  </c:choose>
</ol>
<div class="row" style="height:10px;">
</div>
<form:form method="POST" modelAttribute="role" class="form-horizontal" action="<%=RequestUrls.ROLES %>">
<form:hidden path="id"  class="form-control input-sm"/>
<div class="card">
  <div class="card-body">
    <div class="form-group row">
      <label for="name" class="col-sm-1"></label>
      <label for="name" class="col-sm-2 col-form-label">Role Name&nbsp;<span class="urgent_fields">*</span></label>
      <div class="col-sm-3">
            <form:input type="text" path="name" id="name" class="form-control"/>
            <form:errors path="name" class="help-inline has-error"></form:errors>
      </div>
      <label for="name" class="col-sm-6"></label>
    </div>
    <div class="form-group row">
    	<label for="name" class="col-sm-1"></label>
        <button type="submit" class="col-sm-1 btn btn-info"><%=action %></button>
    </div>
</div>
</div>
</form:form>
<div class="row" style="height: 100px;"></div>