<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Books</title>
</head>
<body>
<h2>Books List</h2>
<p><a href='<c:url value="/add" />'>Add new</a></p>
<table>
    <tr><th>Id</th><th>BookName</th><th>Author</th><th></th></tr>
    <c:forEach var="book" items="${books}">
        <tr>
            <td>${book.id}</td>
            <td>${book.bookName}</td>
            <td>${book.author}</td>
            <td>
                <a href='<c:url value="/edit?id=${book.id}" />'>Edit</a>
                <form method="post" action='<c:url value="/delete" />' style="display:inline;">
                    <input type="hidden" name="id" value="${book.id}">
                    <input type="submit" value="Delete">
                </form>
            </td>

        </tr>
    </c:forEach>
</table>
</body>
</html>