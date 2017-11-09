<html>
<head>
	<title>${title!"我是默认标题"}</title>
</head>
<body>
	<#include "first.ftl">
		<label>id</label>${student.id}<br>
		<label>姓名</label>${student.name}<br>
		<label>地址</label>${student.address}<br>
	
	<table>
	
	<#list persons as p>
	<#if p_index==0 || p_index%2== 0 >
		<tr style="background:red ">
	<#else>
		<tr>
	</#if>
			<td>${p_index}</td>
			<td>${p.id}</td>
			<td>${p.name}</td>
			<td>${p.address}</td>
		</tr>
	</#list>
	</table>
	<#if currentDate??>
		${currentDate?string("yyyy-mm-dd HH:MM:ss")}
	</#if>
</body>
</html>
