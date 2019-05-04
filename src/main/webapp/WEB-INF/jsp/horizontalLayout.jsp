<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
</head>

<body>
	<table width="100%">
		<tr>
		  <td width="5%"></td>
			<td><tiles:insertAttribute name="header" /></td>
			<td width="5%"></td>
		</tr>
		<tr>
		  <td width="5%"></td>
		  <td nowrap="nowrap"><tiles:insertAttribute name="menu" /></td>
		  <td width="5%"></td>
		</tr>
		<tr>
		  <td width="5%"></td>
			<td><tiles:insertAttribute name="body" /></td>
			<td width="5%"></td>
		</tr>
		<tr>
		  <td width="5%"></td>
			<td><tiles:insertAttribute name="footer" /></td>
			<td width="5%"></td>
		</tr>
	</table>
</body>
</html>