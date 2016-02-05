<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
    <head>
        <title>Список групп</title>
    </head>

    <body>
        <form action="<c:url value="/edit"/>" method="POST">
            <input type="hidden" name="groupId" value="${group.groupId}"/>
            <table>
                <tr>
                    <td>Наименование группы:</td><td><input type="text" name="groupName" value="${group.groupName}"/></td>
                </tr>
                <tr>
                    <td>Прозвище куратора в группе</td><td><input type="text" name="curator" value="${group.curator}"/></td>
                </tr>
                <tr>
                    <td>Специальность</td><td><input type="text" name="speciality" value="${group.speciality}"/></td>
                </tr>
                <tr>
                    <td>Куратор:</td>
                    <td>
                        <select name="curatorId">
                        <c:forEach var="curator" items="${group.curators}">
                            <c:choose>
                                <c:when test="${curator.curatorId==group.curatorId}">
                                    <option value="${curator.curatorId}" selected><c:out value="${curator.nameCurator}"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${curator.curatorId}"><c:out value="${curator.nameCurator}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Количество студентов:</td><td><input type="text" name="studentCount" value="${group.studentCount}"/></td>
                </tr>
            </table>
            <table>
                <tr>
                    <td><input type="submit" value="OK" name="OK"/></td>
                    <td><input type="submit" value="Cancel" name="Cancel"/></td>
                </tr>
            </table>
        </form>
    </body>
</html>