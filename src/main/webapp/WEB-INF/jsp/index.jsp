<%@ page import="com.IPILYON.eval_app.repository.CommuneRepository" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="tags/header.jsp" %>



<%-- design page principal --%>

<div class="container">
    <style>

        .container{
            background-color: black;
            color: white;
        }
        .jumbotron{
            padding-top: 20px;
            padding-bottom: 20px;
        }

        <%-- je me suis fait plaisir :) --%>
        .col-md-6 {
            -webkit-filter: blur(3px);
            filter: blur(3px);
            -webkit-transition: .3s ease-in-out;
            transition: .3s ease-in-out;
        }
        .col-md-6:hover {
            -webkit-filter: blur(0);
            filter: blur(0);
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
                /*error: function () {
                    alert("Une erreur s'est produite, veuillez réessayer plus tard.");
                }*/
            });
        }
    </script>

    <div class="jumbotron center-block" text-align="center" ; style="background-color: #2b282b;">
        <h2 class="center-block text-center">Bienvenue !</h2>
        <h2 class="center-block text-center" style="font-size: 20px">Choisissez de visualiser la liste des communes ou de créez votre commune : </h2>
    </div>

    <div class="col-md-6" style="background-color: black;">
        <img src="https://upload.wikimedia.org/wikipedia/commons/3/38/French_Academies_Zone.png" class="img-responsive center-block card-img-top" alt="Image commune de France" width="350px">
    </div>

    <div class="jumbotron" style="background-color: black;" >
        <p> Nombres communes actuelles en France: <a onclick="refreshData('/api/communes/count/commune','commune');" id="nbCommunes" title="Rafraîchir" style="color: green">${nbCommunes}</a></p>
    </div>
    <a role="button" class="btn btn-success navbar-btn" style="background: green;  border: none; font-size: 15px; " href="/communes/create">Créer une commune</a>
    </div>


<%@ include file="tags/footer.jsp" %>

