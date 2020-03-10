<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../tags/header.jsp"%>



<%-- design communes voisines  --%>


<div class="container">
    <div class="row" style="color:white">
        <div class="col-lg-12">
            <h1>Liste des communes</h1>
            <div class="row">
                <form name="searchCodePostal" class="navbar-form navbar-left" action="/communes/recherche/" method="GET">
                    <div class="form-group">
                        <input name="codePostal" class="form-control" id="searchCodePostal" onblur="setTimeout(function () {$('#searchCodePostalProposition').hide();},500);" onKeyUp="searchKeyPress('CodePostal', this.value);" placeholder="Rechercher par code postal" type="text">
                    </div>
                    <div class="searchProposition" id="searchCodePostalProposition" style="display: none;">
                        <div id="CodePostal0" class="proposition" onClick="submitForm(this.innerText, 'searchCodePostal');"></div>
                        <div id="CodePostal1" class="proposition" onClick="submitForm(this.innerText, 'searchCodePostal');"></div>
                        <div id="CodePostal2" class="proposition" onClick="submitForm(this.innerText, 'searchCodePostal');"></div>
                        <div id="CodePostal3" class="proposition" onClick="submitForm(this.innerText, 'searchCodePostal');"></div>
                        <div id="CodePostal4" class="proposition" onClick="submitForm(this.innerText, 'searchCodePostal');"></div>
                    </div>
                    <button type="submit" class="btn btn-default">Rechercher</button>
                </form>

                <form name="searchCodeInsee" class="navbar-form navbar-right" action="/communes/recherche/" method="GET">
                    <div class="form-group" style="color:white">
                        <input name="codeInsee" class="form-control" id="searchCodeInsee" onblur="setTimeout(function () {$('#searchCodeInseeProposition').hide();},500);" onKeyUp="searchKeyPress('CodeInsee', this.value);" placeholder="Rechercher par code Insee" type="text">
                    </div>
                    <div class="searchProposition" id="searchCodeInseeProposition" style="display: none;">
                        <div id="CodeInsee0" class="proposition" onClick="submitForm(this.innerText, 'searchCodeInsee');"></div>
                        <div id="CodeInsee1" class="proposition" onClick="submitForm(this.innerText, 'searchCodeInsee');"></div>
                        <div id="CodeInsee2" class="proposition" onClick="submitForm(this.innerText, 'searchCodeInsee');"></div>
                        <div id="CodeInsee3" class="proposition" onClick="submitForm(this.innerText, 'searchCodeInsee');"></div>
                        <div id="CodeInsee4" class="proposition" onClick="submitForm(this.innerText, 'searchCodeInsee');"></div>
                    </div>
                    <button type="submit" class="btn btn-default">Rechercher</button>
                </form>
            </div>
            <table class="table table-hover table-striped" style="color:grey">
                <thead>
                <tr>
                    <th scope="col">
                        <c:if test="${!communeVoisine}">
                            <a href="/communes?page=${param.page}&size=${param.size}&sortProperty=nomCommune&sortDirection=${sortDirectionOpposite}">
                        </c:if>
                        Nom
                        <c:if test="${!communeVoisine}">
                            <span class="glyphicon glyphicon-chevron-up"></span>
                            </a>
                        </c:if>
                    </th>
                    <th scope="col">
                        <c:if test="${!communeVoisine}">
                            <a href="/communes?page=${param.page}&size=${param.size}&sortProperty=codePostal&sortDirection=${sortDirectionOpposite}">
                        </c:if>
                        Code Postal
                        <c:if test="${!communeVoisine}">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                            </a>
                        </c:if>
                    </th>
                    <th scope="col">
                        <c:if test="${!communeVoisine}">
                            <a href="/communes?page=${param.page}&size=${param.size}&sortProperty=codeInsee&sortDirection=${sortDirectionOpposite}">
                        </c:if>
                        Code INSEE
                        <c:if test="${!communeVoisine}">
                            <span class="glyphicon glyphicon-chevron-down"></span>
                            </a>
                        </c:if>
                    </th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="communeItem" begin="0" end="${communesList.getPageable().getPageSize()}" items="${communesList.getContent()}">
                    <tr>
                        <th scope="row">${empty communeItem.nomCommune ? "" : communeItem.nomCommune}</th>
                        <td>${empty communeItem.codePostal ? "" : communeItem.codePostal}</td>
                        <td>${empty communeItem.codeInsee ? "" : communeItem.codeInsee}</td>
                        <td><a href="/communes/${communeItem.id}">Détail</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="row">
                <c:if test="${!communeVoisine}">
                <div class="col-lg-6">
                    <p>Affichage des communes ${communesList.number*communesList.size + 1} à ${communesList.number*communesList.size+communesList.content.size()} sur un total de ${communesList.totalElements}</p>
                </div>
                    <div class="col-lg-6">
                        <ul class="pagination">
                            <c:if test="${!communesList.isFirst()}">
                                <li><a href="/communes?page=${param.page-1}&size=${param.size}&sortProperty=${param.sortProperty}&sortDirection=${param.sortDirection}">&laquo;</a></li>
                            </c:if>
                            <li><a href="#">Page ${param.page+1}</a></li>
                            <c:if test="${!communesList.isLast()}">
                                <li><a href="/communes?page=${param.page+1}&size=${param.size}&sortProperty=${param.sortProperty}&sortDirection=${param.sortDirection}">&raquo;</a></li>
                            </c:if>
                        </ul>
                    </div>
                </c:if>

                <c:if test="${communeVoisine}">
                    <div>
                        <label class="form-control-label">Position géographique de la commune ${commune.nomCommune} :</label>
                        <iframe class="center-block" width="650" height="450" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://www.openstreetmap.org/export/embed.html?bbox=${commune.longitude-0.10}%2C${commune.latitude-0.5}%2C${commune.longitude+0.10}%2C${commune.latitude+0.5}&amp;layer=mapnik&amp;marker=${commune.latitude}%2C${commune.longitude}" style="border: 1px solid black"></iframe>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<%@ include file="../tags/footer.jsp"%>
