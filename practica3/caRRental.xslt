<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/carrrental">
		<html>
			<head>
				<title>ALL THE RENTALS</title>
			</head>
		
			<body>
				<xsl:apply-templates select="rental"/> <br />
			</body>
		</html>
	</xsl:template>	
	<xsl:template match="rental">
				<table border="0">
					
				<h1>ID: <xsl:value-of select="id"/></h1><br />
				<h2>Make: <xsl:value-of select="make"/></h2><br />
				<h2>Model: <xsl:value-of select="model"/></h2><br />
				<h2>Num of days: <xsl:value-of select="nofdays"/></h2><br />
				<h2>Num of units: <xsl:value-of select="nofounits"/></h2><br /> 
				<h2>Discount:<xsl:value-of select="discount"/></h2><br />

				</table>
			
	</xsl:template>
</xsl:stylesheet>
