<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="tags/header.jsp"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />

<div class="alert alert-danger alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    ${exception.message}
</div>

<%@ include file="tags/footer.jsp"%>