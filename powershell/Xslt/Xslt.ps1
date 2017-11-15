function Xslt($xsltPath, $xmlPath, $outputPath) {
    $xslt = New-Object System.Xml.Xsl.XslCompiledTransform
    $xslt.Load($xsltPath)
    $xslt.Transform($xmlPath, $outputPath)
}

function PrintUsage() {
    Write-Host "Useage:"
    Write-Host "    Xslt XSLT_FILE XML_FILE OUTPUT_PATH"
}

if ($Args.Length -eq 3) {
    Xslt $Args[0] $Args[1] $Args[2]
} else {
    PrintUsage
}

