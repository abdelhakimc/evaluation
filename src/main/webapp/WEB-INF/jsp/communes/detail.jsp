<%@ page import="com.IPILYON.eval_app.model.Commune" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../tags/header.jsp"%>


<%-- design informations communes --%>


<div class="container">

    <h2>Détail de la commune ${commune.nomCommune}</h2>
    <div class="row" style="color:white">
        <form class="col-lg-6" id="saveForm" action="/communes/save/" method="post">
            <div>
                <div class="form-group">
                    <input type="hidden" value="${commune.id}" class="form-control" name="id" id="id">
                    <label class="form-control-label" for="nomCommune">Nom :</label>
                    <input type="text" value="${commune.nomCommune}" class="form-control" name="nomCommune" id="nomCommune">

                    <label class="form-control-label" for="codePostal">Code postal :</label>
                    <input type="text" value="${commune.codePostal}" class="form-control" name="codePostal" id="codePostal">

                    <label class="form-control-label" for="codeInsee">Code Insee :</label>
                    <input type="text" value="${commune.codeInsee}" class="form-control" name="codeInsee" id="codeInsee">

                    <label class="form-control-label" for="latitude">Latitude :</label>
                    <input type="text" value="${commune.latitude}" class="form-control" name="latitude" id="latitude">

                    <label class="form-control-label" for="longitude">Longitude :</label>
                    <input type="text" value="${commune.longitude}" class="form-control" name="longitude" id="longitude">
                </div>
            </div>
            <div>
                <input form="saveForm" class="btn btn-primary" type="submit" value="Enregistrer"/>
                <a href="/communes/${commune.id}/delete" class="btn btn-danger">Supprimer</a>
            </div>
        </form>
        <div>
            <label class="form-control-label">Position géographique :</label>
            <iframe class="col-lg-6" width="650" height="450" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://www.openstreetmap.org/export/embed.html?bbox=${commune.longitude-0.10}%2C${commune.latitude-0.5}%2C${commune.longitude+0.10}%2C${commune.latitude+0.5}&amp;layer=mapnik&amp;marker=${commune.latitude}%2C${commune.longitude}" style="border: 1px solid black"></iframe>
        </div>
        <form id="communeVoisine" action="/communes/voisine/" method="get">
            <input type="hidden" value="${commune.id}" name="id">
            <label class="form-control-label" for="rayon" >Recherche les communes voisines :</label>
            <input type="text" placeholder="Rayon de recherche (en km)" class="form-control col-lg-3" id="rayon" name="rayon" required>
            <input form="communeVoisine" class="btn btn-info col-lg-4" type="submit" value="Rechercher les communes voisine" style="background-color: green; border:none" >
        </form>
        </div>
    </div>
</div>
<%@ include file="../tags/footer.jsp"%>
