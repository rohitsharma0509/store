<%@page import="com.app.ecom.store.constants.FieldNames"%>
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
<form:form method="POST" modelAttribute="<%=FieldNames.PRODUCT_CATEGORY_DTO %>" class="form-horizontal" action="<%=RequestUrls.CATEGORIES %>">
<form:hidden path="id"  class="form-control input-sm"/>
<div class="container py-5">
    <h6><spring:message code="Category Details" text="Category Details" /></h6><hr>
    <div class="row">
        <div class="col-md-10 mx-auto">
          <div class="form-group row">
              <div class="col-sm-6">
                  <label for="name"><spring:message code="Category Name" text="Category Name" />&nbsp;<span class="urgent_fields">*</span></label>
                  <form:input type="text" path="name" id="name" class="form-control input-sm"/>
                  <form:errors path="name" class="help-inline has-error"></form:errors>
              </div>
              <div class="col-sm-6"></div>
          </div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-sm-2"><button type="submit" class="btn btn-success"><spring:message code="<%=action %>" text="<%=action %>" /></button></div>
        <div class="col-sm-10"></div>
    </div>
</div>
</form:form>