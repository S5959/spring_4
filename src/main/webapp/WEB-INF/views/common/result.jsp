<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Result</title>
</head>
<body>
<script type="text/javascript">
	var result = '${msg}';
	var path = '${path}';
	alert(result);
	// /notice/noticeList
	// /common/result.jsp
	location.href=path;
</script>

</body>
</html>