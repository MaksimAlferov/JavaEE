<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Управление группами студентов</title>
    </head>

    <body>
        <form action="<c:url value="/main"/>" method="POST">
            <table>
                <tr>
                    <td>Количество студентов:<input type="text" name="count" value="${form.count}"/><br/></td>
                    <td>Список кураторов:
                        <select name="curatorId">
                            <c:forEach var="group" items="${form.curators}">
                                <c:choose>
                                    <c:when test="${curator.curatorId==form.curatorId}">
                                        <option value="${curator.curatorId}" selected><c:out value="${curator.nameCurator}"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${curator.curatorId}"><c:out value="${curator.nameCurator}"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="submit" name="getList" value="Обновить"/></td>
                </tr>
            </table>

            <p/>
            <b>Список групп для выбранных параметров:<b>
            <br/>
            <table>
                <tr>
                    <th>&nbsp;</th>
                    <th>Наименование группы</th>
                    <th>Куратор группы</th>
                    <th>Специальность</th>
                </tr>
                <c:forEach var="group" items="${form.groups}">
                <tr>
                    <td><input type="radio" name="groupId" value="${group.groupId}"></td>
                    <td><c:out value="${group.groupName}"/></td>
                    <td><c:out value="${group.curator}"/></td>
                    <td><c:out value="${group.speciality}"/></td>
                </tr>
                </c:forEach>
            </table>
                
            <table>
                <tr>
                    <td><input type="submit" value="Add" name="Add"/></td>
                    <td><input type="submit" value="Edit" name="Edit"/></td>
                    <td><input type="submit" value="Delete" name="Delete"/></td>
                </tr>
            </table>

            <p/>
            <b>Изменить куратора группы<b>
            <br/>
            <table>
                <tr>
                   <td>Количество студентов:<input type="text" name="newCount" value="${form.count}"/><br/></td>
                    <td>Список групп:
                        <select name="newCuratorId">
                        <c:forEach var="curator" items="${form.curator}">
                            <option value="${curator.curatorId}"><c:out value="${curator.nameCurator}"/></option>
                        </c:forEach>
                        </select>
                    </td>
                    <td><input type="submit" name="MoveCurator" value="Присвоить"/></td>
                </tr>
            </table>
        </form>
    </body>

</html>