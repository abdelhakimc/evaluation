<%@ page import="com.IPILYON.eval_app.repository.CommuneRepository" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="tags/header.jsp" %>
<div class="container">
    <style>
        .jumbotron{
            padding-top: 10px;
            padding-bottom: 10px;
        }
    </style>
    <script>
        function refreshData(url,type){
            $.ajax({
                type: "GET",
                dataType: 'json',
                url: url,
                success: function (data,status,xhr) {
                    if(type === 'commune'){
                        $("#commune").html(data);
                    } else if (type === 'codePostal'){
                        $("#codePostal").html(data);
                    } else {
                        $("#codeInsee").html(data);
                    }
                },
                error: function () {
                    alert("Une erreur s'est produite, veuillez réessayer plus tard.");
                }
            });
        }
    </script>
    <div class="jumbotron center-block">
        <h2 class="center-block text-center">Bienvenue dans l'interface de gestion des communes !</h2>
    </div>

    <div class="col-md-12" style="background-color: white;">
        <img src="https://upload.wikimedia.org/wikipedia/commons/0/03/Communes_of_France.png" class="img-responsive center-block card-img-top" alt="Image commune de France" width="450px">
    </div>
    <div></div>
    <div class="jumbotron">
        <p><a onclick="refreshData('/api/communes/count/commune','commune');" id="nbCommunes" title="Rafraîchir">${nbCommunes}</a> communes sont enregistrées actuellement.</p>
        <p>Les communes sont répertoriés dans <a onclick="refreshData('/api/communes/count/codePostal','codePostal');" id="codePostal" title="Rafraîchir">${nbCodePostal}</a> code postal ansi que dans <a onclick="refreshData('/api/communes/count/codeInsee', 'codeInsee');" id="codeInsee" title="Rafraîchir">${nbCodeInsee}</a> code INSEE.</p>
    </div>
</div>
<%@ include file="tags/footer.jsp" %>

