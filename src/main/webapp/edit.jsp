<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit book</title>
</head>
<body>
<h3>Edit book</h3>
<form method="post" >
    <label>BookName</label><br>
    <input type="hidden" name="id" value="${book.id}">
    <input type="text" name="bookname" value="${book.bookName}" /><br><br>
    <label>Author</label><br>
    <input type="text" name="author" value="${book.author}"/><br><br>
    <input type="submit" value="Save" />
</form>
</body>
</html>