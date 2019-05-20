<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.app.ecom.store.constants.RequestUrls"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%
String action = "Save";
%>
<ol class="breadcrumb">
  <li class="breadcrumb-item"><a href="${contextPath}/admin"><spring:message code="Admin" text="Admin" /></a></li>
  <li class="breadcrumb-item"><a href="${contextPath}<%=RequestUrls.CATEGORIES %>"><spring:message code="Categories" text="Categories" /></a></li>
  <c:choose>
    <c:when test="${empty productCategory.id}">
      <li class="breadcrumb-item active"><spring:message code="Add Product" text="Add Category" /></li>
    </c:when>
    <c:otherwise>
      <li class="breadcrumb-item active"><spring:message code="Edit Product" text="Edit Category" /></li>
      <% action = "Edit"; %>
    </c:otherwise>
  </c:choose>
</ol>
<div class="row" style="height:10px;">
</div>
<form:form method="POST" modelAttribute="productCategory" class="form-horizontal" action="<%=RequestUrls.CATEGORIES %>">
<form:hidden path="id"  class="form-control input-sm"/>
<div class="card">
  <div class="card-body">
    <div class="form-group row">
      <label for="name" class="col-sm-1"></label>
      <label for="name" class="col-sm-2 col-form-label">Category Name&nbsp;<span class="urgent_fields">*</span></label>
      <div class="col-sm-3">
            <form:input type="text" path="name" id="name" class="form-control"/>
            <form:errors path="name" class="help-inline has-error"></form:errors>
      </div>
      <label for="name" class="col-sm-6"></label>
    </div>
    <div class="form-group row">
    	<label for="name" class="col-sm-1"></label>
        <button type="submit" class="col-sm-1 btn btn-sm btn-success"><%=action %></button>
    </div>
</div>
</div>
</form:form>
<div class="row" style="height: 100px;"></div>