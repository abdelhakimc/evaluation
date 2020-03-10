<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="fr" >

<%-- design header page --%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
</head>
<body>
<style>
    .search{
        position: absolute;
        background-color: white;
        border-radius: 3%;
        border: 1px solid grey;
        width: 174px;
        z-index: 999999;
    }
    .proposition{
        padding: 5px;

    }
    .proposition:hover{
        background-color: darkgrey;
    }
</style>
<script>


    function searchKeyPress(typeSearch, search){
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: '/api/communes/searchProposition?'+typeSearch+'='+search,
            success: function (data) {
                if(data.length > 0){
                    $("#search"+typeSearch+"Proposition").show();
                    $("#search"+typeSearch+"Proposition").width($(".form-group").width());
                    for (i=0; i<data.length; i++){
                        $('#'+typeSearch+i).html(data[i]);
                    }
                } else {
                    $("#search"+typeSearch+"Proposition").hide();
                }
            },
            error: function () {
                alert("Une erreur s'est produite, veuillez réessayer plus tard.");
            }
        });
    }

    function submitForm(selectedProposition, formName) {
        console.log("submitForm !");
        console.log(selectedProposition);
        console.log(formName);
        $("#"+formName).val(selectedProposition);
        $("#"+formName+"Proposition").hide();
        document.forms[formName].submit();
    }
</script>

<%-- design proposition communes --%>

<nav class="navbar navbar-default navbar-inverse" style="background: black;">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="/" class="navbar-brand" style="background: black">Menu Principal</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/communes?page=0&size=10&sortProperty=nomCommune&sortDirection=ASC" class="nav-link" style="background: black;">Listing des communes</a></li>
            </ul>

            <a role="button" class="btn btn-success navbar-btn" style="background: black;  border: none; font-size: 15px; " href="/communes/create">Créer une commune</a>
            <form name="searchNomCommune" class="navbar-form navbar-right" action="/communes/recherche/" method="GET">
                <div class="form-group">
                    <input name="nomCommune" class="form-control" id="searchNomCommune" onblur="setTimeout(function () {$('#searchNomCommuneProposition').hide();},500);" onKeyUp="searchKeyPress('NomCommune', this.value);" placeholder="Rechercher par nom" type="text">
                </div>
                <div class="searchProposition" id="searchNomCommuneProposition" style="display: none;">
                    <div id="NomCommune0" class="proposition" onclick="submitForm(this.innerText, 'searchNomCommune');"></div>
                    <div id="NomCommune1" class="proposition" onclick="submitForm(this.innerText, 'searchNomCommune');"></div>
                    <div id="NomCommune2" class="proposition" onclick="submitForm(this.innerText, 'searchNomCommune');"></div>
                    <div id="NomCommune3" class="proposition" onclick="submitForm(this.innerText, 'searchNomCommune');"></div>
                    <div id="NomCommune4" class="proposition" onClick="submitForm(this.innerText, 'searchNomCommune');"></div>
                </div>
                <button type="submit" class="btn btn-default">Rechercher</button>
            </form>
         </div>
    </div>
</nav>

    <% if(request.getParameter("success") != null) {
    pageContext.setAttribute("success", request.getParameter("success"));
}%>
    <% if (pageContext.findAttribute("success") != null){ %>
<div class="alert alert-success alert-dismissible" role="alert">
    <button onclick="window.location.replace(location.pathname);" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    ${success}
</div>
<% } %>