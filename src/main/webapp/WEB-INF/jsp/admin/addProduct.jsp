<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.app.ecom.store.constants.RequestUrls"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%
String action = "Save";
%>
<ol class="breadcrumb">
  <li class="breadcrumb-item"><a href="${contextPath}/admin"><spring:message code="Admin" text="Admin" /></a></li>
  <li class="breadcrumb-item"><a href="${contextPath}<%=RequestUrls.PRODUCTS %>"><spring:message code="Products" text="Products" /></a></li>
  <c:choose>
    <c:when test="${empty productDto.id}">
      <li class="breadcrumb-item active"><spring:message code="Add Product" text="Add Product" /></li>
    </c:when>
    <c:otherwise>
      <li class="breadcrumb-item active"><spring:message code="Edit Product" text="Edit Product" /></li>
      <% action = "Edit"; %>
    </c:otherwise>
  </c:choose>
</ol>
<div class="row" style="height:10px;">
</div>
<form:form method="POST" modelAttribute="productDto" class="form-horizontal" enctype="multipart/form-data" action="<%=RequestUrls.PRODUCTS %>">
<form:hidden path="id"  class="form-control input-sm"/>





<div class="row" style="background-color: wheat;">
	<div class="col-12 mx-auto d-flex flex-column flex-md-row py-5 py-md-7 position-relative">
	  <div class="col-12 col-md-6 pr-3 pr-md-0 pl-3 pl-md-0 mb-6 mb-md-0">
	    <div class="hide-sm">
	      <div class="home-hero-formWrapper pl-md-4 pr-md-7">
	        <dl class="form-group mt-0">
	          <dt class="input-label">
	            <label class="form-label h5 text-black" for="categoryId">Category&nbsp;<span class="urgent_fields">*</span></label>
	          </dt>
	          <dd>
		          <form:select path="categoryId" id="categoryId" class="form-control form-control-lg input-block">
		            <form:option value="-1">Select Category</form:option>
		            <c:forEach var="category" items="${categories}">
		             <form:option value="${category.id}">${category.name}</form:option>
	              </c:forEach>
	            </form:select>
	            <form:errors path="categoryId" class="help-inline has-error"></form:errors>
	          </dd>
	        </dl>
	        <dl class="form-group">
	          <dt class="input-label">
	            <label class="form-label h5 text-black" for="name">Name&nbsp;<span class="urgent_fields">*</span></label>
	          </dt>
	          <dd>
	            <form:input type="text" path="name" id="name" class="form-control form-control-lg input-block" autocomplete="off"/>
              <form:errors path="name" class="help-inline has-error"></form:errors>
	          </dd>
	        </dl>
	        <dl class="form-group">
            <dt class="input-label">
              <label class="form-label h5 text-black" for="purchasePrice">Purchase Price&nbsp;<span class="urgent_fields">*</span></label>
            </dt>
            <dd>
              <form:input type="text" path="purchasePrice" id="purchasePrice" class="form-control form-control-lg input-block" autocomplete="off"/>
              <form:errors path="purchasePrice" class="help-inline has-error"></form:errors>
            </dd>
          </dl>
          <dl class="form-group">
            <dt class="input-label">
              <label class="form-label h5 text-black" for="quantity">Quantity&nbsp;<span class="urgent_fields">*</span></label>
            </dt>
            <dd>
              <form:input type="text" path="quantity" id="quantity" class="form-control form-control-lg input-block" autocomplete="off"/>
              <form:errors path="quantity" class="help-inline has-error"></form:errors>
            </dd>
          </dl>
	        <button class="btn btn-success f4 btn-block mb-3" type="submit"><%=action %></button>
	      </div>
	    </div>
	  </div>
	  <div class="col-12 col-md-6 pr-3 pr-md-0 pl-3 pl-md-0 mb-6 mb-md-0">
	    <div class="hide-sm">
	      <div class="home-hero-formWrapper pl-md-4 pr-md-7">
	        <dl class="form-group">
            <dt class="input-label">
              <label class="form-label h5 text-black" for="brandName">Brand Name&nbsp;<span class="urgent_fields">*</span></label>
            </dt>
            <dd>
              <form:input type="text" path="brandName" id="brandName" class="form-control form-control-lg input-block" autocomplete="off"/>
              <form:errors path="brandName" class="help-inline has-error"></form:errors>
            </dd>
          </dl>
	        <dl class="form-group">
	          <dt class="input-label">
	            <label class="form-label h5 text-black" for="image">Image</label>
	          </dt>
	          <dd>
	            <form:input type="file" path="image" id="image" class="form-control form-control-lg input-block"/>
	          </dd>
	        </dl>
	        <dl class="form-group">
	          <dt class="input-label">
	            <label class="form-label h5 text-black" for="perProductPrice">Sell Price&nbsp;<span class="urgent_fields">*</span></label>
	          </dt>
	          <dd>
	            <form:input type="text" path="perProductPrice" id="perProductPrice" class="form-control form-control-lg input-block"/>
              <form:errors path="perProductPrice" class="help-inline has-error"></form:errors>
	          </dd>
	        </dl>
	        <dl class="form-group">
            <dt class="input-label">
              <label class="form-label h5 text-black" for="alertQuantity">Alert Quantity&nbsp;<span class="urgent_fields">*</span></label>
            </dt>
            <dd>
              <form:input type="text" path="alertQuantity" id="alertQuantity" class="form-control form-control-lg input-block"/>
              <form:errors path="alertQuantity" class="help-inline has-error"></form:errors>
            </dd>
          </dl>
	      </div>
	    </div>
	  </div>
	</div>
</div>
</form:form>