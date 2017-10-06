<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <xsl:text disable-output-escaping="yes"><![CDATA[<!DOCTYPE html>]]></xsl:text>
    <html>
    <head>
      <title>Textlint Result</title>
    </head>
    <body>
      <xsl:apply-templates/>
    </body>
    </html>
  </xsl:template>
  <xsl:template match="testsuite">
    <div class="testsuite">
      <h1><xsl:value-of select="./@name" /></h1>
      <ul>
        <xsl:apply-templates select=".//failure" />
      </ul>
    </div>
  </xsl:template>
  <xsl:template match="failure">
      <li><xsl:value-of select="." disable-output-escaping="yes" /></li>
  </xsl:template>
</xsl:stylesheet>

